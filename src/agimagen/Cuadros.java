
package agimagen;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
/**
 *
 * @author Rogelio
 */
public class Cuadros implements Paintable {
    	private float x, y, ancho, alto;
	private Color color;
	
	public Cuadros() {
		
	}
	
	private Cuadros(float x, float y, float ancho, float alto, Color color) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.color = color;
	}
	
	public Cuadros(Imagen p) {
		this();
		randomize(p);
	}

	@Override
	public void paint(BufferedImage bi, int paintMethod) {
		   if (paintMethod == Imagen.Metodo_solapamiento) {
		
			   // Crear un contexto gráfico en la imagen almacenada en el buffer
			   Graphics2D g2d = bi.createGraphics();
			   
			   // Dibujar en la imagen almacenada en el buffer
			   g2d.setColor(color);
			   g2d.fill(new Rectangle2D.Float(x, y, ancho, alto));
			   g2d.dispose();
		   }
		   else if (paintMethod == Imagen.Metodo_transparente) {

			   Graphics2D g2d = bi.createGraphics();
			    

			   Color transparent = new Color(color.getRed(), color.getGreen(), color.getBlue(), 127);
			   g2d.setColor(transparent);
			   g2d.setComposite(AlphaComposite.SrcOver);
			   g2d.fill(new Rectangle2D.Float(x, y, ancho, alto));
			   g2d.dispose();
		   }
	}

	@Override
	public void randomize(Imagen p) {
			x = GeneradorRandom.getFloatRandom() * p.getAncho();
			y = GeneradorRandom.getFloatRandom() * p.getAlto();
			ancho = GeneradorRandom.getFloatRandom() * p.getAncho() / 8;
			alto = GeneradorRandom.getFloatRandom() * p.getAlto() / 8;
			color = GeneradorRandom.getColorRandom();
	}

	@Override
	public Paintable copiar() {
		return new Cuadros(x, y, ancho, alto, color);
	}

	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getAncho() {
		return ancho;
	}

	public void setAncho(float ancho) {
		this.ancho = ancho;
	}

	public float getAlto() {
		return alto;
	}

	public void setAlto(float alto) {
		this.alto = alto;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}
