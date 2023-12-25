package bank.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import bank.entity.Customer;

public  interface CustomerRepository  extends JpaRepository<Customer, Long> {
	
	
 Customer findByUsernameAndPassword(String userName, String Password);
	

}
