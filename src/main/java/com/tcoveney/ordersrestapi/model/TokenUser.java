package com.tcoveney.ordersrestapi.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tokenusers")
public class TokenUser {
	@Id
	@Column(name="username", unique=true, nullable=false)
	private String userName;
	
	@Column(nullable=false)
	private String password;
	
	@Column
	private String token;
	
	@Column(name="tokenexp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date tokenExp;
	
	@Column
	private boolean enabled;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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
		return "TokenUser [userName=" + userName + ", password=" + password + ", token=" + token + ", tokenExp="
				+ tokenExp + ", enabled=" + enabled + "]";
	}

}
