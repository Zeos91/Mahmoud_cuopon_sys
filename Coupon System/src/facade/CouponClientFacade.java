package facade;

import beans.ClientType;

public interface CouponClientFacade {
	public CouponClientFacade login(String name, String password, ClientType client);
}


