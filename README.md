![Icon](icon.png) Supplies
==========================
[![Build][1]][2]
[![Release][3]][4]
[![Versions][7]][8]

*Supplies* is a collection of utility libraries implementing basic behaviors.


Usage
-----

Every single library is unique in itself and provides information about how it
should be used. In general all dependencies are `implementation` dependencies.

Projects depending on the supplies have to make sure to define a `compile`
dependency for all required and used `implementation` dependencies.

 * [*Binding Supplies*](binding) — `DataBindingPagerAdapter`
                                 · `DataBindingAdapter`
                                 · `DataBindingViewHolder`
 * [*Logging Supplies*](logging) — `FirebaseTree`
                                 · `LogToTimber`
 * [*Parcel Supplies*](parcel) — `LocalDateTimeParcelAdapter`
                               · `LocalDateParcelAdapter`
                               · `LocalTimeParcelAdapter`
 * [*Reactive Supplies*](reactive) — `ObservableGlue`
                                   · `RxOkCall`
 * [*Reactive2 Supplies*](reactive2) — `ObservableGlue`
                                     · `RxOkCall`


Installation
------------

Add [JitPack][4] to your repositories and *Supplies* to your dependencies

    dependencies {
        implementation "com.github.v2rc.supplies:all:$suppliesVersion"
    }

Also any of the single libraries can be added directly

    dependencies {
        implementation "com.github.v2rc.supplies:binding:$suppliesVersion"
        implementation "com.github.v2rc.supplies:logging:$suppliesVersion"
        implementation "com.github.v2rc.supplies:parcel:$suppliesVersion"
        implementation "com.github.v2rc.supplies:reactive:$suppliesVersion"
        implementation "com.github.v2rc.supplies:reactive2:$suppliesVersion"
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


  [1]: https://travis-ci.org/v2rc/Supplies.svg?branch=master
  [2]: https://travis-ci.org/v2rc/Supplies
  [3]: https://jitpack.io/v/v2rc/supplies.svg
  [4]: https://jitpack.io/#v2rc/supplies
  [7]: https://asapi.herokuapp.com/com.github.v2rc.supplies/all@svg
  [8]: https://asapi.herokuapp.com/com.github.v2rc.supplies/all
