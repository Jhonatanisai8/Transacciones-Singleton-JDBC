package org.jhonatan.jdbc.modelo;

public class Categoria {

    private long idCategoria;
    private String nombre;

    public Categoria() {
    }

    public long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
