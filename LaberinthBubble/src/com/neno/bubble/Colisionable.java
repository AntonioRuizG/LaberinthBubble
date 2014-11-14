package com.neno.bubble;

import java.util.ArrayList;

public abstract class Colisionable extends Dibujable{
	private ArrayList<Circulo> colisionCircles = new ArrayList<Circulo>();
	
	public void addColisionCircle(Circulo c){
		colisionCircles.add(c);
	}
	
	public boolean colision(Colisionable c){
		if(c==this)return false;
		if(vx==0&&vy==0)return false;
		
		for(int i=0;i<colisionCircles.size();i++){
			Circulo c1 = colisionCircles.get(i);
			if(c1==null)return false;
			Circulo c1Desp = new Circulo(c1.radio, c1.x+x, c1.y+y);
			for(int j=0;j<c.colisionCircles.size();j++){
				Circulo c2 = c.colisionCircles.get(j);
				if(c2==null)return false;
				Circulo c2Desp = new Circulo(c2.radio, c2.x+c.x, c2.y+c.y);
				if(c1Desp.hayColision(c2Desp)){
					
					Vector2d vAntigua1 = new Vector2d(vx, vy);
					Vector2d vNueva1 = c1Desp.reflejar(vAntigua1 , c2Desp);
					vx = vNueva1.x;
					vy = vNueva1.y;
					//retrocede hasta dejar tangentes los circulos que
					//han chocado
					double factor1 = calcularFactor(vAntigua1,c1Desp,c2Desp);
					x += vAntigua1.x*factor1;
					y += vAntigua1.y*factor1;
					x += vNueva1.x*-factor1;
					y += vNueva1.y*-factor1;
					
					return true;
				}
			}
		}
		return false;
	}

	private double calcularFactor(Vector2d v, Circulo c1, Circulo c2) {
		
		double dx = c1.x - c2.x;
		double dy = c1.y - c2.y;
		double dr = c2.radio + c1.radio;
		double a = (v.x*v.x + v.y*v.y);
		double b = 2*(v.x*dx + v.y*dy);
		double c = dx*dx + dy*dy - dr*dr;
		
		double radic = Math.sqrt(b*b -4*a*c);
		double d1 = -b + radic;
		double d2 =  -b - radic;
		
		double d1d = Math.abs(d1);
		double d2d =  Math.abs(d2);
		
		if(d1d>d2d)return d2/(2*a);
		return d1/(2*a);
	}

	public void clearColisionCircles() {
		colisionCircles.clear();
	}
}
