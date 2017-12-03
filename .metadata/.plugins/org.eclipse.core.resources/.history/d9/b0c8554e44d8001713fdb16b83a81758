package couponsProject.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import Exceptions.DuplicateNameException;
import Exceptions.NotExistException;
import couponsProject.ConnectionPool;
import couponsProject.Coupon;
import couponsProject.CouponType;
import couponsProject.Customer;
import util.SQLQueryRequest;

public class CustomerDBDAO implements CustomerDAO {

	private CouponDBDAO couponDBDAO;
	private static long custId;

	public long getCustId() {
		return custId;
	}

	public void setCustId(long custId) {
		CustomerDBDAO.custId = custId;
	}

	/**
	 * Creates a new customer and add it to the DB
	 * 
	 * @throws DuplicateNameException
	 */
	@Override
	public void createCustomer(Customer customer) throws ClassNotFoundException, SQLException, DuplicateNameException {
		Connection connection = ConnectionPool.getInstance().getConnection();
		boolean flag = true;
		PreparedStatement statement = connection.prepareStatement(SQLQueryRequest.GET_ALL_CUSTOMER_NAMES);
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			if (customer.getCustName().equals(resultSet.getString(1))) {
				flag = false;
				ConnectionPool.getInstance().returnConnection(connection);
				throw new DuplicateNameException("customer " + customer.getCustName() + " is already exists");
			}
		}
		if (flag) {
			statement = connection.prepareStatement(SQLQueryRequest.ADD_NEW_CUSTOMER_TO_DB);
			statement.setString(1, customer.getCustName());
			statement.setString(2, customer.getPassword());
			statement.executeUpdate();
			statement = connection.prepareStatement(SQLQueryRequest.GET_CUSTOMER_ID_BY_NAME);
			statement.setString(1, customer.getCustName());
			ResultSet resultSetID = statement.executeQuery();
			while (resultSetID.next()) {
				this.setCustId(resultSetID.getLong(1));
			}
			statement = connection.prepareStatement(SQLQueryRequest.ADD_CUSTOMER_TO_CUSTOMER_COUPON_JOIN_TABLE);
			statement.setLong(1, this.getCustId());
			statement.executeUpdate();

			ConnectionPool.getInstance().returnConnection(connection);
			System.out.println("Customer " + customer.getCustName() + " added successfull");
		}

	}

	/**
	 * remove a customer and all purchased by this customer coupons from the DB
	 * 
	 * @throws NotExistException
	 */
	@Override
	public void removeCustomer(Customer customer) throws ClassNotFoundException, SQLException, NotExistException {
		Connection connection = ConnectionPool.getInstance().getConnection();

		PreparedStatement statement = connection.prepareStatement(SQLQueryRequest.GET_ALL_CUSTOMER_ID);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			if (customer.getId() == resultSet.getInt(1)) {

				statement = connection.prepareStatement(SQLQueryRequest.DELETE_CUSTOMER_BY_ID);
				statement.setLong(1, customer.getId());
				statement.executeUpdate();
				statement = connection
						.prepareStatement(SQLQueryRequest.REMOVE_CUSTOMER_FROM_CUSTOMER_COUPON_JOIN_TABLE);
				statement.setLong(1, customer.getId());
				statement.executeUpdate();

				System.out.println("Customer " + customer.getCustName() + " deleted successfull");
			} else
				throw new NotExistException("Customer " + customer.getCustName() + "is not exist");
		}
		ConnectionPool.getInstance().returnConnection(connection);

	}

	/**
	 * Changes a fields of customer with some id to a new fields
	 * 
	 * @throws ClassNotFoundException,
	 *             SQLException
	 */
	@Override
	public void updateCustomer(long id, Customer customer) throws ClassNotFoundException, SQLException {
		Connection connection = ConnectionPool.getInstance().getConnection();

		PreparedStatement statement = connection.prepareStatement(SQLQueryRequest.GET_ALL_CUSTOMER_ID);
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			if (id == resultSet.getInt(1)) {

				statement = connection.prepareStatement(SQLQueryRequest.SET_NEW_CUSTOMER_NAME_BY_ID);
				statement.setString(1, customer.getCustName());
				statement.setLong(2, id);
				statement.executeUpdate();

				statement = connection.prepareStatement(SQLQueryRequest.SET_NEW_CUSTOMER_PASSWORD_BY_ID);
				statement.setString(1, customer.getPassword());
				statement.setLong(2, id);
				statement.executeUpdate();
				System.out.println("Customer updated successfull");
				ConnectionPool.getInstance().returnConnection(connection);

			}
		}

		ConnectionPool.getInstance().returnConnection(connection);
	}

	/**
	 * returns an instance of some customer from a DB by it's id
	 * 
	 * @throws ClassNotFoundException,
	 *             SQLException
	 */
	@Override
	public Customer getCustomer(long id) throws ClassNotFoundException, SQLException {
		Customer customer = new Customer();
		Connection connection = ConnectionPool.getInstance().getConnection();
		PreparedStatement statement;

		statement = connection.prepareStatement(SQLQueryRequest.GET_ALL_CUSTOMER_INFO_BY_ID);
		statement.setLong(1, id);
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			customer.setId(resultSet.getInt(1));
			customer.setCustName(resultSet.getString(2));
			customer.setPassword(resultSet.getString(3));
		}
		ConnectionPool.getInstance().returnConnection(connection);
		return customer;
	}

	/**
	 * returns a collection of all the customers from a DB
	 * 
	 * @throws ClassNotFoundException,
	 *             SQLException
	 */
	@Override
	public Collection<Customer> getAllCustomer() throws ClassNotFoundException, SQLException {
		Connection connection = ConnectionPool.getInstance().getConnection();
		Collection<Customer> collectionCustomer = new HashSet<Customer>();

		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(SQLQueryRequest.GET_ALL_CUSTOMER_ID);
		while (resultSet.next()) {
			collectionCustomer.add(getCustomer(resultSet.getLong(1)));
		}
		ConnectionPool.getInstance().returnConnection(connection);
		return collectionCustomer;
	}

	/**
	 * returns a collection of all coupons, purchased by customer from the DB
	 * 
	 * @throws ClassNotFoundException,
	 *             SQLException, ParseException
	 */
	@Override
	public Collection<Coupon> getCoupons() throws ClassNotFoundException, SQLException, ParseException {
		HashSet<Coupon> purchasedCoupons = new HashSet<>();
		Connection connection = ConnectionPool.getInstance().getConnection();

		PreparedStatement statement = connection
				.prepareStatement(SQLQueryRequest.GET_COUPON_FROM_CUSTOMER_COUPON_JOIN_TABLE_BY_CUSTOMER_ID);
		statement.setLong(1, this.getCustId());
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			purchasedCoupons.add(couponDBDAO.getCoupon(resultSet.getLong(1)));
		}

		return purchasedCoupons;

	}

	/**
	 * returns a collection of only purchased by customer coupons with some type
	 */
	public HashSet<Coupon> getAllPurchasedCouponsByType(Customer customer, CouponType type) {
		HashSet<Coupon> allPurchasedCouponsByType = new HashSet<>();
		HashSet<Coupon> purchasedCoupons = (HashSet<Coupon>) customer.getCoupons();
		Iterator<Coupon> iterator = purchasedCoupons.iterator();
		while (iterator.hasNext()) {
			if (iterator.next().getType().equals(type)) {
				allPurchasedCouponsByType.add(iterator.next());
			}
		}
		return allPurchasedCouponsByType;

	}

	/**
	 * returns all purchased by customer coupons with some special price
	 * 
	 * @param price
	 * @param customer
	 * @return
	 */
	public HashSet<Coupon> getAllPurchasedCouponsByPrice(double price, Customer customer) {
		HashSet<Coupon> allPurchasedCouponsByPrice = new HashSet<>();
		HashSet<Coupon> purchasedCoupons = (HashSet<Coupon>) customer.getCoupons();
		Iterator<Coupon> iterator = purchasedCoupons.iterator();
		while (iterator.hasNext()) {
			if (iterator.next().getPrice() == price) {
				allPurchasedCouponsByPrice.add(iterator.next());
			}
		}
		return allPurchasedCouponsByPrice;
	}

	/**
	 * A customer have to be logged in for managing it's coupons etc. This
	 * method is check if the customer is exist in the DB.
	 * 
	 * @throws ClassNotFoundException,
	 *             SQLException
	 */
	@Override
	public boolean login(String custName, String password) throws ClassNotFoundException, SQLException {
		Connection connection = ConnectionPool.getInstance().getConnection();
		PreparedStatement statement;

		statement = connection.prepareStatement(SQLQueryRequest.GET_CUSTOMER_BY_NAME_AND_PASSWORD);
		statement.setString(1, custName);
		statement.setString(2, password);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			ConnectionPool.getInstance().returnConnection(connection);
			return true;
		}
		ConnectionPool.getInstance().returnConnection(connection);
		return false;
	}

	/**
	 * add's a purchased coupon into Customer-Coupon join table in the DB
	 * 
	 * @throws ClassNotFoundException,
	 *             SQLException, NotExistException
	 */
	@Override
	public void purchaseCoupon(Coupon coupon) throws ClassNotFoundException, SQLException, NotExistException {
		if (coupon.getId() != 0) {
			if (coupon.getAmount() >= 0) {
				Connection connection = ConnectionPool.getInstance().getConnection();
				PreparedStatement statement = null;

				statement = connection.prepareStatement(SQLQueryRequest.SET_COUPON_AMOUNT_BY_ID);
				statement.setInt(1, coupon.getAmount() - 1);
				statement.setLong(2, coupon.getId());
				statement.executeUpdate();
				coupon.setAmount(coupon.getAmount() - 1);
				statement = connection
						.prepareStatement(SQLQueryRequest.ADD_COUPON_TO_CUSTOMER_COUPON_JOIN_TABLE_BY_CUSTOMER_ID);
				statement.setLong(1, this.getCustId());
				statement.setLong(2, coupon.getId());
				statement.executeUpdate();

				ConnectionPool.getInstance().returnConnection(connection);
			} else
				throw new NotExistException("There are no coupons of this company to purchase");
		} else
			throw new NotExistException("This coupon isn't exist");
	}

}
