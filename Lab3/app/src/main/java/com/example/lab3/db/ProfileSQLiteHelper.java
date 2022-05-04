package com.example.lab3.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.lab3.db.users.Authorization;
import com.example.lab3.db.users.Profile;

public class ProfileSQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "relaxation.db";
    private static final int SCHEMA = 1;
    private final String PROFILE_TABLE_NAME = "profiles";
    private final String AUTHORIZATION_TABLE_NAME = "authorized_data";
    private Cursor cursor = null;
    private boolean isActive = false;
    //profile
    private final String PROFILE_ID = "p_id";
    private final String PROFILE_NAME = "p_name";
    private final String PROFILE_NICKNAME = "p_nickname";
    private final String PROFILE_EMAIL = "p_email";
    //authorized_data
    private final String AUTHORIZATION_ID = "a_id";
    private final String AUTHORIZATION_PASSWORD = "a_password";
    private final String AUTHORIZATION_HEX = "a_hex";
    private final String AUTHORIZATION_PROFILE_ID = "a_profile_id";

    public ProfileSQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, SCHEMA);
        isActive = true;
    }

    public void addProfile(SQLiteDatabase db, Authorization obj, Profile person){
        if(isActive){
            try{
                String password = obj.getPassword();
                String hex = obj.getHex();
                String name = person.getName();
                String nickname = person.getNickname();
                String email = person.getEmail();
                db.execSQL("INSERT INTO "+PROFILE_TABLE_NAME+" ("+PROFILE_NAME+", "+PROFILE_NICKNAME+","+PROFILE_EMAIL+") " +
                        "VALUES ('"+name+"', '"+nickname+"', '"+email+"');");
                int prof_id = 0;
                cursor = db.rawQuery("select "+PROFILE_ID+" from " + PROFILE_TABLE_NAME + " where "+ PROFILE_EMAIL +" = '"+ email +"'", null);
                while(cursor.moveToNext()){
                    prof_id = cursor.getInt(0);
                }
                cursor.close();
                db.execSQL("INSERT INTO "+AUTHORIZATION_TABLE_NAME+" ("+AUTHORIZATION_PASSWORD+", "+AUTHORIZATION_HEX+", "+AUTHORIZATION_PROFILE_ID+") " +
                        " VALUES ('"+ password+"', '"+hex+"', "+prof_id+");");
            }catch(Exception ex){
                System.out.println(ex.getMessage());
            }
        }
    }

    public Profile getProfile(SQLiteDatabase db, String hex){
        Profile profile = null;
        if(isActive){
            String name = "";
            String nickname = "";
            String email = "";

            try{
                cursor = db.rawQuery("select * from " + AUTHORIZATION_TABLE_NAME + " where "+ AUTHORIZATION_HEX +" = '"+ hex +"'", null);
                int prof_id = -1;
                while(cursor.moveToNext()){
                    prof_id = cursor.getInt(0);
                }
                cursor.close();
                if(prof_id == -1){
                    return null;
                }else{
                    cursor = db.rawQuery("select * from " + PROFILE_TABLE_NAME + " where "+ PROFILE_ID +" = "+ prof_id +"", null);
                    while(cursor.moveToNext()){
                        name = cursor.getString(1);
                        nickname = cursor.getString(2);
                        email = cursor.getString(3);
                    }
                    profile = new Profile(name, nickname,email);
                }
            }catch(Exception ex){
                System.out.println(ex.getMessage());
            }
        }
        return profile;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+PROFILE_TABLE_NAME+" ("+PROFILE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+PROFILE_NAME+" TEXT, " +
                " "+PROFILE_NICKNAME+" TEXT, "+PROFILE_EMAIL+" TEXT);");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+AUTHORIZATION_TABLE_NAME+" ("+AUTHORIZATION_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+AUTHORIZATION_PASSWORD+" TEXT, " +
                ""+AUTHORIZATION_HEX+" TEXT, "+AUTHORIZATION_PROFILE_ID+" INTEGER, FOREIGN KEY ("+AUTHORIZATION_PROFILE_ID+") REFERENCES "+PROFILE_TABLE_NAME+" ("+PROFILE_ID+"));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(" DROP TABLE IF EXISTS " + AUTHORIZATION_TABLE_NAME);
        db.execSQL(" DROP TABLE IF EXISTS " + PROFILE_TABLE_NAME);
        onCreate(db);
    }
}
