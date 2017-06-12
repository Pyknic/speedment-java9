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
package com.speedment.common.singletonstream;

import static com.speedment.common.singletonstream.internal.SingletonUtil.STRICT;
import static com.speedment.common.singletonstream.internal.SingletonUtil.TRIPWIRE_ENABLED;
import static com.speedment.common.singletonstream.internal.SingletonUtil.trip;
import java.util.LongSummaryStatistics;
import java.util.NoSuchElementException;
import java.util.Objects;
import static java.util.Objects.requireNonNull;
import java.util.OptionalDouble;
import java.util.OptionalLong;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * An implementation of a LongStream that takes exactly one element as its
 * source.
 *
 * This implementation supports optimized implementations of most terminal
 * operations and a some number of intermediate operations. Un-optimized
 * operations just returns a wrapped standard LongStream implementation.
 *
 * For performance reasons, the LongStream does not throw an
 * IllegalStateOperation if methods are called after a terminal operation has
 * been called on the Stream. This could be implemented using a boolean value
 * set by each terminating op. All other ops could then assert this flag.
 *
 * @author Per Minborg
 * @since  1.0.0
 */
public class SingletonLongStream implements LongStream {

    private final long element;

    private SingletonLongStream(long element) {
        this.element = element;
    }

    public static LongStream of(long element) {
        return new SingletonLongStream(element);
    }

    @Override
    public LongStream filter(LongPredicate predicate) {
        requireNonNull(predicate);
        if (STRICT) {
            return toStream().filter(predicate);
        }
        return predicate.test(element) ? this : empty();
    }

    @Override
    public LongStream map(LongUnaryOperator mapper) {
        requireNonNull(mapper);
        if (STRICT) {
            return toStream().map(mapper);
        }
        return of(mapper.applyAsLong(element));
    }

    @Override
    public <U> Stream<U> mapToObj(LongFunction<? extends U> mapper) {
        requireNonNull(mapper);
        if (STRICT) {
            return toStream().mapToObj(mapper);
        }
        return SingletonStream.of(mapper.apply(element));
    }

    @Override
    public IntStream mapToInt(LongToIntFunction mapper) {
        requireNonNull(mapper);
        if (STRICT) {
            return toStream().mapToInt(mapper);
        }
        return SingletonIntStream.of(mapper.applyAsInt(element));
    }

    @Override
    public DoubleStream mapToDouble(LongToDoubleFunction mapper) {
        requireNonNull(mapper);
        if (STRICT) {
            return toStream().mapToDouble(mapper);
        }
        return DoubleStream.of(mapper.applyAsDouble(element));
    }

    @Override
    public LongStream flatMap(LongFunction<? extends LongStream> mapper) {
        requireNonNull(mapper);
        if (STRICT) {
            return toStream().flatMap(mapper);
        }
        return mapper.apply(element);
    }

    @Override
    public LongStream distinct() {
        return this;
    }

    @Override
    public LongStream sorted() {
        return this;
    }

    @Override
    public LongStream peek(LongConsumer action) {
        requireNonNull(action);
        return toStream().peek(action);
    }

    @Override
    public LongStream limit(long maxSize) {
        if (maxSize == 0) {
            return empty();
        }
        if (maxSize > 0) {
            return this;
        }
        throw new IllegalArgumentException(Long.toString(maxSize));
    }

    @Override
    public LongStream skip(long n) {
        if (n == 0) {
            return this;
        }
        if (n > 0) {
            return empty();
        }
        throw new IllegalArgumentException(Long.toString(n));
    }

    @Override
    public void forEach(LongConsumer action) {
        requireNonNull(action);
        action.accept(element);
    }

    @Override
    public void forEachOrdered(LongConsumer action) {
        requireNonNull(action);
        action.accept(element);
    }

    @Override
    public long[] toArray() {
        final long[] result = new long[1];
        result[0] = element;
        return result;
    }

    @Override
    public long reduce(long identity, LongBinaryOperator op) {
        requireNonNull(op);
        return op.applyAsLong(identity, element);
    }

    @Override
    public OptionalLong reduce(LongBinaryOperator op) {
        // Just one element so the accumulator is never called.
        return toOptional();
    }

    @Override
    public <R> R collect(Supplier<R> supplier, ObjLongConsumer<R> accumulator, BiConsumer<R, R> combiner) {
        requireNonNull(supplier);
        requireNonNull(accumulator);
        final R value = supplier.get();
        accumulator.accept(value, element);
        // the combiner is never used in a non-parallell stream
        return value;
    }

    @Override
    public long sum() {
        return element;
    }

    @Override
    public OptionalLong min() {
        return toOptional();
    }

    @Override
    public OptionalLong max() {
        return toOptional();
    }

    @Override
    public long count() {
        return 1;
    }

    @Override
    public OptionalDouble average() {
        return OptionalDouble.of(element);
    }

