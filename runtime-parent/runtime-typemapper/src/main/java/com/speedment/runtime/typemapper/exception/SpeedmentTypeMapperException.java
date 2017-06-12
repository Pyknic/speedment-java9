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
package com.speedment.runtime.typemapper.exception;

/**
 * A specialized version of {@code RuntimeException} that is thrown when 
 * something goes wrong mapping between JDBC and other java types.
 * 
 * @author  Emil Forslund
 * @since   3.0.1
 */
public final class SpeedmentTypeMapperException extends RuntimeException {

    private static final long serialVersionUID = -5526279640277861927L;

    public SpeedmentTypeMapperException() {}

    public SpeedmentTypeMapperException(String message) {
        super(message);
    }

    public SpeedmentTypeMapperException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpeedmentTypeMapperException(Throwable cause) {
        super(cause);
    }

    public SpeedmentTypeMapperException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}