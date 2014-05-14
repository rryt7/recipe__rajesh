package edu.kdm.bean;

import java.util.ArrayList;

public class RecipeItem {

	private String recipeName;
	private String url;
	private ArrayList<String> ingrediants = new ArrayList<String>();
	//Rajesh code starts
	private String ingred_search;
	//public String ingred_search;
	//Rajesh code ends
	private ArrayList<String> directions = new ArrayList<String>();
	private String time = "";
	public String getRecipeName() {
		return recipeName;
	}
	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
	public ArrayList<String> getIngrediants() {
		return ingrediants;
	}
	
	public String getIngrediantString() {
		String ingreStr = "";
		for (String string : ingrediants) {
			ingreStr = ingreStr+ "\n" + string;
		}
		return ingreStr.trim();
	}
	
	public void setIngrediants(ArrayList<String> ingrediants) {
		this.ingrediants = ingrediants;
	}
	public String getIngred_search() {
		return ingred_search;
	}
	public void setIngred_search(String ingred_search) {
		this.ingred_search = ingred_search;
	}
	public ArrayList<String> getDirections() {
		return directions;
	}
	
	public String getDirectionString() {
		String directionStr = "";
		for (String string : directions) {
			directionStr = directionStr+ "\n" + string;
		}
		return directionStr.trim();
	}
	public void setDirections(ArrayList<String> directions) {
		this.directions = directions;
	}
	
	public void setDirections(String directions) {
		
		String[] directionList = directions.split("\n");
		this.directions.clear();
		for (String string : directionList) {
			if(!string.trim().equals(""))
			this.directions.add(string);
		}
		
	}
	
	
	public void setIngrediants(String ingredients) {
		
		String[] ingredientsList = ingredients.split("\n");
		this.ingrediants.clear();
		for (String string : ingredientsList) {
			if(!string.trim().equals(""))
			this.ingrediants.add(string);
		}
		
	}
	
	public void setTime(String time){
		this.time  = time;
	}
	public String getTime() {
		
		return time;
	}
	
	
	

}
