package com.jonasmagno.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jonasmagno.cursomc.domain.Categoria;
import com.jonasmagno.cursomc.domain.Pedido;
import com.jonasmagno.cursomc.repositories.PedidoRepository;
import com.jonasmagno.cursomc.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;
	
	public List<Pedido> salvar(List<Pedido> pedidos) {
		return repo.saveAll(pedidos);
	}
	
	public Pedido buscar(Integer id) {
	 	Optional<Pedido> ped = repo.findById(id);
	 	return ped.orElseThrow(() -> new ObjectNotFoundException(
			"Obejto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}

}
