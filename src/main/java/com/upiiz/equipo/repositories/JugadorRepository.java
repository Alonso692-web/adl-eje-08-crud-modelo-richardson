package com.upiiz.equipo.repositories;

import com.upiiz.equipo.entities.Equipo;
import com.upiiz.equipo.entities.Jugador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JugadorRepository extends JpaRepository<Jugador, Long> {

    // Métodos para buscar jugador por ID usando Query personalizada
    @Query("SELECT j FROM Jugador j WHERE j.id = :id")
    Jugador findJugadorById(@Param("id") Long id);
}
