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
package com.speedment.runtime.core.component.sql;

import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.component.PersistenceComponent;

/**
 * A component responsible for persisting, updating and removing entities in the
 * database. This is a general component for entity management that is invoked
 * by the generated entity managers that Speedment creates.
 * <p>
 * This specialization of the {@link PersistenceComponent}-interface is used for
 * relational SQL databases. If the managers work with other data sources, a
 * different specialization should be used.
 * 
 * @author  Emil Forslund
 * @since   3.0.1
 * 
 * @see PersistenceComponent
 */
public interface SqlPersistenceComponent extends PersistenceComponent {
    
    /**
     * Install the specified table in this component. This method may be used by
     * the implementation to setup the inner storage structure so that requests
     * can be completed with higher efficiency later.
     * <p>
     * Managers that represent a SQL table are required to execute this method
     * as part of their initialization cycle.
     * 
     * @param <ENTITY>         the entity type
     * @param tableIdentifier  identifier for the table to prepare
     */
    <ENTITY> void install(TableIdentifier<ENTITY> tableIdentifier);
    
}