package com.gianca.API_Productos.Controllers;


import com.gianca.API_Productos.Domain.Producto;
import com.gianca.API_Productos.Services.ProductoService;
import com.gianca.API_Productos.Validators.ProductoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/producto")
public class ProductoController {


    // Indicamos que se esta usando inyeccion de dependencia
    @Autowired
    private ProductoService productoService;


    // Aclaracion: TODAS LAS URL SON TENIENDO EN CUENTA DONDE DESPEGO MI SERVIDOR (en mi caso de manera local en puerto 8080)


    // URL:http://localhost:8080/producto
    @GetMapping
    // Si la lista esta vacia: Devuelve 204
    // Si la lista tiene al menos un producto: Devuelve 200.
    public ResponseEntity<List<Producto>> getProductos()
    {
        List<Producto> productos = productoService.getProductos();
        if (productos.isEmpty()) {return ResponseEntity.noContent().build();}
        else return ResponseEntity.ok(productos);
    }

    // URL:http://localhost:8080/producto/{idProducto}
    // URL EJEMPLO: http://localhost:8080/producto/1
    @GetMapping("/{idProducto}")
    // Retorna 200: Si el producto existe
    // Retorna 404: Si el producto no existe
    public ResponseEntity<Producto> getProductoPorID(@PathVariable Integer idProducto)
    {
        Producto producto = productoService.buscarProductoPorID(idProducto);
        if (producto == null) {return ResponseEntity.notFound().build();}
        else return ResponseEntity.ok(producto);
    }

    @PostMapping
    // Retorna 201: Si el producto se agrega exitosamente. Ademas retorna la URI para identificar el recurso.
    // Retorna 400: Si el producto recibido por parametro es null (no es posible agregarlo.)
    public ResponseEntity<Producto> postProducto(@RequestBody Producto productoNuevo)
    {
        try {
            ProductoValidator.esProductoValidoParaInsercion(productoNuevo);
            this.productoService.agregarProducto(productoNuevo);
            URI uri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(productoNuevo.getId())
                    .toUri();
            return ResponseEntity.created(uri).body(productoNuevo);
        }
        catch (Exception e)
        {
            System.out.println("Error: "+e);
            return ResponseEntity.badRequest().build();
        }
    }

    // URL:http://localhost:8080/producto/{idProducto}
    @DeleteMapping("/{idProducto}")
    // Retorna 204: Si el producto se borra exitosamente.
    // Retorna 404: Si el producto no se encuentra.
    public ResponseEntity<Producto> deleteProducto (@PathVariable Integer idProducto)
    {
        boolean productoEliminado = productoService.eliminarProducto(idProducto);
        return productoEliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PutMapping
    // Retorna 200 si: Se actualizo completamente
    // Retorna 400 si: La peticion es invalida.
    // Retorna 404 si: No se encontró el producto a actualizar
    public ResponseEntity<Producto> putProducto (@RequestBody Producto productoActualizar)
    {
        try{
            ProductoValidator.esProductoValidoParaActualizarCompletamente(productoActualizar);
            Producto productoActualizado = this.productoService.actualizarProductoCompletamente(productoActualizar);
            if (productoActualizado != null) {
                return ResponseEntity.ok(productoActualizado);
            } else {
                return ResponseEntity.notFound().build();
            }
        }
        catch (Exception e)
        {
            System.out.println("Error "+e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/actualizarStock/{idProducto}/{unidadesSolicitadas}")
    public ResponseEntity<Producto> patchProductoStock(@PathVariable int idProducto, @PathVariable int unidadesSolicitadas) {
        try {
            Producto productoActualizado = productoService.descontarStock(idProducto, unidadesSolicitadas);
            return ResponseEntity.ok(productoActualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PatchMapping
    // Retorna 200 si: Se actualizo parcialmente
    // Retorna 400 si: La peticion es invalida.
    // Retorna 404 si: No se encontró el producto a actualizar
    public ResponseEntity<Producto> patchProducto (@RequestBody Producto productoActualizar)
    {
        try
        {
            ProductoValidator.esProductoValidoParaActualizarParcialmente(productoActualizar);
            Producto productoActualizado = this.productoService.actualizarProductoParcialmente(productoActualizar);
            if (productoActualizado != null) {return ResponseEntity.ok(productoActualizado);}
            else return ResponseEntity.notFound().build();
        }
        catch (Exception e)
        {
            System.out.println("Error "+e);
            return ResponseEntity.badRequest().build();
        }
    }
}
