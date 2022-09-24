package agimagen;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
/**
 *
 * @author Rogelio
 *  // https://javadoc.scijava.org/Icy/icy/image/ImageUtil.html
 */
public class ImageUtil {

	public static BufferedImage copiarImagen(BufferedImage imagenn){
	    BufferedImage b = new BufferedImage(imagenn.getWidth(), imagenn.getHeight(), imagenn.getType());
	    Graphics g = b.getGraphics();
	    g.drawImage(imagenn, 0, 0, null);
	    g.dispose();
	    return b;
	}
	

	public static BufferedImage imagennCambiarTam(BufferedImage imagen, int newX, int newY, boolean proporcional) {	
		double escaladoX = (double)newX / imagen.getWidth();
		double escaladoY = (double)newY / imagen.getHeight();
		
		if (proporcional) {
			if (escaladoX > escaladoY) {
				escaladoX = escaladoY;
			}
			else
			{
				escaladoY = escaladoX;
			}
		}
		
    	AffineTransform at = new AffineTransform(); // La clase representa una transformada afín 2D que realiza un mapeo lineal de coordenadas 2D a otras coordenadas 2D que conserva la "rectitud" y el "paralelismo" de las líneas. Las transformaciones afín se pueden construir utilizando secuencias de traslaciones, escalas.
    	at.scale(escaladoX, escaladoY);
    	AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
    	return  scaleOp.filter(imagen, null);
	}
	
    /**

     */
    public static BufferedImage cargarImagen(String nombreImagen) {
    	BufferedImage map = null;
    	try {
    		map = ImageIO.read(new File(nombreImagen));
    	} catch (IOException e) {
    		System.err.println(nombreImagen + " Imagen no encontrada");
    	}
    	return map;
    }
    


    public static double difImagen_matiz_saturacion_brillo(BufferedImage imagen1, BufferedImage imagen2) throws Exception {

        final byte[] pixeles1 = ((DataBufferByte) imagen1.getRaster().getDataBuffer()).getData();
        final byte[] pixeles2 = ((DataBufferByte) imagen2.getRaster().getDataBuffer()).getData();
        final boolean tiene_canal_alpha1 = imagen1.getAlphaRaster() != null;
        final boolean tiene_canal_alpha2 = imagen2.getAlphaRaster() != null;
    	float m_s_b_1[] = new float[3];
    	float m_s_b_2[] = new float[3];
    	float dH, dS, dB;
    	double distancia = 0;
    	int contadorPixelesImagen;
       
    	// Encontrar la longitud de los píxeles
        int longitudPixel1 = 3;
        int longitudPixel2 = 3;
        
        if (tiene_canal_alpha1) {
        	longitudPixel1 = 4;
        }
        
        if (tiene_canal_alpha2) {
        	longitudPixel2 = 4;
        }
        
        // 

        // Encontrar tamaño imagen
        contadorPixelesImagen = pixeles1.length / longitudPixel1;
        
        for (int pixel1 = 0, pixel2 = 0; pixel1 < pixeles1.length && pixel2 < pixeles2.length; pixel1 += longitudPixel1, pixel2 += longitudPixel2) {
        	
        	if (tiene_canal_alpha1) {
        		
        		Color.RGBtoHSB((int)pixeles1[pixel1 + 3] & 0xff, (int)pixeles1[pixel1 + 2] & 0xff, (int)pixeles1[pixel1 + 1] & 0xff, m_s_b_1);
        	}
        	else
        	{
        		
        		Color.RGBtoHSB((int)pixeles1[pixel1 + 2] & 0xff, (int)pixeles1[pixel1 + 1] & 0xff, (int)pixeles1[pixel1] & 0xff, m_s_b_1);
        	}
        	
        	
        	if (tiene_canal_alpha2) {
        		
        		Color.RGBtoHSB((int)pixeles2[pixel2 + 3] & 0xff, (int)pixeles2[pixel2 + 2] & 0xff, (int)pixeles2[pixel2 + 1] & 0xff, m_s_b_2);
        	}
        	else
        	{
        	
        		Color.RGBtoHSB((int)pixeles2[pixel2 + 2] & 0xff, (int)pixeles2[pixel2 + 1] & 0xff, (int)pixeles2[pixel2] & 0xff, m_s_b_2);
        	}
        	
            dH = Float.min(Math.abs(m_s_b_1[0] - m_s_b_2[0]), 1.0f - Math.abs(m_s_b_1[0] - m_s_b_2[0]));
            dS = Math.abs(m_s_b_1[1]-m_s_b_2[1]);
            dB = Math.abs(m_s_b_1[2]-m_s_b_2[2]);
        	
            distancia += Math.sqrt(dH*dH + dS*dS + dB*dB);
        }   
        
        return distancia / contadorPixelesImagen;
     }
    

