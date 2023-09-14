package com.thomasmylonas.transaction_microservice_app.helpers;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class HelperClass {

    public static final Random RANDOM = new Random();

    public static String transformStacktraceToString(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }

    public static Map<String, String> generateData(int dataAmount) {

        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < dataAmount; i++) {
            map.put("Key_" + RANDOM.nextInt(1_000_000), "Data_" + RANDOM.nextInt(1_000_000));
        }
        return map;
    }

    public static Timestamp randomTimestamp() {
        long offset = Timestamp.valueOf("2012-01-01 00:00:00").getTime();
        long end = Timestamp.valueOf("2014-01-01 00:00:00").getTime();
        long diff = end - offset + 1;
        return new Timestamp(offset + (long) (Math.random() * diff));
    }
}
