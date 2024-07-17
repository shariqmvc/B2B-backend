package com.korike.logistics.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class AuthRequestDto {

	private String userName;
	private Integer authType;
	private int authMode;
	private String oneTimePass;
	private String password;
	private String mobile;
	private String email;
	private String isNew = "false";

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getAuthType() {
		return authType;
	}

	public void setAuthType(Integer authType) {
		this.authType = authType;
	}

	public String getOneTimePass() {
		return oneTimePass;
	}

	public void setOneTimePass(String oneTimePass) {
		this.oneTimePass = oneTimePass;
	}

	public int getAuthMode() {
		return authMode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public void setAuthMode(int authMode) {
		this.authMode = authMode;
	}
}
