package org.jhonatan.jdbc.modelo.repositorio;

import java.util.List;

//de tipo generico
public interface Repositorio<T> {

    List<T> listar();

    T porId(Long id);

    void guardar(T t);

    void eliminar(Long id);

}
