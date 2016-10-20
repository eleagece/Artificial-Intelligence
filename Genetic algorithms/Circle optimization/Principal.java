import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Random;
import java.util.Collections;

public class Principal
	{
	public static void main(String[] args) throws Exception 
		{
		/**
		 * GENÉTICOS
		 * -Con 'n==0' creamos 38 círculos de población inicial correspondientes a las cua-
		 * drículas 2x2=4 círculos, 3x3=9 círculos y 5x5=25 círculos. Con 'n>0' y par 
		 * creamos 'n' círculos aleatorios de población inicial. Con 'n>0' e impar se vuelven
		 * a pedir los datos. Con 'n<0' se vuelven a pedir los datos.
		 * -Pedimos un mínimo de 10 generaciones para observar cambios significativos y un
		 * máximo de 1000000 porque ya debería haber cambios significativos a esas alturas. Si
		 * proporcionamos un número menor que 10 o mayor que 1000000 lo ajustamos al mínimo o máximo.
		 * -Pedimos la tasa de cruce, si es menor o igual que 0 o mayor o igual que 1 la ponemos a 0,7.
		 * -Pedimos la tasa de mutación, si es menor o igual que 0 o mayor o igual que 1 la ponemos a 0,3.
		 * -Pedimos el porcentaje d élite a guardar (del 5% al 20%).
		 * -Pedimos si queremos que el cruce se produzca a nivel de gen o a nivel de atributo.
		 * -OJO: El programa puede dar errores al meter caracteres inesperados cuando se piden datos.
		 */
		System.out.println("+--------------------------------------------------+");
		System.out.println("| ALGORITMOS GENÉTICOS                             |");
		System.out.println("+--------------------------------------------------+");
		System.out.println("| -Importante: introducir los datos tal como se    |");
		System.out.println("| pide para evitar posibles errores de lectura     |");
		System.out.println("| -Si se introduce 0 como número de individuos     |");
		System.out.println("| iniciales se carga una población predeterminada  |");
		System.out.println("| (explicado en memoria)                           |");    
		System.out.println("+--------------------------------------------------+");
		///////////////////////////////////////////////
	    // 0. Generando población inicial y opciones //
	    ///////////////////////////////////////////////
		//////////////////////////////
		// 0.1 Variables "globales" //
		//////////////////////////////
		Scanner inGEN = new Scanner(System.in); 
		int numGeneraciones;
		int numIndividuosPadres;
		int numIndividuosHijos;
		float tasaCruce;
		float tasaMutacion;
		int porcentajeElite;
		boolean corteANivelGen;
		///////////////////////////
		// 0.1 Población inicial //
		///////////////////////////
		boolean inGENpar=false;	int nGEN=0;
		while (!inGENpar)
			{
			System.out.print("Introduce número de individuos iniciales (recomendado 0) [número par]: ");
			nGEN = inGEN.nextInt();
			if (nGEN%2==0 && nGEN>-1)
				inGENpar=true;
			}
		Problema geneticos=new Problema("inicial.txt");
		ArrayList<Individuo> arrayPoblacionPadres = new ArrayList<Individuo>();
		ArrayList<Float> arrayProbCruce = new ArrayList<Float>();
		ArrayList<Individuo> arrayPoblacionHijos = new ArrayList<Individuo>();
		numIndividuosPadres=0;
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// 0.1.A Población inicial diversa con 'nGEN>0' y par: creamos 'nGEN' círculos aleatorios de población inicial //
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if (nGEN>0 && nGEN%2==0)
			{
		    for (int i=0; i<nGEN; i++)
				{
		    	Circulo c=Circulo.random(Problema.DIMENSION);
		    	Individuo ind=new Individuo(c);
		    	arrayPoblacionPadres.add(ind);
		    	numIndividuosPadres++;
				}
			}
		else 
		////////////////////////////////////////////////////////////////////////////////////////////////////
		// 0.1.B Población inicial predeterminada con 'nGEN==0': creamos 38 círculos de población inicial //
		// inicial correspondientes a las cuadrículas 2x2=4 círculos, 3x3=9 círculos y 5x5=25 círculos    //
		////////////////////////////////////////////////////////////////////////////////////////////////////
			{
			numIndividuosPadres=38;
			int r; int offsetX; int offsetY;
			Circulo c; Individuo ind;
			// Círculos de cuadrícula 2x2
			for (int i=0; i<=1; i++)
				{
				r=((Problema.DIMENSION/2)/2);
				offsetX=r;
				offsetY=r;
				for (int j=0; j<=1; j++)
					{
					int posX=offsetX+(offsetX*2*i);
					int posY=offsetY+(offsetY*2*j);
					c=new Circulo(posX,posY,r);
					ind=new Individuo(c);
					arrayPoblacionPadres.add(ind);
					//System.out.print("(x-"+posX+")^2+(y-"+posY+")^2="+r+"^2, ");  // para plot
					}
				}
			//System.out.println();  // para plot
			// Círculos de cuadrícula 3x3
			for (int i=0; i<=2; i++)
				{
				r=((Problema.DIMENSION/3)/2);
				offsetX=r;
				offsetY=r;
				for (int j=0; j<=2; j++)
					{
					int posX=offsetX+(offsetX*2*i);
					int posY=offsetY+(offsetY*2*j);
					c=new Circulo(posX,posY,r);
					ind=new Individuo(c);
					arrayPoblacionPadres.add(ind);
					//System.out.print("(x-"+posX+")^2+(y-"+posY+")^2="+r+"^2, ");  // para plot
					}
				}
			//System.out.println();  // para plot
			// Círculos de cuadrícula 5x5
			for (int i=0; i<=4; i++)
				{
				r=((Problema.DIMENSION/5)/2);
				offsetX=r;
				offsetY=r;
				for (int j=0; j<=4; j++)
					{
					int posX=offsetX+(offsetX*2*i);
					int posY=offsetY+(offsetY*2*j);
					c=new Circulo(posX,posY,r);
					ind=new Individuo(c);
					arrayPoblacionPadres.add(ind);
					//System.out.print("(x-"+posX+")^2+(y-"+posY+")^2="+r+"^2, ");  // para plot
					}
				}
			//System.out.println();  // para plot
			}
		//////////////////////////////////
		// 0.2 ¿Número de generaciones? //
		//////////////////////////////////
		System.out.print("Introduce número de generaciones (recomendado 10000) [10<=nGEN<=1000000]: ");
	    numGeneraciones = inGEN.nextInt();
	    if (numGeneraciones<10) 
	    	{
	    	System.out.println("   Se usarán 10 generaciones");
	    	numGeneraciones=10;
	    	}
	    else if (numGeneraciones>1000000) 
	    	{
	    	System.out.println("   Se usarán 1000000 generaciones");
	    	numGeneraciones=1000000;
	    	}
		/////////////////////////
		// 0.3 ¿Tasa de cruce? //
		/////////////////////////
	    System.out.print("Introduce tasa de cruce (recomendado 0,7) [0<tasaCruce<1]: ");
	    tasaCruce = inGEN.nextFloat();
	    if (tasaCruce<=0 || tasaCruce >=1) 
	    	{
	    	System.out.println("   Se usará tasa de cruce 0,7");
	    	tasaCruce=0.7f;
	    	}
		////////////////////////////
		// 0.4 ¿Tasa de mutación? //
		////////////////////////////
	    System.out.print("Introduce tasa de mutación (recomendado 0,3) [0<tasaMutación<1]: ");
	    tasaMutacion = inGEN.nextFloat();
	    if (tasaMutacion<=0 || tasaMutacion >=1) 
	    	{
	    	System.out.println("   Se usará tasa de mutación 0,3");
	    	tasaMutacion=0.3f;
	    	}
	    //////////////////////////
	    // 0.5 ¿Mantener élite? //
	    //////////////////////////
		boolean keepElite=false;  // Nos indica si mantenemos el 10% de la élite entre generaciones
		System.out.print("Introduce % de élite para mantener (recomendado 5%) [0<porcentajeElite<=20]: ");
	    porcentajeElite = inGEN.nextInt();
	    if (porcentajeElite==0)
	    	keepElite=false;
	    else
	    	{
	    	keepElite=true;
		    if (porcentajeElite<5)
		    	{
		    	System.out.println("   Se usará porcentaje de élite 5%");
		    	porcentajeElite=5;
		    	}
		    else if (porcentajeElite>20) 
		    	{
		    	System.out.println("   Se usará porcentaje de élite 20%");
		    	porcentajeElite=20;
		    	}
	    	}
		///////////////////////////////////////
		// 0.6 ¿Nivel de corte de cromosoma? //
		///////////////////////////////////////
	    boolean chooseAgain=true;
	    int option=-1;
		corteANivelGen=false;
		while (chooseAgain)
			{
			System.out.print("¿Corte por gen(0) o por atributo(1)? (recomendado gen) [0/1]: ");
			option = inGEN.nextInt();
			if (option==0) 
				{
				corteANivelGen=true;
				chooseAgain=false;
				}
			else if (option==1)
				{
				corteANivelGen=false;
				chooseAgain=false;
				}
			}
		////////////////////////////////////
		// 1. Generando poblaciones hijas //
		////////////////////////////////////
	    for (int generations=0; generations<numGeneraciones; generations++)
	    	{
			numIndividuosHijos=0;
			float fitnessTotal=0;
			///////////////////////////////////////////////////////////////
			// 1.1 Muestra de la población actual 'arrayPoblacionPadres' //
			///////////////////////////////////////////////////////////////
			// Calculo del fitness de cada individuo y el fitness total de la población
			for (int i=0; i<numIndividuosPadres; i++)
				{	
				arrayPoblacionPadres.get(i).calculateFitness(geneticos);
				fitnessTotal=fitnessTotal+arrayPoblacionPadres.get(i).getFitness();
				}
			// Ordenación e impresión
			Collections.sort(arrayPoblacionPadres);  // Se ordenan de menor a mayor fitness
			if (generations==0) 
				System.out.println("----- Generación inicial -----");
			else 
				System.out.println("----- Generación "+generations+" -----");
			for (int i=0; i<numIndividuosPadres; i++)
				System.out.println(arrayPoblacionPadres.get(i).toString());  // Todos los individuos de la población
			Circulo c=arrayPoblacionPadres.get(numIndividuosPadres-1).toCirculo();  // El individuo con mejor fitness
			System.out.println("Mejor (cromosoma): "+arrayPoblacionPadres.get(numIndividuosPadres-1).toString());
			System.out.println("Mejor (círculo): "+c.toString());
			System.out.println("Mejor (plot WolframAlpha): "+c.toPlot());
			if (geneticos.esSolucion(c))  // ¿Es solución el individuo con mejor fitness?
				System.out.println("Es solución");
			else
				System.out.println("No es solución");
			// Calculo del fitness acumulado para ejecutar ruleta
			for (int i=0; i<numIndividuosPadres; i++)
				{
				if (i==0)
					arrayProbCruce.add(arrayPoblacionPadres.get(i).getFitness()/fitnessTotal);
				else
					arrayProbCruce.add((arrayPoblacionPadres.get(i).getFitness()/fitnessTotal)+arrayProbCruce.get(i-1));
				//System.out.println(arrayProbCruce.get(i));  // Test para ver si genera bien la ruleta
				}
			/////////////////////////////////////////////////////
		    // 1.2 Crear población nueva 'arrayPoblacionHijos' //
			/////////////////////////////////////////////////////
		    while (numIndividuosPadres>numIndividuosHijos)  // La siguiente generación se completa cuando 'numIndividuosPadres==numIndividuosHijos' 
		    	{
		    	Random rnd=new Random();  // Genera aleatorios entre 0 y 1 
		    	Individuo individuoA=null, individuoB=null;  // Individuos candidatos a cruzarse
		    	Individuo individuoAB=null, individuoBA=null;  // Individuos hijos de 'individuoA' e 'individuoB'
		    	///////////////////////
			    // A. Mantener élite //
			    ///////////////////////
		    	if (keepElite)  // Si hemos decidido salvar la élite...
		    		{
			    	int numElite=(int)(numIndividuosPadres/100.0*porcentajeElite);  // ...salvamos al porcentaje de individuos acordado...
			    	for (int i=numIndividuosPadres-numElite; i<numIndividuosPadres; i++)  // ...que se van de 'arrayPoblacionPadres' a 'arrayPoblacionHijos'
			    		{
			    		Individuo individuoElite=arrayPoblacionPadres.get(i);
			    		arrayPoblacionHijos.add(individuoElite);
			    		numIndividuosHijos++;
			    		}
		    		}
		    	///////////////
		    	// B. Ruleta //
		    	///////////////
		    	// 'randomA' y 'randomB' generan aleatorios entre 0 y 1 que sirven para elegir el 'individuoA' e 'individuoB'
		    	// Se recorre el array 'arrayProbCruce' y cuando el aleatorio generado sea mayor que el de la posición
		    	// por la que vamos en 'arrayProbCruce' nos quedamos con ese índice que será el que servirá para elegir
		    	// al individuo
		    	float randomA=rnd.nextFloat(); int posIndA=0;
		    	float randomB=rnd.nextFloat(); int posIndB=0;
		    	boolean encontrado=false;
		    	while (!encontrado)
		    		{
		    		if (arrayProbCruce.get(posIndA)<randomA)
		    			posIndA++;
		    		else
		    			{
		    			encontrado=true;
		    			individuoA=arrayPoblacionPadres.get(posIndA);
		    			}
		    		}
		    	encontrado=false;
		    	while (!encontrado)
		    		{
		    		if (arrayProbCruce.get(posIndB)<randomB)
		    			posIndB++;
		    		else
		    			{
		    			encontrado=true;
		    			individuoB=arrayPoblacionPadres.get(posIndB);
		    			}
		    		}
		    	//////////////
		    	// C. Cruce //
		    	//////////////
		    	// Una vez hemos elegido a los dos candidatos y los tenemos en 'individuoA' e 'individuoB' debemos ver si
		    	// los cruzamos o no. Para ello comparamos el aleatorio 'decideCruce' (entre 0 y 1) con la tasa de cruce
		    	// 'tasaCruce' (0,7). Si 'decideCruce' es menor procedemos a cruzar a los individuos en una posición aleatoria.
		    	// El cruce se producirá en consecuencia a lo que decidimos (nivel de gen o nivel de atributo)
		    	float decideCruce=rnd.nextFloat();
		    	boolean hayCruce=false;
		    	//corteANivelGen=true;
		    	String cromIndivAB=null; String cromIndivBA=null;
		    	if (decideCruce<tasaCruce)
		    		{
		    		if (corteANivelGen)  // Cortamos en cualquier posición posible
		    			{
			    		int dondeCruza=(int)(rnd.nextFloat()*29);  // Genera números entre 0 y 28
			    		String cromIndivAB_1=individuoA.getCromosoma().substring(0,dondeCruza+1);
			    		String cromIndivAB_2=individuoB.getCromosoma().substring(dondeCruza+1);
			    		cromIndivAB=cromIndivAB_1+cromIndivAB_2;
			    		String cromIndivBA_1=individuoB.getCromosoma().substring(0,dondeCruza+1);
			    		String cromIndivBA_2=individuoA.getCromosoma().substring(dondeCruza+1);
			    		cromIndivBA=cromIndivBA_1+cromIndivBA_2;
			    		hayCruce=true;
			    		}
		    		else  // Cortamos entre 'x' e 'y' o entre 'y' y 'r'
		    			{
		    			int dondeCruza=(int)(rnd.nextFloat()*2+1);  // Genera números entre 1 y 2
		    			String cromIndivAB_1=individuoA.getCromosoma().substring(0,dondeCruza*10);
			    		String cromIndivAB_2=individuoB.getCromosoma().substring(dondeCruza*10);
			    		cromIndivAB=cromIndivAB_1+cromIndivAB_2;
			    		String cromIndivBA_1=individuoB.getCromosoma().substring(0,dondeCruza*10);
			    		String cromIndivBA_2=individuoA.getCromosoma().substring(dondeCruza*10);
			    		cromIndivBA=cromIndivBA_1+cromIndivBA_2;
			    		hayCruce=true;
		    			}
		    		}
		    	/////////////////
		    	// D. Mutación //
		    	/////////////////
		    	// Solo mutamos después del cruce. Generamos un aleatorio 'decideMutacion' (entre 0 y 1)
		    	// que comparamos con 'tasaMutacion' (0,1) y en el caso de que 'decideMutacion' sea menor
		    	// modificamos el gen por su contrario. Se repite el proceso para todos los genes de los
		    	// individuos resultado del cruce
		    	if (hayCruce)
		    		{
		    		float decideMutacionAB;
		    		float decideMutacionBA;
		    		for (int i=0; i<30; i++)  // Recorremos todos los genes de los dos cromosomas resultado de los cruzados
		    			{
		    			decideMutacionAB=rnd.nextFloat();
		    			if (decideMutacionAB<tasaMutacion)
			    			{
		    				String genMutado;
		    				String genSinMutar=cromIndivAB.substring(i,i+1);
		    				if (genSinMutar.equals("0")) 
		    					genMutado="1";
		    				else 
		    					genMutado="0";
			    			cromIndivAB=cromIndivAB.substring(0,i)+genMutado+cromIndivAB.substring(i+1);
			    			}
			    		decideMutacionBA=rnd.nextFloat();
			    		if (decideMutacionBA<tasaMutacion)
			    			{
		    				String genMutado;
		    				String genSinMutar=cromIndivBA.substring(i,i+1);
		    				if (genSinMutar.equals("0")) 
		    					genMutado="1";
		    				else 
		    					genMutado="0";
			    			cromIndivBA=cromIndivBA.substring(0,i)+genMutado+cromIndivBA.substring(i+1);
			    			}
		    			}
		    		individuoAB=new Individuo(cromIndivAB,0);
		    		individuoBA=new Individuo(cromIndivBA,0);
		    		individuoAB.calculateFitness(geneticos);
		    		individuoBA.calculateFitness(geneticos);
		    		arrayPoblacionHijos.add(individuoAB);
		    		arrayPoblacionHijos.add(individuoBA);
		    		numIndividuosHijos=numIndividuosHijos+2;
		    		}
		    	}	    
		    ///////////////////////////////////
		    // 1.3 Preparar nueva generación //
			///////////////////////////////////	
		    if (!(generations==numGeneraciones-1))
		    	{
		    	arrayPoblacionPadres.clear();
		    	arrayPoblacionPadres=(ArrayList<Individuo>)arrayPoblacionHijos.clone();
		    	arrayPoblacionHijos.clear();
	    		}
	    	}
		//////////////////////////
		// 2. Resumen genéticos //
		//////////////////////////
	    Circulo cGEN=arrayPoblacionPadres.get(numIndividuosPadres-1).toCirculo();
		System.out.println("+--------------------------------------------+");
		System.out.println("|  RESUMEN ALGORITMOS GENÉTICOS              |");
		System.out.println("+--------------------------------------------+");
	    System.out.println("|    Configuración:                          |");
		System.out.println("+--------------------------------------------+");
		System.out.println("| -Individuos por generación: "+numIndividuosPadres);
	    System.out.println("| -Generaciones: "+numGeneraciones);
	    System.out.println("| -Tasa de cruce: "+tasaCruce);
	    System.out.println("| -Tasa de mutación: "+tasaMutacion);
	    System.out.println("| -Porcentaje élite: "+porcentajeElite+"%");
		if (corteANivelGen)
		System.out.println("| -Corte a nivel gen");
		else
		System.out.println("| -Corte a nivel atributo");	
		System.out.println("+--------------------------------------------+");
	    System.out.println("|    Resultados:                             |");
		System.out.println("+--------------------------------------------+");
		System.out.println("| -Círculo solución: "+cGEN.toString());
		System.out.println("| -WolframAlpha:");
		System.out.println("| Círculo solución: plot "+cGEN.toPlot());
		System.out.println("| Círculos problema: plot "+geneticos.toPlot());
		System.out.println("| Todo: plot "+cGEN.toPlot()+", "+geneticos.toPlot());
		System.out.println("+--------------------------------------------+");
		System.out.println();
		System.out.println("Presiona ENTER para continuar con 'fuerza bruta' y 'aleatorio'");
		System.in.read();
		
		/**
		 * FUERZA BRUTA
		 * -Con 'i' y 'j' recorremos todos los puntos del cuadrado.
		 * -Con 'k' creamos todos los círculos hasta el radio máximo que
		 * puede existir dentro del cuadrado que que es 'DIMENSION/2'.
		 * -Cada vez que encontremos una solución mejor que la guardada
		 * la imprimimos.
		 * -Con 'fail' contamos los círculos generados que no fueron solución.
		 * -Con 'success' contamos los círculos generados que fueron solución (mejor que la anterior).
		 */
		System.out.println("+--------------------------------------------------+");
		System.out.println("|  FUERZA BRUTA                                    |");
		System.out.println("+--------------------------------------------------+");
		Problema fuerzaBruta=new Problema("inicial.txt");
		int failFB=0; int successFB=0;
		for (int i=0; i<Problema.DIMENSION; i++)
			{
			for (int j=0; j<Problema.DIMENSION; j++)
				{
				for (int k=0; k<Problema.DIMENSION/2; k++)
					{
					Circulo cFB=new Circulo(i,j,k);
					if (fuerzaBruta.esMejorSolucion(cFB))
						{
						fuerzaBruta.setCirculoSol(cFB);
						System.out.println("Solución parcial: "+cFB.toString());
						successFB++;
						}
					else
						failFB++;
					}
				}
			}
		int totalFB=successFB+failFB;
		System.out.println("+--------------------------------------------+");
		System.out.println("|  RESUMEN FUERZA BRUTA                      |");
		System.out.println("+--------------------------------------------+");
		System.out.println("| Círculos generados: "+totalFB);
		System.out.println("| Círculos solución: "+successFB);
		System.out.println("| Círculos no solución: "+failFB);
		System.out.println("+--------------------------------------------+");
	    System.out.println("|    Resultados:                             |");
		System.out.println("+--------------------------------------------+");
		System.out.println("| Solución final: "+fuerzaBruta.getCirculoSol().toString());
		System.out.println("| -WolframAlpha:");
		System.out.println("| Círculo solución: plot "+fuerzaBruta.getCirculoSol().toPlot());
		System.out.println("| Círculos problema: plot "+fuerzaBruta.toPlot());
		System.out.println("| Todo: plot "+fuerzaBruta.getCirculoSol().toPlot()+", "+fuerzaBruta.toPlot());
		System.out.println("+--------------------------------------------+");
		System.out.println();
		
		/**
		 * ALEATORIO
		 * -Con 'i' creamos 'n' círculos aleatoriamente.
		 * -Cada vez que encontremos una solución mejor que la guardada
		 * la imprimimos.
		 * -Con 'fail' contamos los círculos generados que no fueron solución.
		 * -Con 'success' contamos los círculos generados que fueron solución (mejor que la anterior).
		 */
		System.out.println("+--------------------------------------------------+");
		System.out.println("|  ALEATORIO                                       |");
		System.out.println("+--------------------------------------------------+");
		Problema aleatorio=new Problema("inicial.txt");
		int failRND=0; int successRND=0;
		Scanner inRND = new Scanner(System.in); 
	    System.out.printf("Introduce un número aleatorio: ");
	    int nRND = inRND.nextInt();
		for (int i=0; i<nRND; i++)
			{
			Circulo cRND=Circulo.random(Problema.DIMENSION);
			if (aleatorio.esMejorSolucion(cRND))
				{
				aleatorio.setCirculoSol(cRND);
				System.out.println("Solución parcial: "+cRND.toString());
				successRND++;
				}
			else
				failRND++;
			}
		int totalRND=successRND+failRND;
		System.out.println("+--------------------------------------------+");
		System.out.println("| RESUMEN ALEATORIO                          |");
		System.out.println("+--------------------------------------------+");
		System.out.println("| Círculos generados: "+totalRND);
		System.out.println("| Círculos solución: "+successRND);
		System.out.println("| Círculos no solución: "+failRND);
		System.out.println("+--------------------------------------------+");
	    System.out.println("|    Resultados:                             |");
		System.out.println("+--------------------------------------------+");
		System.out.println("| Solución final: "+aleatorio.getCirculoSol().toString());
		System.out.println("| -WolframAlpha:");
		System.out.println("| Círculo solución: plot "+aleatorio.getCirculoSol().toPlot());
		System.out.println("| Círculos problema: plot "+aleatorio.toPlot());
		System.out.println("| Todo: plot "+aleatorio.getCirculoSol().toPlot()+", "+aleatorio.toPlot());
		System.out.println("+--------------------------------------------+");
		System.out.println();
		}
	}
