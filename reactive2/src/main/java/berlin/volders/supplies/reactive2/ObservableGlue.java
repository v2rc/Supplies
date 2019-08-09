/*
 * Copyright (C) 2017 Christian Schmitz
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

import androidx.annotation.NonNull;
import androidx.databinding.Observable;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableByte;
import androidx.databinding.ObservableDouble;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableFloat;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableLong;
import androidx.databinding.ObservableShort;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.functions.Cancellable;
import io.reactivex.functions.Function;

/**
 * Glues an {@link Observable} and a {@link Flowable} together.
 * All changes to the data binding observable will be emitted through the rx observable.
 *
 * @param <T> the type of the data binding {@link Observable}
 * @param <R> the value type being observed
 */
@SuppressWarnings("WeakerAccess")
public class ObservableGlue<T extends Observable, R> implements FlowableOnSubscribe<R> {

    final T observable;
    final int propertyId;
    final R nullValue;
    final Function<T, R> value;

    private ObservableGlue(T observable, int propertyId, R nullValue, Function<T, R> value) {
        this.observable = observable;
        this.propertyId = propertyId;
        this.nullValue = nullValue;
        this.value = value;
    }

    @Override
    public void subscribe(FlowableEmitter<R> emitter) {
        emitter.setCancellable(new OnGlue(emitter));
    }

    private class OnGlue extends Observable.OnPropertyChangedCallback implements Cancellable {

        private final FlowableEmitter<R> emitter;

        OnGlue(FlowableEmitter<R> emitter) {
            this.emitter = emitter;
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
        public void cancel() {
            observable.removeOnPropertyChangedCallback(this);
        }

        private void emit() {
            if (!emitter.isCancelled()) {
                try {
                    R next = value.apply(observable);
                    if (next == null) {
                        next = nullValue;
                    }
                    if (!emitter.isCancelled()) {
                        emitter.onNext(next);
                    }
                } catch (Exception error) {
                    if (!emitter.isCancelled()) {
                        emitter.onError(error);
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
     * @param nullValue  to emit instead of {@code null}
     * @param value      function to retrieve the actual value from {@code observable}
     * @param <T>        the type of the data binding {@link Observable}
     * @param <R>        the value type being observed
     * @return a {@link Flowable} emitting the updates from {@code observable}
     */
    public static <T extends Observable, R> Flowable<R>
    with(@NonNull T observable, int property, @NonNull R nullValue, @NonNull Function<T, R> value) {
        return Flowable.create(new ObservableGlue<>(observable, property, nullValue, value),
                BackpressureStrategy.LATEST);
    }

    /**
     * Glues to a generic {@code ObservableField} and emits its values
     *
     * @param observable to observe
     * @param nullValue  to emit instead of {@code null}
     * @return a {@link Flowable} emitting the updates from {@code observable}
     */
    public static <R> Flowable<R> with(@NonNull ObservableField<R> observable, @NonNull R nullValue) {
        return with(observable, 0, nullValue, new Function<ObservableField<R>, R>() {
            @Override
            public R apply(ObservableField<R> observableField) {
                return observableField.get();
            }
        });
    }

    /**
     * Glues to an {@code ObservableBoolean} and emits its values or {@code false}
     *
     * @param observable to observe
     * @return a {@link Flowable} emitting the updates from {@code observable}
     */
    public static Flowable<Boolean> with(@NonNull ObservableBoolean observable) {
        return with(observable, 0, false, new Function<ObservableBoolean, Boolean>() {
            @Override
            public Boolean apply(ObservableBoolean observableBoolean) {
                return observableBoolean.get();
            }
        });
    }

    /**
     * Glues to an {@code ObservableByte} and emits its values or {@code 0}
     *
     * @param observable to observe
     * @return a {@link Flowable} emitting the updates from {@code observable}
     */
    public static Flowable<Byte> with(@NonNull ObservableByte observable) {
        return with(observable, 0, (byte) 0, new Function<ObservableByte, Byte>() {
            @Override
            public Byte apply(ObservableByte observableByte) {
                return observableByte.get();
            }
        });
    }

    /**
     * Glues to an {@code ObservableDouble} and emits its values or {@code 0}
     *
     * @param observable to observe
     * @return a {@link Flowable} emitting the updates from {@code observable}
     */
    public static Flowable<Double> with(@NonNull ObservableDouble observable) {
        return with(observable, 0, (double) 0, new Function<ObservableDouble, Double>() {
            @Override
            public Double apply(ObservableDouble observableDouble) {
                return observableDouble.get();
            }
        });
    }

    /**
     * Glues to an {@code ObservableFloat} and emits its values or {@code 0}
     *
     * @param observable to observe
     * @return a {@link Flowable} emitting the updates from {@code observable}
     */
    public static Flowable<Float> with(@NonNull ObservableFloat observable) {
        return with(observable, 0, (float) 0, new Function<ObservableFloat, Float>() {
            @Override
            public Float apply(ObservableFloat observableFloat) {
                return observableFloat.get();
            }
        });
    }

    /**
     * Glues to an {@code Observableint} and emits its values or {@code 0}
     *
     * @param observable to observe
     * @return a {@link Flowable} emitting the updates from {@code observable}
     */
    public static Flowable<Integer> with(@NonNull ObservableInt observable) {
        return with(observable, 0, 0, new Function<ObservableInt, Integer>() {
            @Override
            public Integer apply(ObservableInt observableInt) {
                return observableInt.get();
            }
        });
    }

    /**
     * Glues to an {@code ObservableLong} and emits its values or {@code 0}
     *
     * @param observable to observe
     * @return a {@link Flowable} emitting the updates from {@code observable}
     */
    public static Flowable<Long> with(@NonNull ObservableLong observable) {
        return with(observable, 0, (long) 0, new Function<ObservableLong, Long>() {
            @Override
            public Long apply(ObservableLong observableLong) {
                return observableLong.get();
            }
        });
    }

    /**
     * Glues to an {@code ObservableShort} and emits its values or {@code 0}
     *
     * @param observable to observe
     * @return a {@link Flowable} emitting the updates from {@code observable}
     */
    public static Flowable<Short> with(@NonNull ObservableShort observable) {
        return with(observable, 0, (short) 0, new Function<ObservableShort, Short>() {
            @Override
            public Short apply(ObservableShort observableShort) {
                return observableShort.get();
            }
        });
    }
}
