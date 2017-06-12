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
package com.speedment.runtime.core.internal.manager.sql;

import com.speedment.runtime.core.component.sql.SqlStreamOptimizer;
import com.speedment.runtime.core.component.sql.SqlStreamOptimizerComponent;
import com.speedment.runtime.core.component.sql.SqlStreamOptimizerInfo;
import com.speedment.runtime.core.component.sql.override.SqlStreamTerminatorComponent;
import com.speedment.runtime.core.db.AsynchronousQueryResult;
import com.speedment.runtime.core.internal.stream.builder.pipeline.DoublePipeline;
import com.speedment.runtime.core.internal.stream.builder.pipeline.IntPipeline;
import com.speedment.runtime.core.internal.stream.builder.pipeline.LongPipeline;
import com.speedment.runtime.core.internal.stream.builder.pipeline.ReferencePipeline;
import com.speedment.runtime.core.internal.stream.builder.streamterminator.StreamTerminator;
import com.speedment.runtime.core.stream.Pipeline;
import com.speedment.runtime.core.util.StreamComposition;
import java.util.Comparator;
import java.util.Iterator;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * A class that will terminate ENTITY streams. ENTITY is the original type.
 *
 * Type T is the final type after stream mappings.
 *
 *
 * @author pemi
 * @param <ENTITY> the entity type of the original stream, e.g. hares.stream()
 * is of type Hare
 *
 */
public final class SqlStreamTerminator<ENTITY> implements StreamTerminator {

    protected final String UNSUPPORTED_BECAUSE_OF_CLOSE_MAY_NOT_BE_CALLED = "This method has been disabled for this Stream type "
        + "because improper use will lead to resources not being freed up. "
        + "We regret any inconvenience caused by this. "
        + "If you want to concatenate two or more stream, please use the " + StreamComposition.class.getName()
        + "#concatAndAutoClose() method instead."
        + "Note: If you want to enable this functionality, please use the .withAllowStreamIteratorAndSpliterator() application builder "
        + "method. Be aware though, you are then responsible for closing the stream implicitly after use, ALWAYS";

    private final SqlStreamTerminatorComponent sqlStreamTerminatorComponent;
    private final SqlStreamOptimizerComponent sqlStreamOptimizerComponent;
    private final SqlStreamOptimizerInfo<ENTITY> info;
    private final AsynchronousQueryResult<ENTITY> asynchronousQueryResult;
    private final boolean allowIteratorAndSpliterator;

    public SqlStreamTerminator(
        final SqlStreamOptimizerInfo<ENTITY> info,
        final AsynchronousQueryResult<ENTITY> asynchronousQueryResult,
        final SqlStreamOptimizerComponent sqlStreamOptimizerComponent,
        final SqlStreamTerminatorComponent sqlStreamTerminatorComponent,
        final boolean allowIteratorAndSpliterator
    ) {
        this.info = requireNonNull(info);
        this.asynchronousQueryResult = requireNonNull(asynchronousQueryResult);
        this.sqlStreamOptimizerComponent = requireNonNull(sqlStreamOptimizerComponent);
        this.sqlStreamTerminatorComponent = requireNonNull(sqlStreamTerminatorComponent);
        this.allowIteratorAndSpliterator = allowIteratorAndSpliterator;
    }

    //Todo: Remove this and split up responsibility
    public AsynchronousQueryResult<ENTITY> getAsynchronousQueryResult() {
        return asynchronousQueryResult;
    }
    
    @Override
    public <P extends Pipeline> P optimize(final P initialPipeline) {
        requireNonNull(initialPipeline);
        final SqlStreamOptimizer<ENTITY> optimizer = sqlStreamOptimizerComponent.get(initialPipeline, info.getDbmsType());
        return optimizer.optimize(initialPipeline, info, asynchronousQueryResult);
    }

    @Override
    public <T> void forEach(ReferencePipeline<T> pipeline, Consumer<? super T> action) {
        sqlStreamTerminatorComponent.<ENTITY>getForEachTerminator().apply(info, this, pipeline, action);
    }

