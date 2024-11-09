package com.upiiz.equipo.controllers;

import com.upiiz.equipo.entities.Jugador;
import com.upiiz.equipo.responses.CustomResponseJugador;
import com.upiiz.equipo.services.JugadorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/v1/jugador")
@Tag(
        name = "Jugadores"
)
public class JugadorController {

    @Autowired
    JugadorService jugadorService;

    @GetMapping
    public ResponseEntity<CustomResponseJugador<List<Jugador>>> getJugador() {
        List<Jugador> jugadores = new ArrayList<>();
        Link allClientsLink = linkTo(JugadorController.class).withSelfRel();
        List<Link> links = List.of(allClientsLink);
        try {
            jugadores = jugadorService.obtenerTodosJugadores();
            if (!jugadores.isEmpty()) {
                CustomResponseJugador<List<Jugador>> response = new CustomResponseJugador<>(1, "Jugadores encontrados", jugadores, links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponseJugador<>(6, "Jugadores no encontrados", jugadores, links));
            }
        } catch (Exception e) {
            CustomResponseJugador<List<Jugador>> response = new CustomResponseJugador<>(8, "Error interno de servidor", jugadores, links);
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Obtener equipo por Id
    @GetMapping("/{id}")
    public ResponseEntity<CustomResponseJugador<Jugador>> getJugadorById(@PathVariable Long id) {
        Optional<Jugador> equipo = null;
        CustomResponseJugador<Jugador> response = null;
        Link allJugadorsLink = linkTo(JugadorController.class).withSelfRel();
        List<Link> links = List.of(allJugadorsLink);
        try {
            equipo = jugadorService.obtenerJugadorPorId(id);
            if (equipo.isPresent()) {
                response = new CustomResponseJugador<>(1, "Jugador encontrado", equipo.get(), links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                response = new CustomResponseJugador<>(0, "Jugador no encontrado", null, links);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response = new CustomResponseJugador<>(0, "Error interno de servidor", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Guardar Jugador
    @PostMapping
    public ResponseEntity<CustomResponseJugador<Jugador>> crearJugador(@RequestBody Jugador equipo) {
        Link allClientsLink = linkTo(JugadorController.class).withSelfRel();
        List<Link> links = List.of(allClientsLink);
        try {
            Jugador equipoEntity = jugadorService.guardarJugador(equipo);
            if (equipoEntity != null) {
                CustomResponseJugador<Jugador> response = new CustomResponseJugador<>(1, "Jugador creado", equipoEntity, links);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponseJugador<>(6, "Jugador no encontrado", equipoEntity, links));
            }
        } catch (Exception e) {
            CustomResponseJugador<Jugador> response = new CustomResponseJugador<>(8, "Error interno de servidor", null, links);
            System.out.println("Error en Post Jugador: "+e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Eliminar Jugador
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponseJugador<Jugador>> deleteJugadorById(@PathVariable Long id) {
        Optional<Jugador> equipo = null;
        CustomResponseJugador<Jugador> response = null;
        Link allJugadorsLink = linkTo(JugadorController.class).withSelfRel();
        List<Link> links = List.of(allJugadorsLink);

        try {
            equipo = jugadorService.obtenerJugadorPorId(id);
            if (equipo.isPresent()) {
                jugadorService.deleteJugador(id);
                response = new CustomResponseJugador<>(1, "Jugador eliminado", null, links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                response = new CustomResponseJugador<>(0, "Jugador no encontrado", null, links);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response = new CustomResponseJugador<>(500, "Error interno de servidor", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Actualizar equipo por id
    @PutMapping("/{id}")
    public ResponseEntity<CustomResponseJugador<Jugador>> updateJugador(@RequestBody Jugador equipo, @PathVariable Long id) {
        Link allJugadorsLink = linkTo(JugadorController.class).withSelfRel();
        List<Link> links = List.of(allJugadorsLink);
        try {
            equipo.setId(id);
            if (jugadorService.obtenerJugadorPorId(id).isPresent()) {
                Jugador equipoEntity = jugadorService.actualizarJugador(equipo);
                CustomResponseJugador<Jugador> response = new CustomResponseJugador<>(1, "Jugador actualizado", equipoEntity, links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }else{
                CustomResponseJugador<Jugador> response = new CustomResponseJugador<>(0, "Jugador no encontrado", null, links);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        }catch (Exception e) {
            CustomResponseJugador<Jugador> response = new CustomResponseJugador<>(500, "Error interno de servidor", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
