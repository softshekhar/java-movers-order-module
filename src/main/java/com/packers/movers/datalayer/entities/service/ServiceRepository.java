package com.packers.movers.datalayer.entities.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface ServiceRepository extends JpaRepository<Service, Integer> {
}
