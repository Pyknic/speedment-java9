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
package com.speedment.maven;

import com.speedment.maven.abstractmojo.AbstractReloadMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.ResolutionScope;

/**
 * A maven goal that reloads the JSON configuration file
 * from the database, overwriting all manual changes.
 * 
 * @author  Emil Forslund
 * @since   3.0.0
 */
@Mojo(name = "reload", 
    defaultPhase = LifecyclePhase.GENERATE_SOURCES,
    requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME
)
public class ReloadMojo extends AbstractReloadMojo {
    
    @Override
    protected String launchMessage() {
        return "Starting speedment:reload";
    }
}