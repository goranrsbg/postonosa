package com.goranrsbg.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="puser")
public class User {
	
	@Id
	@Column(name="uname", length=64)
	@Size(min=64, max=64, message="name is not SHA-256 string")
	private String name;
	
	@Column(name="upword", length=64)
	@Size(min=64, max=64, message="pword is not SHA-256 string")
	private String pword;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPword() {
		return pword;
	}

	public void setPword(String pword) {
		this.pword = pword;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", pword=" + pword + "]";
	}
	
}
