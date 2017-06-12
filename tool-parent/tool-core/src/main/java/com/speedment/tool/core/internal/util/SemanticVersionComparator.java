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

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Specialized String Comparator that evaluated two versions as semantic
 * versions.
 *
 * @author Emil Forslund
 * @since  3.0.10
 */
public final class SemanticVersionComparator implements Comparator<String> {

    private static final Pattern NUMERIC =
        Pattern.compile("^[0-9]+$");

    @Override
    public int compare(String first, String second) {
        if (first == null || second == null) {
            return first == null ? 1 : -1;
        } else {
            final String[] firstWords  = first.split("(\\.|-)");
            final String[] secondWords = second.split("(\\.|-)");
            for (int i = 0; i < Math.min(firstWords.length, secondWords.length); i++) {
                final String fw = firstWords[i];
                final String sw = secondWords[i];

                // If they are completely equal, continue to the next word.
                if (fw.equals(sw)) {
                    continue;
                }

                // Snapshots are considered < than everything.
                if ("SNAPSHOT".equals(fw)) {
                    return -1;
                } else if ("SNAPSHOT".equals(sw)) {
                    return 1;
                }

                // Releases are considered > than everything.
                if ("RELEASE".equals(fw)) {
                    return 1;
                } else if ("RELEASE".equals(sw)) {
                    return -1;
                }

                // Since neither is a release, an early access is > than everything else.
                if ("EA".equals(fw)) {
                    return 1;
                } else if ("EA".equals(sw)) {
                    return -1;
                }

                // Look for any initial non-numeric characters. If present, we
                // should begin by comparing those.
                final Matcher fn = NUMERIC.matcher(fw);
                final Matcher sn = NUMERIC.matcher(sw);

                if (fn.find()) {
                    if (sn.find()) { // Both are numeric
                        final int f = Integer.parseInt(fn.group());
                        final int s = Integer.parseInt(sn.group());
                        final int c = Integer.compare(f, s);
                        if (c != 0) return c;
                    } else { // sw is not numeric. We are not equal.
                        return fw.compareTo(sw);
                    }
                } else {
                    if (sn.find()) { // sw is numeric but fw isn't
                        return fw.compareTo(sw);
                    } else { // Neither is numeric
                        final int c = fw.compareTo(sw);
                        if (c != 0) return c;
                    }
                }

                // Continue to the next word.
            }

            // If first has more words, it is considered greater
            // (1.0 > 1.0-SNAPSHOT)
            if (firstWords.length < secondWords.length) {
                return 1;
            } else if (firstWords.length > secondWords.length) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}
