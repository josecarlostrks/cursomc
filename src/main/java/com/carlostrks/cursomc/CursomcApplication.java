package com.carlostrks.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.carlostrks.cursomc.domain.Categoria;
import com.carlostrks.cursomc.domain.Cidade;
import com.carlostrks.cursomc.domain.Cliente;
import com.carlostrks.cursomc.domain.Endereco;
import com.carlostrks.cursomc.domain.Estado;
import com.carlostrks.cursomc.domain.Pagamento;
import com.carlostrks.cursomc.domain.PagamentoComBoleto;
import com.carlostrks.cursomc.domain.PagamentoComCartao;
import com.carlostrks.cursomc.domain.Pedido;
import com.carlostrks.cursomc.domain.Produto;
import com.carlostrks.cursomc.domain.enums.EstadoPagamento;
import com.carlostrks.cursomc.domain.enums.TipoCliente;
import com.carlostrks.cursomc.repositories.CategoriaRepository;
import com.carlostrks.cursomc.repositories.CidadeRepository;
import com.carlostrks.cursomc.repositories.ClienteRepository;
import com.carlostrks.cursomc.repositories.EnderecoRepository;
import com.carlostrks.cursomc.repositories.EstadoRepository;
import com.carlostrks.cursomc.repositories.PagamentoRepository;
import com.carlostrks.cursomc.repositories.PedidoRepository;
import com.carlostrks.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{
	
	@Autowired
	private CategoriaRepository categoriaRepository;	
	@Autowired
	private ProdutoRepository produtoRepository;
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
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		categoriaRepository.flush();
		produtoRepository.flush();
		
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().add(p2);
		
		p1.getCategorias().add(cat1);
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().add(cat1);
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlandia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().add(c1);
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36789325617", TipoCliente.PESSOAFISICA);
		
		cli1.getTelefones().addAll(Arrays.asList("8399348655","83993701903"));
		
		Endereco e1 = new Endereco(null,"Rua Flores", "300","Apto303","Jardim","38220634",cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105","Sala 800","Centro","38779832", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
		
		clienteRepository.save(cli1);
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm"); 
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6 );
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 23:59"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		
	}

}








