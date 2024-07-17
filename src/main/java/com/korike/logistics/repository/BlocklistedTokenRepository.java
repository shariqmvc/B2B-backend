package com.korike.logistics.repository;

import com.korike.logistics.entity.BlocklistedUserTokens;
import com.korike.logistics.entity.Category;
import com.korike.logistics.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlocklistedTokenRepository extends JpaRepository<BlocklistedUserTokens, String> {
    @Query(value = "select * from blocklister_user_tokens where user_id = :user_id and token = :token" , nativeQuery = true)
    BlocklistedUserTokens getBlocklistedUserToken(@Param("user_id") String user_id, @Param("token") String token);
}
