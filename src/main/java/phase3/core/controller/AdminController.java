package phase3.core.controller;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import phase3.core.entities.Company;
import phase3.core.entities.Customer;
import phase3.core.repositories.CouponsExceptions;
import phase3.core.service.AdminImpl;
import phase3.core.sessions.SessionContext;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController extends ClientController {

	@Autowired
	private AdminImpl adminImpl;
	

	@Autowired
	private SessionContext sessionContext;

	
	
	public AdminController() {

	}

	@Override
	public ResponseEntity<?> login(String email, String password){

		try {
			adminImpl.login(email, password);
			return ResponseEntity.ok("you are logged in");
		} catch (CouponsExceptions e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
			
		}
	}

	
	@PostMapping(path = "/add-company", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addCompany(@RequestHeader String token, @RequestBody Company company) {

		try {
			adminImpl.addCompany(company);
			return ResponseEntity.ok(company);
		} catch (CouponsExceptions e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());

		}

	}

	@PutMapping(path = "/update-company")
	public ResponseEntity<?> updateCompany(@RequestHeader String token, @RequestBody Company company) {

		try {
			adminImpl.updateCompany(company);
			return ResponseEntity.ok("Company updated" + company);
		} catch (CouponsExceptions e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());

		}

	}

	@DeleteMapping(path = "/delete-company/{companyId}")
	public ResponseEntity<?> deleteCompany(@RequestHeader String token, @PathVariable int companyId) {

		try {
			adminImpl.deleteCompany(companyId);
			return ResponseEntity.ok(companyId);
		} catch (CouponsExceptions e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());

		}

	}

	@GetMapping(path = "/get-all-companies")
	public ResponseEntity<?> getAllCompanies(@RequestHeader String token) {

		try {
			List<Company> list = adminImpl.getAllCompanies();
			return ResponseEntity.ok(list);
		} catch (CouponsExceptions e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

		}
	}

	@GetMapping(path = "/get-one-company/{companyId}")
	public ResponseEntity<?> getOneCompany(@RequestHeader String token, @PathVariable int companyId) {

		try {
			Company company = adminImpl.getOneCompany(companyId);
			return ResponseEntity.ok(company);
		} catch (CouponsExceptions e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());

		}

	}

	@PostMapping(path = "/add-customer")
	public ResponseEntity<?> addCustomer(@RequestHeader String token, @RequestBody Customer customer) {

		try {
			adminImpl.addCustomer(customer);
			return ResponseEntity.ok(customer);
		} catch (CouponsExceptions e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());

		}

	}

	@PutMapping(path = "/update-customer")
	public ResponseEntity<?> updateCustomer(@RequestHeader String token, @RequestBody Customer customer) {

		try {
			adminImpl.updateCustomer(customer);
			return ResponseEntity.ok(customer);
		} catch (CouponsExceptions e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());

		}

	}

	@DeleteMapping(path = "/delete-customer")
	public ResponseEntity<?> deleteCustomer(@RequestHeader String token, int customerId) {
		try {
			Customer cust = adminImpl.getOneCustomer(customerId);
			adminImpl.deleteCustomer(customerId);
			return ResponseEntity.ok(cust);
		} catch (CouponsExceptions e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());

		}

	}

	@GetMapping(path = "/get-all-customer")
	public ResponseEntity<?> getAllCustomers(@RequestHeader String token) {

		try {
			List<Customer> list = adminImpl.getAllCustomers();
			return ResponseEntity.ok(list);
		} catch (CouponsExceptions e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}

	}

	@GetMapping(path = "/get-one-customer/")
	public ResponseEntity<?> getOneCustomer(@RequestHeader String token, int customerId){
		
		try {
			Customer cust = adminImpl.getOneCustomer(customerId);
			return ResponseEntity.ok(cust);
		} catch (CouponsExceptions e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("This customer id doesnt exist");
			
		}
			
		

	}

	@PostMapping(path = "/logout")
	public ResponseEntity<?> logout(@RequestHeader String token) {

		sessionContext.logout(token);
		return ResponseEntity.ok("you have logged out successfuly");

	}

}
