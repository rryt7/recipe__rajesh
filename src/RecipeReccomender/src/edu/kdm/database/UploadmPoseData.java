package edu.kdm.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import edu.kdm.bean.RecipeItem;

/**
 * This class is used to upload data to the database tables.
 * @author varun
 *
 */

public class UploadmPoseData {

	private SQLiteDatabase sqlDB;
	private static final String TAG = "UploadRecipeData";
	
	
	
	
	public boolean uploadRecipeDetails(RecipeItem recipe, Context context) {
		boolean statusUpdatedInDatabase = false;
		MPoseDatabaseHandler dbHandler = new MPoseDatabaseHandler(context);
		sqlDB = dbHandler.getWritableDatabase();
		
		String query = "insert into recipe_details values(?,?,?,?,?,?)";
		SQLiteStatement stmt = sqlDB.compileStatement(query);
		stmt.bindString(1, recipe.getUrl());
		stmt.bindString(2, recipe.getRecipeName());
		stmt.bindString(3, recipe.getIngred_search());
		stmt.bindString(4, recipe.getIngrediantString());
		stmt.bindString(5, recipe.getDirectionString());
		stmt.bindString(6, recipe.getTime());
		long rowId = -1;

		try {
			rowId = stmt.executeInsert();
		} catch(SQLException sqle) {
			
			sqle.printStackTrace();
		}
		finally{

			if(rowId > 0) {
				statusUpdatedInDatabase = true;
			} else {
				statusUpdatedInDatabase = false;
			}
			try {
				sqlDB.close();
			} catch(SQLException sqle) {
				
				sqle.printStackTrace();
			}
		}

		
		return statusUpdatedInDatabase;
	}

	/*public boolean uploadUserDetails(String phoneNumber, Context context) {
		boolean statusUpdatedInDatabase = false;
		if(phoneNumber == null || phoneNumber.equals(""))
		{
			Logger.fatal("PhoneNumber is null or empty to enter into user_details database table ");
			return statusUpdatedInDatabase;
		}
		MPoseDatabaseHandler dbHandler = new MPoseDatabaseHandler(context);
		sqlDB = dbHandler.getWritableDatabase();

		String query = "insert into user_details values(?,?,?,?,?)";  

		Logger.info("Inserting User Status..."+phoneNumber);

		SQLiteStatement stmt = sqlDB.compileStatement(query);
		stmt.bindString(1, phoneNumber);

		long rowId = -1;

		try {
			rowId = stmt.executeInsert();
		} catch(SQLException sqle) {
			Logger.fatal("Error in inserting phonenumber into database ");
			Logger.logStackTrace(sqle);
			sqle.printStackTrace();
		}
		finally{

			if(rowId > 0) {
				statusUpdatedInDatabase = true;
			} else {
				statusUpdatedInDatabase = false;
			}
			try {
				sqlDB.close();
			} catch(SQLException sqle) {
				Logger.logStackTrace(sqle);
				sqle.printStackTrace();
			}
		}

		Logger.info("User Status Row Id: " + rowId);
		return statusUpdatedInDatabase;
	}

	public boolean uploadAvatarData(Context mContext, String avatarName, String imageFile, String soundFile, boolean fbFlag, boolean tweetFlag){
		boolean isUplaod = false;

		if(avatarName == null || avatarName.equals(""))
		{
			Logger.fatal("Cannot upload Avatar into Database because avatarName is null or empty");
			return isUplaod;
		}
		MPoseDatabaseHandler databaseHandler = new MPoseDatabaseHandler(mContext);
		sqlDB = databaseHandler.getWritableDatabase();

		String query = "insert into user_avatar values (?,?,?,?,?,?)";

		try{
			SQLiteStatement statement = sqlDB.compileStatement(query);
			if(avatarName!=null && avatarName!=""){
				statement.bindString(1, avatarName);
			}
			if(imageFile!=null && imageFile!=""){
				statement.bindString(2, imageFile);
			}
			if(soundFile!=null && soundFile!=""){
				statement.bindString(3, soundFile);
			}
			if(fbFlag){
				statement.bindString(4, "1");
			}else{
				statement.bindString(4, "0");
			}

			if(tweetFlag){
				statement.bindString(5, "1");
			}else{
				statement.bindString(5, "0");
			}

			long rowID = -1;

			rowID = statement.executeInsert();

			if(rowID == -1) {
				isUplaod = false;

			} else {
				isUplaod = true;

			}
		}catch (SQLException e) {
			isUplaod = false;
			Logger.logStackTrace(e);
			e.printStackTrace();
		}finally {
			try {
				if(sqlDB != null) {
					sqlDB.close();
				}
			} catch(SQLException sqle) {
				Logger.logStackTrace(sqle);
				sqle.printStackTrace();
			}
		}

		return isUplaod;
	}


	public boolean uploadContactForAvatar(Context mContext, String contactNumber,String avatarName){
		boolean isUplaod = false;
		if(contactNumber == null || contactNumber.equals(""))
		{
			Logger.fatal("Cannot upload Contact for Avatar into Database because contactNumber is null or empty");
			return isUplaod;
		}
		MPoseDatabaseHandler databaseHandler = new MPoseDatabaseHandler(mContext);
		sqlDB = databaseHandler.getWritableDatabase();
		String query = "insert into mposed_contacts values (?,?)";
		try{

			SQLiteStatement statement = sqlDB.compileStatement(query);
			if(contactNumber!=null && contactNumber!=""){
				statement.bindString(1, contactNumber);
			}
			if(avatarName!=null && avatarName!=""){
				statement.bindString(2, avatarName);
			}

			long rowID = -1;
			rowID = statement.executeInsert();

			if(rowID == -1) {
				isUplaod = false;

			} else {
				isUplaod = true;

			}
		}catch (SQLException e) {
			isUplaod = false;
			e.printStackTrace();
			Logger.logStackTrace(e);
		}finally {
			try {
				if(sqlDB != null) {
					sqlDB.close();
				}
			} catch(SQLException sqle) {
				sqle.printStackTrace();
				Logger.logStackTrace(sqle);
			}
		}

		return isUplaod;
	}*/


