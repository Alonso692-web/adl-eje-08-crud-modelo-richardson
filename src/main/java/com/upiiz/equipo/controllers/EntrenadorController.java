package com.upiiz.equipo.controllers;

import com.upiiz.equipo.entities.Entrenador;
import com.upiiz.equipo.responses.CustomResponseEntrenador;
import com.upiiz.equipo.services.EntrenadorService;
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
@RequestMapping("/api/v1/entrenador")
@Tag(
        name = "Entrenadores"
)
public class EntrenadorController {

    @Autowired
    EntrenadorService entrenadorService;

    // Obtener todos los entrenadores
    @GetMapping
    public ResponseEntity<CustomResponseEntrenador<List<Entrenador>>> getEntrenadores() {
        List<Entrenador> entrenadors = new ArrayList<>();
        Link allEntrenadoresLink = linkTo(EntrenadorController.class).withSelfRel();
        List<Link> links = List.of(allEntrenadoresLink);
        try {
            entrenadors = entrenadorService.obtenerTodosEntrenadores();
            if (!entrenadors.isEmpty()) {
                CustomResponseEntrenador<List<Entrenador>> response = new CustomResponseEntrenador<>(1, "Entrenadores encontrados", entrenadors, links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponseEntrenador<>(6, "Entrenadores no encontrados", entrenadors, links));
            }
        } catch (Exception e) {
            CustomResponseEntrenador<List<Entrenador>> response = new CustomResponseEntrenador<>(8, "Error interno de servidor", entrenadors, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Guardar Entrenador
    @PostMapping
    public ResponseEntity<CustomResponseEntrenador<Entrenador>> crearEntrenador(@RequestBody Entrenador entrenador) {
        Link allEntrenadoresLink = linkTo(EntrenadorController.class).withSelfRel();
        List<Link> links = List.of(allEntrenadoresLink);
        try {
            Entrenador entrenadorEntity = entrenadorService.guardarEntrenador(entrenador);
            if (entrenadorEntity != null) {
                CustomResponseEntrenador<Entrenador> response = new CustomResponseEntrenador<>(1, "Entrenador creado", entrenadorEntity, links);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponseEntrenador<>(6, "Entrenador no encontrado", entrenadorEntity, links));
            }
        } catch (Exception e) {
            CustomResponseEntrenador<Entrenador> response = new CustomResponseEntrenador<>(8, "Error interno de servidor", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Obtener entrenador por Id
    @GetMapping("/{id}")
    public ResponseEntity<CustomResponseEntrenador<Entrenador>> getEntrenadorById(@PathVariable Long id) {
        Optional<Entrenador> entrenador = null;
        CustomResponseEntrenador<Entrenador> response = null;
        Link allEntrenadoresLink = linkTo(EntrenadorController.class).withSelfRel();
        List<Link> links = List.of(allEntrenadoresLink);
        try {
            entrenador = entrenadorService.obtenerEntrenadorPorId(id);
            if (entrenador.isPresent()) {
                response = new CustomResponseEntrenador<>(1, "Entrnador encontrado", entrenador.get(), links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                response = new CustomResponseEntrenador<>(0, "Entrenador no encontrado", null, links);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response = new CustomResponseEntrenador<>(0, "Error interno de servidor", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Eliminar Entrenador
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponseEntrenador<Entrenador>> deleteEntrenadorById(@PathVariable Long id) {
        Optional<Entrenador> entrenador = null;
        CustomResponseEntrenador<Entrenador> response = null;
        Link allEntrenadoresLink = linkTo(EquipoController.class).withSelfRel();
        List<Link> links = List.of(allEntrenadoresLink);

        try {
            entrenador = entrenadorService.obtenerEntrenadorPorId(id);
            if (entrenador.isPresent()) {
                entrenadorService.deleteEntrenador(id);
                response = new CustomResponseEntrenador<>(1, "Entrenador eliminado", null, links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                response = new CustomResponseEntrenador<>(0, "Entrenador no encontrado", null, links);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response = new CustomResponseEntrenador<>(500, "Error interno de servidor", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Actualizar entrenador por id
    @PutMapping("/{id}")
    public ResponseEntity<CustomResponseEntrenador<Entrenador>> updateEntrenador(@RequestBody Entrenador entrenador, @PathVariable Long id) {
        Link allEntrenadoresLink = linkTo(EntrenadorController.class).withSelfRel();
        List<Link> links = List.of(allEntrenadoresLink);
        try {
            entrenador.setId(id);
            if (entrenadorService.obtenerEntrenadorPorId(id).isPresent()) {
                Entrenador entrenadorEntity = entrenadorService.actualizarEntrenador(entrenador);
                CustomResponseEntrenador<Entrenador> response = new CustomResponseEntrenador<>(1, "Entrenador actualizado", entrenadorEntity, links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }else{
                CustomResponseEntrenador<Entrenador> response = new CustomResponseEntrenador<>(0, "Entrenador no encontrado", null, links);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        }catch (Exception e) {
            CustomResponseEntrenador<Entrenador> response = new CustomResponseEntrenador<>(500, "Error interno de servidor", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}