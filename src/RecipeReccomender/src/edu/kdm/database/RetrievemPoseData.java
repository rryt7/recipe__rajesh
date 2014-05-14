package edu.kdm.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import edu.kdm.bean.RecipeItem;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

public class RetrievemPoseData {

	private Cursor cursor;
	private SQLiteDatabase sqlDB;
	private String[] columns, selectionArgs;
	private String selection, groupBy, having, orderBy;
	private static final String TAG = "RetrieveJXTAPoseData";
	

	

	public List<RecipeItem> getRecipies(Context context,ArrayList<String> searchStrings) {
		List<RecipeItem> recipeList = null;
		if (context == null)
			return null;
		MPoseDatabaseHandler pdh = new MPoseDatabaseHandler(context);
		sqlDB = pdh.getReadableDatabase();

		columns = null;//new String[]{"mposed_contact"};
		
		selection = "ingredient_search like  '%" + searchStrings.get(0) + "%'";
		for (int i = 1; i < searchStrings.size(); i++) {
			selection = selection + "AND ingredient_search LIKE '%"+searchStrings.get(i)+"%'";
		}
		groupBy = having = null;
		selectionArgs = null;
		orderBy = null;
		cursor = sqlDB.query("recipe_details", columns, selection, selectionArgs, groupBy, having, orderBy);

		if(cursor != null && cursor.moveToFirst()) {
			recipeList = new ArrayList<RecipeItem>();
			do {
				String url = null;
				String name = null;
				String searchString = null;
				String ingredients = null;
				String directions = null;
				String time = null;
				
				RecipeItem recipeItem = new RecipeItem();
				
				
				url = cursor.getString(0);
				name = cursor.getString(1);
				searchString = cursor.getString(2);
				ingredients = cursor.getString(3);
				directions = cursor.getString(4);
				//time = cursor.getString(5);
				
				recipeItem.setUrl(url);
				recipeItem.setRecipeName(name);
				recipeItem.setIngred_search(searchString);
				recipeItem.setIngrediants(ingredients);
				recipeItem.setDirections(directions);
				
				
				recipeList.add(recipeItem);
			} while(cursor.moveToNext());
		} else {
			Log.v("ContactsList-RETRIEVE", "No record found...");
		}

		try {
			if(cursor != null)
				cursor.close();
			sqlDB.close();
		} catch(SQLException sqle) {
			
			Log.e("ContactsList-RETRIEVE", "Error closing sqlDatabase: " + sqle);
		}
		
		return recipeList;
	}

	
	public RecipeItem getRecipies(Context context,String recipeName) {
		RecipeItem recipeitem = null;
		if (context == null)
			return null;
		MPoseDatabaseHandler pdh = new MPoseDatabaseHandler(context);
		sqlDB = pdh.getReadableDatabase();

		columns = null;//new String[]{"mposed_contact"};
		
		selection = "name like  '%" + recipeName + "%'";
		
		groupBy = having = null;
		selectionArgs = null;
		orderBy = null;
		cursor = sqlDB.query("recipe_details", columns, selection, selectionArgs, groupBy, having, orderBy);

		if(cursor != null && cursor.moveToFirst()) {
			
			
				String url = null;
				String name = null;
				String searchString = null;
				String ingredients = null;
				String directions = null;
				String time = null;
				
				RecipeItem recipeItem = new RecipeItem();
				
				
				url = cursor.getString(0);
				name = cursor.getString(1);
				searchString = cursor.getString(2);
				ingredients = cursor.getString(3);
				directions = cursor.getString(4);
				//time = cursor.getString(5);
				
				recipeItem.setUrl(url);
				recipeItem.setRecipeName(name);
				recipeItem.setIngred_search(searchString);
				recipeItem.setIngrediants(ingredients);
				recipeItem.setDirections(directions);
				
				
				
			
		} else {
			Log.v("ContactsList-RETRIEVE", "No record found...");
		}

		try {
			if(cursor != null)
				cursor.close();
			sqlDB.close();
		} catch(SQLException sqle) {
			
			Log.e("ContactsList-RETRIEVE", "Error closing sqlDatabase: " + sqle);
		}
		
		return recipeitem;
	}

