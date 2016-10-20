/**
 * Representa un individuo de la poblaci�n como cromosoma. Almacena el valor de fitness 
 * por comodidad, para ordenar la poblaci�n.
 */
public class Individuo implements Comparable<Individuo> 
	{
	// N�mero de genes para representar cada entero
	private final int PADDING = 10;
	
	// Representación binaria del círculo con centro (x,y) y radio r. Se representa como
	// la concatenación de las cadenas x + y + r donde cada una consta de 10 dígitos binarios.
	private String cromosoma;
	
	// Fitness de este individuo
	private float fitness;
	
	/**
	 * Constructor a partir del cromosoma. 
	 */
	public Individuo(String cromosoma, float fitness) 
		{
		this.cromosoma = cromosoma;
		this.fitness = fitness;
		}

	/**
	 * Constructor a partir del círculo.
	 */
	public Individuo(Circulo c) 
		{
		cromosoma = int2str(c.getX()) + int2str(c.getY()) + int2str(c.getRadio());
		}
	
	/**
	 * Devuelve el c�rculo representado por este cromosoma.
	 */
	public Circulo toCirculo() 
		{
		int x = Integer.parseInt(cromosoma.substring(0, PADDING), 2);
		int y = Integer.parseInt(cromosoma.substring(PADDING, 2*PADDING), 2);
		int r = Integer.parseInt(cromosoma.substring(2*PADDING, 3*PADDING), 2);
		return new Circulo(x, y, r);
		}
	
	public float getFitness() 
		{
		return fitness;
		}

	public void setFitness(float fitness) 
		{
		this.fitness = fitness;
		}

	public String getCromosoma() 
		{
		return cromosoma;
		}
	
	/**
	 * Calcula lo adecuado que es el individuo dentro de un Problema 'p'. Nos 
	 * basamos en varias cosas
	 * -Si el c�rculo candidato solapa con alguno del problema o se sale de las dimensiones
	 * se queda con un fitness de 1 para que por ruleta al menos tenga probabilidad de ser escogido
	 * -Si el c�rculo no solapa ni se sale de las dimensiones su fitness es su radio
	 */
	public void calculateFitness(Problema p)
		{
		float f=0;
		Circulo c=toCirculo();
		if (c.getRadio()<=Problema.DIMENSION/2)
			f=c.getRadio();
		if (!c.dentroDeCuadrado(Problema.DIMENSION) || p.solapa(c))
			f=1;
		fitness=f;
		}

	/**
	 * Devuelve la cadena que representa al n�mero n en binario con 10 posiciones.
	 * Las posiciones de la izquierda sobrantes se ponen a cero.
	 */
	private String int2str(int n) 
		{
		return String.format("%"+PADDING+"s", Integer.toBinaryString(n)).replace(' ', '0');
		}

	/**
	 * Comparaci�n por fitness para ordenar la poblaci�n de individuos.
	 */
	@Override
	public int compareTo(Individuo o) 
		{
		return Float.compare(fitness, o.fitness);
		}

	@Override
	public String toString() 
		{
		String cromosomaX=cromosoma.substring(0,10);
		String cromosomaY=cromosoma.substring(10,20);
		String cromosomaR=cromosoma.substring(20,30);
		return "[" + cromosomaX + "|" + cromosomaY + "|" + cromosomaR + ",F=" + fitness + "]";
		//return "[" + cromosoma + ",F=" + fitness + "]";
		}
	}
