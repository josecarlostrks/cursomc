package com.carlostrks.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carlostrks.cursomc.domain.Cliente;
import com.carlostrks.cursomc.repositories.ClienteRepository;
import com.carlostrks.cursomc.services.excepions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	
	public Cliente buscar(Integer id){
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: "+id+", Tipo: "+Cliente.class.getName()));
	}

}
