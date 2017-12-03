package facade;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Collection;

import beans.ClientType;
import beans.ClientTypeFactory;
import beans.Coupon;
import beans.CouponType;
import beans.Customer;
import database.NotExistException;
import dbdao.CustomerDBDAO;



public class CustomerFacade implements CouponClientFacade {
	private CustomerDBDAO customerDBDAO = new CustomerDBDAO();

	public CustomerFacade() {
	 
	}

	public void purchaseCoupon(Coupon coupon)  {
		try {
			customerDBDAO.purchaseCoupon(coupon);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} catch (NotExistException e) {
			e.printStackTrace();
		}
	}

	public Collection<Coupon> getAllPurchasedCoupons() {
		try {
			return customerDBDAO.getCoupons();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Collection<Coupon> getAllPurchasedCouponsByType(Customer customer, CouponType type) {
		return customerDBDAO.getAllPurchasedCouponsByType(customer, type);
	}
	
	public Collection<Coupon> getAllPurchasedCouponsByPrice(double price, Customer customer) {
		return customerDBDAO.getAllPurchasedCouponsByPrice(price, customer);
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
