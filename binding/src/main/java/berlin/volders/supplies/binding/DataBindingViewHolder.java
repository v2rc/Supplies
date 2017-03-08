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

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * {@link RecyclerView.ViewHolder} wrapping a generic Android Data Binding layout.
 */
public class DataBindingViewHolder extends RecyclerView.ViewHolder {

    private final ViewDataBinding binding;

    private DataBindingViewHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    /**
     * Update a view model identified by {@code varId} with a new object {@code value}.
     *
     * @param varId to update
     * @param value to set
     * @see ViewDataBinding#setVariable(int, Object)
     */
    public void update(int varId, @Nullable Object value) {
        binding.setVariable(varId, value);
        binding.executePendingBindings();
    }

    /**
     * @param layoutRes to inflate
     * @param parent    the layout will be attached to
     * @return a new instance of {@code DataBindingViewHolder} with given layout
     */
    public static DataBindingViewHolder inflate(@LayoutRes int layoutRes,
                                                @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return inflate(inflater, layoutRes, parent);
    }

    /**
     * @param inflater  to use for inflating the layout
     * @param layoutRes to inflate
     * @param parent    the layout will be attached to
     * @return a new instance of {@code DataBindingViewHolder} with given layout
     */
    public static DataBindingViewHolder inflate(@NonNull LayoutInflater inflater,
                                                @LayoutRes int layoutRes,
                                                @NonNull ViewGroup parent) {
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, layoutRes, parent, false);
        return new DataBindingViewHolder(binding);
    }
}
