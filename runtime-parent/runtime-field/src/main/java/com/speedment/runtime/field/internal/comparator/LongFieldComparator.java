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
package com.speedment.runtime.field.internal.comparator;

import com.speedment.common.annotation.GeneratedCode;
import com.speedment.runtime.field.comparator.FieldComparator;
import com.speedment.runtime.field.trait.HasLongValue;

/**
 * A predicate that evaluates if a value is between two longs.
 * 
 * @param <ENTITY> entity type
 * @param <D>      database type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
@GeneratedCode(value = "Speedment")
public interface LongFieldComparator<ENTITY, D> extends FieldComparator<ENTITY> {
    
    /**
     * Gets the field that is being compared.
     * 
     * @return the compared field
     */
    @Override
    HasLongValue<ENTITY, D> getField();
}