	//
	//	/**
	//	 * Uploads and mposed contact into the database along with sound file and looks file details 
	//	 * @param number MPosed number
	//	 * @param soundFile mposed ringtone
	//	 * @param looksFile mposed pic
	//	 * @param context associated context
	//	 * @return true if upload successful, false otherwise.
	//	 */
	//	public boolean uploadMposedContacts( Context context, String number, String avatarName) {
	//		boolean isUploaded = false;
	//		MPoseDatabaseHandler dbHandler = new MPoseDatabaseHandler(context);
	//		sqlDB = dbHandler.getWritableDatabase();
	//
	//		Log.v("MPosedContact-UPLOAD", "Uploading following info -> Contact: " + number + " avatar Name : "+avatarName);
	//
	//		String query = null;
	//		query = "insert into mposed_contacts values(?,?)";
	//
	//		try {
	//			SQLiteStatement stmt = sqlDB.compileStatement(query);
	//			if(number != null) {
	//				stmt.bindString(1, number);
	//			} else {
	//				stmt.bindString(1, "");
	//			}
	//			if(avatarName != ""){
	//				stmt.bindString(2, ""+avatarName);
	//			}else{
	//				stmt.bindString(2, ""+"dummy");
	//			}
	//
	//			long rowID = -1;
	//			rowID = stmt.executeInsert();
	//
	//			if(rowID == -1) {
	//				isUploaded = false;
	//
	//				if(SpellManager.DEBUG)
	//					Log.e("MPosedContacts-UPLOAD", "Error uploading mposed contact: " + number);
	//			} else {
	//				isUploaded = true;
	//
	//				if(SpellManager.DEBUG)
	//					Log.v("MPosedContacts-UPLOAD", "Storage of MPosed contact successful: " + number);
	//			}
	//		} catch(SQLException sqle) {
	//			isUploaded = false;
	//			if(SpellManager.DEBUG)
	//				Log.e("MPosedContacts-UPLOAD", "Error due to: " + sqle);
	//		} finally {
	//			try {
	//				if(sqlDB != null) {
	//					sqlDB.close();
	//				}
	//			} catch(SQLException sqle) {
	//				if(SpellManager.DEBUG)
	//					Log.e("MPosedContact-UPLOAdD", "Error closing sqlite database due to: " + sqle);
	//			}
	//		}
	//
	//		return isUploaded;
	//	}

