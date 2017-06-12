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
package com.speedment.runtime.config.trait;

import com.speedment.runtime.config.Document;

import java.util.Optional;

/**
 * A trait for {@link Document documents} that implement the
 * {@link #getEnumConstants()} method.
 *
 * @author Emil Forslund
 * @since  3.0.10
 */
public interface HasEnumConstants extends Document {

    /**
     * The attribute for the 'enumConstants' field in the JSON Configuration
     * file.
     */
    String ENUM_CONSTANTS = "enumConstants";

    /**
     * Returns a comma separated string of the possible values that this column
     * may have. If the list of potential values are not constrained, an empty
     * optional is returned.
     *
     * @return  list of constant values separated by commas or empty
     */
    default Optional<String> getEnumConstants() {
        return getAsString(ENUM_CONSTANTS);
    }

}
