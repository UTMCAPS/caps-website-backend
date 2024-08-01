package com.utmcpas.caps_website_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.utmcpas.caps_website_backend.model.entity.Ledger;

@Repository
public interface LedgerRepository extends JpaRepository<Ledger, Integer>{
    
}
