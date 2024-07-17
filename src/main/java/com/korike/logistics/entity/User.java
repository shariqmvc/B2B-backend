package com.korike.logistics.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.korike.logistics.controller.AuthenticationController;
import com.korike.logistics.util.CoreUtil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.korike.logistics.controller.AuthenticationController.authMode;

@Entity
@Table(name="users")

public class User implements UserDetails{

	@Id
	@Column(name="userId", length=100)
	private String userId;
	@Column(name = "user_details", nullable = true, columnDefinition="TEXT")
	private String userDetails;
	@Column(name = "user_name", nullable = true)
	private String userName;

	@Column(name = "email", nullable = true)
	private String email;


	@Column(name = "password", nullable = true)
	private String password;

	@Column(name = "mobile", nullable = true)
	private String mobile;
	@Column(name = "one_time_pass", nullable = true)
	private String oneTimePass;

	@Column(name = "is_account_new", nullable = true)
	private boolean isAccountNew = true;

	@Column(name = "is_email_verified", nullable = true)
	private boolean isEmailVerified = false;
	@Column(name = "is_mobile_verified", nullable = true)
	private boolean isMobileVerified = false;


	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss.S")
	private Timestamp otpCreatedAt;
	@Column(name = "auth_token", nullable = true)
	private String authToken;
	@Column(name = "auth_token_expiry", nullable = true)
	private Date authTokenExpiry;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss.S")
	private Timestamp createdAt;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss.S")
	private Timestamp lastUpdatedAt;
	@Column(name = "created_by", nullable = true)
	private String createdBy;
	@Column(name = "last_updated_by", nullable = true)
	private String lastUpdatedBy;
	
	
	
	
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

/*	public User(String userId, String userDetails, String email, String password, String mobile, String oneTimePass, Timestamp otpCreatedAt, String authToken, Date authTokenExpiry, Timestamp createdAt, Timestamp lastUpdatedAt, String createdBy, String lastUpdatedBy, Set<Role> roles, Boolean isActive) {
		this.userId = userId;
		this.userDetails = userDetails;
		this.email = email;
		this.password = password;
		this.mobile = mobile;
		this.oneTimePass = oneTimePass;
		this.otpCreatedAt = otpCreatedAt;
		this.authToken = authToken;
		this.authTokenExpiry = authTokenExpiry;
		this.createdAt = createdAt;
		this.lastUpdatedAt = lastUpdatedAt;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.roles = roles;
		this.isActive = isActive;
	}

	public User(String userId, String userDetails, String userName, String oneTimePass, Timestamp createdAt,
				Timestamp lastUpdatedAt, String createdBy, String lastUpdatedBy, Set<Role> roles, Boolean isActive) {
		super();
		this.userId = userId;
		this.userDetails = userDetails;
		//this.userName = userName;
		this.oneTimePass = oneTimePass;
		this.createdAt = createdAt;
		this.lastUpdatedAt = lastUpdatedAt;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.roles = roles;
		this.isActive = isActive;
	} */

/*	public User(String userId, String userDetails, String userName, String oneTimePass, Timestamp createdAt,
				Timestamp lastUpdatedAt, String createdBy, String lastUpdatedBy, Set<Role> roles, Boolean isActive, String mobile, String password) {
		super();
		this.userId = userId;
		this.userDetails = userDetails;
		//this.userName = userName;
		this.oneTimePass = oneTimePass;
		this.createdAt = createdAt;
		this.lastUpdatedAt = lastUpdatedAt;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.roles = roles;
		this.isActive = isActive;
		this.mobile = mobile;
		this.password = password;
	} */

	public User(String userId, String userDetails, String userName, String email, String password, String mobile, String oneTimePass, Timestamp otpCreatedAt, String authToken, Date authTokenExpiry, Timestamp createdAt, Timestamp lastUpdatedAt, String createdBy, String lastUpdatedBy) {
		this.userId = userId;
		this.userDetails = userDetails;
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.mobile = mobile;
		this.oneTimePass = oneTimePass;
		this.otpCreatedAt = otpCreatedAt;
		this.authToken = authToken;
		this.authTokenExpiry = authTokenExpiry;
		this.createdAt = createdAt;
		this.lastUpdatedAt = lastUpdatedAt;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		//this.roles = roles;
		//this.isActive = isActive;
	}

	public User(String userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserDetails() {
		return userDetails;
	}
	public void setUserDetails(String userDetails) {
		this.userDetails = userDetails;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getOneTimePass() {
		return oneTimePass;
	}
	public void setOneTimePass(String oneTimePass) {
		this.oneTimePass = oneTimePass;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public Timestamp getLastUpdatedAt() {
		return lastUpdatedAt;
	}
	public void setLastUpdatedAt(Timestamp lastUpdatedAt) {
		this.lastUpdatedAt = lastUpdatedAt;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
	
	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	public Date getAuthTokenExpiry() {
		return authTokenExpiry;
	}
	public void setAuthTokenExpiry(Date authTokenExpiry) {
		this.authTokenExpiry = authTokenExpiry;
	}
	public Timestamp getOtpCreatedAt() {
		return otpCreatedAt;
	}
	public void setOtpCreatedAt(Timestamp otpCreatedAt) {
		this.otpCreatedAt = otpCreatedAt;
	}

	public Boolean getActive() {
		return isActive;
	}

	public void setActive(Boolean active) {
		isActive = active;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAccountNew() {
		return isAccountNew;
	}

	public void setAccountNew(boolean accountNew) {
		isAccountNew = accountNew;
	}

	public boolean isEmailVerified() {
		return isEmailVerified;
	}

	public void setEmailVerified(boolean emailVerified) {
		isEmailVerified = emailVerified;
	}

	public boolean isMobileVerified() {
		return isMobileVerified;
	}

	public void setMobileVerified(boolean mobileVerified) {
		isMobileVerified = mobileVerified;
	}

	@ManyToMany(fetch = FetchType.EAGER,cascade = {
		        CascadeType.PERSIST,
		        CascadeType.MERGE
		    })
		@JoinTable( name="user_role",
		  joinColumns = @JoinColumn(name = "user_id"), 
		  inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;
	@Column(name = "is_active", nullable = true)
	private Boolean isActive;
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<String> roleslst = new ArrayList<>();
		roleslst.add(roles.stream().collect(Collectors.toList()).get(0).getName());
        return roleslst.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		String password = null;
		if(CoreUtil.getAuthModeByNum(authMode).equals("EP") || CoreUtil.getAuthModeByNum(authMode).equals("MP")){
			password = this.password;
		}else if(CoreUtil.getAuthModeByNum(authMode).equals("EO") || CoreUtil.getAuthModeByNum(authMode).equals("MO")){
			password = this.oneTimePass;
		}
		return password;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		String refUserName = null;
		if(CoreUtil.getAuthModeByNum(authMode).equals("MO") || CoreUtil.getAuthModeByNum(authMode).equals("MP")){
			refUserName = this.mobile;
		}else if(CoreUtil.getAuthModeByNum(authMode).equals("EO") || CoreUtil.getAuthModeByNum(authMode).equals("EP")){
			//refUserName = this.userName;
			refUserName = this.email;
		}

		return refUserName;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		 return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		 return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		 return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return isActive;
	}
	
}
