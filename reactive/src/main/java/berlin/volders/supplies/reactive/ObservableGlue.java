/*
 * Copyright (C) 2016 Christian Schmitz
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

import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableByte;
import android.databinding.ObservableDouble;
import android.databinding.ObservableField;
import android.databinding.ObservableFloat;
import android.databinding.ObservableInt;
import android.databinding.ObservableLong;
import android.databinding.ObservableShort;
import android.support.annotation.NonNull;

import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.subscriptions.Subscriptions;

/**
 * Glues an {@link Observable} and an {@link rx.Observable} together.
 * All changes to the data binding observable will be emitted through the rx observable.
 *
 * @param <T> the type of the data binding {@link Observable}
 * @param <R> the value type being observed
 */
public class ObservableGlue<T extends Observable, R> implements rx.Observable.OnSubscribe<R> {

    final T observable;
    final int propertyId;
    final Func1<T, R> value;

    private ObservableGlue(T observable, int propertyId, Func1<T, R> value) {
        this.observable = observable;
        this.propertyId = propertyId;
        this.value = value;
    }

    @Override
    public void call(Subscriber<? super R> subscriber) {
        subscriber.add(Subscriptions.create(new OnGlue(subscriber)));
    }

    private class OnGlue extends Observable.OnPropertyChangedCallback implements Action0 {

        private final Subscriber<? super R> subscriber;

        OnGlue(Subscriber<? super R> subscriber) {
            this.subscriber = subscriber;
            observable.addOnPropertyChangedCallback(this);
            emit();
        }

        @Override
        public void onPropertyChanged(Observable observable, int propertyId) {
            if (!ObservableGlue.this.observable.equals(observable)) {
                throw new AssertionError("observing wrong observable");
            }
            if (ObservableGlue.this.propertyId == propertyId) {
                emit();
            }
        }

        @Override
        public void call() {
            observable.removeOnPropertyChangedCallback(this);
        }

        private void emit() {
            if (!subscriber.isUnsubscribed()) {
                try {
                    R next = value.call(observable);
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(next);
                    }
                } catch (Exception error) {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onError(error);
                    }
                }
            }
        }
    }

    /**
     * Glues to any {@code observable} and emits its values
     *
     * @param observable to observe
     * @param property   to observe or 0
     * @param value      function to retrieve the actual value from {@code observable}
     * @param <T>        the type of the data binding {@link Observable}
     * @param <R>        the value type being observed
     * @return an {@link rx.Observable} emitting the updates from {@code observable}
     */
    public static <T extends Observable, R> rx.Observable<R>
    with(@NonNull T observable, int property, @NonNull Func1<T, R> value) {
        return rx.Observable.create(new ObservableGlue<>(observable, property, value))
                .onBackpressureLatest();
    }

    /**
     * Glues to a generic {@code ObservableField} and emits its values
     *
     * @param observable to observe
     * @return an {@link rx.Observable} emitting the updates from {@code observable}
     */
    public static <R> rx.Observable<R> with(@NonNull ObservableField<R> observable) {
        return with(observable, 0, new Func1<ObservableField<R>, R>() {
            @Override
            public R call(ObservableField<R> observableField) {
                return observableField.get();
            }
        });
    }

    /**
     * Glues to an {@code ObservableBoolean} and emits its values
     *
     * @param observable to observe
     * @return an {@link rx.Observable} emitting the updates from {@code observable}
     */
    public static rx.Observable<Boolean> with(@NonNull ObservableBoolean observable) {
        return with(observable, 0, new Func1<ObservableBoolean, Boolean>() {
            @Override
            public Boolean call(ObservableBoolean observableBoolean) {
                return observableBoolean.get();
            }
        });
    }

    /**
     * Glues to an {@code ObservableByte} and emits its values
     *
     * @param observable to observe
     * @return an {@link rx.Observable} emitting the updates from {@code observable}
     */
    public static rx.Observable<Byte> with(@NonNull ObservableByte observable) {
        return with(observable, 0, new Func1<ObservableByte, Byte>() {
            @Override
            public Byte call(ObservableByte observableByte) {
                return observableByte.get();
            }
        });
    }

    /**
     * Glues to an {@code ObservableDouble} and emits its values
     *
     * @param observable to observe
     * @return an {@link rx.Observable} emitting the updates from {@code observable}
     */
    public static rx.Observable<Double> with(@NonNull ObservableDouble observable) {
        return with(observable, 0, new Func1<ObservableDouble, Double>() {
            @Override
            public Double call(ObservableDouble observableDouble) {
                return observableDouble.get();
            }
        });
    }

    /**
     * Glues to an {@code ObservableFloat} and emits its values
     *
     * @param observable to observe
     * @return an {@link rx.Observable} emitting the updates from {@code observable}
     */
    public static rx.Observable<Float> with(@NonNull ObservableFloat observable) {
        return with(observable, 0, new Func1<ObservableFloat, Float>() {
            @Override
            public Float call(ObservableFloat observableFloat) {
                return observableFloat.get();
            }
        });
    }

    /**
     * Glues to an {@code Observableint} and emits its values
     *
     * @param observable to observe
     * @return an {@link rx.Observable} emitting the updates from {@code observable}
     */
    public static rx.Observable<Integer> with(@NonNull ObservableInt observable) {
        return with(observable, 0, new Func1<ObservableInt, Integer>() {
            @Override
            public Integer call(ObservableInt observableInt) {
                return observableInt.get();
            }
        });
    }

    /**
     * Glues to an {@code ObservableLong} and emits its values
     *
     * @param observable to observe
     * @return an {@link rx.Observable} emitting the updates from {@code observable}
     */
    public static rx.Observable<Long> with(@NonNull ObservableLong observable) {
        return with(observable, 0, new Func1<ObservableLong, Long>() {
            @Override
            public Long call(ObservableLong observableLong) {
                return observableLong.get();
            }
        });
    }

    /**
     * Glues to an {@code ObservableShort} and emits its values
     *
     * @param observable to observe
     * @return an {@link rx.Observable} emitting the updates from {@code observable}
     */
    public static rx.Observable<Short> with(@NonNull ObservableShort observable) {
        return with(observable, 0, new Func1<ObservableShort, Short>() {
            @Override
            public Short call(ObservableShort observableShort) {
                return observableShort.get();
            }
        });
    }
}
