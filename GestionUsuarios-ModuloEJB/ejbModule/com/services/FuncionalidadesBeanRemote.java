package com.services;

import java.util.List;
import javax.ejb.Remote;
import com.entities.Funcionalidades;

@Remote
public interface FuncionalidadesBeanRemote {
	List<Funcionalidades> SelectTasksWithRole(String roleName);	//Metodo para obtener un List con las Funcionalidades asociadas a un Rol con determinado nombre
	List<Funcionalidades> Select();								//Metodo para obtener un List con todas las Funcionalidades
}
