package com.duberlyguarnizo.designartifacts.controller;

import com.duberlyguarnizo.designartifacts.model.Admin;
import com.duberlyguarnizo.designartifacts.repository.AdminRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/admin")
@Log
public class AdminAPIController {
    private final AdminRepository adminRepository;

    public AdminAPIController(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Operation(summary = "Listar los administradores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de administradores",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Admin.class))}),
            @ApiResponse(responseCode = "204", description = "Solicitud exitosa pero no hay administradores")})
    @GetMapping("/all")
    public ResponseEntity<List<Admin>> getAllAdmins() {
        List<Admin> result = adminRepository.findAll();
        if (result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Obtener datos de un administrador por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Administrador encontrado",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Admin.class))}),
            @ApiResponse(responseCode = "400", description = "Id inválido (tal vez no es un número?)",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No se ha encontrado a un administrador con ese id",
                    content = @Content)})
    @GetMapping("/id/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable("id") Long id) {
        return adminRepository.findById(id).orElse(null) != null ?
                new ResponseEntity<>(adminRepository.findById(id).orElse(null), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Listar todos los administradores activos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Administradores activos encontrados",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Admin.class))}),
            @ApiResponse(responseCode = "204", description = "Solicitud exitosa pero no hay administradores activos"),
            @ApiResponse(responseCode = "400", description = "Error en la petición desde el cliente",
                    content = @Content)})
    @GetMapping("/active")
    public ResponseEntity<List<Admin>> getActiveAdmins() {
        List<Admin> result = adminRepository.findByActiveIsTrue();
        if (result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Listar todos los administradores inactivos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Administradores inactivos encontrados",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Admin.class))}),
            @ApiResponse(responseCode = "204", description = "Solicitud exitosa pero no hay administradores inactivos"),
            @ApiResponse(responseCode = "400", description = "Error en la petición desde el cliente",
                    content = @Content)})
    @GetMapping("/inactive")
    public ResponseEntity<List<Admin>> getInactiveAdmins() {
        List<Admin> result = adminRepository.findByActiveIsFalse();
        if (result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Listar todos los administradores por nombre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Administradores con coincidencia en el nombre encontrados",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Admin.class))}),
            @ApiResponse(responseCode = "400", description = "Error en la petición desde el cliente (tal vez el nombre está vacío o mal formado?)",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Error 404, no se han encontrado administradores con el nombre indicado",
                    content = @Content)})
    @GetMapping("/name/{name}")
    public ResponseEntity<List<Admin>> getAdminsByName(@PathVariable("name") String name) {
        List<Admin> result = adminRepository.findByNameContaining(name);
        if (result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Crear un nuevo administrador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Administrador creado",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Admin.class))}),
            @ApiResponse(responseCode = "400", description = "Error en la petición desde el cliente (headers mal formados?)",
                    content = @Content)})
    @PostMapping("/create")
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin) {
        adminRepository.save(admin);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar un administrador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Administrador actualizado",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Admin.class))}),
            @ApiResponse(responseCode = "404", description = "No se ha encontrado al administrador con el id indicado",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Error en la petición desde el cliente (tal vez Ids no se corresponden?)",
                    content = @Content)})
    @PutMapping("/update/{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable("id") Long adminId, @RequestBody Admin admin) {
        if (adminRepository.findById(adminId).orElse(null) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!adminId.equals(admin.getAdminId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        adminRepository.save(admin);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Borrar un administrador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Administrador con id indicado borrado",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Admin.class))}),
            @ApiResponse(responseCode = "404", description = "No se ha encontrado al administrador con el id indicado",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Error en la petición desde el cliente (headers mal formados?)",
                    content = @Content)})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Admin> deleteAdmin(@PathVariable("id") Long id) {
        if (adminRepository.findById(id).orElse(null) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        adminRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
