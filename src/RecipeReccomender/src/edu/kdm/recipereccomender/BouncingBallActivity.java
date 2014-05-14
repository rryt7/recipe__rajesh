package edu.kdm.recipereccomender;

import static android.hardware.SensorManager.DATA_X;
import static android.hardware.SensorManager.DATA_Y;
import static android.hardware.SensorManager.SENSOR_ACCELEROMETER;
import static android.hardware.SensorManager.SENSOR_DELAY_GAME;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import edu.kdm.manager.RecipeManager;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * This activity shows a ball that bounces around. The phone's 
 * accelerometer acts as gravity on the ball. When the ball hits
 * the edge, it bounces back and triggers the phone vibrator.
 */
public class BouncingBallActivity extends Activity implements Callback, SensorListener, OnTouchListener {
	//private static int BALL_RADIUS = 50;
	private SurfaceView surface;
	private SurfaceHolder holder;
	private boolean clearFlag = false;
	private ArrayList<String> totalIngrediants = new ArrayList<String>();
	ArrayList<BouncingBallModel> addList = new ArrayList<BouncingBallModel>();
	private ArrayList<BouncingBallModel> ingrediantsList = new ArrayList<BouncingBallModel>();
	private String[] colors = {"#FE2E2E","#FE2EF7","#642EFE","#2ECCFA","#FACC2E","#FE9A2E"};
	//private final BouncingBallModel model = new BouncingBallModel(BALL_RADIUS);
	private GameLoop gameLoop;
	private Paint backgroundPaint;
	private Paint ballPaint;
	private SensorManager sensorMgr;
	private long lastSensorUpdate = -1;
	private Button cusine;
	private Button more;
	private Button addIngre;
	private EditText newInge;
	
	private int width = 0;
	private int height = 0;
	
	private RelativeLayout addLayout;
	
	private RecipeManager manager = RecipeManager.getInstance();
	
	public static ArrayList<String> selectedList = new ArrayList<String>();

	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	setContentView(R.layout.bouncing_ball);
    	
    	surface = (SurfaceView) findViewById(R.id.bouncing_ball_surface);
    	cusine = (Button)findViewById(R.id.cusine);
    	more = (Button)findViewById(R.id.more);
    	addLayout = (RelativeLayout)findViewById(R.id.add_layout);
    	addIngre = (Button)findViewById(R.id.add_inge);
    	newInge = (EditText)findViewById(R.id.new_ingre);
    	
    	holder = surface.getHolder();
    	surface.getHolder().addCallback(this);
    	surface.setOnTouchListener(this);
    	
    	
    	
    	backgroundPaint = new Paint();
		backgroundPaint.setColor(Color.WHITE);

		ballPaint = new Paint();
		ballPaint.setColor(Color.BLUE);
		ballPaint.setAntiAlias(true);
		
		
		addTotalIngrediants();
		getInitialSetUp();
		
		manager.getIngredientList().clear();
		
		
		cusine.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				for (BouncingBallModel item : ingrediantsList) {
					
					if(item.getBallRadius() == 100 && !item.getText().trim().equalsIgnoreCase("")){
						manager.getIngredientList().add(item.getText());
						selectedList.add(item.getText().toLowerCase().trim());
					}
				}
				
