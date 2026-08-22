package model;

public class BinaryTreeSort {

    // clase interna para representar un vertice del arbol
	
    class Nodo {
        int valor;
        Nodo izquierdo;
        Nodo derecho;

        public Nodo(int valor) {
            this.valor = valor;
            izquierdo = derecho = null;
        }
    }

    // raiz del arbol binario
    
    private Nodo raiz;

    // metodo principal para ordenar el arreglo
    
    public void ordenar(int[] arr) {
    	
        // reinicia la raiz a nulo por si se usa el mismo objeto
    	
        raiz = null; 
        int n = arr.length;
        
        if (n == 0) {
            return;
        }

        // inserta todos los elementos del arreglo en el arbol
        
        for (int i = 0; i < n; i++) {
            raiz = insertarRecursivo(raiz, arr[i]);
        }

        // usamos un arreglo de un solo elemento para llevar el indice por referencia
        
        int[] indice = {0};
        
        // extrae los elementos ordenados mediante un recorrido inorden
        
        guardarInOrden(raiz, arr, indice);
    }

    // inserta un nuevo valor en el arbol de forma recursiva
    
    private Nodo insertarRecursivo(Nodo nodoActual, int valor) {
    	
        // si el espacio esta vacio crea un nuevo vertice
        
    	if (nodoActual == null) {
            return new Nodo(valor);
        }

        // si el valor es menor va al subarbol izquierdo
        
    	if (valor < nodoActual.valor) {
            nodoActual.izquierdo = insertarRecursivo(nodoActual.izquierdo, valor);
        } 
    	
        // si el valor es mayor o igual va al subarbol derecho
        
    	else {
            nodoActual.derecho = insertarRecursivo(nodoActual.derecho, valor);
        }

        return nodoActual;
    }

    // realiza un recorrido inorden y sobreescribe el arreglo original
    
    private void guardarInOrden(Nodo nodo, int[] arr, int[] indice) {
        if (nodo != null) {
          
        	// visita primero el subarbol izquierdo
            
        	guardarInOrden(nodo.izquierdo, arr, indice);
            
            // guarda el valor actual en el arreglo y avanza el indice
            
        	arr[indice[0]] = nodo.valor;
            indice[0]++;
            
            // visita finalmente el subarbol derecho 
            
            guardarInOrden(nodo.derecho, arr, indice);
        }
    }
}
