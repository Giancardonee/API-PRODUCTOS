package com.gianca.API_Productos.Services;

import com.gianca.API_Productos.Domain.Producto;
import com.gianca.API_Productos.Validators.ProductoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.beans.BeanProperty;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoServiceBdMySQL implements ProductoService {

    // JdbcdTemplate nos sirve para interactuar con la BD.
    @Autowired
    private JdbcTemplate jdbcTemplate;



    @Override
    public List<Producto> getProductos() {
        String SQL = "SELECT * FROM producto";
        // Mapeamos los resultados de la consulta a objetos de tipo Producto.
        return jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(Producto.class));
    }

    @Override
    public Producto agregarProducto(Producto producto) {
        String SQL = "INSERT INTO producto (nombre,descripcion,stock,precio) values (?,?,?,?)";
        jdbcTemplate.update(SQL, producto.getNombre(), producto.getDescripcion(), producto.getStock(), producto.getPrecio());
            producto.setId(this.obtenerElUltimoID());
            return producto;
    }

    @Override
    public Producto actualizarProductoCompletamente(Producto producto) {
        String SQL = "UPDATE producto SET nombre=?,descripcion=?,stock=?,precio=? WHERE id=?";
        jdbcTemplate.update(SQL, producto.getNombre(), producto.getDescripcion(), producto.getStock(), producto.getPrecio(), producto.getId());
        return this.buscarProductoPorID(producto.getId());
    }

    @Override
    public Producto actualizarProductoParcialmente(Producto producto) {
        List<Object> parametros = new ArrayList<>();
        String SQL = obtenerSQLParaActualizarParcialmente(producto,parametros);
        jdbcTemplate.update(SQL,parametros.toArray());
        return this.buscarProductoPorID(producto.getId());
    }

    @Override
    public boolean eliminarProducto(Integer idProducto) {
        String SQL ="DELETE FROM producto WHERE id=?";
        // jdbcTemplate.update, devuelve 1 si el producto fue eliminado correctamente.
        return jdbcTemplate.update(SQL,idProducto) == 1;
    }

    @Override
    public Producto buscarProductoPorID(Integer idProducto) {
        String SQL = "SELECT * FROM producto WHERE id=?";
        try {
            return jdbcTemplate.queryForObject(SQL, new Object[]{idProducto}, new BeanPropertyRowMapper<>(Producto.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    @Override
    public Producto descontarStock(int idProducto,int cantidadSolicitada)
    {
        Producto producto = this.buscarProductoPorID(idProducto);
        ProductoValidator.haySuficienteStock(producto,cantidadSolicitada);
        String SQL = "UPDATE producto SET stock=? WHERE id=?";
        int stockNuevo = producto.getStock() - cantidadSolicitada;
        jdbcTemplate.update(SQL, stockNuevo, idProducto);
        producto.setStock(stockNuevo);
        return producto;
    }

    // Este metodo obtiene el ultimo id que se inserto (es mas que nada para mostrarlo en postman por ahora.)
    private int obtenerElUltimoID()
    {
        // Necesitamos devolver el objeto con su id, por ende, necesitamos saber cual es el ultimo id que insertamos
        String SQL_Ultimo_ID = "SELECT LAST_INSERT_ID()";
        return jdbcTemplate.queryForObject(SQL_Ultimo_ID,Integer.class);
    }

    private String obtenerSQLParaActualizarParcialmente(Producto producto, List<Object> parametros) {
        StringBuilder SQL = new StringBuilder("UPDATE producto SET ");
        // Para saber si estamos añadiendo el primer campo
        // ya que debemos saber, en que momento ponemos la ',' en la sentencia sql para separar los atributos.
        boolean first = true;

        if (producto.getNombre() != null) {
            if (!first) SQL.append(", ");
            SQL.append("nombre=?");
            parametros.add(producto.getNombre());
            first = false;
        }
        if (producto.getDescripcion() != null) {
            if (!first) SQL.append(", ");
            SQL.append("descripcion=?");
            parametros.add(producto.getDescripcion());
            first = false;
        }
        if (producto.getStock() != null) {
            if (!first) SQL.append(", ");
            SQL.append("stock=?");
            parametros.add(producto.getStock());
            first = false;
        }
        if (producto.getPrecio() != null) {
            if (!first) SQL.append(", ");
            SQL.append("precio=?");
            parametros.add(producto.getPrecio());
        }

        // Añadimos el WHERE
        SQL.append(" WHERE id=?");
        parametros.add(producto.getId());

        return SQL.toString();
    }

}
