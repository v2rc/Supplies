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

/**
 * {@code TypeAdapter} for the AutoValue: Parcel Extension,
 * handling ThreeTen ABP {@link LocalDate} instances.
 */
public class LocalDateParcelAdapter implements TypeAdapter<LocalDate> {

    @Override
    public LocalDate fromParcel(Parcel parcel) {
        return LocalDate.ofEpochDay(parcel.readLong());
    }

    @Override
    public void toParcel(LocalDate localDate, Parcel parcel) {
        parcel.writeLong(localDate.toEpochDay());
    }
}
