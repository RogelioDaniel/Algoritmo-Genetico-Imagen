
package agimagen;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;

/**
 *
 * @author Rogelio
 */
public class Principal {
    	
	public static void main(String[] args) throws IOException {
		
	//Variables primeros parametros
	    String rutaImagen = "C:\\Users\\Rogelio\\Desktop\\Java\\AGImagen\\babyyoda.png";			
	    int num_imagenes = 50; 		
	    int num_figuras = 150;   		// 
	    int generaciones = 50000;           //Generacion maxima
	    double rango_seleccion = 0.10;  
	    double rango_mutacion = 0.005;


            // creamos pantalla principal
            Pantalla pantalla_principal = new Pantalla(480, 480, 45, 150, true);

            // Cargamos imagen
            BufferedImage imagen_original = ImageUtil.cargarImagen(rutaImagen);		

            // Escalamos imagen al tamaño adecuado 640x 480
            BufferedImage imagen_original_escalada = ImageUtil.imagennCambiarTam(imagen_original, 480, 480, true);

            // Mostramos imagen
            pantalla_principal.mostrar(imagen_original_escalada);

            // Titulo y tamaño letra
            pantalla_principal.setTitulo("Imagen original");
            pantalla_principal.setTextTam(20);

	    // Creamos pantalla generada
	    Pantalla pantalla_generada = new Pantalla(480, 480, 515, 150, true);
	
            //Pasamos parametros a nuestro objeto Genetico 
	    Genetico g = new Genetico(num_imagenes, "cuadrados", num_figuras, 0, imagen_original, rango_seleccion, rango_mutacion);
	    
	    
	    for (int i=0; i<generaciones; i++) {
	        g.entrenamiento();
	        g.Seleccion();
	        // Mostramos la mejor imagen
	        Imagen p = g.getMejor();
	        pantalla_generada.mostrar(p.getImagen(), "Generacion: " + i, "Entrenamiento " + String.format("%.3f", p.entrenamiento));
	        g.Cruce();
	        g.mutacion();
	    }
	    
	
	    pantalla_generada.cerrar();
	    pantalla_principal.cerrar();
	    System.exit(0);
    
	}

}
