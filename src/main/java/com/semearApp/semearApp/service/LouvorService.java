package com.semearApp.semearApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.semearApp.semearApp.models.Louvor;
import com.semearApp.semearApp.repository.LouvorRepository;

@Service
public class LouvorService {

    @Autowired
    private LouvorRepository louvorRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Louvor> findAllActive() {
        return louvorRepository.findByAtivoTrue();
    }
}