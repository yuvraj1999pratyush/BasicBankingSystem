package com.mkg;


import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CustomerController {
	public long some_id;
	public long to_id;
	public double money;
	public String curr_time;
	public String result=""; 
	
	@Autowired
    private CustomerRepository customerRepo;

	@GetMapping("/")
	public String home() {
		return "index";
	}
	
	@GetMapping("/users")
	public ModelAndView getAllEmployees() {
		ModelAndView mav = new ModelAndView("all_users");
		mav.addObject("customers", customerRepo.findAll());
		return mav;
	}
	
	
	@GetMapping("/users/{id}")
	public ModelAndView getCustomerById(@PathVariable long id, Model model, Customer customer) {
		ModelAndView mv=new ModelAndView();
		model.addAttribute(new Customer());
		mv.addObject("customers",customerRepo.findById(id));
		mv.setViewName("one_user");		
		some_id=customer.getId();
		return mv;
	}
	
	@PostMapping("/users/{id}")
	public String updateAmount(@ModelAttribute Customer customer,Model model,@PathVariable(value="id") String c_id) {
		model.addAttribute("customer", customer);
		if(customer.getId()<=0 || customer.getId()>10 || customer.getId()==some_id) {
			return "fail";
		}
		updateCustomerContacts(some_id,customer.getId(),customer.getBalance());
		
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		
		to_id=customer.getId();
		money=customer.getBalance();
		curr_time=formatter.format(date);
		
		result="Money transferred from customer_ID  "+ String.valueOf(some_id)+"  to customer_ID  "+(String.valueOf(to_id))+"  Amount is "+(String.valueOf(money))+" at IST "+curr_time+" <<<<<>>>>> "+result;
	    System.out.println(result);
		return "success";
	  }
	
	public void updateCustomerContacts(long id1,long id2, double amount) {
	    Customer customer = customerRepo.findById(id1);
	    Customer customer2 = customerRepo.findById(id2);
	    customer.setBalance(customer.getBalance()-amount);
	    customer2.setBalance(customer2.getBalance()+amount);
	    customerRepo.save(customer);
	    customerRepo.save(customer2);
	}
	
	@GetMapping("/transactions")
	public String showTransactions(Model model) {
		model.addAttribute("first", result);
		
		return "transactions";
	}
	
	
}
