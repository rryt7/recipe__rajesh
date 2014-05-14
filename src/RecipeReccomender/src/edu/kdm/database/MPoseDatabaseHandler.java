package edu.kdm.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Database helper class used to create all the tables used by the application.
 * @author varun
 *
 */

public class MPoseDatabaseHandler extends SQLiteOpenHelper {

	public static final boolean DEBUG = true;
	
	
	private static final String DATABASE_NAME = "recipe";
	private static final int DATABASE_VERSION = 1;
	
	private static final String RECIPE_TABLE = "recipe_details", 
								RECIPE_SEARCH_TABLE = "search_ingred"/*, 
								SOCIAL_DETAILS_TABLE = "social_details", 
								PERMISSIONS_LIST_TABLE = "permissions_list",
								USER_AVATAR = "user_avatar",
								REGISTERED_USERS ="registered_users"*/
								;
	
	private static final String CREATE_RECIPE_TABLE = "CREATE TABLE IF NOT EXISTS " + RECIPE_TABLE + " (" +
								"url TEXT PRIMARY KEY," +
								"name TEXT NULL," +
								"ingredient_search TEXT NULL," +
								"ingredients TEXT NULL, " +
								"directions TEXT NULL, " +
								"time TEXT NULL);";
								
	/*private static final String CREATE_MPOSED_CONTACT_TABLE = "CREATE TABLE IF NOT EXISTS " + RECIPE_SEARCH_TABLE + " (" +
								"mposed_contact TEXT PRIMARY KEY, " +
								"avatar_name TEXT NULL);" ;
	
	private static final String CREATE_SOCIAL_DETAILS_TABLE = "CREATE TABLE IF NOT EXISTS " + SOCIAL_DETAILS_TABLE + " (" +
								"phone_num TEXT PRIMARY KEY, " +
								"avatar_id TEXT NULL,"+
								"facebook_id TEXT NULL, " +
								"twitter_id TEXT NULL, " +
								"facebook_status TEXT NULL, " +
								"twitter_status TEXT NULL);";
	
	private static final String CREATE_WHITE_BLACK_LIST_TABLE = "CREATE TABLE IF NOT EXISTS "+PERMISSIONS_LIST_TABLE+ " ("+
								"phone_number TEXT NULL, " +
								"filter_flag TEXT NULL)";
	
	private static final String CREATE_USER_AVATAR_TABLE = "CREATE TABLE IF NOT EXISTS "+USER_AVATAR+" (" +
								"avatar_name TEXT PRIMARY KEY," +
								"looks_file_name TEXT NULL, " +
								"sound_file_name TEXT NULL, " +
								"facebook_flag TEXT NULL, " +
								"twitter_flag TEXT NULL," +
								"avatar_server_id TEXT NULL); ";
	
	private static final String CREATE_REGISTERED_USERS_TABLE = "CREATE TABLE IF NOT EXISTS " + REGISTERED_USERS + " (" +
			"phone_number TEXT PRIMARY KEY, " +
			"contact_name TEXT NULL, " +
			"status BOOL NULL);" ;
	
	private static final String CREATE_USER_AVATAR_DELETE_TRIGGER = "CREATE TRIGGER DELETE_AVATAR AFTER DELETE ON "+USER_AVATAR+" BEGIN" +
			" DELETE FROM "+ RECIPE_SEARCH_TABLE + " WHERE avatar_name = old.avatar_name" +
					"; END;";
	*/
	
	private static final String TAG = "RecipeReccomenderDatabaseHelper";

	public MPoseDatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_RECIPE_TABLE);
		/*db.execSQL(CREATE_MPOSED_CONTACT_TABLE);
		db.execSQL(CREATE_SOCIAL_DETAILS_TABLE);
		db.execSQL(CREATE_WHITE_BLACK_LIST_TABLE);
		db.execSQL(CREATE_USER_AVATAR_TABLE);
		db.execSQL(CREATE_REGISTERED_USERS_TABLE);
		db.execSQL(CREATE_USER_AVATAR_DELETE_TRIGGER);*/
		if(DEBUG){
			Log.i(TAG, "Database created...");
		}
		
	}

	
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
}