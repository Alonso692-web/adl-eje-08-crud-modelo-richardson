package com.upiiz.equipo.services;

import com.upiiz.equipo.entities.Equipo;
import com.upiiz.equipo.entities.Liga;
import com.upiiz.equipo.repositories.LigaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LigaService {

    @Autowired
    LigaRepository ligaRepository;

    public List<Liga> obtenerTodos() {
        return ligaRepository.findAll();
    }

    public Liga guardarLiga(Liga liga) {
        return ligaRepository.save(liga);
    }

    public Optional<Liga> obtenerLigaPorId(Long id) {
        return Optional.ofNullable(ligaRepository.findLigaById(id));
    }

    @Transactional
    public void deleteLiga(Long id){
        ligaRepository.deleteById(id);
    }

    public Liga actualizarEquipo(Liga liga) {
        return ligaRepository.save(liga);
    }

}
