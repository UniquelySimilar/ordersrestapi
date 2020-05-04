package com.tcoveney.ordersrestapi.dao;

import com.tcoveney.ordersrestapi.model.TokenUser;

public interface TokenUserDao {
	public TokenUser find(String userName);
	public TokenUser findByToken(String token);
}
