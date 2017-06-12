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
package com.speedment.runtime.core.internal;

import com.speedment.common.json.Json;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.internal.ProjectImpl;
import com.speedment.runtime.config.trait.HasName;
import com.speedment.runtime.config.util.DocumentTranscoder;
import com.speedment.runtime.core.ApplicationMetadata;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author  Emil Forslund
 * @since   3.0.0
 */
public abstract class AbstractApplicationMetadata implements ApplicationMetadata {

    protected AbstractApplicationMetadata() {}
    
    /**
     * Returns the meta data as a String that shall be used to build up the
     * complete Project meta data. If no metadata exists, returns an
     * empty optional.
     *
     * @return the meta data or empty if none exists for this session
     */
    protected abstract Optional<String> getMetadata();
    
    @Override
    public Project makeProject() {
        return getMetadata().map(json -> DocumentTranscoder.load(json, this::fromJson)).orElseGet(() -> {
            final Map<String, Object> data = new ConcurrentHashMap<>();
            data.put(HasName.NAME, "Project");
            data.put(Project.APP_ID, UUID.randomUUID().toString());
            return new ProjectImpl(data);
        });
    }
    
    private Map<String, Object> fromJson(String json) {
        @SuppressWarnings("unchecked")
        final Map<String, Object> parsed =
            (Map<String, Object>) Json.fromJson(json);
        return parsed;
    }
}