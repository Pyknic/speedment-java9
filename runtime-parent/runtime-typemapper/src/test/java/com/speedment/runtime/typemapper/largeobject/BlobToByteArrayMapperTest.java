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
package com.speedment.runtime.typemapper.largeobject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.util.Random;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class BlobToByteArrayMapperTest {

    private BlobToByteArrayMapper mapper;
    private Random random;

    @Before
    public void createMapper(){
        mapper = new BlobToByteArrayMapper();
        random = new Random(0);
    }

    @Test
    public void toJavaType() throws Exception {
        assertEquals(byte[].class,mapper.getJavaType(null));
    }

    @Test
    public void dbTypeToJavaType() throws Exception {
        byte[] array= new byte[10];
        IntStream.range(0,array.length).forEach(index -> array[index] = nextByte());
        Blob blob = mapper.toDatabaseType(array);
        byte[] actual = mapper.toJavaType(null,null,blob);
        Assert.assertArrayEquals(array,actual);
    }

    @Test
    public void javaTypeToDbType() throws Exception {
        byte[] array= new byte[10];
        Blob blob = new SerialBlob(array);
        byte[] actual = mapper.toJavaType(null,null,blob);
        Assert.assertArrayEquals(array,actual);
    }

    public byte nextByte() {
        switch (random.nextInt(10)) {
            case 0 : return 0;
            case 1 : return 1;
            case 2 : return 2;
            case 3 : return 3;
            case 4 : return 4;
            case 5 : return -1;
            case 6 : return -2;
            case 7 : return Byte.MIN_VALUE;
            case 8 : return Byte.MAX_VALUE;
            default : return (byte) random.nextInt();
        }
    }
}