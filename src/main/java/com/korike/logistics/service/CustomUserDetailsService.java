package com.korike.logistics.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.korike.logistics.repository.UserRepository;


@Component
public class CustomUserDetailsService implements UserDetailsService {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	private UserRepository restClientRepo;	

	public CustomUserDetailsService(UserRepository restClientRepo) {
		super();
		this.restClientRepo = restClientRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String clientName) throws UsernameNotFoundException, DataAccessException {
		try {
			
			return (UserDetails) this.restClientRepo.findByUserName(clientName)
			        .orElseThrow(() -> new Exception("Rest Client: " + clientName + " not found"));
		} catch (Exception e) {
			LOGGER.error("***********Token generation error while getting rest client data from DB " + e.getMessage());
			return null;
		}
	}

}
