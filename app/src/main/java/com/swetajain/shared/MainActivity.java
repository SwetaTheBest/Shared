package com.swetajain.shared;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //current count
    private int mCount = 0;
    // Current background color
    private int mColor;
    // Text view to display both count and color
    private TextView mShowCountTextView;

    // Key for current count
    private final String COUNT_KEY = "count";
    // Key for current color
    private final String COLOR_KEY = "color";
    //shared preferences
    private SharedPreferences mSharedPreferences;
    private String sharedPrefsFile = "com.swetajain.shared";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views, color
        mShowCountTextView = findViewById(R.id.count_textview);
        mColor = ContextCompat.getColor(this,
                R.color.default_background);

        mSharedPreferences = getSharedPreferences(sharedPrefsFile,MODE_PRIVATE);
        mCount = mSharedPreferences.getInt(COUNT_KEY,0);
        mShowCountTextView.setText(String.valueOf(mCount));

        mColor = mSharedPreferences.getInt(COLOR_KEY,0);
        mShowCountTextView.setBackgroundColor(mColor);

        // Restore the saved instance state.
//        if (savedInstanceState != null) {
//
//            mCount = savedInstanceState.getInt(COUNT_KEY);
//            if (mCount != 0) {
//                mShowCountTextView.setText(String.format("%s", mCount));
//            }
//
//            mColor = savedInstanceState.getInt(COLOR_KEY);
//            mShowCountTextView.setBackgroundColor(mColor);
//        }
    }

    /**
     * Handles the onClick for the background color buttons. Gets background
     * color of the button that was clicked, and sets the TextView background
     * to that color.
     *
     * @param view The view (Button) that was clicked.
     */
    public void changeBackground(View view) {
        int color = ((ColorDrawable) view.getBackground()).getColor();
        mShowCountTextView.setBackgroundColor(color);
        mColor = color;
    }

    /**
     * Handles the onClick for the Count button. Increments the value of the
     * mCount global and updates the TextView.
     *
     * @param view The view (Button) that was clicked.
     */
    public void countUp(View view) {
        mCount++;
        mShowCountTextView.setText(String.format("%s", mCount));
    }

    /**
     * Saves the instance state if the activity is restarted (for example,
     * on device rotation.) Here you save the values for the count and the
     * background color.
     *
     * @param outState The state data.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(COUNT_KEY, mCount);
        outState.putInt(COLOR_KEY, mColor);
    }

    /**
     * Handles the onClick for the Reset button. Resets the global count and
     * background variables to the defaults and resets the views to those
     * default values.
     *
     * @param view The view (Button) that was clicked.
     */
    public void reset(View view) {
        // Reset count
        mCount = 0;
        mShowCountTextView.setText(String.format("%s", mCount));

        // Reset color
        mColor = ContextCompat.getColor(this,
                R.color.default_background);
        mShowCountTextView.setBackgroundColor(mColor);

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.clear();
        editor.apply();
        
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor preferenceEditor = mSharedPreferences.edit();
        preferenceEditor.putInt(COLOR_KEY,mColor);
        preferenceEditor.putInt(COUNT_KEY,mCount);
        preferenceEditor.apply();
    }

}