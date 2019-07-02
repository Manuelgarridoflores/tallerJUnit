package tallerJUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;

import com.everis.bootcamp.tallerjunit.Articulo;
import com.everis.bootcamp.tallerjunit.BBDD;
import com.everis.bootcamp.tallerjunit.CarritoCompraService;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//@TestMethodOrder(TestUnitario.class)
public class TestUnitario {
	CarritoCompraService carritocompraservice;
	static CarritoCompraService carritocompraservice02;
	Articulo articulo;
	BBDD bbdd;
	
	@BeforeClass
	public static void imprimirPorPantalla02() {
		carritocompraservice02 = new CarritoCompraService();
	}
	
	@Before
	public void setUp() {
		carritocompraservice = new CarritoCompraService();
		articulo = new Articulo();
		
		System.out.println("Inicializamos el servicio");
		bbdd = Mockito.mock(BBDD.class);
		carritocompraservice.setBbddService(bbdd);
	}
	
	@Test
	//@Order(1)
	public void Test() {
		System.out.println("Test Realizado correctamente.");
	}
	
	@Test
	public void Test01() {
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
	
	@Test
	public void Test06() {
		Mockito.when(bbdd.findArticuloById(1)).thenReturn(new Articulo("prueba01", 50d));
		
		Double desc = carritocompraservice.aplicarDescuento(1, 10d);
		assertEquals(45d, desc,0);
	}
	
	@Test
	public void Test07() {
		Mockito.when(bbdd.findArticuloById(1)).thenReturn(null);
		
		Double desc = carritocompraservice.aplicarDescuento(1, 10d);
		assertNull(desc);
	}
	
	@Test(expected=Exception.class)
	public void Test08() {
		Mockito.when(bbdd.findArticuloById(0)).thenThrow(new Exception());
		Double result = carritocompraservice.aplicarDescuento(1, 12.5);
		System.out.println("Precio Final: " + result);
	}
	
	@Test
	public void Test09() {
		Mockito.when(bbdd.findArticuloById(1)).thenReturn(new Articulo("PRUEBA02", 10.0)); //Añado un nuevo articulo a la BBDD.
		Double result = carritocompraservice.aplicarDescuento(1, 12.0); //Creo una variable con el descuento aplicado.
		
		assertEquals(result, new Double(8.8)); //Comparo y las variables.
		Mockito.verify(bbdd, Mockito.times(1)).findArticuloById(1); //Verifico que todo haya ido correctamente.
		
		System.out.println("El precio definitivo del articulo tras el descuento: " + result); //Imprimo por pantalla el precio final.
	}
	
	@Test
	public void Test10(){
		String descripcion = "";
		int id;
		
		//Insertar datos en la BBDD y en la lista de la compra.
		carritocompraservice.addArticulo(new Articulo("articulo04",12.0));
		bbdd.insertArticulo(new Articulo("articulo04",12.0));
		
		//Listado del carrito de la compra.
		for(Articulo a: carritocompraservice.getArticulos()) {
			descripcion = a.getDescripcion();
			id = a.getId().intValue();
			
			articulo = bbdd.findArticuloById(id);
			
			if(id == articulo.getId()) {
				System.out.println("Articulo insertado correctamente en la lista compra: " + descripcion);
				System.out.println("Articulo insertado correctamente en la BBDD: " + descripcion);
			}
			
		}
	}
}