package phase3.core.service;

import java.util.List;

import phase3.core.entities.Company;
import phase3.core.entities.Customer;
import phase3.core.repositories.CouponsExceptions;

public interface AdminService {

	/**
	 * This method adds a new company to the company list
	 * 
	 * @param company - a company with all the details
	 * @throws couponExceptions
	 */
	public void addCompany(Company company) throws CouponsExceptions;
	
	
	/**
	 * This method updates the email and password of an existing company
	 * 
	 * @param company - Company
	 * @throws couponExceptions
	 */
	public void updateCompany(Company company) throws CouponsExceptions;
	
	
	/**
	 * This method finds a company by its id and deletes it
	 * 
	 * @param companyId - an Integer containing the specified id
	 * @throws couponExceptions
	 */
	public void deleteCompany(int companyId) throws CouponsExceptions;
	

	/**
	 * This method returns all the companies in the list
	 * 
	 * 
	 * @return list of Company
	 * @throws couponExceptions
	 */
	public List<Company> getAllCompanies() throws CouponsExceptions;

	
	/**
	 * This method returns a company by its id
	 * 
	 * @param companyId - an Integer containing the specified id
	 * @return Company
	 * @throws couponExceptions
	 */
	public Company getOneCompany(int companyId) throws CouponsExceptions;
	
	/**
	 * This method adds a new customer to the customer list
	 * 
	 * @param customer Customer
	 * @throws couponExceptions
	 */
	public void addCustomer(Customer customer) throws CouponsExceptions;
	
	/**
	 * This method updates an existing customer
	 * 
	 * @param customer Customer
	 * @throws couponExceptions
	 */
	public void updateCustomer(Customer customer) throws CouponsExceptions;

	/**
	 * This method finds a customer by its id and deletes it
	 * 
	 * @param customerId - an Integer containing the specified id
	 * @throws couponExceptions
	 */
	public void deleteCustomer(int customerId) throws CouponsExceptions;

	/**
	 * This method returns all the customers in the list
	 * 
	 * 
	 * @return list of Customer
	 * @throws couponExceptions
	 */
	public List<Customer> getAllCustomers() throws CouponsExceptions;

	/**
	 * This method return a customer by its id
	 * 
	 * @param customerId - an Integer containing the specified id
	 * 
	 * @return Customer
	 * @throws couponExceptions
	 */
	public Customer getOneCustomer(int customerId) throws CouponsExceptions;

}
