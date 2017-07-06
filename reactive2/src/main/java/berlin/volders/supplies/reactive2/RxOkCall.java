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

package berlin.volders.supplies.reactive2;

import java.io.IOException;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RxOkCall extends Single<Response> {

    private final Call call;

    private RxOkCall(final Call call) {
        this.call = call;
    }

    @Override
    protected void subscribeActual(@NonNull final SingleObserver<? super Response> observer) {
        observer.onSubscribe(new Disposable() {
            @Override
            public void dispose() {
                call.cancel();
            }

            @Override
            public boolean isDisposed() {
                return call.isCanceled();
            }
        });
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                observer.onError(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                observer.onSuccess(response);
            }
        });
    }

    public static RxOkCall from(Call call) {
        return new RxOkCall(call);
    }

    public static RxOkCall with(Request request) {
        return with(request, new OkHttpClient());
    }

    public static RxOkCall with(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        return with(request);
    }

    public static RxOkCall with(Request request, OkHttpClient client) {
        return new RxOkCall(client.newCall(request));
    }
}
