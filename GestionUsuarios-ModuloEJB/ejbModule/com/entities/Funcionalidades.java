package com.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToMany;


/**
 * The persistent class for the FUNCIONALIDADES database table.
 * 
 */
@Entity
@NamedQuery(name="Funcionalidades.findAll", query="SELECT f FROM Funcionalidades f")
public class Funcionalidades implements Serializable {
	private static final long serialVersionUID = 1L;

	@GeneratedValue
	@Id
	@Column(name="ID_FUNCIONALIDAD")
	private long idFuncionalidad;

	private String descripcion;

	private String nombre;

	//bi-directional many-to-many association to Role
	@ManyToMany(mappedBy="funcionalidades")
	private List<Roles> roles;

	public Funcionalidades() {
	}

	public long getIdFuncionalidad() {
		return this.idFuncionalidad;
	}

	public void setIdFuncionalidad(long idFuncionalidad) {
		this.idFuncionalidad = idFuncionalidad;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Roles> getRoles() {
		return this.roles;
	}

	public void setRoles(List<Roles> roles) {
		this.roles = roles;
	}

}