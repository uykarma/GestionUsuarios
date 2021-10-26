package com.services;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.*;
import com.entities.Usuario;
import com.exceptions.ServiciosException;

/**
 * Session Bean implementation class Usuarios
 */
@Stateless
public class UsuariosBean implements UsuariosBeanRemote {

	@PersistenceContext
	private EntityManager em;
	
    public UsuariosBean() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public void CreateUser(Usuario user) throws ServiciosException {
		try {
			if(!AlredyExists(user)) {	//Antes de Crear el Usuario se verifica que no exista con otro ID
				em.persist(user);
				em.flush();
			}
			else 
				throw new ServiciosException("Ya existe un usuario con ese documento");
		} catch(PersistenceException e) { 
			e.printStackTrace();
			throw new ServiciosException("No se pudo crear el Usuario: " + user.getNombre()); 
		}
		
	}

	@Override
	public void UpdateUser(Usuario user, String oldUser) throws ServiciosException {
		try {			 
			Object oldDocument = null;
			if (!user.getDocumento().equals(oldDocument)) { //Si cambio el documento
				if(!AlredyExists(user)) {	//Antes de Modificar el Usuario se verifica que no exista con otro ID
					em.merge(user);
					em.flush();
				} 
				else
					throw new ServiciosException("Ya existe un usuario con ese documento");
			} else {
				em.merge(user);
				em.flush();
			}
		} catch(PersistenceException e) { 
			e.printStackTrace();
			throw new ServiciosException("No se pudo actualizar el Usuario: " + user.getNombre()); 
		}
		
	}

	@Override
	public void DeleteUser(Long id) throws ServiciosException {
		 try {
			 Usuario user = em.find(Usuario.class, id);
			 em.remove(user);
			 em.flush();
		 } catch(PersistenceException e){ 
			 throw new ServiciosException("No se pudo eliminar el Usuario"); 
			 }
	}

	@Override
	public List<Usuario> SelectUsers() {
		TypedQuery<Usuario> query = 
				em.createNamedQuery("Usuario.findAll", Usuario.class);
		
		return query.getResultList();
	}

	@Override
	public Long GetIDUser(String nombUsuario) {
		Object documento = null;
		TypedQuery<Usuario> query = 
				em.createQuery("SELECT u FROM USUARIOS u WHERE u.documento LIKE :documento", Usuario.class).setParameter("documento", documento);
		
		if(query.getResultList().size() > 0)
			return query.getResultList().get(0).getIdUsuario();
		else
			return -1L;	//Si el Usuario no existe se devuelve '-1' a modo de señal de error
	
	}

	@Override
	public Usuario Login(String nombUsuario, String contrasenia) {
		Object clave = null;
		Object documento = null;
		TypedQuery<Usuario> query = 
				em.createQuery("SELECT u FROM USUARIOS u WHERE u.documento LIKE :documento AND u.clave LIKE :clave", Usuario.class).setParameter("documento", documento).setParameter("clave", clave);
		
		if(query.getResultList().size() > 0)
			return query.getResultList().get(0);
		else
			return null;
	}

	//Metodo que verifica si ya existe un Usuario con determinado nombre de usuario
		private boolean AlredyExists(Usuario user) throws ServiciosException {
			if(GetIDUser(user.getDocumento()) == -1) 
				return false;
			else
				return true;
		}
}
