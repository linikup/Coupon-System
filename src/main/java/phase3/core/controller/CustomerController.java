package phase3.core.controller;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import phase3.core.entities.Coupon;
import phase3.core.entities.Coupon.Category;
import phase3.core.entities.Customer;
import phase3.core.repositories.CouponsExceptions;
import phase3.core.service.CustomerImpl;
import phase3.core.sessions.SessionContext;

@CrossOrigin
@RestController
@RequestMapping("/customer")
public class CustomerController extends ClientController {

	@Autowired
	private CustomerImpl customerImpl;

	@Autowired
	private SessionContext sessionContext;

	@Override
	public ResponseEntity<?> login(String email, String password) {
		try {
			customerImpl.login(email, password);
			int id = customerImpl.getCustomerDetails().getId();
			return ResponseEntity.ok("customer with the ID #" + id + " has logged in");
		} catch (CouponsExceptions e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}

	}

	@PostMapping(path = "/purchase-coupon")
	public ResponseEntity<?> purchaseCoupon(@RequestHeader String token, @RequestBody Coupon coupon) {

		try {
			customerImpl.purchaseCoupon(coupon);
			return ResponseEntity.ok("coupon purchased " + coupon);
		} catch (CouponsExceptions e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}

	}

	@GetMapping(path = "/get-customer-coupons")
	public ResponseEntity<?> getCustomerCoupons(@RequestHeader String token) {

		List<Coupon> list;
		try {
			list = customerImpl.getCustomerCoupons();
			return ResponseEntity.ok(list);
		} catch (CouponsExceptions e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}

	}

	@GetMapping(path = "/get-customer-by-category")
	public ResponseEntity<?> getCustomerCoupons(@RequestHeader String token, Category category) {
		List<Coupon> list;
		try {
			list = customerImpl.getCustomerCoupons(category);
			return ResponseEntity.ok(list);
		} catch (CouponsExceptions e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}

	}

	@GetMapping(path = "/get-customer-coupons-maxprice")
	public ResponseEntity<?> getCustomerCoupons(@RequestHeader String token, double maxPrice) {
		List<Coupon> list;
		try {
			list = customerImpl.getCustomerCoupons(maxPrice);
			return ResponseEntity.ok(list);

		} catch (CouponsExceptions e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}

	}

	@GetMapping(path = "/get-customer-details")
	public ResponseEntity<?> getCustomerDetails(@RequestHeader String token){

		Customer cust;
		try {
			cust = customerImpl.getCustomerDetails();
			return ResponseEntity.ok(cust);
		} catch (CouponsExceptions e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("This costumer doesnt exist");
			
		}
		
		
	}

	@PostMapping(path = "/logout")
	public ResponseEntity<?> logout(@RequestHeader String token) {

		sessionContext.logout(token);
		return ResponseEntity.ok("you have logged out successfuly");

	}

}
