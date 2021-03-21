package phase3.core.service;

import java.util.List;

import phase3.core.entities.Coupon;
import phase3.core.entities.Coupon.Category;
import phase3.core.entities.Customer;
import phase3.core.repositories.CouponsExceptions;



public interface CustomerService {
	

	public void purchaseCoupon(Coupon coupon) throws CouponsExceptions;

	public List<Coupon> getCustomerCoupons() throws CouponsExceptions;

	public List<Coupon> getCustomerCoupons(Category category) throws CouponsExceptions;

	public List<Coupon> getCustomerCoupons(double maxPrice) throws CouponsExceptions;

	public Customer getCustomerDetails() throws CouponsExceptions;

}
