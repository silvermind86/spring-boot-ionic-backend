package com.jonasmagno.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.jonasmagno.cursomc.domain.Cliente;
import com.jonasmagno.cursomc.dto.ClienteDTO;
import com.jonasmagno.cursomc.repositories.ClienteRepository;
import com.jonasmagno.cursomc.services.exception.DataIntegrityException;
import com.jonasmagno.cursomc.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	public Cliente salvar(Cliente cliente) {
		Cliente novoCLiente = buscar(cliente.getId());
		atualizaDados(novoCLiente, cliente);
		return repo.save(novoCLiente);
	}
	
	private void atualizaDados(Cliente novoCLiente, Cliente cliente) {
		novoCLiente.setNome(cliente.getNome());
		novoCLiente.setEmail(cliente.getEmail());
	}

	public Cliente buscar(Integer id) {
		Optional<Cliente> cliente = repo.findById(id);
		return cliente.orElseThrow(() -> new ObjectNotFoundException(
			"Cliente não encontrado, Id: "
			+ id + ", Tipo: " + Cliente.class.getName()));
	}
	
	public List<Cliente> salvar(List<Cliente> clientes) {
		return repo.saveAll(clientes);
	}
	
	public void delete(Integer id) {
		buscar(id);
		try {
			repo.deleteById(id);
		}catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException(
				"Não é possível excluir pois há entidades relacionadas.");
		}
	}

	public List<Cliente> buscarTodos() {
		return repo.findAll();
	}
	
	public Page<Cliente> buscaPagina(
		Integer pagina, Integer linhasPorPagina, String ordenar, String sentido) {
		
		PageRequest page = PageRequest.of(
			pagina, linhasPorPagina, Direction.valueOf(sentido), ordenar);
		return repo.findAll(page);
	}

	/** 
	 * Retorna uma categoria criada de um DTO.
	 * 
	 * @param objDTO
	 * @return
	 */
	public Cliente fromDTO(ClienteDTO objDTO) {		
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null);
	}
	
}
