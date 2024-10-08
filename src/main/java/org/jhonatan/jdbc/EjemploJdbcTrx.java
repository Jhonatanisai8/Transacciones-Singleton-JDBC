package org.jhonatan.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import org.jhonatan.jdbc.modelo.Categoria;
import org.jhonatan.jdbc.modelo.Producto;
import org.jhonatan.jdbc.modelo.repositorio.ProductoRepositorioImpl;
import org.jhonatan.jdbc.modelo.repositorio.Repositorio;
import org.jhonatan.jdbc.util.ConexionBaseDatos;

public class EjemploJdbcTrx {

    public static void main(String[] args) throws SQLException {
        System.out.println("\tJAVA Y JBDC REPOSITORIO DE PRODUCTOS");
        //le pasamos los parametros
        try ( Connection con = ConexionBaseDatos.getInstance();) {
            if (con.getAutoCommit()) { // si esta en true cambiamos a false
                con.setAutoCommit(false);
            }
            try {

                System.out.println("\t========LISTA DE PRODUCTOS========");
                Repositorio<Producto> repositorio = new ProductoRepositorioImpl();
                repositorio.listar().forEach(System.out::println);

                System.out.println("\t========OBTENER POR ID========");
                System.out.println(repositorio.porId(3l));

                System.out.println("\t========CREAR UN OBJETO========");
                Producto p = new Producto();
                p.setNombre("Teclado IMB Mecánico");
                p.setPrecio(1550);
                p.setFechaRegistro(new Date());
                //creamos una categoria
                Categoria c = new Categoria();
                c.setIdCategoria(3L);
                //establecemos la categoria al producto
                p.setCategoria(c);
                p.setSku("abod12312");
                //guardamos
                repositorio.guardar(p);
                System.out.println("Producto guardado con exito...");

                System.out.println("\t========MODIFICACION DE PRODUCTO========");
                Producto p1 = new Producto();
                p1.setId(15l);
                p1.setNombre("Teclado Cosair K95 Mecánico");
                p1.setPrecio(1000);
                p1.setSku("fghj1234");
                //creamos la categoria y establecemos al producto
                Categoria ca = new Categoria();
                ca.setIdCategoria(2l);
                p1.setCategoria(ca);
                //guardamos
                repositorio.guardar(p1);
                System.out.println("Producto modificado con exito");

                System.out.println("\t========LISTA DE PRODUCTOS========");
                repositorio.listar().forEach(System.out::println); 
                
                
                //hacemos el commit
                con.commit();
            } catch (SQLException e) {
                
                //revertimos los cambios en caso de fallo
                con.rollback();
                System.out.println("error:  "+e.getMessage());
            }
        }
    }
}
