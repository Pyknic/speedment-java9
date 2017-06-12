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
package com.speedment.runtime.field.method;

import com.speedment.runtime.field.trait.HasReferenceValue;

/**
 *
 * @param <ENTITY> the entity type
 * @param <D>      the database type
 * @param <T>      the java type
 * 
 * @author Emil Forslund
 * @since  3.0.2
 */
public interface GetReference<ENTITY, D, T> 
extends ReferenceGetter<ENTITY, T> {
    
    /**
     * Returns the field that created the {@code get()}-operation.
     * 
     * @return  the field
     */
    HasReferenceValue<ENTITY, D, T> getField();
    
}