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
package com.speedment.generator.standard.lifecycle;

import com.speedment.common.codegen.constant.SimpleType;
import com.speedment.common.codegen.model.File;
import com.speedment.common.codegen.model.Interface;
import com.speedment.generator.translator.AbstractJavaClassTranslator;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.core.ApplicationBuilder;

import java.lang.reflect.Type;

/**
 *
 * @author Emil Forslund
 * @since  2.0.0
 */
public final class ApplicationTranslator extends AbstractJavaClassTranslator<Project, Interface> {

    public ApplicationTranslator(Project project) {
        
        super(project, Interface::of);
    }

    @Override
    protected String getClassOrInterfaceName() {
        return getSupport().typeName(getSupport().projectOrThrow()) + "Application";
    }

    @Override
    protected Interface makeCodeGenModel(File file) {
        return newBuilder(file, getClassOrInterfaceName())
            .forEveryProject((clazz, project) -> {
                clazz.public_().add(generatedType());
            }).build();
    }

    @Override
    protected String getJavadocRepresentText() {
        return "An {@link " + ApplicationBuilder.class.getName() + 
            "} interface for the {@link " + Project.class.getName() + 
            "} named " + getSupport().projectOrThrow().getId() + ".";
    }
    
    private Type generatedType() {
        return SimpleType.create(
            getSupport().basePackageName() + ".generated.Generated" + 
            getSupport().typeName(getSupport().projectOrThrow()) + "Application"
        );
    }
}