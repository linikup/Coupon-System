package phase3.core.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import phase3.core.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	public Customer findByEmailAndPassword(String email, String password) throws CouponsExceptions;

	@Query(value = "SELECT ID FROM CUSTOMER WHERE EMAIL = 'email' AND PASSWORD = 'password'", nativeQuery = true)
	public Integer findCustomerId(String email, String password) throws CouponsExceptions;

	@Query(value = "SELECT COUPON_ID FROM CUSTOMERS_VS_COUPONS WHERE CUSTOMER_ID = ?1 AND COUPON_ID = ?2", nativeQuery = true)
	public Integer findCouponsByCusIdAndCoup(int customerId, int coupId) throws CouponsExceptions;

}
