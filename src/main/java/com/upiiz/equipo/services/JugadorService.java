package com.upiiz.equipo.services;

import com.upiiz.equipo.entities.Jugador;
import com.upiiz.equipo.repositories.JugadorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JugadorService {

    @Autowired
    JugadorRepository jugadorRepository;

    public List<Jugador> obtenerTodosJugadores() {
        return jugadorRepository.findAll();
    }

    public Jugador guardarJugador(Jugador jugador) {
        return jugadorRepository.save(jugador);
    }

    public Optional<Jugador> obtenerJugadorPorId(Long id) {
        return Optional.ofNullable(jugadorRepository.findJugadorById(id));
    }

    @Transactional
    public void deleteJugador(Long id) {
        jugadorRepository.deleteById(id);
    }

    public Jugador actualizarJugador(Jugador jugador) {
        return jugadorRepository.save(jugador);
    }

}
