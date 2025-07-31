package com.bms.service;

import com.bms.entity.Customer;
import com.bms.entity.Loan;
import com.bms.entity.User;
import com.bms.repository.CustomerRepository;
import com.bms.repository.LoanRepository;
import com.bms.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private HttpSession session;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public int registerCustomer(Customer customer) {
        customerRepository.save(customer);
        User user = new User();
        user.setUsername(customer.getUsername());
        user.setPassword(passwordEncoder.encode(customer.getPassword()));
        user.setRole("USER");
        user.setEnabled(true);
        userRepository.save(user);
        return 1;
    }

    public int updateCustomerDetails(Customer customer) {
        customerRepository.save(customer);
        return 1;
    }

    public int applyLoan(Loan loan) {
        Customer customer = customerRepository.findByUsername((String) session.getAttribute("username"));
        loan.setCustomer(customer);
        loanRepository.save(loan);
        return 1;
    }

    public List<Loan> getLoansByUsername(String username) {
        return loanRepository.findAllByCustomerUsername(username);
    }

}
