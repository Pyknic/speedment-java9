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
package com.speedment.runtime.core.component.connectionpool;



import java.sql.Connection;
import java.sql.SQLException;

/**
 * A connection in a {@link ConnectionPoolComponent} that in addition to 
 * the standard JDBC methods also has information about when it was 
 * created and twhen it expires, among other things.
 * 
 * @author  Per Minborg
 * @since   2.2.0
 */

public interface PoolableConnection extends Connection {

    long getId();
    
    void rawClose() throws SQLException;

    long getCreated();

    long getExpires();

    String getUser();

    char[] getPassword();

    String getUri();

    void onClose();

    void setOnClose(Runnable onClose);

}