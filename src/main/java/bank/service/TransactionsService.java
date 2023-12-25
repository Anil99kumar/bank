package bank.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bank.entity.Account;
import bank.entity.Transaction;
import bank.repo.AccountRepository;
import bank.repo.TransactionRepository;

@Service
public class TransactionsService {

	@Autowired
	private TransactionRepository repository;

	@Autowired
	private AccountRepository accountRepository;

	public void deposit(String accountNo, Double amount) {
		Account account = accountRepository.findByAccountNumber(accountNo);

		if (account != null && amount > 0) {
			Double depositAmount = amount;
			account.setBalance(depositAmount + account.getBalance());
			accountRepository.save(account);

			// Create transaction for deposit
			Transaction transaction = new Transaction();
			transaction.setAccount(account);
			transaction.setAmount(depositAmount);
			transaction.setTransactionType("credit");
			transaction.setTransactionStatus("success");
			transaction.setTransactionDate(LocalDateTime.now());
			transaction.setDescription("Payment credit");

			repository.save(transaction);
		} else {
			throw new IllegalArgumentException("Invalid account or amount");
		}
	}
	
	public void withdraw(String accountNo, Double amount) {
	    Account account = accountRepository.findByAccountNumber(accountNo);

	    if (account != null && amount > 0 && account.getBalance() >= amount) {
	        Double withdrawalAmount = amount;
	        account.setBalance(account.getBalance() - withdrawalAmount);
	        accountRepository.save(account);

	        // Create transaction for withdrawal
	        Transaction transaction = new Transaction();
	        transaction.setAccount(account);
	        transaction.setAmount(withdrawalAmount);
	        transaction.setTransactionType("debit");
	        transaction.setTransactionStatus("success");
	        transaction.setTransactionDate(LocalDateTime.now());
	        transaction.setDescription("Payment debit");

	        repository.save(transaction);
	    } else {
	        throw new IllegalArgumentException("Invalid account, insufficient balance, or invalid amount");
	    }
	    
	    
	}
	
    public List<Transaction> getAllTransactions() {
        return repository.findAll();
    }


}
