package com.example.dp_v4;


import java.util.HashMap;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	protected static final int ACTIVITY_SELECT_IMAGE = 0;
	private Button btn_fillUp;
	private Button btn_planner;
	private Button btn_overall;
	private Button btn_start;
	private Button btn_details;
	private Button btn_notes;
	private Button btn_menu;
	
	  // User Session Manager Class
    UserSessionManager session;
     

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
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
         
       
		//Initialise all the buttons
		btn_fillUp = (Button) findViewById(R.id.btn_fillUp);
		btn_planner = (Button) findViewById(R.id.btn_planner);
		btn_overall = (Button) findViewById(R.id.btn_overall);
		btn_start = (Button) findViewById(R.id.btn_start);
		btn_details = (Button) findViewById(R.id.btn_details);
		btn_menu = (Button) findViewById(R.id.btn_menu);
		btn_notes =(Button) findViewById(R.id.btn_notes);
		
		btn_fillUp.setOnClickListener(new OnClickListener() {
        	public void onClick(final View v) {
        		if(v.getId()==R.id.btn_fillUp){
        			 final Intent intent = new Intent(MainActivity.this, fillUpMenuActivity.class);
                     MainActivity.this.startActivity(intent);
        		}        		
        	}
        });
		
		btn_planner.setOnClickListener(new OnClickListener() {
        	public void onClick(final View v) {
        		if(v.getId()==R.id.btn_planner){
        			 final Intent intent = new Intent(MainActivity.this, PlannerActivity.class);
                     MainActivity.this.startActivity(intent);
        		}        		
        	}
        });
	
	btn_overall.setOnClickListener(new OnClickListener() {
    	public void onClick(final View v) {
    		if(v.getId()==R.id.btn_overall){
    			 final Intent intent = new Intent(MainActivity.this, OverallActivity.class);
                 MainActivity.this.startActivity(intent);
    		}        		
    	}
    });
	btn_start.setOnClickListener(new OnClickListener() {
    	public void onClick(final View v) {
    		if(v.getId()==R.id.btn_start){
    			 
    			 final Intent intent = new Intent(MainActivity.this, UploadImage.class);
                MainActivity.this.startActivity(intent);
    		}        		
    	}
    });
	btn_details.setOnClickListener(new OnClickListener() {
    	public void onClick(final View v) {
    		if(v.getId()==R.id.btn_details){
    			 final Intent intent = new Intent(MainActivity.this, EditDetailsActivity.class);
                 MainActivity.this.startActivity(intent);
    		}        		
    	}
    });
	btn_notes.setOnClickListener(new OnClickListener() {
    	public void onClick(final View v) {
    		if(v.getId()==R.id.btn_notes){
    			 final Intent intent = new Intent(MainActivity.this, NotesActivity.class);
                 MainActivity.this.startActivity(intent);
    		}        		
    	}
    });
	btn_menu.setOnClickListener(new OnClickListener() {
    	public void onClick(final View v) {
    		if(v.getId()==R.id.btn_menu){
    			 final Intent intent = new Intent(MainActivity.this, MainActivity.class);
                 MainActivity.this.startActivity(intent);
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
