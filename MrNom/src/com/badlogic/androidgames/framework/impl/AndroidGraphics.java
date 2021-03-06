package com.badlogic.androidgames.framework.impl;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.Log;

import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Pixmap;

public class AndroidGraphics implements Graphics{

	AssetManager assets;
	Bitmap frameBuffer;
	Canvas canvas;
	Paint paint;
	Rect srcRect = new Rect();
	Rect dstRect = new Rect();
	
	
	public AndroidGraphics(AssetManager assets, Bitmap frameBuffer){
		this.assets = assets;
		this.frameBuffer = frameBuffer;
		this.canvas = new Canvas(frameBuffer);    //bug
		this.paint = new Paint();
	}
	
	
	
	@Override
	public Pixmap newPixmap(String filename, PixmapFormat format) {
		// TODO Auto-generated method stub
		Config config = null;
		if(format==PixmapFormat.RGB565)
			config = Config.RGB_565;
		else if(format==PixmapFormat.ARGB4444)
			config = Config.ARGB_4444;
		else
			config = Config.ARGB_8888;
		Options options = new Options();
		options.inPreferredConfig = config;	
		InputStream in = null;
		Bitmap bitmap = null;
		try {
			in = assets.open(filename);
			bitmap = BitmapFactory.decodeStream(in);
			if(bitmap==null)
				throw new RuntimeException("Couldn't load bitmap from asset '"+
			filename+"'");
		} catch (IOException e) {
			// TODO: handle exception
			throw new RuntimeException("Couldn't load bitmap from asset '"+
					filename+"'");
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e2) {
					// TODO: handle exception
				}
			}
		}		
		if(bitmap.getConfig()==Config.RGB_565)
			format = PixmapFormat.RGB565;
		else if(bitmap.getConfig()==Config.ARGB_4444)
			format = PixmapFormat.ARGB4444;
		else
			format = PixmapFormat.ARGB8888;
		return new AndroidPixmap(bitmap, format);
		

	}

	@Override
	public void clear(int color) {
		// TODO Auto-generated method stub
		canvas.drawRGB((color & 0xff0000) >> 16, (color & 0xff00) >> 8, 
				(color & 0xff));
	}

	@Override
	public void drawPixel(int x, int y, int color) {
		// TODO Auto-generated method stub
		paint.setColor(color);
		canvas.drawPoint(x, y, paint);
	}

	@Override
	public void drawLine(int x, int y, int x2, int y2, int color) {
		// TODO Auto-generated method stub
		paint.setColor(color);
		canvas.drawLine(x, y, x2, y2, paint);
	}

	@Override
	public void drawRect(int x, int y, int width, int height, int color) {
		// TODO Auto-generated method stub
		paint.setColor(color);
		paint.setStyle(Style.FILL);
		canvas.drawRect(x, y, x+width-1, y+height-1, paint); //*
		
	}

	@Override
	public void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY,
			int srcWidth, int srcHeight) {
		// TODO Auto-generated method stub
		srcRect.left = srcX;
		srcRect.top = srcY;
		srcRect.right = srcX+srcWidth-1;
		srcRect.bottom = srcY+srcHeight-1;
		
		dstRect.left = x;
		dstRect.top = y;
		dstRect.right = x+srcWidth-1;
		dstRect.bottom = y+srcHeight-1;
		canvas.drawBitmap(((AndroidPixmap)pixmap).bitmap, srcRect, dstRect,
				null);
	}

	@Override
	public void drawPixmap(Pixmap pixmap, int x, int y) {
		// TODO Auto-generated method stub
		canvas.drawBitmap(((AndroidPixmap)pixmap).bitmap, x, y, null);
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return frameBuffer.getWidth();
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return frameBuffer.getHeight();
	}

}
