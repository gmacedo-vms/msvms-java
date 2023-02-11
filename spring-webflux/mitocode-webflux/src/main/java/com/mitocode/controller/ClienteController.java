package com.mitocode.controller;

import com.mitocode.model.Cliente;
import com.mitocode.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

/** Clase con enfoque de enotaciones */
@RestController
@RequestMapping("/clientes")
public class ClienteController {

  @Autowired private IClienteService clienteService;

  @GetMapping
  public Mono<ResponseEntity<Flux<Cliente>>> list() {

    Flux<Cliente> listaclientes = clienteService.listar();
    return Mono.just(
        ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(listaclientes));
  }

  @GetMapping("/{id}")
  public Mono<ResponseEntity<Cliente>> listById(@PathVariable("id") String id) {
    return clienteService
        .listarPorId(id)
        .map(
            clienteEncontrado ->
                ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(clienteEncontrado))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PostMapping
  public Mono<ResponseEntity<Cliente>> registrar(
      @RequestBody Cliente requestBody, final ServerHttpRequest request) {
    return clienteService
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
  public Mono<ResponseEntity<Cliente>> modificar(
      @RequestBody Cliente Cliente, @PathVariable("id") String id) {

    Mono<Cliente> nuevoDatoscliente = Mono.just(Cliente);
    Mono<Cliente> datosclientes = clienteService.listarPorId(id);

    return datosclientes
        .zipWith(
            nuevoDatoscliente,
            (datosActuales, nuevosDatos) -> {
              datosActuales.setId(id);
              datosActuales.setNombres(nuevosDatos.getNombres());
              datosActuales.setApellidos(nuevosDatos.getApellidos());
              datosActuales.setFechaNac(nuevosDatos.getFechaNac());
              datosActuales.setUrlfoto(nuevosDatos.getUrlfoto());
              return datosActuales;
            })
        .flatMap(cliente1 -> clienteService.actualizar(cliente1))
        .map(
            datosActualizados ->
                ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(datosActualizados))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<Void>> eliminar(@PathVariable("id") String id) {

    return clienteService
        .listarPorId(id)
        .flatMap(
            Cliente -> {
              return clienteService
                  .eliminar(Cliente.getId())
                  .then(Mono.just((new ResponseEntity<Void>(HttpStatus.NO_CONTENT))));
            })
        .defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
  }
}
