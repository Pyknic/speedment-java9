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
package com.speedment.tool.core.internal.util;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.stage.Stage;

import static com.speedment.runtime.core.util.StaticClassUtil.instanceNotAllowed;

/**
 *
 * @author Emil Forslund
 * @since  3.0.0
 */
public final class CloseUtil {
    
    public static EventHandler<ActionEvent> newCloseHandler() {
        return event -> {
            final Node source = (Node) event.getSource();
            final Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        };
    }
    
    /**
     * This should never be called.
     */
    private CloseUtil() {
        instanceNotAllowed(getClass());
    }
}
