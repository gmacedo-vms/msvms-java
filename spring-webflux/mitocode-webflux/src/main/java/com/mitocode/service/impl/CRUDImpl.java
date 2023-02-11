package com.mitocode.service.impl;

import com.mitocode.repo.IGenericRepo;
import com.mitocode.service.ICRUD;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class CRUDImpl<T,ID> implements ICRUD<T, ID> {

    protected abstract IGenericRepo<T, ID> getRepositorio();

    @Override
    public Mono<T> registrar(T t) {
        return getRepositorio().save(t);
    }

    @Override
    public Mono<T> actualizar(T t) {
        return getRepositorio().save(t);
    }

    @Override
    public Flux<T> listar() {
        return getRepositorio().findAll();
    }

    @Override
    public Mono<T> listarPorId(ID id) {
        return getRepositorio().findById(id);
    }

    @Override
    public Mono<Void> eliminar(ID id) {
        return getRepositorio().deleteById(id);
    }
}
