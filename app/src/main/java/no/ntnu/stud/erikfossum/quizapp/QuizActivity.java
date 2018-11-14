package no.ntnu.stud.erikfossum.quizapp;


import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private String category;
    private String alternative1;
    private String alternative2;
    private String alternative3;
    private String alternative4;
    private String correctAnswer;
    private String question;
    private int score = 0;

    final List<Questions> questionList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Intent i = getIntent();
        category = i.getStringExtra("category");

        handler.post(runnableCode);
        Button startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateQuestions();
                changeVisibillity();

            }
        });


        final Button choice1 = (Button) findViewById(R.id.choice1);
        final Button choice2 = (Button) findViewById(R.id.choice2);
        final Button choice3 = (Button) findViewById(R.id.choice3);
        final Button choice4 = (Button) findViewById(R.id.choice4);
        final TextView s = (TextView) findViewById(R.id.score);

        choice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (alternative1.equals(correctAnswer)) {
                    score = score + 10;
                    s.setText(""+score);
                    Toast toast = Toast.makeText(QuizActivity.this, "The answer is correct", Toast.LENGTH_SHORT);
                    toast.show();
                    updateQuestions();
                } else {
                    Toast toast = Toast.makeText(QuizActivity.this, "The answer is incorrect", Toast.LENGTH_SHORT);
                    toast.show();
                    updateQuestions();
                }
            }
        });

        choice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (alternative2.equals(correctAnswer)) {
                    score = score + 10;
                    s.setText(""+score);
                    Toast toast = Toast.makeText(QuizActivity.this, "The answer is correct", Toast.LENGTH_SHORT);
                    toast.show();
                    updateQuestions();
                } else {
                    Toast toast = Toast.makeText(QuizActivity.this, "The answer is incorrect", Toast.LENGTH_SHORT);
                    toast.show();
                    updateQuestions();
                }
            }
        });

        choice3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(alternative3.equals(correctAnswer)){
                    score = score + 10;
                    s.setText(""+score);
                    Toast toast = Toast.makeText(QuizActivity.this,"The answer is correct",Toast.LENGTH_SHORT);
                    toast.show();
                    updateQuestions();
                } else {
                    Toast toast = Toast.makeText(QuizActivity.this,"The answer is incorrect",Toast.LENGTH_SHORT);
                    toast.show();
                    updateQuestions();
                }
            }
        });

        choice4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(alternative4.equals(correctAnswer)){
                    score = score + 10;
                    s.setText(""+score);
                    Toast toast = Toast.makeText(QuizActivity.this,"The answer is correct",Toast.LENGTH_SHORT);
                    toast.show();
                    updateQuestions();
                } else {
                    Toast toast = Toast.makeText(QuizActivity.this,"The answer is incorrect",Toast.LENGTH_SHORT);
                    toast.show();
                    updateQuestions();
                }
            }
        });
    }

    Handler handler = new Handler();
    private RequestQueue queue;
    private Runnable runnableCode = new Runnable() {
        @Override
        public void run() {
            if (queue == null) {
                queue = Volley.newRequestQueue(QuizActivity.this);
            }
            String url = "http://10.0.2.2:8080/QuizServer/api/quiz/getQuestions?category=" + category;

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {

                                JSONArray array = new JSONArray(response);
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object = array.optJSONObject(i);
                                    alternative1 = object.optString("alternative1");
                                    alternative2 = object.optString("alternative2");
                                    alternative3 = object.optString("alternative3");
                                    alternative4 = object.optString("alternative4");
                                    correctAnswer = object.optString("correctAnswer");
                                    question = object.optString("question");
                                    Questions q = new Questions(alternative1, alternative2, alternative3,
                                            alternative4, correctAnswer, question);
                                    questionList.add(q);
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

    public void updateQuestions() {
        final Button choice1 = (Button) findViewById(R.id.choice1);
        final Button choice2 = (Button) findViewById(R.id.choice2);
        final Button choice3 = (Button) findViewById(R.id.choice3);
        final Button choice4 = (Button) findViewById(R.id.choice4);
        final TextView q = (TextView) findViewById(R.id.question);

        choice1.setText(questionList.get(0).getAlternative1());
        choice2.setText(questionList.get(0).getAlternative2());
        choice3.setText(questionList.get(0).getAlternative3());
        choice4.setText(questionList.get(0).getAlternative4());
        q.setText(questionList.get(0).getQuestion());
    }

    public void changeVisibillity() {
        Button startButton = (Button) findViewById(R.id.startButton);
        final Button choice1 = (Button) findViewById(R.id.choice1);
        final Button choice2 = (Button) findViewById(R.id.choice2);
        final Button choice3 = (Button) findViewById(R.id.choice3);
        final Button choice4 = (Button) findViewById(R.id.choice4);
        final TextView q = (TextView) findViewById(R.id.question);
        startButton.setVisibility(View.GONE);
        choice1.setVisibility(View.VISIBLE);
        choice2.setVisibility(View.VISIBLE);
        choice3.setVisibility(View.VISIBLE);
        choice4.setVisibility(View.VISIBLE);
        q.setVisibility(View.VISIBLE);
    }


}

