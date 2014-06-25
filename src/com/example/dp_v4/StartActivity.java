package com.example.dp_v4;




import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StartActivity extends Activity {
	private EditText editReg;
	private EditText editName;
	private EditText editCompany;
	private EditText editStartMileage;
	String enterDate;
	
	private Button btn_enterDetails;
	  // User Session Manager Class
    UserSessionManager session;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		
		// User Session Manager
        session = new UserSessionManager(getApplicationContext());  
		
		editReg = (EditText) findViewById(R.id.editReg);
		editName = (EditText) findViewById(R.id.editName);
		editCompany = (EditText) findViewById(R.id.editCompany);
		editStartMileage = (EditText) findViewById(R.id.editStartMileage);
		
		btn_enterDetails = (Button) findViewById(R.id.btn_enterDetails);

		btn_enterDetails.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(final View v){
            	
            	   // Get username, password from EditText
                String username = editReg.getText().toString();
                String company = editCompany.getText().toString();
                 
                // Validate if username, password is filled            
                if(username.trim().length() > 0){
                     
                    // For testing puspose username, password is checked with static data
                    // username = admin
                    // password = admin
                     
                  
                         
                        // Creating user login session
                        // Statically storing name="Android Example"
                        // and email="androidexample84@gmail.com"
                        session.createUserLoginSession(username,company);
                         
                        // Starting MainActivity
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                         
                        // Add new Flag to start new Activity
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                         
                        finish();
                        
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            			String currentDate = sdf.format(new Date());
            			       	enterDate=	currentDate;
                        
                        // TODO Auto-generated method stub
                        String registration = editReg.getText().toString();
                        String name = editName.getText().toString();
                        String start_mileage = editStartMileage.getText().toString();
                        
                        String date=enterDate;
                       
                        

                  insertRecords(registration,name,start_mileage,company,date);
                  Toast.makeText(StartActivity.this, "Saved to database", Toast.LENGTH_SHORT).show();
                         
                              
                }else{
                     
                    // user didn't entered username or password
                    Toast.makeText(getApplicationContext(),
                         "Please enter username",
                              Toast.LENGTH_LONG).show();
                     
                }
                 
            }
        });
            	

            	
            
   }     
                

        
        
		
		
		
		
		
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	 private void insertRecords(String registration,String name,String start_mileage,String company,String date)
     {
          ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
             nameValuePairs.add(new BasicNameValuePair("registration", registration));
             nameValuePairs.add(new BasicNameValuePair("name",name));
             nameValuePairs.add(new BasicNameValuePair("start_mileage",start_mileage));
             nameValuePairs.add(new BasicNameValuePair("company",company));
             nameValuePairs.add(new BasicNameValuePair("date",date));
             sendData(nameValuePairs);
     }
     private void sendData(ArrayList<NameValuePair> data)
     {
         try 
         {
             HttpClient httpclient = new DefaultHttpClient();
             //HttpPost httppost = new HttpPost("http://192.168.1.136/android/start_screen_insert.php");//HTC Phone hotspot
             HttpPost httppost = new HttpPost("http://192.168.1.9/android/start_screen_insert.php");//Home Network
             httppost.setEntity(new UrlEncodedFormEntity(data));
             HttpResponse response = httpclient.execute(httppost);

         }
         catch (Exception e) {
             // TODO: handle exception
             Log.e("log_tag", "Error:  "+e.toString());
         }
     }

}
