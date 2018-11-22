package com.jonasmagno.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.jonasmagno.cursomc.domain.Categoria;
import com.jonasmagno.cursomc.repositories.CategoriaRepository;
import com.jonasmagno.cursomc.services.exception.DataIntegrityException;
import com.jonasmagno.cursomc.services.exception.ObjectNotFoundException;


@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;
	
	public Categoria buscar(Integer id) {
		
		Optional<Categoria> cat = repo.findById(id);
		return cat.orElseThrow(() -> new ObjectNotFoundException(
			"Obejto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}

	public List<Categoria> salvar(List<Categoria> categorias) {
		return repo.saveAll(categorias);
	}
	
	public Categoria salvar(Categoria categoria) {
		return repo.save(categoria);
	}

	public void delete(Integer id) {
		buscar(id);
		try {
			repo.deleteById(id);
		}catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException(
				"Não é possível excluir uma Categoria que possui Produtos");
		}
	}

	public List<Categoria> buscarTodos() {
		return repo.findAll();
	}
	
	public Page<Categoria> buscaPagina(
		Integer pagina, Integer linhasPorPagina, String ordenar, String sentido) {
		
		PageRequest page = PageRequest.of(
			pagina, linhasPorPagina, Direction.valueOf(sentido), ordenar);
		return repo.findAll(page);
	}
}
