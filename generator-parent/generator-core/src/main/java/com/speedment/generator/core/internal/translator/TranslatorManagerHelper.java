/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.generator.core.internal.translator;

import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.Meta;
import com.speedment.common.codegen.internal.java.JavaGenerator;
import static com.speedment.common.codegen.internal.util.NullUtil.requireNonNulls;
import com.speedment.common.codegen.model.File;
import com.speedment.common.codegen.util.Formatting;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.generator.core.component.EventComponent;
import com.speedment.generator.core.component.PathComponent;
import com.speedment.generator.core.event.AfterGenerate;
import com.speedment.generator.core.event.BeforeGenerate;
import com.speedment.generator.core.event.FileGenerated;
import com.speedment.generator.core.internal.util.HashUtil;
import com.speedment.generator.translator.Translator;
import com.speedment.generator.translator.TranslatorManager;
import com.speedment.generator.translator.component.CodeGenerationComponent;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.config.trait.HasEnabled;
import static com.speedment.runtime.config.util.DocumentDbUtil.traverseOver;
import com.speedment.runtime.core.component.InfoComponent;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.core.exception.SpeedmentException;
import com.speedment.runtime.core.internal.util.Statistics;
import static com.speedment.runtime.core.internal.util.Statistics.Event.GENERATE;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 * @since 3.0.2
 */
public final class TranslatorManagerHelper {

    private static final Logger LOGGER
        = LoggerManager.getLogger(TranslatorManagerHelper.class);

    private static final String HASH_PREFIX = ".";
    private static final String HASH_SUFFIX = ".md5";

    private final AtomicInteger fileCounter = new AtomicInteger(0);
    
    @Inject
    private InfoComponent info;
    @Inject
    private PathComponent paths;
    @Inject
    private EventComponent events;
    @Inject
    private ProjectComponent projects;
    @Inject
    private CodeGenerationComponent codeGenerationComponent;

    public void accept(TranslatorManager delegator, Project project) {
        requireNonNull(project);
        Statistics.report(info, projects, GENERATE);

        final List<Translator<?, ?>> writeOnceTranslators = new ArrayList<>();
        final List<Translator<?, ?>> writeAlwaysTranslators = new ArrayList<>();
        final Generator gen = new JavaGenerator();

        fileCounter.set(0);
        Formatting.tab("    ");

        events.notify(new BeforeGenerate(project, gen, delegator));

        codeGenerationComponent.translators(project)
            .forEachOrdered(t -> {
                if (t.isInGeneratedPackage()) {
                    writeAlwaysTranslators.add(t);
                } else {
                    writeOnceTranslators.add(t);
                }
            });

        traverseOver(project, Table.class)
            .filter(HasEnabled::test)
            .forEach(table
                -> codeGenerationComponent.translators(table).forEachOrdered(t -> {
                if (t.isInGeneratedPackage()) {
                    writeAlwaysTranslators.add(t);
                } else {
                    writeOnceTranslators.add(t);
                }
            })
            );

        // Erase any previous unmodified files.
        delegator.clearExistingFiles(project);

        // Write generated code to file.
        gen.metaOn(writeOnceTranslators.stream()
            .map(Translator::get)
            .collect(Collectors.toList())
        ).forEach(meta -> delegator.writeToFile(project, meta, false));

        gen.metaOn(writeAlwaysTranslators.stream()
            .map(Translator::get)
            .collect(Collectors.toList())
        ).forEach(meta -> delegator.writeToFile(project, meta, true));

        LOGGER.info("Wrote %d files in %s", getFilesCreated(), paths.packageLocation());

        events.notify(new AfterGenerate(project, gen, delegator));
    }

    public void clearExistingFiles(Project project) {
        final Path dir = paths.packageLocation();

        try {
            clearExistingFilesIn(dir);
        } catch (final IOException ex) {
            throw new SpeedmentException(
                "Error! Could not delete files in '" + dir.toString() + "'.", ex
            );
        }
    }

