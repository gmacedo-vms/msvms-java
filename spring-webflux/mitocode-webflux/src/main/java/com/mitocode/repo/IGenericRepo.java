package com.mitocode.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean // Interdaz que actue como una abastraccion, mas no se debe implmentado por mongo
public interface IGenericRepo<T, ID> extends ReactiveMongoRepository<T, ID> {}
