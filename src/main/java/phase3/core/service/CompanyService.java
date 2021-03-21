package phase3.core.service;

import java.util.List;


import phase3.core.entities.Company;
import phase3.core.entities.Coupon;
import phase3.core.entities.Coupon.Category;
import phase3.core.repositories.CouponsExceptions;

public interface CompanyService extends ClientService {


	/**
	 * This method adds a new coupon to the coupon list
	 * 
	 * @param coupon Coupon
	 * @throws couponExceptions
	 */
	public void addCoupon(Coupon coupon) throws CouponsExceptions;
	
	/**
	 * This method updates a coupon
	 * 
	 * @param coupon Coupon
	 * @throws couponExceptions
	 */
	public void updateCoupon(Coupon coupon) throws CouponsExceptions;
	
	/**
	 * This method finds a coupon by its id and deletes it
	 * 
	 * @param couponId Integer
	 * @throws couponExceptions
	 */
	public void deleteCoupon(int couponId) throws CouponsExceptions;
	
	
	/**
	 * This method returns all coupons of an specific company
	 * 
	 * 
	 * @return list of Coupon
	 * @throws couponExceptions
	 */
	public List<Coupon> getCompaniesCoupons() throws CouponsExceptions;
	
	
	/**
	 * This method returns all coupons that belongs to the same category of an specific company
	 * 
	 * @param category Category
	 * @return list of Coupon
	 * @throws couponExceptions
	 */
	public List<Coupon> getCompaniesCoupons(Category category) throws CouponsExceptions;
	
	/**
	 * This method returns all coupons which the price is less than maxPrice
	 * 
	 * @param maxPrice double
	 * @return list of Coupon
	 * @throws couponExceptions
	 */
	public List<Coupon> getCompaniesCoupons(double maxPrice) throws CouponsExceptions;
	
	
	/**
	 * This method returns the details of an specific company
	 * 
	 *
	 * @return Company
	 * @throws couponExceptions
	 */
	public Company getCompanyDetails() throws CouponsExceptions;

}
