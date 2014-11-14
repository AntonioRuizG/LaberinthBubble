package com.neno.bubble;

import android.graphics.Bitmap;
import android.graphics.Color;

public class TechTalents {

	static int [][] array;
	private static boolean leido;
	
	public static int[][] getArray() {
		if(!leido){
			leer();
			
			leido=true;
		}
		return array;
	}

	private static void leer() {
		array = new int[51][65];
		
		
		Bitmap b = ImageDatabase.getBitmap("techarray");
		if(b==null){
			return;
		}
		
		
		int minI = b.getHeight();
		int minJ = b.getWidth();
		
		
		
		for(int i=0;i<minI;i++){
			for(int j=0;j<minJ;j++){
				int pixelColor=b.getPixel(i, j);
				if(pixelColor!=Color.BLACK){
					array[i][j]=1;
				}
			}
		}
		
		b = ImageDatabase.getBitmap("letras");
		if(b==null){
			return;
		}
		
		minJ = b.getHeight();
		minI = b.getWidth();
		
		for(int i=0;i<minI;i++){
			for(int j=0;j<minJ;j++){
				int pixelColor=b.getPixel(i, j);
				if(pixelColor!=Color.BLACK){
					array[i][j+51]=1;
				}
			}
		}
	}

}
