![Icon](icon.png) Supplies
==========================
[![Build][1]][2]
[![Release][3]][4]

*Supplies* is a collection of utility libraries implementing basic behaviors.


Usage
-----

Every single library is unique in itself and provides information about how it
should be used. In general all dependencies are `provided` dependencies.

Projects depending on the supplies have to make sure to define a `compile`
dependency for all required and used `provided` dependencies.

 * [*Binding Supplies*](binding) — `DataBindingPagerAdapter`
                                 · `DataBindingAdapter`
                                 · `DataBindingViewHolder`
 * [*Logging Supplies*](logging) — `FirebaseTree`
                                 · `LogToTimber`
 * [*Parcel Supplies*](parcel) — `LocalDateTimeParcelAdapter`
                               · `LocalDateParcelAdapter`
                               · `LocalTimeParcelAdapter`
 * [*Reactive Supplies*](reactive) — `ObservableGlue`
 * [*Reactive2 Supplies*](reactive2) — `ObservableGlue`


Installation
------------

Add [JitPack][4] to your repositories and *Supplies* to your dependencies

    dependencies {
        compile "berlin.volders:supplies:$suppliesVersion"
    }

Also any of the single libraries can be added directly

    dependencies {
        compile "berlin.volders.supplies:binding:$suppliesVersion"
        compile "berlin.volders.supplies:logging:$suppliesVersion"
        compile "berlin.volders.supplies:parcel:$suppliesVersion"
        compile "berlin.volders.supplies:reactive:$suppliesVersion"
        compile "berlin.volders.supplies:reactive2:$suppliesVersion"
    }


License
-------

    Copyright (C) 2016 volders GmbH with <3 in Berlin

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


  [1]: https://travis-ci.org/volders/Supplies.svg?branch=master
  [2]: https://travis-ci.org/volders/Supplies
  [3]: https://jitpack.io/v/berlin.volders/supplies.svg
  [4]: https://jitpack.io/#berlin.volders/supplies
