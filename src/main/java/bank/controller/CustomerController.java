package bank.controller;

import bank.entity.Account;
import bank.entity.Customer;
import bank.entity.LoginDto;
import bank.entity.Transaction;
import bank.service.CustomerService;
import bank.service.TransactionsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController {

	private final CustomerService customerService;


	
	@Autowired
	private TransactionsService service;
	
	@Autowired
	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@GetMapping
	public String showHome() {
		return "index";
	}

	@GetMapping("/register")
	public String register() {
		return "register";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@PostMapping("/signup")
	public String addCustomer(@ModelAttribute Customer customer, Model model) {
		Account saveCustomer = customerService.saveCustomer(customer);
		if (saveCustomer != null) {
			model.addAttribute("msg", "Account created successfully with ID: " + saveCustomer.getAccountNumber());
		} else {
			model.addAttribute("msg", "Failed to create account. Please try again.");
		}
		return "register";
	}

	@GetMapping("/dashboard")
	public String getDashboard() {
		return "Dashboard";
	}

	@PostMapping("/login")
	public String login(@ModelAttribute LoginDto dto, Model model) {
		String loginResult = customerService.login(dto);
		model.addAttribute("msg", loginResult);
		return "Dashboard"; // Redirect to the dashboard on successful login

	}
	
	@GetMapping("/deposit")
	public String showDeposit() {
		return "deposit";	
	}
	
	@GetMapping("/withdraw")
	public String showWithdrwa() {
		return "withdraw";	
	}
	
    @PostMapping("/deposit")
    public String depositAmount(@RequestParam("accountNo") String accountNo,
                                @RequestParam("amount") Double amount,
                                Model model) {
        try {
            service.deposit(accountNo, amount);
            model.addAttribute("message", "Deposit successful");
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/customers/dashboard"; // Redirect to the dashboard or any appropriate page
    }
    
    @PostMapping("/withdraw")
    public String withdrawAmount(@RequestParam("accountNo") String accountNo,
                                 @RequestParam("amount") Double amount,
                                 Model model) {
        try {
            service.withdraw(accountNo, amount);
            model.addAttribute("message", "Withdrawal successful");
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/customers/dashboard"; // Redirect to the dashboard or any appropriate page
    }

    


    @GetMapping("/transactions")
    public String getAllTransactions(Model model) {
        List<Transaction> transactions = service.getAllTransactions();
        model.addAttribute("transactions", transactions);
        return "transactions";
    }
    
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/customers"; 
    }
}




