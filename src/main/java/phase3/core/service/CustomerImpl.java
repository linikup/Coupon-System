package phase3.core.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import phase3.core.entities.Coupon;
import phase3.core.entities.Coupon.Category;
import phase3.core.entities.Customer;
import phase3.core.repositories.CouponRepository;
import phase3.core.repositories.CouponsExceptions;
import phase3.core.repositories.CustomerRepository;


@Service
@Transactional
public class CustomerImpl implements CustomerService, ClientService {

	
	@Autowired
	private CouponRepository couponRep;

	
	@Autowired
	private CustomerRepository customerRep;

	int customerId;

	private LocalDate localDate = LocalDate.now();

	@Override
	public boolean login(String email, String password) throws CouponsExceptions {
		Customer cust = customerRep.findByEmailAndPassword(email, password);
		if(cust == null) {
			throw new CouponsExceptions("customer Login failed");
		}
		int id = cust.getId();
		customerId = id;
		System.out.println(customerId);
		return true;

		
	}

	@Override
	public void purchaseCoupon(Coupon coupon) throws CouponsExceptions {
		Coupon cou = couponRep.findById(coupon.getId()).get();
		

		if (cou.getEndDate().isBefore(localDate)) {
			throw new CouponsExceptions("can not buy this coupon, is expired");

		} if (customerRep.findCouponsByCusIdAndCoup(customerId, cou.getId()) == coupon.getId()) {
			throw new CouponsExceptions("coupon is already purchased");
		}if (cou.getAmount() <= 0) {
			throw new CouponsExceptions("coupon is unavailable");
		}

		Customer c = customerRep.findById(customerId).get();
		List<Coupon> list = c.getCoupons();
		list.add(cou);
		cou.setAmount(cou.getAmount() - 1);
		//couponRep.save(coupon);

	}

	@Override
	public List<Coupon> getCustomerCoupons() throws CouponsExceptions {

		List<Coupon> list = customerRep.findById(customerId).get().getCoupons();
		if (list.isEmpty()) {
			throw new CouponsExceptions("This customer doesnt have coupons");
		} else {

			return list;
		}

	}

	@Override
	public List<Coupon> getCustomerCoupons(Category category) throws CouponsExceptions {
		List<Coupon> list = customerRep.findById(customerId).get().getCoupons();
		List<Coupon> listNew = new ArrayList<Coupon>();

		for (int i = 0; i < list.size(); i++) {

			if (list.get(i).getCategory() == category) {
				listNew.add(list.get(i));
			}

		}

		if (listNew.isEmpty()) {
			throw new CouponsExceptions("there are not coupons with this category");
		} else {
			return listNew;
		}

	}

	@Override
	public List<Coupon> getCustomerCoupons(double maxPrice) throws CouponsExceptions {
		List<Coupon> list = customerRep.findById(customerId).get().getCoupons();
		List<Coupon> listNew = new ArrayList<Coupon>();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getPrice() < maxPrice) {
				listNew.add(list.get(i));
			}

		}

		if (listNew.isEmpty()) {
			throw new CouponsExceptions("There are not coupons under this maxPrice");
		} else {

			return listNew;
		}

	}

	@Override
	public Customer getCustomerDetails() throws CouponsExceptions {
		
		Customer c;
		if(!customerRep.findById(customerId).isPresent()) {
			throw new CouponsExceptions("This costumer doesnt exist");
		}
		c = customerRep.findById(customerId).get();
		System.out.println(c);

		return c;
	}

}
