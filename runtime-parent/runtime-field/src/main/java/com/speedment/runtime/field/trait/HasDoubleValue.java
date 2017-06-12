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
package com.speedment.runtime.field.trait;

import com.speedment.common.annotation.GeneratedCode;
import com.speedment.runtime.field.Field;
import com.speedment.runtime.field.internal.method.SetToDoubleImpl;
import com.speedment.runtime.field.method.DoubleSetter;
import com.speedment.runtime.field.method.GetDouble;
import com.speedment.runtime.field.method.SetToDouble;
import com.speedment.runtime.typemapper.TypeMapper;

/**
 * A representation of an Entity field that is a primitive {@code double} type.
 * 
 * @param <ENTITY> entity type
 * @param <D>      database type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
@GeneratedCode(value = "Speedment")
public interface HasDoubleValue<ENTITY, D> extends Field<ENTITY> {
    
    @Override
    DoubleSetter<ENTITY> setter();
    
    @Override
    GetDouble<ENTITY, D> getter();
    
    @Override
    TypeMapper<D, Double> typeMapper();
    
    /**
     * Gets the value from the Entity field.
     * 
     * @param entity the entity
     * @return       the value of the field
     */
    default double getAsDouble(ENTITY entity) {
        return getter().applyAsDouble(entity);
    }
    
    /**
     * Sets the value in the given Entity.
     * 
     * @param entity the entity
     * @param value  to set
     * @return       the entity itself
     */
    default ENTITY set(ENTITY entity, double value) {
        return setter().setAsDouble(entity, value);
    }
    
    /**
     * Creates and returns a setter handler with a given value.
     * 
     * @param value to set
     * @return      a set-operation with a given value
     */
    default SetToDouble<ENTITY, D> setTo(double value) {
        return new SetToDoubleImpl<>(this, value);
    }
}