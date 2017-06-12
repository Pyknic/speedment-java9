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
package com.speedment.common.tuple.internal.nullable;

import com.speedment.common.tuple.Tuple0;
import com.speedment.common.tuple.TupleOfNullables;
import com.speedment.common.tuple.internal.AbstractTupleOfNullables;
import com.speedment.common.tuple.nullable.Tuple0OfNullables;
import java.util.Optional;

/**
 * An implementation class of a {@link Tuple0OfNullables }
 * 
 * @author Per Minborg
 */
public final class Tuple0OfNullablesImpl 
extends AbstractTupleOfNullables 
implements Tuple0OfNullables {
    
    public final static Tuple0OfNullables EMPTY_TUPLE = new Tuple0OfNullablesImpl();
    
    /**
     * Constructs a {@link TupleOfNullables } of type {@link Tuple0OfNullables
     * }.
     */
    private Tuple0OfNullablesImpl() {
        super(Tuple0OfNullablesImpl.class);
    }
}