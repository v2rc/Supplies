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

import android.view.ViewGroup;

import androidx.annotation.CallSuper;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

/**
 * Simple {@link RecyclerView.Adapter} using Android Data Binding with its view holders.
 *
 * @param <M> common Model type this adapter handles
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class DataBindingAdapter<M> extends RecyclerView.Adapter<DataBindingViewHolder> {

    private final DataBinders<M, Binder<? super M>> binders;

    /**
     * Constructor for a {@code DataBindingAdapter} with a single view type to handle.
     *
     * @param bindingId of the binding variable to update
     * @param layoutRes of the binding to inflate
     */
    protected DataBindingAdapter(int bindingId, @LayoutRes int layoutRes) {
        this(new Binder<M>(bindingId, layoutRes) {
            @Override
            protected boolean handlesItem(@Nullable M item) {
                return true;
            }
        });
    }

    /**
     * @param binders list of binders for several view types
     */
    @SafeVarargs
    protected DataBindingAdapter(Binder<? super M>... binders) {
        this(Arrays.asList(binders));
    }

    /**
     * @param binders list of binders for several view types
     */
    protected DataBindingAdapter(List<Binder<? super M>> binders) {
        this.binders = new DataBinders<>(binders);
    }

    /**
     * @param position of the item
     * @return the item at {@code position}
     */
    protected abstract M getItem(int position);

    @Override
    @LayoutRes
    public final int getItemViewType(int position) {
        return binders.tie(getItem(position)).layoutRes;
    }

    @Override
    public final DataBindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return DataBindingViewHolder.inflate(viewType, parent);
    }

    @Override
    @CallSuper
    public void onBindViewHolder(@NonNull DataBindingViewHolder holder, int position) {
        M item = getItem(position);
        holder.update(binders.tie(item).bindingId, item);
    }

    @Override
    public long getItemId(int position) {
        M item = getItem(position);
        return binders.tie(item).getItemId(item);
    }

    /**
     * @param <M> the Model type this binder handles
     */
    public static abstract class Binder<M> extends DataBinder<M> {

        /**
         * @param bindingId of the binding variable to update
         * @param layoutRes of the binding to inflate
         */
        protected Binder(int bindingId, @LayoutRes int layoutRes) {
            super(bindingId, layoutRes);
        }

        /**
         * @param item to get the id for
         * @return the id of the provided {@code item}
         */
        protected long getItemId(@Nullable M item) {
            return item == null ? 0 : item.hashCode();
        }
    }
}
