package activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import com.example.hp.intmarks.TripleArrayAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import helper.SQLiteHandler;
import helper.SessionManager;



/**
 * Created by HP on 26/02/2017.
 */

public class DisplayMarks extends AppCompatActivity{
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;

    public static final String TAG =DisplayMarks.class.getSimpleName();

    TextView firstInt;
    TextView secondInt;
    TextView thirdInt;

    @Override
    public void onCreate(Bundle savedInstanceState) {

      //  getActionBar().show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_marks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        session = new SessionManager(getApplicationContext());
        db = new SQLiteHandler(getApplicationContext());
        String[] marks;

        String TypeOfMarks[]=new String[]{"First Internals","Second Internals","Third Internals","Quiz","Lab","Self Study","Total","Best Of Two"};

        //ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.item_marks,R.id.type,TypeOfMarks);
        ListView listView=(ListView)findViewById(R.id.marks);
        //listView.setAdapter(adapter);

        Intent intent=getIntent();
        String coursecode= intent.getStringExtra("courseId");
       //marks=new String[]{"10","20","30","40","50","60","70","80","90"};
        HashMap<String, String> user = db.getMarks(coursecode);

           int size=user.size();
           System.out.println(size);

          if(!(size>0)) {
            Toast.makeText(this, "Marks of subject " + coursecode + "are not available,try again later", Toast.LENGTH_SHORT).show();
            Intent i=new Intent(DisplayMarks.this,MainActivity.class);
            startActivity(i);
            finish();
           }
        else {
            marks = new String[user.size()];

            marks[0] = user.get("F_INT");
            marks[1] = user.get("S_INT");
            marks[2] = user.get("T_INT");
            marks[3] = user.get("QUIZ");
            marks[4] = user.get("LAB");
            marks[5] = user.get("S_STUDY");
            marks[6] = user.get("FINAL_MARKS");
            marks[7] = user.get("BOT");

            listView.setAdapter(new TripleArrayAdapter(this, TypeOfMarks, marks));

        /*    String firstInternals = user.get("F_INT");
            String secondInternals = user.get("S_INT");
            String thirdInternals = user.get("T_INT");
            String quiz = user.get("QUIZ");
            String lab = user.get("LAB");
            String selfStudy = user.get("SELF_STUDY");
            String finalMarks = user.get("FINAL_MARKS");
            String bestoftwo = user.get("BOT");
            */
        }
    }

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
