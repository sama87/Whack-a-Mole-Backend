package com.cognixia.jump.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

@Entity
public class User {
	
	@Id //primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) //autoincrements
	@OneToMany(mappedBy = "ID")
	private Integer ID;
	
	@NotBlank(message = "Enter a username")
	@Column(name = "Username", columnDefinition = "varchar(10) DEFAULT 'Username'", nullable = false)
	private String userName;
	
	@NotBlank(message = "Enter your password")
	@Column(name = "Password", columnDefinition = "varchar(50) DEFAULT 'Password'", nullable = false)
	private String password;
	
	public User() {
		ID = -1;
		userName = "No such User";
		password = "N/A";
	}

	public User(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}
	
	/**
	 * @return the iD
	 */
	public Integer getID() {
		return ID;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}	
}
