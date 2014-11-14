package com.neno.bubble;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Style;

public class Pelota extends Colisionable{
	
	private double radio;
	private Paint paint;
	private double limiteDerecho=10000;
	private double limiteIzquierdo=-10000;
	private double limiteSuperior=-10000;
	private double limiteInferior=10000;
	private String id;

	public Pelota(double x, double y, double radio){
		super.x=x;
		super.y=y;
		this.radio=radio;
		id="pelota";
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
	public void mover(double t) {
		double nuevoX = x + (t*vx);
		double nuevoY = y + (t*vy);
		
		// comprobar limites
		if(nuevoX > limiteDerecho){
			nuevoX = limiteDerecho;
			vx *= -1;
			if(vy>0&&vx>0){
				vx-=3;
				vy+=3;
			}
			if(vy>0&&vx<0){
				vx+=3;
				vy+=3;
			}
			if(vy<0&&vx>0){
				vx-=3;
				vy-=3;
			}
			if(vy<0&&vx<0){
				vx+=3;
				vy-=3;
			}
		}
		if(nuevoX < limiteIzquierdo){
			nuevoX = limiteIzquierdo;
			vx *= -1;
			if(vy>0&&vx>0){
				vx-=3;
				vy+=3;
			}
			if(vy>0&&vx<0){
				vx+=3;
				vy+=3;
			}
			if(vy<0&&vx>0){
				vx-=3;
				vy-=3;
			}
			if(vy<0&&vx<0){
				vx+=3;
				vy-=3;
			}
		}
		if(nuevoY < limiteSuperior){
			nuevoY = limiteSuperior;
			vy *= -1;
		}
		if(nuevoY > limiteInferior){
			nuevoY = limiteInferior;
			vy *= -1;
		}
		x=nuevoX;
		y=nuevoY;
	}

	public void setColor(int c) {
		paint.setColor(c);
	}
	
	@Override
	public void onSizeChanged(int w, int h, int oldw, int oldh) {
		if(w!=0 && h!=0){
			limiteDerecho = w - radio;
			limiteIzquierdo = radio;
			limiteSuperior = radio;
			limiteInferior= h - radio;
		}
	}

}
