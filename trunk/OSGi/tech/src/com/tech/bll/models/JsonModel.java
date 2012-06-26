package com.tech.bll.models;

public class JsonModel {
	public String err;
	public String status;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Object model;
	public String getErr() {
		return err;
	}
	public void setErr(String err) {
		this.err = err;
	}
	public Object getModel() {
		return model;
	}
	public void setModel(Object model) {
		this.model = model;
	}
}
