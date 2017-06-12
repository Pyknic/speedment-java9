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
package com.speedment.runtime.field;

import com.speedment.common.annotation.GeneratedCode;
import com.speedment.runtime.config.identifier.ColumnIdentifier;
import com.speedment.runtime.field.internal.FloatForeignKeyFieldImpl;
import com.speedment.runtime.field.method.FloatGetter;
import com.speedment.runtime.field.method.FloatSetter;
import com.speedment.runtime.field.trait.HasFinder;
import com.speedment.runtime.typemapper.TypeMapper;

/**
 * A field that represents a primitive {@code float} value that references
 * another column using a foreign key.
 * 
 * @param <ENTITY>    entity type
 * @param <D>         database type
 * @param <FK_ENTITY> foreign entity type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 * 
 * @see ReferenceField
 * @see ComparableForeignKeyField
 */
@GeneratedCode(value = "Speedment")
public interface FloatForeignKeyField<ENTITY, D, FK_ENTITY> extends FloatField<ENTITY, D>, HasFinder<ENTITY, FK_ENTITY> {
    
    /**
     * Creates a new {@link FloatForeignKeyField} using the default
     * implementation.
     * 
     * @param <ENTITY>    entity type
     * @param <D>         database type
     * @param <FK_ENTITY> foreign entity type
     * @param identifier  column that this field represents
     * @param getter      method reference to the getter in the entity
     * @param setter      method reference to the setter in the entity
     * @param referenced  field in the foreign entity that is referenced
     * @param typeMapper  type mapper that is applied
     * @param unique      if represented column only contains unique values
     * @return            the created field
     */
    static <ENTITY, D, FK_ENTITY> FloatForeignKeyField<ENTITY, D, FK_ENTITY> create(
    ColumnIdentifier<ENTITY> identifier,
            FloatGetter<ENTITY> getter,
            FloatSetter<ENTITY> setter,
            FloatField<FK_ENTITY, D> referenced,
            TypeMapper<D, Float> typeMapper,
            boolean unique) {
        return new FloatForeignKeyFieldImpl<>(
            identifier, getter, setter, referenced, typeMapper, unique
        );
    }
}