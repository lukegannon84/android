package com.example.dp_v4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class UploadImage extends ListActivity {

   // Progress Dialog
   private ProgressDialog pDialog;

   // Creating JSON Parser object
   JSONParser jParser = new JSONParser();

   ArrayList<HashMap<String, String>> productsList;

   // url to get all products list
   //private static String url_all_products = "http://192.168.1.136/android/overall_select_data.php";
   private static String url_all_products = "http://192.168.1.9/android/trip_select_data.php";

   // JSON Node names
   private static final String TAG_SUCCESS = "success";
   private static final String TAG_PRODUCTS = "products";
  
   private static final String TAG_PRICE ="depart";
   private static final String TAG_MILEAGE ="destination";
   private static final String TAG_QTY ="start_time";
   private static final String TAG_TOTAL ="registration";

   // products JSONArray
   JSONArray products = null;

   @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_overall);
       
       ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

       // Hashmap for ListView
       productsList = new ArrayList<HashMap<String, String>>();

       // Loading products in Background Thread
       new LoadAllProducts().execute();

       // Get listview
       ListView lv = getListView();

       // on seleting single product
       // launching Edit Product Screen


   }

   // Response from Edit Product Activity
   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       super.onActivityResult(requestCode, resultCode, data);
       // if result code 100
       if (resultCode == 100) {
           // if result code 100 is received
           // means user edited/deleted product
           // reload this screen again
           Intent intent = getIntent();
           finish();
           startActivity(intent);
       }

   }

   /**
    * Background Async Task to Load all product by making HTTP Request
    * */
   class LoadAllProducts extends AsyncTask<String, String, String> {

       /**
        * Before starting background thread Show Progress Dialog
        * */
       @Override
       protected void onPreExecute() {
           super.onPreExecute();
           pDialog = new ProgressDialog(UploadImage.this);
           pDialog.setMessage("Loading products. Please wait...");
           pDialog.setIndeterminate(false);
           pDialog.setCancelable(false);
           pDialog.show();
       }

       /**
        * getting All products from url
        * */
       protected String doInBackground(String... args) {
           // Building Parameters
           List<NameValuePair> params = new ArrayList<NameValuePair>();
           // getting JSON string from URL
           JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);

           // Check your log cat for JSON reponse
           Log.d("All Products: ", json.toString());

           try {
               // Checking for SUCCESS TAG
               int success = json.getInt(TAG_SUCCESS);

               if (success == 1) {
                   // products found
                   // Getting Array of Products
                   products = json.getJSONArray(TAG_PRODUCTS);

                   // looping through All Products
                   for (int i = 0; i < products.length(); i++) {
                       JSONObject c = products.getJSONObject(i);

                       // Storing each json item in variable
                       
                       String depart = c.getString(TAG_MILEAGE);
                       String destination = c.getString(TAG_PRICE);
                       String start_time = c.getString(TAG_TOTAL);
                       String registration = c.getString(TAG_QTY);
                       // creating new HashMap
                       HashMap<String, String> map = new HashMap<String, String>();

                       // adding each child node to HashMap key => value
                       
                       map.put(TAG_MILEAGE, depart);
                       map.put(TAG_PRICE, destination);
                       map.put(TAG_TOTAL, start_time);
                       map.put(TAG_QTY, registration);

                       // adding HashList to ArrayList
                       productsList.add(map);
                   }
               } 
           } catch (JSONException e) {
               e.printStackTrace();
           }

           return null;
       }

       /**
        * After completing background task Dismiss the progress dialog
        * **/
       protected void onPostExecute(String file_url) {
           // dismiss the dialog after getting all products
           pDialog.dismiss();
           // updating UI from Background Thread
           runOnUiThread(new Runnable() {
               public void run() {
                   /**
                    * Updating parse dJSON data into ListView
                    * */
                   ListAdapter adapter = new SimpleAdapter(
                           UploadImage.this, productsList,
                           R.layout.list_trip, new String[] { TAG_MILEAGE,
                                   TAG_QTY,TAG_PRICE,TAG_TOTAL},
                           new int[] { R.id.depart, R.id.destination,R.id.start_time,R.id.registration });
                   // updating listview
                   setListAdapter(adapter);
               }
           });

       }

   }
}