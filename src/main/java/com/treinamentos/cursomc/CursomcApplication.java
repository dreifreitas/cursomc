package com.treinamentos.cursomc;

import java.text.SimpleDateFormat;
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
import com.treinamentos.cursomc.domain.ItemPedido;
import com.treinamentos.cursomc.domain.Pagamento;
import com.treinamentos.cursomc.domain.PagamentoComBoleto;
import com.treinamentos.cursomc.domain.PagamentoComCartao;
import com.treinamentos.cursomc.domain.Pedido;
import com.treinamentos.cursomc.domain.Produto;
import com.treinamentos.cursomc.domain.enums.EstadoPagamento;
import com.treinamentos.cursomc.domain.enums.TipoCliente;
import com.treinamentos.cursomc.repositories.CategoriaRepository;
import com.treinamentos.cursomc.repositories.CidadeRepository;
import com.treinamentos.cursomc.repositories.ClienteRepository;
import com.treinamentos.cursomc.repositories.EnderecoRepository;
import com.treinamentos.cursomc.repositories.EstadoRepository;
import com.treinamentos.cursomc.repositories.ItemPedidoRepository;
import com.treinamentos.cursomc.repositories.PagamentoRepository;
import com.treinamentos.cursomc.repositories.PedidoRepository;
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
	@Autowired 
	private PedidoRepository pedidoRepository;
	@Autowired 
	private PagamentoRepository pagamentoRepository;
	@Autowired 
	private ItemPedidoRepository itemPedidoRepository;
	
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
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cl1, end1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cl1, end2);
		
		Pagamento pagto1 = new PagamentoComCartao(null,EstadoPagamento.QUITADO, ped1, 6 );
		ped1.setPagamento(pagto1);

		Pagamento pagto2 = new PagamentoComBoleto(null,EstadoPagamento.PENDENTE, ped2, sdf.parse("20/01/2017 00:00"), null );
		ped2.setPagamento(pagto2);
		
		cl1.getPedidos().addAll(Arrays.asList(ped1,ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1,ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00 , 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1,ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1,ip2,ip3));
		
		
	}
}
