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
package com.speedment.runtime.field;

import java.util.List;
import java.util.function.Function;
import static java.util.stream.Collectors.joining;
import org.junit.Assert;

/**
 *
 * @author Emil Forslund
 * @since  3.0.3
 */
public final class TestUtil {
    
    public static <E> void assertListEqual(String msg, List<E> actual, List<E> expected, Function<E, String> toString) {
        Assert.assertTrue(msg +
            "\n  'actual'   = " + listToString(actual, toString) +
            "\n  'expected' = " + listToString(expected, toString), 
            actual.equals(expected)
        );
    }
    
    private static <E> String listToString(List<E> list, Function<E, String> toString) {
        if (list == null) {
            return "null";
        } else {
            return list.stream()
                .map(toString)
                .collect(joining(",", "[", "]"));
        }
    }
    
    private TestUtil() {}
}