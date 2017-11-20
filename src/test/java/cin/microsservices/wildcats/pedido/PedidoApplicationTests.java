package cin.microsservices.wildcats.pedido;

import cin.microsservices.wildcats.pedido.domain.pedido.Pedido;
import cin.microsservices.wildcats.pedido.domain.pedido.StatusPedido;
import cin.microsservices.wildcats.pedido.dto.pedido.ItemPedidoDTO;
import cin.microsservices.wildcats.pedido.rest.PedidoRestService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cin.microsservices.wildcats.pedido.domain.pedido.ItemPedido;

import java.io.IOException;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PedidoApplicationTests {
	private ItemPedidoDTO item1DTO = new ItemPedidoDTO();
	private ItemPedido item1 = new ItemPedido();
	private PedidoRestService pedido_rest = new PedidoRestService();

	@Before
	public void setUp() {
		//Criação de item pedido

		item1.setIdProduto(1);
		item1.setQuantidade(2);
		item1DTO.setIdPedido(1);
		item1DTO.setIdCliente(1);
		item1DTO.setItem(item1);
	}
	/**
	 * Teste simples na criação de um ItemPedido
	 * 12/11/2017
	 */
	@Test
	public void testCriacaoDePedido() {
		try {
			pedido_rest.adicionaItemPedido(item1DTO);
			List<Pedido> lista_pedidos;
			lista_pedidos = pedido_rest.buscarPedidosPorCliente(Long.valueOf(1));
			//=================VERIFICAÇÃO===============
			assertTrue(lista_pedidos.size() > 0);
		} catch (IOException e) {
			//Se vier para o catch, quer dizer que a verificação interna
			//do pedido falhou
			fail();
		}
	}
	@Test
	public void testRemovePedido() {
		pedido_rest.removeItemPedido(item1DTO);
		List<Pedido> lista_pedidos;
		lista_pedidos = pedido_rest.buscarPedidosPorCliente(Long.valueOf(1));
		//=================VERIFICAÇÃO===============
		assertFalse(lista_pedidos.size() > 0);
	}

}
