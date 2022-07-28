package com.techleads.app.service;

import com.techleads.app.model.Customer;
import com.techleads.app.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService implements UserDetailsService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<Customer> customers = customerRepository.findByEmail(username);
        if(customers.size()==0){
            throw new UsernameNotFoundException("User details not found for the user "+username);
        }
        return new CustomerSecurity(customers.get(0)) ;
    }
}
