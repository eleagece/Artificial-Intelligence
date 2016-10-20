
/**
 * Utilidades.
 * 
 * @author Antonio Sánchez
 */
public class Util 
	{
	/**
	 *  Valor entero aleatorio en [min, max).
	 */
	public static int randomInt(int min, int max) 
		{
		return (int) Math.floor(Math.random() * (max - min + 1) + min);  
		}
	
	/**
	 *  Valor float aleatorio en [0, 1)
	 */
	public static float random() 
		{
		return (float) Math.random();
		}
	
	/**
	 *  Valor float aleatorio en [0, max)
	 */
	public static float random(float max) 
		{
		return (float) Math.random() * max;
		}
	
	/**
	 *  Distancia euclidea entre los puntos (x1, y1) y (x2, y2)
	 */
	public static float distEuclidea(int x1, int y1, int x2, int y2) 
		{
		return (float) Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
		}
	}
