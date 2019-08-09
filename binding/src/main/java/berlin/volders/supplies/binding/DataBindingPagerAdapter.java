/*
 * Copyright (C) 2017 volders GmbH with <3 in Berlin
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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.CallSuper;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager.widget.PagerAdapter;

import java.util.Arrays;
import java.util.List;

/**
 * Simple {@link PagerAdapter} using Android Data Binding to manage page root views.
 *
 * @param <M> common Model type this adapter handles
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class DataBindingPagerAdapter<M> extends PagerAdapter {

    private final DataBinders<M, Binder<? super M>> binders;

    /**
     * Constructor for a {@code DataBindingAdapter} with a single view type to handle.
     *
     * @param bindingId of the binding variable to update
     * @param layoutRes of the binding to inflate
     */
    protected DataBindingPagerAdapter(int bindingId, @LayoutRes int layoutRes) {
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
    protected DataBindingPagerAdapter(Binder<? super M>... binders) {
        this(Arrays.asList(binders));
    }

    /**
     * @param binders list of binders for several view types
     */
    protected DataBindingPagerAdapter(List<Binder<? super M>> binders) {
        this.binders = new DataBinders<>(binders);
    }

    @NonNull
    @Override
    public final ViewDataBinding instantiateItem(ViewGroup container, int position) {
        M item = getItem(position);
        DataBinder<? super M> binder = binders.tie(item);
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        ViewDataBinding binding = DataBindingUtil
                .inflate(inflater, binder.layoutRes, container, true);
        binding.setVariable(binder.bindingId, item);
        return binding;
    }

    @Override
    @CallSuper
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(((ViewDataBinding) object).getRoot());
    }

    @Override
    public final boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return ((ViewDataBinding) object).getRoot() == view;
    }

    /**
     * @param position of the item
     * @return the item at {@code position}
     */
    protected abstract M getItem(int position);

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
    }
}
