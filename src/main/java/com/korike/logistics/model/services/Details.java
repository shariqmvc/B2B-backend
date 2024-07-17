package com.korike.logistics.model.services;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Details {
	
	    @JsonProperty("s_id")
	    private String sId;
	    @JsonProperty("s_name")
	    private String sName;
	    @JsonProperty("s_icon")
	    private String sIcon;
	    @JsonProperty("s_promo")
	    private SPromo sPromo;
	    @JsonProperty("s_nearest")
	    private List<SNearest> sNearest;
		@JsonProperty("is_multiple_allowed")
		private Boolean isMultipleAllowed;
		@JsonProperty("is_active")
		private Boolean isActive;

	public Boolean getMultipleAllowed() {
		return isMultipleAllowed;
	}

	public void setMultipleAllowed(Boolean multipleAllowed) {
		isMultipleAllowed = multipleAllowed;
	}

	public Boolean getActive() {
		return isActive;
	}

	public void setActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getIsMultipleAllowed() {
		return isMultipleAllowed;
	}

	public void setIsMultipleAllowed(Boolean isMultipleAllowed) {
		this.isMultipleAllowed = isMultipleAllowed;
	}

	public Details() {
			super();
			// TODO Auto-generated constructor stub
		}
		public Details(String sId, String sName, String sIcon, Boolean isMultipleAllowed, Boolean isActive, SPromo sPromo, List<SNearest> sNearest) {
			super();
			this.sId = sId;
			this.sName = sName;
			this.sIcon = sIcon;
			this.isMultipleAllowed = isMultipleAllowed;
			this.isActive = isActive;
			this.sPromo = sPromo;
			this.sNearest = sNearest;
		}
		public String getsId() {
			return sId;
		}
		public void setsId(String sId) {
			this.sId = sId;
		}
		public String getsName() {
			return sName;
		}
		public void setsName(String sName) {
			this.sName = sName;
		}
		public String getsIcon() {
			return sIcon;
		}
		public void setsIcon(String sIcon) {
			this.sIcon = sIcon;
		}
		public SPromo getsPromo() {
			return sPromo;
		}
		public void setsPromo(SPromo sPromo) {
			this.sPromo = sPromo;
		}
		public List<SNearest> getsNearest() {
			return sNearest;
		}
		public void setsNearest(List<SNearest> sNearest) {
			this.sNearest = sNearest;
		}
	    
	    
}
