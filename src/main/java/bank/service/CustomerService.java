package bank.service;

import bank.entity.Account;
import bank.entity.Customer;
import bank.entity.LoginDto;
import bank.repo.AccountRepository;
import bank.repo.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class CustomerService {

	private final CustomerRepository customerRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	public CustomerService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}

	public Optional<Customer> getCustomerById(Long id) {
		return customerRepository.findById(id);
	}

	public Account saveCustomer(Customer customer) {

		Customer save = customerRepository.save(customer);

		// Create an account for the customer
		Account account = new Account();

		account.setAccountType("SAVING");
		account.setBalance(0);
		account.setInterestRate(7.5);
		account.setStatus("active");
		account.setOpenDate(LocalDate.now());
		account.setOverdraftProtection(false);

		String accountNumber = generateRandomAccountNumber();
		account.setAccountNumber(accountNumber);
		account.setCustomer(customer);
		return accountRepository.save(account);

	}

	private String generateRandomAccountNumber() {
		Random random = new Random();
		StringBuilder accountNumber = new StringBuilder();

		for (int i = 0; i < 10; i++) {
			accountNumber.append(random.nextInt(10));
		}

		return accountNumber.toString();
	}

	public void deleteCustomerById(Long id) {
		customerRepository.deleteById(id);
	}
	
	 public String login(LoginDto loginDto) {
	        if (loginDto.getPassword()=="admin"&& loginDto.getUsername()=="admin") {
	            return "Login successful. User details: ";
	        } else {
	            return "Invalid username or password.";
	        }
	    }
}
