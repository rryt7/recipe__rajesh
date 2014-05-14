package edu.kdm.recipereccomender;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import edu.kdm.adapter.IngredientsAdapter;
import edu.kdm.bean.RecipeItem;
import edu.kdm.database.RetrievemPoseData;

public class RecommendationList extends Activity{
		private String list = "Chicken-with-Green-Peppers-in-Black-Bean-Sauce,Grilled-Chicken-with-Fresh-Mango-Salsa,Grilled-Chicken-Breasts-with-Zesty-Peanut-Sauce,Grilled-Sweet-Italian-Chicken-Sausage-with-Tomato-Cream-Sauce-Over-Linguine,Beef-Sirloin-Kabobs-with-Roasted-Red-Pepper-Dipping-Sauce,Gnocchi-with-Cherry-Tomato-Sauce,Rotini-Chicken-Casserole,Chicago-Style-Stuffed-Pizza,Grandmas-Homemade-Pizza-ala-Da-Boys,25-Minute-Tunisian-Vegetable-Couscous,Chili-Beef-Bake,Peruvian-Chicken-Soup-Aguadito-de-Pollo,Quick-Chicken-Stew,Penne-with-Spicy-Chicken-Sausage-Beans-and-Greens,Easy-Chicken-Stew-with-Apple,Auto-Parts-Chicken,30-Minute-Minestrone-2,Baked-Spaghetti-with-Chicken,Stuffed-Chicken-Breasts-with-Asparagus-and-Parmesan-Rice,Chestnut-Lentils-and-Vegetable-Stew,Wakulas-First-Attempt-at-Vegetable-Stew,Moroccan-Couscous,Moroccan-Chicken-and-Whole-Grain-Couscous,Company-Couscous,Tempeh-Kabobs-with-Moroccan-Couscous,Roasted-Lamb-with-Root-Vegetables,Greek-Pasta-Salad-with-Roasted-Vegetables-and-Feta,Smoky-Chipotle-Hummus,Ninas-Texas-Chili,Smokin-Scovilles-Turkey-Chili,Secret-Ingredient-Chili,Pasta-with-Thai-Style-Chicken,Minestrone-Soup-II,Italian-Turkey-Cutlets,Halibut-with-Zesty-Peach-Salsa,Easy-Black-Bean-Salsa,Mushroom-Cheese-and-Haddock-Bake,Grandmas-Best-Ever-Sour-Cream-Lasagna,Cheesy-Kielbasa-Bake,Hunters-Venison-Casserole,Skillet-Chicken-Pasta,Ground-Turkey-Noodle-Bake,Turkey-Garbanzo-Bean-and-Kale-Soup-with-Pasta,Peachy-Avocado-Salsa,Kickin-Spicy-Turkey-Beer-Chili,Lucies-Vegetarian-Chili,Sarahs-Spicy-Turkey-Chili,Tortilla-Pie,Chompchae-Deopbap-Korean-Spicy-Tuna-and-Rice,A-Pad-Thai-Worth-Making,Chilled-Salmon-With-Summer-Tomato-Salsa,Grilled-Macadamia-Crusted-Tuna-with-Papaya-Salsa,Blackened-Tuna-Steaks-with-Mango-Salsa,Citrus-Swordfish-With-Citrus-Salsa,Spicy-Jalapeno-Chicken-Sausage-with-Mango-Pineapple-Salsa,Corn-and-Tomatillo-Salsa,Green-Salad-with-Dried-Mint,Summertime-Salsa,Warm-Chicken-and-Mango-Salad,Mango-Berry-Fruit-Salad,Habanero-Salsa,Pittsburgh-Football-Party-Cilantro-Salsa,Maine-Wild-Blueberry-Salsa,Classic-Pub-Style-Nachos,Spanish-Chicken-2,Spicy-Beans-N-Rice,Sausage-Red-Beans-n-Rice,Beef-Noodle-Casserole,South-of-the-Border-Salad,San-Antonio-Taco-Salad,Joys-Taco-Salad,Arroz-con-Pollo-Chicken-and-Rice,Asopao-de-Pollo-Chicken-Rice-Gumbo,Mamas-Asian-Chicken-and-Rice,Lime-Chicken-with-Cilantro-Cream-Sauce-and-Roasted-Zucchini,Chicken-Rice-Skillet,Pineapple-Chicken-with-Spaghetti,Chicken-Breasts-Stuffed-with-Perfection,Broiled-Chicken-Breasts-with-Chutney-Lime-Glaze-and-Broccoli-Slaw,Mushroom-Stuffed-Chicken-Breasts-in-a-Balsamic-Pan-Sauce,Crawfish-Stuffed-Chicken-Breasts,Baked-Tilapia-with-Arugula-and-Pecan-Pesto,Penne-with-Black-Olive-Pesto-and-Sun-Dried-Tomatoes,Soft-Mahi-Mahi-Tacos-with-Ginger-Lime-Dressing,Gnocchi-and-Peppers-in-Balsamic-Sauce,Spaghetti-Sauce-with-Meatballs,Fabulous-Pesto-Pasta-Salad,Chicken-Noodle-Casserole,Homemade-Pizza-Supreme,Easy-Spinach-Lasagna,Spinach-Manicotti,Spinach-Chicken-Manicotti,Stuffed-Peppers-with-Creole-Sauce,Chicken-Stuffed-Peppers-with-Enchilada-Sauce,Chicken-Pesto-Pizza-with-Roasted-Red-Peppers-and-Asparagus,Spaghetti-Casserole,Spaghetti-Ham-Bake,Roasted-Vegetable-Ziti-Bake,Cheddar-Topped-Veggie-Beef-Skillet-Dinner,Sausage-Macaroni-Bake,Best-Ever-Sausage-with-Peppers-Onions-and-Beer,Sweet-Italian-Sausage-Ragout-with-Linguine,Curly-Noodle-Chicken-Soup,Creamy-Beet-With-Dill-Soup,Creamy-Sweet-Potato-With-Ginger-Soup,Thai-Chicken-with-Basil-Stir-Fry,Chicken-with-Pepper-Cheese-Sauce,Chickpeas-in-Tomato-Sauce-With-Feta-and-Wine,Arugula-Salad-with-Avocado-Citrus-Vinaigrette,Chicken-and-Pasta-in-a-Mango-Cream-Sauce,Malabari-Chicken-Stew,Picante-Chicken-Rice-Burritos,White-Chili-with-Chicken,Chicken-Chili-II,Amelias-Slow-Cooker-Brunswick-Stew,Carnation-Chicken-And-Wild-Rice-Soup,Spicy-Seafood-Bisque,Seafood-Stew-3,Chicken-Scampi,Phenomenal-Chicken-and-Pasta-in-Creamy-Pesto-Sauce,Pasta-with-Bacon-and-Peas,Chicken-and-Tomato-Scampi,Creamy-Pesto-Pasta-Salad-with-Chicken-Asparagus-and-Cherry-Tomatoes,Savory-Vegetable-Beef-Stew,West-African-Vegetable-Stew,Couscous-with-Mushrooms-and-Sun-Dried-Tomatoes,Spicy-Couscous-with-Dates,Middle-Eastern-Rice-with-Black-Beans-and-Chickpeas,Grilled-Chicken-Breasts-with-Fresh-Strawberry-Salsa,Pasta-with-Mock-Creamy-Tomato-Sauce,Greek-Pasta-Salad-with-Shrimp-Tomatoes-Zucchini-Peppers-and-Feta,Emilys-Famous-Roasted-Vegetable-Salad,Greek-Pasta-Salad-I,Kas-Chili,Emilys-Famous-Chili,Slow-Cooked-Chili,Spicy-Pumpkin-Chili,Easy-Chili-I,Pressure-Cooker-Chili,Daddys-If-Theyda-had-This-at-the-Alamo-we-wouldha-WON-Texas-Chili,Mr-Bills-New-Mexico-Buffalo-Chili,Pumpkin-Turkey-Chili,Terrific-Turkey-Chili,Venison-Burger-and-Steak-Chili,Slow-Cooker-Venison-Chili-for-the-Big-Game,Killer-Chili,Beefy-Cowboy-Chili,Quick-and-Easy-Mexican-Beef-Chili,Creamy-Bow-Tie-Pasta-Salad-with-Prosciutto-Peas-Grapes-and-Dried-Cranberries,Pork-Chops-with-Garden-Rice,Grilled-Mahi-Mahi-with-Roasted-Pepper-Sauce-and-Cilantro-Pesto,Penne-Tomato-and-Mozzarella-Salad,Tortellini-Minestrone,Minestrone-Soup-2,Prosciutto-Fave-Minestrone-alla-Riso,Italian-Vegetable-Soup-with-Beans-Spinach--Pesto,Quick-Italian-Vegetable-Soup,Zesty-Rice-N-Bean-Casserole,Southwest-Pasta-Bake,Sour-Cream-Turkey-Enchiladas,Zucchini-Lasagna-2,Penne-Salami-Bake,Simple-Spinach-Lasagna,Baked-Ziti-with-Turkey-Meatballs-2,Olive-Lamb-and-Red-Pepper-Casserole,Flash-blasted-Broccoli-and-Feta-Pasta,Pasta-Primavera-with-Smoked-Gouda,Zippy-Turkey-Tortilla-Bake,Hot-and-Sour-Soup-with-Bean-Sprouts,Savory-Zucchini-Muffins,Seven-Layer-Dip-II,Tex-Mex-Black-Bean-Dip,Seven-Layer-Dip-III,All-Bran-Veggie-Pizza-With-Cheese-Crust,Muir-Glen-Salsa-Guacamole,Waistline-Friendly-Turkey-Chili,Butternut-Squash-and-Turkey-Chili,Quinoa-and-Black-Bean-Chili,Chicken-and-Black-Bean-Chili,Meatiest-Vegetarian-Chili-from-your-Slow-Cooker,Veggie-Vegetarian-Chili,Summer-Vegetarian-Chili,Smoky-Sweet-Pea-Soup,Macaroni-Meatball-Soup,Hidden-Valley-Harvest-Butternut-Squash-Soup-with-Ranch-Croutons,Meatball-and-Olive-Stew-Albondigas-Verdes,Meatloaf-With-a-Twist,Mikes-Portuguese-Tuna-Rice-Casserole,Rosemary-Chicken-Stew,Runaway-Bay-Jamaican-Chicken,Creamy-Chicken-Vegetable-Chowder,Brazilian-Chicken-with-Coconut-Milk,Chicken-and-Tomato-Stew-with-Arugula-and-Cannellini,Slow-Cooker-Moroccan-Chicken,Diced-Lamb-with-Roasted-Vegetables-and-Couscous,Smokin-Texas-Chili,Hamburger-Minestrone,Kens-Minestrone-Soup,Turkey-Minestrone,Lasagna-Bolognese,Corn-n-Beef-Pasta-Bake,Creamy-Mexican-Salsa-Dip,7-Layer-Taco-Dip,Caliente-Christmas-Salsa,Blueberry-Salsa-Salad,Heathers-Cilantro-Black-Bean-and-Corn-Salsa,Corn-and-Avocado-Salsa,Lindas-Summertime-Eggplant-Salsa,Ancho-Chipotle-Salsa,Cool-Cucumber-Salsa,Cucumber-Melon-Salsa,That-Good-Salad,Cheesy-Beef-Taco-Salad";
		public static RecipeItem selecteditem;
		private ArrayList<RecipeItem> splits;
		ListView lv;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.list_screen);
		lv = (ListView)findViewById(R.id.listView1);
		
		IngredientsAdapter adapter = new IngredientsAdapter(RecommendationList.this, getInput());
		lv.setAdapter(adapter);
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				selecteditem  = splits.get(arg2);
				
				Intent intent = new Intent(RecommendationList.this, RecoResultActivity.class);
				startActivity(intent);
				
			}
		});
		}

		private ArrayList<String> getInput() {
			ArrayList<String> Stlist = new ArrayList<String>();
			RetrievemPoseData retrive = new RetrievemPoseData();
			ArrayList<String> searchInput = new ArrayList<String>();
			searchInput.add("milk");
			searchInput.add("chicken");
			searchInput.add("egg");
			splits = (ArrayList<RecipeItem>) retrive.getRecipies(getApplicationContext(), searchInput);
			
		
			for (RecipeItem string : splits) {
				
					Stlist.add(string.getRecipeName());	
				
				
				
			}
			
			return Stlist;
		}		
}
