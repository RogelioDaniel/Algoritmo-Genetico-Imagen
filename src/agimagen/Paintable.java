
package agimagen;
import java.awt.image.BufferedImage;
/**
 *
 * @author Rogelio
 */

//
public interface Paintable {

	public void paint(BufferedImage bi, int metodoPintado);

       
	public void randomize(Imagen p);
	

	public Paintable copiar();
}