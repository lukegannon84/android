package com.example.dp_v4;


import java.util.HashMap;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class EditDetailsActivity extends Activity {
	
	private Button btn_menu;
	private Button btnChangeVechile;
	private Button btnEditDetails;
	
	  // User Session Manager Class
    UserSessionManager session;
     

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editdetails);
		
		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		
		
		
		
		 // Session class instance
        session = new UserSessionManager(getApplicationContext());
        
        
        // Check user login (this is the important point)
        // If User is not logged in , This will redirect user to LoginActivity
        // and finish current activity from activity stack.
        if(session.checkLogin())
            finish();
         
        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
         
        // get name
        String registration = user.get(UserSessionManager.KEY_NAME);
        
        // get user data from session
        HashMap<String, String> companyName = session.getCompanyDetails();
         
        // get name
        final String company = companyName.get(UserSessionManager.KEY_COMPANY);
         
       
		//Initialise all the buttons
		
		btnChangeVechile = (Button) findViewById(R.id.btnChangeVechile);
		btn_menu = (Button) findViewById(R.id.btn_menu);
		btnEditDetails =(Button) findViewById(R.id.btnEditDetails);
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		

	    builder.setTitle("Confirm");
	    builder.setMessage("Are you sure?");

	    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

	        public void onClick(DialogInterface dialog, int which) {
	            // Do nothing but close the dialog
	        	session.createUserLoginSession("", company);
	        	Toast.makeText(EditDetailsActivity.this, "Register New Vechile", Toast.LENGTH_LONG).show();
	        	final Intent intent = new Intent(EditDetailsActivity.this, StartActivity.class);
    			EditDetailsActivity.this.startActivity(intent);
	            dialog.dismiss();
	        }

	    });

	    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

	        @Override
	        public void onClick(DialogInterface dialog, int which) {
	            // Do nothing
	            dialog.dismiss();
	        }
	    });
		btnChangeVechile.setOnClickListener(new OnClickListener() {
        	public void onClick(final View v) {
        		if(v.getId()==R.id.btnChangeVechile){
        			AlertDialog alert = builder.create();
        		    alert.show();
        			
        			 
        		}        		
        	}
        });
		
		btnEditDetails.setOnClickListener(new OnClickListener() {
        	public void onClick(final View v) {
        		if(v.getId()==R.id.btnEditDetails){
        			 final Intent intent = new Intent(EditDetailsActivity.this, EditStartActivity.class);
        			 EditDetailsActivity.this.startActivity(intent);
        		}        		
        	}
        });
		
	
	btn_menu.setOnClickListener(new OnClickListener() {
    	public void onClick(final View v) {
    		if(v.getId()==R.id.btn_menu){
    			 final Intent intent = new Intent(EditDetailsActivity.this, MainActivity.class);
    			 EditDetailsActivity.this.startActivity(intent);
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

}
