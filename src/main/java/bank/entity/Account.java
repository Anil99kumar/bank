package bank.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "accounts")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long accountId; 
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer; 
	private String accountType; 
	private String accountNumber;
	private double balance;
	private double interestRate;
	private LocalDate openDate;
	private String status;
	private boolean overdraftProtection;
}

