package model.repository;

import model.data.Arreglo;

import java.io.IOException;
import java.util.List;

public interface RepositorioArreglos {

    void guardar(Arreglo arreglo) throws IOException;

    List<Arreglo> cargar() throws IOException;
}