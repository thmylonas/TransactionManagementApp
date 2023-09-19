package com.thomasmylonas.authentication_microservice_app.helpers;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class HelperClass {

    public static final Random RANDOM = new Random();

    public static String stringifyStacktrace(Exception e) {
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

    public static LocalDateTime randomTimestamp() {

        LocalDate localDate = LocalDate.now()
                .minus(Period.ofDays((RANDOM.nextInt(2500))));
        LocalTime localTime = LocalTime.of(RANDOM.nextInt(23), RANDOM.nextInt(59), RANDOM.nextInt(59));
        return LocalDateTime.of(localDate, localTime);
    }
}
