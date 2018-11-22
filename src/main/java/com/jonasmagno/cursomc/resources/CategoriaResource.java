package com.jonasmagno.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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

import com.jonasmagno.cursomc.domain.Categoria;
import com.jonasmagno.cursomc.dto.CategoriaDTO;
import com.jonasmagno.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService service;
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {
		
		Categoria categoria = service.buscar(id);
		if (categoria == null) {
			return ResponseEntity.unprocessableEntity().body(null);
		}
		return ResponseEntity.ok().body(categoria);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Categoria obj){
		obj.setId(null);
		Categoria categoria = service.salvar(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			.buildAndExpand(categoria.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody Categoria obj, @PathVariable Integer id){
		service.buscar(id);
		obj.setId(id);
		service.salvar(obj);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> findAll() {
		
		List<Categoria> lista = service.buscarTodos();
		List<CategoriaDTO> categorias = lista.stream().map(
			Obj -> new CategoriaDTO(Obj)).collect(Collectors.toList());
		if (categorias == null) {
			return ResponseEntity.unprocessableEntity().body(null);
		}
		return ResponseEntity.ok().body(categorias);
	}
	
	@RequestMapping(value="/page",method=RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> findPage(
		@RequestParam(value="page",defaultValue="0") Integer pagina, 
		@RequestParam(value="linesPerPage",defaultValue="24") Integer linhasPorPagina, 
		@RequestParam(value="orderBy",defaultValue="nome") String ordenar, 
		@RequestParam(value="direction",defaultValue="ASC") String sentido) {
		
		Page<Categoria> lista = service.buscaPagina(pagina, linhasPorPagina, ordenar, sentido);
		Page<CategoriaDTO> categorias = lista.map(Obj -> new CategoriaDTO(Obj));
		if (categorias == null) {
			return ResponseEntity.unprocessableEntity().body(null);
		}
		return ResponseEntity.ok().body(categorias);
	}
}
