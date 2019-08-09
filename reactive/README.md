Reactive Supplies
=================
[![Release][1]][2]
[![Versions][3]][4]

*Reactive Supplies* utility library.


Usage
-----

Collected Rx1 enclosed utilities.

    implementation 'io.reactivex:rxjava:1.3.+'

### ObservableGlue

*ObservableGlue* provides a simple interface to glue an Android Data Binding
`Observable` field to an `rx.Observable` emitting changes within a reactive
stream.

    ObservableGlue.with(observableField)
                  ....
                  .subscribe(onNext);

This is particularly useful to observe user input.

### RxOkCall

*RxOkCall* provides an Rx wrapper around *OkHttp* exposing an
`rx.Single<Response>` emitting the `okHttp3.Response` or any error
to the reactive stream.

    RxOkCall.from(call)
            ....
            .subscribe(consumeResponse, onError);

<!---->

    provided 'com.squareup.okhttp3:okhttp:3.12.+'


Installation
------------

Add [JitPack][2] to your repositories and *Reactive Supplies* to your
dependencies

    dependencies {
        implementation "com.github.v2rc.supplies:reactive:$suppliesVersion"
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


  [1]: https://jitpack.io/v/com.github.v2rc.supplies/reactive.svg
  [2]: https://jitpack.io/#com.github.v2rc.supplies/reactive
  [3]: https://asapi.herokuapp.com/com.github.v2rc.supplies/reactive@svg
  [4]: https://asapi.herokuapp.com/com.github.v2rc.supplies/reactive
