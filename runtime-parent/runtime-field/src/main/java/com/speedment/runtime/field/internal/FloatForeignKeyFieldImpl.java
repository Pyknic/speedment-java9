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
import com.speedment.runtime.field.FloatField;
import com.speedment.runtime.field.FloatForeignKeyField;
import com.speedment.runtime.field.internal.comparator.FloatFieldComparator;
import com.speedment.runtime.field.internal.comparator.FloatFieldComparatorImpl;
import com.speedment.runtime.field.internal.method.BackwardFinderImpl;
import com.speedment.runtime.field.internal.method.FindFromFloat;
import com.speedment.runtime.field.internal.method.GetFloatImpl;
import com.speedment.runtime.field.internal.predicate.floats.FloatBetweenPredicate;
import com.speedment.runtime.field.internal.predicate.floats.FloatEqualPredicate;
import com.speedment.runtime.field.internal.predicate.floats.FloatGreaterOrEqualPredicate;
import com.speedment.runtime.field.internal.predicate.floats.FloatGreaterThanPredicate;
import com.speedment.runtime.field.internal.predicate.floats.FloatInPredicate;
import com.speedment.runtime.field.method.BackwardFinder;
import com.speedment.runtime.field.method.FindFrom;
import com.speedment.runtime.field.method.FloatGetter;
import com.speedment.runtime.field.method.FloatSetter;
import com.speedment.runtime.field.method.GetFloat;
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
public final class FloatForeignKeyFieldImpl<ENTITY, D, FK_ENTITY> implements FloatField<ENTITY, D>, FloatForeignKeyField<ENTITY, D, FK_ENTITY> {
    
    private final ColumnIdentifier<ENTITY> identifier;
    private final GetFloat<ENTITY, D> getter;
    private final FloatSetter<ENTITY> setter;
    private final FloatField<FK_ENTITY, D> referenced;
    private final TypeMapper<D, Float> typeMapper;
    private final boolean unique;
    
    public FloatForeignKeyFieldImpl(
            ColumnIdentifier<ENTITY> identifier,
            FloatGetter<ENTITY> getter,
            FloatSetter<ENTITY> setter,
            FloatField<FK_ENTITY, D> referenced,
            TypeMapper<D, Float> typeMapper,
            boolean unique) {
        this.identifier = requireNonNull(identifier);
        this.getter     = new GetFloatImpl<>(this, getter);
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
    public FloatSetter<ENTITY> setter() {
        return setter;
    }
    
    @Override
    public GetFloat<ENTITY, D> getter() {
        return getter;
    }
    
    @Override
    public FloatField<FK_ENTITY, D> getReferencedField() {
        return referenced;
    }
    
    @Override
    public BackwardFinder<FK_ENTITY, ENTITY> backwardFinder(TableIdentifier<ENTITY> identifier, Supplier<Stream<ENTITY>> streamSupplier) {
        return new BackwardFinderImpl<>(this, identifier, streamSupplier);
    }
    
    @Override
    public FindFrom<ENTITY, FK_ENTITY> finder(TableIdentifier<FK_ENTITY> identifier, Supplier<Stream<FK_ENTITY>> streamSupplier) {
        return new FindFromFloat<>(this, referenced, identifier, streamSupplier);
    }
    
    @Override
    public TypeMapper<D, Float> typeMapper() {
        return typeMapper;
    }
    
    @Override
    public boolean isUnique() {
        return unique;
    }
    
    @Override
    public FloatFieldComparator<ENTITY, D> comparator() {
        return new FloatFieldComparatorImpl<>(this);
    }
    
    @Override
    public FloatFieldComparator<ENTITY, D> comparatorNullFieldsFirst() {
        return comparator();
    }
    
    @Override
    public FloatFieldComparator<ENTITY, D> comparatorNullFieldsLast() {
        return comparator();
    }
    
    @Override
    public FieldPredicate<ENTITY> equal(Float value) {
        return new FloatEqualPredicate<>(this, value);
    }
    
    @Override
    public FieldPredicate<ENTITY> greaterThan(Float value) {
        return new FloatGreaterThanPredicate<>(this, value);
    }
    
    @Override
    public FieldPredicate<ENTITY> greaterOrEqual(Float value) {
        return new FloatGreaterOrEqualPredicate<>(this, value);
    }
    
    @Override
    public FieldPredicate<ENTITY> between(Float start, Float end, Inclusion inclusion) {
        return new FloatBetweenPredicate<>(this, start, end, inclusion);
    }
    
    @Override
    public FieldPredicate<ENTITY> in(Collection<Float> values) {
        return new FloatInPredicate<>(this, collectionToSet(values));
    }
    
    @Override
    public Predicate<ENTITY> notEqual(Float value) {
        return new FloatEqualPredicate<>(this, value).negate();
    }
    
    @Override
    public Predicate<ENTITY> lessOrEqual(Float value) {
        return new FloatGreaterThanPredicate<>(this, value).negate();
    }
    
    @Override
    public Predicate<ENTITY> lessThan(Float value) {
        return new FloatGreaterOrEqualPredicate<>(this, value).negate();
    }
    
    @Override
    public Predicate<ENTITY> notBetween(Float start, Float end, Inclusion inclusion) {
        return new FloatBetweenPredicate<>(this, start, end, inclusion).negate();
    }
    
    @Override
    public Predicate<ENTITY> notIn(Collection<Float> values) {
        return new FloatInPredicate<>(this, collectionToSet(values)).negate();
    }
}