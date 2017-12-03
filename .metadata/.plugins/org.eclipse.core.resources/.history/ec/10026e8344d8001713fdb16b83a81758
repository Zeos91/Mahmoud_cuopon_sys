package couponsProject.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Collection;
import java.util.HashSet;

import Exceptions.DuplicateNameException;
import Exceptions.NotExistException;
import couponsProject.Company;
import couponsProject.ConnectionPool;
import couponsProject.Coupon;
import util.SQLQueryRequest;

public class CompanyDBDAO implements CompanyDAO {
	CouponDBDAO couponDBDAO = new CouponDBDAO();
	private static long compId = 0;

	public CompanyDBDAO() {

	}

	@SuppressWarnings("static-access")
	public void setCompId(long compId) {
		this.compId = compId;
	}

	public static long getCompId() {
		return compId;
	}

	/**
	 * Creating a new company and adding it to the database
	 * 
	 */
	@Override
	public void createCompany(Company company) throws ClassNotFoundException, SQLException, DuplicateNameException {
		Connection connection = ConnectionPool.getInstance().getConnection();
		boolean flag = true;
		PreparedStatement statement = connection.prepareStatement(SQLQueryRequest.GET_ALL_COMPANY_NAMES);
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			if (company.getCompName().equals(resultSet.getString(1))) {
				flag = false;
				throw new DuplicateNameException("company name " + company.getCompName() + " is already exists");
			}
		}
		statement = connection.prepareStatement(SQLQueryRequest.GET_ALL_COMPANY_EMAILS);
		resultSet = statement.executeQuery();
		while (resultSet.next()) {
			if (company.getEmail().equals(resultSet.getString(1))) {
				flag = false;
				throw new DuplicateNameException("Company with email " + company.getEmail() + " already exists");
			}
		}
		if (flag) {
			statement = connection.prepareStatement(SQLQueryRequest.ADD_NEW_COMPANY_TO_DB);
			statement.setString(1, company.getCompName());
			statement.setString(2, company.getPassword());
			statement.setString(3, company.getEmail());
			statement.executeUpdate();
			statement = connection.prepareStatement(SQLQueryRequest.GET_COMPANY_ID_BY_NAME);
			statement.setString(1, company.getCompName());
			ResultSet id = statement.executeQuery();
			while (id.next()) {
				company.setId(Long.parseLong(id.getString(1)));
			}
			statement = connection.prepareStatement(SQLQueryRequest.ADD_COMPANY_TO_COMPANY_COUPON_JOIN_TABLE);
			statement.setLong(1, company.getId());
			statement.executeUpdate();
		}

