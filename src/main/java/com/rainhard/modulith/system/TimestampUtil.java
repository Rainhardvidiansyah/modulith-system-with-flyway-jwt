package com.rainhard.modulith.system;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimestampUtil {

    public static Instant translateTimestamp(String batchTimestamp){

        if(batchTimestamp.isEmpty()){
            throw new IllegalStateException("Batch timestamp cannot be empty");
        }

        //remove "the batch", get the timestamp
        String numeric = batchTimestamp.substring(batchTimestamp.lastIndexOf("-") + 1);

        long epochMillis = Long.parseLong(numeric);

        Instant instant = Instant.ofEpochMilli(epochMillis);

        ZonedDateTime dateTime = instant.atZone(ZoneId.of("Indonesia"));

        return instant;
    }
}
