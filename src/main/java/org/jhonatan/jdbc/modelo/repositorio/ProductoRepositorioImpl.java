package org.jhonatan.jdbc.modelo.repositorio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.jhonatan.jdbc.modelo.Categoria;
import org.jhonatan.jdbc.modelo.Producto;
import org.jhonatan.jdbc.util.ConexionBaseDatos;

public class ProductoRepositorioImpl
        implements Repositorio<Producto> {

    private Connection getConection() throws SQLException {
        return ConexionBaseDatos.getInstance();
    }

    @Override
    public List<Producto> listar() throws SQLException {
        List<Producto> productos = new ArrayList<>();
        try ( Statement stmt = getConection().createStatement();  ResultSet rs = stmt.executeQuery("SELECT p.*,c.categoria AS categoria FROM productos AS p  INNER JOIN categoria AS c ON p.id_categoria = c.id_categoria")) {
            while (rs.next()) {
                Producto p = creaProducto(rs);
                //agregamos al arraylist
                productos.add(p);
            }
        }
        //revolvemos la lista
        return productos;
    }

    @Override
    public Producto porId(Long id) throws SQLException {
        Producto p = null;
        try ( PreparedStatement stmt = getConection()
                .prepareStatement("SELECT p.*,c.categoria AS categoria FROM productos AS p  INNER JOIN categoria AS c ON p.id_categoria = c.id_categoria "
                        + " WHERE p.id_categoria = ?")) {
            //parametro de la consulta
            stmt.setLong(1, id);
            try ( //ejecutamos la consulta y se cierra automaticamente
                     ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    p = creaProducto(rs);
                }
            }
        }

        return p;
    }

    @Override
    public void guardar(Producto t) throws SQLException {
        String sql;
        if (t.getId() != null && t.getId() > 0) {
            sql = "UPDATE productos SET nombre = ?, precio = ?,id_categoria = ?,sku = ?  WHERE idproducto = ? ";
        } else {
            sql = "INSERT INTO productos (nombre,precio,id_categoria,sku,fecha) VALUES (?,?,?,?,? )";
        }
        try ( PreparedStatement stmt = getConection().prepareStatement(sql)) {

            //le pasamos los parametros
            stmt.setString(1, t.getNombre());
            stmt.setDouble(2, t.getPrecio());
            stmt.setLong(3, t.getCategoria().getIdCategoria());
            stmt.setString(4, t.getSku());
            if (t.getId() != null && t.getId() > 0) {
                stmt.setLong(5, t.getId());
            } else {
                stmt.setDate(5, new Date(t.getFechaRegistro().getTime()));
            }
            //ejecutamos
            stmt.executeUpdate();
        }
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        try ( PreparedStatement stmt = getConection()
                .prepareStatement("DELETE FROM productos WHERE idproducto = ?")) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    public Producto creaProducto(ResultSet rs) throws SQLException {
        Producto p = new Producto();
        p.setId(rs.getLong("idproducto"));
        p.setNombre(rs.getString("nombre"));
        p.setPrecio(rs.getInt("precio"));
        p.setFechaRegistro(rs.getDate("fecha"));
        p.setSku(rs.getString("sku"));

        Categoria c = new Categoria();
        c.setIdCategoria(rs.getInt("id_categoria"));
        c.setNombre(rs.getString("categoria"));
        //estableecmos la categoria
        p.setCategoria(c);
        return p;
    }
}
