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

import rx.functions.Func1;
import rx.observers.TestSubscriber;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ObservableGlueTest {

    @Test
    public void onPropertyChanged() throws Exception {
        TestSubscriber<String> subscriber = TestSubscriber.create();
        TestObservable<String> observable = new TestObservable<>();
        ObservableGlue.with(observable, TestObservable.ID, observable)
                .subscribe(subscriber);

        observable.set("test");

        subscriber.assertNoErrors();
        subscriber.assertNotCompleted();
        subscriber.assertValues(null, "test");
        observable.assertCallbackSet();
    }

    @Test
    public void call() throws Exception {
        TestSubscriber<String> subscriber = TestSubscriber.create();
        TestObservable<String> observable = new TestObservable<>();
        ObservableGlue.with(observable, TestObservable.ID, observable)
                .subscribe(subscriber);

        subscriber.assertNoErrors();
        subscriber.assertNotCompleted();
        subscriber.assertValue(null);
        observable.assertCallbackSet();
    }

    @Test
    public void unsubscribe() throws Exception {
        TestSubscriber<String> subscriber = TestSubscriber.create();
        TestObservable<String> observable = new TestObservable<>();
        ObservableGlue.with(observable, TestObservable.ID, observable)
                .subscribe(subscriber)
                .unsubscribe();

        subscriber.assertNoErrors();
        subscriber.assertNotCompleted();
        subscriber.assertUnsubscribed();
        observable.assertCallbackUnset();
    }

    @Test
    public void with_ObservableBoolean() throws Exception {
        TestSubscriber<Boolean> subscriber = TestSubscriber.create();
        ObservableBoolean observable = new ObservableBoolean();
        ObservableGlue.with(observable)
                .subscribe(subscriber);

        observable.set(true);

        subscriber.assertNoErrors();
        subscriber.assertNotCompleted();
        subscriber.assertValues(false, true);
    }

    @Test
    public void with_ObservableByte() throws Exception {
        TestSubscriber<Byte> subscriber = TestSubscriber.create();
        ObservableByte observable = new ObservableByte();
        ObservableGlue.with(observable)
                .subscribe(subscriber);

        observable.set((byte) 1);

        subscriber.assertNoErrors();
        subscriber.assertNotCompleted();
        subscriber.assertValues((byte) 0, (byte) 1);
    }

    @Test
    public void with_ObservableDouble() throws Exception {
        TestSubscriber<Double> subscriber = TestSubscriber.create();
        ObservableDouble observable = new ObservableDouble();
        ObservableGlue.with(observable)
                .subscribe(subscriber);

        observable.set(2.0);

        subscriber.assertNoErrors();
        subscriber.assertNotCompleted();
        subscriber.assertValues(0.0, 2.0);
    }

    @Test
    public void with_ObservableField() throws Exception {
        TestSubscriber<String> subscriber = TestSubscriber.create();
        ObservableField<String> observable = new ObservableField<>();
        ObservableGlue.with(observable)
                .subscribe(subscriber);

        observable.set("test");

        subscriber.assertNoErrors();
        subscriber.assertNotCompleted();
        subscriber.assertValues(null, "test");
    }

    @Test
    public void with_ObservableFloat() throws Exception {
        TestSubscriber<Float> subscriber = TestSubscriber.create();
        ObservableFloat observable = new ObservableFloat();
        ObservableGlue.with(observable)
                .subscribe(subscriber);

        observable.set(1.0f);

        subscriber.assertNoErrors();
        subscriber.assertNotCompleted();
        subscriber.assertValues(0.0f, 1.0f);
    }

    @Test
    public void with_ObservableInt() throws Exception {
        TestSubscriber<Integer> subscriber = TestSubscriber.create();
        ObservableInt observable = new ObservableInt();
        ObservableGlue.with(observable)
                .subscribe(subscriber);

        observable.set(1);

        subscriber.assertNoErrors();
        subscriber.assertNotCompleted();
        subscriber.assertValues(0, 1);
    }

    @Test
    public void with_ObservableLong() throws Exception {
        TestSubscriber<Long> subscriber = TestSubscriber.create();
        ObservableLong observable = new ObservableLong();
        ObservableGlue.with(observable)
                .subscribe(subscriber);

        observable.set(1L);

        subscriber.assertNoErrors();
        subscriber.assertNotCompleted();
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
        subscriber.assertNotCompleted();
        subscriber.assertValues((short) 0, (short) 1);
    }

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
