#Taller Algoritmos 

	  Nota: el txt no deja subirlo, pesa mucho, y el bublle con n² tarda 2-3 dias
	  Estructura del proyecto MVC Java 
	
		Model -> contiene la generacion de los arreglos aleatorios y la logica de los 
		         algoritmos
			model.algorithms
					 AlgoritmoOrdenamieto.java
			         Arreglo.java
					 BubbleSort.java
					 RadixSort.java
					 ShellSort.java
					 BinaryTreeSort.java -> en su version balanceada
					 QuickSort.java -> dependiento la varianza de datos tarda mas o menos
			model.data
					 Arreglo.java -> comprobacion de que el arreglo se cree en condiciones esperadas
					 Estadisticas.java
					 Resultado.java
			model.repository
					 ArchivoArreglos.java -> donde se lee el txt
					 RepositorioArreglos.java
			model.service
					 EjecutorAlgortimos.java
					 GeneradorArreglos.java
					 MedidorTiempo.java
			 
	  	View -> contiene la interaccion del usuario
	  		 
	  		 Interfaz.java
	  		 
	  	Controller -> es el intermediario entre el model y el view, controlando el flujo y el tiempo
	  	
	  		 Controlador.java
			 Main.java -> aqui se modifica la direccion del txt y el tamaño maximo del arreglo
