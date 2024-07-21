package com.example.java6_thymeleaf.controller;

import com.example.java6_thymeleaf.model.Customer;
import com.example.java6_thymeleaf.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/view")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;
    List<Customer> customers = new ArrayList<>();

    @ModelAttribute("list")
    public List<Customer> getCustomers() {
        customers = customerRepository.findAll();
        return customers;
    }
    @GetMapping("/list")
    public String list(Model model,@ModelAttribute Customer customer) {
        model.addAttribute("customer", customer);
        return "view/index";
    }
    @PostMapping("/create")
    public String create(Customer customer) {
        customerRepository.save(customer);
        return "redirect:/view/list";
    }
    @PostMapping("/update/{id}")
  public String update(@PathVariable Long id,Customer customer) {
        Customer customer1 = customerRepository.findById(id).orElseThrow();
        customer1.setName(customer.getName());
        customer1.setEmail(customer.getEmail());
        customerRepository.save(customer1);
        return "redirect:/view/list";
    }
    @GetMapping("/details/{id}")
    public String details(@PathVariable Long id, Model model) {
    Customer customer1 = customerRepository.findById(id).orElseThrow();
    model.addAttribute("customer", customer1);
        return "view/index.html";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        customerRepository.deleteById(id);
        return "redirect:/view/list";
    }

}
