package com.bms.controller;

import com.bms.entity.Customer;
import com.bms.entity.Loan;
import com.bms.repository.CustomerRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @Autowired
    private HttpSession session;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/")
    public String defaultPage() {
        return "redirect:/login";
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }


    @GetMapping("/registercustomer")
    public String register(Model model) {
        model.addAttribute("customer", new Customer());
        return "registercustomer";
    }


    @GetMapping("/customer/updateCustomerDetails")
    public String showUpdateCustomerDetailsPage(Model model) {
        Customer customer = customerRepository.findByUsername((String) session.getAttribute("username"));
        model.addAttribute("customer", customer);
        return "updatedetails";
    }

    // add endpoint for apply loan page
    @GetMapping("/customer/applyLoan")
    public String applyLoan(Model model) {
        model.addAttribute("loan", new Loan());
        return "applyloan";
    }

}
