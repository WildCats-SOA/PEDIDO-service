package cin.microsservices.wildcats.pedido;

import cin.microsservices.wildcats.pedido.domain.pedido.Pedido;
import cin.microsservices.wildcats.pedido.domain.pedido.StatusPedido;
import cin.microsservices.wildcats.pedido.dto.pedido.ItemPedidoDTO;
import cin.microsservices.wildcats.pedido.rest.PedidoRestService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.hamcrest.core.Is;
import static org.hamcrest.CoreMatchers.is;
import org.hamcrest.core.IsEqual;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cin.microsservices.wildcats.pedido.domain.pedido.ItemPedido;

import java.io.IOException;
import java.util.ArrayList;
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
		try {
			pedido_rest.adicionaItemPedido(item1DTO);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			fail();
		}
	}
	@After
	public void limpa_teste() {
		item1DTO = new ItemPedidoDTO();
		item1 = new ItemPedido();
		pedido_rest = new PedidoRestService();

	}
	/**
	 * Teste simples na criação de um ItemPedido
	 * 12/11/2017
	 */
	@Test
	public void testCriacaoDePedido() {
		try {
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
	/**
	 * Testa se esta sendo efetuado o pagamento de pedidos
	 * esperado que retorne como 'CONCLUIDO'
	 * 21/11/2017
	 */
	@Test
	public void testPagaPedido() {
		pedido_rest.pagaPedido(Long.valueOf(1));
		StatusPedido stspedido = null;
		List<Pedido> lista_pedidos = new ArrayList<Pedido>();
		Pedido pedido_teste= new Pedido();
		lista_pedidos = pedido_rest.buscarPedidos();
		for (Pedido pedido : lista_pedidos) {
			if (pedido != null && pedido.getId() == Long.valueOf(1)) {
				pedido_teste = pedido;
				break;
			}
		}
		//=================VERIFICAÇÃO===============
		assertTrue(pedido_teste.getId() == Long.valueOf(1));
		assertTrue(pedido_teste.getStatus().equals(stspedido.CONCLUIDO));
	}
	
	@Test
	public void testRemovePedido() {
		pedido_rest.removeItemPedido(item1DTO);
		List<Pedido> lista_pedidos;
		lista_pedidos = pedido_rest.buscarPedidos();
		Boolean encontrou = false;
		for (Pedido pedido : lista_pedidos) {
			List<ItemPedido> lista_item_pedidos = pedido.getItems();
			if(pedido != null && pedido.getId() == Long.valueOf(1) && pedido.getIdCliente()==Long.valueOf(1)) {
				for (ItemPedido itemPedido : lista_item_pedidos) {
					if(itemPedido != null && itemPedido.getIdProduto() == Long.valueOf(1)) {
						encontrou = true;
						break;
					}
				}
			}
		}
		//=================VERIFICAÇÃO===============
		assertFalse(encontrou);
	}

}
