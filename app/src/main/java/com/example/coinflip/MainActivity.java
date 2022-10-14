package com.example.coinflip;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity<Botton> extends AppCompatActivity implements View.OnClickListener {

    private ImageView coinView;
    private Button headBtn, tailBtn;
    private TextView outText;
    private Random rnd;

    private AlertDialog.Builder winOrLoose;
    private int coinState = 0;

    private int countOfFlips, countOfLosee, countOfWins;
    Toast toastMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        rnd  = new Random();


        winOrLoose = new AlertDialog.Builder(this);
        winOrLoose.setMessage("Szeretnél új játékot játszani?");

        winOrLoose.setPositiveButton("igen", (dialogInterface, i) -> setGameToDefault());

        winOrLoose.setNegativeButton("nem", (dialogInterface, i) -> finish());

        coinView = findViewById(R.id.coinView);
        headBtn = findViewById(R.id.headBtn);
        tailBtn = findViewById(R.id.tailBtn);
        outText = findViewById(R.id.outText);

        headBtn.setOnClickListener(this);
        tailBtn.setOnClickListener(this);
        setGameToDefault();
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View v) {
        Log.d("test", "text");
        switch (v.getId()) {
            case R.id.headBtn:
                dobas(0);
                Log.d("test", "text");
                break;
            case R.id.tailBtn:
                dobas(1);
                break;
        }
    }


    @SuppressLint("DefaultLocale")
    private void dobas(int tip) {
        int result = rnd.nextInt(2);
        Handler waiter = new Handler();
        dobasAnimete(result, 0);
        waiter.postDelayed(() -> dobasAnimete(result, 1), 500);
        waiter.postDelayed(() -> dobasAnimete(result, 2), 1000);
        waiter.postDelayed(() -> dobasAnimete(result, 3), 1500);
        waiter.postDelayed(() -> dobasAnimete(result, 4), 2000);
        waiter.postDelayed(() -> dobasAnimete(result, 5), 2500);
        waiter.postDelayed(() -> dobasKezelo(result, tip), 2500);

    }

    private void dobasAnimete(int result, int count) {
        if (count == 5) {
            if (result == 0 && coinState != 0) {
                coinView.setImageResource(R.drawable.heads);
            } else if (result == 1 && coinState != 1) {
                coinView.setImageResource(R.drawable.tails);
            }
            coinState = result;
        } else {
            if (coinState == 0) {
                coinView.setImageResource(R.drawable.tails);
                coinState = 1;
            } else {
                coinView.setImageResource(R.drawable.heads);
                coinState = 0;
            }
        }
    }

    private void dobasKezelo(int result, int tip) {
        if (result == tip) {
            countOfWins++;
        } else {
            countOfLosee++;
        }

        countOfFlips++;

        if (result == 0) {
            coinView.setImageResource(R.drawable.heads);
            toastMsg = Toast.makeText(
                    this, "A dobás eredménye: fej", Toast.LENGTH_SHORT
            );
        } else {
            toastMsg = Toast.makeText(
                    this, "A dobás eredménye: írás", Toast.LENGTH_SHORT
            );
            coinView.setImageResource(R.drawable.tails);
        }
        toastMsg.show();
        outText.setText(String.format("Dobások: %d\nGyőzelem: %d\nVereség: %d",
                countOfFlips, countOfWins, countOfLosee
        ));
        if (countOfFlips >= 3) {
            if (countOfWins >= 3) {
                winOrLoose.setTitle("Győztél");
                winOrLoose.show();
            } else if(countOfLosee >= 3) {
                winOrLoose.setTitle("Vesztettél");
                winOrLoose.show();
            }
        }
    }




    private void setGameToDefault() {
        countOfWins = 0;
        countOfLosee = 0;
        countOfFlips = 0;
        coinState = 0;
        outText.setText("Dobások: 0\nGyőzelem: 0\nVereség: 0");
        coinView.setImageResource(R.drawable.heads);
    }

}