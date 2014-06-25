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
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class PlannerActivity extends Activity implements LocationListener {
    private EditText editDepart;
    private EditText editDestination;
   
   
    String enterDate;
    
    
    // User Session Manager Class
    UserSessionManager session;
    
    private Button btnStop;
    private Button btnStart;
    private Button btn_Cancel;
   // private Button btn_location;
    private Button btn_menu;
   Double startLat,startLon,stopLat,stopLon;
    String startL,startT,stopT,stopL;
    String latLongString = "No location found";
    String addressString = "No address found";
    Double longitude,latitude;
    
	private LocationManager lm; // the location manager to get access to the gps stuff
	private Location l; // the location that we will get from the gps provider
	
	
   
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner);     
        
        ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		
		

		 // Session class instance
      session = new UserSessionManager(getApplicationContext());
      //get access to the gps provider
      
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		l = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		
		
				
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5, 0, this);
        //Graphical components are recovered
        editDepart = (EditText) findViewById(R.id.editDepart);
        editDestination = (EditText) findViewById(R.id.editDestination);
       //editStartLocation = (EditText) findViewById(R.id.editStartLocation);
      // editStopLocation = (EditText) findViewById(R.id.editStopLocation);
       // editStartTime=(EditText) findViewById(R.id.editStartTime);
        btnStop= (Button) findViewById(R.id.btnStop);
        btnStart = (Button) findViewById(R.id.btnStart);
         btn_Cancel = (Button) findViewById(R.id.btn_Cancel);
        
        //btn_location= (Button) findViewById(R.id.btn_location);
        btn_menu = (Button) findViewById(R.id.btn_menu);
        
        

              
        
        btnStop.setOnClickListener(new OnClickListener() {
        	public void onClick(final View v) {
        		if(v.getId()==R.id.btnStop){
            		//setText set departure as current location
        		     // TODO Auto-generated method stub
        			
        			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        			String currentTime = sdf.format(new Date());
        			       	stopT=	currentTime;	
        			//editStopTime.setText(currentTime);
            		//editStopTime.getText().toString();
            			//stopL=l.getLatitude() + ", " + l.getLongitude();
                  // editStopLocation.setText(l.getLatitude() + ", " + l.getLongitude());
                  // editStopLocation.getText().toString();
        			       	try{
        			       		
        			       		Double lat=l.getLatitude();
                 				Double lon= l.getLongitude();
                 				String stLat = Double.toString(lat);
                 				String stLon = Double.toString(lon);
                 				String comma=",";
                 				stopL=stLat+comma+stLon;
        			       //	stopL=l.getLatitude() + ", " + l.getLongitude();
                 				
                 			//l.setLatitude(Location.convert(editStartLat.getText().toString()));
                 			//l.setLongitude(Location.convert(editStartLon.getText().toString()));
                 			}catch (Exception e) {
                 	             // TODO: handle exception
                 	             Log.e("log_tag", "Error:  "+e.toString());
                 	         }
        			        
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
                    			       	
	                String depart = editDepart.getText().toString();
	                String destination = editDestination.getText().toString();
	                String startLocation = startL;
	                String startTime = startT;
	                String stopLocation=stopL;
	                String stopTime=stopT;
	                String date=enterDate;
	            

	          insertRecords(depart,destination,startLocation,startTime,stopLocation,stopTime,registration,company,date);
            		   
	          Toast.makeText(PlannerActivity.this, "Saved to database", Toast.LENGTH_SHORT).show();
	            }
	                final Intent intent = new Intent(PlannerActivity.this, MainActivity.class);
	                PlannerActivity.this.startActivity(intent);		
        		
        	}
        });
        
        btn_Cancel.setOnClickListener(new OnClickListener() {
        	public void onClick(final View v) {
        		if(v.getId()==R.id.btn_Cancel){
            		//setText will remove all text that is written by someone
            		    editDepart.setText("");
            		    editDestination.setText("");
            		    }
        		
        		
        	}
        });
    	btn_menu.setOnClickListener(new OnClickListener() {
        	public void onClick(final View v) {
        		if(v.getId()==R.id.btn_menu){
        			 final Intent intent = new Intent(PlannerActivity.this, MainActivity.class);
                     PlannerActivity.this.startActivity(intent);
        		}        		
        	}
        });
        
        
 
    	 btnStart.setOnClickListener(new OnClickListener() {
             
             public void onClick(final View v) {
             	if(v.getId()==R.id.btnStart){
             		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
         		String currentTime = sdf.format(new Date());
         			       			
         			startT=currentTime;
         		//	editStartTime.setText(startT);
         			//editStartTime.getText().toString();
         			try{
         				Double lat=l.getLatitude();
         				Double lon= l.getLongitude();
         				String stLat = Double.toString(lat);
         				String stLon = Double.toString(lon);
         				String comma=",";
         				startL=stLat+comma+stLon;
         				//startL=l.getLatitude() + "," + l.getLongitude();
         			//l.setLatitude(Location.convert(editStartLat.getText().toString()));
         			//l.setLongitude(Location.convert(editStartLon.getText().toString()));
         			}catch (Exception e) {
         	             // TODO: handle exception
         	             Log.e("log_tag", "Error:  "+e.toString());
         	         }


                 //editStartLocation.setText(l.getLatitude() + ", " + l.getLongitude());
                 //editStartLocation.getText().toString();
                 //startL=l.getLatitude() + ", " + l.getLongitude();
              //   editStartLocation.setText(startL);
               editDepart.getText().toString();
               editDestination.getText().toString();
             }
             }
         });
        
    }
    
	@Override
	public void onLocationChanged(Location location) {
		
	}
	@Override
	public void onProviderDisabled(String provider) {
	// will do nothing here for now
	}
	
	@Override
	public void onProviderEnabled(String provider) {
	// will do nothing here for now
	}
	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}
	
	
	 private void insertRecords(String depart,String destination,String startLocation,String startTime,String stopLocation,String stopTime,String registration,String company,String date)
     {
          ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(7);
             nameValuePairs.add(new BasicNameValuePair("depart", depart));
             nameValuePairs.add(new BasicNameValuePair("destination",destination));
             nameValuePairs.add(new BasicNameValuePair("start_location",startLocation));
             nameValuePairs.add(new BasicNameValuePair("start_time",startTime));
             nameValuePairs.add(new BasicNameValuePair("stop_location",stopLocation));
             nameValuePairs.add(new BasicNameValuePair("stop_time",stopTime));
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
             HttpPost httppost = new HttpPost("http://192.168.1.9/android/insert_trip.php");//Home Network
             httppost.setEntity(new UrlEncodedFormEntity(data));
             HttpResponse response = httpclient.execute(httppost);

         }
         catch (Exception e) {
             // TODO: handle exception
             Log.e("log_tag", "Error:  "+e.toString());
         }
     }
     
}
