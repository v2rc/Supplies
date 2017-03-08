/*
 * Copyright (C) 2016 volders GmbH with <3 in Berlin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package berlin.volders.supplies.logging;

import timber.log.Timber;

/**
 * A simple implementation of a common log interface; logging with {@link Timber}.
 */
public abstract class LogToTimber {

    private final String tag;
    private final int priority;

    /**
     * @param tag      to use with {@code Timber}
     * @param priority to log with
     */
    protected LogToTimber(String tag, int priority) {
        this.tag = tag;
        this.priority = priority;
    }

    /**
     * Logs the {@code message} with Timber using the given {@code tag} and {@code priority}.
     *
     * @param message to log
     * @see Timber#tag(String)
     * @see Timber#log(int, String, Object...)
     */
    public void log(String message) {
        Timber.tag(tag);
        Timber.log(priority, message);
    }
}
