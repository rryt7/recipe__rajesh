package edu.kdm.recipeprocessor;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import edu.kdm.bean.RecipeItem;

public class RecipeProcessor {

	private String head = "http://allrecipes.com/Recipe/";
	private String tail = "/Detail.aspx";
	//Rajesh code starts
	/*RecipeProcessor(){
		File folder = new File("C:/varun/KDM/work/recipes_dataset");
		File[] listOfFiles = folder.listFiles();
		for(int i=0;i<listOfFiles.length;i++){
			File f = listOfFiles[i];
			try {
				RecipeItem RI = new RecipeItem();
				RI = process(f);
				String s = RI.getIngred_search();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}*/
	//Rajesh code ends
	public RecipeItem process(File recipe) throws Exception{
		Scanner scan = new Scanner(recipe);
		String url;
		String name;
		ArrayList<String> ingreadList = new ArrayList<String>();
		ArrayList<String> ingreadSearchList = new ArrayList<String>();
		
		ArrayList<String> directionsList = new ArrayList<String>();
		url = scan.nextLine();
		name = url.substring(head.length(), url.length()-tail.length());
		scan.nextLine();
		scan.nextLine();
		scan.nextLine();
		while (scan.hasNext()) {
			String line = scan.nextLine().trim();
			
			if(line.contains("-------------")){
				break;
			}else{
				ingreadList.add(line);
				
				String[] splits = line.split(" ");
				for (String split : splits) {
					
					split = split.toLowerCase();
					if(!split.matches(".*\\d.*") && !ingreadSearchList.contains(split) && !split.contains("cup") && !split.contains("spoon")){
						ingreadSearchList.add(split);
					}
				}
			}
			
		}
		
		
		
		while (scan.hasNext()) {
			String line = scan.nextLine().trim();
			line = line.replaceAll("in\\.", "in");
			if(line.contains("-----------")){
				scan.close();
				break;
			}else{
				String[] splits = line.split("\\.");
				for (String string : splits) {
					directionsList.add(string.trim());
				}
			}
		}
		
		String searchString = "";
		Collections.sort(ingreadSearchList);
		
		for (String string : ingreadSearchList) {
			searchString = searchString +" "+ string;
		}
		RecipeItem item = new RecipeItem();
		
		item.setUrl(url);
		item.setRecipeName(name);
		item.setIngrediants(ingreadList);
		item.setDirections(directionsList);
		item.setIngred_search(searchString);
		
		return item;
	}
}



