package com.korike.logistics.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class Kyc {

	@JsonProperty("kycId")
	private String kycId;
	@JsonProperty("kycName")
	private String kycName;
	@JsonProperty("kycDescription")
	private String kycDescription;
	@JsonProperty("kycAttachmentPath")
	private List<String> kycAttachementPath;
	@JsonProperty("kycVerification")
	private Boolean kycVerification;
}
