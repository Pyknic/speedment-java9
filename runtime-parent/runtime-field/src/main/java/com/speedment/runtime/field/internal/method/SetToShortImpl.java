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
package com.speedment.runtime.field.internal.method;

import com.speedment.common.annotation.GeneratedCode;
import com.speedment.runtime.field.method.SetToShort;
import com.speedment.runtime.field.trait.HasShortValue;
import static java.util.Objects.requireNonNull;

/**
 * A {@code set} operation that will apply a value {@link #getValue()} to the
 * field {@link #getField()} of any instance passed to it.
 * <p>
 * This particular implementation is for values of type {@code short}.
 * 
 * @param <ENTITY> entity type
 * @param <D>      database type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
@GeneratedCode(value = "Speedment")
public final class SetToShortImpl<ENTITY, D> implements SetToShort<ENTITY, D> {
    
    private final HasShortValue<ENTITY, D> field;
    private final short newValue;
    
    public SetToShortImpl(HasShortValue<ENTITY, D> field, short newValue) {
        this.field    = requireNonNull(field);
        this.newValue = requireNonNull(newValue);
    }
    
    @Override
    public HasShortValue<ENTITY, D> getField() {
        return field;
    }
    
    @Override
    public short getValue() {
        return newValue;
    }
    
    @Override
    public ENTITY apply(ENTITY entity) {
        return field.setter().setAsShort(entity, newValue);
    }
}