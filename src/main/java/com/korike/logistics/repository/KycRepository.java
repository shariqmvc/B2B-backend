package com.korike.logistics.repository;

import com.korike.logistics.entity.Items;
import com.korike.logistics.entity.KycEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KycRepository extends JpaRepository<KycEntity, String> {
}
