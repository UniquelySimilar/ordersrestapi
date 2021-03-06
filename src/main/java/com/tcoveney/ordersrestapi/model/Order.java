package com.tcoveney.ordersrestapi.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "orders")
public class Order {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "customer_id", nullable=false, updatable=false)
	private Integer customerId;
	
	@Column(name = "order_status")
	@Range(min = 1, max = 4)
	private int orderStatus;
	
	// TODO: Currently limited to values provided via Datepickers in client app.  Possibly implement format validation in the future.
	@Column(name = "order_date")
	@NotNull()
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private LocalDate orderDate;
	
	@Column(name = "required_date")
	@NotNull()
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private LocalDate requiredDate;
	
	@Column(name = "shipped_date")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private LocalDate shippedDate;
	
	@Column(name = "created_at", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date createdAt;
	
	@Column(name = "updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date updatedAt;

	@Transient
	// This implementation of Transient keeps Hibernate from trying to map/persist this property,
	// but still includes it in any Jackson conversion for a JSON response.
	private List<LineItem> lineItems;
	

	public Integer getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getId() {
		return id;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	public LocalDate getRequiredDate() {
		return requiredDate;
	}

	public void setRequiredDate(LocalDate requiredDate) {
		this.requiredDate = requiredDate;
	}

	public LocalDate getShippedDate() {
		return shippedDate;
	}

	public void setShippedDate(LocalDate shippedDate) {
		this.shippedDate = shippedDate;
	}

	public java.util.Date getCreatedAt() {
		return createdAt;
	}

	public java.util.Date getUpdatedAt() {
		return updatedAt;
	}

	public List<LineItem> getLineItems() {
		return lineItems;
	}

	public void setLineItems(List<LineItem> lineItems) {
		this.lineItems = lineItems;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", customerId=" + customerId + ", orderStatus=" + orderStatus + ", orderDate=" + orderDate 
				+ ", requiredDate=" + requiredDate + ", shippedDate=" + shippedDate + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + "]";
	}

}
