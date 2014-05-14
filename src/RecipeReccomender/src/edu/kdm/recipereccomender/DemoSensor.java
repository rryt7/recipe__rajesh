package edu.kdm.recipereccomender;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

public class DemoSensor extends Activity implements SensorEventListener{
    /** Called when the activity is first created. */
	
	private SensorManager sensorManager = null;
	private CanvasView mCanvasView;
	private float previousX = 0;
	private float previousY = 0;
	public  int mWidth;
	public  int mHeight;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
         mWidth = metrics.widthPixels;
         mHeight = metrics.heightPixels;
         previousX = mWidth/2;
         previousY = mHeight/2;
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mCanvasView = new CanvasView(this,mWidth,mHeight);
        mCanvasView.setOnTouchListener(new View.OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
				
				mCanvasView.mPaint.setStrokeWidth(50);
				previousX = mCanvasView.currentX = event.getX();
				previousY = mCanvasView.currentY = event.getY();
				mCanvasView.invalidate();
				
				return false;
			}
		});
        setContentView(mCanvasView);
    }
    
    @Override
    protected void onResume() {
     super.onResume();
     // Register this class as a listener for the accelerometer sensor
     sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
     // ...and the orientation sensor
     sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_NORMAL);
    }
   
    @Override
    protected void onStop() {
     // Unregister the listener
     sensorManager.unregisterListener(this);
     super.onStop();
    } 
 



	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		
		
	}

	public void onSensorChanged(SensorEvent event) {
		 synchronized (this) {
	           if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
	        	   
	               final float x =  event.values[1];
	               final float y =  event.values[2];
	               
	               mCanvasView.currentX = boundaryCheckX(mCanvasView.currentX - y);
	               
	               mCanvasView.currentY = boundaryCheckY(mCanvasView.currentY - x);
	               
	               
	               
	               if(mCanvasView!=null)
	               {
	            	   mCanvasView.mPath.moveTo(previousX , previousY);
	            	   mCanvasView.mPath.lineTo(mCanvasView.currentX, mCanvasView.currentY);
	            	   previousX = mCanvasView.currentX;
	            	   previousY = mCanvasView.currentY;
	            	   Rect dirty = new Rect((int)mCanvasView.currentX-50, (int)mCanvasView.currentY-50,(int) mCanvasView.currentX+50,(int)mCanvasView.currentY+50);
	            	   mCanvasView.invalidate(dirty);
	            	   //mCanvasView.invalidate();
	            	   mCanvasView.fixBitmap();
	               }
	               
	       		   
	           }
	       }
		 
		
	}
	
	private float boundaryCheckX(float f) {
		if(f<10)
		{
			return 10;
			
		}
		else if(f >mWidth-10)
		{
			return mWidth-10;
		}
		
		return f;
	}
	
	private float boundaryCheckY(float f) {
		if(f<10)
		{
			return 10;
			
		}
		else if(f >mHeight-10)
		{
			return mHeight-10;
		}
		
		return f;
	}
	class CanvasView  extends View
	{
		
		public Paint mPaint;
		public Path mPath;
		public float currentX;
		public float currentY;
		public Bitmap mBitmap;
		public Canvas mCanvas;
		public CanvasView(Context c,int width, int height)
		{
			super(c);
			mPaint = new Paint();
			mPath = new Path();
			mBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
			mCanvas = new Canvas(mBitmap);
			mPaint.setStyle(Style.STROKE);
			mPaint.setAntiAlias(true);
			mPaint.setStrokeWidth(50);
			mPaint.setStrokeCap(Cap.ROUND);
			currentX=mWidth/2;
			currentY=mHeight/2;
			mPath.moveTo(currentX, currentY);
		}

		public void fixBitmap() {
			mCanvas.drawPath(mPath,mPaint);
			float width = mPaint.getStrokeWidth();
			if(width/20 > 1.0)
			{
			width = (float) (width-1*width/20);
			}
			else
			{
				width = (float) (width-0.4);
			}
			if(width <= 1)
			{
				width = (float) 0.01;
			}
			System.out.println(width);
			mPaint.setStrokeWidth(width);
			mPath.reset();
		}

		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			canvas.drawColor(Color.WHITE);
			canvas.drawPath(mPath, mPaint);
			canvas.drawBitmap(mBitmap, 0, 0, mPaint);
			
		}
		
		
		
	}
}