package phase3.core.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import phase3.core.entities.Company;
import phase3.core.entities.Customer;
import phase3.core.repositories.CompanyRepository;
import phase3.core.repositories.CouponsExceptions;
import phase3.core.repositories.CustomerRepository;

@Component
@Service
@Transactional
@Scope("prototype")
public class AdminImpl implements AdminService, ClientService {

	@Autowired
	private CustomerRepository customerRep;

	@Autowired
	private CompanyRepository companyRep;

	@Override
	public boolean login(String email, String password) throws CouponsExceptions {
		if (email.equals("admin@admin.com") && (password.equals("admin"))) {
			return true;
		} else {
			throw new CouponsExceptions("admin login failed");
		}
	}

	public void addCompany(Company company) throws CouponsExceptions {
		List<Company> list = new ArrayList<>();

		list = companyRep.findAll();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getName().equals(company.getName())
					|| (list.get(i).getEmail().equals(company.getEmail()))) {
				throw new CouponsExceptions("name or email already exist");
			}

		}
		companyRep.save(company);

	}

	public void updateCompany(Company company) throws CouponsExceptions {
		Company com = companyRep.findByName(company.getName());
		if ((com == null)) {
			throw new CouponsExceptions("This company is not registered");
		}
		if (company.getEmail() == null) {
			throw new CouponsExceptions("email can not be null");
		}
		if (company.getPassword() == null) {
			throw new CouponsExceptions("password can not be null");
		}
		

		com.setPassword(company.getPassword());
		com.setEmail(company.getEmail());
		companyRep.save(com);

	}

	public void deleteCompany(int companyId) throws CouponsExceptions {
		Company com;

		if (!companyRep.findById(companyId).isPresent()) {
			throw new CouponsExceptions(" Unable to delete company, there is not company with that id");

		}
		com = companyRep.findById(companyId).get();
		companyRep.delete(com);

	}

	public List<Company> getAllCompanies() throws CouponsExceptions {
		List<Company> list = companyRep.findAll();
		if (list.size() == 0) {
			throw new CouponsExceptions("there is not companies ");
		}

		return list;
	}

	public Company getOneCompany(int companyId) throws CouponsExceptions {
		Optional<Company> com = companyRep.findById(companyId);
		if (!com.isPresent()) {
			throw new CouponsExceptions("this company id doesn't exist");
		} else {
			return com.get();
		}
	}

	public void addCustomer(Customer customer) throws CouponsExceptions {
		List<Customer> list = new ArrayList<>();

		list = customerRep.findAll();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getEmail().equals(customer.getEmail())) {
				throw new CouponsExceptions("customer's email already exists");
			}

		}
		customerRep.save(customer);

	}

	public void updateCustomer(Customer customer) throws CouponsExceptions {
		Customer cust;

		if ((!customerRep.findById(customer.getId()).isPresent())) {
			throw new CouponsExceptions("you can not update this customer, this customer doesnt exist");
		}
		if (customer.getEmail() == null) {
			throw new CouponsExceptions("email can not be null");
		}
		if (customer.getFirstName() == null) {
			throw new CouponsExceptions("first name can not be null");
		}
		if (customer.getLastName() == null) {
			throw new CouponsExceptions("last name can not be null");
		}
		if (customer.getPassword() == null) {
			throw new CouponsExceptions("last name can not be null");
		}
		
		cust = customerRep.findById(customer.getId()).get();
		cust.setEmail(customer.getEmail());
		cust.setFirstName(customer.getFirstName());
		cust.setLastName(customer.getLastName());
		cust.setPassword(customer.getPassword());
		customerRep.save(cust);

	}

	public void deleteCustomer(int customerId) throws CouponsExceptions {
		Customer cust = customerRep.findById(customerId).get();
		if (cust == null) {
			throw new CouponsExceptions("customer doesnt exist");
		} else {
			customerRep.delete(cust);
		}

	}

	@Override
	public List<Customer> getAllCustomers() throws CouponsExceptions {
	 List<Customer> list = customerRep.findAll();
	 if(list.size() == 0) {
		 throw new CouponsExceptions("the customer list is empty");
	 }
		return list;
	}

	@Override
	public Customer getOneCustomer(int customerId) throws CouponsExceptions {
		Optional<Customer> cust = customerRep.findById(customerId);
		if (!cust.isPresent()) {
			throw new CouponsExceptions("This customer id doesnt exist");
		}

		return cust.get();
	}

}
