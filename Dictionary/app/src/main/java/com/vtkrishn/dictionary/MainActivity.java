package com.vtkrishn.dictionary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<String> items = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        ListView l = (ListView) findViewById(R.id.list);
        l.setAdapter(adapter);

        l.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                items.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
        TextView t = (TextView)findViewById(R.id.editText);
        t.clearFocus();
        t.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                TextView x = ((TextView)v);
                if(x.getText().equals("Add Text"))
                    x.setText("");
                return false;
            }
        });
    }

    public void onTextClick(View view){
        //Button b = (Button)findViewById(R.id.button);
        clearText();
    }

    private void clearText() {
        TextView b = (TextView)findViewById(R.id.editText);
        b.setText(null);
    }

    public void onButtonClick(View view){
        Button b = (Button)findViewById(R.id.button);
        TextView t = (TextView)findViewById(R.id.editText);
        String value = t.getText().toString();
        if(!value.equals("") && !value.contains("Add Text") && !items.contains(value)) {
            items.add(value);
            adapter.notifyDataSetChanged();
            t.setText(R.string.text_add);
        }
    }
}
