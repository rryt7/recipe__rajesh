package edu.kdm.views;

import android.content.Context;
import android.widget.RelativeLayout;

public class Layer extends RelativeLayout {

	public Layer(Context context) {
		super(context);
		this.setLayoutParams(
				new LayoutParams(
						LayoutParams.FILL_PARENT,
						LayoutParams.FILL_PARENT)
				);
		
	}
	
	public void setPosition(int left,int top){
		
		this.setPadding(left, top, 0, 0);
	}
	
	
	
	

}
