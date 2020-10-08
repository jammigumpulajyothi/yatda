package com.hcl.yatda;
/**
 * POJO class with fields, constructor, getter and setter methods.
 * @author SupriyaBar
 *
 */
public class Task {

	public int id;
	public String subject;
	public String creationDate;
	public String status;
	public String lastChanged;
	public String inCharge;
	
	public Task() {
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public String getLastChanged() {
		return lastChanged;
	}
	public void setLastChanged(String lastChanged) {
		this.lastChanged = lastChanged;
	}
	public String getInCharge() {
		return inCharge;
	}
	public void setInCharge(String inCharge) {
		this.inCharge = inCharge;
	}
	
}
