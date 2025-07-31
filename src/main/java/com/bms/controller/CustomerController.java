package com.bms.controller;

import com.bms.entity.Customer;
import com.bms.entity.Loan;
import com.bms.service.CustomerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private HttpSession session;

    @PostMapping("/registercustomer")
    public String registerCustomer(@ModelAttribute("customer") Customer customer, BindingResult result, RedirectAttributes redirectAttributes){

        if (result.hasErrors()) {
            return "registercustomer";
        }
        customerService.registerCustomer(customer);
        redirectAttributes.addFlashAttribute("successMessage", "Register successfully");
        return "redirect:/login";
    }

    @PostMapping(path = "/updateCustomerDetails")
    public String updateCustomerDetails(@ModelAttribute("customer") Customer customer, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "updateCustomer";
        }
        customerService.updateCustomerDetails(customer);
        redirectAttributes.addFlashAttribute("successMessage", "Updated successfully!");
        return "redirect:/home";
    }

    @PostMapping("/applyLoan")
    public String applyLoan(@ModelAttribute("loan") Loan loan, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
            return "applyloan";
        }

        customerService.applyLoan(loan);
        redirectAttributes.addFlashAttribute("successMessage", "Loan successfully applied");
        return "redirect:/home";
    }

    @GetMapping("/viewLoans")
    public String viewLoans(Model model) {
        String username = (String) session.getAttribute("username");
        List<Loan> loans = customerService.getLoansByUsername(username);
        model.addAttribute("loans", loans);
        return "viewLoans";
    }
}
