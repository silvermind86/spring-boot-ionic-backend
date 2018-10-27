package com.jonasmagno.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jonasmagno.cursomc.domain.ItemPedido;
import com.jonasmagno.cursomc.repositories.ItemPedidoRepository;

@Service
public class ItemPedidoService {

	@Autowired
	private ItemPedidoRepository repo;
	
	public List<ItemPedido> salvar(List<ItemPedido> itensPedido) {
		return repo.saveAll(itensPedido);
	}
	
	

}
