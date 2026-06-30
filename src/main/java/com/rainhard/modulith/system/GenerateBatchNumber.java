package com.rainhard.modulith.system;

import java.time.Instant;

public class GenerateBatchNumber {

    public static String batchNumber() {
        return "BATCH-" + Instant.now().toEpochMilli();
    }
}
