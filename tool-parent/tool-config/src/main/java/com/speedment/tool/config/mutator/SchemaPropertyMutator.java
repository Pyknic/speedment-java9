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
package com.speedment.tool.config.mutator;


import com.speedment.runtime.config.mutator.SchemaMutator;
import com.speedment.tool.config.SchemaProperty;
import com.speedment.tool.config.TableProperty;
import com.speedment.tool.config.mutator.trait.HasAliasPropertyMutator;
import com.speedment.tool.config.mutator.trait.HasEnabledPropertyMutator;
import com.speedment.tool.config.mutator.trait.HasNamePropertyMutator;

/**
 *
 * @author  Emil Forslund
 * @since   2.3.0
 */

public final class SchemaPropertyMutator extends SchemaMutator<SchemaProperty> implements 
        HasEnabledPropertyMutator<SchemaProperty>, 
        HasNamePropertyMutator<SchemaProperty>, 
        HasAliasPropertyMutator<SchemaProperty> {

    SchemaPropertyMutator(SchemaProperty schema) {
        super(schema);
    }

    @Override
    public void setDefaultSchema(Boolean defaultSchema) {
        document().defaultSchemaProperty().setValue(defaultSchema);
    }
    
    @Override
    public TableProperty addNewTable() {
        final TableProperty child = new TableProperty(document());
        document().tablesProperty().add(child);
        return child;
    }
}