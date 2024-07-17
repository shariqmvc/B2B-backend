package com.korike.logistics.repository;

import java.util.List;
import java.util.Optional;

import com.korike.logistics.entity.BillingTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.korike.logistics.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{

	Optional<User> findByUserName(String restClientName);

	@Query(value = "select * from (select * from users) as userdata JOIN (select * from user_role) as userroledata ON userdata.user_id = userroledata.user_id and user_name = :user_name and role_id = :role_id" , nativeQuery = true)
	Optional<User> getUserByNameAndRole(@Param("user_name") String user_name, @Param("role_id") Integer role_id);

	Optional<User> findByOneTimePass(String oTp);

	User findByAuthToken(String authToken);

}
