package com.zs.loginpage.records;

public class UserDetails {
	int id;
	String name;
	String email;
	String password;

	public UserDetails(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}
	public UserDetails(String email,String password){
		this.email = email;
		this.password = password;
	}

	UserDetails(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
