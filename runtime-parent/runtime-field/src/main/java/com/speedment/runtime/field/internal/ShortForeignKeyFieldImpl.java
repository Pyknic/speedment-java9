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
import com.speedment.runtime.field.ShortField;
import com.speedment.runtime.field.ShortForeignKeyField;
import com.speedment.runtime.field.internal.comparator.ShortFieldComparator;
import com.speedment.runtime.field.internal.comparator.ShortFieldComparatorImpl;
import com.speedment.runtime.field.internal.method.BackwardFinderImpl;
import com.speedment.runtime.field.internal.method.FindFromShort;
import com.speedment.runtime.field.internal.method.GetShortImpl;
import com.speedment.runtime.field.internal.predicate.shorts.ShortBetweenPredicate;
import com.speedment.runtime.field.internal.predicate.shorts.ShortEqualPredicate;
import com.speedment.runtime.field.internal.predicate.shorts.ShortGreaterOrEqualPredicate;
import com.speedment.runtime.field.internal.predicate.shorts.ShortGreaterThanPredicate;
import com.speedment.runtime.field.internal.predicate.shorts.ShortInPredicate;
import com.speedment.runtime.field.method.BackwardFinder;
import com.speedment.runtime.field.method.FindFrom;
import com.speedment.runtime.field.method.GetShort;
import com.speedment.runtime.field.method.ShortGetter;
import com.speedment.runtime.field.method.ShortSetter;
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
public final class ShortForeignKeyFieldImpl<ENTITY, D, FK_ENTITY> implements ShortField<ENTITY, D>, ShortForeignKeyField<ENTITY, D, FK_ENTITY> {
    
    private final ColumnIdentifier<ENTITY> identifier;
    private final GetShort<ENTITY, D> getter;
    private final ShortSetter<ENTITY> setter;
    private final ShortField<FK_ENTITY, D> referenced;
    private final TypeMapper<D, Short> typeMapper;
    private final boolean unique;
    
    public ShortForeignKeyFieldImpl(
            ColumnIdentifier<ENTITY> identifier,
            ShortGetter<ENTITY> getter,
            ShortSetter<ENTITY> setter,
            ShortField<FK_ENTITY, D> referenced,
            TypeMapper<D, Short> typeMapper,
            boolean unique) {
        this.identifier = requireNonNull(identifier);
        this.getter     = new GetShortImpl<>(this, getter);
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
    public ShortSetter<ENTITY> setter() {
        return setter;
    }
    
    @Override
    public GetShort<ENTITY, D> getter() {
        return getter;
    }
    
    @Override
    public ShortField<FK_ENTITY, D> getReferencedField() {
        return referenced;
    }
    
    @Override
    public BackwardFinder<FK_ENTITY, ENTITY> backwardFinder(TableIdentifier<ENTITY> identifier, Supplier<Stream<ENTITY>> streamSupplier) {
        return new BackwardFinderImpl<>(this, identifier, streamSupplier);
    }
    
    @Override
    public FindFrom<ENTITY, FK_ENTITY> finder(TableIdentifier<FK_ENTITY> identifier, Supplier<Stream<FK_ENTITY>> streamSupplier) {
        return new FindFromShort<>(this, referenced, identifier, streamSupplier);
    }
    
    @Override
    public TypeMapper<D, Short> typeMapper() {
        return typeMapper;
    }
    
    @Override
    public boolean isUnique() {
        return unique;
    }
    
    @Override
    public ShortFieldComparator<ENTITY, D> comparator() {
        return new ShortFieldComparatorImpl<>(this);
    }
    
    @Override
    public ShortFieldComparator<ENTITY, D> comparatorNullFieldsFirst() {
        return comparator();
    }
    
    @Override
    public ShortFieldComparator<ENTITY, D> comparatorNullFieldsLast() {
        return comparator();
    }
    
    @Override
    public FieldPredicate<ENTITY> equal(Short value) {
        return new ShortEqualPredicate<>(this, value);
    }
    
    @Override
    public FieldPredicate<ENTITY> greaterThan(Short value) {
        return new ShortGreaterThanPredicate<>(this, value);
    }
    
    @Override
    public FieldPredicate<ENTITY> greaterOrEqual(Short value) {
        return new ShortGreaterOrEqualPredicate<>(this, value);
    }
    
    @Override
    public FieldPredicate<ENTITY> between(Short start, Short end, Inclusion inclusion) {
        return new ShortBetweenPredicate<>(this, start, end, inclusion);
    }
    
    @Override
    public FieldPredicate<ENTITY> in(Collection<Short> values) {
        return new ShortInPredicate<>(this, collectionToSet(values));
    }
    
    @Override
    public Predicate<ENTITY> notEqual(Short value) {
        return new ShortEqualPredicate<>(this, value).negate();
    }
    
    @Override
    public Predicate<ENTITY> lessOrEqual(Short value) {
        return new ShortGreaterThanPredicate<>(this, value).negate();
    }
    
    @Override
    public Predicate<ENTITY> lessThan(Short value) {
        return new ShortGreaterOrEqualPredicate<>(this, value).negate();
    }
    
    @Override
    public Predicate<ENTITY> notBetween(Short start, Short end, Inclusion inclusion) {
        return new ShortBetweenPredicate<>(this, start, end, inclusion).negate();
    }
    
    @Override
    public Predicate<ENTITY> notIn(Collection<Short> values) {
        return new ShortInPredicate<>(this, collectionToSet(values)).negate();
    }
}