				Intent intent = new Intent(BouncingBallActivity.this, MainActivity.class);
				startActivity(intent);
				
				
				
			}
		});
		
		more.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				//addMoreIngrediants();
				addLayout.setVisibility(View.VISIBLE);
				
				
			}

			
		});
		
		addIngre.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				addLayout.setVisibility(View.GONE);
				
				String ingre = newInge.getText().toString();
				BouncingBallModel newIngr = new BouncingBallModel(100,ingre);
				newIngr.moveBall(100, 50);
				
				newIngr.setSize(width, height);
				addList.add(newIngr);
				clearFlag = true;
				newInge.setText("");
				
			}
		});
		
    }
    
	
	
	private void addTotalIngrediants() {
		
		totalIngrediants.add("Chicken");
		totalIngrediants.add("Salt");
		totalIngrediants.add("Oil");
		totalIngrediants.add("Chilli");
		totalIngrediants.add("Youget");
		totalIngrediants.add("Beef");
		totalIngrediants.add("Pork");
		totalIngrediants.add("Milk");
		totalIngrediants.add("Eggs");
		totalIngrediants.add("Suger");
		totalIngrediants.add("Potato");
		totalIngrediants.add("Tarmeric");
		
	}



	

	private void getInitialSetUp() {
		
		if(!totalIngrediants.isEmpty()){
			BouncingBallModel first = new BouncingBallModel(50,totalIngrediants.remove(0));
			first.moveBall(100, 50);
			ingrediantsList.add(first);
		}
		
		
		if(!totalIngrediants.isEmpty()){
			BouncingBallModel first = new BouncingBallModel(50,totalIngrediants.remove(0));
			first.moveBall(400, 250);
			ingrediantsList.add(first);
		}
		
		if(!totalIngrediants.isEmpty()){
			BouncingBallModel first = new BouncingBallModel(50,totalIngrediants.remove(0));
			first.moveBall(700, 150);
			ingrediantsList.add(first);
		}
		
		if(!totalIngrediants.isEmpty()){
			BouncingBallModel first = new BouncingBallModel(50,totalIngrediants.remove(0));
			first.moveBall(1000, 100);
			ingrediantsList.add(first);
		}
		/*BouncingBallModel oil = new BouncingBallModel(50,"Oil");
		BouncingBallModel salt = new BouncingBallModel(50,"Salt");
		BouncingBallModel onions = new BouncingBallModel(50,"Onions");*/
		
		/*oil.moveBall(400, 250);
		salt.moveBall(700, 150);
		onions.moveBall(1000, 100);
		
		ingrediantsList.add(oil);
		ingrediantsList.add(salt);
		ingrediantsList.add(onions);*/
	}

	@Override
	protected void onPause() {
		super.onPause();
		
		//model.setVibrator(null);
		
		sensorMgr.unregisterListener(this, SENSOR_ACCELEROMETER);
		sensorMgr = null;
		for (BouncingBallModel model : ingrediantsList) {
			model.setAccel(0, 0);	
		}
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
		boolean accelSupported = sensorMgr.registerListener(this, 
				SENSOR_ACCELEROMETER,
				SENSOR_DELAY_GAME);
		
		if (!accelSupported) {
			// on accelerometer on this device
			sensorMgr.unregisterListener(this, SENSOR_ACCELEROMETER);
			// TODO show an error
		}
		
		// NOTE 1: you cannot get system services before onCreate()
		// NOTE 2: AndroidManifest.xml must contain this line:
		// <uses-permission android:name="android.permission.VIBRATE"/>
		//Vibrator vibrator = (Vibrator) getSystemService(Activity.VIBRATOR_SERVICE);
		//model.setVibrator(vibrator);
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		this.width = width;
		this.height = height;
		for (BouncingBallModel model : ingrediantsList) {
			model.setSize(width, height);
		}
		
	}

	public void surfaceCreated(SurfaceHolder holder) {
		gameLoop = new GameLoop();
		gameLoop.start();
	}
	
	private void draw() {
		// TODO thread safety - the SurfaceView could go away while we are drawing
		
		Canvas c = null;
		try {
			// NOTE: in the LunarLander they don't have any synchronization here,
			// so I guess this is OK. It will return null if the holder is not ready
			c = holder.lockCanvas();
			
			
			if (c != null) {
				doDraw(c);
			}
		} finally {
			if (c != null) {
				holder.unlockCanvasAndPost(c);
			}
		}
	}
	
	private void doDraw(Canvas c) {
		int width = c.getWidth();
		int height = c.getHeight();
		c.drawRect(0, 0, width, height, backgroundPaint);
		
		float ballX, ballY;
		int i=0;
		for (BouncingBallModel model : ingrediantsList) {
			
		synchronized (model.LOCK) {
			ballX = model.ballPixelX;
			ballY = model.ballPixelY;
			
		}
		ballPaint.setColor(Color.parseColor(colors[i%colors.length]));
		c.drawCircle(ballX, ballY, model.getBallRadius(), ballPaint);
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(2);
		paint.setTextSize(20);
		float len = paint.measureText(model.getText());
		c.drawText(model.getText(),ballX-len/2, ballY, paint);
		i++;
		
		}
		
		if(clearFlag ){
			for (BouncingBallModel ball : addList) {
				
				ingrediantsList.add(ball);
			}
			clearFlag = false;
			addList.clear();
		}
		
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		try {
			for (BouncingBallModel model : ingrediantsList) {
			model.setSize(0,0);
			}
			gameLoop.safeStop();
		} finally {
			gameLoop = null;
		}
	}
    
	private class GameLoop extends Thread {
		private volatile boolean running = true;
		
		public void run() {
			while (running) {
				try {
					// TODO don't like this hardcoding
					TimeUnit.MILLISECONDS.sleep(5);
					
					draw();
					
					for (BouncingBallModel model : ingrediantsList) {
						
					model.updatePhysics();
					
					}

				} catch (InterruptedException ie) {
					running = false;
				}
			}
		}
		
		public void safeStop() {
			running = false;
			interrupt();
		}
	}

	public void onAccuracyChanged(int sensor, int accuracy) {		
	}

	public void onSensorChanged(int sensor, float[] values) {
		if (sensor == SENSOR_ACCELEROMETER) {
			long curTime = System.currentTimeMillis();
			// only allow one update every 50ms, otherwise updates
			// come way too fast
			if (lastSensorUpdate == -1 || (curTime - lastSensorUpdate) > 50) {
				lastSensorUpdate = curTime;
				for (BouncingBallModel model : ingrediantsList) {
				model.setAccel(values[DATA_X], values[DATA_Y]);
				}
			}
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			float x = event.getX();
			float y = event.getY();
			for (BouncingBallModel model : ingrediantsList) {
			float diffX = model.ballPixelX;
			float diffY = model.ballPixelY;
			
			double dist = (diffX-x)*(diffX-x) + (diffY-y)*(diffY-y);
			dist = Math.sqrt(dist);
			if(dist < model.getBallRadius() ){
				if(model.getBallRadius() <100){
				int BALL_RADIUS = model.getBallRadius() *2;
				model.setBallRadius(BALL_RADIUS);
				}else{
				int	BALL_RADIUS = model.getBallRadius() /2;
					model.setBallRadius(BALL_RADIUS);
				}
				break;
			}
			}
			
            // Do what you want
            return true;
        }
		return false;
	}
}
