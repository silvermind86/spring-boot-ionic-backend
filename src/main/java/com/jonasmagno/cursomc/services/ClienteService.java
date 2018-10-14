package com.jonasmagno.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jonasmagno.cursomc.domain.Cliente;
import com.jonasmagno.cursomc.repositories.ClienteRepository;
import com.jonasmagno.cursomc.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	public Cliente salvar(Cliente cliente) {
		return repo.save(cliente);
	}
	
	public Cliente buscar(Integer id) {
		Optional<Cliente> cliente = repo.findById(id);
		return cliente.orElseThrow(() -> new ObjectNotFoundException(
			"Cliente não encontrado, Id: "
			+ id + ", Tipo: " + Cliente.class.getName()));
	}

}