    private void clearExistingFilesIn(Path directory) throws IOException {
        if (Files.exists(directory)) {
            try (final DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
                for (final Path entry : stream) {
                    if (Files.isDirectory(entry)) {
                        clearExistingFilesIn(entry);

                        if (isDirectoryEmpty(entry)) {
                            Files.delete(entry);
                        }
                    } else {
                        final String filename = entry.toFile().getName();
                        if (filename.startsWith(HASH_PREFIX)
                            && filename.endsWith(HASH_SUFFIX)) {
                            final Path original
                                = entry
                                    .getParent() // The hidden folder
                                    .getParent() // The parent folder
                                    .resolve(filename.substring( // Lookup original .java file
                                        HASH_PREFIX.length(),
                                        filename.length() - HASH_SUFFIX.length()
                                    ));

                            if (original.toFile().exists()
                                && HashUtil.compare(original, entry)) {
                                delete(original);
                                delete(entry);
                            }
                        }
                    }
                }
            }
        }
    }

    private static void delete(Path path) throws IOException {
        LOGGER.debug("Deleting '" + path.toString() + "'.");
        Files.delete(path);
    }

    private static boolean isDirectoryEmpty(Path directory) throws IOException {
        try (final DirectoryStream<Path> dirStream = Files.newDirectoryStream(directory)) {
            return !dirStream.iterator().hasNext();
        }
    }

    public void writeToFile(TranslatorManager delegator, Project project, Meta<File, String> meta, boolean overwriteExisting) {
        events.notify(new FileGenerated(project, meta));
        delegator.writeToFile(project, meta.getModel().getName(), meta.getResult(), overwriteExisting);
    }

    public void writeToFile(TranslatorManager delegator, Project project, String filename, String content, boolean overwriteExisting) {
        final Path codePath = paths.packageLocation().resolve(filename);
        delegator.writeToFile(codePath, content, overwriteExisting);
    }

    public void writeToFile(TranslatorManager delegator, Path codePath, String content, boolean overwriteExisting) {
        requireNonNulls(codePath, content);

        try {
            if (overwriteExisting || !codePath.toFile().exists()) {
                final Path hashPath = codePath.getParent()
                    .resolve(secretFolderName())
                    .resolve(HASH_PREFIX + codePath.getFileName().toString() + HASH_SUFFIX);

                write(hashPath, HashUtil.md5(content), true);
                write(codePath, content, false);

                fileCounter.incrementAndGet();
            }
        } catch (final IOException ex) {
            LOGGER.error(ex, "Failed to write file " + codePath);
        }

        LOGGER.trace("*** BEGIN File:" + codePath);
        Stream.of(content.split(Formatting.nl())).forEachOrdered(LOGGER::trace);
        LOGGER.trace("*** END   File:" + codePath);

    }

    public int getFilesCreated() {
        return fileCounter.get();
    }

    private static void write(Path path, String content, boolean hidden) throws IOException {
        LOGGER.debug("Creating '" + path.toString() + "'.");

        final Path parent = path.getParent();
        try {
            if (parent != null) {
                Files.createDirectories(parent);
                if (hidden) {
                    setAttributeHidden(parent);
                }
            }
        } catch (final SecurityException se) {
            throw new SpeedmentException("Unable to create directory " + parent.toString(), se);
        }

        Files.write(path, content.getBytes(StandardCharsets.UTF_8),
            StandardOpenOption.CREATE,
            StandardOpenOption.TRUNCATE_EXISTING
        );

        if (hidden) {
            setAttributeHidden(path);
        }
    }

    private static void setAttributeHidden(Path path) {
        try {
            Files.setAttribute(path, "dos:hidden", true);
        } catch (final IOException | UnsupportedOperationException e) {
            // Ignore. Maybe this is Linux or MacOS
        }
    }

    private String secretFolderName() {
        return "." + info.getTitle()
            .replace(" ", "")
            .replace(".", "")
            .replace("/", "")
            .toLowerCase();
    }
}
