package controller;

import model.algorithms.AlgoritmoOrdenamiento;
import model.algorithms.BinaryTreeSort;
import model.algorithms.BubbleSort;
import model.algorithms.QuickSort;
import model.algorithms.RadixSort;
import model.algorithms.ShellSort;
import model.repository.ArchivoArreglos;
import model.repository.RepositorioArreglos;
import model.service.EjecutorAlgoritmos;
import model.service.GeneradorArreglos;
import model.service.MedidorTiempo;
import view.Interfaz;

import java.util.List;

public final class Main {
	
    private Main() {

    }

    public static void main(String[] args) {

        Interfaz interfaz = new Interfaz();

        GeneradorArreglos generador =
                new GeneradorArreglos(0, 900_000); // valores del arreglo, de donde varia 

        RepositorioArreglos repositorio =
                new ArchivoArreglos("data/arreglos.txt"); // direccion del txt

        MedidorTiempo medidorTiempo =
                new MedidorTiempo();

        // define la lista 
       
        List<AlgoritmoOrdenamiento> algoritmos = List.of(
                new BubbleSort(),
                new RadixSort(),
                new ShellSort(),
                new BinaryTreeSort(),
                new QuickSort()
        );

        EjecutorAlgoritmos ejecutor =
                new EjecutorAlgoritmos(
                        algoritmos,
                        medidorTiempo
                );

        // mete las dependencias concretas aislando la vista
        
        Controlador controlador =
                new Controlador(
                        interfaz,
                        generador,
                        repositorio,
                        ejecutor
                );

        controlador.iniciar();
        interfaz.cerrar();
    }
}