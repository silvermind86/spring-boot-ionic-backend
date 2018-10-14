package com.jonasmagno.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jonasmagno.cursomc.domain.Pagamento;
import com.jonasmagno.cursomc.repositories.PagamentoRepository;

@Service
public class PagamentoService {

	@Autowired
	private PagamentoRepository repo;
	
	public List<Pagamento> salvar(List<Pagamento> pagamentos) {
		return repo.saveAll(pagamentos);
	}

}
