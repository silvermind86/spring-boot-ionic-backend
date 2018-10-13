package com.jonasmagno.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jonasmagno.cursomc.domain.Cliente;
import com.jonasmagno.cursomc.repositories.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	public Cliente salvar(Cliente cliente) {
		return repo.save(cliente);
	}

}
