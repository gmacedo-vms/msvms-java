package com.mitocode.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Size;

@Document(collection = "clientes")
public class Cliente {

  @Id private String id;

  @Size(min = 3)
  @Field(name = "nombres")
  private String nombres;

  @Field(name = "apellidos")
  private String apellidos;

  @Field(name = "fechaNac")
  @DateTimeFormat(pattern = "dd/MM/yyyy")
  private String fechaNac;

  @Field(name = "urlfoto")
  private String urlfoto;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getNombres() {
    return nombres;
  }

  public void setNombres(String nombres) {
    this.nombres = nombres;
  }

  public String getApellidos() {
    return apellidos;
  }

  public void setApellidos(String apellidos) {
    this.apellidos = apellidos;
  }

  public String getUrlfoto() {
    return urlfoto;
  }

  public void setUrlfoto(String urlfoto) {
    this.urlfoto = urlfoto;
  }

  public String getFechaNac() {
    return fechaNac;
  }

  public void setFechaNac(String fechaNac) {
    this.fechaNac = fechaNac;
  }
}
