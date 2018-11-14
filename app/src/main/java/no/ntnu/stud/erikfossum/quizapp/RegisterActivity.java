package no.ntnu.stud.erikfossum.quizapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button button= (Button) findViewById(R.id.createNewAccount);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                final EditText password = (EditText) findViewById(R.id.newPassword);
                final EditText password2 = (EditText) findViewById(R.id.confirmNewPassword);
                final EditText userid = (EditText) findViewById(R.id.usernameID);
                if (password.getText().toString().equals(password2.getText().toString())){

                    handler.post(runnableCode);
                    Toast toast = Toast.makeText(RegisterActivity.this,"User "
                            + userid.getText().toString() + " was created",Toast.LENGTH_LONG);
                    toast.show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

                }else {
                    Toast toast = Toast.makeText(RegisterActivity.this,"The passwords do not correspond",Toast.LENGTH_LONG);
                    toast.show();
                }

            }
        });
    }

    Handler handler = new Handler();
    private RequestQueue queue;
    private Runnable runnableCode = new Runnable() {
        @Override
        public void run() {
            if(queue == null){
                queue = Volley.newRequestQueue(RegisterActivity.this);
            }
            final EditText password = (EditText) findViewById(R.id.newPassword);
            final EditText userid = (EditText) findViewById(R.id.usernameID);

            String url ="http://10.0.2.2:8080/QuizServer/api/auth/create?uid=" + userid.getText().toString()
                    + "&pwd=" + password.getText().toString();

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {

                                JSONArray array = new JSONArray(response);
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object = array.optJSONObject(i);
                                    String line = object.optString("userId");
                                    Log.i("user", line);

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
            queue.add(stringRequest);

            // Repeat this the same runnable code block again another 2 seconds
            //handler.postDelayed(runnableCode, 2000);
        }
    };


}
