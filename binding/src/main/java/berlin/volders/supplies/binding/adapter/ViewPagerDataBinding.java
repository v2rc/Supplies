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

package berlin.volders.supplies.binding.adapter;

import android.databinding.BindingAdapter;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.databinding.InverseBindingListener;
import android.databinding.adapters.ListenerUtil;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import berlin.volders.supplies.binding.R;

/**
 * Android Data Binding methods for {@link ViewPager} and related classes.
 */
@BindingMethods(
        @BindingMethod(
                type = TabLayout.class,
                attribute = "viewPager",
                method = "setupWithViewPager")
)
public class ViewPagerDataBinding {

    private ViewPagerDataBinding() {
        throw new AssertionError("No instances!");
    }

    /**
     * Setup 2-way binding for {@link ViewPager#getCurrentItem()} page changes.
     *
     * @param viewPager to listen page changes of
     * @param listener  to notify the binding of page changes
     */
    @BindingAdapter("currentItemAttrChanged")
    public static void setOnPageChangeListener(ViewPager viewPager,
                                               final InverseBindingListener listener) {
        ViewPager.OnPageChangeListener newListener = null;
        if (listener != null) {
            newListener = new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int p, float o, int x) {
                }

                @Override
                public void onPageSelected(int position) {
                    listener.onChange();
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            };
        }
        ViewPager.OnPageChangeListener oldListener
                = ListenerUtil.trackListener(viewPager, newListener, R.id.on_page_change_listener);
        if (oldListener != null) {
            viewPager.removeOnPageChangeListener(oldListener);
        }
        if (newListener != null) {
            viewPager.addOnPageChangeListener(newListener);
        }
    }
}