    @Override
    public <T> void forEachOrdered(ReferencePipeline<T> pipeline, Consumer<? super T> action) {
        sqlStreamTerminatorComponent.<ENTITY>getForEachOrderedTerminator().apply(info, this, pipeline, action);
    }

    @Override
    public <T> Object[] toArray(ReferencePipeline<T> pipeline) {
        return sqlStreamTerminatorComponent.<ENTITY>getToArrayTerminator().apply(info, this, pipeline);
    }

    @Override
    public <T, A> A[] toArray(ReferencePipeline<T> pipeline, IntFunction<A[]> generator) {
        return sqlStreamTerminatorComponent.<ENTITY>getToArrayGeneratorTerminator().apply(info, this, pipeline, generator);
    }

    @Override
    public <T> T reduce(ReferencePipeline<T> pipeline, T identity, BinaryOperator<T> accumulator) {
        return sqlStreamTerminatorComponent.<ENTITY>getReduceIdentityTerminator().apply(info, this, pipeline, identity, accumulator);
    }

    @Override
    public <T, U> U reduce(ReferencePipeline<T> pipeline, U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner) {
        return sqlStreamTerminatorComponent.<ENTITY>getReduceIdentityCombinerTerminator().apply(info, this, pipeline, identity, accumulator, combiner);
    }

    @Override
    public <T> Optional<T> reduce(ReferencePipeline<T> pipeline, BinaryOperator<T> accumulator) {
        return sqlStreamTerminatorComponent.<ENTITY>getReduceTerminator().apply(info, this, pipeline, accumulator);
    }

    @Override
    public <T, R> R collect(ReferencePipeline<T> pipeline, Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner) {
        return sqlStreamTerminatorComponent.<ENTITY>getCollectSupplierAccumulatorCombinerTerminator().apply(info, this, pipeline, supplier, accumulator, combiner);
    }

    @Override
    public <T, R, A> R collect(ReferencePipeline<T> pipeline, Collector<? super T, A, R> collector) {
        return sqlStreamTerminatorComponent.<ENTITY>getCollectTerminator().apply(info, this, pipeline, collector);
    }

    @Override
    public <T> Optional<T> min(ReferencePipeline<T> pipeline, Comparator<? super T> comparator) {
        return sqlStreamTerminatorComponent.<ENTITY>getMinTerminator().apply(info, this, pipeline, comparator);
    }

    @Override
    public <T> Optional<T> max(ReferencePipeline<T> pipeline, Comparator<? super T> comparator) {
        return sqlStreamTerminatorComponent.<ENTITY>getMaxTerminator().apply(info, this, pipeline, comparator);
    }

    @Override
    public <T> long count(ReferencePipeline<T> pipeline) {
        return sqlStreamTerminatorComponent.<ENTITY>getCountTerminator().apply(info, this, pipeline);
    }

    @Override
    public <T> boolean anyMatch(ReferencePipeline<T> pipeline, Predicate<? super T> predicate) {
        return sqlStreamTerminatorComponent.<ENTITY>getAnyMatchTerminator().apply(info, this, pipeline, predicate);
    }

    @Override
    public <T> boolean allMatch(ReferencePipeline<T> pipeline, Predicate<? super T> predicate) {
        return sqlStreamTerminatorComponent.<ENTITY>getAllMatchTerminator().apply(info, this, pipeline, predicate);
    }

    @Override
    public <T> boolean noneMatch(ReferencePipeline<T> pipeline, Predicate<? super T> predicate) {
        return sqlStreamTerminatorComponent.<ENTITY>getNoneMatchTerminator().apply(info, this, pipeline, predicate);
    }

    @Override
    public <T> Optional<T> findAny(ReferencePipeline<T> pipeline) {
        return sqlStreamTerminatorComponent.<ENTITY>getFindAnyTerminator().apply(info, this, pipeline);

    }

