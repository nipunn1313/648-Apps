package com.appdisp.AppDisp;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AppDisp extends Activity {
    
    EditText brightnessText;
    TextView resultText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        brightnessText = (EditText) findViewById(R.id.brightnessText);
        resultText = (TextView) findViewById(R.id.Result);
        
        
        Button setBrightnessButton = (Button) findViewById(R.id.set_brightness_button);
        
        setBrightnessButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setBrightness();
            }
        });
    }

    private void setBrightness() {
        int brightness;
        
        try {
            String pidStr = brightnessText.getText().toString();
            brightness = Integer.parseInt(pidStr);
        } catch (NumberFormatException e) {
            resultText.setText("brightness must be an integer");
            return;
        }
        
        int result = MySyscall.setDisplayBrightness(brightness);
        resultText.setText("setDisplayBrightness returned: " + result);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
