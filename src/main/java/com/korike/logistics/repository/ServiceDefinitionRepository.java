package com.korike.logistics.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.korike.logistics.entity.ServiceDefinition;

@Repository
public interface ServiceDefinitionRepository extends JpaRepository<ServiceDefinition, String>{

	@Query(value = "select * from service_definition where service_id = :service_id", nativeQuery = true)
	ServiceDefinition getByServiceId(@Param("service_id") String service_id);
	
	//select * from service_definition where service_id = '' or is_active = false or service_name=''
	@Query(value = "select * from service_definition where service_id = :service_id or is_active = :is_active or service_name= :service_name", nativeQuery = true)
	/* select * from customer_order_details where date(created_at)  between '2022-04-06' and '2022-04-06' and order_status in ('CREATED','INPROGRESS','COMPLETED','FAILED','CANCELLED') */
	List<ServiceDefinition> getServiceByFilter(@Param("service_id") String service_id, @Param("service_name") String service_name, @Param("is_active") Boolean is_active);

	@Query(value = "select * from service_definition where service_id in (select service_id from partner where user_id = :user_id)", nativeQuery = true)
	List<ServiceDefinition> getRegisteredServicesByUser(@Param("user_id") String user_id);
}
