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

package berlin.volders.supplies.reactive;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Single;
import rx.SingleSubscriber;
import rx.functions.Action0;
import rx.subscriptions.Subscriptions;

public class RxOkCall extends Single<Response> {

    private RxOkCall(final Call call) {
        super(new OnSubscribe<Response>() {
            @Override
            public void call(final SingleSubscriber<? super Response> subscriber) {
                subscriber.add(Subscriptions.create(new Action0() {
                    @Override
                    public void call() {
                        call.cancel();
                    }
                }));
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onError(e);
                        }
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onSuccess(response);
                        }
                    }
                });
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