	/*public boolean isPhoneNumberRegExpMposed(String phoneNumber, Context context) {
		boolean exists = false;
		if (phoneNumber == null || context == null)
			return exists;
		Logger.info(TAG+" isPhoneRegXMposed"+ "Checking phone number in social_details : " + phoneNumber);
		try{
		MPoseDatabaseHandler pdh = new MPoseDatabaseHandler(context);
		sqlDB = pdh.getReadableDatabase();

		if (phoneNumber.length() >= 10) {
			phoneNumber = phoneNumber.substring(phoneNumber.length()-8);
		}

		columns = null;
		selection = "phone_num like  '%" + phoneNumber + "'";
		groupBy = having = null;
		selectionArgs = null;
		orderBy = null;

		cursor = sqlDB.query("social_details", columns, selection, selectionArgs, groupBy, having, orderBy);

		if(cursor!=null && cursor.moveToFirst()) {
			exists = true;
			Logger.info(TAG+ "social_details RegExp : Number existed in records...");
		} else {
			Logger.info(TAG+ "social_details RegExp : Number is not in JXTA frnds list...");
		}

		if(cursor != null) {
			Logger.info(TAG+ "Closing cursor...");
			cursor.close();
		}
		Logger.info(TAG+ "Closing database connection..");
		try {
			sqlDB.close();
		} catch(SQLException sqle) {
			Logger.logStackTrace(sqle);
			Logger.fatal("CheckPhone-SQLError"+ "Error closing sqlite database: " + sqle);
		}
		
		}catch (Exception e) {
		Logger.logStackTrace(e);
		}

		return exists;
	}

	public ArrayList<String> getUserSocialIds(Context mContext, String selfNumber){

		ArrayList<String> userSocialIds = null;

		if(selfNumber == null){
			return userSocialIds;
		}

		MPoseDatabaseHandler pdh = new MPoseDatabaseHandler(mContext);
		sqlDB = pdh.getReadableDatabase();

		columns = null;		
		selection = "phone_num like  '%" + selfNumber + "'";
		groupBy = having = null;
		selectionArgs = null;
		orderBy = null;

		cursor = sqlDB.query("user_details", columns, selection, selectionArgs, groupBy, having, orderBy);

		if(cursor.moveToFirst()) {
			userSocialIds = new ArrayList<String>();

			userSocialIds.add(cursor.getString(cursor.getColumnIndex("user_facebook_id")));
			userSocialIds.add(cursor.getString(cursor.getColumnIndex("user_twitter_id")));

			Logger.info("user_facebook_id from db is " + userSocialIds.get(0));
			Logger.info("user_tweeter_id from db is " + userSocialIds.get(1));


		}else{
			Logger.warn(TAG+"NO SOCIAL IDS ....");
		}

		if(cursor != null) {
			Log.i(TAG, "Closing cursor...");
			cursor.close();
		}
		Log.i(TAG, "Closing database connection..");
		try {
			sqlDB.close();
		} catch(SQLException sqle) {
			Logger.logStackTrace(sqle);
			Log.e("CheckPhone-SQLError", "Error closing sqlite database: " + sqle);
		}

		return userSocialIds;
	}


	public ArrayList<String> getUserSocialSatuses(Context mContext) {

		ArrayList<String> userStatuses = new ArrayList<String>(); 
		String selfNumber = SpellManager.getSelfNumber();

		if(selfNumber == null){
			return userStatuses;
		}

		MPoseDatabaseHandler pdh = new MPoseDatabaseHandler(mContext);
		sqlDB = pdh.getReadableDatabase();

		columns = null;		
		selection = "phone_num like  '%" + selfNumber + "'";
		groupBy = having = null;
		selectionArgs = null;
		orderBy = null;

		cursor = sqlDB.query("user_details", columns, selection, selectionArgs, groupBy, having, orderBy);

		if(cursor.moveToFirst()) {
			userStatuses.add(cursor.getString(cursor.getColumnIndex("user_facebook_status")));
			userStatuses.add(cursor.getString(cursor.getColumnIndex("user_tweet")));
			Logger.info("user_facebook_status from db is " + userStatuses.get(0));
			Logger.info("user_tweeter_status from db is " + userStatuses.get(1));
		}

		if(cursor != null) {
			Logger.info(TAG+ "Closing cursor...");
			cursor.close();
		}
		Logger.info(TAG+ "Closing database connection..");
		try {
			sqlDB.close();
		} catch(SQLException sqle) {
			Logger.logStackTrace(sqle);
			Log.e("CheckPhone-SQLError", "Error closing sqlite database: " + sqle);
		}

		return userStatuses;
	}


	public Map<String, String> getTwitterFacebookIds(Context context, String phoneNumber, boolean methodCall) {

		Map<String, String> map = new HashMap<String, String>();
		if (phoneNumber == null || context == null) {
			return map;
		}
		String tempString = phoneNumber.replaceAll("[^0-9]","");
		String regExmPoseNumber = null;
		if (tempString.length() >= 10) {
			regExmPoseNumber = tempString.substring(tempString.length()-10);
		} else {
			regExmPoseNumber = tempString;
		}

		MPoseDatabaseHandler pdh = new MPoseDatabaseHandler(context);
		sqlDB = pdh.getReadableDatabase();

		columns = null;		
		selection = "phone_num like  '%" + regExmPoseNumber + "'";
		groupBy = having = null;
		selectionArgs = null;
		orderBy = null;

		cursor = sqlDB.query("social_details", columns, selection, selectionArgs, groupBy, having, orderBy);

		if(cursor !=null && cursor.moveToFirst()) {
			map.put("Facebook", cursor.getString(1));
			map.put("Twitter", cursor.getString(2));
			map.put("FacebookFlag", cursor.getString(3));
			map.put("TwitterFlag", cursor.getString(4));

			if(methodCall){

				if(cursor.getString(3) != null && cursor.getString(3).equalsIgnoreCase("1")){
					facebookIdStatus = true;
				}
				else{
					facebookIdStatus=false;
				}
				if(cursor.getString(4) != null && cursor.getString(4).equalsIgnoreCase("1")){
					twitterIdStatus = true;
				}
				else{
					twitterIdStatus=false;
				}
			}


			Logger.info("MAHESH"+ "Facebook ID " + map.get("Facebook"));
			Logger.info("MAHESH"+ "Twitter ID " + map.get("Twitter"));
			Logger.info("MAHESH"+ "Facebook status " + map.get("FacebookFlag"));
			Logger.info("MAHESH"+ "Twitter status " + map.get("TwitterFlag"));

		} else {

			Logger.info(TAG+ "Number is not in  social_details table...");
			Logger.info("MAHESH"+ "Number is not in  social_details table...");

		}

		if(cursor != null) {
			Logger.info(TAG+ "Closing cursor...");
			cursor.close();
		}
		Logger.info(TAG+ "Closing database connection..");
		try {
			sqlDB.close();
		} catch(SQLException sqle) {
			Logger.logStackTrace(sqle);
			Logger.fatal("CheckPhone-SQLError"+ "Error closing sqlite database: " + sqle);
		}

		return map;

	}


	public SocialStatusBean getSocialStatusDB(Context context, String phoneNum){
		SocialStatusBean userStatuses = null;
		Logger.info("phone number is " + phoneNum);
		if(phoneNum != null){

			String tempString = phoneNum.replaceAll("[^0-9]","");
			String regExmPoseNumber = null;
			if (tempString.length() >= 10) {
				regExmPoseNumber = tempString.substring(tempString.length()-10);
			} else {
				regExmPoseNumber = tempString;
			}

			System.out.println("regex phone number is  " + regExmPoseNumber);

			MPoseDatabaseHandler poseHandler = new MPoseDatabaseHandler(context);
			
			sqlDB = poseHandler.getReadableDatabase();

			columns = null;
			//selection = "frnd_phone_num = '" + phoneNumber + "'";
			System.out.println(" checkPhoneNumber : "+regExmPoseNumber);
			selection = "phone_num like  '%" + regExmPoseNumber + "'";
			groupBy = having = null;
			selectionArgs = null;
			orderBy = null;

			System.out.println("Before Fetching Social Statuses");
			cursor = sqlDB.query("social_details", columns, selection, selectionArgs, groupBy, having, orderBy);

			if(cursor!=null && cursor.moveToFirst()){
				do {
					Logger.info("Facebook Value : "+cursor.getString(cursor.getColumnIndex("facebook_status")));
					Logger.info("Twitter Value : "+cursor.getString(cursor.getColumnIndex("twitter_status")));
					Logger.info("Twitter id : "+cursor.getString(cursor.getColumnIndex("twitter_id")));
					Logger.info("facebook id  : "+cursor.getString(cursor.getColumnIndex("facebook_id")));
										Logger.info(cursor.getString(0) + cursor.getString(1)+ cursor.getString(2)+ cursor.getString(3)
							+cursor.getString(4)+cursor.getString(5)+cursor.getString(6));

					userStatuses = new SocialStatusBean(null, cursor.getString(cursor.getColumnIndex("facebook_status")), cursor.getString(cursor.getColumnIndex("twitter_status")));
					userStatuses.setUserFbId(cursor.getString(cursor.getColumnIndex("facebook_id")));
					userStatuses.setUserTwitterId(cursor.getString(cursor.getColumnIndex("twitter_id")));
				} while(cursor.moveToNext());
			}

			if(cursor != null) {
				Logger.info(TAG+ "Closing cursor...");
				cursor.close();
			}
			Logger.info(TAG+ "Closing database connection..");
			try {
				sqlDB.close();
			} catch(SQLException sqle) {
				Logger.logStackTrace(sqle);
				Logger.fatal("CheckPhone-SQLError"+ "Error closing sqlite database: " + sqle);
			}
		}

		return userStatuses;
	}


	public ArrayList<String> getFbTwitterIds(Context context){
		ArrayList<String> allIds = new ArrayList<String>();
		MPoseDatabaseHandler mposeHandler = new MPoseDatabaseHandler(context);
		sqlDB = mposeHandler.getReadableDatabase();

		columns = null;		
		selection = null;
		groupBy = having = null;
		selectionArgs = null;
		orderBy = null;

		cursor = sqlDB.query("social_details", columns, selection, selectionArgs, groupBy, having, orderBy);

		if(cursor != null && cursor.moveToFirst()){

			for(int i = 0 ; i < cursor.getCount(); i++){
				Logger.info("Value @ 0 : "+cursor.getString(0));
				allIds.add(cursor.getString(0));
				cursor.moveToNext();
			}

		}else{
			Logger.info("Cursor is null");
		}

		if(cursor != null) {
			Logger.info(TAG+ "Closing cursor...");
			cursor.close();
		}
		Logger.info(TAG+ "Closing database connection..");
		try {
			sqlDB.close();
		} catch(SQLException sqle) {
			Logger.logStackTrace(sqle);
			Logger.fatal("CheckPhone-SQLError"+ "Error closing sqlite database: " + sqle);
		}

		return allIds;
	}

	public ContactBean getMPosedContactBean(Context context, String phoneNumber) {

		ContactBean contactBean = null;
		if (phoneNumber == null || context == null)
			return contactBean;

		MPoseDatabaseHandler pdh = new MPoseDatabaseHandler(context);
		sqlDB = pdh.getReadableDatabase();
		String tempString = phoneNumber.replaceAll("[^0-9]","");
		String regExmPoseNumber = null;
		if (tempString.length() >= 10) {
			regExmPoseNumber = tempString.substring(tempString.length()-10);
		} else {
			regExmPoseNumber = tempString;
		}

		Logger.info("Regx of Number : "+regExmPoseNumber);

		columns = null;//new String[]{"mposed_contact"};		
		selection = "mposed_contact like  '%" + regExmPoseNumber + "'";

		groupBy = having = null;
		selectionArgs = null;
		orderBy = null;

		cursor = sqlDB.query("mposed_contacts", columns, selection, selectionArgs, groupBy, having, orderBy);

		if(cursor != null && cursor.moveToFirst()) {
			contactBean = new ContactBean();
			contactBean.setNumber(cursor.getString(0));
			if(cursor.getString(2) != null){
				contactBean.setPhotoUri(cursor.getString(2));
			}
			else{
				contactBean.setPhotoUri("");

			}
			if(cursor.getString(1) != null) {
				contactBean.setRingtone(cursor.getString(1));
				//				contactBean.setRingToneImge(true);
			}
			else {
				contactBean.setRingtone("Tap to select ringtone");
				//				contactBean.setRingToneImge(false);
			}
		} else {
			Logger.info("ContactsList-RETRIEVE"+ "No record found...");
		}

		try {
			if(cursor != null)
				cursor.close();
			sqlDB.close();
		} catch(SQLException sqle) {
			Logger.logStackTrace(sqle);
			Logger.fatal("ContactsList-RETRIEVE"+ "Error closing sqlDatabase: " + sqle);
		}
		return contactBean;
	}


	public AvatarBean getCreatedAvatarBean(Context context, String avatarName) {

		AvatarBean avatarBean = null;
		if (avatarName == null || context == null)
			return avatarBean;

		MPoseDatabaseHandler pdh = new MPoseDatabaseHandler(context);
		sqlDB = pdh.getWritableDatabase();
		//		String tempString = phoneNumber.replaceAll("[^0-9]","");
		//		String regExmPoseNumber = null;
		//		if (tempString.length() >= 10) {
		//			regExmPoseNumber = tempString.substring(tempString.length()-10);
		//		} else {
		//			regExmPoseNumber = tempString;
		//		}

		//		System.out.println("Regx of Number : "+regExmPoseNumber);

		columns = null;//new String[]{"mposed_contact"};		
		selection = "avatar_name =  '" + avatarName + "'";

		groupBy = having = null;
		selectionArgs = null;
		orderBy = null;

		cursor = sqlDB.query("user_avatar", columns, selection, selectionArgs, groupBy, having, orderBy);

		if(cursor != null && cursor.moveToFirst()) {
			avatarBean = new AvatarBean();
			avatarBean.setAvatarName(cursor.getString(0));
			if(cursor.getString(1) != null){
				avatarBean.setAvatarImage(cursor.getString(1));
			}
			else{
				avatarBean.setAvatarImage("");

			}
			if(cursor.getString(2) != null) {
				avatarBean.setAvatarSound(cursor.getString(2));
				//				contactBean.setRingToneImge(true);
			}
			else {
				avatarBean.setAvatarSound("");
				//				contactBean.setRingToneImge(false);
			}
			if(cursor.getString(3) != null) {
				avatarBean.setAvatarFbFlag(cursor.getString(3));
				//				contactBean.setRingToneImge(true);
			}
			else {
				avatarBean.setAvatarFbFlag("0");
				//				contactBean.setRingToneImge(false);
			}
			if(cursor.getString(4) != null) {
				avatarBean.setAvatarTweetFlag(cursor.getString(4));
				//				contactBean.setRingToneImge(true);
			}
			else {
				avatarBean.setAvatarTweetFlag("0");
				//				contactBean.setRingToneImge(false);
			}

		} else {
			Logger.info("ContactsList-RETRIEVE"+ "No record found...");
		}

		try {
			if(cursor != null)
				cursor.close();
			sqlDB.close();
		} catch(SQLException sqle) {
			Logger.logStackTrace(sqle);
			Logger.fatal("ContactsList-RETRIEVE"+ "Error closing sqlDatabase: " + sqle);
		}
		return avatarBean;
	}


	public ArrayList<AvatarBean>  getAvatarDetails(Activity mContext){
		ArrayList<AvatarBean> avatarList = new ArrayList<AvatarBean>();

		MPoseDatabaseHandler pdh = new MPoseDatabaseHandler(mContext);
		sqlDB = pdh.getReadableDatabase();

		columns = null;
		selection = null;
		groupBy = having = null;
		selectionArgs = null;
		orderBy = null;

		cursor = sqlDB.query("user_avatar", columns, selection, selectionArgs, groupBy, having, orderBy);


		if(cursor != null && cursor.moveToFirst()){
			System.out.println("Cursor is not null "+cursor.getCount());
			do {

				String avatarName = cursor.getString(0);
				String avatarImageUri = cursor.getString(1);
				String avatarSoundUri = cursor.getString(2);
				String avatarFBValue = cursor.getString(3);
				String avatarTweetValue = cursor.getString(4);
				String avatarId = cursor.getString(cursor.getColumnIndex("avatar_server_id"));

				Logger.info("Avatar Image URI : "+avatarImageUri);
				Logger.info("Avatar Sound URI : "+avatarSoundUri);
				Logger.info("Facebook : "+avatarFBValue+" : tweet : "+avatarTweetValue);


				AvatarBean avatarValues = new AvatarBean(avatarName, avatarImageUri, avatarSoundUri, avatarFBValue, avatarTweetValue);
				avatarValues.setAvatar_server_id(avatarId);
				avatarList.add(avatarValues);
			}while (cursor.moveToNext());

			Uri imageUri = Uri.parse(avatarImageUri);
			ImageBean imageBean = getImageBean(mContext, imageUri);
			SpellManager.createLooksSpell(imageBean);

			Uri soundUri = Uri.parse(avatarSoundUri);
			SoundBean mSoundBean = getSoundBean(mContext, soundUri);
			SpellManager.createSoundSpell(mSoundBean);

		}else{

			Logger.info("Cursor is null or dosent have values");

		}

		if(cursor != null) {
			Logger.info(TAG+ "Closing cursor...");
			cursor.close();
		}
		Logger.info(TAG+ "Closing database connection..");
		try {
			sqlDB.close();
		} catch(SQLException sqle) {
			Logger.logStackTrace(sqle);
			Log.e("CheckPhone-SQLError", "Error closing sqlite database: " + sqle);
		}

		return avatarList;
	}




	public HashMap<String, ArrayList<String>> getAvatarContactDetails(Activity context){
		HashMap<String, ArrayList<String>> avatarContacts = new HashMap<String, ArrayList<String>>();
		ArrayList<AvatarBean> avatarList = getAvatarDetails(context);		
		for(int i=0;i<avatarList.size();i++){
			if(avatarList.get(i).getAvatar_server_id() !=null && avatarList.get(i).getAvatar_server_id() !="" ){
				avatarContacts.put(avatarList.get(i).getAvatarName(), getAvatarContacts(context, 
						avatarList.get(i).getAvatarName()));
			}
		}  

		return avatarContacts;
	}


	private ArrayList<String> getAvatarContacts(Activity mContext,String avatarName){

		ArrayList<String> avatarContactList = new ArrayList<String>();

		MPoseDatabaseHandler pdh = new MPoseDatabaseHandler(mContext);
		sqlDB = pdh.getReadableDatabase();

		columns = null;
		selection = "avatar_name='" + avatarName+"'";
		groupBy = having = null;
		selectionArgs = null;
		orderBy = null;
		//android.database.sqlite.SQLiteException: no such column: avatar_name: , while compiling: SELECT * FROM mposed_contacts WHERE avatar_name='1'


		cursor = sqlDB.query("mposed_contacts", columns, selection, selectionArgs, groupBy, having, orderBy);

		if(cursor != null && cursor.moveToFirst()){
			System.out.println("Cursor is not null "+cursor.getCount());

			do{
				String phoneNumber = cursor.getString(0);
				if(phoneNumber!=null && phoneNumber!=""){
					avatarContactList.add(phoneNumber);
				}
			}while(cursor.moveToNext());
		}

		try {
			cursor.close();
			sqlDB.close();
		} catch(SQLException sqle) {
			Logger.logStackTrace(sqle);
			Logger.fatal("CheckPhone-SQLError"+ "Error closing sqlite database: " + sqle);
		}
		return avatarContactList;
	}



	*//**
	 * Fetches the image data like name, path, id, etc.. from the Uri. 
	 * Populates the ImageBean with this values.
	 *  
	 * @param uri
	 * @return ImageBean 
	 *//*
	public ImageBean getImageBean(Activity mContext, Uri uri) {
		ImageBean bean = null;


		Logger.info("Image URI : " + uri.toString());

		String[] projection = { MediaStore.Images.Media.DATA,
				MediaStore.Images.Media._ID,
				MediaStore.Audio.Media.DISPLAY_NAME };

		Cursor cursor = mContext.managedQuery(uri, projection, null, null, null);

		if (cursor != null && cursor.moveToFirst()) {
			bean = new ImageBean(uri, cursor.getString(0), cursor.getString(2), cursor.getString(1));
		}

		return bean;
	}

	public SoundBean getSoundBean(Activity mActivity, Uri soundUri){
		SoundBean soundBean = null;
		if(SpellManager.DEBUG){
			System.out.println("Sound Uri : "+soundUri);
		}

		String[] projection = {
				MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.Media.DATA,
				MediaStore.Audio.Media.TITLE	
		};

		Cursor mCursor = mActivity.managedQuery(soundUri, projection, null, null, null);

		if(mCursor != null && mCursor.moveToFirst()){
			soundBean = new SoundBean(cursor.getString(1), soundUri.toString(), cursor.getString(2));
		}else{
			if(SpellManager.DEBUG)
				System.out.println("Media not found -------------------------------------------- >>>>> -------------------------");
		}

		mCursor.close();

		return soundBean;
	}

	public boolean isPhoneNumberMposed(String phoneNumber, Context context) {
		boolean exists = false;
		if (phoneNumber == null || context == null)
			return exists;


		Logger.info(TAG+"isPhoneMpose"+ "Checking phone number mposed_contacts : " + phoneNumber);

		MPoseDatabaseHandler pdh = new MPoseDatabaseHandler(context);
		if(pdh != null){
			try{
			sqlDB = pdh.getReadableDatabase();
			String tempString = phoneNumber.replaceAll("[^0-9]","");
			String regExmPoseNumber = null;
			if (tempString.length() >= 10) {
				regExmPoseNumber = tempString.substring(tempString.length()-10);
			} else {
				regExmPoseNumber = tempString;
			}

			columns = null;
			selection = "mposed_contact like  '%" + regExmPoseNumber + "'";
			//selection = "mposed_contact = '" + phoneNumber + "'";
			groupBy = having = null;
			selectionArgs = null;
			orderBy = null;

			cursor = sqlDB.query("mposed_contacts", columns, selection, selectionArgs, groupBy, having, orderBy);

			if(cursor.moveToFirst()) {
				exists = true;
				Log.i(TAG, "mposed_contacts : Number existed in records...");
			} else {
				Log.i(TAG, "mposed_contacts : Number is not in JXTA frnds list...");
			}

			if(cursor != null) {
				Log.i(TAG, "Closing cursor...");
				cursor.close();
			}
			Log.i(TAG, "Closing database connection..");
			try {
				sqlDB.close();
			} catch(SQLException sqle) {
				Logger.logStackTrace(sqle);
				Log.e("CheckPhone-SQLError", "Error closing sqlite database: " + sqle);
			}
			}catch (Exception e) {
				Logger.logStackTrace(e);
			}
		}else{
			Logger.fatal(" THere is a null pointer in creating db");
		}

		return exists;
	}


	public boolean checkRegExpPhoneNumber(String phoneNumber, Context context) {

		boolean exists = false;
		if (phoneNumber == null || context == null)
			return exists;
		Logger.info(TAG+" chkRegExPhoneNum"+ "Checking phone number: " + phoneNumber);

		MPoseDatabaseHandler pdh = new MPoseDatabaseHandler(context);
		sqlDB = pdh.getReadableDatabase();

		columns = null;
		selection = "frnd_phone_num like  '%" + phoneNumber + "'";
		groupBy = having = null;
		selectionArgs = null;
		orderBy = null;

		cursor = sqlDB.query("friends_details", columns, selection, selectionArgs, groupBy, having, orderBy);

		if(cursor.moveToFirst()) {
			exists = true;
			Log.i(TAG, "Number existed in records...");
		} else {
			Log.i(TAG, "Number is not in JXTA frnds list...");
		}

		if(cursor != null) {
			Log.i(TAG, "Closing cursor...");
			cursor.close();
		}
		Log.i(TAG, "Closing database connection..");
		try {
			sqlDB.close();
		} catch(SQLException sqle) {
			Logger.logStackTrace(sqle);
			Log.e("CheckPhone-SQLError", "Error closing sqlite database: " + sqle);
		}

		return exists;
	}

	public int getWhiteListFilterFlag(Context context, String phone_num){
		int iswhiteListContacts = -1;

		if(context == null){
			return -1;
		}

		MPoseDatabaseHandler listDataHandler = new MPoseDatabaseHandler(context);
		sqlDB = listDataHandler.getReadableDatabase();

		String[] select_columns = {"filter_flag"};
		selection = "phone_number like  '%" + phone_num + "'";
		groupBy = having = null;
		selectionArgs = null;
		orderBy = null;
		cursor = sqlDB.query("permissions_list", select_columns, selection, selectionArgs, groupBy, having, orderBy);

		if(cursor != null && cursor.moveToFirst()){
			iswhiteListContacts = cursor.getShort(cursor.getColumnIndex("filter_flag"));
		}
		if(SpellManager.DEBUG)
			System.out.println("is white list contact : "+iswhiteListContacts);

		try {
			if(cursor != null)
				cursor.close();
			sqlDB.close();
		} catch(SQLException sqle) {
			Logger.logStackTrace(sqle);
			Log.e("WhiteList-RETRIEVE", "Error closing sqlDatabase: " + sqle);
		}
		return iswhiteListContacts;
	}


	public AvatarBean getAvatarBean(Context myOwnCOntext,String avatarName){

		if(myOwnCOntext == null){
			Log.e(TAG, "context is null");
		}
		else{
			Log.e(TAG, "context is not null");
		}
		MPoseDatabaseHandler pdh = new MPoseDatabaseHandler(myOwnCOntext);
		sqlDB = pdh.getWritableDatabase();

		columns = null;
		selection = "avatar_name = '"+avatarName+"'";
		groupBy = having = null;
		selectionArgs = null;
		orderBy = null;

		cursor = sqlDB.query("user_avatar", columns, selection, selectionArgs, groupBy, having, orderBy);
		AvatarBean avatarValues = null;

		if(cursor != null && cursor.moveToFirst()){
			System.out.println("Cursor is not null "+cursor.getCount());

			String avatarName1 = cursor.getString(0);
			String avatarImageUri = cursor.getString(1);
			String avatarSoundUri = cursor.getString(2);
			String avatarFBValue = cursor.getString(3);
			String avatarTweetValue = cursor.getString(4);
			String avatarsenderid = cursor.getString(5);
			System.out.println("setting avatar id " + avatarsenderid);
			System.out.println("avatar name " + avatarName1);

			if(SpellManager.DEBUG){
				System.out.println("Avatar Image URI : "+avatarImageUri);
				System.out.println("Avatar Sound URI : "+avatarSoundUri);
				System.out.println("Facebook : "+avatarFBValue+" : tweet : "+avatarTweetValue);
			}

			avatarValues = new AvatarBean(avatarName1, avatarImageUri, avatarSoundUri, avatarFBValue, avatarTweetValue);
			avatarValues.setAvatar_server_id(avatarsenderid);
		}

		try {
			if(cursor != null)
				cursor.close();
			sqlDB.close();
		} catch(SQLException sqle) {
			Logger.logStackTrace(sqle);
			sqle.printStackTrace();
		}

		return avatarValues;
	}
	*//**
	 * when contact already added returns true.
	 * 
	 * @param phoneNumber
	 * @param activity
	 * @return <code>true/flase</code> 
	 *//*
	public String[] isContactAlreadyAdded(String phoneNumber,Activity activity){
		boolean contactPresent = false;
		String avatarName = "";
		String[] results = new String[2];
		MPoseDatabaseHandler pdh = new MPoseDatabaseHandler(activity);
		sqlDB = pdh.getReadableDatabase();

		columns = null;
		selection = " mposed_contact= '"+phoneNumber+"'";
		groupBy = having = null;
		selectionArgs = null;
		orderBy = null;

		cursor = sqlDB.query("mposed_contacts", columns, selection, selectionArgs, groupBy, having, orderBy);

		if(cursor!=null && cursor.moveToFirst()){
			if(cursor.getCount()>0){
				contactPresent = true;
				avatarName = cursor.getString(cursor.getColumnIndex("avatar_name"));
			}
		}

		cursor.close();

		sqlDB.close();

		if(contactPresent){
			results[0] = "1";
			results[1] = avatarName;
		}else{
			results[0] = "0";
			results[1] = avatarName;
		}

		return results;
	}

	public RegisteredUserBean[] getRegisterdUsers(Context context){
		Cursor cursor=null;
		RegisteredUserBean[] mRegisterdUsers = null; 
		MPoseDatabaseHandler pdh = new MPoseDatabaseHandler(context);
		sqlDB = pdh.getReadableDatabase();

		columns = null;
		selection = null;
		groupBy = having = null;
		selectionArgs = null;
		orderBy = null;
		try{
			cursor = sqlDB.query("registered_users", columns, selection, selectionArgs, groupBy, having, orderBy);

			mRegisterdUsers = new RegisteredUserBean[cursor.getCount()];
			cursor.moveToFirst();
			for(int i=0; i<cursor.getCount();i++){
				String number = cursor.getString(0);
				String name =  cursor.getString(1);
				mRegisterdUsers[i] = new RegisteredUserBean(name, number);
				cursor.moveToNext();
			}
		}
		catch (Exception e) {
			Logger.logStackTrace(e);
			e.printStackTrace();
		}
		finally{
			cursor.close();
			sqlDB.close();
		}
		return mRegisterdUsers;
	}

	public boolean isPhoneNumberAvailableToMPose(Context context,String number){
		boolean numberFound = false;

		MPoseDatabaseHandler pdh = new MPoseDatabaseHandler(context);
		sqlDB = pdh.getWritableDatabase();

		columns = null;//new String[]{"mposed_contact"};		
		selection = "mposed_contact =  '" + number + "'";

		groupBy = having = null;
		selectionArgs = null;
		orderBy = null;

		cursor = sqlDB.query("mposed_contacts", columns, selection, selectionArgs, groupBy, having, orderBy);
		if(cursor!=null && cursor.moveToFirst()){
			if(cursor.getCount()>0){
				numberFound = true;
			}
		}

		cursor.close();

		sqlDB.close();

		return numberFound;
	}*/


}