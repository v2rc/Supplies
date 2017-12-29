Binding Supplies
================
[![Release][1]][2]
[![Versions][3]][4]

*Binding Supplies* utility library.


Usage
-----

### DataBindingAdapter

*DataBindingAdapter* is a `RecyclerView.Adapter` implementation using `DataBindingViewHolder` to
handle the view inflation and updates. The configuration of the binding happens within the provided
`DataBindingAdapter.Binder` instances.

    implementation "com.android.support:recyclerview-v7:$androidSupportVersion"

### DataBindingPagerAdapter

*DataBindingPagerAdapter* is a `PagerAdapter` implementation instantiating its items as a
`ViewDataBinding`. The configuration of the binding happens within the provided
`DataBindingPagerAdapter.Binder` instances.

    implementation "com.android.support:support-core-ui:$androidSupportVersion"

### @BindingAdapter & @BindingMethods

The `adapter` subpackage contains several binding adapters and methods for the Android Data Binding
library. The usage of each is declared by `{view-type}:{attribute-name}([=]{value-type})` where
`=` indicates two-way binding.

#### ViewPagerDataBinding

 * `TabLayout:viewPager(ViewPager)`
 * `ViewPager:currentItem(=int)`


Installation
------------

Add [JitPack][2] to your repositories and *Binding Supplies* to your
dependencies

    dependencies {
        implementation "com.github.v2rc.supplies:binding:$suppliesVersion"
    }


License
-------

    Copyright (C) 2016-2017  volders GmbH with <3 in Berlin

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


  [1]: https://jitpack.io/v/com.github.v2rc.supplies/binding.svg
  [2]: https://jitpack.io/#com.github.v2rc.supplies/binding
  [3]: https://asapi.herokuapp.com/com.github.v2rc.supplies/binding@svg
  [4]: https://asapi.herokuapp.com/com.github.v2rc.supplies/binding
