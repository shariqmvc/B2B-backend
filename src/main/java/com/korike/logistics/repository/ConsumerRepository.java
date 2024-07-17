package com.korike.logistics.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.korike.logistics.entity.Consumer;
import com.korike.logistics.entity.User;

@Repository
public interface ConsumerRepository extends JpaRepository<Consumer, String>{
	
	Consumer findByUser(User fetchedUser);

}
