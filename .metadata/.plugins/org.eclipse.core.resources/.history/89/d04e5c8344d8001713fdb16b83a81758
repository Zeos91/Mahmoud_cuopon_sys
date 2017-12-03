package couponsProject.DAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.sql.Date;
import java.util.HashSet;

import Exceptions.DuplicateNameException;
import Exceptions.NotExistException;
import couponsProject.ConnectionPool;
import couponsProject.Coupon;
import couponsProject.CouponType;
import util.SQLQueryRequest;
import util.StringDateConvertor;

public class CouponDBDAO implements CouponDAO {

	private static long coupId;

	public void setCoupId(long coupId) {
		CouponDBDAO.coupId = coupId;
	}

	public long getCoupId() {
		return coupId;
	}

	public CouponDBDAO() {

	}

	/**
	 * Adding a coupon to the DB
	 * 
	 * @param Coupon
	 *            coupon
	 * @throws ClassNotFoundException,
	 *             SQLException, ParseException, DuplicateNameException
	 * @see couponsProject.DAO.CouponDAO#createCoupon(couponsProject.Coupon)
	 * @throws ClassNotFoundException,
	 *             SQLException, ParseException, DuplicateNameException
	 */
	@Override
	public void createCoupon(Coupon coupon)
			throws ClassNotFoundException, SQLException, ParseException, DuplicateNameException {
		Connection connection = ConnectionPool.getInstance().getConnection();
		boolean flag = true;

		PreparedStatement statement = connection.prepareStatement(SQLQueryRequest.GET_ALL_COUPON_TITLES);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			if (coupon.getTitle().equals(resultSet.getString(1))) {
				flag = false;
				ConnectionPool.getInstance().returnConnection(connection);
				throw new DuplicateNameException("Coupon title " + coupon.getTitle() + " is already exists");
			}
		}
		if (flag) {
			statement = connection.prepareStatement(SQLQueryRequest.ADD_NEW_COUPON_TO_DB);
			statement.setString(1, coupon.getTitle());
			statement.setDate(2, StringDateConvertor.convert(coupon.getStartDate().toString()));
			statement.setDate(3, StringDateConvertor.convert(coupon.getEndDate().toString()));
			statement.setInt(4, coupon.getAmount());
			statement.setString(5, coupon.getType().toString());
			statement.setString(6, coupon.getMessage());
			statement.setDouble(7, coupon.getPrice());
			statement.setString(8, coupon.getImage());
			statement.executeUpdate();

			statement = connection.prepareStatement(SQLQueryRequest.GET_COUPON_ID_BY_TITLE);
			statement.setString(1, coupon.getTitle());
			ResultSet thisCouponId = statement.executeQuery();
			while (thisCouponId.next()) {
				this.setCoupId(thisCouponId.getLong(1));
			}

			ConnectionPool.getInstance().returnConnection(connection);
			System.out.println("Coupon added successfull");
		}

	}

	/**
	 * Delete a coupon from the DB
	 * 
	 * @throws ClassNotFoundException,
	 *             SQLException, NotExistException
	 */
	@Override
	public void removeCoupon(Coupon coupon) throws ClassNotFoundException, SQLException, NotExistException {
		Connection connection = ConnectionPool.getInstance().getConnection();

		PreparedStatement statement = connection.prepareStatement(SQLQueryRequest.GET_ALL_COUPON_TITLES);
		ResultSet resultSet = statement.executeQuery();

		if (!resultSet.first()) {
			ConnectionPool.getInstance().returnConnection(connection);
			throw new NotExistException("Coupon title " + coupon.getTitle() + " is not exist");
		}
		statement = connection.prepareStatement(SQLQueryRequest.REMOVE_COUPON_BY_ID);
		statement.setLong(1, coupon.getId());
		statement.executeUpdate();
		statement = connection.prepareStatement(SQLQueryRequest.REMOVE_COUPON_FROM_COMPANY_COUPON_BY_COUPON_ID);
		statement.setLong(1, coupon.getId());
		statement.executeUpdate();

		ConnectionPool.getInstance().returnConnection(connection);
	}

	/**
	 * change some coupon in the DB to a new coupon
	 * 
	 * @throws ClassNotFoundException,
	 *             SQLException, IOException, ParseException
	 */
	@Override
	public void updateCoupon(long id, Coupon coupon)
			throws ClassNotFoundException, SQLException, IOException, ParseException {
		Connection connection = ConnectionPool.getInstance().getConnection();

		PreparedStatement statement = connection.prepareStatement(SQLQueryRequest.GET_COUPON_BY_ID);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			if (id == resultSet.getInt(1)) {

				statement = connection.prepareStatement(SQLQueryRequest.SET_COUPON_TITLE_BY_ID);
				statement.setString(1, coupon.getTitle());
				statement.setLong(2, id);
				statement.executeUpdate();

				statement = connection.prepareStatement(SQLQueryRequest.SET_COUPON_START_DATE_BY_ID);
				statement.setDate(1, (Date) coupon.getStartDate());
				statement.setLong(2, id);
				statement.executeUpdate();

				statement = connection.prepareStatement(SQLQueryRequest.SET_COUPON_END_DATE_BY_ID);
				statement.setDate(1, (Date) coupon.getEndDate());
				statement.setLong(2, id);
				statement.executeUpdate();

				statement = connection.prepareStatement(SQLQueryRequest.SET_COUPON_AMOUNT_BY_ID);
				statement.setInt(1, coupon.getAmount());
				statement.setLong(2, id);
				statement.executeUpdate();

				statement = connection.prepareStatement(SQLQueryRequest.SET_COUPON_TYPE_BY_ID);
				statement.setString(1, coupon.getType().toString());
				statement.setLong(2, id);
				statement.executeUpdate();

				statement = connection.prepareStatement(SQLQueryRequest.SET_COUPON_MESSAGE_BY_ID);
				statement.setString(1, coupon.getMessage());
				statement.setLong(2, id);
				statement.executeUpdate();

				statement = connection.prepareStatement(SQLQueryRequest.SET_COUPON_PRICE_BY_ID);
				statement.setDouble(1, coupon.getPrice());
				statement.setLong(2, id);
				statement.executeUpdate();

				statement = connection.prepareStatement(SQLQueryRequest.SET_COUPON_IMAGE_BY_ID);
				statement.setString(1, coupon.getImage());
				statement.setLong(2, id);

				System.out.println("Coupon updated successfull");
			}

		}
		ConnectionPool.getInstance().returnConnection(connection);
	}

	/**
	 * Return a coupon from the DB by coupon's id
	 * 
	 * @return Coupon
	 * @throws ClassNotFoundException,
	 *             SQLException, ParseException
	 */
	@Override
	public Coupon getCoupon(long id) throws ClassNotFoundException, SQLException, ParseException {
		Coupon coupon = new Coupon();
		Connection connection = ConnectionPool.getInstance().getConnection();
		PreparedStatement statement;
		statement = connection.prepareStatement(SQLQueryRequest.GET_COUPON_BY_ID);
		statement.setLong(1, id);
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			coupon.setId(resultSet.getLong(1));
			coupon.setTitle(resultSet.getString(2));
			coupon.setStartDate(StringDateConvertor.convert(resultSet.getString(3)));
			coupon.setEndDate(StringDateConvertor.convert(resultSet.getString(4)));
			coupon.setAmount(resultSet.getInt(5));
			coupon.setType(CouponType.valueOf(resultSet.getString(6)));
			coupon.setMessage(resultSet.getString(7));
			coupon.setPrice(resultSet.getDouble(8));
			coupon.setImage(resultSet.getString(9));
			ConnectionPool.getInstance().returnConnection(connection);
			return coupon;
		}

		ConnectionPool.getInstance().returnConnection(connection);
		return null;
	}

	/**
	 * return's a collection of all the coupons from the DB
	 */
	@Override
	public HashSet<Coupon> getAllCoupon() throws ClassNotFoundException, SQLException, ParseException {
		Connection connection = ConnectionPool.getInstance().getConnection();
		HashSet<Coupon> collectionCoupon = new HashSet<Coupon>();
		PreparedStatement statement = connection.prepareStatement(SQLQueryRequest.GET_ALL_COUPON_ID);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			collectionCoupon.add(getCoupon(resultSet.getLong(1)));
		}
		ConnectionPool.getInstance().returnConnection(connection);
		return collectionCoupon;
	}

	/**
	 * returns all the coupons of some type from the DB
	 * 
	 * @throws ClassNotFoundException,
	 *             SQLException, ParseException
	 */
	@Override
	public HashSet<Coupon> getCouponByType(CouponType couponType)
			throws ClassNotFoundException, SQLException, ParseException {
		Connection connection = ConnectionPool.getInstance().getConnection();
		HashSet<Coupon> collectionCoupon = new HashSet<Coupon>();
		PreparedStatement statement = connection.prepareStatement(SQLQueryRequest.GET_ALL_COUPON_ID_BY_TYPE);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			collectionCoupon.add(getCoupon(resultSet.getLong(1)));
		}
		ConnectionPool.getInstance().returnConnection(connection);
		return collectionCoupon;
	}

}
