package tallerJUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import com.everis.bootcamp.tallerjunit.Articulo;
import com.everis.bootcamp.tallerjunit.CarritoCompraService;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestUnitario {
	CarritoCompraService carritocompraservice;
	Articulo articulo;
	
	@Before
	public void imprimirPorPantalla() {
		carritocompraservice = new CarritoCompraService();
		articulo = new Articulo();
	}
	
	@Test
	public void Test() {
		System.out.println("Test Realizado correctamente.");
	}
	
	@Test
	public void Test01() {
		double precio = 12.5;
		
		carritocompraservice.addArticulo(new Articulo("articulo",12.0));
		
		assertFalse("La lista esta vacia",carritocompraservice.getArticulos().isEmpty());
	}
	
	@Test
	public void Test02() {
		double precio = 12.5;
		
		articulo.setDescripcion("Esto es una prueba del test02");
		articulo.setPrecio(precio);
		carritocompraservice.addArticulo(articulo);
		
		carritocompraservice.limpiarCesta();
		assertTrue("La esta cesta vacia",carritocompraservice.getArticulos().isEmpty());
	}

	@Test
	public void Test03() {
		double resultado = 0;
		double resultado02 = 0;
		
		carritocompraservice.addArticulo(new Articulo("articulo02",12.0));
		carritocompraservice.addArticulo(new Articulo("articulo03",12.0));
		
		for(Articulo a: carritocompraservice.getArticulos()) {
			resultado = resultado + a.getPrecio();
		}
		
		/*for(int i=0; i<carritocompraservice.getArticulos().size(); i++) {
			resultado = resultado + carritocompraservice.getArticulos().get(i).getPrecio(i);
		}*/
		
		resultado02 = carritocompraservice.totalPrice();
		assertEquals("Error en la suma total ",resultado, resultado02,0);
	}
	
	@Test
	public void Test04() {
		double porcentajeConDescuento = 5;
		double precio = 5.0;
		double precioCalculado = precio - (precio * (porcentajeConDescuento/100));
		
		assertEquals("Error en los descuentos ",precioCalculado, carritocompraservice.calculadorDescuento(precio, porcentajeConDescuento),0);
	}
	
	@Test(expected=IOException.class)
	public void Test05() throws IOException {
		carritocompraservice.limpiarCesta02();
	}
}