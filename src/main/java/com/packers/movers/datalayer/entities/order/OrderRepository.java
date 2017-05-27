package com.packers.movers.datalayer.entities.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

}
