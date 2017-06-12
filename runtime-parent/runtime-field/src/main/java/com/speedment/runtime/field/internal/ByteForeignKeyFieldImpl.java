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
package com.speedment.runtime.field.internal;

import com.speedment.common.annotation.GeneratedCode;
import com.speedment.runtime.config.identifier.ColumnIdentifier;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.field.ByteField;
import com.speedment.runtime.field.ByteForeignKeyField;
import com.speedment.runtime.field.internal.comparator.ByteFieldComparator;
import com.speedment.runtime.field.internal.comparator.ByteFieldComparatorImpl;
import com.speedment.runtime.field.internal.method.BackwardFinderImpl;
import com.speedment.runtime.field.internal.method.FindFromByte;
import com.speedment.runtime.field.internal.method.GetByteImpl;
import com.speedment.runtime.field.internal.predicate.bytes.ByteBetweenPredicate;
import com.speedment.runtime.field.internal.predicate.bytes.ByteEqualPredicate;
import com.speedment.runtime.field.internal.predicate.bytes.ByteGreaterOrEqualPredicate;
import com.speedment.runtime.field.internal.predicate.bytes.ByteGreaterThanPredicate;
import com.speedment.runtime.field.internal.predicate.bytes.ByteInPredicate;
import com.speedment.runtime.field.method.BackwardFinder;
import com.speedment.runtime.field.method.ByteGetter;
import com.speedment.runtime.field.method.ByteSetter;
import com.speedment.runtime.field.method.FindFrom;
import com.speedment.runtime.field.method.GetByte;
import com.speedment.runtime.field.predicate.FieldPredicate;
import com.speedment.runtime.field.predicate.Inclusion;
import com.speedment.runtime.typemapper.TypeMapper;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import static com.speedment.runtime.field.internal.util.CollectionUtil.collectionToSet;
import static java.util.Objects.requireNonNull;

/**
 * @param <ENTITY>    entity type
 * @param <D>         database type
 * @param <FK_ENTITY> foreign entity type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
@GeneratedCode(value = "Speedment")
public final class ByteForeignKeyFieldImpl<ENTITY, D, FK_ENTITY> implements ByteField<ENTITY, D>, ByteForeignKeyField<ENTITY, D, FK_ENTITY> {
    
    private final ColumnIdentifier<ENTITY> identifier;
    private final GetByte<ENTITY, D> getter;
    private final ByteSetter<ENTITY> setter;
    private final ByteField<FK_ENTITY, D> referenced;
    private final TypeMapper<D, Byte> typeMapper;
    private final boolean unique;
    
    public ByteForeignKeyFieldImpl(
            ColumnIdentifier<ENTITY> identifier,
            ByteGetter<ENTITY> getter,
            ByteSetter<ENTITY> setter,
            ByteField<FK_ENTITY, D> referenced,
            TypeMapper<D, Byte> typeMapper,
            boolean unique) {
        this.identifier = requireNonNull(identifier);
        this.getter     = new GetByteImpl<>(this, getter);
        this.setter     = requireNonNull(setter);
        this.referenced = requireNonNull(referenced);
        this.typeMapper = requireNonNull(typeMapper);
        this.unique     = unique;
    }
    
    @Override
    public ColumnIdentifier<ENTITY> identifier() {
        return identifier;
    }
    
    @Override
    public ByteSetter<ENTITY> setter() {
        return setter;
    }
    
    @Override
    public GetByte<ENTITY, D> getter() {
        return getter;
    }
    
    @Override
    public ByteField<FK_ENTITY, D> getReferencedField() {
        return referenced;
    }
    
    @Override
    public BackwardFinder<FK_ENTITY, ENTITY> backwardFinder(TableIdentifier<ENTITY> identifier, Supplier<Stream<ENTITY>> streamSupplier) {
        return new BackwardFinderImpl<>(this, identifier, streamSupplier);
    }
    
    @Override
    public FindFrom<ENTITY, FK_ENTITY> finder(TableIdentifier<FK_ENTITY> identifier, Supplier<Stream<FK_ENTITY>> streamSupplier) {
        return new FindFromByte<>(this, referenced, identifier, streamSupplier);
    }
    
    @Override
    public TypeMapper<D, Byte> typeMapper() {
        return typeMapper;
    }
    
    @Override
    public boolean isUnique() {
        return unique;
    }
    
    @Override
    public ByteFieldComparator<ENTITY, D> comparator() {
        return new ByteFieldComparatorImpl<>(this);
    }
    
    @Override
    public ByteFieldComparator<ENTITY, D> comparatorNullFieldsFirst() {
        return comparator();
    }
    
    @Override
    public ByteFieldComparator<ENTITY, D> comparatorNullFieldsLast() {
        return comparator();
    }
    
    @Override
    public FieldPredicate<ENTITY> equal(Byte value) {
        return new ByteEqualPredicate<>(this, value);
    }
    
    @Override
    public FieldPredicate<ENTITY> greaterThan(Byte value) {
        return new ByteGreaterThanPredicate<>(this, value);
    }
    
    @Override
    public FieldPredicate<ENTITY> greaterOrEqual(Byte value) {
        return new ByteGreaterOrEqualPredicate<>(this, value);
    }
    
    @Override
    public FieldPredicate<ENTITY> between(Byte start, Byte end, Inclusion inclusion) {
        return new ByteBetweenPredicate<>(this, start, end, inclusion);
    }
    
    @Override
    public FieldPredicate<ENTITY> in(Collection<Byte> values) {
        return new ByteInPredicate<>(this, collectionToSet(values));
    }
    
    @Override
    public Predicate<ENTITY> notEqual(Byte value) {
        return new ByteEqualPredicate<>(this, value).negate();
    }
    
    @Override
    public Predicate<ENTITY> lessOrEqual(Byte value) {
        return new ByteGreaterThanPredicate<>(this, value).negate();
    }
    
    @Override
    public Predicate<ENTITY> lessThan(Byte value) {
        return new ByteGreaterOrEqualPredicate<>(this, value).negate();
    }
    
    @Override
    public Predicate<ENTITY> notBetween(Byte start, Byte end, Inclusion inclusion) {
        return new ByteBetweenPredicate<>(this, start, end, inclusion).negate();
    }
    
    @Override
    public Predicate<ENTITY> notIn(Collection<Byte> values) {
        return new ByteInPredicate<>(this, collectionToSet(values)).negate();
    }
}