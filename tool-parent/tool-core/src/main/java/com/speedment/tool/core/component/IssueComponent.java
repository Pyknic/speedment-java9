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
package com.speedment.tool.core.component;

import com.speedment.common.injector.annotation.InjectKey;
import com.speedment.tool.core.rule.Issue;
import com.speedment.tool.core.rule.Rule;
import javafx.collections.ObservableList;

/**
 * A component for posting and retrieving {@link Issue} that has been detected
 * during validation of all {@link Rule}
 * 
 * @author Simon Jonasson
 * @since 3.0.0
 */
@InjectKey(IssueComponent.class)
public interface IssueComponent {
    
    /**
     * Allows for posting an {@link Issue} that's been detected during
     * rule validation.
     * 
     * @param issue  the Issue
     */
    void post(Issue issue);
    
    /**
     * Clears the list of all issues.
     */
    void clear();
    
    /**
     * Retrieves an <b>unmodifiable</b> ObservableList of all current issues.
     * 
     * @return  list of all current issues
     */
    ObservableList<Issue> getIssues();
}
