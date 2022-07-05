package com.coursenet.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coursenet.order.entity.Orders;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long>{

}
