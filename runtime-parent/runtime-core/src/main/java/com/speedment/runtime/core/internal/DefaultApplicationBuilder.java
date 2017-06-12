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
package com.speedment.runtime.core.internal;

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.InjectorBuilder;
import com.speedment.runtime.core.ApplicationMetadata;
import com.speedment.runtime.core.Speedment;

/**
 *
 * @author Emil Forslund
 * @since  3.0.0
 */
public final class DefaultApplicationBuilder extends
    AbstractApplicationBuilder<Speedment, DefaultApplicationBuilder> {

    public DefaultApplicationBuilder(
            ClassLoader classLoader, 
            Class<? extends ApplicationMetadata> metadataClass) {
        
        super(classLoader, SpeedmentImpl.class, metadataClass);
    }
    
    public DefaultApplicationBuilder(
            Class<? extends ApplicationMetadata> metadataClass) {
        
        super(SpeedmentImpl.class, metadataClass);
    }

    public DefaultApplicationBuilder(InjectorBuilder injectorBuilder) {
        super(injectorBuilder);
    }

    @Override
    protected Speedment build(Injector injector) {
        return injector.getOrThrow(Speedment.class);
    }

    @Override
    protected void printWelcomeMessage(Injector injector) {}
}
