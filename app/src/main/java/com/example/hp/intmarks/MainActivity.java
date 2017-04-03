package com.example.hp.intmarks;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import activity.DisplayMarks;
import activity.NameActivity;
import helper.SQLiteHandler;
import helper.SessionManager;
import activity.UpdatePassword;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView txtName;
    private TextView txtUSN;

    private Button btnLogout;

    private Button course1;
    private Button course2;
    private Button course3;
    private Button course4;
    private Button course5;
    private Button course6;
    private SQLiteHandler db;
    private SessionManager session;

    public static String dispName;
    public static String usn;
    public static String courseCode1;
    public static String courseCode2;
    public static String courseCode3;
    public static String courseCode4;
    public static String courseCode5;
    private ProgressDialog pDialog;
    public String name;
    public String USN;
    String courseId[]={"16IS6DCSEO","16IS6DCCNS","16IS6DCCDN","16IS6DCEAM","16IS6DCSNA","16IS6DCMLG"};
    public static final String TAG =MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
      // pDialog = new ProgressDialog(this);
       // pDialog.setCancelable(false);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        btnLogout = (Button) findViewById(R.id.btnLogout);
        course1=(Button)findViewById(R.id.sub1);
        course2=(Button)findViewById(R.id.sub2);
        course3=(Button)findViewById(R.id.sub3);
        course4=(Button)findViewById(R.id.sub4);
        course5=(Button)findViewById(R.id.sub5);
        course6=(Button)findViewById(R.id.sub6);
        courseCode1=course1.getText().toString();
        courseCode2=course2.getText().toString();
        courseCode3=course3.getText().toString();
        courseCode4=course4.getText().toString();
        courseCode5=course5.getText().toString();
        setSupportActionBar(toolbar);

 /*       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();
        View hView =  navigationView.getHeaderView(0);
        name = user.get("name");
        USN = user.get("USN");
        String password=user.get("password");
        System.out.println(name+" "+USN);
     /*   if(password.equals(USN))
        {
            Intent intent=new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }*/
       dispName=name;
        usn=USN;
        // Displaying the user details on the screen
        txtName = (TextView)hView.findViewById(R.id.name);
        txtName.setText(name);
        txtUSN = (TextView)hView.findViewById(R.id.username);
        txtUSN.setText(USN);

        //check if marks are present in database
        for(int i=0;i<6;i++) {
            HashMap<String, String> courseMarks = db.getMarks(courseId[i]);
            if (courseMarks.size() == 0) {
                if (isOnline()) {
                    new MarksDataRetrieveAsyncTask().execute(courseId[i]);
                }
                else{
                    Toast.makeText(this, "Not connected to online.Please connect to download marks", Toast.LENGTH_SHORT).show();
                }

            }
        }

        course1.setOnClickListener(new View.OnClickListener() {

                                       public void onClick(View view) {
                                           //View hView =  navigationView.getHeaderView(0);

                                           Intent intent = new Intent(MainActivity.this, DisplayMarks.class);
                                           intent.putExtra("courseId",courseId[0]);
                                           startActivity(intent);


                                       }
                                   }
        );
        course2.setOnClickListener(new View.OnClickListener() {

                                       public void onClick(View view) {
                                           //View hView =  navigationView.getHeaderView(0);
                                           Intent intent = new Intent(MainActivity.this, DisplayMarks.class);
                                           intent.putExtra("courseId",courseId[1]);
                                           startActivity(intent);
                                       }
                                   }
        );
        course3.setOnClickListener(new View.OnClickListener() {

                                       public void onClick(View view) {
                                           //View hView =  navigationView.getHeaderView(0);
                                           Intent intent = new Intent(MainActivity.this, DisplayMarks.class);
                                           intent.putExtra("courseId",courseId[2]);
                                           startActivity(intent);

                                       }
                                   }
        );
        course4.setOnClickListener(new View.OnClickListener() {

                                       public void onClick(View view) {
                                           //View hView =  navigationView.getHeaderView(0);
                                           Intent intent = new Intent(MainActivity.this, DisplayMarks.class);
                                           intent.putExtra("courseId",courseId[3]);
                                           startActivity(intent);

                                       }
                                   }
        );
        course5.setOnClickListener(new View.OnClickListener() {

                                       public void onClick(View view) {
                                           //View hView =  navigationView.getHeaderView(0);
                                           Intent intent = new Intent(MainActivity.this, DisplayMarks.class);
                                           intent.putExtra("courseId",courseId[4]);
                                           startActivity(intent);

                                       }
                                   }
        );
        course6.setOnClickListener(new View.OnClickListener() {

                                       public void onClick(View view) {
                                           //View hView =  navigationView.getHeaderView(0);
                                           Intent intent = new Intent(MainActivity.this, DisplayMarks.class);
                                           intent.putExtra("courseId",courseId[5]);
                                           startActivity(intent);

                                       }
                                   }
        );


    }


    private Boolean isOnline()  {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if(ni != null && ni.isConnected())
            return true;

        return false;
    }

    private class MarksDataRetrieveAsyncTask extends AsyncTask<String,Void,Void> {
        @Override
        protected Void doInBackground(String... params){
            marks(usn,params[0]);
           // marks(usn,courseId[1]);
            //marks(usn,courseId[2]);
            //marks(usn,courseId[3]);
            //marks(usn,courseId[4]);
            return null;
        }

    }

    public boolean marks(final String username,final String course)
    {
        String tag_string_req = "req_login";

        /*pDialog.setMessage("Downloading Marks ...");
        showDialog();*/

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_MARKS, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Request Response: " + response.toString());
              //  hideDialog();

                try {

                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create login session
                        session.setLogin(true);

                        // Now store the user in SQLite
                        JSONObject user = jObj.getJSONObject("user");
                        String USN = user.getString("USN");
                        String subject_code=user.getString("COURSE_CODE");
                        String firstInternals=user.getString("TEST1");
                        String secondInternals=user.getString("TEST2");
                        String thirdInternals=user.getString("TEST3");
                        String quiz=user.getString("QUIZ");
                        String lab=user.getString("LAB");
                        String self_study=user.getString("S_STUDY");
                        String finalMarks=user.getString("FINAL_MARKS");
                        String bestofTwo=user.getString("BEST_OF_TWO");

                        Toast.makeText(getApplicationContext(),"Marks have been successfully retrieved",Toast.LENGTH_SHORT).show();
                        // String created_at = user
                        //.getString("created_at");

                        // Inserting row in users table

                        db.addmarks(USN,subject_code,firstInternals,secondInternals,thirdInternals,quiz,lab,self_study,finalMarks,bestofTwo);

                        // Launch main activity
                        /*Intent intent = new Intent(DisplayMarks.this,
                                MainActivity.class);
                        startActivity(intent);*/
                       // Intent intent = new Intent(MainActivity.this, DisplayMarks.class);
                        //intent.putExtra("course",course1.getText());
                        //startActivity(intent);
                        //finish();
                    } else {
                        // Error in login. Get the error message
                      String errorMsg = jObj.getString("error_msg");
                        /*Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();*/
                        System.out.println(errorMsg);
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), "unable to contact server for marks", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Retrieving Marks Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
               // hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("course", course);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq);
        return true;
    }


  /*  private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }*/
    private void logoutUser() {
        session.setLogin(false);
        db.deleteUsers();
        db.deleteMarks();
        // Launching the login activity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //System.exit(0);
            //finish();

            moveTaskToBack(true);
           /* if (exit) {
                finish(); // finish activity
            } else {
                Toast.makeText(this, "Press Back again to Exit.",
                        Toast.LENGTH_SHORT).show();
                exit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        exit = false;
                    }
                }, 3 * 1000);

            }*/
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id==R.id.btnLogout)
            logoutUser();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.passwordChange) {
                 Intent intent =new Intent(MainActivity.this,UpdatePassword.class);
                 startActivity(intent);
        }
        if(id==R.id.chat)
        {
            Intent i=new Intent(this, NameActivity.class);
            startActivity(i);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
