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

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableByte;
import androidx.databinding.ObservableDouble;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableFloat;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableLong;
import androidx.databinding.ObservableShort;

import org.junit.Test;

import rx.functions.Func1;
import rx.observers.AssertableSubscriber;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ObservableGlueTest {

    @Test
    public void onPropertyChanged() {
        TestObservable<String> observable = new TestObservable<>();
        AssertableSubscriber<String> subscriber =
                ObservableGlue.with(observable, TestObservable.ID, observable).test();

        observable.set("test");

        subscriber.assertNoErrors();
        subscriber.assertNotCompleted();
        subscriber.assertValues(null, "test");
        observable.assertCallbackSet();
    }

    @Test
    public void call() {
        TestObservable<String> observable = new TestObservable<>();
        AssertableSubscriber<String> subscriber =
                ObservableGlue.with(observable, TestObservable.ID, observable).test();

        subscriber.assertNoErrors();
        subscriber.assertNotCompleted();
        subscriber.assertValue(null);
        observable.assertCallbackSet();
    }

    @Test
    public void unsubscribe() {
        TestObservable<String> observable = new TestObservable<>();
        AssertableSubscriber<String> subscriber =
                ObservableGlue.with(observable, TestObservable.ID, observable).test();

        subscriber.unsubscribe();

        subscriber.assertNoErrors();
        subscriber.assertNotCompleted();
        subscriber.assertUnsubscribed();
        observable.assertCallbackUnset();
    }

    @Test
    public void with_ObservableBoolean() {
        ObservableBoolean observable = new ObservableBoolean();
        AssertableSubscriber<Boolean> subscriber =
                ObservableGlue.with(observable).test();

        observable.set(true);

        subscriber.assertNoErrors();
        subscriber.assertNotCompleted();
        subscriber.assertValues(false, true);
    }

    @Test
    public void with_ObservableByte() {
        ObservableByte observable = new ObservableByte();
        AssertableSubscriber<Byte> subscriber =
                ObservableGlue.with(observable).test();

        observable.set((byte) 1);

        subscriber.assertNoErrors();
        subscriber.assertNotCompleted();
        subscriber.assertValues((byte) 0, (byte) 1);
    }

    @Test
    public void with_ObservableDouble() {
        ObservableDouble observable = new ObservableDouble();
        AssertableSubscriber<Double> subscriber =
                ObservableGlue.with(observable).test();

        observable.set(2.0);

        subscriber.assertNoErrors();
        subscriber.assertNotCompleted();
        subscriber.assertValues(0.0, 2.0);
    }

    @Test
    public void with_ObservableField() {
        ObservableField<String> observable = new ObservableField<>();
        AssertableSubscriber<String> subscriber =
                ObservableGlue.with(observable).test();

        observable.set("test");

        subscriber.assertNoErrors();
        subscriber.assertNotCompleted();
        subscriber.assertValues(null, "test");
    }

    @Test
    public void with_ObservableFloat() {
        ObservableFloat observable = new ObservableFloat();
        AssertableSubscriber<Float> subscriber =
                ObservableGlue.with(observable).test();

        observable.set(1.0f);

        subscriber.assertNoErrors();
        subscriber.assertNotCompleted();
        subscriber.assertValues(0.0f, 1.0f);
    }

    @Test
    public void with_ObservableInt() {
        ObservableInt observable = new ObservableInt();
        AssertableSubscriber<Integer> subscriber =
                ObservableGlue.with(observable).test();

        observable.set(1);

        subscriber.assertNoErrors();
        subscriber.assertNotCompleted();
        subscriber.assertValues(0, 1);
    }

    @Test
    public void with_ObservableLong() {
        ObservableLong observable = new ObservableLong();
        AssertableSubscriber<Long> subscriber =
                ObservableGlue.with(observable).test();

        observable.set(1L);

        subscriber.assertNoErrors();
        subscriber.assertNotCompleted();
        subscriber.assertValues(0L, 1L);
    }

    @Test
    public void with_ObservableShort() {
        ObservableShort observable = new ObservableShort();
        AssertableSubscriber<Short> subscriber =
                ObservableGlue.with(observable).test();

        observable.set((short) 1);

        subscriber.assertNoErrors();
        subscriber.assertNotCompleted();
        subscriber.assertValues((short) 0, (short) 1);
    }

    @SuppressWarnings("SameParameterValue")
    static class TestObservable<T> extends BaseObservable implements Func1<TestObservable<T>, T> {

        final static int ID = 1;

        boolean callbackSet;

        T o = null;

        void set(T o) {
            if (o != null && o != this.o && !o.equals(this.o)) {
                this.o = o;
                notifyPropertyChanged(ID);
            }
        }

        T get() {
            return o;
        }

        @Override
        public T call(TestObservable<T> testObservable) {
            return testObservable.get();
        }

        @Override
        public synchronized void addOnPropertyChangedCallback(@NonNull OnPropertyChangedCallback callback) {
            super.addOnPropertyChangedCallback(callback);
            callbackSet = true;
        }

        void assertCallbackSet() {
            assertTrue(callbackSet);
        }

        @Override
        public synchronized void removeOnPropertyChangedCallback(@NonNull OnPropertyChangedCallback callback) {
            super.removeOnPropertyChangedCallback(callback);
            callbackSet = false;
        }

        void assertCallbackUnset() {
            assertFalse(callbackSet);
        }
    }
}
