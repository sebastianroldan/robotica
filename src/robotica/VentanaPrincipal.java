package robotica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.WindowConstants;


public class VentanaPrincipal {

	private Lector lector;
	private JButton mostrar = new JButton();
	private JLabel anguloActual = new JLabel("205.58");
	private JLabel anguloDestino = new JLabel("205.58");
	private JLabel flecha = new JLabel();
	private Pintor pintor;
	private javax.swing.JLabel contenedorDeImagen;
	private JLabel labetiempoTranscurrido = new JLabel("Time");
	private JLabel tiempoTranscurrido = new JLabel("0");
	private JFrame v;
	private JPanel superior = new JPanel();
	private JPanel inferior = new JPanel();
	private int diferencia =0;
	private int destino;
	private int tiempo;
	private int segundos = 0;
	private JLabel estado = new JLabel("ESTADO:");
	private JLabel advertencia = new JLabel();
	private JLabel info1 = new JLabel();
	private JLabel info2 = new JLabel();
	private boolean estaSensando = false;
	private Timer timer;
	private JCheckBox checkBox = new JCheckBox();
    
    public VentanaPrincipal() {
    
        v = new JFrame("Sistema guia");
        inciarComponentes();
        Dimension minimumSize= new Dimension(400,350);
        v.setMinimumSize(minimumSize);
        v.pack();
        v.setVisible(true);
        v.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

	private void inciarComponentes() {
		lector = new Lector(this);
    	pintor = new Pintor("./flecharoja.png");
    	superior.setLayout(new BorderLayout());
        agregarContenedorSuperior();
        agregarContenedorDeFlecha();
        //agregarContenedorInferior();
        GridLayout layout = new GridLayout(0,1);
        layout.setHgap(20);
        inferior.setLayout(layout);
        inferior.add(estado);
        inferior.add(info1);
        inferior.add(info2);
        info1.setText("El angulo debe estar entre 0 y 359 grados.");
        info2.setText("El tiempo debe estar entre 10 y 60 segundos.");
        advertencia.setForeground(Color.RED);
        inferior.add(advertencia);
        v.getContentPane().add(superior, BorderLayout.NORTH);
        v.getContentPane().add(inferior, BorderLayout.CENTER);
        
	}

	private void agregarContenedorInferior() {
		JPanel panelInferior = new JPanel(new FlowLayout());
        JButton unBoton = new JButton("Un boton");
        JButton algunOtroBoton = new JButton("Algun otro boton");
        panelInferior.add(unBoton);
        panelInferior.add(algunOtroBoton);
        superior.add(panelInferior, BorderLayout.SOUTH);
	}

	private void agregarContenedorSuperior() {
		JPanel botonesRumbo = new JPanel(new FlowLayout(0, 40,0));
        //JButton otroBoton = new JButton("Algo");
        JLabel labelTiempo = new JLabel("Tiempo:");
        JTextField fieldTiempo = new JTextField(2);
        JLabel labelSegundos = new JLabel("seg.");
        JLabel labelAngulo = new JLabel("Angulo:");
        JTextField fieldAngulo = new JTextField(2);
        JLabel labelGrados = new JLabel("°");
        JButton botonFijarRumbo = new JButton("Fijar rumbo");
        JPanel tiempo = new JPanel();
        JPanel angulo = new JPanel();
        //botonesRumbo.add(otroBoton);
        tiempo.add(labelTiempo);
        tiempo.add(fieldTiempo);
        tiempo.add(labelSegundos);
        angulo.add(labelAngulo);
        angulo.add(fieldAngulo);
        angulo.add(labelGrados);
        botonesRumbo.add(tiempo);
        botonesRumbo.add(angulo);
        botonesRumbo.add(botonFijarRumbo);
        superior.add(botonesRumbo, BorderLayout.NORTH);
//        otroBoton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                cambiarLabel();
//            }
//
//        });
        botonFijarRumbo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fijarDestino(fieldAngulo.getText(), fieldTiempo.getText()); 
            }
        });
	}

	private void fijarDestino(String angulo, String newTiempo) {
		int proximoDestino;  
		int nuevoTiempo;
		try{
			proximoDestino = Integer.parseInt(angulo);
			nuevoTiempo = Integer.parseInt(newTiempo);
			if (destinoEsValido(proximoDestino) && tiempoEsValido(nuevoTiempo)){
				destino = proximoDestino;
				tiempo = nuevoTiempo;
				dispararMedicion();
			}else{
				throw new NumberFormatException();
			}
			
			anguloDestino.setText(angulo);
			advertencia.setText("");
		}
		catch (NumberFormatException e){
			advertencia.setText("ADVERTENCIA: los datos ingresados para el rumbo son incorrectos");
		}
	}
	
	private void dispararMedicion() {
			segundos = 0;
			estaSensando = true;
			info1.setVisible(false);
			info2.setVisible(false);
			timer = new Timer (1000, new ActionListener () 
			{ 
			    public void actionPerformed(ActionEvent e) 
			    { 
			    	contarTiempo();
			     }

				private void contarTiempo() {
					segundos++;
					tiempoTranscurrido.setText(Integer.toString(segundos));
					if (segundos == tiempo){
						estaSensando = false;
						timer.stop();
						estado.setText("ESTADO: Tiempo finalizado, seleccione otro rumbo.");
						segundos = 0;
						tiempoTranscurrido.setText(Integer.toString(segundos));
						info1.setVisible(true);
						info2.setVisible(true);
					}
					
				} 
			});
			timer.start();
	}

	private boolean tiempoEsValido(int nuevoTiempo) {
		
		return (nuevoTiempo >= 10 && nuevoTiempo <=60);
	}

	private boolean destinoEsValido(int proximoDestino) {
		return (proximoDestino >= 0 && proximoDestino < 360);
	}

	private void agregarContenedorDeFlecha() {
		JPanel contenedorFlecha = new JPanel(new FlowLayout());
    	//flecha.setIcon(new ImageIcon("./verde.png"));
		JPanel columna2 = new JPanel(new GridLayout(0,1));
		JPanel columna1 = new JPanel(new GridLayout(0,1));
		JLabel labelAngulo = new JLabel("Actual");
		JLabel labelDestino = new JLabel("Destino");
		anguloDestino.setText(Integer.toString(this.destino));
		columna2.add(labelAngulo);
		columna2.add(anguloActual);
		columna2.add(labelDestino);
		columna2.add(anguloDestino);
		labetiempoTranscurrido.setText("Transcurrido");
		columna1.add(labetiempoTranscurrido);
		tiempoTranscurrido.setFont(new Font("Serif", Font.PLAIN, 40));
		columna1.add(tiempoTranscurrido);
		JPanel check = new JPanel(new FlowLayout());
		check.add(new JLabel("Sensar todo el tiempo"));
		check.add(checkBox);
		columna1.add(check);
    	contenedorFlecha.add(columna1);
    	
    	//contenedorFlecha.add(flecha);
    	contenedorFlecha.add(pintor);
    	contenedorFlecha.add(columna2, BorderLayout.EAST);
        superior.add(contenedorFlecha, BorderLayout.CENTER);
	}

	private void dibujarBrujula() {
//		pintor.paint(new Graphics());
		
	}

	

	private void agregarAccionAMostrar() {
		mostrar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cambiarLabel();
			}
		});
	}
	
	private void cambiarLabel(){
		
		String angulo = Double.toString(lector.leerDatos());
		this.anguloActual.setText(angulo);
	}
	
	private Double pasarGradosARadianes(){
		double angRadianes = Math.toRadians((double)lector.leerDatos());
		return angRadianes;
	}
	
	public void mostrarFlecha(){
		if (estaSensando || checkBox.isSelected()){
			this.cambiarLabel();
		
			boolean derecha = this.calcularDiferencia(lector.leerDatos());
			this.actualizarEstado(derecha);
			pintor.setDestino(destino);
			pintor.setRotacion(pasarGradosARadianes());
			pintor.setFactor(diferencia);
			pintor.repaint();
		}
	}

	private void actualizarEstado(boolean desvioAderecha) {
		if (desvioAderecha){
			this.estado.setText("ESTADO: desvio hacia la derecha corregir "+diferencia+" grados a la Izquierda");
		}else{
			this.estado.setText("ESTADO: desvio hacia la izquierda corregir "+diferencia+" grados a la Derecha");
		}
	}

	private boolean calcularDiferencia(int actual) {
		boolean correccionDerecha = false;
		int difIzquierda = 0;
		int difDerecha = 0;
		if (destino < actual){
			difIzquierda  = destino + (360-actual);
			difDerecha = actual - destino;
		}else{
			if (destino > actual){
				difDerecha  = actual + (360-destino);
				difIzquierda = destino - actual;
			}else{
				diferencia = 0;
			}
		}
		if (difDerecha < difIzquierda){
			diferencia = difDerecha;
			correccionDerecha = true;
		}else{
			if (difDerecha > difIzquierda){
				diferencia = difIzquierda;
			}
		}
		return correccionDerecha;
	}

}
