package com.example.pranali.scarnedice;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {


    public static final int WINNING_SCORE = 50;
    public int userOverallScore = 0;
    public int userTurnScore = 0;
    public int compOverallScore = 0;
    public int compTurnScore = 0;
    public int currentDiceValue = 0, currentDiceValue1=0;
    public int n=0;
    public boolean start =false;
    public boolean userTurn = true; // user always starts the game
    public boolean gameOver = false;

    private static int faces[] = {
            R.drawable.one,
            R.drawable.two,
            R.drawable.three,
            R.drawable.four,
            R.drawable.five,
            R.drawable.six
    };

    Button rollButton, holdButton, resetButton;
    ImageView image;
    TextView totalscore, disp, turnscore;
    FloatingActionButton play;
    Animation animation1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rollButton = (Button) findViewById(R.id.rollButton);
        holdButton = (Button) findViewById(R.id.holdButton);
        resetButton = (Button) findViewById(R.id.resetButton);
        totalscore = (TextView) findViewById(R.id.totalscore);
        turnscore = (TextView) findViewById(R.id.turnscore);
        disp = (TextView) findViewById(R.id.disp);
        play = (FloatingActionButton) findViewById(R.id.play);
        image = (ImageView) findViewById(R.id.image);

        animation1 = AnimationUtils.loadAnimation(MainActivity.this,R.anim.rotate);
        animation1.setFillAfter(true);

        if(start==false){
            rollButton.setEnabled(false);
            resetButton.setEnabled(false);
            holdButton.setEnabled(false);
        }

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(MainActivity.this, "The game starts!!", Toast.LENGTH_SHORT).show();

                disp.setText("Your Turn");
                start=true;
                rollButton.setEnabled(true);
                resetButton.setEnabled(true);
                holdButton.setEnabled(true);
                roll();
            }
        });

        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roll();
            }
        });

        holdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hold();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });
    }

        public void roll() {
            if(!gameOver) {
                Random random = new Random();
                currentDiceValue = random.nextInt(6);
                image.startAnimation(animation1);
                image.setImageResource(faces[currentDiceValue]);
                currentDiceValue = currentDiceValue + 1;
                if (currentDiceValue != 1) {
                    if (userTurn) {
                        userTurnScore += currentDiceValue;
                        updateTurnScore(userTurnScore);
                    } else {
                        compTurnScore += currentDiceValue;
                        updateTurnScore(compTurnScore);
                    }

                } else {
                    updateTurnScore(0);
                    changeTurn();
                }
            }
            else
                Toast.makeText(MainActivity.this,"Game Over",Toast.LENGTH_SHORT).show();
        }

            public void hold(){

                userOverallScore+=userTurnScore;
                compOverallScore+=compTurnScore;

                updateScore();

                userTurnScore=0;
                compTurnScore=0;
                updateTurnScore(0);

                changeTurn();
         }

         public void updateScore(){
             totalscore.setText("Your Score: "+userOverallScore+" | "+"Computer Score: "+compOverallScore);
             if(!gameOver) {
                 if (userOverallScore >= WINNING_SCORE) {
                     Toast.makeText(MainActivity.this, "You Win!", Toast.LENGTH_SHORT).show();
                     gameOver = true;
                     reset();
                 } else if (compOverallScore >= WINNING_SCORE) {
                     Toast.makeText(MainActivity.this, "Computer Wins!", Toast.LENGTH_SHORT).show();
                     gameOver = true;
                     reset();
                 }
             }

         }

         public void changeTurn(){
             if(userTurn){
                 disp.setText("Computer's Turn");
                 userTurn=!userTurn;
                 rollButton.setEnabled(false);
                 holdButton.setEnabled(false);
                 compTurn();
             }
             else{
                 disp.setText("Your turn");
                 userTurn=true;
                 rollButton.setEnabled(true);
                 holdButton.setEnabled(true);
             }
         }

         public void updateTurnScore(int k){
             turnscore.setText("Turn Score: "+k);
         }

         public void compTurn(){
             roll();
             Random random1=new Random();
             n=random1.nextInt(11);
             if(n%2==0) {
                 roll();
                 new android.os.Handler().postDelayed(new Runnable() {
                     @Override
                     public void run() {
                         Random random2=new Random();
                         int m=random2.nextInt(5);
                         if(m%2!=0)
                            compTurn();
                         else
                             hold();
                     }
                 },1000);
             }
             else
                 hold();

         }

         public void reset(){
             userOverallScore=0;
             userTurnScore=0;
             compOverallScore=0;
             compTurnScore=0;
             userTurn=true;
             gameOver=false;
             updateTurnScore(0);
             image.setImageResource(R.drawable.one);
             disp.setText("Click on play");
             rollButton.setEnabled(false);
             holdButton.setEnabled(false);
             totalscore.setText("Your score: 0 | Computer Score:0");
             start=false;

         }

}


