package edu.kdm.recipereccomender;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import edu.kdm.adapter.IngredientsAdapter;
import edu.kdm.bean.RecipeItem;
import edu.kdm.database.RetrievemPoseData;
import edu.kdm.database.UploadmPoseData;
import edu.kdm.manager.RecipeManager;
import edu.kdm.views.Layer;

public class ResultActivity extends Activity {

	public static String path = "/mnt/sdcard/kdm/recipes_dataset/";
	private ViewFlipper viewFlipper;
	private Button detail;
	private TextView name ;
	private ListView ingred;
	private Button more;
    private float lastX;
	
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result_layout);
		 viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
		 ingred = (ListView)findViewById(R.id.ingred_list);
		 detail = (Button)findViewById(R.id.detail);
		 name = (TextView)findViewById(R.id.name);
		 more = (Button)findViewById(R.id.similar);
		 
		 RecipeManager manager = RecipeManager.getInstance();
		 /*File dir = new File(path);
		if(dir.exists()){
			System.out.println("exists");
		}else{
			
			System.out.println("something wrong");
		}
		File[] fileList = dir.listFiles();
		ArrayList<RecipeItem> list = new ArrayList<RecipeItem>();
		for (File file : fileList) {
			RecipeProcessor processor = new RecipeProcessor();
			try {
				RecipeItem recipe = processor.process(file);
				list.add(recipe);
				System.out.println(recipe.getIngred_search());
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
			
		}
		
		
		Toast.makeText(this, "recipies list "+list.size(), Toast.LENGTH_SHORT).show();
		*/
		/*ArrayList<String> ingre = new ArrayList<String>();
		ingre.add("potato");
		ingre.add("sugar");*/
		
		
		//addToDB(list);
		
		retriveFromDB(/*manager.getIngredientList()*/BouncingBallActivity.selectedList);
		
		
		detail.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View button) {
				
				String url = (String) button.getTag();
				
				
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				startActivity(i);
			}
		});
		
		more.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ResultActivity.this, RecommendationList.class);
				startActivity(intent);
				
			}
		});
		
	}

	private void setIngredients(ArrayList<String> ingre) {
	
		BaseAdapter ingreAdapter = new IngredientsAdapter(this, ingre);
		ingred.setAdapter(ingreAdapter);
		
	}

	private void retriveFromDB(ArrayList<String> ingrediants) {
		
		RetrievemPoseData retrive = new RetrievemPoseData();
		
		ArrayList<RecipeItem> list = (ArrayList<RecipeItem>) retrive.getRecipies(this, ingrediants);
		if(list == null){
			Toast.makeText(getApplicationContext(), "No results found for these ingredients", Toast.LENGTH_SHORT).show();
			return ;
		}
		System.out.println("Recipie count"+list.size());
		for (RecipeItem recipeItem : list) {
			
			System.out.println("Name : "+recipeItem.getRecipeName());
		}
		
		RecipeItem item = list.get(0);
		int size = item.getDirections().size() ;
		
		for (int i = 0; i < size; i++) {
			
			Layer layer = new Layer(this);
			
			layer.setGravity(Gravity.CENTER);
			TextView tv = new TextView(this);
			tv.setTextSize(20);
			tv.setTextColor(Color.WHITE);
			tv.setGravity(Gravity.CENTER);
			//android:textSize="20dp"
			tv.setText(item.getDirections().get(i));
			layer.addView(tv);
			viewFlipper.addView(layer);
		}
		
		name.setText(item.getRecipeName());
		detail.setTag(item.getUrl());
		setIngredients(item.getIngrediants());
		
		
		
	}

	private void addToDB(ArrayList<RecipeItem> list) {

		UploadmPoseData upload = new UploadmPoseData();
		for (RecipeItem recipeItem : list) {
			boolean flag = upload.uploadRecipeDetails(recipeItem, this);
			System.out.println("upload result "+ recipeItem.getRecipeName()+ " "+ flag);
		}
		
	
	}
	
	  // Method to handle touch event like left to right swap and right to left swap
    public boolean onTouchEvent(MotionEvent touchevent) 
    {
                 switch (touchevent.getAction())
                 {
                        // when user first touches the screen to swap
                         case MotionEvent.ACTION_DOWN: 
                         {
                             lastX = touchevent.getX();
                             break;
                        }
                         case MotionEvent.ACTION_UP: 
                         {
                             float currentX = touchevent.getX();
                             
                             // if left to right swipe on screen
                             if (lastX < currentX) 
                             {
                                  // If no more View/Child to flip
                                 if (viewFlipper.getDisplayedChild() == 0)
                                     break;
                                 
                                 // set the required Animation type to ViewFlipper
                                 // The Next screen will come in form Left and current Screen will go OUT from Right 
                                 viewFlipper.setInAnimation(this, R.anim.in_from_left);
                                 viewFlipper.setOutAnimation(this, R.anim.out_to_right);
                                 // Show the next Screen
                                 viewFlipper.showNext();
                             }
                             
                             // if right to left swipe on screen
                             if (lastX > currentX)
                             {
                                 if (viewFlipper.getDisplayedChild() == 1)
                                     break;
                                 // set the required Animation type to ViewFlipper
                                 // The Next screen will come in form Right and current Screen will go OUT from Left 
                                 viewFlipper.setInAnimation(this, R.anim.in_from_right);
                                 viewFlipper.setOutAnimation(this, R.anim.out_to_left);
                                 // Show The Previous Screen
                                 viewFlipper.showPrevious();
                             }
                             break;
                         }
                 }
                 return false;
    }
	
}
