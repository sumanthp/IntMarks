package activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.hp.intmarks.AppConfig;
import com.example.hp.intmarks.AppController;
import com.example.hp.intmarks.MainActivity;
import com.example.hp.intmarks.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import helper.SQLiteHandler;
import helper.SessionManager;

/**
 * Created by HP on 26/02/2017.
 */

public class DisplayMarks extends Activity{
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;

    public static final String TAG =DisplayMarks.class.getSimpleName();

    TextView firstInt;
    TextView secondInt;
    TextView thirdInt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_marks);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        session = new SessionManager(getApplicationContext());
        db = new SQLiteHandler(getApplicationContext());

        firstInt=(TextView)findViewById(R.id.FIMarks);
        secondInt=(TextView)findViewById(R.id.SIMarks);
        thirdInt=(TextView)findViewById(R.id.TIMarks);

        //Intent intent=getIntent();
        // String code=intent.getStringExtra("course");
       //marks(MainActivity.usn,code);


        HashMap<String, String> user = db.getMarks();
        String firstInternals=user.get("F_INT");
        String secondInternals=user.get("S_INT");
        String thirdInternals=user.get("T_INT");
        String quiz=user.get("QUIZ");
        String lab=user.get("LAB");
        String selfStudy=user.get("SELF_STUDY");
        String finalMarks=user.get("FINAL_MARKS");
        String bestoftwo=user.get("BOT");

        firstInt.setText(firstInternals);
        secondInt.setText(secondInternals);
        thirdInt.setText(thirdInternals);
    }

  /*  public void marks(final String username,final String course)
    {
        String tag_string_req = "req_login";

        pDialog.setMessage("Downloading Marks ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_MARKS, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Request Response: " + response.toString());
                hideDialog();

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

                        Toast.makeText(getApplicationContext(),"Marks have been successfully retrieved",Toast.LENGTH_LONG).show();
                        // String created_at = user
                        //.getString("created_at");

                        // Inserting row in users table
                         db.addmarks(USN,subject_code,firstInternals,secondInternals,thirdInternals,quiz,lab,self_study,finalMarks,bestofTwo);

                        // Launch main activity
                        /*Intent intent = new Intent(DisplayMarks.this,
                                MainActivity.class);
                        startActivity(intent);*/
                   /*     finish();
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {*/

         /*   @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Retrieving Marks Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
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
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }*/

  /*  private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }*/
    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {

            Intent intent=new Intent(DisplayMarks.this,MainActivity.class);
        startActivity(intent);

    }


}
