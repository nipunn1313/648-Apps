/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.lunarlander;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.example.android.lunarlander.LunarView.LunarThread;

/**
 * This is a simple LunarLander activity that houses a single LunarView. It
 * demonstrates...
 * <ul>
 * <li>animating by calling invalidate() from draw()
 * <li>loading and drawing resources
 * <li>handling onPause() in an animation
 * </ul>
 */
public class LunarLander extends Activity {
    private static final int MENU_EASY = 1;

    private static final int MENU_HARD = 2;

    private static final int MENU_MEDIUM = 3;

    private static final int MENU_PAUSE = 4;

    private static final int MENU_RESUME = 5;

    private static final int MENU_START = 6;

    private static final int MENU_STOP = 7;
    
    private static final int MENU_SETTINGS = 8;

    /** A handle to the thread that's actually running the animation. */
    //private LunarThread mLunarThread;

    /** A handle to the View in which the game is running. */
    private LunarView mLunarView;

    private SensorManager mSensorManager;

    private Sensor mSensor;

    /**
     * Invoked during init to give the Activity a chance to set up its Menu.
     *
     * @param menu the Menu to which entries may be added
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        
        MenuItem e;

        e = menu.add(0, MENU_SETTINGS, 0, R.string.menu_settings);
        e.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        
        menu.add(0, MENU_START, 0, R.string.menu_start);
        menu.add(0, MENU_STOP, 0, R.string.menu_stop);
        menu.add(0, MENU_PAUSE, 0, R.string.menu_pause);
        menu.add(0, MENU_RESUME, 0, R.string.menu_resume);
        
        e = menu.add(1, MENU_EASY, 0, R.string.menu_easy);
        e.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        e = menu.add(1, MENU_MEDIUM, 0, R.string.menu_medium);
        e.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        e.setChecked(true);
        e = menu.add(1, MENU_HARD, 0, R.string.menu_hard);
        e.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        
        menu.setGroupCheckable(1, true, true);

        return true;
    }

    /**
     * Invoked when the user selects an item from the Menu.
     *
     * @param item the Menu entry which was selected
     * @return true if the Menu item was legit (and we consumed it), false
     *         otherwise
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_START:
                mLunarView.getThread().doStart();
                return true;
            case MENU_STOP:
                mLunarView.getThread().setState(LunarThread.STATE_LOSE,
                        getText(R.string.message_stopped));
                return true;
            case MENU_PAUSE:
                mLunarView.getThread().pause();
                return true;
            case MENU_RESUME:
                mLunarView.getThread().unpause();
                return true;
            case MENU_EASY:
                mLunarView.getThread().setDifficulty(LunarThread.DIFFICULTY_EASY);
                item.setChecked(true);
                return true;
            case MENU_MEDIUM:
                mLunarView.getThread().setDifficulty(LunarThread.DIFFICULTY_MEDIUM);
                item.setChecked(true);
                return true;
            case MENU_HARD:
                mLunarView.getThread().setDifficulty(LunarThread.DIFFICULTY_HARD);
                item.setChecked(true);
                return true;
            case MENU_SETTINGS:
                mLunarView.getThread().toggleSettings();
                return true;
        }

        return false;
    }

    /**
     * Invoked when the Activity is created.
     *
     * @param savedInstanceState a Bundle containing state saved from a previous
     *        execution, or null if this is a new execution
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // tell system to use the layout defined in our XML file
        setContentView(R.layout.lunar_layout);

        // get handles to the LunarView from XML, and its LunarThread
        mLunarView = (LunarView) findViewById(R.id.lunar);

        Button bL = (Button) findViewById(R.id.leftButton);
        Button bR = (Button) findViewById(R.id.rightButton);
        Button bF = (Button) findViewById(R.id.fireButton);
        Button bS = (Button) findViewById(R.id.startButton);
        RadioButton r1 = (RadioButton) findViewById(R.id.radioOnScreen);
        RadioButton r2 = (RadioButton) findViewById(R.id.radioOrientationSensor);
        RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup1);
        
        Button YEAH = (Button) findViewById(R.id.sendScoreButton);
        Button NAH = (Button) findViewById(R.id.noSendScoreButton);
        EditText NAME = (EditText) findViewById(R.id.nameForm);
        TextView SubmitText = (TextView) findViewById(R.id.submitText);
        Button SubmitButton = (Button) findViewById(R.id.submitButton);
        TextView HighScores = (TextView) findViewById(R.id.highScores);
        TextView YourScore = (TextView) findViewById(R.id.YourScore);
        EditText SERVER = (EditText) findViewById(R.id.serverForm);
        EditText PORT = (EditText) findViewById(R.id.portForm);
        
        View regularLayout = findViewById(R.id.regularLayout);
        View scoresLayout = findViewById(R.id.scoresLayout);
        
        View[] buttons = { bL, bR, bF, bS, YEAH, NAH, SubmitButton };
        
        OnTouchListener listener = new OnTouchListener() {
        	public boolean onTouch(View v, MotionEvent event) {
        		return mLunarView.getThread().onTouch(v, event);
        	}
        };
        OnCheckedChangeListener occl = new OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup rg, int id) {
                mLunarView.getThread().onCheckedChange(id);
            }
        };
        for (View b : buttons) {
            b.setOnTouchListener(listener);
        }
        rg.setOnCheckedChangeListener(occl);
        
        mLunarView.setButtons(bL, bR, bF, bS, r1, r2, 
                YEAH, NAH, NAME, SubmitText, SubmitButton, HighScores,
                regularLayout, scoresLayout, YourScore, SERVER, PORT);
        
        // give the LunarView a handle to the TextView used for messages
        mLunarView.setTextView((TextView) findViewById(R.id.text));
        
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        mSensorManager.registerListener(new SensorEventListener() {
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
            public void onSensorChanged(SensorEvent event) {
                mLunarView.getThread().onSensorChanged(event);
            }
        }, mSensor, SensorManager.SENSOR_DELAY_GAME);

        if (savedInstanceState == null) {
            // we were just launched: set up a new game
            mLunarView.getThread().setState(LunarThread.STATE_READY);
            Log.w(this.getClass().getName(), "SIS is null");
        } else {
            // we are being restored: resume a previous game
            mLunarView.getThread().restoreState(savedInstanceState);
            Log.w(this.getClass().getName(), "SIS is nonnull");
        }
    }

    /**
     * Invoked when the Activity loses user focus.
     */
    @Override
    protected void onPause() {
        super.onPause();
        mLunarView.getThread().pause(); // pause game when Activity pauses
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        super.onTouchEvent(e);
        return mLunarView.getThread().onTouchEvent(e); 
    }

    /**
     * Notification that something is about to happen, to give the Activity a
     * chance to save state.
     *
     * @param outState a Bundle into which this Activity should save its state
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // just have the View's thread save its state into our Bundle
        super.onSaveInstanceState(outState);
        mLunarView.getThread().saveState(outState);
        Log.w(this.getClass().getName(), "SIS called");
    }
}
