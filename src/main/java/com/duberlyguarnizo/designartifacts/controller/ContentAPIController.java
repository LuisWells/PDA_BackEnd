package com.duberlyguarnizo.designartifacts.controller;

import com.duberlyguarnizo.designartifacts.model.GraphContent;
import com.duberlyguarnizo.designartifacts.repository.ContentRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/content")
public class ContentAPIController {
    private final ContentRepository contentRepository;

    public ContentAPIController(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    @Operation(summary = "Listar todos los contenidos de gráficos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de contenido de gráficos",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GraphContent.class))}),
            @ApiResponse(responseCode = "204", description = "Solicitud exitosa pero no hay contenidos en la base de datos",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Error en la petición desde el cliente",
                    content = @Content)})
    @GetMapping("/all")
    public ResponseEntity<List<GraphContent>> getAllContent() {
        if (contentRepository.findAll().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(contentRepository.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "Detalle de contenido de gráfico por Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contenido de grafico con Id encontrado",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GraphContent.class))}),
            @ApiResponse(responseCode = "404", description = "No se ha encontrado contenido de gráfico con el Id especificado",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Error en la petición desde el cliente",
                    content = @Content)})
    @GetMapping("/id/{id}")
    public ResponseEntity<GraphContent> getContentById(@PathVariable("id") Long id) {
        Optional<GraphContent> result = contentRepository.findById(id);
        return result.map(graphContent -> new ResponseEntity<>(graphContent, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Detalles de contenido de gráfico por fecha")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de contenido de gráficos con fecha especificada",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GraphContent.class))}),
            @ApiResponse(responseCode = "204", description = "Solicitud exitosa pero no hay contenidos en la base de datos con la fecha especificada",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Error en la petición desde el cliente",
                    content = @Content)})
    @GetMapping("/date/{date}")
    public ResponseEntity<List<GraphContent>> getContentByDate(@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        List<GraphContent> result = contentRepository.findByCreationDateBetween(
                date.atStartOfDay(), date.plusDays(1).atStartOfDay()
        );
        if (result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Crear contenido de gráfico mediante POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Contenido de grafico creado",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GraphContent.class))}),
            @ApiResponse(responseCode = "400", description = "Error en la petición desde el cliente",
                    content = @Content)})
    @PostMapping("/create")
    public ResponseEntity<GraphContent> createContent(@RequestBody GraphContent graphContent) {
        GraphContent result = contentRepository.save(graphContent);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar un contenido de gráfico mediante PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contenido de gráfico actualizado",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GraphContent.class))}),
            @ApiResponse(responseCode = "404", description = "No se ha encontrado al contenido de gráfico con el id indicado",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Error en la petición desde el cliente (tal vez los Id no corresponden?)",
                    content = @Content)})

    @PutMapping("/update/{id}")
    public ResponseEntity<GraphContent> updateGraphContent(@PathVariable("id") Long contentId, @RequestBody GraphContent content) {
        if (contentRepository.findById(contentId).orElse(null) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!contentId.equals(content.getContentId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        contentRepository.save(content);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Borrar un contenido de gráfico mediante DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contenido de gráfico con id indicado borrado",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GraphContent.class))}),
            @ApiResponse(responseCode = "404", description = "No se ha encontrado al contenido de gráfico con el id indicado",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Error en la petición desde el cliente (Tal vez los Id no se corresponden?)",
                    content = @Content)})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GraphContent> deleteGraphContent(@PathVariable("id") Long id) {
        if (contentRepository.findById(id).orElse(null) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        contentRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
