package no.ntnu.stud.erikfossum.quizapp;


import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
    private int currentQuestion = 0;
    ProgressBar mProgressBar;
    CountDownTimer mCountDownTimer;
    int in=0;

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

                updateQuestions(currentQuestion);
                changeVisibillity();
                mCountDownTimer.start();
                mCountDownTimer.cancel();
                in=0;
                mProgressBar.setProgress(0);
                mCountDownTimer.start();
            }
        });



        mProgressBar=(ProgressBar)findViewById(R.id.progressBar);
        mProgressBar.setProgress(in);
        mCountDownTimer=new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.v("Log_tag", "Tick of Progress"+ in+ millisUntilFinished);
                in++;
                mProgressBar.setProgress((int)in*100/(10000/1000));
            }
            @Override
            public void onFinish() {
                //Do what you want
                in++;
                mProgressBar.setProgress(0);
                currentQuestion = currentQuestion+1;
                if(currentQuestion < questionList.size()) {
                    mCountDownTimer.cancel();
                    updateQuestions(currentQuestion);
                }else {
                    changeVisibilityAfterQuiz();
                    mCountDownTimer.cancel();
                }
            }
        };



        final Button choice1 = (Button) findViewById(R.id.choice1);
        final Button choice2 = (Button) findViewById(R.id.choice2);
        final Button choice3 = (Button) findViewById(R.id.choice3);
        final Button choice4 = (Button) findViewById(R.id.choice4);
        final TextView s = (TextView) findViewById(R.id.score);

        choice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (questionList.get(currentQuestion).getAlternative1()
                        .equals(questionList.get(currentQuestion).getCorrectAnswer())) {
                    score = score + 10;
                    s.setText(""+score);
                    Toast toast = Toast.makeText(QuizActivity.this, "The answer is correct", Toast.LENGTH_SHORT);
                    toast.show();
                    currentQuestion = currentQuestion + 1;
                    if(currentQuestion < questionList.size()) {
                        mCountDownTimer.cancel();
                        updateQuestions(currentQuestion);
                    }else {
                        changeVisibilityAfterQuiz();
                        mCountDownTimer.cancel();
                    }
                } else {
                    Toast toast = Toast.makeText(QuizActivity.this, "The answer is incorrect", Toast.LENGTH_SHORT);
                    toast.show();
                    currentQuestion = currentQuestion + 1;
                    if(currentQuestion < questionList.size()) {
                        mCountDownTimer.cancel();
                        updateQuestions(currentQuestion);
                    }else {
                        changeVisibilityAfterQuiz();
                        mCountDownTimer.cancel();
                    }
                }
            }
        });

        choice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (questionList.get(currentQuestion).getAlternative2()
                        .equals(questionList.get(currentQuestion).getCorrectAnswer())) {
                    score = score + 10;
                    s.setText(""+score);
                    Toast toast = Toast.makeText(QuizActivity.this, "The answer is correct", Toast.LENGTH_SHORT);
                    toast.show();
                    currentQuestion = currentQuestion + 1;
                    if(currentQuestion < questionList.size()) {
                        mCountDownTimer.cancel();
                        updateQuestions(currentQuestion);
                    }else {
                        changeVisibilityAfterQuiz();
                        mCountDownTimer.cancel();
                    }
                } else {
                    Toast toast = Toast.makeText(QuizActivity.this, "The answer is incorrect", Toast.LENGTH_SHORT);
                    toast.show();
                    currentQuestion = currentQuestion + 1;
                    if(currentQuestion < questionList.size()) {
                        mCountDownTimer.cancel();
                        updateQuestions(currentQuestion);
                    }else {
                        changeVisibilityAfterQuiz();
                        mCountDownTimer.cancel();
                    }
                }
            }
        });

        choice3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(questionList.get(currentQuestion).getAlternative3()
                        .equals(questionList.get(currentQuestion).getCorrectAnswer())){
                    score = score + 10;
                    s.setText(""+score);
                    Toast toast = Toast.makeText(QuizActivity.this,"The answer is correct",Toast.LENGTH_SHORT);
                    toast.show();
                    currentQuestion = currentQuestion + 1;
                    if(currentQuestion < questionList.size()) {
                        mCountDownTimer.cancel();
                        updateQuestions(currentQuestion);
                    }else {
                        changeVisibilityAfterQuiz();
                        mCountDownTimer.cancel();
                    }
                } else {
                    Toast toast = Toast.makeText(QuizActivity.this,"The answer is incorrect",Toast.LENGTH_SHORT);
                    toast.show();
                    currentQuestion = currentQuestion + 1;
                    if(currentQuestion < questionList.size()) {
                        mCountDownTimer.cancel();
                        updateQuestions(currentQuestion);
                    }else {
                        changeVisibilityAfterQuiz();
                        mCountDownTimer.cancel();
                    }
                }
            }
        });

        choice4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(questionList.get(currentQuestion).getAlternative4()
                        .equals(questionList.get(currentQuestion).getCorrectAnswer())){
                    score = score + 10;
                    s.setText(""+score);
                    Toast toast = Toast.makeText(QuizActivity.this,"The answer is correct",Toast.LENGTH_SHORT);
                    toast.show();
                    currentQuestion = currentQuestion + 1;
                    if(currentQuestion < questionList.size()) {
                        mCountDownTimer.cancel();
                        updateQuestions(currentQuestion);
                    }else {
                        changeVisibilityAfterQuiz();
                        mCountDownTimer.cancel();
                    }
                } else {
                    Toast toast = Toast.makeText(QuizActivity.this,"The answer is incorrect",Toast.LENGTH_SHORT);
                    toast.show();
                    currentQuestion = currentQuestion + 1;
                    if(currentQuestion < questionList.size()) {
                        mCountDownTimer.cancel();
                        updateQuestions(currentQuestion);
                    }else {
                        changeVisibilityAfterQuiz();
                        mCountDownTimer.cancel();
                    }
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
        }
    };

    public void updateQuestions(int i) {
        final Button choice1 = (Button) findViewById(R.id.choice1);
        final Button choice2 = (Button) findViewById(R.id.choice2);
        final Button choice3 = (Button) findViewById(R.id.choice3);
        final Button choice4 = (Button) findViewById(R.id.choice4);
        final TextView q = (TextView) findViewById(R.id.question);

        choice1.setText(questionList.get(i).getAlternative1());
        choice2.setText(questionList.get(i).getAlternative2());
        choice3.setText(questionList.get(i).getAlternative3());
        choice4.setText(questionList.get(i).getAlternative4());
        q.setText(questionList.get(i).getQuestion());
        in=0;
        mProgressBar.setProgress(0);
        mCountDownTimer.start();

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

    public void changeVisibilityAfterQuiz(){
        Button startButton = (Button) findViewById(R.id.startButton);
        final Button choice1 = (Button) findViewById(R.id.choice1);
        final Button choice2 = (Button) findViewById(R.id.choice2);
        final Button choice3 = (Button) findViewById(R.id.choice3);
        final Button choice4 = (Button) findViewById(R.id.choice4);
        final TextView q = (TextView) findViewById(R.id.question);
        final TextView s = (TextView) findViewById(R.id.score);
        startButton.setVisibility(View.VISIBLE);
        choice1.setVisibility(View.GONE);
        choice2.setVisibility(View.GONE);
        choice3.setVisibility(View.GONE);
        choice4.setVisibility(View.GONE);
        q.setText("Your final score: " + score + "/" + (questionList.size()*10));
        currentQuestion = 0;
        score = 0;
        s.setText("" + score);
        mProgressBar.setProgress(0);
    }

}

