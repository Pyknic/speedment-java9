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
package com.speedment.runtime.field.internal.predicate.chars;

import com.speedment.common.annotation.GeneratedCode;
import com.speedment.common.tuple.Tuple1;
import com.speedment.runtime.field.internal.predicate.AbstractFieldPredicate;
import com.speedment.runtime.field.predicate.PredicateType;
import com.speedment.runtime.field.trait.HasCharValue;

/**
 * @param <ENTITY> entity type
 * @param <D>      database type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
@GeneratedCode(value = "Speedment")
public final class CharGreaterOrEqualPredicate<ENTITY, D> 
extends AbstractFieldPredicate<ENTITY, HasCharValue<ENTITY, D>> 
implements Tuple1<Character> {
    
    private final char value;
    
    public CharGreaterOrEqualPredicate(HasCharValue<ENTITY, D> field, char value) {
        super(PredicateType.GREATER_OR_EQUAL, field, entity -> field.getAsChar(entity) >= value);
        this.value = value;
    }
    
    @Override
    public Character get0() {
        return value;
    }
    
    @Override
    public CharLessThanPredicate<ENTITY, D> negate() {
        return new CharLessThanPredicate<>(getField(), value);
    }
}