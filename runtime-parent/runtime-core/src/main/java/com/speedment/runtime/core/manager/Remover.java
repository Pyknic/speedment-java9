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
package com.speedment.runtime.core.manager;

import com.speedment.runtime.core.exception.SpeedmentException;

import java.util.function.Consumer;
import java.util.function.UnaryOperator;

/**
 * An action that takes an entity and removes it from a data store. This 
 * interface extends the standard {@code UnaryOperator}- and 
 * {@code Consumer}-interfaces so that it can be used inside a {@code Stream}.
 * 
 * @param <ENTITY>  the entity type
 * 
 * @author  Per Minborg
 * @since   3.0.1
 */
@FunctionalInterface
public interface Remover<ENTITY> extends UnaryOperator<ENTITY>, Consumer<ENTITY> {

    /**
     * Removes the entity from the data store, returning the same or a different
     * entity with any auto-generated fields updated.
     * 
     * @param entity  the entity to remove
     * @return        the removed entity (same instance or new is not defined)
     * 
     * @throws SpeedmentException  if removing the entity failed
     */
    @Override
    ENTITY apply(ENTITY entity) throws SpeedmentException;

    /**
     * Removes the entity from the data store. The specified instance might be
     * modified by this method in some implementations.
     * 
     * @param entity  the entity to remove
     * 
     * @throws SpeedmentException  if removing the entity failed
     */
    @Override
    default void accept(ENTITY entity) {
        apply(entity);
    }
}