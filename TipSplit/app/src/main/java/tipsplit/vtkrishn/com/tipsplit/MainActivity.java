package tipsplit.vtkrishn.com.tipsplit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainActivity extends AppCompatActivity {
    float billAmount;
    int partySize = 1;
    float tipPercent;
    float tipAmount;
    float total;
    float eachShare;

    private void customizeTitleBar(){
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorAccent));
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(ContextCompat.getColor(this,R.color.colorAccent)));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText billAmountEdit = (EditText)findViewById(R.id.editText4);
        final EditText partySizeEdit = (EditText)findViewById(R.id.editText5);

        customizeTitleBar();

        //Button btn= (Button)findViewById(R.id.btnCapture);

        //btn.setOnClickListener(new View.OnClickListener(){

//            @Override
//            public void onClick(View arg0) {
//                Intent intent = new Intent (android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                //  Intent intent = new Intent (getApplicationContext(),MainActivity2.class);
//
//                //startActivity(intent);
//                startActivityForResult(intent,0);
//
//            }
//
//        });


        billAmountEdit.setText("0.0");
        partySizeEdit.setText("1");

        billAmountEdit.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String str = s.toString();
                        if(str != null && !str.equals("") && str.length() > 0) {
                            billAmount = Float.valueOf(s.toString());
                            if (billAmount > 0) {
                                SeekBar tipAmountSeek = (SeekBar) findViewById(R.id.seekBar2);
                                tipAmountSeek.setEnabled(true);
                                calculateEverything();
                            }
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        String str = s.toString();
                        if(str != null && !str.equals("") && str.length() > 0) {
                            billAmount = Float.valueOf(s.toString());
                            if (billAmount > 0) {
                                SeekBar tipAmountSeek = (SeekBar) findViewById(R.id.seekBar2);
                                tipAmountSeek.setEnabled(true);
                                calculateEverything();
                            }
                        }
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
                if(billAmount == 0)
                    seekBar.setEnabled(false);
                else
                    seekBar.setEnabled(true);
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
                        if(partySize > 0) {
                            calculateEverything();
                        }
                    }
                }
        );
    }

    protected void onActivityResult( int requestCode, int resultCode,Intent data)
    {
        if (requestCode==0)
        {
            Bitmap theimage = (Bitmap) data.getExtras().get("data");
            //viewpict.setImageBitmap(theimage);
        }

    }

    public void startCamera(View view) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI.getPath());
        startActivityForResult(intent, 1);
    }

    public void shareText(View view) {

//        String filelocation="/mnt/sdcard/capture.png";
//        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
//        sharingIntent.setType("vnd.android.cursor.dir/email");
//        String to[] = "user@gmail.com";
//        sharingIntent.putExtra(Intent.EXTRA_EMAIL, to);
//        sharingIntent.putExtra(Intent.EXTRA_STREAM,filelocation);
//        sharingIntent.putExtra(Intent.EXTRA_SUBJECT,"subject");
//        startActivity(Intent.createChooser(sharingIntent, "Send email"));

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

                if(billAmount == 0 || tipAmount ==0 || total == 0){
                    return false;
                }
                else {
                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    StringBuilder stb = new StringBuilder();
                    stb.append("This is the summary of the last party");
                    stb.append("\n");
                    stb.append("Bill Amount :" + billAmount);
                    stb.append("\n");
                    stb.append("Tip Percent :" + tipPercent);
                    stb.append("\n");
                    stb.append("Tip Amount :" + tipAmount);
                    stb.append("\n");
                    stb.append("Total Amount :" + total);
                    stb.append("\n");
                    stb.append("Each Share :" + eachShare);
                    stb.append("\n");
                    stb.append("Hope we will get together soon!. Happy partying");
                    stb.append("\n");
                    stb.append("Created using - Tip Split app");
                    stb.append("https://goo.gl/dXJ1NF");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject here");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, stb.toString());
                    startActivity(Intent.createChooser(sharingIntent, "Shearing Option"));
                    return true;
                }
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

        EditText billAmountEdit = (EditText)findViewById(R.id.editText4);
        EditText partySizeEdit = (EditText)findViewById(R.id.editText5);

        final TextView tipPercentText = (TextView) findViewById(R.id.textView12);

        TextView tipAmountText = (TextView) findViewById(R.id.textView7);
        TextView totalText = (TextView) findViewById(R.id.textView8);
        TextView eachShareText = (TextView) findViewById(R.id.textView9);

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
        else{
            partySize = 1;
        }

    }
}
