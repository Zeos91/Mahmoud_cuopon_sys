package couponsProject;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

import Exceptions.NotExistException;
import couponsProject.DAO.*;

public class DailyCouponExpirationTask implements Runnable {
	private CouponDBDAO couponDBDAO = new CouponDBDAO();
	private boolean quit = false;
	private Thread thread;

	public DailyCouponExpirationTask() {
		thread = new Thread(this, "DailyCouponExpirationTask");
		thread.start();
	}
/**
 *  every day check the end-date of each coupon. If the end-date is over delete's a coupon
 */
	@Override
	public void run() {
		HashSet<Coupon> allCoupons;
		try {
			allCoupons = couponDBDAO.getAllCoupon();
		while (!quit) {
			Iterator<Coupon> iterator = allCoupons.iterator();
					while (iterator.hasNext()) {
						Coupon tmp = iterator.next();
						if (tmp.getEndDate().before(new Date())) {
							couponDBDAO.removeCoupon(tmp);
						}
					}
					Thread.sleep(86400000);
			}
		
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (NotExistException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void stopTask() {
		quit = true;
	}

}
