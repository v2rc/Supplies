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

package berlin.volders.supplies.binding;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

abstract class DataBinder<M> {

    final int bindingId;
    @LayoutRes
    final int layoutRes;

    DataBinder(int bindingId, @LayoutRes int layoutRes) {
        this.bindingId = bindingId;
        this.layoutRes = layoutRes;
    }

    /**
     * @param item to find a binder for
     * @return whether or not this binder handles the {@code item}
     */
    protected abstract boolean handlesItem(@Nullable M item);
}
