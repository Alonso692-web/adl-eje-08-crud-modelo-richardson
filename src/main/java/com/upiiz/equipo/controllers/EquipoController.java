package com.upiiz.equipo.controllers;

import com.upiiz.equipo.entities.Equipo;
import com.upiiz.equipo.responses.CustomResponseEquipo;
import com.upiiz.equipo.services.EquipoService;
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
@RequestMapping("/api/v1/equipo")
@Tag(
        name = "Equipo"
)
public class EquipoController {

    @Autowired
    EquipoService equipoService;

    @GetMapping
    public ResponseEntity<CustomResponseEquipo<List<Equipo>>> getEquipo() {
        List<Equipo> equipos = new ArrayList<>();
        Link allClientsLink = linkTo(EquipoController.class).withSelfRel();
        List<Link> links = List.of(allClientsLink);
        try {
            equipos = equipoService.obtenerTodos();
            if (!equipos.isEmpty()) {
                CustomResponseEquipo<List<Equipo>> response = new CustomResponseEquipo<>(1, "Equipos encontrados", equipos, links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponseEquipo<>(6, "Equipos no encontrados", equipos, links));
            }
        } catch (Exception e) {
            CustomResponseEquipo<List<Equipo>> response = new CustomResponseEquipo<>(8, "Error interno de servidor", equipos, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Obtener equipo por Id
    @GetMapping("/{id}")
    public ResponseEntity<CustomResponseEquipo<Equipo>> getEquipoById(@PathVariable Long id) {
        Optional<Equipo> equipo = null;
        CustomResponseEquipo<Equipo> response = null;
        Link allEquiposLink = linkTo(EquipoController.class).withSelfRel();
        List<Link> links = List.of(allEquiposLink);
        try {
            equipo = equipoService.obtenerEquipoPorId(id);
            if (equipo.isPresent()) {
                response = new CustomResponseEquipo<>(1, "Equipo encontrado", equipo.get(), links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                response = new CustomResponseEquipo<>(0, "Equipo no encontrado", null, links);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response = new CustomResponseEquipo<>(0, "Error interno de servidor", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Guardar Equipo
    @PostMapping
    public ResponseEntity<CustomResponseEquipo<Equipo>> crearEquipo(@RequestBody Equipo equipo) {
        Link allClientsLink = linkTo(EquipoController.class).withSelfRel();
        List<Link> links = List.of(allClientsLink);
        try {
            Equipo equipoEntity = equipoService.guardarEquipo(equipo);
            if (equipoEntity != null) {
                CustomResponseEquipo<Equipo> response = new CustomResponseEquipo<>(1, "Equipo creado", equipoEntity, links);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponseEquipo<>(6, "Equipo no encontrado", equipoEntity, links));
            }
        } catch (Exception e) {
            CustomResponseEquipo<Equipo> response = new CustomResponseEquipo<>(8, "Error interno de servidor", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Eliminar Equipo
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponseEquipo<Equipo>> deleteEquipoById(@PathVariable Long id) {
        Optional<Equipo> equipo = null;
        CustomResponseEquipo<Equipo> response = null;
        Link allEquiposLink = linkTo(EquipoController.class).withSelfRel();
        List<Link> links = List.of(allEquiposLink);

        try {
            equipo = equipoService.obtenerEquipoPorId(id);
            if (equipo.isPresent()) {
                equipoService.deleteEquipo(id);
                response = new CustomResponseEquipo<>(1, "Equipo eliminado", null, links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                response = new CustomResponseEquipo<>(0, "Equipo no encontrado", null, links);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response = new CustomResponseEquipo<>(500, "Error interno de servidor", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Actualizar equipo por id
    @PutMapping("/{id}")
    public ResponseEntity<CustomResponseEquipo<Equipo>> updateEquipo(@RequestBody Equipo equipo, @PathVariable Long id) {
        Link allEquiposLink = linkTo(EquipoController.class).withSelfRel();
        List<Link> links = List.of(allEquiposLink);
        try {
            equipo.setId(id);
            if (equipoService.obtenerEquipoPorId(id).isPresent()) {
                Equipo equipoEntity = equipoService.actualizarEquipo(equipo);
                CustomResponseEquipo<Equipo> response = new CustomResponseEquipo<>(1, "Equipo actualizado", equipoEntity, links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }else{
                CustomResponseEquipo<Equipo> response = new CustomResponseEquipo<>(0, "Equipo no encontrado", null, links);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        }catch (Exception e) {
            CustomResponseEquipo<Equipo> response = new CustomResponseEquipo<>(500, "Error interno de servidor", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
