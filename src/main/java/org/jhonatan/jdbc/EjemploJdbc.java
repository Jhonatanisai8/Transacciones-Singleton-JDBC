package org.jhonatan.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jhonatan.jdbc.modelo.Categoria;
import org.jhonatan.jdbc.modelo.Producto;
import org.jhonatan.jdbc.modelo.repositorio.ProductoRepositorioImpl;
import org.jhonatan.jdbc.modelo.repositorio.Repositorio;
import org.jhonatan.jdbc.util.ConexionBaseDatos;

public class EjemploJdbc {

    public static void main(String[] args) {
        System.out.println("JAVA Y JBDC");

        try ( //le pasamos los parametros
                 Connection con = ConexionBaseDatos.getInstance();) {
            System.out.println("\t========LISTA DE PRODUCTOS========");
            Repositorio<Producto> repositorio = new ProductoRepositorioImpl();
            repositorio.listar().forEach(System.out::println);

            System.out.println("\t========POR ID========");
            System.out.println(repositorio.porId(3l));

            System.out.println("\t========CREAR UN OBJETO========");
            Producto p = new Producto();
            p.setNombre("Pelota de Fultob");
            p.setPrecio(40);
            p.setFechaRegistro(new Date());

            //creamos una categoria
            Categoria c = new Categoria();
            c.setIdCategoria(1L);

            //establecemos la categoria al producto
            p.setCategoria(c);

            //guardamos
            repositorio.guardar(p);
            System.out.println("Producto guardado con exito");

            System.out.println("\t========LISTA DE PRODUCTOS========");
            repositorio.listar().forEach(System.out::println);

        } catch (SQLException ex) {
            Logger.getLogger(EjemploJdbc.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
