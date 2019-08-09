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

package berlin.volders.supplies.parcel;

import android.os.Parcel;

import org.junit.Before;
import org.junit.Test;
import org.threeten.bp.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@SuppressWarnings("WeakerAccess")
public class LocalDateParcelAdapterTest {

    final long epochDay = 1234567890L;

    Parcel parcel;

    LocalDateParcelAdapter adapter;

    @Before
    public void setup() {
        adapter = new LocalDateParcelAdapter();
        parcel = Parcel.obtain();
    }

    @Test
    public void fromParcel() {
        parcel.writeLong(epochDay);
        parcel.setDataPosition(0);

        LocalDate date = adapter.fromParcel(parcel);

        assertThat(date.toEpochDay(), is(epochDay));
    }

    @Test
    public void toParcel() {
        adapter.toParcel(LocalDate.ofEpochDay(epochDay), parcel);
        parcel.setDataPosition(0);

        assertThat(parcel.readLong(), is(epochDay));
    }
}
