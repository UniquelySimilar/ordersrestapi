package com.tcoveney.ordersrestapi.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tokenusersspring")
public class TokenUser {
	@Id
	@Column(name="username", unique=true, nullable=false)
	private String username;
	
	@Column(nullable=false)
	private String password;
	
	@Column
	private String token;
	
	@Column(name="tokenexp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date tokenExp;
	
	@Column
	private boolean enabled;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getTokenExp() {
		return tokenExp;
	}

	public void setTokenExp(Date tokenExp) {
		this.tokenExp = tokenExp;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "TokenUser [username=" + username + ", password=" + password + ", token=" + token + ", tokenExp="
				+ tokenExp + ", enabled=" + enabled + "]";
	}

}
