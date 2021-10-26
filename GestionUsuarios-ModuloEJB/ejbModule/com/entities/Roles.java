package com.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;


/**
 * The persistent class for the "ROLES" database table.
 * 
 */
@Entity
@Table(name="\"ROLES\"")
@NamedQuery(name="Roles.findAll", query="SELECT r FROM Roles r")
public class Roles implements Serializable {
	private static final long serialVersionUID = 1L;

	@GeneratedValue
	@Id
	@Column(name="ID_ROL")
	private long idRol;

	private String descripcion;

	private String nombre;

	//bi-directional many-to-many association to Funcionalidades
	@ManyToMany
	@JoinTable(
		name="ROL_FUNCION"
		, joinColumns={
			@JoinColumn(name="ID_ROL")
			}
		, inverseJoinColumns={
			@JoinColumn(name="ID_FUNCION")
			}
		)
	private List<Funcionalidades> funcionalidades;

	//bi-directional many-to-one association to Usuario
	@OneToMany(mappedBy="roles")
	private List<Usuario> usuarios;

	public Roles() {
	}

	public long getIdRol() {
		return this.idRol;
	}

	public void setIdRol(long idRol) {
		this.idRol = idRol;
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

	public List<Funcionalidades> getFuncionalidades() {
		return this.funcionalidades;
	}

	public void setFuncionalidades(List<Funcionalidades> funcionalidades) {
		this.funcionalidades = funcionalidades;
	}

	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario addUsuario(Usuario usuario) {
		getUsuarios().add(usuario);
		usuario.setRoles(this);

		return usuario;
	}

	public Usuario removeUsuario(Usuario usuario) {
		getUsuarios().remove(usuario);
		usuario.setRoles(null);

		return usuario;
	}

}