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
package com.speedment.runtime.typemapper.other;

import com.speedment.runtime.typemapper.TypeMapper;
import org.junit.Before;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.UUID;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Emil Forslund
 * @since  3.0.10
 */
public class BinaryToUuidMapperTest {

    private TypeMapper<Object, UUID> uuidMapper;

    @Before
    public void setup() {
        uuidMapper = new BinaryToUuidMapper();
    }

    @Test
    public void toJavaTypeHex() throws Exception {
        final byte[] test = {
            0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
            0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17,
        };

        final String expected = bytesToHex(test);
        final String actual = uuidMapper.toJavaType(null, null, test).toString()
            .replace("-", "").toLowerCase();

        assertEquals("Compare as HEX: ", expected, actual);
    }

    @Test
    public void toJavaTypeUUID() throws Exception {
        final ByteBuffer test = ByteBuffer.wrap(new byte[] {
            0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
            0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17,
        });

        final UUID expected = new UUID(test.getLong(), test.getLong());
        final UUID actual   = uuidMapper.toJavaType(null, null, test.array());

        assertEquals("Compare as UUID: ", expected, actual);
    }

    @Test
    public void toDatabaseType() throws Exception {
        final byte[] expected = {
            0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
            0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17,
        };

        final ByteBuffer test = ByteBuffer.wrap(expected);
        final UUID original = new UUID(test.getLong(), test.getLong());

        final Object actual = uuidMapper.toDatabaseType(original);

        assertNotNull("Result not null: ", actual);
        assertEquals("Result correct type: ", byte[].class, actual.getClass());
        assertArrayEquals("Result correct values: ", expected, (byte[]) actual);
    }

    private final static char[] HEX = "0123456789abcdef".toCharArray();
    private static String bytesToHex(byte[] bytes) {
        final char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2]     = HEX[v >>> 4];
            hexChars[j * 2 + 1] = HEX[v & 0x0F];
        }
        return new String(hexChars);
    }
}