	/*public boolean uploadSocialInfoDetails(Context context, String phoneNumber, String avatarId) {

		boolean statusUpdatedInDatabase = false;
		if(phoneNumber == null || phoneNumber.equals(""))
		{
			Logger.fatal("PhoneNumber is null or empty to enter into user_details database table ");
			return statusUpdatedInDatabase;
		}

		Logger.info("New Entry to SOCIAL DETAIS TABLE : "+phoneNumber);

		MPoseDatabaseHandler dbHandler = new MPoseDatabaseHandler(context);
		sqlDB = dbHandler.getWritableDatabase();

		String query = "insert into social_details values(?,?,?,?,?,?)";

		SQLiteStatement stmt = sqlDB.compileStatement(query);
		stmt.bindString(1, phoneNumber);

		if(!(avatarId != "")){
			stmt.bindString(2, avatarId+"");
		}else{
			//			stmt.bindString(2, 0+"");
		}

		Logger.info(TAG+ "Inserting User Social Info Status...");

		long rowId = -1;

		try {
			rowId = stmt.executeInsert();
		} catch(SQLException sqle) {

			Logger.fatal("UserDetails-UPLOAD "+"Error performing insert due to: " + sqle);
			Logger.logStackTrace(sqle);
		}
		finally{
			if(rowId > 0) {
				statusUpdatedInDatabase = true;
			} else {
				statusUpdatedInDatabase = false;
			}
			try {
				sqlDB.close();
			} catch(SQLException sqle) {
				Log.e("UploadStatus-SQLError", "Error closing sqlite database: " + sqle);
				Logger.logStackTrace(sqle);
			}
		}

		Logger.info(TAG+ "User Status Row Id: " + rowId);


		return statusUpdatedInDatabase;
	}


	public boolean uplaodSocialInfoSync( Context context, String phoneNumber,String avatarId ,String fbID, String tId, String fbStatus, String tweet){

		boolean updateStatus = false;
		if(phoneNumber == null || phoneNumber.equals(""))
		{
			Logger.fatal("PhoneNumber is null or empty to enter into user_details database table ");

			return updateStatus;
		}
		MPoseDatabaseHandler databaseHandler = new MPoseDatabaseHandler(context);
		sqlDB = databaseHandler.getWritableDatabase();

		String query = "insert into social_details values(?,?,?,?,?,?)";
		SQLiteStatement stmt = sqlDB.compileStatement(query);

		System.out.println("----------> Phone num : "+phoneNumber);

		stmt.bindString(1, phoneNumber);

		if(avatarId != null){
			stmt.bindString(2, avatarId);
		}


		if(fbID != null){
			stmt.bindString(3, fbID);
		}

		if(tId != null){
			stmt.bindString(4, tId);
		}

		if(fbStatus != null){
			stmt.bindString(5, fbStatus);
		}

		if(tweet != null){
			stmt.bindString(6, tweet);
		}

		long rowId = -1;

		try {
			rowId = stmt.executeInsert();
		} catch(SQLException sqle) {
			Logger.fatal("UserDetails-UPLOAD"+ "Error performing insert due to: " + sqle);
			Logger.logStackTrace(sqle);

		}
		finally{
			if(rowId > 0) {
				updateStatus = true;
			} else {
				updateStatus = false;
			}
			try {
				sqlDB.close(); 
			} catch(SQLException sqle) {
				Logger.fatal("UploadStatus-SQLError"+ "Error closing sqlite database: " + sqle);
				Logger.logStackTrace(sqle);
			}
		}

		Logger.info(TAG+ "User Status Row Id: " + rowId);


		return updateStatus;
	}

	public boolean uploadFilterListContacts(Context context, String number, boolean isWhiteListNumber){
		boolean isUploaded = false;
		MPoseDatabaseHandler dbHandler = new MPoseDatabaseHandler(context);
		sqlDB = dbHandler.getWritableDatabase();

		String query = null;
		query = "insert into permissions_list values(?,?)";

		try{
			SQLiteStatement stmt = sqlDB.compileStatement(query);
			System.out.println("Number is db helper is " + number);
			stmt.bindString(1, number);
			if(isWhiteListNumber){
				stmt.bindString(2, "1");
			}else{
				stmt.bindString(2, "0");
			}

			long rowID = -1;
			rowID = stmt.executeInsert();

			if(rowID == -1) {
				Log.e("FILTER CONTACT UPLOAD", "Error uploading filter contact: " + number);
				isUploaded = false;
			} else {
				isUploaded = true;
				Log.v("FILTER CONTACT UPLOAD", "Storage of filter contact successful: " + number);
			}

		}catch(SQLException e){
			isUploaded = false;
			Log.e("FILTER CONTACT UPLOAD", "Error due to: " + e);
			Logger.logStackTrace(e);
		}finally {
			try {
				if(sqlDB != null) {
					sqlDB.close();
				}
			} catch(SQLException sqle) {
				Log.e("FILTER CONTACT UPLOAD", "Error closing sqlite database due to: " + sqle);
				Logger.logStackTrace(sqle);
			}
		}

		return isUploaded;
	}


	public boolean uploadRegisteredUsers(Context mContext, String contactNumber, String contactName) {
		boolean isUpload = false;

		if (contactNumber == null || contactNumber.equals("")) {
			Logger.fatal("Cannot upload Contact for Avatar into Database because contactNumber is null or empty");
			return isUpload;
		}

		MPoseDatabaseHandler databaseHandler = new MPoseDatabaseHandler(mContext);
		sqlDB = databaseHandler.getWritableDatabase();
		String query = "insert into registered_users values (?,?,?)";					//??????????????? what is the third bind??
		try {

			SQLiteStatement statement = sqlDB.compileStatement(query);
			if(contactNumber!=null && contactNumber!=""){
				statement.bindString(1, contactNumber);
			}
			if(contactName!=null && contactName!=""){
				statement.bindString(2, contactName);
			}

			long rowID = -1;
			rowID = statement.executeInsert();

			if(rowID == -1) {
				isUpload = false;

			} else {
				isUpload = true;

			}
		} catch (SQLException e) {
			isUpload = false;
			e.printStackTrace();
			Logger.logStackTrace(e);
		} finally {
			try {
				if(sqlDB != null) {
					sqlDB.close();
				}
			} catch(SQLException sqle) {
				sqle.printStackTrace();
				Logger.logStackTrace(sqle);
			}
		}

		return isUpload;
	}

	*//**
	 * Uploads a <code>Map</code> of contact details
	 * where the map contains contact number mapped
	 * to contact name, for the given context. Performs
	 * a bulk upload and returns true if whole process 
	 * is successful, else returns false.
	 * 
	 * @param contactDetails <code>Map</code> of contact
	 * details containing contact numbers mapped to contact
	 * names.
	 * 
	 * @param context Calling context.
	 * 
	 * @return true if the batch upload is successful as a
	 * whole, else false.
	 *//*
	public boolean uploadRegisteredUsers(Map<String, String> contactDetails, Context context) {
		boolean isUploaded = false;

		if (context == null) {
			Logger.fatal("Calling context is null...");
			return false;
		}

		MPoseDatabaseHandler databaseHandler = new MPoseDatabaseHandler(context);
		sqlDB = databaseHandler.getWritableDatabase();

		if (sqlDB == null) {					//Database connection failed...
			Logger.fatal("Unable to get database connection...");
			return false;
		}

		if (contactDetails == null) {			//Contact details null...
			Logger.fatal("Cannot upload contact details for Avatar to Database because contact details is null...");
			return false;
		}

		if (contactDetails.isEmpty()) {			//Contact details empty...
			Logger.fatal("Cannot upload contact detals for Avatar to database because contact details is empty...");
			return false;
		}

		Logger.info("Beginning transaction for bulk update for Avtaar contact details...");
		sqlDB.beginTransaction();				//beginning the transaction

		String queryString = "INSERT INTO registered_users VALUES (?, ?)";		//"INSERT INTO registered_users VALUES (?, ?, ?)";

		try {
			SQLiteStatement stmt = sqlDB.compileStatement(queryString);

			Set<String> setOfContactNumbers = contactDetails.keySet(); 

			Bulk:
				for (String contactNumber : setOfContactNumbers) {
					String contactName = (String) contactDetails.get(contactNumber);

					stmt.bindString(1, contactNumber);
					stmt.bindString(2, contactName);

					long rowID = stmt.executeInsert();

					if (rowID == -1) {
						isUploaded = false;
						break Bulk;
					}

					isUploaded = true;
				}

			if (isUploaded == true) {
				sqlDB.setTransactionSuccessful();			//setting the transaction as successful since no error occurred.
				Logger.info("Bulk upload successful for contact details for an Avtaar...");
			} else {
				Logger.fatal("Bulk upload failed for contact details for an Avtaar...");
			}
		} catch (SQLException sqle) {
			Logger.fatal("SQLException occurred while performing bulk update");
			Logger.logStackTrace(sqle);
			sqle.printStackTrace();
			return false;
		} catch (Exception e) {
			Logger.fatal(e.getClass().getName() + " exception occurred while performing bulk update");
			Logger.logStackTrace(e);
			e.printStackTrace();
			return false;
		} finally {
			try {
				sqlDB.endTransaction();				//ending the transaction
				sqlDB.close();						//closing SQLiteDatabase instance
			} catch (Exception e) {
				Logger.fatal(e.getClass().getName() + " exception occurred while performing bulk update");
				Logger.logStackTrace(e);
				e.printStackTrace();
			}
		}

		return isUploaded;
	}//end of uploadRegisteredUsers()
*/
}//end of class