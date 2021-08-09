package com.juesfe.inventario.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juesfe.inventario.entity.Cargo;

@Transactional
public interface CargoDao extends JpaRepository <Cargo, Long> {

}
