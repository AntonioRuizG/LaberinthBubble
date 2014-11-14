package com.neno.bubble;

import android.graphics.Canvas;

public abstract class Dibujable {
	protected double x;
	protected double y;
	protected double vx;
	protected double vy;
	
	public abstract void dibujar(Canvas c);
	public abstract void onSizeChanged(int w, int h, int oldw, int oldh);
	public abstract void mover(double t);
	public void setVelocidad(double x, double y) {
		vx=x;
		vy=y;
	}
}
