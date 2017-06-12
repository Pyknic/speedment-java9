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
package com.speedment.maven.component;

import com.speedment.common.injector.annotation.Config;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.generator.core.component.PathComponent;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.core.component.ProjectComponent;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author  Emil Forslund
 * @since   3.0.0
 */
public final class MavenPathComponent implements PathComponent {

    public final static String MAVEN_BASE_DIR = "maven.baseDir";
    
    private @Config(name=MAVEN_BASE_DIR, value="") String mavenBaseDir;
    private @Inject ProjectComponent projectComponent;
    
    @Override
    public Path baseDir() {
        if (mavenBaseDir.isEmpty()) {
            return Paths.get(System.getProperty("user.home"));
        } else {
            return Paths.get(mavenBaseDir);
        }
    }

    @Override
    public Path packageLocation() {
        final Project project = projectComponent.getProject();
        return baseDir().resolve(project.getPackageLocation());
    }

}