
package agimagen;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Rogelio
 */
//Clase que contiene toda la parte del algoritmo genetico, entrenamiento, seleccion, mutacion
public class Genetico {
  public static final int Num_hilos = 6;
    
    ArrayList<Imagen> imagen;
    ArrayList<Imagen> imagenSeleccionada;
    int num_figuras;
    int num_imagenes;
    double rango_seleccion;
    double rango_mutacion;
    int metodo_imagen;
    String figura;
    BufferedImage imagen_real;
    Imagen mejor_imagen;
    
    public Genetico(int num_imagenes,String figura, int num_figuras, int metodo_imagen, BufferedImage imagen_real, double rango_seleccion, double rango_mutacion){
        this.num_figuras=num_figuras;
        this.num_imagenes=num_imagenes;
        this.imagen_real=imagen_real;
        this.rango_seleccion=rango_seleccion;
        this.metodo_imagen=metodo_imagen;
        this.figura=figura;
        this.rango_mutacion=rango_mutacion;     
        this.imagen = new ArrayList<Imagen>();
        this.imagenSeleccionada = new ArrayList<Imagen>();
        
        inicializar();  
    }
    
    public void inicializar(){
        
        imagen.clear();
               
        //se crea una población de imágenes
        for(int i=0;i<num_imagenes;i++){
        	imagen.add(new Imagen(imagen_real.getWidth(), imagen_real.getHeight(), metodo_imagen));
        }
        
        //cada figura se crea y se añade al array de imágenes llamado "figura"
        //los tamaños de las figuras son aleatorios mientras se crea el objeto de figuras
        for(int i=0;i<num_imagenes;i++){
            if(figura.equals("cuadrados")){
                for(int j=0;j<num_figuras;j++){
                    Paintable p=new Cuadros(imagen.get(i));
                    imagen.get(i).elementoForma(p);
                }
            
            }

        }
    }
    
    public void entrenamiento(){    
    	
        // Establecer mejor imagen
    	if (mejor_imagen != null){
    		imagen.set(0, mejor_imagen);
    	}
    	calculo_entrenamiento t[] = new calculo_entrenamiento[Num_hilos];
    	
    	for (int i=0; i<Num_hilos; i++) {
    		t[i] = new calculo_entrenamiento(i, Num_hilos, imagen, imagen_real);
    	}
    	
    	for (int i=0; i<Num_hilos; i++) {
    		t[i].start();
    	}
    	
    	for (int i=0; i<Num_hilos; i++) {
    		try {
				t[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
    	
    	
    	
    }
    
    public void Seleccion(){
    	// Acomodamos de mejor a peor
        Collections.sort(imagen);
        
        // hacemos seleccion
        imagenSeleccionada.clear();    
        for (int i = 0; i <= (int)(imagen.size() * rango_seleccion) ; i++) {
            imagenSeleccionada.add(imagen.get(i));
        }
 
   
    }
    
    public void Cruce(){     
    	
    	Imagen p1 = null;
    	Imagen p2 = null;
        
    	// Eliminamos la poblacion vieja y creamos una nueva
        inicializar();
        
        for (int i = 0; 2*i+1 < imagen.size(); i++) {  
        	
        	p1 = imagenSeleccionada.get( GeneradorRandom.getIntRandom(imagenSeleccionada.size()) ); // seleccionamos una imagen random para realizar el cruce
            
            do {
            	p2 = imagenSeleccionada.get( GeneradorRandom.getIntRandom(imagenSeleccionada.size()) ); //seleccionamos una imagen random para realizar el cruce
            } while (p1 == p2);
            
            int puntoCruce = GeneradorRandom.getIntRandom(num_figuras); 
            
            //cruzando
            for (int j = 0; j < p1.getContadorFormas() && j < p2.getContadorFormas(); j++) {
                if (j < puntoCruce) {                 
                    imagen.get(2 * i).setForma(j, p1.getForma(j) );                    
                    imagen.get(2 * i + 1).setForma(j, p2.getForma(j));
                } 
                else {                   
                    imagen.get(2 * i).setForma(j, p2.getForma(j));
                    imagen.get(2 * i + 1).setForma(j, p1.getForma(j));
                }
            }               
        }   
    }
    
    public void mutacion(){
    	for (Imagen p : imagen) {
    		for (int i=0; i<p.getContadorFormas(); i++) {
                    if(GeneradorRandom.getFloatRandom() < rango_mutacion) {
                            // Mover el elemento de la figura al final de la lista
                            Paintable paintable = p.getForma(i);
                            p.eliminarForma(i);
                            paintable.randomize(p);
                            p.insertarForma(GeneradorRandom.getIntRandom(p.getContadorFormas()),paintable);

                    }
    		}
    	}
    
    }
    
    public Imagen getMejor(){         
        Collections.sort(imagenSeleccionada);
        mejor_imagen = imagenSeleccionada.get(0);
        //System.out.println("Mejor entrenado = " + mejor_imagen.entrenamiento);
        return mejor_imagen;
    }
    
}

class calculo_entrenamiento extends Thread {
	int id, paso;
	ArrayList<Imagen> imagen;
	BufferedImage imagen_real;
	
	public calculo_entrenamiento(int id, int paso, ArrayList<Imagen> imagen, BufferedImage imagen_real) {
		super();
		this.id = id;
		this.paso = paso;
		this.imagen = imagen;
		this.imagen_real = imagen_real;
	}
	
	public void run() {
        for(int i=id; i<imagen.size(); i+=paso){
            try {
				imagen.get(i).entrenamiento= 1.0 - ImageUtil.difImagen_matiz_saturacion_brillo(imagen.get(i).getImagen(), imagen_real); // HSB
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
	}
	
	
}
