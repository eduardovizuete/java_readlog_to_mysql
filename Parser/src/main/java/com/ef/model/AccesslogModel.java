package com.ef.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="accesslog")
public class AccesslogModel {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)	
    @Column(name = "acc_id")
    private Long acc_id;
	
	@Column(name = "acc_date")
	private Date acc_date;
	
	@Column(name = "acc_ip")
	private String acc_ip;
	
	@Column(name = "acc_request")
	private String acc_request;
	
	@Column(name = "acc_status")
	private String acc_status;
	
	@Column(name = "acc_useragent")
	private String acc_useragent;
	
	protected AccesslogModel() {}

	public AccesslogModel(Date acc_date, String acc_ip, String acc_request, String acc_status,
			String acc_useragent) {
		this.acc_date = acc_date;
		this.acc_ip = acc_ip;
		this.acc_request = acc_request;
		this.acc_status = acc_status;
		this.acc_useragent = acc_useragent;
	}

	public Long getAcc_id() {
		return acc_id;
	}

	public void setAcc_id(Long acc_id) {
		this.acc_id = acc_id;
	}

	public Date getAcc_date() {
		return acc_date;
	}

	public void setAcc_date(Date acc_date) {
		this.acc_date = acc_date;
	}

	public String getAcc_ip() {
		return acc_ip;
	}

	public void setAcc_ip(String acc_ip) {
		this.acc_ip = acc_ip;
	}

	public String getAcc_request() {
		return acc_request;
	}

	public void setAcc_request(String acc_request) {
		this.acc_request = acc_request;
	}

	public String getAcc_status() {
		return acc_status;
	}

	public void setAcc_status(String acc_status) {
		this.acc_status = acc_status;
	}

	public String getAcc_useragent() {
		return acc_useragent;
	}

	public void setAcc_useragent(String acc_useragent) {
		this.acc_useragent = acc_useragent;
	}

	@Override
	public String toString() {
		return new String(
				this.getAcc_id().toString() + '|' +
				this.getAcc_date() + '|' +
				this.getAcc_ip() + '|' +
				this.getAcc_request() + '|' +
				this.acc_status+ '|' +
				this.getAcc_useragent()
				);
	}
	
	
}
