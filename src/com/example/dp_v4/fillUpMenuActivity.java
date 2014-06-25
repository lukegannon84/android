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

public class fillUpMenuActivity extends Activity {
	  // User Session Manager Class
    UserSessionManager session;
	  private Button calcTipAmountButton;
	    private Button btn_Save;
	    private Button btn_Cancel;
	    private Button btn_menu;
	    
	    String enterDate;
	    
	    private EditText editMileage;
	    private EditText editDate;
	    private EditText editQty;
	    private EditText editPrice_Per;
	    private EditText editTotal_Cost;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fill);
		
		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		

		 // Session class instance
       session = new UserSessionManager(getApplicationContext());
       
       
				
		editMileage = (EditText) findViewById(R.id.editMileage);
		
		editQty = (EditText) findViewById(R.id.editQty);
		editPrice_Per = (EditText) findViewById(R.id.editPrice_Per);
		editTotal_Cost = (EditText) findViewById(R.id.editTotal_Cost);
		
		btn_menu = (Button) findViewById(R.id.btn_menu);
		btn_Save= (Button) findViewById(R.id.btn_Save); 
		btn_Cancel=(Button) findViewById(R.id.btn_Cancel);
		calcTipAmountButton=(Button) findViewById(R.id.calcTipAmountButton);
		
		
		
		btn_menu.setOnClickListener(new OnClickListener() {
	    	public void onClick(final View v) {
	    		if(v.getId()==R.id.btn_menu){
	    			 final Intent intent = new Intent(fillUpMenuActivity.this, MainActivity.class);
	                 fillUpMenuActivity.this.startActivity(intent);
	    		}        		
	    	}
	    });
		 btn_Save.setOnClickListener(new OnClickListener() {
	            
	            @Override
	            public void onClick(final View v){
	            	
	             	
	                if("".equals(editMileage.getText().toString().trim())) {
	                    Toast.makeText(fillUpMenuActivity.this, "Milage missing", Toast.LENGTH_SHORT).show();
	                }
	               
	                else if("".equals(editQty.getText().toString().trim())) {
	                    Toast.makeText(fillUpMenuActivity.this, "Qty amount missing", Toast.LENGTH_SHORT).show();
	                }
	                else if("".equals(editPrice_Per.getText().toString().trim())) {
	                    Toast.makeText(fillUpMenuActivity.this, "Price Per Litre missing", Toast.LENGTH_SHORT).show();
	                }
	                else if("".equals(editTotal_Cost.getText().toString().trim())) {
	                    Toast.makeText(fillUpMenuActivity.this, "Missing a total value", Toast.LENGTH_SHORT).show();
	                }
	                else {
	            	
	                	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            			String currentDate = sdf.format(new Date());
            			       	enterDate=	currentDate;
            
            			     // get user data from session
            	                HashMap<String, String> user = session.getUserDetails();
            	                 
            	                // get name
            	                String registration = user.get(UserSessionManager.KEY_NAME);
            	                
            	             // get user data from session
            	                HashMap<String, String> companyName = session.getCompanyDetails();
            	                 
            	                // get name
            	                String company = companyName.get(UserSessionManager.KEY_COMPANY);
            	                
            	                
	                // TODO Auto-generated method stub
	                String mileage = editMileage.getText().toString();
	                String qty = editQty.getText().toString();
	                String price = editPrice_Per.getText().toString();
	                String total = editTotal_Cost.getText().toString();
	                String date=enterDate;
	                
	                
	             

	          insertRecords(mileage,date,qty,price,total,registration,company);
	          Toast.makeText(fillUpMenuActivity.this, "Saved to database", Toast.LENGTH_SHORT).show();
	            }
	                final Intent intent = new Intent(fillUpMenuActivity.this, MainActivity.class);
	                fillUpMenuActivity.this.startActivity(intent);
	            }
	        });
	        btn_Cancel.setOnClickListener(new OnClickListener() {
	            
	            @Override
	            public void onClick(final View v)
	            {
	                 editMileage.setText("");
	              	 editQty.setText("");
	                 editPrice_Per.setText("");
	             	 editTotal_Cost.setText("");

	          
	            }            
	            
	        });
	        
	        calcTipAmountButton.setOnClickListener(new OnClickListener() {
	            
	            @Override
	            public void onClick(final View v) {

	                    calculateTip();
	                }              
	            
	        });
	        
	}
	        private void calculateTip() {
	    		double qtyAmount;
	    		double priceAmount;
	    		
	    		try {
	    			qtyAmount = Double.parseDouble(editQty.getText().toString());
	    		
	    		} catch (NumberFormatException ex) {
	    			qtyAmount = 0;
	    	}
	    		try {
	    			priceAmount = Double.parseDouble(editPrice_Per.getText().toString());
	    		
	    		} catch (NumberFormatException ex) {
	    			priceAmount =0;
	    	}
	    	
	    	
	    	double totalAmount = priceAmount * qtyAmount;
	    	
	    	//tipAmountTextView.setText(String.format("$%.2f", tipAmount));
	    	editTotal_Cost.setText(Double.toString(totalAmount));	
	    	}
	    	
	  private void insertRecords(String mileage,String date,String qty,String price,String total,String registration,String company)
	         {
	              ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
	                 nameValuePairs.add(new BasicNameValuePair("mileage", mileage));
	                 nameValuePairs.add(new BasicNameValuePair("date",date));
	                 nameValuePairs.add(new BasicNameValuePair("qty",qty));
	                 nameValuePairs.add(new BasicNameValuePair("price",price));
	                 nameValuePairs.add(new BasicNameValuePair("total",total));
	                 nameValuePairs.add(new BasicNameValuePair("registration",registration));
	                 nameValuePairs.add(new BasicNameValuePair("company",company));
	                 sendData(nameValuePairs);
	         }
	         private void sendData(ArrayList<NameValuePair> data)
	         {
	             try 
	             {//remember to change IP addresses when at home or in college etc!!
	                 HttpClient httpclient = new DefaultHttpClient();
	                 //HttpPost httppost = new HttpPost("http://192.168.1.136/android/insert.php");//HTC Phone hotspot
	                 HttpPost httppost = new HttpPost("http://192.168.1.9/android/insert.php");//Home Network
	                 httppost.setEntity(new UrlEncodedFormEntity(data));
	                 HttpResponse response = httpclient.execute(httppost);

	             }
	             catch (Exception e) {
	                 // TODO: handle exception
	                 Log.e("log_tag", "Error:  "+e.toString());
	             }
	         }
	 }
