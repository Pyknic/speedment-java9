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
package com.speedment.generator.translator;

import com.speedment.common.codegen.internal.java.JavaGenerator;
import com.speedment.common.injector.InjectBundle;
import com.speedment.generator.translator.internal.component.CodeGenerationComponentImpl;
import com.speedment.generator.translator.internal.component.TypeMapperComponentImpl;
import com.speedment.generator.translator.internal.namer.JavaLanguageNamerImpl;

import java.util.stream.Stream;

/**
 *
 * @author  Emil Forslund
 * @since   3.0.1
 */
public final class TranslatorBundle implements InjectBundle {

    @Override
    public Stream<Class<?>> injectables() {
        return Stream.of(
            CodeGenerationComponentImpl.class,
            TypeMapperComponentImpl.class,
            JavaLanguageNamerImpl.class,
            JavaGenerator.class
        );
    }
}