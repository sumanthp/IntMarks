package activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hp.intmarks.R;

/**
 * Created by HP on 25/03/2017.
 */

public class NameActivity extends Activity {
    private Button join;
    private EditText name;

    @Override
    protected void onCreate(Bundle bundleSavedInstances)
    {
        super.onCreate(bundleSavedInstances);
        setContentView(R.layout.activity_name);

        join=(Button)findViewById(R.id.bg_btn_join);
        name=(EditText)findViewById(R.id.name);

        //getActionBar().hide();
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().trim().length()>0)
                {
                    String inputname=name.getText().toString().trim();
                    Intent intent=new Intent(NameActivity.this,WebSocketChat.class);
                    intent.putExtra("name",inputname);


                    startActivity(intent);

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Enter your name",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
