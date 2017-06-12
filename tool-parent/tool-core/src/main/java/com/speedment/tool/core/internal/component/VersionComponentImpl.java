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
package com.speedment.tool.core.internal.component;

import com.speedment.common.rest.Rest;
import com.speedment.tool.core.component.VersionComponent;
import com.speedment.tool.core.exception.SpeedmentToolException;

import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * The default implementation of the {@link VersionComponent} interface that 
 * uses the public GitHub API to establish the latest released version.
 * 
 * @author  Emil Forslund
 * @since   3.0.0
 */
public final class VersionComponentImpl implements VersionComponent {

    @Override
    public CompletableFuture<String> latestVersion() {
        return Rest.connectHttps("api.github.com")
            .get("repos/speedment/speedment/releases")
            .thenApplyAsync(res -> {
                if (res.success()) {
                    return res.decodeJsonArray()
                        .map(o -> {
                            @SuppressWarnings("unchecked")
                            final Map<String, Object> map = (Map<String, Object>) o;
                            return map;
                        })
                        .filter(m -> !((Boolean) m.get("draft")))
                        .map(m -> m.get("tag_name"))
                        .map(String.class::cast)
                        .sorted(Comparator.reverseOrder())
                        .findFirst()
                        .orElseThrow(() -> new SpeedmentToolException(
                            "Could not establish the latest version."
                        ));
                } else {
                    throw new SpeedmentToolException(
                        "Received an error '" + res.getText() + 
                        "' from the GitHub API."
                    );
                }
            });
    }
}