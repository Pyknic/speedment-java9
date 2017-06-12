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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.runtime.config.identifier;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Per Minborg
 */
public class TableIdentifierTest {

    @Test
    public void testOf() {
        final String db = "db";
        final String sc = "sc";
        final String ta = "ta";

        TableIdentifier<Integer> ti0 = TableIdentifier.of(db, sc, ta);
        TableIdentifier<Integer> ti1 = TableIdentifier.of(db, sc, ta);
        TableIdentifier<Integer> ti2 = TableIdentifier.of(db, sc, "Arne");

        assertTrue(ti0 == ti1); // Make sure that the interface interns duplicates
        assertFalse(ti0 == ti2);

    }

}
