package com.neno.bubble;

import java.util.Hashtable;

import android.graphics.Bitmap;

public class ImageDatabase {
	private static Hashtable<String,Bitmap> imagenes=new Hashtable<String,Bitmap>();
	
	public static Bitmap getBitmap(String id){
		return imagenes.get(id);
	}
	
	public static void putBitmap(Bitmap b, String id){
		imagenes.put(id, b);
	}

	public static void putBitmap(Bitmap b, String id, boolean noTrans){
		imagenes.put(id, b);
	}

}
