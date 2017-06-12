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
package com.speedment.generator.core.event;

import com.speedment.common.codegen.Meta;
import com.speedment.common.codegen.model.File;
import com.speedment.generator.core.event.trait.ProjectEvent;
import com.speedment.runtime.config.Project;

import static java.util.Objects.requireNonNull;

/**
 *
 * @author  Emil Forslund
 * @since   3.0.0
 */

public final class FileGenerated implements ProjectEvent, Event {
    
    private final Project project;
    private final Meta<File, String> meta;

    public FileGenerated(Project project, Meta<File, String> meta) {
        this.project = requireNonNull(project);
        this.meta    = requireNonNull(meta);
    }
    
    public Meta<File, String> meta() {
        return meta;
    }

    @Override
    public Project project() {
        return project;
    }
}