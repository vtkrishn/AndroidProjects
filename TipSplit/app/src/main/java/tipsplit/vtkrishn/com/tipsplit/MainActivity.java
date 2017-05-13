package tipsplit.vtkrishn.com.tipsplit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText billAmountEdit = (EditText)findViewById(R.id.editText2);
        final EditText partySizeEdit = (EditText)findViewById(R.id.editText3);

        billAmountEdit.setText("0.0");
        partySizeEdit.setText("0");

        billAmountEdit.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        //billAmountEdit.setText(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        //calculateEverything();
                    }
                }
        );

        SeekBar tipAmountSeek = (SeekBar)findViewById(R.id.seekBar2);
        final TextView tipPercentValue = (TextView) findViewById(R.id.textView12);
        tipAmountSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float decimalProgress = (float) progress/10;
                tipPercentValue.setText(String.valueOf(decimalProgress) + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                calculateEverything();
            }
        });

        partySizeEdit.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        //partySizeEdit.setText(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        //calculateEverything();
                    }
                }
        );
    }

    public void shareText(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        String shareBodyText = "Your shearing message goes here";
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject/Title");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
        startActivity(Intent.createChooser(intent, "Choose sharing method"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBodyText = "Check it out. Your message goes here";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Subject here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
                startActivity(Intent.createChooser(sharingIntent, "Shearing Option"));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static float round(float value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.floatValue();
    }

    public void calculateEverything(){

        EditText billAmountEdit = (EditText)findViewById(R.id.editText2);
        EditText partySizeEdit = (EditText)findViewById(R.id.editText3);

        final TextView tipPercentText = (TextView) findViewById(R.id.textView12);

        TextView tipAmountText = (TextView) findViewById(R.id.textView7);
        TextView totalText = (TextView) findViewById(R.id.textView8);
        TextView eachShareText = (TextView) findViewById(R.id.textView9);


        float billAmount=0;
        int partySize;
        float tipPercent=0;
        float tipAmount;
        float total;
        float eachShare;

        if(billAmountEdit.getText() != null)
        billAmount = Float.valueOf(billAmountEdit.getText().toString());

        if(tipPercentText.getText() != null) {
            String percentNumber = tipPercentText.getText().toString();
            int len = percentNumber.length();
            String percent = percentNumber.substring(0, len - 1);
            tipPercent = Float.valueOf(percent);
        }

        if(billAmount > 0 || tipPercent > 0) {
            tipAmount = round(billAmount * (tipPercent / 100), 2);
            tipAmountText.setText(String.valueOf(tipAmount));
            total = round(billAmount + tipAmount,2);
            totalText.setText(String.valueOf(total));

                if(partySizeEdit.getText() != null) {
                partySize = Integer.valueOf(partySizeEdit.getText().toString());
                if(partySize > 0) {
                    eachShare = round(total / partySize, 2);
                    eachShareText.setText(String.valueOf(eachShare));
                }
            }
        }
    }
}
