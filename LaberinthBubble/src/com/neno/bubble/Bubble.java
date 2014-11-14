package com.neno.bubble;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;

public class Bubble extends Colisionable{
	
	private static final double ROZAMIENTO=0.001;
	private double radio;
	private Paint paint;
	private int tipoBubble;
	private String idBubble;
	private double posicionInicialX;
	private double posicionInicialY;
	private boolean atractor;
	private double aX;
	private double aY;
	private double fuerza;

	public Bubble(double radio, double x, double y, int tipo){
		super.addColisionCircle(new Circulo(radio,0,0));
		this.radio=radio;
		super.x=x;
		super.y=y;
		this.posicionInicialX=x;
		this.posicionInicialY=y;
		paint = new Paint();
		paint.setAntiAlias(true);
		if(tipo>4)tipo=4;
		if(tipo<1)tipo=1;
		tipoBubble=tipo;
		if(tipoBubble<=1){
			paint.setColor(Color.RED);
			idBubble = "bubbleRoja";
		}
		if(tipoBubble==2){
			paint.setColor(Color.YELLOW);
			idBubble = "bubbleNaranja";
		}
		if(tipoBubble==3){
			paint.setColor(Color.GREEN);
			idBubble = "bubbleAmarilla";
		}
		if(tipoBubble>=4){
			paint.setColor(Color.BLUE);
			idBubble = "bubbleAzul";
		}
		paint.setStyle(Style.FILL);
		aX=x;
		aY=y;
	}

	@Override
	public void dibujar(Canvas c) {
		//c.drawCircle((float)x, (float)y, (float)radio, paint);
		RectF dst=new RectF((float)(x-radio), (float)(y-radio),(float)(x+radio),(float)(y+radio));
		Bitmap b=ImageDatabase.getBitmap(idBubble);
		if(b!=null)
			c.drawBitmap(b, null, dst, paint);
		else
			c.drawCircle((float)x, (float)y, (float)radio, paint);
	}

	@Override
	public void onSizeChanged(int w, int h, int oldw, int oldh) {
		
	}

	@Override
	public void mover(double t) {
		if(atractor){
			vx-=vx*ROZAMIENTO;
			vy-=vy*ROZAMIENTO;
		}else{
			vx-=vx*ROZAMIENTO*10;
			vy-=vy*ROZAMIENTO*10;
		}
		
		
		
		
		
		double modulo=Math.sqrt(((aX-x)*(aX-x))+((aY-y)*(aY-y)));
		double velocidad=Math.sqrt(((vx)*(vx))+((vy)*(vy)));
		
		if(modulo<0.02 && velocidad < 0.1){
			x=aX;
			y=aY;
			return;
		}
		if(modulo!=0){
			vx=vx+t*(aX-x)/modulo*fuerza;
			vy=vy+t*(aY-y)/modulo*fuerza;
		}
		x = x + (t*vx);
		y = y + (t*vy);
		
	}
	
	public int hit(){
		tipoBubble--;
		if(tipoBubble<1){
			paint.setColor(Color.CYAN);
			idBubble = getRandomId();
			super.clearColisionCircles();
		}
		if(tipoBubble==1){
			paint.setColor(Color.RED);
			idBubble = "bubbleRoja";
		}
		if(tipoBubble==2){
			paint.setColor(Color.YELLOW);
			idBubble = "bubbleNaranja";
		}
		if(tipoBubble==3){
			paint.setColor(Color.GREEN);
			idBubble = "bubbleAmarilla";
		}
		if(tipoBubble>=4){
			paint.setColor(Color.BLUE);
			idBubble = "bubbleAzul";
		}
		return tipoBubble;
	}

	private String getRandomId() {
		int ran = new Random().nextInt(6);
		switch (ran) {
		case 0:
			return "airelibre";
		case 1:
			return "aprende";
		case 2:
			return "curva";
		case 3:
			return "imagina";
		case 4:
			return "equipo";
		}
		return "profes";
	}

	public void setAtractor(double x, double y, double fuerza) {
		atractor=true;
		aX=x;
		aY=y;
		this.fuerza=fuerza;
	}

	public void removeAtractor() {
		aX=posicionInicialX;
		aY=posicionInicialY;
		atractor=false;
	}

}
