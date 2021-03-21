package phase3.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import phase3.core.entities.Company;



public interface CompanyRepository extends JpaRepository<Company, Integer> {
	
	public Company findByEmailAndPassword(String email , String password) throws CouponsExceptions;
	
	public Company findByName(String name) throws CouponsExceptions;
	
	@Query(value = "SELECT * FROM COMPANY WHERE ID = ?1", nativeQuery = true)
	public Company findCompId(int companyId);
	
	
	
	

	
	
	
}
