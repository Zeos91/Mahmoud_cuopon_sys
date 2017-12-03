package couponsProject.DAO;

import java.sql.SQLException;
import java.util.Collection;

import Exceptions.DuplicateNameException;
import Exceptions.NotExistException;
import couponsProject.Company;
import couponsProject.Coupon;

public interface CompanyDAO {
	public void createCompany(Company company) throws ClassNotFoundException, SQLException, DuplicateNameException;

	public void updateCompany(long id, Company company) throws ClassNotFoundException, SQLException, NotExistException;

	public void removeCompany(Company company) throws ClassNotFoundException, SQLException;

	public Company getCompany(long id) throws ClassNotFoundException, SQLException;

	public Collection<Company> getAllCompanies() throws ClassNotFoundException, SQLException;

	public Collection<Coupon> getAllCoupons(long id) throws ClassNotFoundException, SQLException;

	public boolean login(String name, String password) throws ClassNotFoundException, SQLException;
}
