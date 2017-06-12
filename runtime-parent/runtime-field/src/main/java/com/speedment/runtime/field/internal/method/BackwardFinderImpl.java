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

import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.field.Field;
import com.speedment.runtime.field.method.BackwardFinder;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.field.trait.HasFinder;

import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 *
 * @param <ENTITY>     the source entity
 * @param <FK_ENTITY>  the target entity
 * @param <T>          the column type
 * @param <FIELD>      the field type
 * 
 * @author  Emil Forslund
 * @since   3.0.0
 */
public final class BackwardFinderImpl<
        ENTITY, 
        FK_ENTITY, 
        T extends Comparable<? super T>, 
        FIELD extends Field<FK_ENTITY> 
                    & HasComparableOperators<FK_ENTITY, T> 
                    & HasFinder<FK_ENTITY, ENTITY>>
implements BackwardFinder<ENTITY, FK_ENTITY> {

    private final FIELD target;
    private final TableIdentifier<FK_ENTITY> identifier;
    private final Supplier<Stream<FK_ENTITY>> streamSupplier;
    
    public BackwardFinderImpl(
            FIELD target, 
            TableIdentifier<FK_ENTITY> identifier, 
            Supplier<Stream<FK_ENTITY>> streamSupplier) {
        
        this.target         = requireNonNull(target);
        this.identifier     = requireNonNull(identifier);
        this.streamSupplier = requireNonNull(streamSupplier);
    }
    
    @Override
    public final FIELD getField() {
        return target;
    }

    @Override
    public final TableIdentifier<FK_ENTITY> getTableIdentifier() {
        return identifier;
    }

    @Override
    public Stream<FK_ENTITY> apply(ENTITY entity) {
        @SuppressWarnings("unchecked")
        final T value = (T) getField().getReferencedField().getter().apply(entity);
        if (value == null) {
            return null;
        } else {
            return streamSupplier.get().filter(getField().equal(value));
        }
    }
}