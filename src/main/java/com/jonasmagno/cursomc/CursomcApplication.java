package com.jonasmagno.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jonasmagno.cursomc.domain.Categoria;
import com.jonasmagno.cursomc.domain.Cidade;
import com.jonasmagno.cursomc.domain.Estado;
import com.jonasmagno.cursomc.domain.Produto;
import com.jonasmagno.cursomc.services.CategoriaService;
import com.jonasmagno.cursomc.services.CidadeService;
import com.jonasmagno.cursomc.services.EstadoService;
import com.jonasmagno.cursomc.services.ProdutoService;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{
	
	@Autowired
	private CategoriaService catService;
	@Autowired
	private ProdutoService prodService;
	@Autowired
	private EstadoService estService;
	@Autowired
	private CidadeService cidService;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().add(p2);
		
		p1.getCategorias().add(cat1);
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().add(cat1);
		
		catService.salvar(Arrays.asList(cat1, cat2));
		prodService.salvar(Arrays.asList(p1,p2,p3));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().add(c1);
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		estService.salvar(Arrays.asList(est1,est2));
		cidService.salvar(Arrays.asList(c1,c2,c3));
		
	}
}
