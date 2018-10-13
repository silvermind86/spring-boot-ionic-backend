package com.jonasmagno.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jonasmagno.cursomc.domain.Endereco;
import com.jonasmagno.cursomc.repositories.EnderecoRepository;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository repo;
	
	public List<Endereco> salvar(List<Endereco> enderecos) {
		return repo.saveAll(enderecos);
	}

}
