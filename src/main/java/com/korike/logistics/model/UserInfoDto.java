package com.korike.logistics.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data @AllArgsConstructor @NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class UserInfoDto {
	@JsonProperty("userId")
	private String userId;
	private String userName;
	@JsonProperty("fullName")
	private String fullName;
	@JsonProperty("phone")
	private String phone;
	private String role;
	@JsonProperty("emailId")
	private String emailId;
	@JsonProperty("profilePhotoPath")
	private String profilePhotoPath;
	@JsonProperty("kyc")
	private List<Kyc> kyc;
	
	
	
	public UserInfoDto(String userId, String userName, String fullName, String phone, String role, String emailId,
			String profilePhotoPath, List<Kyc> kyc) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.fullName = fullName;
		this.phone = phone;
		this.role = role;
		this.emailId = emailId;
		this.profilePhotoPath = profilePhotoPath;
		this.kyc = kyc;
	}
	public UserInfoDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getProfilePhotoPath() {
		return profilePhotoPath;
	}
	public void setProfilePhotoPath(String profilePhotoPath) {
		this.profilePhotoPath = profilePhotoPath;
	}
	public List<Kyc> getKyc() {
		return kyc;
	}
	public void setKyc(List<Kyc> kyc) {
		this.kyc = kyc;
	}
	
	
	
	
	
}
