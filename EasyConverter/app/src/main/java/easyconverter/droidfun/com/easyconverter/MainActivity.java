package easyconverter.droidfun.com.easyconverter;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.green));
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(ContextCompat.getColor(this,R.color.green)));
    }

    public void click(View view){


        EditText temp = (EditText)findViewById(R.id.editText);
        ToggleButton button = (ToggleButton)findViewById(R.id.toggleButton);
        if(temp.getText() != null) {
            float temperature = Float.valueOf(temp.getText().toString());
            if(button.getText().equals("Celcius")){
                float farenheit = (float)(temperature * 1.8) + 32;
                temp.setText(String.valueOf(farenheit));
                button.setText("Farenheit");
            }
            else{
                float celcius = (float)((temperature - 32) / 1.8);
                temp.setText(String.valueOf(celcius));
                button.setText("Celcius");
            }
        }
    }
}
