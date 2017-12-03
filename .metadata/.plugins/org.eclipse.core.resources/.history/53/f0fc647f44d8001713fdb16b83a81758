package couponsProject;

import java.sql.SQLException;

import Facades.CouponClientFacade;

public class CouponSystem {
	private CouponClientFacade client;
	private static CouponSystem instance;
	private DailyCouponExpirationTask task = new DailyCouponExpirationTask();

	private CouponSystem() {
	}

	public static CouponSystem getInstance() {
		if (instance == null) {
			instance = new CouponSystem();
		}
		System.out.println("You got an instance of CouponSystem");
		return instance;
	}

	public CouponClientFacade login(String name, String password, ClientType type) {
		try {
			client = ClientTypeFactory.login(name, password, type);
			return client;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	return null;
	}
	
	public void shutdown() {
		task.stopTask();
		try {
			ConnectionPool.getInstance().closeConnections();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
