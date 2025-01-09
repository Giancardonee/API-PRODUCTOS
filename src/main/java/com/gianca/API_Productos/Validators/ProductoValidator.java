package com.gianca.API_Productos.Validators;

import com.gianca.API_Productos.Domain.Producto;

public class ProductoValidator {


    public static void esProductoValidoParaActualizarParcialmente (Producto producto)
    {
        if (producto == null || producto.getId() <= 0 ||
                (producto.getDescripcion() == null && producto.getNombre() == null && producto.getStock() == null && producto.getPrecio() == null)) {
            throw new IllegalArgumentException("Producto no válido para actualizar parcialmente.");
        }
    }

    public static void haySuficienteStock(Producto producto, int cantidadSolicitada)
    {
        if (producto == null) {throw new IllegalArgumentException("El producto no puede ser nulo.");}
        if ((producto.getStock() - cantidadSolicitada) <0) {
            throw new IllegalArgumentException("La cantidad solicitada supera al stock actual.");
        }
    }

    public static void esProductoValidoParaInsercion(Producto producto)
    {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo.");
        }
        if (producto.getNombre() == null || producto.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }
        if (producto.getDescripcion() == null || producto.getDescripcion().isEmpty()) {
            throw new IllegalArgumentException("La descripción es obligatoria.");
        }
        if (producto.getPrecio() == null || producto.getPrecio() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor que 0.");
        }
        if (producto.getStock() == null || producto.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo.");
        }
    }

    public static void esProductoValidoParaActualizarCompletamente(Producto producto) {
        esProductoValidoParaInsercion(producto);
        if (producto.getId() == 0)
        {
            throw new IllegalArgumentException("Producto no valido para actualizacion completa.");
        }
    }


    /*
    Funcion que genera el mensaje de error para los productos
    public static String getErrores(Producto producto) {
        StringBuilder errores = new StringBuilder();
        if (producto.getId() != 0) errores.append("ID debe ser 0 para inserción. ");
        if (producto.getNombre() == null || producto.getNombre().isEmpty()) errores.append("Nombre es obligatorio. ");
        if (producto.getDescripcion() == null || producto.getDescripcion().isEmpty()) errores.append("Descripción es obligatoria. ");
        if (producto.getPrecio() <= 0) errores.append("Precio debe ser mayor que 0. ");
        if (producto.getStock() < 0) errores.append("Stock no puede ser negativo. ");
        return errores.toString();
    }
     */

}
