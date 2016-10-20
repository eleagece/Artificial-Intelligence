import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Representa el conjunto de cÃ­rculos inicial del problema.
 * 
 * @author Antonio SÃ¡nchez
 */
public class Problema 
	{
	/** TamaÃ±o del cuadrado */
	public static int DIMENSION = 1024;
	
	// Lista de cÃ­rculos
	private List<Circulo> circulos;
	private Circulo circuloSol;

	/**
	 * Crea un problema sin cÃ­rculos.
	 */
	public Problema() 
		{
		circulos = new ArrayList<>();
		}
	
	/**
	 * Lee un problema a partir de un fichero de texto. 
	 */
	public Problema(String fichero) throws FileNotFoundException 
		{
		Scanner sc = new Scanner(new File(fichero));
		circulos = new ArrayList<>();
		
		int numCirculos = sc.nextInt();
		for (int i=0; i<numCirculos; i++) 
			{
			int x = sc.nextInt();
			int y = sc.nextInt();
			int r = sc.nextInt();
			circulos.add(new Circulo(x, y, r));
			}
		sc.close();
		}
	
	public List<Circulo> getCirculos() 
		{
		return circulos;
		}

	public void addCirculo(Circulo c) 
		{
		circulos.add(c);
		}
	
	public void setCirculoSol(Circulo c)
		{
		circuloSol=c;
		}
	
	Circulo getCirculoSol()
		{
		return circuloSol;
		}
	
	/**
	 * Indica si el círculo 'c' solapa con algún círculo del problema. En caso afir-
	 * mativo devuelve 'true'. En otro caso devuelve 'false'
	 */
	public boolean solapa(Circulo c) 
		{
		for (Circulo circulo: circulos) 
			{
			if (circulo.interseccion(c))
				return true;
			}
		return false;
		}
	
	/**
	 * Indica si el círculo c es una solución válida del problema: si está dentro
	 * del cuadrado y no solapa con ninguno de los círculos existentes.
	 */
	public boolean esSolucion(Circulo c) 
		{
		// Está dentro del cuadrado
		if (!c.dentroDeCuadrado(DIMENSION))
			return false;
		
		// No tiene intersección con los otros círculos
		for (Circulo circulo: circulos) 
			{
			if (circulo.interseccion(c))
				return false;
			}
		return true;
		}
	
	/**
	 * Actualiza 'circuloSol' con 'c' si 'c' resulta ser la mejor solución encontrada hasta
	 * ahora. Devuelve 'true' en ese caso y 'false' en otro.
	 */
	public boolean esMejorSolucion(Circulo c) 
		{
		if (esSolucion(c) && (circuloSol==null || circuloSol.getRadio()<c.getRadio()))
			return true;
		else
			return false;
		}
	
	public String toPlot()
		{
		String plot="";
		for (int i=0; i<this.circulos.size(); i++)
			{
			plot=plot+"(x-"+circulos.get(i).getX()+")^2+(y-"+circulos.get(i).getY()+")^2="+circulos.get(i).getRadio()+"^2, ";
			}
		return plot;
		}
	}
