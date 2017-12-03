package Facades;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Collection;

import Exceptions.DuplicateNameException;
import Exceptions.NotExistException;
import couponsProject.ClientType;
import couponsProject.ClientTypeFactory;
import couponsProject.Coupon;
import couponsProject.CouponType;
import couponsProject.DAO.CompanyDBDAO;
import couponsProject.DAO.CouponDBDAO;

public class CompanyFacade implements CouponClientFacade {
	private CouponDBDAO coupon = new CouponDBDAO();
	private CompanyDBDAO company = new CompanyDBDAO();

	public CompanyFacade() {

	}

	public void createCoupon(Coupon coup) {
		try {
			company.createCoupon(coup);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (DuplicateNameException e) {
			e.printStackTrace();
		}
	}

	public void removeCoupon(Coupon coup) {
		try {
			coupon.removeCoupon(coup);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NotExistException e) {
			e.printStackTrace();
		}
	}

	public void updateCoupon(long id, Coupon coup) {
		try {
			coupon.updateCoupon(id, coup);
		} catch (ClassNotFoundException | SQLException | IOException | ParseException e) {
			e.printStackTrace();
		}
	}

	public Coupon getCoupon(long id) {
		try {
			return coupon.getCoupon(id);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Collection<Coupon> getAllCoupon() {
		try {
			return coupon.getAllCoupon();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Collection<Coupon> getCouponByType(CouponType couponType) {
		try {
			return coupon.getCouponByType(couponType);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public CouponClientFacade login(String name, String password, ClientType client) {
		try {
			return ClientTypeFactory.login(name, password, client);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
