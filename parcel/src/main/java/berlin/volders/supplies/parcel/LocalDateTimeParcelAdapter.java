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

package berlin.volders.supplies.parcel;

import android.os.Parcel;

import com.ryanharter.auto.value.parcel.TypeAdapter;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;

/**
 * {@code TypeAdapter} for the AutoValue: Parcel Extension,
 * handling ThreeTen ABP {@link LocalDateTime} instances.
 */
public class LocalDateTimeParcelAdapter implements TypeAdapter<LocalDateTime> {

    @Override
    public LocalDateTime fromParcel(Parcel parcel) {
        LocalDate date = LocalDate.ofEpochDay(parcel.readLong());
        LocalTime time = LocalTime.ofNanoOfDay(parcel.readLong());
        return LocalDateTime.of(date, time);
    }

    @Override
    public void toParcel(LocalDateTime localDateTime, Parcel parcel) {
        parcel.writeLong(localDateTime.toLocalDate().toEpochDay());
        parcel.writeLong(localDateTime.toLocalTime().toNanoOfDay());
    }
}
