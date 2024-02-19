package usothreads;


import java.awt.geom.*;

import javax.swing.*;

import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class UsoThreads {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JFrame marco=new MarcoRebote();
		
		marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		marco.setVisible(true);

	}

}

class PelotaHilos implements Runnable{

	public PelotaHilos(Pelota unaPelota, Component unComponente) {
		
	    pelota=unaPelota;
		
		componente=unComponente;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//for (int i=1; i<=6000; i++){
			
		//while(!Thread.interrupted()) {
		
           System.out.println("Estado de hilo al comenzar "+ Thread.currentThread().isInterrupted());
		
		while(!Thread.currentThread().isInterrupted()) {
			pelota.mueve_pelota(componente.getBounds());
						
			componente.paint(componente.getGraphics());
			
		
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			//	System.out.println("hilo Bloqueado imposible su ejecucion");
			
			Thread.currentThread().interrupt();
			
			}
			
		}
		 System.out.println("Estado de hilo al terminar "+ Thread.currentThread().isInterrupted());
		
	}
	
	private Pelota pelota;
	
	private Component componente;
}



//Movimiento de la pelota-----------------------------------------------------------------------------------------

class Pelota{
	
	// Mueve la pelota invirtiendo posici�n si choca con l�mites
	
	public void mueve_pelota(Rectangle2D limites){
		
		x+=dx;
		
		y+=dy;
		
		if(x<limites.getMinX()){
			
			x=limites.getMinX();
			
			dx=-dx;
		}
		
		if(x + TAMX>=limites.getMaxX()){
			
			x=limites.getMaxX() - TAMX;
			
			dx=-dx;
		}
		
		if(y<limites.getMinY()){
			
			y=limites.getMinY();
			
			dy=-dy;
		}
		
		if(y + TAMY>=limites.getMaxY()){
			
			y=limites.getMaxY()-TAMY;
			
			dy=-dy;
			
		}
		
	}
	
	//Forma de la pelota en su posici�n inicial
	
	public Ellipse2D getShape(){
		
		return new Ellipse2D.Double(x,y,TAMX,TAMY);
		
	}	
	
	private static final int TAMX=15;
	
	private static final int TAMY=15;
	
	private double x=0;
	
	private double y=0;
	
	private double dx=1;
	
	private double dy=1;
	
	
}

// L�mina que dibuja las pelotas----------------------------------------------------------------------


class LaminaPelota extends JPanel{
	
	//A�adimos pelota a la l�mina
	
	public void add(Pelota b){
		
		pelotas.add(b);
	}
	
	public void paintComponent(Graphics g){
		
		super.paintComponent(g);
		
		Graphics2D g2=(Graphics2D)g;
		
		for(Pelota b: pelotas){
			
			g2.fill(b.getShape());
		}
		
	}
	
	private ArrayList<Pelota> pelotas=new ArrayList<Pelota>();
}


//Marco con l�mina y botones------------------------------------------------------------------------------

class MarcoRebote extends JFrame{
	
	public MarcoRebote(){
		
		setBounds(600,300,600,350);
		
		setTitle ("Rebotes");
		
		lamina=new LaminaPelota();
		
		add(lamina, BorderLayout.CENTER);
		
	   JPanel laminaBotones=new JPanel();
	   
	   arranca1=new JButton("Hilo1");	
	   arranca1.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			comienza_el_juego(e);
		}
	});	   
		
	   laminaBotones.add(arranca1);
	   //--------------------------
	   arranca2=new JButton("Hilo2");	
	   arranca2.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			comienza_el_juego(e);
		}
	});	   
		
	   laminaBotones.add(arranca2);
	   //----------------------------------------------
	   arranca3=new JButton("Hilo3");	
	   arranca3.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			comienza_el_juego(e);
		}
	});	   
		
	   laminaBotones.add(arranca3);
	   //-----------------------------------
	   det1=new JButton("det1");	
	   det1.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			detener(e);
		}
	});	   
		
	   laminaBotones.add(det1);
	   
	   //--------------------------------------
	   det2=new JButton("det2");	
	   det2.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			detener(e);
		}
	});	   
		
	   laminaBotones.add(det2);
	   
	   //-----------------------------------------------
	   
	   det3=new JButton("det3");	
	   det3.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			detener(e);
		}
	});	   
		
	   laminaBotones.add(det3);
	   
	   
		add(laminaBotones, BorderLayout.SOUTH);
	}
	
	
	
	

	
	//A�ade pelota y la bota 1000 veces
	
	public void comienza_el_juego (ActionEvent e){
		
					
			Pelota pelota=new Pelota();
			
			lamina.add(pelota);
			
	        Runnable r = new PelotaHilos(pelota, lamina);
	        
	       // Thread t = new Thread(r);
	        
	        if(e.getSource().equals(arranca1)) {
	        
	      t1=new Thread(r);
	        
	        t1.start();
			
	        }else if (e.getSource().equals(arranca2)) {
	        	
	        	 t2=new Thread(r);
	 	        
	 	        t2.start();
	        }else if (e.getSource().equals(arranca3)) {
	        	
	        	 t3=new Thread(r);
	 	        
	 	        t3.start();
	        }
		
	}
	
	public void detener(ActionEvent e) {
		
		if(e.getSource().equals(det1)) {
			t1.interrupt();
		}
		if(e.getSource().equals(det2)) {
			t2.interrupt();
		}
		if(e.getSource().equals(det3)) {
			t3.interrupt();
		}
	}
	
	private LaminaPelota lamina;
	
	Thread t1,t2,t3;
	
	JButton arranca1,arranca2,arranca3,det1,det2,det3;
	
}