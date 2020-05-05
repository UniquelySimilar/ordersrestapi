package com.tcoveney.ordersrestapi.dao;

import com.tcoveney.ordersrestapi.model.TokenUser;

public interface TokenUserDao {
	public TokenUser find(String username, String password);
	public TokenUser findByToken(String token);
	public void update(TokenUser tokenUser);
}
