package com.ef.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="accesslogfilter")
public class AccesslogfilterModel {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)	
    @Column(name = "accfil_id")
    private Long accfil_id;
	
	@Column(name = "accfil_ip")
	private String accfil_ip;
	
	@Column(name = "accfil_comment")
	private String accfil_comment;
	
	protected AccesslogfilterModel() {}
	
	public AccesslogfilterModel(String accfil_ip, String accfil_comment) {
		this.accfil_ip = accfil_ip;
		this.accfil_comment = accfil_comment;
	}

	public Long getAccfil_id() {
		return accfil_id;
	}

	public void setAccfil_id(Long accfil_id) {
		this.accfil_id = accfil_id;
	}

	public String getAccfil_ip() {
		return accfil_ip;
	}

	public void setAccfil_ip(String accfil_ip) {
		this.accfil_ip = accfil_ip;
	}

	public String getAccfil_comment() {
		return accfil_comment;
	}

	public void setAccfil_comment(String accfil_comment) {
		this.accfil_comment = accfil_comment;
	}
	@Override
	public String toString() {
		return new String(
				this.getAccfil_id().toString() + '|' +
				this.getAccfil_ip() + '|' +
				this.getAccfil_comment()
				);
	}

}
