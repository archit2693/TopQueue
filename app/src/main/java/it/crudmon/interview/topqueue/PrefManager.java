package it.crudmon.interview.topqueue;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.List;

import model.questionList;

/**
 * Created by Ravi on 01/06/15.
 */
public class PrefManager {
    // Shared Preferences
    SharedPreferences pref;
    SharedPreferences pref1;

    // Editor for Shared preferences
    Editor editor;
    Editor editor1;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

	

    // Shared pref file name
    private static final String PREF_NAME = "AndroidHive";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";
    
    public static final String Event = "event";

    public static final String LOGIN = "login";


    // Constructor
    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        pref1 = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        
        editor = pref.edit();
        editor1 = pref1.edit();
    }

    /**
     * Create login session
     */
    public void createLoginSession(String email) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);

        // commit changes
        editor.commit();
    }
    
    public void setEvent(String product) {

	
	editor.putString(Event, product);

        // Storing email in pref
        

        // commit changes
        editor.commit();
    }
    
    public void setLogin(String product) {

    	
    	editor1.putString(LOGIN, product);

            // Storing email in pref
            

            // commit changes
            editor1.commit();
        }
 public void setRecent(String product) {

    	
    	editor1.putString("recent", product);

            // Storing email in pref
            

            // commit changes
            editor1.commit();
        }
    public void setPopular(String product) {

    	
    	editor1.putString("popular", product);

            // Storing email in pref
            

            // commit changes
            editor1.commit();
        }
 public void setCenter(String product) {

    	
    	editor1.putString("center", product);

            // Storing email in pref
            

            // commit changes
            editor1.commit();
        }
 public void setClass(String product) {

 	
 	editor1.putString("class", product);

         // Storing email in pref
         

         // commit changes
         editor1.commit();
     }
    public void setPref(String array) {

//        Set<String> set=new HashSet<String>();
//        set.addAll(arrayList);
       editor1.putString("pref", array);




        // commit changes
        editor1.commit();
    }
 public void setRoll(String product) {

	 	
	 	editor1.putString("roll", product);

	         // Storing email in pref
	         

	         // commit changes
	         editor1.commit();
	     }
 public void setName(String product) {

	 	
	 	editor1.putString("name", product);

	         // Storing email in pref
	         

	         // commit changes
	         editor1.commit();
	     }
    public void setId(int product) {


        editor1.putInt("id", product);

        // Storing email in pref


        // commit changes
        editor1.commit();
    }
    public void setnetworkstate(int product) {


        editor1.putInt("astate", product);

        // Storing email in pref


        // commit changes
        editor1.commit();
    }
    public void setcontacts(int product) {


        editor1.putInt("contacts", product);

        // Storing email in pref


        // commit changes
        editor1.commit();
    }
    public void setFields(String product) {


        editor1.putString("fields", product);

        // Storing email in pref


        // commit changes
        editor1.commit();
    }
    public void setBranch(String product) {

        editor1.putString("branchList", product);
        // Storing email in pref
        // commit changes
        editor1.commit();
    }
    public void setupdateCheck(String product) {


        editor1.putString("update", product);

        // Storing email in pref


        // commit changes
        editor1.commit();
    }
    public void setFieldId(int product) {


        editor1.putInt("fieldId", product);

        // Storing email in pref


        // commit changes
        editor1.commit();
    }
    public void setcheck(boolean product) {


        editor1.putBoolean("fieldId", product);

        // Storing email in pref


        // commit changes
        editor1.commit();
    }
    public void setPermanentFieldId(int product) {


        editor1.putInt("perId", product);

        // Storing email in pref


        // commit changes
        editor1.commit();
    }


    public void setDescription(String message,String product) {

    	
    	editor.putString(message, product);

            // Storing email in pref
            

            // commit changes
            editor.commit();
        }
    
    public String getDescription(String message) {
        return pref.getString(message, null);
    }
    
    public String getEvent() {
        return pref.getString(Event, null);
    }
    
    public String getLogin() {
        return pref.getString(LOGIN, null);
    }
    
    public String getName() {
        return pref.getString("name", null);
    }

    public int getFieldId() {
        return pref.getInt("fieldId", 0);
    }
    public int getPermanentFieldId() {
        return pref.getInt("perId", 0);
    }

    public int getId() {
        return pref.getInt("id", 0);
    }
    public int getcontacts() {
        return pref.getInt("contacts", 0);
    }
    public String getFields() {
        return pref.getString("fields", null);
    }
    public String getBranches() {
        return pref.getString("branchList", null);
    }
    public String getUpdateCheck() {
        return pref.getString("update", null);
    }

    public String getRoll() {
        return pref.getString("roll", null);
    }
    public boolean getcheck() {
        return pref.getBoolean("roll", false);
    }
    public String getpref() {
        return pref.getString("pref", null);
    }
    
    public String getRecent() {
        return pref.getString("recent", null);
    }

    public String getEmail() {
        return pref.getString(KEY_EMAIL, null);
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }
    public String getPopular() {
        return pref.getString("popular", null);
    }  public String getCenter() {
        return pref.getString("center", null);
    }  public String getclass() {
        return pref.getString("class", null);
    }
    public int getnetworkstate(){return pref.getInt("nstate",0);}
    public void logout() {
        editor1.clear();
        editor1.commit();
    }


    public void setList(List<questionList> questionlist) {
        Gson gson = new Gson();
            String json = gson.toJson(questionlist);

            set("ques_list", json);
    }
    public void set(String key, String value)
    {
        editor1.putString("ques_list", value);
        editor1.commit();
    }
    /*public String getList() {
        Gson gson = new Gson();
        String json = pref.getString("MyObject", "");
        ArrayList<NativeDashFragment.MenuEntry> list2 = gson.fromJson(json, list);


        }*/

    public void saveList(String list) {
        //myvalue =list;
       /* GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ClassData.class, new ClassDataSerializer());
        HashMap<List, ClassData> map = new HashMap<>();
        map.put("key", new ClassData("key", List.class));

        Gson gson = gsonBuilder.create();
        String json = gson.toJson(map);*/
        editor.putString("MyObject", list);
        editor.commit();
    }

    public String getJson()
    {

        return pref.getString("MyObject",null);
    }





     class ClassData {
        public String jsonString;
        public Class classType;

        public ClassData(String jsonString, Class classType) {
            this.jsonString = jsonString;
            this.classType = classType;
        }
    }

     class ClassDataSerializer implements JsonSerializer<ClassData> {
        @Override
        public JsonElement serialize(ClassData src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.jsonString);
        }
    }




}

