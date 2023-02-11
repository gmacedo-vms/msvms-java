package com.mitocode.service.impl;

import com.mitocode.model.Cliente;
import com.mitocode.repo.IClienteRepo;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl extends CRUDImpl<Cliente, String> implements IClienteService {

  @Autowired private IClienteRepo clienteRepo;

  @Override
  protected IGenericRepo<Cliente, String> getRepositorio() {
    return clienteRepo;
  }
}
