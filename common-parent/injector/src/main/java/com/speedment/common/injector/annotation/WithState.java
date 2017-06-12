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
package com.speedment.common.injector.annotation;

import com.speedment.common.injector.State;

import java.lang.annotation.*;

/**
 * Annotes that the method parameter should be set to 
 * something with a certain state.
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
@Inherited
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface WithState {
    
    /**
     * The {@link State} that the injected value must be in before 
     * it can be injected.
     * <p>
     * The default state is {@link State#CREATED}.
     * 
     * @return  the expected phase of the injected component
     */
    State value() default State.CREATED;
}
