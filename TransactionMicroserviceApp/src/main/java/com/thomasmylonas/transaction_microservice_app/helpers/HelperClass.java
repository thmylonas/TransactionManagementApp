package com.thomasmylonas.transaction_microservice_app.helpers;

import java.io.PrintWriter;
import java.io.StringWriter;

public class HelperClass {

    public static String transformStacktraceToString(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }
}
