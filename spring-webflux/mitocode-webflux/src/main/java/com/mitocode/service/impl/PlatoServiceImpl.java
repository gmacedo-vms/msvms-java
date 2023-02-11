package com.mitocode.service.impl;

import com.mitocode.model.Plato;
import com.mitocode.repo.IPlatoRepo;
import com.mitocode.service.IPlatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PlatoServiceImpl implements IPlatoService {

  @Autowired private IPlatoRepo platoRepo;

  @Override
  public Mono<Plato> registrar(Plato cliente) {
    return platoRepo.save(cliente);
  }

  @Override
  public Mono<Plato> actualizar(Plato cliente) {
    return platoRepo.save(cliente);
  }

  @Override
  public Flux<Plato> listar() {
    return platoRepo.findAll();
  }

  @Override
  public Mono<Plato> listarPorId(String id) {
    return platoRepo.findById(id);
  }

  @Override
  public Mono<Void> eliminar(String id) {
    return platoRepo.deleteById(id);
  }
}
