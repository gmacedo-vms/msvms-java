package com.mitocode.service.impl;

import com.mitocode.model.Factura;
import com.mitocode.repo.IFacturaRepo;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.service.IFacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl extends CRUDImpl<Factura, String> implements IFacturaService {

  @Autowired private IFacturaRepo facturaRepo;

  @Override
  protected IGenericRepo<Factura, String> getRepositorio() {
    return facturaRepo;
  }
}
