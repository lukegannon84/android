package com.example.dp_v4;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NotesActivity extends Activity {
	 // User Session Manager Class
    UserSessionManager session;
    
	private EditText editHotel;
	private EditText editFood;
	private EditText editOther;
	private EditText editNotes;
	private EditText editMaterials;
	 String enterDate;
	 	private Button btn_Save;
	    private Button btn_Cancel;
	    private Button btn_menu;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notes);
		
		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		

		 // Session class instance
      session = new UserSessionManager(getApplicationContext());
      
		
		editHotel = (EditText) findViewById(R.id.editHotel);
		editFood = (EditText) findViewById(R.id.editFood);
		editOther = (EditText) findViewById(R.id.editOther);
		editNotes = (EditText) findViewById(R.id.editNotes);
		editMaterials = (EditText) findViewById(R.id.editMaterials);
		
		btn_menu = (Button) findViewById(R.id.btn_menu);
		btn_Save= (Button) findViewById(R.id.btn_Save); 
		btn_Cancel=(Button) findViewById(R.id.btn_Cancel);

		btn_menu.setOnClickListener(new OnClickListener() {
	    	public void onClick(final View v) {
	    		if(v.getId()==R.id.btn_menu){
	    			 final Intent intent = new Intent(NotesActivity.this, MainActivity.class);
	                 NotesActivity.this.startActivity(intent);
	    		}        		
	    	}
	    });
		 btn_Save.setOnClickListener(new OnClickListener() {
	            
	            @Override
	            public void onClick(final View v){
	            	
	             	
	                if("".equals(editHotel.getText().toString().trim())) {
	                    Toast.makeText(NotesActivity.this, "hotel", Toast.LENGTH_SHORT).show();
	                }
	                else if("".equals(editFood.getText().toString().trim())) {
	                    Toast.makeText(NotesActivity.this, "food", Toast.LENGTH_SHORT).show();
	                }
	                else if("".equals(editNotes.getText().toString().trim())) {
	                    Toast.makeText(NotesActivity.this, "notes", Toast.LENGTH_SHORT).show();
	                }
	                else if("".equals(editMaterials.getText().toString().trim())) {
	                    Toast.makeText(NotesActivity.this, "materials missing", Toast.LENGTH_SHORT).show();
	                }
	                else if("".equals(editOther.getText().toString().trim())) {
	                    Toast.makeText(NotesActivity.this, "missing other ", Toast.LENGTH_SHORT).show();
	                }
	                else {
	            	
	                	   // get user data from session
		                HashMap<String, String> user = session.getUserDetails();
		                 
		                // get name
		                String registration = user.get(UserSessionManager.KEY_NAME);
		                
		                // get user data from session
		                HashMap<String, String> companyName = session.getCompanyDetails();
		                 
		                // get name
		                String company = companyName.get(UserSessionManager.KEY_COMPANY);

	                	SimpleDateFormat sdatef = new SimpleDateFormat("yyyy-MM-dd");
	        			String currentDate = sdatef.format(new Date());
	        			       	enterDate=	currentDate;
	        			       	
	                // TODO Auto-generated method stub
	                String hotel = editHotel.getText().toString();
	                String food = editFood.getText().toString();
	                String materials = editMaterials.getText().toString();
	                String notes = editNotes.getText().toString();
	                String other = editOther.getText().toString();
	                String date=enterDate;
	                
	          

	          insertRecords(hotel,food,materials,other,notes,registration,company,date);
	          Toast.makeText(NotesActivity.this, "Saved to database", Toast.LENGTH_SHORT).show();
	            }
	                final Intent intent = new Intent(NotesActivity.this, MainActivity.class);
	                NotesActivity.this.startActivity(intent);
	            }
	        });
	        btn_Cancel.setOnClickListener(new OnClickListener() {
	            
	            @Override
	            public void onClick(final View v)
	            {
	                 editHotel.setText("");
	              	 editFood.setText("");
	                 editMaterials.setText("");
	                 editOther.setText("");
	             	 editNotes.setText("");

	          
	            }            
	            
	        });

	}
	private void insertRecords(String hotel,String food,String materials,String other,String notes,String registration,String company,String date)
    {
         ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
            nameValuePairs.add(new BasicNameValuePair("hotel", hotel));
            nameValuePairs.add(new BasicNameValuePair("food",food));
            nameValuePairs.add(new BasicNameValuePair("materials",materials));
            nameValuePairs.add(new BasicNameValuePair("other",other));
            nameValuePairs.add(new BasicNameValuePair("notes",notes));
            nameValuePairs.add(new BasicNameValuePair("registration",registration));
            nameValuePairs.add(new BasicNameValuePair("company",company));
            nameValuePairs.add(new BasicNameValuePair("date",date));
            sendData(nameValuePairs);
    }
    private void sendData(ArrayList<NameValuePair> data)
    {
        try 
        {//remember to change IP addresses when at home or in college etc!!
            HttpClient httpclient = new DefaultHttpClient();
            //HttpPost httppost = new HttpPost("http://192.168.1.136/android/insert.php");//HTC Phone hotspot
            HttpPost httppost = new HttpPost("http://192.168.1.9/android/insert_expenses.php");//Home Network
            httppost.setEntity(new UrlEncodedFormEntity(data));
            HttpResponse response = httpclient.execute(httppost);

        }
        catch (Exception e) {
            // TODO: handle exception
            Log.e("log_tag", "Error:  "+e.toString());
        }
    }
}