    @Override
    public <T> Optional<T> findFirst(ReferencePipeline<T> pipeline) {
        return sqlStreamTerminatorComponent.<ENTITY>getFindFirstTerminator().apply(info, this, pipeline);
    }

    @Override
    public <T> Iterator<T> iterator(ReferencePipeline<T> pipeline) {
        if (allowIteratorAndSpliterator) {
            return sqlStreamTerminatorComponent.<ENTITY>getIteratorTerminator().apply(info, this, pipeline);
        }
        throw new UnsupportedOperationException(UNSUPPORTED_BECAUSE_OF_CLOSE_MAY_NOT_BE_CALLED);
    }

    @Override
    public <T> Spliterator<T> spliterator(ReferencePipeline<T> pipeline) {
        if (allowIteratorAndSpliterator) {
            return sqlStreamTerminatorComponent.<ENTITY>getSpliteratorTerminator().apply(info, this, pipeline);
        }
        throw new UnsupportedOperationException(UNSUPPORTED_BECAUSE_OF_CLOSE_MAY_NOT_BE_CALLED);
    }

    ///////////// double
    @Override
    public long count(DoublePipeline pipeline) {
        return sqlStreamTerminatorComponent.<ENTITY>getDoubleCountTerminator().apply(info, this, pipeline);
    }

    // Todo: Introduce delegator
    @Override
    public PrimitiveIterator.OfDouble iterator(DoublePipeline pipeline) {
        if (allowIteratorAndSpliterator) {
            return StreamTerminator.super.iterator(pipeline);
        }
        throw new UnsupportedOperationException(UNSUPPORTED_BECAUSE_OF_CLOSE_MAY_NOT_BE_CALLED);
    }

    // Todo: Introduce delegator
    @Override
    public Spliterator.OfDouble spliterator(DoublePipeline pipeline) {
        if (allowIteratorAndSpliterator) {
            return StreamTerminator.super.spliterator(pipeline);
        }
        throw new UnsupportedOperationException(UNSUPPORTED_BECAUSE_OF_CLOSE_MAY_NOT_BE_CALLED);
    }

    ///////////// int
    @Override
    public long count(IntPipeline pipeline) {
        return sqlStreamTerminatorComponent.<ENTITY>getIntCountTerminator().apply(info, this, pipeline);
    }

    // Todo: Introduce delegator
    @Override
    public PrimitiveIterator.OfInt iterator(IntPipeline pipeline) {
        if (allowIteratorAndSpliterator) {
            return StreamTerminator.super.iterator(pipeline);
        }
        throw new UnsupportedOperationException(UNSUPPORTED_BECAUSE_OF_CLOSE_MAY_NOT_BE_CALLED);
    }

    // Todo: Introduce delegator
    @Override
    public Spliterator.OfInt spliterator(IntPipeline pipeline) {
        if (allowIteratorAndSpliterator) {
            return StreamTerminator.super.spliterator(pipeline);
        }
        throw new UnsupportedOperationException(UNSUPPORTED_BECAUSE_OF_CLOSE_MAY_NOT_BE_CALLED);
    }

    ///////////// long 
    @Override
    public long count(LongPipeline pipeline) {
        return sqlStreamTerminatorComponent.<ENTITY>getLongCountTerminator().apply(info, this, pipeline);
    }

    // Todo: Introduce delegator
    @Override
    public PrimitiveIterator.OfLong iterator(LongPipeline pipeline) {
        if (allowIteratorAndSpliterator) {
            return StreamTerminator.super.iterator(pipeline);
        }
        throw new UnsupportedOperationException(UNSUPPORTED_BECAUSE_OF_CLOSE_MAY_NOT_BE_CALLED);
    }

    // Todo: Introduce delegator
    @Override
    public Spliterator.OfLong spliterator(LongPipeline pipeline) {
        if (allowIteratorAndSpliterator) {
            return StreamTerminator.super.spliterator(pipeline);
        }
        throw new UnsupportedOperationException(UNSUPPORTED_BECAUSE_OF_CLOSE_MAY_NOT_BE_CALLED);
    }

}
