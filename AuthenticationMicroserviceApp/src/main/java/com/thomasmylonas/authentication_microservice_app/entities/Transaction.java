package com.thomasmylonas.authentication_microservice_app.entities;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
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
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    @Column(name = "Type")
    private String type;

    @Column(name = "Actor")
    private String actor;

    @ElementCollection
    @CollectionTable(name = "Transactions_Data",
            joinColumns = {@JoinColumn(name = "Transactions_Data_Id", referencedColumnName = "Id")}
    )
    @MapKeyColumn(name = "Data_Key")
    @Column(name = "Data_Value", length = 15)
    private Map<String, String> transactionData;
}
