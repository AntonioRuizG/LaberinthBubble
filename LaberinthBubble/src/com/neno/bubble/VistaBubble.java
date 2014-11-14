package com.neno.bubble;

import java.io.InputStream;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;

public class VistaBubble extends View{

	private static final int NUM_ALTO = 51;
	private static final int NUM_ANCHO = 51;
	protected static final long INTERVALO_REFRESCO = 16;
	private static final double RADIO_PELOTA = 1.0/102.0;//0.05;
	private static final double RADIO_BUBBLE = 1.0/102.0;//0.05;
	private static final double RADIO_RAQUETA = 0.08;
	
	private Thread hiloRender;
	private long TiempoAnterior;
	private boolean partidaCreada=false;
	private ArrayList<Dibujable>dibujables;
	private ArrayList<Bubble>bubbles;
	private int[][] matrizBubbles;
	private int indice;
	private Laberinto laberinto;
	private boolean laberintoTerminado=false;
	
	public VistaBubble(Context context) {
		super(context);
		Bitmap b;
		this.setBackgroundColor(Color.BLACK);
		matrizBubbles = new int[NUM_ANCHO][NUM_ALTO];
		
		
		InputStream is = this.getResources().openRawResource(R.drawable.techarray);
		b = BitmapFactory.decodeStream(is);
		ImageDatabase.putBitmap(b, "techarray", true);
		
		is = this.getResources().openRawResource(R.drawable.techtalents);
		b = BitmapFactory.decodeStream(is);
		ImageDatabase.putBitmap(b, "letras", true);
		
		laberinto = new Laberinto(NUM_ANCHO, NUM_ALTO);
		
		for(int i=0;i<NUM_ANCHO;i++){
			for(int j=0;j<NUM_ALTO;j++){
				matrizBubbles[i][j]=(((int)(Math.sqrt((i-NUM_ANCHO/2)*(i-NUM_ANCHO/2)+(j-NUM_ALTO/2)*(j-NUM_ALTO/2))))%32)/8+1;
			}
		}
		
		dibujables=new ArrayList<Dibujable>();
		bubbles=new ArrayList<Bubble>();
		crearHiloRender();
		
		b=BitmapFactory.decodeResource(getResources(), R.drawable.bubbleamarilla);
		ImageDatabase.putBitmap(b, "bubbleAmarilla");
		
		b=BitmapFactory.decodeResource(getResources(), R.drawable.bubbleazul);
		ImageDatabase.putBitmap(b, "bubbleAzul");
		
		b=BitmapFactory.decodeResource(getResources(), R.drawable.bubblenaranja);
		ImageDatabase.putBitmap(b, "bubbleNaranja");
		
		b=BitmapFactory.decodeResource(getResources(), R.drawable.bubbleroja);
		ImageDatabase.putBitmap(b, "bubbleRoja");
		
		b=BitmapFactory.decodeResource(getResources(), R.drawable.bubblecyan);
		ImageDatabase.putBitmap(b, "bubbleCyan");
		
		b=BitmapFactory.decodeResource(getResources(), R.drawable.pelota);
		ImageDatabase.putBitmap(b, "pelota");
		
		b=BitmapFactory.decodeResource(getResources(), R.drawable.raqueta);
		ImageDatabase.putBitmap(b, "raqueta");
		
		b=BitmapFactory.decodeResource(getResources(), R.drawable.iconoairelibre);
		ImageDatabase.putBitmap(b, "airelibre", true);
		
		b=BitmapFactory.decodeResource(getResources(), R.drawable.iconoaprende);
		ImageDatabase.putBitmap(b, "aprende", true);
		
		b=BitmapFactory.decodeResource(getResources(), R.drawable.iconocurva);
		ImageDatabase.putBitmap(b, "curva", true);
		
		b=BitmapFactory.decodeResource(getResources(), R.drawable.iconoimagina);
		ImageDatabase.putBitmap(b, "imagina", true);
		
		b=BitmapFactory.decodeResource(getResources(), R.drawable.iconoprofes);
		ImageDatabase.putBitmap(b, "profes", true);
		
		b=BitmapFactory.decodeResource(getResources(), R.drawable.iconotrabajoequipo);
		ImageDatabase.putBitmap(b, "equipo", true);
		
	}



	private void actualizarPosiciones() {
		if(!partidaCreada)return;
		double t=0;
		long TiempoAhora=System.currentTimeMillis();
		if(TiempoAnterior==0)TiempoAnterior=System.currentTimeMillis();
		t=(TiempoAhora-TiempoAnterior)/1000.0;
		for(int i=0;i<4;i++)
		if(indice<laberinto.laberinto.size()){
			
			double radioBubble = getWidth()*RADIO_BUBBLE;
			if(!laberintoTerminado){
				Vec2 posicion = laberinto.laberinto.get(indice++);
				Bubble b = new Bubble(radioBubble, radioBubble*2*(posicion.x+0.5), radioBubble*2*(posicion.y+0.5),
						2);
				bubbles.add(b);
				dibujables.add(b);
				if(indice==laberinto.laberinto.size()){
					laberintoTerminado=true;
					indice=0;
				}
			}
		}
		//mover dibujables
		for(int i=0;i<dibujables.size();i++){
			Dibujable d=dibujables.get(i);
			if(d!=null)
				d.mover(t);
		}
		
		
		TiempoAnterior=System.currentTimeMillis();
		postInvalidate();
		
	}

	private void crearPartida() {
		int ancho = getWidth();
		int alto = getHeight();
		
		new Pelota(ancho*0.5, alto-RADIO_RAQUETA*ancho-RADIO_PELOTA*ancho, RADIO_PELOTA*ancho);
		
				
		new Raqueta(ancho*0.5, alto, RADIO_RAQUETA*ancho);
		
				
		/**	
		//crea burbujas
		for(int i =0;i<NUM_ANCHO;i++){
			for(int j=0;j<NUM_ALTO;j++){
				if(matrizBubbles[i][j]==0)continue;
				double radioBubble = ancho*RADIO_BUBBLE;
				Bubble b = new Bubble(radioBubble, radioBubble*2*(j+0.5), radioBubble*2*(i+0.5),
						matrizBubbles[i][j]);
				bubbles.add(b);
				dibujables.add(b);
			}
		}*/
		//dibujables.add(pelota);
		//dibujables.add(raqueta);
		partidaCreada=true;
		hiloRender.start();
	}
	
	@Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(w!=0 && h!=0){
        	crearPartida();
        	for(int i=0;i<dibujables.size();i++){
    			Dibujable d=dibujables.get(i);
    			if(d!=null)
    				d.onSizeChanged(w, h, oldw, oldh);
    		}
        }
    }

	@Override
	protected void onDraw(Canvas canvas){
		super.onDraw(canvas);
		for(int i=0;i<dibujables.size();i++){
			Dibujable d = dibujables.get(i);
			if(d!=null)d.dibujar(canvas);
		}
	}

	private void crearHiloRender() {
		hiloRender=new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					actualizarPosiciones();
					try {
						Thread.sleep(INTERVALO_REFRESCO);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}
		});
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		if ((event.getAction() == MotionEvent.ACTION_DOWN)||
			(event.getAction() == MotionEvent.ACTION_MOVE))
		{
			if(laberintoTerminado){
				double x = event.getX();
				double y = event.getY();
				for(Bubble b : bubbles){
					b.setAtractor(x, y, 100.0);
					
				}
			}
		} else if(event.getAction() == MotionEvent.ACTION_UP){
			for(Bubble b : bubbles){
				b.removeAtractor();
				
			}
		}

		
		return true;
	}
	
}
