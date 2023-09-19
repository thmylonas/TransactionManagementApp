package com.thomasmylonas.transaction_microservice_app.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Map;

@Entity(name = "Transaction")
@Table(name = "Transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Timestamp")
    private LocalDateTime timestamp;

    @Column(name = "Type")
    private String type;

    @Column(name = "Actor")
    private String actor;

    @ElementCollection
    @CollectionTable(name = "Transactions_Data",
            joinColumns = {@JoinColumn(name = "Transactions_Data_Id", referencedColumnName = "Id")}
    )
    @MapKeyColumn(name = "Data_Key")
    @Column(name = "Data_Value")
    private Map<String, String> transactionData;
}
