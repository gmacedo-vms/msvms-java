package com.mitocode.controller;

import com.mitocode.model.Plato;
import com.mitocode.service.IPlatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Links;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;
import static reactor.function.TupleUtils.function;

/** Clase con enfoque de enotaciones */
@RestController
@RequestMapping("/platos")
public class PlatoController {

  @Autowired private IPlatoService platoService;

  @GetMapping
  public Mono<ResponseEntity<Flux<Plato>>> list() {

    Flux<Plato> listaPlatos = platoService.listar();
    return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(listaPlatos));
  }

  @GetMapping("/{id}")
  public Mono<ResponseEntity<Plato>> listById(@PathVariable("id") String id) {
    return platoService
        .listarPorId(id)
        .map(
            platoEncontrado ->
                ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(platoEncontrado))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PostMapping
  public Mono<ResponseEntity<Plato>> registrar(
      @Valid @RequestBody Plato requestBody, final ServerHttpRequest request) {
    return platoService
        .registrar(requestBody)
        .map(
            response ->
                ResponseEntity.created(
                        URI.create(
                            request.getURI().toString().concat("/").concat(response.getId())))
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response));
  }

  @PutMapping("/{id}")
  public Mono<ResponseEntity<Plato>> modificar(
      @RequestBody Plato plato, @PathVariable("id") String id) {

    Mono<Plato> nuevoDatosPlato = Mono.just(plato);
    Mono<Plato> datosPlatos = platoService.listarPorId(id);

    return datosPlatos
        .zipWith(
            nuevoDatosPlato,
            (datosActuales, nuevosDatos) -> {
              datosActuales.setId(id);
              datosActuales.setNombre(nuevosDatos.getNombre());
              datosActuales.setPrecio(nuevosDatos.getPrecio());
              datosActuales.setEstado(nuevosDatos.getEstado());
              return datosActuales;
            })
        .flatMap(plato1 -> platoService.actualizar(plato1))
        .map(
            datosActualizados ->
                ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(datosActualizados))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<Void>> eliminar(@PathVariable("id") String id) {

    return platoService
        .listarPorId(id)
        .flatMap(
            plato -> {
              return platoService
                  .eliminar(plato.getId())
                  .then(Mono.just((new ResponseEntity<Void>(HttpStatus.NO_CONTENT))));
            })
        .defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
  }

  @GetMapping("/hateoas/{id}")
  public Mono<EntityModel<Plato>> listarHateoasPorId(@PathVariable("id") String id) {
    Mono<Link> linkMono =
        linkTo(methodOn(PlatoController.class).listById(id)).withSelfRel().toMono();
    Mono<Link> linkMonoTwo =
        linkTo(methodOn(PlatoController.class).listById(id)).withSelfRel().toMono();
    // Para mostrar un enlace
    /*    return platoService
    .listarPorId(id)
    .zipWith(linkMono, (plato, link) -> EntityModel.of(plato, link));*/

    // Para mostrar dos enlaes
    return linkMono
        .zipWith(linkMonoTwo)
        .map(function((left, right) -> Links.of(left, right)))
        .zipWith(platoService.listarPorId(id), (links, plato) -> EntityModel.of(plato, links));
  }
}
