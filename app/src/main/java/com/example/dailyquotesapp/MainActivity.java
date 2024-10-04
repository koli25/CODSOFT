package com.example.dailyquotesapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todolistapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView quoteTextView;
    private List<String> quotes;
    private String currentQuote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quoteTextView = findViewById(R.id.quoteTextView);
        Button shareButton = findViewById(R.id.shareButton);
        Button favoriteButton = findViewById(R.id.favoriteButton);

        // Initialize quotes list
        quotes = new ArrayList<>();
        quotes.add("The only limit to our realization of tomorrow is our doubts of today.");
        quotes.add("The future belongs to those who believe in the beauty of their dreams.");
        quotes.add("Believe you can and you're halfway there.");
        // Add more quotes as needed

        // Display the daily quote
        displayDailyQuote();

        // Set up Share button functionality
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareQuote();
            }
        });

        // Set up Favorite button functionality
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFavoriteQuote();
            }
        });
    }

    private void displayDailyQuote() {
        Collections.shuffle(quotes); // Shuffle quotes to get a random one
        currentQuote = quotes.get(0);
        quoteTextView.setText(currentQuote);
    }

    private void shareQuote() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, currentQuote);
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "Share quote via"));
    }

    private void saveFavoriteQuote() {
        // Implement saving favorite quote logic (e.g., using SharedPreferences or a database)
        // For demonstration, we'll just display a Toast message
        // Toast.makeText(this, "Favorite quote saved!", Toast.LENGTH_SHORT).show();
       // SharedPreferences sharedPreferences = getSharedPreferences("FavoriteQuotes", MODE_PRIVATE);
       // SharedPreferences.Editor editor = sharedPreferences.edit();
       // editor.putString("favoriteQuote", currentQuote);
       // editor.apply();
        Toast.makeText(this, "Favorite quote saved!", Toast.LENGTH_SHORT).show();
    }

//    SharedPreferences sharedPreferences = getSharedPreferences("FavoriteQuotes", MODE_PRIVATE);
//    String favoriteQuote = sharedPreferences.getString("favoriteQuote", "No favorite quote found");

}


