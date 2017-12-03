package dao;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Collection;

import beans.Coupon;
import beans.CouponType;
import database.DuplicateNameException;
import database.NotExistException;

public interface CouponDAO {
	public void createCoupon(Coupon coupon)
			throws ClassNotFoundException, SQLException, ParseException, DuplicateNameException;

	public void removeCoupon(Coupon coupon) throws ClassNotFoundException, SQLException, NotExistException;

	public void updateCoupon(long id, Coupon coupon)
			throws ClassNotFoundException, SQLException, IOException, ParseException;

	public Coupon getCoupon(long id) throws ClassNotFoundException, SQLException, ParseException;

	public Collection<Coupon> getAllCoupon() throws ClassNotFoundException, SQLException, ParseException;

	public Collection<Coupon> getCouponByType(CouponType couponType)
			throws ClassNotFoundException, SQLException, ParseException;
}
