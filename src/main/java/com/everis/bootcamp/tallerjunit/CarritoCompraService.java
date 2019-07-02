package com.everis.bootcamp.tallerjunit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.junit.Test;

import junit.framework.TestCase;

public class CarritoCompraService{
	
	List<Articulo> articulos = new ArrayList<Articulo>();
	
	public void limpiarCesta(){
		articulos = new ArrayList<Articulo>();
	}
	
	public void limpiarCesta02() throws IOException{
		if(articulos.isEmpty()) {
			throw new IOException("Carrito vacio");
		}
		articulos = new ArrayList<Articulo>();
	}
	
	public void addArticulo(Articulo a){
		articulos.add(a);
	}
	
	public int getNumArticulo(){
		return articulos.size();
	}
	
	public Double totalPrice(){
		double precioTotal = articulos.stream().  	
			     mapToDouble(Articulo::getPrecio).
			     sum();
		return precioTotal;
	}
	
	public static Double calculadorDescuento(double precio, double porcentajeDescuento){
		return precio - (precio * (porcentajeDescuento/100));
	}

	public List<Articulo> getArticulos() {
		return articulos;
	}

	public void setArticulos(List<Articulo> articulos) {
		this.articulos = articulos;
	}
	
	

}
