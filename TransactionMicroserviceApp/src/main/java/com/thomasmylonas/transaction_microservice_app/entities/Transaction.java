package com.thomasmylonas.transaction_microservice_app.entities;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Entity(name = "Transaction")
@Table(name = "Transactions")
@Getter
@Setter
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
