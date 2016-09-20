package robotica;

import javax.comm.*;
import java.io.*;
import java.util.*;

public class Lector implements SerialPortEventListener{

	static CommPortIdentifier portId1;
	static CommPortIdentifier portId2;

	InputStream inputStream;
	OutputStream outputStream;
	SerialPort serialPort1;
	Thread readThread;
	private int angulo = 0;
	private static BufferedReader input;
	private VentanaPrincipal ventana;

	
	public Lector(VentanaPrincipal newVentana){
		this.inicializar();
		this.ventana = newVentana;
		Thread t=new Thread() {
			public void run() {
				//the following line will keep this app alive for 1000 seconds,
				//waiting for events to occur and responding to them (printing incoming messages to console).
				try {Thread.sleep(1000000);} catch (InterruptedException ie) {}
			}
		};
		t.start();
	}
	
	public void abrirPuerto() {

		try
		{
			CommPortIdentifier cpi = null;
			Enumeration e = CommPortIdentifier.getPortIdentifiers() ;
			while( e.hasMoreElements())
			{
				CommPortIdentifier tmp = (CommPortIdentifier)e.nextElement();

				if (tmp.getName().equals("COM3"))
				{
					cpi = tmp;
					
				}
			}
			serialPort1 = (SerialPort) cpi.open("cpi(" + cpi.getName().toString() + ")", 2000);


		}	

		catch
		(PortInUseException e) {
			System.out.println("puerto ocupado");
		}
		try {
			if (serialPort1 != null)
			serialPort1.setSerialPortParams(9600, SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
		} catch (UnsupportedCommOperationException e1) {
			System.out.println("params");
		}
			try {
				serialPort1.addEventListener(this);
			} catch (TooManyListenersException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			serialPort1.notifyOnDataAvailable(true);
			
			try {
				input = new BufferedReader(new InputStreamReader(
						serialPort1.getInputStream()));
			} catch (IOException e) {
				System.out.println("imput error");
			}
		

	}

	public void inicializar() {
		this.abrirPuerto();
	}
	
	public int leerDatos(){
		return angulo;
	}
	
	public synchronized void close() {
		if (serialPort1 != null) {
			serialPort1.removeEventListener();
			serialPort1.close();
		}
	}

	public synchronized void serialEvent(SerialPortEvent oEvent) {
		if (puertoSerialEstaDisponible(oEvent)) {
			try {
				angulo = (int)Double.parseDouble(input.readLine());
				ventana.mostrarFlecha();
			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}
	}
	

	private boolean puertoSerialEstaDisponible(SerialPortEvent oEvent) {
		return oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE;
	}
	
}

//
//float imprimirCardinal(int angulo){
//	  if((angulo < 22.5) || (angulo > 337.5 )){
//	   Serial.println("N");
//	 }
//	 if((angulo > 22.5) && (angulo < 67.5 )){
//	   Serial.println("NE");
//	 }
//	 if((angulo > 67.5) && (angulo < 112.5 )){
//	   Serial.println("E");
//	 }
//	 if((angulo > 112.5) && (angulo < 157.5 )){
//	   Serial.println("SE");
//	 }
//	 if((angulo > 157.5) && (angulo < 202.5 )){
//	   Serial.println("S");
//	 }
//	 if((angulo > 202.5) && (angulo < 247.5 )){
//	   Serial.println("SO");
//	 }
//	 if((angulo > 247.5) && (angulo < 292.5 )){
//	   Serial.println("O");
//	 }
//	 if((angulo > 292.5) && (angulo < 337.5 )){
//	   Serial.println("NO");
//	 }