    @Override
    public LongSummaryStatistics summaryStatistics() {
        final LongSummaryStatistics result = new LongSummaryStatistics();
        result.accept(element);
        return result;
    }

    @Override
    public boolean anyMatch(LongPredicate predicate) {
        requireNonNull(predicate);
        return predicate.test(element);
    }

    @Override
    public boolean allMatch(LongPredicate predicate) {
        requireNonNull(predicate);
        return predicate.test(element);
    }

    @Override
    public boolean noneMatch(LongPredicate predicate) {
        requireNonNull(predicate);
        return !predicate.test(element);
    }

    @Override
    public OptionalLong findFirst() {
        return toOptional();
    }

    @Override
    public OptionalLong findAny() {
        return toOptional();
    }

    @Override
    public DoubleStream asDoubleStream() {
        return DoubleStream.of(element);
    }

    @Override
    public Stream<Long> boxed() {
        return mapToObj(Long::valueOf);
    }

    @Override
    public LongStream sequential() {
        return this;
    }

    @Override
    public LongStream parallel() {
        return toStream().parallel();
    }

    @Override
    public PrimitiveIterator.OfLong iterator() {
        return singletonIntIterator(element);
    }

    @Override
    public Spliterator.OfLong spliterator() {
        return singletonIntSpliterator(element);
    }

    @Override
    public boolean isParallel() {
        return false;
    }

    @Override
    public LongStream unordered() {
        return this; // Todo: may convey to singletonSpliterator()
    }

    @Override
    public LongStream onClose(Runnable closeHandler) {
        return toStream().onClose(closeHandler);
    }

    @Override
    public void close() {
        // do nothing. OnClose createa a real Stream
    }

    private LongStream toStream() {
        return LongStream.of(element);
    }

    private OptionalLong toOptional() {
        // if element is null, Optional will throw an NPE 
        // just as the standard Stream implementation does.
        return OptionalLong.of(element);
    }

    private static LongStream empty() {
        return LongStream.empty();
    }

    private static PrimitiveIterator.OfLong singletonIntIterator(final long e) {
        return new PrimitiveIterator.OfLong() {
            private boolean hasNext = true;

            @Override
            public boolean hasNext() {
                return hasNext;
            }

            @Override
            public long nextLong() {
                if (hasNext) {
                    hasNext = false;
                    return e;
                }
                throw new NoSuchElementException();
            }

            @Override
            public Long next() {
                if (TRIPWIRE_ENABLED) {
                    trip(getClass(), "{0} calling SingletonLongStream.singletonIterator.nextInt()");
                }
                return nextLong();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

            @Override
            public void forEachRemaining(LongConsumer action) {
                requireNonNull(action);
                if (hasNext) {
                    action.accept(e);
                    hasNext = false;
                }
            }
        };
    }

    private static Spliterator.OfLong singletonIntSpliterator(final long element) {
        return new Spliterator.OfLong() {
            long estimatedSize = 1;

            @Override
            public Spliterator.OfLong trySplit() {
                return null;
            }

            @Override
            public boolean tryAdvance(LongConsumer consumer) {
                Objects.requireNonNull(consumer);
                if (estimatedSize > 0) {
                    estimatedSize--;
                    consumer.accept(element);
                    return true;
                }
                return false;
            }

            @Override
            public void forEachRemaining(LongConsumer consumer) {
                tryAdvance(consumer);
            }

            @Override
            public long estimateSize() {
                return estimatedSize;
            }

            @Override
            public int characteristics() {
                return Spliterator.NONNULL | Spliterator.SIZED | Spliterator.SUBSIZED | Spliterator.IMMUTABLE
                    | Spliterator.DISTINCT | Spliterator.ORDERED;
            }
        };
    }

    // Java 9 Stream features
    /**
     * Returns, if this stream is ordered, a stream consisting of the longest
     * prefix of elements taken from this stream that match the given predicate.
     *
     * @param predicate - a non-interfering, stateless predicate to apply to
     * elements to determine the longest prefix of elements.
     * @return the new stream
     */
    public LongStream takeWhile(LongPredicate predicate) {
        requireNonNull(predicate);
        if (predicate.test(element)) {
            return this;
        } else {
            return empty();
        }
    }

    /**
     * Returns, if this stream is ordered, a stream consisting of the remaining
     * elements of this stream after dropping the longest prefix of elements
     * that match the given predicate. Otherwise returns, if this stream is
     * unordered, a stream consisting of the remaining elements of this stream
     * after dropping a subset of elements that match the given predicate.
     *
     * @param predicate - a non-interfering, stateless predicate to apply to
     * elements to determine the longest prefix of elements.
     *
     * @return new new stream
     */
    public LongStream dropWhile(LongPredicate predicate) {
        requireNonNull(predicate);
        if (predicate.test(element)) {
            return empty();
        } else {
            return this;
        }
    }
}