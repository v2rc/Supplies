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

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;

import com.google.firebase.crash.FirebaseCrash;

import timber.log.Timber;

/**
 * A simple {@link Timber.Tree} delegate logging to {@link FirebaseCrash}.
 * <p>
 * Logging is only delegated if and only if a given threshold for priority is reached.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public final class FirebaseTree extends Timber.Tree {

    private final int priority;

    /**
     * @param priority threshold to delegate the logging
     */
    public FirebaseTree(int priority) {
        this.priority = priority;
    }

    @Override
    protected final void log(int priority, String tag, @NonNull String message, Throwable error) {
        if (this.priority <= priority) {
            onLog(tag, message, error);
        }
    }

    /**
     * Log non-null {@code tag}, {@code message} and {@code error} to {@link FirebaseCrash}.
     * Override this to suppress logging of different types.
     * <pre>{@code
     *   @Override
     *   protected void onLog(String tag, String message, Throwable error) {
     *     super.onLog(null, null, error);
     *   }
     * }</pre>
     *
     * @param tag     to describe the log
     * @param message to log
     * @param error   to log
     */
    @CallSuper
    protected void onLog(String tag, String message, Throwable error) {
        if (tag != null) {
            FirebaseCrash.log(tag);
        }
        if (message != null) {
            FirebaseCrash.log(message);
        }
        if (error != null) {
            FirebaseCrash.report(error);
        }
    }
}