		ConnectionPool.getInstance().returnConnection(connection);
	}

	/**
	 * Change a fields of company with some id to new fields.
	 * 
	 * @throws NotExistException
	 * 
	 * 
	 */
	@Override
	public void updateCompany(long id, Company company) throws ClassNotFoundException, SQLException, NotExistException {
		Connection connection = ConnectionPool.getInstance().getConnection();
		boolean flag = false; // variable flag for checking if company we want
								// to change exist
		PreparedStatement statement = connection.prepareStatement(SQLQueryRequest.GET_ALL_COMPANY_ID);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			if (id == resultSet.getInt(1)) {
				flag = true;
				statement = connection.prepareStatement(SQLQueryRequest.SET_NEW_COMPANY_NAME_BY_ID);
				statement.setString(1, company.getCompName());
				statement.setLong(2, id);
				statement.executeUpdate();

				statement = connection.prepareStatement(SQLQueryRequest.SET_NEW_COMPANY_PASSWORD_BY_ID);
				statement.setString(1, company.getPassword());
				statement.setLong(2, id);
				statement.executeUpdate();

				statement = connection.prepareStatement(SQLQueryRequest.SET_NEW_COMPANY_EMAIL_BY_ID);
				statement.setString(1, company.getEmail());
				statement.setLong(2, id);
				statement.executeUpdate();

				System.out.println("Company updated successfull");
				ConnectionPool.getInstance().returnConnection(connection);

			}
		}
		// if the id of company we want to change is not in a database we have
		// to sent some message into exception
		if (!flag) {
			ConnectionPool.getInstance().returnConnection(connection);
			throw new NotExistException("This company is not exist");
		}
	}

	/**
	 * returns a company instance from the database
	 * 
	 * @return Company
	 */
	@Override
	public Company getCompany(long id) throws ClassNotFoundException, SQLException {
		Company company = new Company();
		Connection connection = ConnectionPool.getInstance().getConnection();
		PreparedStatement statement;

		statement = connection.prepareStatement(SQLQueryRequest.GET_COMPANY_BY_ID);
		statement.setLong(1, id);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			company.setId(resultSet.getLong(1));
			company.setCompName(resultSet.getString(2));
			company.setPassword(resultSet.getString(3));
			company.setEmail(resultSet.getString(4));
		}
		ConnectionPool.getInstance().returnConnection(connection);
		return company;
	}

	/**
	 * returns a collection of all the companies from the database
	 * 
	 * @return Collection<Company>
	 */
	@Override
	public Collection<Company> getAllCompanies() throws ClassNotFoundException, SQLException {
		Connection connection = ConnectionPool.getInstance().getConnection();
		Collection<Company> collectionCompany = new HashSet<Company>();
		PreparedStatement statement = connection.prepareStatement(SQLQueryRequest.GET_ALL_COMPANY_ID);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			collectionCompany.add(getCompany(resultSet.getLong(1)));
		}
		ConnectionPool.getInstance().returnConnection(connection);
		return collectionCompany;

	}

	/**
	 * returns a collection of all coupons of some company
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Coupon> getAllCoupons(long id) throws ClassNotFoundException, SQLException {
		HashSet<Coupon> allCompanyCoupons = new HashSet<>();
		Connection connection = ConnectionPool.getInstance().getConnection();

		PreparedStatement statement = connection
				.prepareStatement(SQLQueryRequest.GET_COUPON_FROM_COMPANY_COUPON_JOIN_TABLE_BY_COMPANY_ID);
		statement.setLong(1, id);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			PreparedStatement tmpStatement = connection.prepareStatement(SQLQueryRequest.GET_COUPON_BY_ID);
			tmpStatement.setLong(1, resultSet.getLong(1));
			ResultSet resultSetCoupons = tmpStatement.executeQuery();
			allCompanyCoupons.addAll((Collection<? extends Coupon>) resultSetCoupons);
		}
		return allCompanyCoupons;
	}

	/**
	 * A company have to be logged in for managing it's coupons etc. This method
	 * is check if the company is exist in the DB.
	 * 
	 * 
	 */
	@Override
	public boolean login(String name, String password) throws ClassNotFoundException, SQLException {
		Connection connection = ConnectionPool.getInstance().getConnection();
		PreparedStatement statement;
		statement = connection.prepareStatement(SQLQueryRequest.GET_COMPANY_BY_NAME_AND_PASSWORD);
		statement.setString(1, name);
		statement.setString(2, password);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			compId = resultSet.getLong(1);
			ConnectionPool.getInstance().returnConnection(connection);
			return true;
		}
		ConnectionPool.getInstance().returnConnection(connection);
		return false;

	}

	/**
	 * remove a company and all its coupons from the DB
	 */
	@Override
	public void removeCompany(Company company) throws ClassNotFoundException, SQLException {
		Connection connection = ConnectionPool.getInstance().getConnection();

		PreparedStatement statement = connection.prepareStatement(SQLQueryRequest.GET_ALL_COMPANY_ID);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			if (company.getId() == resultSet.getInt(1)) {

				statement = connection.prepareStatement(SQLQueryRequest.DELETE_COMPANY_BY_ID);
				statement.setLong(1, company.getId());
				statement.executeUpdate();
				statement = connection.prepareStatement(SQLQueryRequest.REMOVE_COMPANY_FROM_COMPANY_COUPON_JOIN_TABLE);
				statement.setLong(1, company.getId());
				statement.executeUpdate();
				ConnectionPool.getInstance().returnConnection(connection);
				System.out.println("Company " + company.getCompName() + " deleted successfull");
			}
		}
		ConnectionPool.getInstance().returnConnection(connection);

	}

	/**
	 * Adding a coupon to the DB and adding coupon's id and company's id to
	 * company-coupon join table
	 * 
	 * @param coupon
	 * @param compID
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ParseException
	 * @throws DuplicateNameException
	 */
	public void createCoupon(Coupon coupon)
			throws ClassNotFoundException, SQLException, ParseException, DuplicateNameException {
		Connection connection = ConnectionPool.getInstance().getConnection();

		couponDBDAO.createCoupon(coupon);
		coupon.setId(couponDBDAO.getCoupId());
		PreparedStatement statement = connection
				.prepareStatement(SQLQueryRequest.ADD_COUPON_AND_COMP_ID_TO_COMPANY_COUPON_JOIN_TABLE);
		statement.setLong(1, compId);
		statement.setLong(2, coupon.getId());
		statement.executeUpdate();
		ConnectionPool.getInstance().returnConnection(connection);

	}
}
