
package agimagen;
import java.awt.Color;
import java.util.Random;

/**
 *
 * @author Rogelio
 */
public class GeneradorRandom {
    	
	private static Random rand = null;

	private GeneradorRandom() {}
	
	private static void verificarInstancia() {
		if (rand == null) {
			rand = new Random();
		}
	}

	public static Color getColorRandom() {
		verificarInstancia();
	
		return Color.getHSBColor(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
	}
	
	/**
	 * Genera un valor int aleatorio.
	 */
	public static int getIntRandom(int limite) {
		verificarInstancia();
		return rand.nextInt(limite);
	}
	

	public static int getIntRandom() {
		verificarInstancia();
		return rand.nextInt();
	}
	
	/**
	 * Genera un valor flotante aleatorio.
	 * entre 0.0 to 1.0
	 */
	public static float getFloatRandom() {
		verificarInstancia();
		return rand.nextFloat();
	}
	


	


}
