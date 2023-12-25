package bank.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import bank.entity.Transaction;

public interface TransactionRepository   extends JpaRepository<Transaction, Long>{

}