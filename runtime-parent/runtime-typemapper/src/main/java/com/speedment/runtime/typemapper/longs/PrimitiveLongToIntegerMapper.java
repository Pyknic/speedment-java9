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
package com.speedment.runtime.typemapper.longs;

import com.speedment.runtime.config.Column;
import com.speedment.runtime.typemapper.TypeMapper;
import java.lang.reflect.Type;

/**
 * Maps between {@code Long} and {@code int} values by simply casting to
 * lower precision.
 * 
 * @author Emil Forslund
 * @since  3.0.2
 */
public final class PrimitiveLongToIntegerMapper implements TypeMapper<Long, Integer> {

    @Override
    public String getLabel() {
        return "Long to int (primitive)";
    }

    @Override
    public Type getJavaType(Column column) {
        return int.class;
    }

    @Override
    public Integer toJavaType(Column column, Class<?> entityType, Long value) {
        return value == null ? null : ((int) (long) value);
    }

    @Override
    public Long toDatabaseType(Integer value) {
        return value == null ? null : ((long) (int) value);
    }
    
    @Override
    public Ordering getOrdering() {
        return Ordering.RETAIN;
    }

}