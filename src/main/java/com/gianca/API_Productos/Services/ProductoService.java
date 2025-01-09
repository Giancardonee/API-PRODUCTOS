package com.gianca.API_Productos.Services;

import com.gianca.API_Productos.Domain.Producto;

import java.util.List;

public interface ProductoService {
    public List<Producto> getProductos();
    public Producto agregarProducto(Producto producto);
    public Producto actualizarProductoCompletamente(Producto producto);
    public Producto actualizarProductoParcialmente(Producto producto);
    public boolean eliminarProducto(Integer idProducto);
    public Producto buscarProductoPorID(Integer idProducto);
    public Producto descontarStock (int idProducto, int cantidadSolicitada);
}
