package edu.kdm.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import edu.kdm.recipereccomender.R;

public class RecipeAdapter extends BaseAdapter {

	
	private Activity context;
	private ArrayList<String> ingre;
	
	public RecipeAdapter(Activity context,ArrayList<String> ingre){
		this.ingre = ingre;
		this.context = context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return ingre.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return ingre.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 LayoutInflater inflater = (LayoutInflater) context
			        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			    View rowView = inflater.inflate(R.layout.recipe_row, parent, false);
			    TextView textView = (TextView) rowView.findViewById(R.id.ingredient);
			   
			    textView.setText(ingre.get(position));
			    

			    return rowView;
		
	}

}
