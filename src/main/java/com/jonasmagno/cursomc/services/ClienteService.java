package com.jonasmagno.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jonasmagno.cursomc.domain.Cidade;
import com.jonasmagno.cursomc.domain.Cliente;
import com.jonasmagno.cursomc.domain.Endereco;
import com.jonasmagno.cursomc.domain.enums.TipoCliente;
import com.jonasmagno.cursomc.dto.ClienteDTO;
import com.jonasmagno.cursomc.dto.ClienteNewDTO;
import com.jonasmagno.cursomc.repositories.CidadeRepository;
import com.jonasmagno.cursomc.repositories.ClienteRepository;
import com.jonasmagno.cursomc.repositories.EnderecoRepository;
import com.jonasmagno.cursomc.services.exception.DataIntegrityException;
import com.jonasmagno.cursomc.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Transactional
	public Cliente inserir(Cliente cliente) {
		cliente.setId(null);
		Cliente objCliente = repo.save(cliente);
		enderecoRepository.saveAll(objCliente.getEnderecos());
		return objCliente;
	}
	
	public Cliente atualizar(Cliente cliente) {
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
	
	public Cliente fromDTO(ClienteNewDTO objDTO) {
		
		Cliente cli = new Cliente(
			null, objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfOuCnpj(), TipoCliente.toEnum(objDTO.getTipo()));
		Cidade cidade = new Cidade(objDTO.getCidadeId(), null, null);
		Endereco endereco = new Endereco(
			null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getComplemento(), objDTO.getBairro(), objDTO.getCep(), cli, cidade);
		cli.getEnderecos().add(endereco);
		cli.getTelefones().add(objDTO.getTelefone1());
		if (objDTO.getTelefone2() != null) {
			cli.getTelefones().add(objDTO.getTelefone2());
		}
		if (objDTO.getTelefone3() != null) {
			cli.getTelefones().add(objDTO.getTelefone3());
		}
		return cli;
	}
	
}
