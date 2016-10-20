/**
 * Representa un círculo a partir de su centro y su radio.
 * 
 * @author Antonio S�nchez
 */
public class Circulo 
	{
	
	private int x, y;  // centro del círculo
	private int radio;
	
	/**
	 * Devuelve un círculo aleatorio con 0 <= x,y < dimensiones
	 * y 0 <= r < dimensiones / 2.
	 */
	public static Circulo random(int dimensiones) 
		{
		int x = Util.randomInt(0, dimensiones);
		int y = Util.randomInt(0, dimensiones);
		int r = Util.randomInt(1, dimensiones / 2);
		return new Circulo(x, y, r);
		}
	
	/**
	 * Constructor a partir del centro (x,y) y el radio.
	 */
	public Circulo(int x, int y, int radio) 
		{
		this.x = x;
		this.y = y;
		this.radio = radio;
		}
	
	public int getX() 
		{
		return x;
		}

	public int getY() 
		{
		return y;
		}

	public int getRadio() 
		{
		return radio;
		}

	public void setRadio(int radio) 
		{
		this.radio = radio;
		}

	/**
	 * Calcula si hay intersección entre este círculo y c. Si sólo se tocan en 
	 * un punto (son tangentes) decimos que no hay intersección.
	 */
	public boolean interseccion(Circulo c) 
		{
		return Util.distEuclidea(x, y, c.x, c.y) < radio + c.radio;
		}
	
	/**
	 *  Calcula si este círculo está dentro del cuadrado apoyado en el (0,0) 
	 *  con longitud de lado dimensiones.
	 */
	public boolean dentroDeCuadrado(int dimensiones) 
		{
		return (radio <= x) && (radio <= y) && (radio < dimensiones - x) && (radio < dimensiones - y);
		}

	@Override
	public String toString() 
		{
		return "[x=" + x + ", y=" + y + ", r=" + radio + "]";
		}
	
	public String toPlot()
		{
		return "(x-"+x+")^2+(y-"+y+")^2="+radio+"^2";
		}
	}
