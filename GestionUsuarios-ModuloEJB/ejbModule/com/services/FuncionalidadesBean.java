package com.services;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.*;

import com.entities.Funcionalidades;

/**
 * Session Bean implementation class Funcionalidades
 */
@Stateless
public class FuncionalidadesBean implements FuncionalidadesBeanRemote {
	@PersistenceContext
	private EntityManager em;
	
    public FuncionalidadesBean() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public List<Funcionalidades> SelectTasksWithRole(String roleName) {
		TypedQuery<Funcionalidades> query = em.createQuery("SELECT f FROM Funcionalidades f "
				+ "INNER JOIN f.roles r "
				+ "WHERE r.nombre = :roleName", Funcionalidades.class).setParameter("roleName", roleName);
		
		return query.getResultList();
	}

	@Override
	public List<Funcionalidades> Select() {
		TypedQuery<Funcionalidades> query = 
				em.createNamedQuery("Funcionalidades.findAll", Funcionalidades.class);
		
		return query.getResultList();
	}

}
