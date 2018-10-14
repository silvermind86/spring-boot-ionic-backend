package com.jonasmagno.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jonasmagno.cursomc.domain.Pedido;
import com.jonasmagno.cursomc.repositories.PedidoRepository;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;
	
	public List<Pedido> salvar(List<Pedido> pedidos) {
		return repo.saveAll(pedidos);
	}

}
