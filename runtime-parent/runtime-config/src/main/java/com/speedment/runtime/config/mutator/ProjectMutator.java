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
package com.speedment.runtime.config.mutator;


import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.internal.DbmsImpl;
import com.speedment.runtime.config.mutator.trait.HasEnabledMutator;
import com.speedment.runtime.config.mutator.trait.HasNameMutator;

import static com.speedment.runtime.config.Project.*;
import com.speedment.runtime.config.mutator.trait.HasIdMutator;
import static com.speedment.runtime.config.util.DocumentUtil.newDocument;

/**
 *
 * @author       Per Minborg
 * @param <DOC>  document type
 */

public class ProjectMutator<DOC extends Project> extends DocumentMutatorImpl<DOC> implements 
        HasEnabledMutator<DOC>, 
        HasIdMutator<DOC>,    
        HasNameMutator<DOC> {

    public ProjectMutator(DOC project) {
        super(project);
    }
    
    public void setCompanyName(String companyName) {
        put(COMPANY_NAME, companyName);
    }
    
    public void setPackageName(String packageName) {
        put(PACKAGE_NAME, packageName);
    }

    public void setPackageLocation(String packageLocation) {
        put(PACKAGE_LOCATION, packageLocation);
    }

    public void setConfigPath(String configPath) {
        put(CONFIG_PATH, configPath);
    }
    
    public Dbms addNewDbms() {
        return new DbmsImpl(document(), newDocument(document(), DBMSES));
    }
}