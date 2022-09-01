package com.treinamentos.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.treinamentos.cursomc.domain.Categoria;
import com.treinamentos.cursomc.domain.Cidade;
import com.treinamentos.cursomc.domain.Cliente;
import com.treinamentos.cursomc.domain.Endereco;
import com.treinamentos.cursomc.domain.Estado;
import com.treinamentos.cursomc.domain.Produto;
import com.treinamentos.cursomc.domain.enums.TipoCliente;
import com.treinamentos.cursomc.repositories.CategoriaRepository;
import com.treinamentos.cursomc.repositories.CidadeRepository;
import com.treinamentos.cursomc.repositories.ClienteRepository;
import com.treinamentos.cursomc.repositories.EnderecoRepository;
import com.treinamentos.cursomc.repositories.EstadoRepository;
import com.treinamentos.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired 
	private CidadeRepository cidadeRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired 
	private EnderecoRepository enderecoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
		
		Estado e1 = new Estado(null, "Minas Gerais");
		Estado e2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null,"Uberlândia",e1);
		Cidade c2 = new Cidade(null,"São Paulo",e2);
		Cidade c3 = new Cidade(null,"Campinas",e2);
		
		e1.getCidades().addAll(Arrays.asList(c1));
		e2.getCidades().addAll(Arrays.asList(c2,c3));
		
		estadoRepository.saveAll(Arrays.asList(e1,e2));
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
		
		Cliente cl1 = new Cliente(null, "Maria Silva", "maria@gmail.com","12345677874", TipoCliente.PESSOAFISICA );
		//Cliente cl2 = new Cliente(null, "Maria Silva", "maria@gmail.com","12345677874", TipoCliente.PESSOAFISICA );
		
		cl1.getTelefones().addAll(Arrays.asList("32123636", "78459685"));
		
		Endereco end1 = new Endereco(null, "Rua Flores", "300", "Apt 303", "Jardim", "32136985", cl1, c1);
		Endereco end2 = new Endereco(null, "Av Matos", "105", "Sala 800", "Centro", "321369655", cl1, c2);
		
		cl1.getEnderecos().addAll(Arrays.asList(end1,end2));
		
		clienteRepository.saveAll(Arrays.asList(cl1));
		enderecoRepository.saveAll(Arrays.asList(end1,end2));
		
		
		
	}
}
