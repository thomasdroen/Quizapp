package no.ntnu.stud.erikfossum.quizapp;

import android.content.Intent;
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

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
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
                if (password.getText().toString().equals(password2.getText().toString())){

                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));;
                }else {
                    Toast toast = Toast.makeText(RegisterActivity.this,"The passwords do not correspond",Toast.LENGTH_LONG);
                    toast.show();
                }

            }
        });
    }

}
