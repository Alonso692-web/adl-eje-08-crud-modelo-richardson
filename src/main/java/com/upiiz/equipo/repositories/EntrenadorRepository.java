package com.upiiz.equipo.repositories;

import com.upiiz.equipo.entities.Entrenador;
import com.upiiz.equipo.entities.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EntrenadorRepository extends JpaRepository<Entrenador, Long> {

    @Query("SELECT en FROM Entrenador en WHERE en.id = :id")
    Entrenador findEntrenadorBy(@Param("id") Long id);

}
