package com.duberlyguarnizo.designartifacts.controller;

import com.duberlyguarnizo.designartifacts.model.Visit;
import com.duberlyguarnizo.designartifacts.repository.VisitRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/visit")
public class VisitController {
    private final VisitRepository visitRepository;

    public VisitController(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    @Operation(summary = "Listado de visitas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todas las visitas",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Visit.class))}),
            @ApiResponse(responseCode = "204", description = "Solitud exitosa pero no hay visitas",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Error en la petición desde el cliente",
                    content = @Content)})
    @GetMapping("all")
    public ResponseEntity<List<Visit>> getAllVisits() {
        List<Visit> result = visitRepository.findAll();
        if (result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Recuperación de visita única por Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Visita con el Id especificado recuperada",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Visit.class))}),
            @ApiResponse(responseCode = "404", description = "No se encuentra la visita con el Id indicado",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Error en la petición desde el cliente",
                    content = @Content)})
    @GetMapping("/id/{id}")
    public ResponseEntity<Visit> getVisitById(@PathVariable("id") Long id) {
        Visit result = visitRepository.findById(id).orElse(null);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Número de visitas que dieron clic en el botón de descargar un gráfico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conteo de número de visitas que descargaron un gráfico exitoso",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Visit.class))}),
            @ApiResponse(responseCode = "400", description = "Error en la petición desde el cliente",
                    content = @Content)})
    @GetMapping("downloaded-graphs")
    public ResponseEntity<Long> getTotalVisitorsDownloadedGraphs() {
        Long result = visitRepository.countByClickedDownloadGraphIsTrue();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Número de visitas que dieron clic en al botón de generar un gráfico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conteo de número de visitas que generaron un gráfico exitoso",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Visit.class))}),
            @ApiResponse(responseCode = "400", description = "Error en la petición desde el cliente",
                    content = @Content)})
    @GetMapping("generated-output")
    public ResponseEntity<Long> getTotalVisitorsGeneratedOutput() {
        Long result = visitRepository.countByClickedGenerateOutputIsTrue();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Número de visitas que seleccionaron un tipo de gráfico del selector")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conteo de número de visitas que seleccionaron un tipo de gráfico exitoso",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Visit.class))}),
            @ApiResponse(responseCode = "400", description = "Error en la petición desde el cliente",
                    content = @Content)})
    @GetMapping("selected-graph-type")
    public ResponseEntity<Long> getTotalVisitorsSelectedGraphType() {
        Long result = visitRepository.countByClickedSelectGraphTypeIsTrue();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Número de visitas que seleccionaron un formato de salida para un gráfico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conteo de número de visitas que seleccionaron un tipo de salida exitoso",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Visit.class))}),
            @ApiResponse(responseCode = "400", description = "Error en la petición desde el cliente",
                    content = @Content)})
    @GetMapping("selected-output")
    public ResponseEntity<Long> getTotalVisitorsSelectedOutput() {
        Long result = visitRepository.countByClickedSelectOutputIsTrue();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Número de visitas que dieron clic a la opción de compartir o copiar link de un gráfico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conteo de número de visitas que copiaron el link a un gráfico exitoso",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Visit.class))}),
            @ApiResponse(responseCode = "400", description = "Error en la petición desde el cliente",
                    content = @Content)})
    @GetMapping("copied-share-link")
    public ResponseEntity<Long> getTotalVisitorsCopiedShareLink() {
        Long result = visitRepository.countByCopiedShareLinkIsTrue();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Número de visitas que llegaron a la web por un link compartido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conteo de número de visitas que llegaron por link compartido exitoso",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Visit.class))}),
            @ApiResponse(responseCode = "400", description = "Error en la petición desde el cliente",
                    content = @Content)})
    @GetMapping("from-share-link")
    public ResponseEntity<Long> getTotalVisitorsFromShareLink() {
        Long result = visitRepository.countByFromShareLinkIsTrue();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Número de visitas que escribieron contenido en los cuadros para generar un gráfico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conteo de número de visitas que escribieron contenido exitoso",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Visit.class))}),
            @ApiResponse(responseCode = "400", description = "Error en la petición desde el cliente",
                    content = @Content)})
    @GetMapping("typed-graph-content")
    public ResponseEntity<Long> getTotalVisitorsTypedGraphContent() {
        Long result = visitRepository.countByTypedGraphContentIsTrue();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Número de visitas que tuvieron alguna interacción con la web")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conteo de número de visitas que tuvieron alguna interacción con la web exitoso",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Visit.class))}),
            @ApiResponse(responseCode = "400", description = "Error en la petición desde el cliente",
                    content = @Content)})
    @GetMapping("had-interactions")
    public ResponseEntity<Long> getTotalVisitorsHadInteractions() {
        Long result = visitRepository.countByHadInteractionsIsTrue();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Número de visitas a la web que eran administradores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conteo de número de visitas en la web que eran administradores existoso",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Visit.class))}),
            @ApiResponse(responseCode = "400", description = "Error en la petición desde el cliente",
                    content = @Content)})
    @GetMapping("were-admins")
    public ResponseEntity<Long> getTotalVisitorsWereAdmin() {
        Long result = visitRepository.countByWasAdminIsTrue();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Número de visitas en la web por contenido de gráfico, por Id de contenido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conteo de número de visitas que llegaron por contenido especificado exitoso",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Visit.class))}),
            @ApiResponse(responseCode = "404", description = "No se encontró el id del contenido especificado",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Visit.class))}),
            @ApiResponse(responseCode = "400", description = "Error en la petición desde el cliente",
                    content = @Content)})
    @GetMapping("graph/{graphId}")
    public ResponseEntity<Long> getTotalVisitorsByGraphId(@PathVariable("graphId") Long graphId) {
        Long result = visitRepository.countByGraphLink_GraphContent_ContentId(graphId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Número de visitas en la web por rango de fechas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conteo de número de visitas que llegaron en rango de fechas especificado exitoso",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Visit.class))}),
            @ApiResponse(responseCode = "204", description = "Sin contenido en el rango de fechas especificado",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Visit.class))}),
            @ApiResponse(responseCode = "400", description = "Error en la petición desde el cliente",
                    content = @Content)})
    @GetMapping("date/{start}/{end}")
    public ResponseEntity<List<Visit>> getVisitsInDateRange(@PathVariable("start") LocalDate start, @PathVariable("end") LocalDate end) {
        List<Visit> result = visitRepository.findByVisitDateTimeBetween(start.atStartOfDay(), end.plusDays(1).atStartOfDay());
        if (result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Creación de una visita a la web")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registro de visita creado",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Visit.class))}),
            @ApiResponse(responseCode = "400", description = "Error en la petición desde el cliente",
                    content = @Content)})
    @PostMapping("/create")
    public ResponseEntity<Visit> createVisit(@RequestBody Visit visit) {
        Visit result = visitRepository.save(visit);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar una visita mediante PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Visita actualizada",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Visit.class))}),
            @ApiResponse(responseCode = "404", description = "No se ha encontrado una visita con el id indicado",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Error en la petición desde el cliente (tal vez los Id no corresponden?)",
                    content = @Content)})

    @PutMapping("/update/{id}")
    public ResponseEntity<Visit> updateGraphContent(@PathVariable("id") Long visitId, @RequestBody Visit visit) {
        if (visitRepository.findById(visitId).orElse(null) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!visitId.equals(visit.getVisitId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        visitRepository.save(visit);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Borrar una visita mediante DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Visita con id indicado borrado",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Visit.class))}),
            @ApiResponse(responseCode = "404", description = "No se ha encontrado una visita con el id indicado",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Error en la petición desde el cliente (Tal vez los Id no se corresponden?)",
                    content = @Content)})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Visit> deleteGraphContent(@PathVariable("id") Long id) {
        if (visitRepository.findById(id).orElse(null) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        visitRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
