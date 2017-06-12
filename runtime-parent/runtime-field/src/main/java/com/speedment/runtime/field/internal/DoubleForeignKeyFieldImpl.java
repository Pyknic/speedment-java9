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
import com.speedment.runtime.field.DoubleField;
import com.speedment.runtime.field.DoubleForeignKeyField;
import com.speedment.runtime.field.internal.comparator.DoubleFieldComparator;
import com.speedment.runtime.field.internal.comparator.DoubleFieldComparatorImpl;
import com.speedment.runtime.field.internal.method.BackwardFinderImpl;
import com.speedment.runtime.field.internal.method.FindFromDouble;
import com.speedment.runtime.field.internal.method.GetDoubleImpl;
import com.speedment.runtime.field.internal.predicate.doubles.DoubleBetweenPredicate;
import com.speedment.runtime.field.internal.predicate.doubles.DoubleEqualPredicate;
import com.speedment.runtime.field.internal.predicate.doubles.DoubleGreaterOrEqualPredicate;
import com.speedment.runtime.field.internal.predicate.doubles.DoubleGreaterThanPredicate;
import com.speedment.runtime.field.internal.predicate.doubles.DoubleInPredicate;
import com.speedment.runtime.field.method.BackwardFinder;
import com.speedment.runtime.field.method.DoubleGetter;
import com.speedment.runtime.field.method.DoubleSetter;
import com.speedment.runtime.field.method.FindFrom;
import com.speedment.runtime.field.method.GetDouble;
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
public final class DoubleForeignKeyFieldImpl<ENTITY, D, FK_ENTITY> implements DoubleField<ENTITY, D>, DoubleForeignKeyField<ENTITY, D, FK_ENTITY> {
    
    private final ColumnIdentifier<ENTITY> identifier;
    private final GetDouble<ENTITY, D> getter;
    private final DoubleSetter<ENTITY> setter;
    private final DoubleField<FK_ENTITY, D> referenced;
    private final TypeMapper<D, Double> typeMapper;
    private final boolean unique;
    
    public DoubleForeignKeyFieldImpl(
            ColumnIdentifier<ENTITY> identifier,
            DoubleGetter<ENTITY> getter,
            DoubleSetter<ENTITY> setter,
            DoubleField<FK_ENTITY, D> referenced,
            TypeMapper<D, Double> typeMapper,
            boolean unique) {
        this.identifier = requireNonNull(identifier);
        this.getter     = new GetDoubleImpl<>(this, getter);
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
    public DoubleSetter<ENTITY> setter() {
        return setter;
    }
    
    @Override
    public GetDouble<ENTITY, D> getter() {
        return getter;
    }
    
    @Override
    public DoubleField<FK_ENTITY, D> getReferencedField() {
        return referenced;
    }
    
    @Override
    public BackwardFinder<FK_ENTITY, ENTITY> backwardFinder(TableIdentifier<ENTITY> identifier, Supplier<Stream<ENTITY>> streamSupplier) {
        return new BackwardFinderImpl<>(this, identifier, streamSupplier);
    }
    
    @Override
    public FindFrom<ENTITY, FK_ENTITY> finder(TableIdentifier<FK_ENTITY> identifier, Supplier<Stream<FK_ENTITY>> streamSupplier) {
        return new FindFromDouble<>(this, referenced, identifier, streamSupplier);
    }
    
    @Override
    public TypeMapper<D, Double> typeMapper() {
        return typeMapper;
    }
    
    @Override
    public boolean isUnique() {
        return unique;
    }
    
    @Override
    public DoubleFieldComparator<ENTITY, D> comparator() {
        return new DoubleFieldComparatorImpl<>(this);
    }
    
    @Override
    public DoubleFieldComparator<ENTITY, D> comparatorNullFieldsFirst() {
        return comparator();
    }
    
    @Override
    public DoubleFieldComparator<ENTITY, D> comparatorNullFieldsLast() {
        return comparator();
    }
    
    @Override
    public FieldPredicate<ENTITY> equal(Double value) {
        return new DoubleEqualPredicate<>(this, value);
    }
    
    @Override
    public FieldPredicate<ENTITY> greaterThan(Double value) {
        return new DoubleGreaterThanPredicate<>(this, value);
    }
    
    @Override
    public FieldPredicate<ENTITY> greaterOrEqual(Double value) {
        return new DoubleGreaterOrEqualPredicate<>(this, value);
    }
    
    @Override
    public FieldPredicate<ENTITY> between(Double start, Double end, Inclusion inclusion) {
        return new DoubleBetweenPredicate<>(this, start, end, inclusion);
    }
    
    @Override
    public FieldPredicate<ENTITY> in(Collection<Double> values) {
        return new DoubleInPredicate<>(this, collectionToSet(values));
    }
    
    @Override
    public Predicate<ENTITY> notEqual(Double value) {
        return new DoubleEqualPredicate<>(this, value).negate();
    }
    
    @Override
    public Predicate<ENTITY> lessOrEqual(Double value) {
        return new DoubleGreaterThanPredicate<>(this, value).negate();
    }
    
    @Override
    public Predicate<ENTITY> lessThan(Double value) {
        return new DoubleGreaterOrEqualPredicate<>(this, value).negate();
    }
    
    @Override
    public Predicate<ENTITY> notBetween(Double start, Double end, Inclusion inclusion) {
        return new DoubleBetweenPredicate<>(this, start, end, inclusion).negate();
    }
    
    @Override
    public Predicate<ENTITY> notIn(Collection<Double> values) {
        return new DoubleInPredicate<>(this, collectionToSet(values)).negate();
    }
}