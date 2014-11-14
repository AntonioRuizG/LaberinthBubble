package com.neno.bubble;

public class Circulo {
	public double x;
	public double y;
	public double radio;
	
	public Circulo(double radio, double x, double y) {
		this.radio = radio;
		this.x = x;
		this.y = y;
	}

	public boolean hayColision(Circulo c){
		double x = this.x - c.x;
		double y = this.y - c.y;
		
		return (Math.sqrt(x*x + y*y)) < (radio + c.radio);
	}
	
	public Vector2d reflejar(Vector2d v, Circulo c){
		
		double x = this.x - c.x;
		double y = this.y - c.y;
		double moduloCuadrado = x*x + y*y;
		
		double dotproduct = x * v.x +
							y * v.y;
		
		//if(moduloCuadrado==0||dotproduct>=0)return v;
		if(moduloCuadrado==0)return v;
		
		double factor = (dotproduct*2/moduloCuadrado);
		
		return new Vector2d(v.x - factor * x,
							v.y - factor * y);
	}
}
