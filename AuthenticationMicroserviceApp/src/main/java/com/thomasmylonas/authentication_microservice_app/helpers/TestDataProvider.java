package com.thomasmylonas.authentication_microservice_app.helpers;

import com.thomasmylonas.authentication_microservice_app.entities.Transaction;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TestDataProvider {

    public static final int AMOUNT_OF_AUTO_GENERATED_TRANSACTIONS = 5;

    public static final List<Transaction> TRANSACTIONS_LIST = Arrays.asList(
            Transaction.builder()
                    .timestamp(LocalDateTime.now())
                    .type("Type1")
                    .actor("Actor1")
                    .transactionData(new HashMap<>() {{
                        put("Key_1", "Data_1");
                        put("Key_2", "Data_2");
                        put("Key_3", "Data_3");
                    }})
                    .build(),
            Transaction.builder()
                    .timestamp(LocalDateTime.now())
                    .type("Type2")
                    .actor("Actor2")
                    .transactionData(new HashMap<>() {{
                        put("Key_1", "Data_1");
                        put("Key_2", "Data_2");
                        put("Key_3", "Data_4");
                        put("Key_4", "Data_5");
                    }})
                    .build(),
            Transaction.builder()
                    .timestamp(LocalDateTime.now())
                    .type("Type3")
                    .actor("Actor3")
                    .transactionData(new HashMap<>() {{
                        put("Key_1", "Data_2");
                        put("Key_2", "Data_4");
                        put("Key_3", "Data_5");
                        put("Key_4", "Data_6");
                        put("Key_5", "Data_7");
                    }})
                    .build()
    );
}
