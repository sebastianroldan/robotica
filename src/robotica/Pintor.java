package robotica;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 * Componente que dibuja una foto y permite rotarla.
 * @author chuidiang
 *
 */
public class Pintor extends JComponent {
    
    /**
     * Devuelve como tamaño preferido el de la foto.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(icono.getIconHeight(), icono.getIconHeight());
    }

    /** La foto */
    private ImageIcon icono = null;

    /**
     * Carga la foto y la guarda
     * @param ficheroImagen
     */
    public Pintor(String ficheroImagen) {
        icono = new ImageIcon(ficheroImagen);
        this.setLayout(new BorderLayout());
    }

    /**
     * Cuanto queremos que se rote la foto, en radianes.
     */
    private double rotacion = 0.0;
    private int factor = 150;
	private int destino;

    public void paint(Graphics g) {
    	BufferedImage  bufIm=new BufferedImage(600,600,BufferedImage.TYPE_INT_ARGB);
    	ImageIcon verdeImage = new ImageIcon("./flechaverde.png");
        Graphics2D g2d = bufIm.createGraphics();
        g2d = (Graphics2D) g;
        
        AffineTransform rotate = AffineTransform.getRotateInstance(Math.toRadians(destino), 
                verdeImage.getIconWidth()/2
                , verdeImage.getIconHeight()/2);
        
        g2d.drawImage(verdeImage.getImage(),rotate,null);
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER);
        g2d.setComposite(ac);

        rotate = AffineTransform.getRotateInstance(rotacion, 
                icono.getIconWidth()/2
                , icono.getIconHeight()/2);
        int desplazar = 0;
        if (factor < 150){
        	desplazar = (int)(75-factor/2);
        }else{
        	desplazar = (int)(-1*(factor/2 - 75));
        }
        AffineTransform translate = AffineTransform.getTranslateInstance(desplazar, 0);
        
        ImageIcon auxiliar = new ImageIcon();
        auxiliar.setImage(icono.getImage().getScaledInstance(factor, 150, 2));
        AffineTransform original = g2d.getTransform();
        g2d.setTransform(rotate);
        g2d.transform(translate);
        g2d.drawImage(auxiliar.getImage(), 0,0, null);
        
        g2d.setTransform(original);
        
    }

	/**
     * Devuelve la rotacion actual.
     * @return rotacion en radianes
     */
    public double getRotacion() {
        return rotacion;
    }

    /**
     * Se le pasa la rotación deseada.
     * @param rotacion La rotacion en radianes.
     */
    public void setRotacion(double rotacion) {
        this.rotacion = rotacion;
    }

	public void setFactor(int diferencia) {
		
		factor =  15 + diferencia;
		
	}
	
	private double transformacionLineal(double suma, double max, double min) {
		double salida = suma*(255/(max-min))+(255-((255*max)/(max-min)));
		return salida;
	}

	public void setDestino(int destino) {
		this.destino = destino;
		
	}
}