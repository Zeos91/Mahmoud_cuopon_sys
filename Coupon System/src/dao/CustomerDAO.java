package dao;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Collection;
import java.util.HashSet;

import beans.Coupon;
import beans.CouponType;
import beans.Customer;
import database.DuplicateNameException;
import database.NotExistException;

public interface CustomerDAO {
	public void createCustomer(Customer customer) throws ClassNotFoundException, SQLException, DuplicateNameException;

	public void removeCustomer(Customer customer) throws ClassNotFoundException, SQLException, NotExistException;

	public void updateCustomer(long id, Customer customer) throws ClassNotFoundException, SQLException, IOException;

	public Customer getCustomer(long id) throws ClassNotFoundException, SQLException;

	public Collection<Customer> getAllCustomer() throws ClassNotFoundException, SQLException;

	public Collection<Coupon> getCoupons() throws ClassNotFoundException, SQLException, ParseException;

	HashSet<Coupon> getAllPurchasedCouponsByType(Customer customer, CouponType type);

	public boolean login(String custName, String password) throws ClassNotFoundException, SQLException;

	void purchaseCoupon(Coupon coupon) throws ClassNotFoundException, SQLException, NotExistException;
}
