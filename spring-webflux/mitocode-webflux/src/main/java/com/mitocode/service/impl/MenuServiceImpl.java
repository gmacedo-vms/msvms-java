package com.mitocode.service.impl;

import com.mitocode.model.Menu;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.repo.IMenuRepo;
import com.mitocode.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl extends CRUDImpl<Menu, String> implements IMenuService {

  @Autowired private IMenuRepo menuRepo;

  @Override
  protected IGenericRepo<Menu, String> getRepositorio() {
    return menuRepo;
  }
}
