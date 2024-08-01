package com.utmcpas.caps_website_backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utmcpas.caps_website_backend.model.entity.Ledger;
import com.utmcpas.caps_website_backend.repository.LedgerRepository;

@Service
public class LedgerService {
    
    @Autowired
    private LedgerRepository ledgerRepository;

    public List<Ledger> findAll(){
        return ledgerRepository.findAll();
    }

    public void insertInfo(Ledger ledger){
        ledgerRepository.save(ledger);
    }

    public void deleteLedgerById(Integer ledgerID) {
        ledgerRepository.deleteById(ledgerID);;
    }
}