    public static double difImagen_RGB(BufferedImage imagen1, BufferedImage imagen2) throws Exception {

        final byte[] pixeles1 = ((DataBufferByte) imagen1.getRaster().getDataBuffer()).getData();
        final byte[] pixeles2 = ((DataBufferByte) imagen2.getRaster().getDataBuffer()).getData();
        final boolean tiene_canal_alpha1 = imagen1.getAlphaRaster() != null;
        final boolean tiene_canal_alpha2 = imagen2.getAlphaRaster() != null;
    	float rgb1[] = new float[3];
    	float rgb2[] = new float[3];
    	double distancia = 0;
    	int contadorPixelesImagen;
       
    	// Encontrar la longitud de los píxeles
        int longitudPixel1 = 3;
        int longitudPixel2 = 3;
        
        if (tiene_canal_alpha1) {
        	longitudPixel1 = 4;
        }
        
        if (tiene_canal_alpha2) {
        	longitudPixel2 = 4;
        }
        
 
        
        // Encontrar tamaño imagen
        contadorPixelesImagen = pixeles1.length / longitudPixel1;
        
        for (int pixel1 = 0, pixel2 = 0; pixel1 < pixeles1.length && pixel2 < pixeles2.length; pixel1 += longitudPixel1, pixel2 += longitudPixel2) {
        	
        	if (tiene_canal_alpha1) {
        		// resultados para RBG
        		rgb1[0] = (float)((int)pixeles1[pixel1 + 3] & 0xff) / 256.0f;
        		rgb1[1] = (float)((int)pixeles1[pixel1 + 2] & 0xff) / 256.0f;
        		rgb1[2] = (float)((int)pixeles1[pixel1 + 1] & 0xff) / 256.0f;
        		
        	}
        	else
        	{
        		// resultados RGB
        		rgb1[0] = (float)((int)pixeles1[pixel1 + 2] & 0xff) / 256.0f;
        		rgb1[1] = (float)((int)pixeles1[pixel1 + 1] & 0xff) / 256.0f;
        		rgb1[2] = (float)((int)pixeles1[pixel1 + 0] & 0xff) / 256.0f;
        	}
        	
        	
        	if (tiene_canal_alpha2) {
        	
        		rgb2[0] = (float)((int)pixeles2[pixel2 + 3] & 0xff) / 256.0f;
        		rgb2[1] = (float)((int)pixeles2[pixel2 + 2] & 0xff) / 256.0f;
        		rgb2[2] = (float)((int)pixeles2[pixel2 + 1] & 0xff) / 256.0f;
        	}
        	else
        	{
        	
        		rgb2[0] = (float)((int)pixeles2[pixel2 + 2] & 0xff) / 256.0f;
        		rgb2[1] = (float)((int)pixeles2[pixel2 + 1] & 0xff) / 256.0f;
        		rgb2[2] = (float)((int)pixeles2[pixel2 + 0] & 0xff) / 256.0f;
        	}
        	
        	float dR = rgb1[0]-rgb2[0];
        	float dG = rgb1[1]-rgb2[1];
        	float dB = rgb1[2]-rgb2[2];
            distancia += Math.sqrt(dR*dR + dG*dG + dB*dB);
        }   
        
        return distancia / contadorPixelesImagen;
     }
	
}

