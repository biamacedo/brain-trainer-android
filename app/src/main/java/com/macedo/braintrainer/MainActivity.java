package com.macedo.braintrainer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button startButton;
    TextView sumTextView;
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    TextView resultTextView;
    TextView pointsTextView;
    TextView timerTextView;
    Button playAgainButton;

    RelativeLayout gameRelativeLayout;

    ArrayList<Integer> answers = new ArrayList<>();
    ArrayList<String> signs = new ArrayList<>(Arrays.asList("+", "-", "*", "/"));
    int locationOfCorrectAnswer;
    int score = 0;
    int numberOfQuestions = 0;
    int scorePercentage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        startButton = (Button) findViewById(R.id.startButton);
        sumTextView = (TextView) findViewById(R.id.sumTextView);
        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        pointsTextView = (TextView) findViewById(R.id.pointsTextView);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        playAgainButton = (Button) findViewById(R.id.playAgainButton);

        gameRelativeLayout = (RelativeLayout) findViewById(R.id.gameRelativeLayout);

        startButton.setVisibility(View.VISIBLE);
        gameRelativeLayout.setVisibility(View.INVISIBLE);

    }

    public void start(View view){
        startButton.setVisibility(View.INVISIBLE);
        gameRelativeLayout.setVisibility(View.VISIBLE);

        playAgain(playAgainButton);
    }

    public void chooseAnswer(View view){
        Button buttonPressed = (Button) view;

        if (buttonPressed.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))){
            score++;
            resultTextView.setText("Correct!");
        } else {
            resultTextView.setText("Wrong!");
        }
        numberOfQuestions++;
        pointsTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));

        generateQuestion();
    }

    public void generateQuestion(){
        Random rand = new Random();

        int a = rand.nextInt(21);
        int b = rand.nextInt(21);
        int signRandom = rand.nextInt(4);
        String sign = signs.get(signRandom);


        int correctAnswer;
        switch (sign){
            case "+":
                correctAnswer = a + b;
                break;
            case "-":
                correctAnswer = a - b;
                break;
            case "*":
                correctAnswer = a * b;
                break;
            case "/":
                if (b == 0){ b++; }
                correctAnswer = a / b;
                break;
            default:
                correctAnswer = a + b;
                break;
        }

        sumTextView.setText(Integer.toString(a) + " " + sign + " " + Integer.toString(b));

        int incorrectAnswer;

        locationOfCorrectAnswer = rand.nextInt(4);

        answers.clear();

        for (int i = 0; i < 4; i++){
            if (i == locationOfCorrectAnswer){
                answers.add(correctAnswer);
            } else {
                incorrectAnswer = rand.nextInt(41);

                while (incorrectAnswer == correctAnswer){
                    incorrectAnswer = rand.nextInt(41);
                }

                answers.add(incorrectAnswer);
            }
        }

        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
    }

    public String resultMessage() {

        if (scorePercentage < 40) {
            return "You need to practice more to be an math star...";
        } else if (scorePercentage > 80) {
            return "That's great! You are doing well indeed!";
        } else {
            return "With more practice comes more knowledge!";
        }

    }

    public void calculateResulltPercentage(){
        if (numberOfQuestions == 0) {
            scorePercentage = 0;
        } else {
            scorePercentage = ((score * 100) / numberOfQuestions);
        }
    }

    public void playAgain(View view){

        score = 0;
        numberOfQuestions = 0;

        timerTextView.setText("30s");
        pointsTextView.setText("0/0");
        resultTextView.setText("");
        playAgainButton.setVisibility(View.INVISIBLE);

        button0.setEnabled(true);
        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);

        generateQuestion();

        new CountDownTimer(30100, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(String.valueOf(millisUntilFinished / 1000) + "s");
            }

            @Override
            public void onFinish() {
                playAgainButton.setVisibility(View.VISIBLE);

                calculateResulltPercentage();

                timerTextView.setText("0s");
                resultTextView.setText("You got " + scorePercentage + "% correct!\n" + resultMessage());

                button0.setEnabled(false);
                button1.setEnabled(false);
                button2.setEnabled(false);
                button3.setEnabled(false);

            }
        }.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

        return super.onOptionsItemSelected(item);
    }
}
