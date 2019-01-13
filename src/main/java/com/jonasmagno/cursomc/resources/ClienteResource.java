package com.jonasmagno.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jonasmagno.cursomc.domain.Cliente;
import com.jonasmagno.cursomc.dto.ClienteDTO;
import com.jonasmagno.cursomc.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService clienteService;
	
	@RequestMapping(value= "/{id}",method=RequestMethod.GET)
	private ResponseEntity<Cliente> find(@PathVariable Integer id) {
		Cliente cliente = clienteService.buscar(id);
		return ResponseEntity.ok().body(cliente);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteDTO objDTO){
		Cliente obj = clienteService.fromDTO(objDTO);
		Cliente categoria = clienteService.inserir(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			.buildAndExpand(categoria.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDTO, @PathVariable Integer id){
		Cliente obj = clienteService.fromDTO(objDTO);
		clienteService.buscar(id);
		obj.setId(id);
		clienteService.atualizar(obj);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		
		clienteService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> findAll() {
		
		List<Cliente> lista = clienteService.buscarTodos();
		List<ClienteDTO> categorias = lista.stream().map(
			Obj -> new ClienteDTO(Obj)).collect(Collectors.toList());
		if (categorias == null) {
			return ResponseEntity.unprocessableEntity().body(null);
		}
		return ResponseEntity.ok().body(categorias);
	}
	
	@RequestMapping(value="/page",method=RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage(
		@RequestParam(value="page",defaultValue="0") Integer pagina, 
		@RequestParam(value="linesPerPage",defaultValue="24") Integer linhasPorPagina, 
		@RequestParam(value="orderBy",defaultValue="nome") String ordenar, 
		@RequestParam(value="direction",defaultValue="ASC") String sentido) {
		
		Page<Cliente> lista = clienteService.buscaPagina(pagina, linhasPorPagina, ordenar, sentido);
		Page<ClienteDTO> categorias = lista.map(Obj -> new ClienteDTO(Obj));
		if (categorias == null) {
			return ResponseEntity.unprocessableEntity().body(null);
		}
		return ResponseEntity.ok().body(categorias);
	}
}
