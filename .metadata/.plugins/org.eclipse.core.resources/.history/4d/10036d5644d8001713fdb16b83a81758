package Facades;

import java.io.IOException;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.util.Collection;

import Exceptions.DuplicateNameException;
import Exceptions.NotExistException;
import couponsProject.ClientType;
import couponsProject.ClientTypeFactory;
import couponsProject.Company;
import couponsProject.Customer;
import couponsProject.DAO.CompanyDBDAO;
import couponsProject.DAO.CustomerDBDAO;

public class AdminFacade implements CouponClientFacade {
	private CompanyDBDAO company = new CompanyDBDAO();
	private CustomerDBDAO customer = new CustomerDBDAO();

	public AdminFacade() {
	}

	public void createCompany(Company comp) {
		try {
			company.createCompany(comp);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DuplicateNameException e) {
			e.printStackTrace();
		}
	}

	public void removeCompany(Company comp) {
		try {
			company.removeCompany(comp);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateCompany(long id, Company comp) {
		try {
			try {
				company.updateCompany(id, comp);
			} catch (NotExistException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public Company getCompany(long id) {
		try {
			return company.getCompany(id);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	public Collection<Company> getAllCompanies() {
		try {
			return company.getAllCompanies();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	public void createCustomer(Customer cust) {
		try {
			customer.createCustomer(cust);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (DuplicateNameException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void removeCustomer(Customer cust) {
		try {
			customer.removeCustomer(cust);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NotExistException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateCustomer(long id, Customer cust) {
		try {
			customer.updateCustomer(id, cust);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Customer getCustomer(long id) {
		try {
			return customer.getCustomer(id);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	public Collection<Customer> getAllCustomer() {
		try {
			return customer.getAllCustomer();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public CouponClientFacade login(String name, String password, ClientType client) {
		try {
			return ClientTypeFactory.login(name, password, client);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
