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
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LocalDateTimeParcelAdapterTest {

    final long epochDay = 1234567890L;
    final long nanoOfDay = 9876543210L;

    Parcel parcel;

    LocalDateTimeParcelAdapter adapter;

    @Before
    public void setup() {
        adapter = new LocalDateTimeParcelAdapter();
        parcel = Parcel.obtain();
    }

    @Test
    public void fromParcel() throws Exception {
        parcel.writeLong(epochDay);
        parcel.writeLong(nanoOfDay);
        parcel.setDataPosition(0);

        LocalDateTime dateTime = adapter.fromParcel(parcel);

        assertThat(dateTime.toLocalDate().toEpochDay(), is(epochDay));
        assertThat(dateTime.toLocalTime().toNanoOfDay(), is(nanoOfDay));
    }

    @Test
    public void toParcel() throws Exception {
        LocalDate date = LocalDate.ofEpochDay(epochDay);
        LocalTime time = LocalTime.ofNanoOfDay(nanoOfDay);
        adapter.toParcel(LocalDateTime.of(date, time), parcel);
        parcel.setDataPosition(0);

        assertThat(parcel.readLong(), is(epochDay));
        assertThat(parcel.readLong(), is(nanoOfDay));
    }
}
