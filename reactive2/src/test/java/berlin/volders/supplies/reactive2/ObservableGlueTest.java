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

import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableByte;
import android.databinding.ObservableDouble;
import android.databinding.ObservableField;
import android.databinding.ObservableFloat;
import android.databinding.ObservableInt;
import android.databinding.ObservableLong;
import android.databinding.ObservableShort;

import org.junit.Test;

import io.reactivex.functions.Function;
import io.reactivex.subscribers.TestSubscriber;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ObservableGlueTest {

    final String nullValue = "nullValue";

    @Test
    public void onPropertyChanged() throws Exception {
        TestObservable<String> observable = new TestObservable<>();
        TestSubscriber<String> subscriber
                = ObservableGlue.with(observable, TestObservable.ID, nullValue, observable).test();

        observable.set("test");

        subscriber.assertNoErrors();
        subscriber.assertNotComplete();
        subscriber.assertValues(nullValue, "test");
        observable.assertCallbackSet();
    }

    @Test
    public void subscribe() throws Exception {
        TestObservable<String> observable = new TestObservable<>();
        TestSubscriber<String> subscriber
                = ObservableGlue.with(observable, TestObservable.ID, nullValue, observable).test();

        subscriber.assertNoErrors();
        subscriber.assertNotComplete();
        subscriber.assertValue(nullValue);
        observable.assertCallbackSet();
    }

    @Test
    public void unsubscribe() throws Exception {
        TestObservable<String> observable = new TestObservable<>();
        TestSubscriber<String> subscriber
                = ObservableGlue.with(observable, TestObservable.ID, nullValue, observable).test();

        subscriber.dispose();

        subscriber.assertNoErrors();
        subscriber.assertNotComplete();
        assertTrue(subscriber.isDisposed());
        observable.assertCallbackUnset();
    }

    @Test
    public void with_ObservableBoolean() throws Exception {
        ObservableBoolean observable = new ObservableBoolean();
        TestSubscriber<Boolean> subscriber
                = ObservableGlue.with(observable).test();

        observable.set(true);

        subscriber.assertNoErrors();
        subscriber.assertNotComplete();
        subscriber.assertValues(false, true);
    }

    @Test
    public void with_ObservableByte() throws Exception {
        ObservableByte observable = new ObservableByte();
        TestSubscriber<Byte> subscriber
                = ObservableGlue.with(observable).test();

        observable.set((byte) 1);

        subscriber.assertNoErrors();
        subscriber.assertNotComplete();
        subscriber.assertValues((byte) 0, (byte) 1);
    }

    @Test
    public void with_ObservableDouble() throws Exception {
        ObservableDouble observable = new ObservableDouble();
        TestSubscriber<Double> subscriber
                = ObservableGlue.with(observable).test();

        observable.set(2.0);

        subscriber.assertNoErrors();
        subscriber.assertNotComplete();
        subscriber.assertValues(0.0, 2.0);
    }

    @Test
    public void with_ObservableField() throws Exception {
        ObservableField<String> observable = new ObservableField<>();
        TestSubscriber<String> subscriber
                = ObservableGlue.with(observable, nullValue).test();

        observable.set("test");

        subscriber.assertNoErrors();
        subscriber.assertNotComplete();
        subscriber.assertValues(nullValue, "test");
    }

    @Test
    public void with_ObservableFloat() throws Exception {
        ObservableFloat observable = new ObservableFloat();
        TestSubscriber<Float> subscriber
                = ObservableGlue.with(observable).test();

        observable.set(1.0f);

        subscriber.assertNoErrors();
        subscriber.assertNotComplete();
        subscriber.assertValues(0.0f, 1.0f);
    }

    @Test
    public void with_ObservableInt() throws Exception {
        ObservableInt observable = new ObservableInt();
        TestSubscriber<Integer> subscriber
                = ObservableGlue.with(observable).test();

        observable.set(1);

        subscriber.assertNoErrors();
        subscriber.assertNotComplete();
        subscriber.assertValues(0, 1);
    }

    @Test
    public void with_ObservableLong() throws Exception {
        ObservableLong observable = new ObservableLong();
        TestSubscriber<Long> subscriber
                = ObservableGlue.with(observable).test();

        observable.set(1L);

        subscriber.assertNoErrors();
        subscriber.assertNotComplete();
        subscriber.assertValues(0L, 1L);
    }

    @Test
    public void with_ObservableShort() throws Exception {
        TestSubscriber<Short> subscriber = TestSubscriber.create();
        ObservableShort observable = new ObservableShort();
        ObservableGlue.with(observable)
                .subscribe(subscriber);

        observable.set((short) 1);

        subscriber.assertNoErrors();
        subscriber.assertNotComplete();
        subscriber.assertValues((short) 0, (short) 1);
    }

    static class TestObservable<T> extends BaseObservable implements Function<TestObservable<T>, T> {

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
        public T apply(TestObservable<T> testObservable) {
            return testObservable.get();
        }

        @Override
        public synchronized void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
            super.addOnPropertyChangedCallback(callback);
            callbackSet = true;
        }

        void assertCallbackSet() {
            assertTrue(callbackSet);
        }

        @Override
        public synchronized void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
            super.removeOnPropertyChangedCallback(callback);
            callbackSet = false;
        }

        void assertCallbackUnset() {
            assertFalse(callbackSet);
        }
    }
}
