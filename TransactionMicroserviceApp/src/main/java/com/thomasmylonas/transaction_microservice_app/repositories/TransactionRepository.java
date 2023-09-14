package com.thomasmylonas.transaction_microservice_app.repositories;

import com.thomasmylonas.transaction_microservice_app.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
