package activity;

import android.os.Bundle;

import com.example.hp.intmarks.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.example.hp.intmarks.R;
import com.example.hp.intmarks.MainActivity;
import com.example.hp.intmarks.AppConfig;
import com.example.hp.intmarks.AppController;
import helper.SQLiteHandler;
import helper.SessionManager;
import com.example.hp.intmarks.AppConfig;
import com.example.hp.intmarks.AppController;
/**
 * Created by HP on 25/02/2017.
 */

public class UpdatePassword extends Activity {
  EditText password;
    EditText confirmpassword;
    Button update;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    public static final String TAG =UpdatePassword.class.getSimpleName() ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);



        password=(EditText)findViewById(R.id.Password);
        confirmpassword=(EditText)findViewById(R.id.Confirmpassword);
        update=(Button)findViewById(R.id.btnUpdate);


        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        session = new SessionManager(getApplicationContext());
        db = new SQLiteHandler(getApplicationContext());


        update.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                //View hView =  navigationView.getHeaderView(0);
                String pass1 = password.getText().toString().trim();
                String pass2 = confirmpassword.getText().toString().trim();

                if (!pass1.isEmpty() && !pass2.isEmpty() && pass1.equals(pass2)) {
                    updatePassword(MainActivity.usn,pass1);
                }
                else{
                    Toast.makeText(getApplicationContext(),"The two passwords does not match",Toast.LENGTH_LONG).show();
                }
            }
        }
    );

    }

    public void updatePassword(final String username,final String password)
    {
        String tag_string_req = "req_login";

        pDialog.setMessage("Updating password ...");
        showDialog();

        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_UPDATE, new Response.Listener<String>() {



            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
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
                       // String name = user.getString("name");
                       // String username = user.getString("username");
                       Toast.makeText(getApplicationContext(),"Password has been updated successfully",Toast.LENGTH_LONG).show();
                        // String created_at = user
                        //.getString("created_at");

                        // Inserting row in users table
                       // db.updatePassword(name, username, newpassword);

                        // Launch main activity
                        Intent intent = new Intent(UpdatePassword.this,
                                MainActivity.class);
                        startActivity(intent);
                        finish();
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
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
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
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }

}

