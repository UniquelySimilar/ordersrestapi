package com.tcoveney.ordersrestapi.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Pattern.List;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "customers")
public class Customer {
	// NOTE: MySQL child 'orders' table configured for CASCADE DELETE when parent 'customers' record deleted
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "first_name")
	@Size( min = 1,max = 30)
	private String firstName;
	
	@Column(name = "last_name")
	@Size( min = 1,max = 30)
	private String lastName;
	
	@NotBlank
	private String street;
	
	@NotBlank
	private String city;
	
	@NotBlank
	private String state;
	
	@Pattern(regexp = "^[0-9]{5}(?:-[0-9]{4})?$")
	private String zipcode;

	@Column(name = "home_phone")
	@Pattern(regexp = "\\d{3}\\-\\d{3}\\-\\d{4}")	// Accept only format nnn-nnn-nnnn
	//@Pattern(regexp = "\\d{10}|\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}|\\(\\d{3}\\)\\d{3}-\\d{4}")	// Test all three patterns
	//validate phone numbers of format "1234567890": regexp = "\\d{10}"
	//validating phone number with -, . or spaces: regexp = "\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}"
	//validating phone number where area code is in braces (): regexp = "\\(\\d{3}\\)\\d{3}-\\d{4}"
	private String homePhone;

	// TODO: Use above @Pattern OR'd with empty pattern since not required
	@Column(name = "work_phone")
	@Pattern(regexp = "^$|\\d{3}\\-\\d{3}\\-\\d{4}")	// Accept only format nnn-nnn-nnnn
	private String workPhone;
	
	@NotBlank
	@Email()
	private String email;
	
	@Column(name = "created_at", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	@Column(name = "updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;
		
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getStreet() {
		return street;
	}
	
	public void setStreet(String street) {
		this.street = street;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public String getZipcode() {
		return zipcode;
	}
	
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
	public String getHomePhone() {
		return homePhone;
	}
	
	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}
	
	public String getWorkPhone() {
		return workPhone;
	}
	
	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Date getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	public Date getUpdatedAt() {
		return updatedAt;
	}
	
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", street=" + street + ", city=" + city + ", state=" + state
				+ ", zipcode=" + zipcode + ", homePhone=" + homePhone + ", workPhone=" + workPhone + ", email=" + email
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}

}
