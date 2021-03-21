package phase3.core.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import phase3.core.entities.Coupon;



public interface CouponRepository extends JpaRepository<Coupon, Integer> {
	
	@Query(value = "SELECT COMPANY_ID FROM COUPON WHERE ID = id" , nativeQuery = true)
	public int findByCompany_id(int id);
	
	
	@Query(value = "SELECT * FROM COUPON WHERE COMPANY_ID = ?1" , nativeQuery = true)
	public List<Coupon> companyCoupons(int companyId); 
	
	
	
	
	

}
