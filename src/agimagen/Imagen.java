
package agimagen;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Rogelio
 * 
 */
public class Imagen implements Comparable<Imagen> {
 	public static final int Metodo_transparente = 0;
	public static final int Metodo_solapamiento = 1;
	public static final Color color_Fondo = Color.WHITE;
	
	private BufferedImage image;
	private int ancho;
	private int alto;
	private int metodoDePintado;
	private ArrayList<Paintable> array_formas;
	private boolean x;
	double entrenamiento;
	

	public Imagen(int ancho, int alto, int metodoDePintado) {
		this.ancho = ancho;
		this.alto = alto;
		this.metodoDePintado = metodoDePintado;
		this.array_formas = new ArrayList<Paintable>();
		x = false;
	}

	public BufferedImage getImagen(int metodoPintado) {
		
		if (x) {
			return image;
		}
		
		image = new BufferedImage(ancho, alto, BufferedImage.TYPE_4BYTE_ABGR);
		
		Graphics2D grafica = image.createGraphics();

		grafica.setPaint ( (Paint)color_Fondo );
		grafica.fillRect ( 0, 0, image.getWidth(), image.getHeight() );
		
		for (Paintable p : array_formas) {
			if (p != null) {
				p.paint(image, metodoPintado);
			}
		}
		
		return image;
	}
	

	//Pinta todas las formas en la imagen con el método de pintura por defecto (Esto puede tomar algún tiempo)

	public BufferedImage getImagen() {
		return getImagen(metodoDePintado);
	}
	
	/**
        Agrega la copia de p a la lista de formas
	 */
	public void elementoForma(Paintable p) {
		x = false;
		array_formas.add(p.copiar());
	}
	
	/**
        Inserta la copia de p en la lista de formas
	 */
	public void insertarForma(int index, Paintable p) {
		x = false;
		array_formas.add(index, p.copiar());
	}
	
	/**
        Devuelve el objeto de forma original
	 */
	public Paintable getForma(int index) {
		x = false;
		return array_formas.get(index);
	}
	
	/**
	  Establecer formas de p para copiar
	 */
	public void setForma(int index, Paintable p) {
		x = false;
		array_formas.set(index, p.copiar());
	}
	
	/**
         * Obtener contador de formas
	 */
	public int getContadorFormas() {
		return array_formas.size();
	}
	
	public void eliminarForma(int index) {
		x = false;
		array_formas.remove(index);
	}
	
	/**
	 Limpiar todas las formas
	 */
	public void limpiarFormas() {
		x = false;
		array_formas.clear();
	}
	
    public int compareTo(Imagen object){

        if(entrenamiento==object.entrenamiento){
            return 0;
        }            
        else if(entrenamiento>object.entrenamiento)
            return -1;
        else
            return 1;
    }

    
	
	public int getAncho() {
		return ancho;
	}

	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	public int getAlto() {
		return alto;
	}
	
	public void setAlto(int alto) {
		this.alto = alto;
	}

	public int getMetodoPintado() {
		return metodoDePintado;
	}

	public void setMetodoPintado(int metodoDePintado) {
		this.metodoDePintado = metodoDePintado;
	}
	
}
