package agimagen;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 *
 * @author Rogelio
 */
public class Pantalla {
    	private JFrame jframe;
	private JPanel jpanel;
	private JLabel jLabelAlto;
	public JLabel jLabelImagen;	
	private JLabel jLabelBajo;
	private int alto;
	private int ancho;
	
	// MAIN CONSTRUCTOR
	/**
	 * Pantalla Constructor

	 */
	public Pantalla(int ancho, int alto, int posX, int posY, boolean salir) {
		this.ancho = ancho;
		this.alto = alto;
		pantallasMejoras(posX, posY, salir);
	}
	
	// SUB CONSTRUCTOR

	public Pantalla(int ancho, int alto, boolean salir) {
		this(ancho, 
				alto, 
				GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint().x - ancho / 2, 
				GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint().y - alto / 2, 
				salir);
	}
	
	private void pantallasMejoras(int posX, int posY, boolean salir) {
		jframe = new JFrame("Mejores generaciones");
		jpanel = new JPanel(new BorderLayout()); 	
		jLabelAlto = new JLabel();
		jLabelImagen = new JLabel();
		jLabelBajo = new JLabel();
		
		// Centramos pantallas
		jLabelAlto.setHorizontalAlignment(JLabel.CENTER);
		jLabelImagen.setHorizontalAlignment(JLabel.CENTER);
		jLabelBajo.setHorizontalAlignment(JLabel.CENTER);

		jpanel.add(jLabelAlto, BorderLayout.PAGE_START);
		jpanel.add(jLabelImagen, BorderLayout.CENTER);
		jpanel.add(jLabelBajo, BorderLayout.PAGE_END);
		jframe.add(jpanel);
		jframe.setSize(ancho, alto);
		jframe.setLocation(posX, posY);
		if (salir) {
			jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
	}
	
	/**
        Se ajusta y muestra la imagen en el marco, las leyendas superior e inferior siguen siendo las mismas
	 */
	public void mostrar(BufferedImage bi) {	
		jframe.setVisible(true);

		int anchoReal = jframe.getWidth() - jLabelAlto.getFont().getSize() - jLabelBajo.getFont().getSize();
		int altoReal = jframe.getHeight() - jLabelAlto.getFont().getSize() - jLabelBajo.getFont().getSize() - 120;
		
		BufferedImage nuevaImagen = ImageUtil.imagennCambiarTam(bi, anchoReal, altoReal, true);
		jLabelImagen.setIcon(new ImageIcon(nuevaImagen));
		jframe.repaint();
	}
	
	/**
            Ajusta y muestra la imagen en el marco, actualiza los subtítulos superiores e inferiores
	 */
	public void mostrar(BufferedImage bi, String subtitu_superior, String subtitu_inferior) {
		jframe.setVisible(true);
		mostrar(bi);
		jLabelAlto.setText(subtitu_superior);
		jLabelBajo.setText(subtitu_inferior);
	}
	
	public void cerrar() {
		jframe.dispatchEvent(new WindowEvent(jframe, WindowEvent.WINDOW_CLOSING));
	}

	public void setTamTextSup(int size) {
		jLabelAlto.setFont(new Font(jLabelAlto.getFont().getFontName(), jLabelAlto.getFont().getStyle(), size));
	}

	public void setTamTextInf(int size) {
		jLabelBajo.setFont(new Font(jLabelBajo.getFont().getFontName(), jLabelBajo.getFont().getStyle(), size));
	}
	
	public int getTamTextSup() {
		return jLabelAlto.getFont().getSize();
	}
	
	public int getTamTextInf() {
		return jLabelBajo.getFont().getSize();
	}
	

	public int getTextTam() {
		return getTamTextSup() > getTamTextInf() ? getTamTextSup() : getTamTextInf();
	}

	public void setTextTam(int size) {
		setTamTextSup(size);
		setTamTextInf(size);
	}
	
	/**
	 * Establecer el tamaño del marco
	 */
	public void setTam(int ancho, int alto) {
		this.ancho = ancho;
		this.alto = alto;
		jframe.setSize(alto, alto);
	}
	
	public int getAlto() {
		return alto;
	}

	public int getAncho() {
		return ancho;
	}
	
	public void setTitulo(String title) {
		jframe.setTitle(title);
	}
	
	public String getTitulo() {
		return jframe.getTitle();
	}
	
	

}

