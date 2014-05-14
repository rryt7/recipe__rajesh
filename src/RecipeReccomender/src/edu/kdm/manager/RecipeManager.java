package edu.kdm.manager;

import java.util.ArrayList;

import edu.kdm.bean.RecipeItem;

public class RecipeManager {
	
	
	private static RecipeManager INSTANCE;
	
	private ArrayList<String> ingredientList = new ArrayList<String>();
	
	private ArrayList<RecipeItem> itemsList = new ArrayList<RecipeItem>();
	
	private RecipeManager(){
		
	}
	
	public static RecipeManager getInstance(){
		
		if(INSTANCE == null){
			return new RecipeManager();
		}else{
			return INSTANCE;
		}
	}

	public ArrayList<String> getIngredientList() {
		return ingredientList;
	}

	public void setIngredientList(ArrayList<String> ingredientList) {
		this.ingredientList = ingredientList;
	}

	public ArrayList<RecipeItem> getItemsList() {
		return itemsList;
	}

	public void setItemsList(ArrayList<RecipeItem> itemsList) {
		this.itemsList = itemsList;
	}
	
	
	
	

}
