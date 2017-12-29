Logging Supplies
================
[![Release][1]][2]
[![Versions][3]][4]

*Logging Supplies* utility library.


Usage
-----

### FirebaseTree

*FirebaseTree* implements a `Timber.Tree` logging messages and errors with a tag
to `FirebaseCrash`.

    implementation 'com.google.firebase:firebase-crash:11.8.+'
    implementation 'com.jakewharton.timber:timber:4.5.+'

### LogToTimber

*LogToTimber* is a simple logger with a method `log(String)` logging to `Timber`.

    implementation 'com.jakewharton.timber:timber:4.5.+'

This is especially useful when implementing logger interfaces only declaring
this single method.

    import com.moczul.ok2curl.logger.Loggable;
    import okhttp3.logging.HttpLoggingInterceptor.Logger;

    public class OkLogger extends LogToTimber implements Logger, Loggable {
        public OkLogger() { super(TAG, DEBUG); }
    }


Installation
------------

Add [JitPack][2] to your repositories and *Logging Supplies* to your
dependencies

    dependencies {
        implementation "com.github.v2rc.supplies:logging:$suppliesVersion"
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


  [1]: https://jitpack.io/v/com.github.v2rc.supplies/logging.svg
  [2]: https://jitpack.io/#com.github.v2rc.supplies/logging
  [3]: https://asapi.herokuapp.com/com.github.v2rc.supplies/logging@svg
  [4]: https://asapi.herokuapp.com/com.github.v2rc.supplies/logging
