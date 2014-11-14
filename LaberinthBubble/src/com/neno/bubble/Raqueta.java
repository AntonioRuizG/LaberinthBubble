package com.neno.bubble;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Style;

public class Raqueta extends Colisionable{

	private double radio;
	private String id="raqueta";
	private Paint paint;
	private double limiteDerecho=10000;
	private double limiteIzquierdo=-10000;

	
	public Raqueta(double x, double y, double radio){
		super.x=x;
		super.y=y;
		this.radio=radio;
		super.addColisionCircle(new Circulo(radio, 0, 0));
		paint = new Paint();
		paint.setColor(Color.RED);
		paint.setAntiAlias(true);
		paint.setStyle(Style.FILL);
	}
	
	@Override
	public void dibujar(Canvas c) {
		//c.drawCircle((float)x, (float)y, (float)radio, paint);
		RectF dst=new RectF((float)(x-radio), (float)(y-radio),(float)(x+radio),(float)(y+radio));
		Bitmap b=ImageDatabase.getBitmap(id);
		if(b!=null)
			c.drawBitmap(b, null, dst, paint);
		else
			c.drawCircle((float)x, (float)y, (float)radio, paint);
	}

	@Override
	public void onSizeChanged(int w, int h, int oldw, int oldh) {
		if(w!=0 && h!=0){
			limiteDerecho = w-radio;
			limiteIzquierdo = radio;
		}
	}

	@Override
	public void mover(double t) {
		double nuevoX = x + (t*vx);
		// comprobar limites
		if(nuevoX > limiteDerecho){
			nuevoX = limiteDerecho;
		}
		if(nuevoX < limiteIzquierdo){
			nuevoX = limiteIzquierdo;
		}
		x=nuevoX;
	}

}
