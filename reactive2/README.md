Reactive2 Supplies
==================
[![Release][1]][2]

*Reactive2 Supplies* utility library.


Usage
-----

### ObservableGlue

*ObservableGlue* provides a simple interface to glue an Android Data Binding
`Observable` field to a `Flowable` emitting changes within a reactive stream.

    ObservableGlue.with(observableLong)
                  ....
                  .subscribe(onNext);

This is particularly useful to observe user input.

In contrast to Rx1, `null` values are not supported with Rx2. Therefore
only primitive values are supported directly. For an `ObservableField` it is
necessary to implement a Null Object pattern or wrap the field value with an
`Optional` or similar.

    provided 'io.reactivex.rxjava2:rxjava:2.0.+'


Installation
------------

Add [JitPack][2] to your repositories and *Reactive2 Supplies* to your
dependencies

    dependencies {
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


  [1]: https://jitpack.io/v/berlin.volders.supplies/reactive2.svg
  [2]: https://jitpack.io/#berlin.volders.supplies/reactive2
