package service;

import model.Customer;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CustomerService {
    public static final Set<Customer> setOfCustomer = new HashSet<Customer>();

    private static CustomerService customerService = null;

    public static CustomerService getInstance() {
        if (customerService == null)
            customerService = new CustomerService();
        return customerService;
    }
    public void addCustomer(String email, String firstName, String lastName) {
        setOfCustomer.add(new Customer(firstName, lastName, email));
    }

    public Customer getCustomer(String customerEmail) {
        Customer customer = null;
        for (Customer c : setOfCustomer) {
            if (c.getEmail().equals(customerEmail))
                customer = c;
        }
        return customer;
    }

    public Collection<Customer> getAllCustomers() {
        return setOfCustomer;
    }

}
