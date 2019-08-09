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

import java.util.ArrayList;
import java.util.List;

class DataBinders<M, B extends DataBinder<? super M>> {

    private final List<B> binders;

    DataBinders(List<B> binders) {
        if (binders == null || binders.contains(null)) {
            throw new IllegalArgumentException("null binder found");
        }
        if (binders.isEmpty()) {
            throw new IllegalArgumentException("no binder found");
        }
        this.binders = new ArrayList<>(binders);
    }

    B tie(M item) {
        for (B binder : binders) {
            if (binder.handlesItem(item)) {
                return binder;
            }
        }
        throw new IllegalStateException("no binder found");
    }
}
