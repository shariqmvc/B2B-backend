package com.korike.logistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.korike.logistics.entity.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, String>{

}
