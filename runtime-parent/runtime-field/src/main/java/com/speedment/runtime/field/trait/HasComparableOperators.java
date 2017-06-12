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

import com.speedment.runtime.field.Field;
import com.speedment.runtime.field.comparator.FieldComparator;
import com.speedment.runtime.field.predicate.Inclusion;

import java.util.Collection;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

/**
 * A representation of an Entity field that is a reference type (e.g.
 * {@code Integer} and not {@code int}) and that implements {@link Comparable}.
 *
 * @param <ENTITY> the entity type
 * @param <V> the field value type
 *
 * @author Per Minborg
 * @author Emil Forslund
 * @since 2.2.0
 */
public interface HasComparableOperators<ENTITY, V extends Comparable<? super V>>
extends Field<ENTITY> {

    /**
     * Returns a {@link Comparator} that will compare to this field using this
     * fields natural order.
     *
     * @return a {@link Comparator} that will compare to this field using this
     * fields natural order
     * @throws NullPointerException if a field is null
     */
    FieldComparator<ENTITY> comparator();

    /**
     * Returns a {@link Comparator} that will compare to this field using this
     * fields natural order, null fields are sorted first.
     *
     * @return a {@link Comparator} that will compare to this field using this
     * fields natural order, null fields are sorted first
     */
    FieldComparator<ENTITY> comparatorNullFieldsFirst();

    /**
     * Returns a {@link Comparator} that will compare to this field using this
     * fields natural order, null fields are sorted last.
     *
     * @return a {@link Comparator} that will compare to this field using this
     * fields natural order, null fields are sorted last
     *
     *
     * @deprecated This method is the same as comparator(). Use that method
     * instead. This method will be removed in coming API versions.
     */
    @Deprecated
    FieldComparator<ENTITY> comparatorNullFieldsLast();

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is <em>equal</em> to the given
     * value.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is <em>equal</em> to the given value
     */
    Predicate<ENTITY> equal(V value);

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is <em>not equal</em> to the
     * given value.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is <em>not equal</em> to the given value
     */
    Predicate<ENTITY> notEqual(V value);

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is <em>less than</em> the given
     * value.
     * <p>
     * If the specified value is {@code null}, the returned predicate will
     * always return {@code false}.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is <em>less than</em> the given value
     */
    Predicate<ENTITY> lessThan(V value);

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is <em>less than or equal</em>
     * to the given value.
     * <p>
     * If the specified value is {@code null}, the returned predicate will only
     * return {@code true} for {@code null} values.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is <em>less than or equal</em> to the given value
     */
    Predicate<ENTITY> lessOrEqual(V value);

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is <em>greater than</em>
     * the given value. If the specified value is {@code null}, the returned
     * predicate will always return {@code false}.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is <em>greater than</em> the given value
     */
    Predicate<ENTITY> greaterThan(V value);

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is <em>greater than or equal</em>
     * to the given value.
     * <p>
     * If the specified value is {@code null}, the returned predicate will only
     * return {@code true} for {@code null} values.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is <em>greater than or equal</em> to the given value
     */
    Predicate<ENTITY> greaterOrEqual(V value);

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is <em>between</em>
     * the given values (inclusive the start value but exclusive the end value).
     * <p>
     * N.B. if the start value is greater or equal to the end value, then the
     * returned Predicate will always evaluate to {@code false}.
     *
     * @param start to compare as a start value
     * @param end to compare as an end value
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is <em>between</em> the given values (inclusive the start
     * value but exclusive the end value)
     */
    default Predicate<ENTITY> between(V start, V end) {
        return between(start, end, Inclusion.START_INCLUSIVE_END_EXCLUSIVE);
    }

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is <em>between</em>
     * the given values and taking the Inclusion parameter into account when
     * determining if either of the end points shall be included in the Field
     * range or not.
     * <p>
     * N.B. if the start value is greater than the end value, then the returned
     * Predicate will always evaluate to {@code false}
     *
     * @param start to compare as a start value
     * @param end to compare as an end value
     * @param inclusion determines if the end points is included in the Field
     * range.
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is <em>between</em> the given values and taking the Inclusion
     * parameter into account when determining if either of the end points shall
     * be included in the Field range or not
     */
    Predicate<ENTITY> between(V start, V end, Inclusion inclusion);

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is <em>not between</em>
     * the given values (inclusive the start value but exclusive the end value).
     * <p>
     * N.B. if the start value is greater than the end value, then the returned
     * Predicate will always evaluate to {@code true}
     *
     * @param start to compare as a start value
     * @param end to compare as an end value
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is <em>not between</em> the given values (inclusive the start
     * value but exclusive the end value)
     */
    default Predicate<ENTITY> notBetween(V start, V end) {
        return notBetween(start, end, Inclusion.START_INCLUSIVE_END_EXCLUSIVE);
    }

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is <em>not between</em>
     * the given values and taking the Inclusion parameter into account when
     * determining if either of the end points shall be included in the Field
     * range or not.
     * <p>
     * N.B. if the start value is greater than the end value, then the returned
     * Predicate will always evaluate to {@code true}
     *
     * @param start to compare as a start value
     * @param end to compare as an end value
     * @param inclusion determines if the end points is included in the Field
     * range.
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is <em>not between</em> the given values and taking the
     * Inclusion parameter into account when determining if either of the end
     * points shall be included in the Field range or not
     */
    Predicate<ENTITY> notBetween(V start, V end, Inclusion inclusion);

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is <em>in</em> the set of given
     * values.
     * <p>
     * N.B. if no values are given, then the returned Predicate will always
     * evaluate to {@code false}
     *
     * @param values the set of values to match towards this Field
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is <em>in</em> the set of given values
     */
    @SuppressWarnings("unchecked")
    default Predicate<ENTITY> in(V... values) {
        return in(Stream.of(values).collect(toSet()));
    }

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is <em>in</em> the given set. (If
     * the collection is not a set, then a set will be created temporarily from
     * the values of the collection).
     * <p>
     * N.B. if the Set is empty, then the returned Predicate will always
     * evaluate to {@code false}
     *
     * @param values the set of values to match towards this Field
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is <em>in</em> the given Set
     */
    Predicate<ENTITY> in(Collection<V> values);

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is <em>not in</em> the set of
     * given values.
     * <p>
     * N.B. if no values are given, then the returned Predicate will always
     * evaluate to {@code true}
     *
     * @param values the set of values to match towards this Field
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is <em>not in</em> the set of given values
     */
    @SuppressWarnings("unchecked")
    default Predicate<ENTITY> notIn(V... values) {
        return notIn(Stream.of(values).collect(toSet()));
    }

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is <em>not in</em> the given Set.
     * (If the collection is not a set, then a set will be created temporarily
     * from the values of the collection).
     * <p>
     * N.B. if the Set is empty, then the returned Predicate will always
     * evaluate to {@code true}
     *
     * @param values the set of values to match towards this Field
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is <em>not in</em> the given Set
     */
    Predicate<ENTITY> notIn(Collection<V> values);
}
