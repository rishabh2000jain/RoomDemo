package com.example.roomdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Main2Activity extends AppCompatActivity {
    private static final String NOTE_ADDED ="new_note" ;
    Button button;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        editText  =  findViewById(R.id.edit_text);
        button = findViewById(R.id.button);
        final Intent intent =  new Intent();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().isEmpty())
                {
                   setResult(RESULT_CANCELED,intent);
                }
                else
                {
                    intent.putExtra(NOTE_ADDED,editText.getText().toString());
                    setResult(RESULT_OK,intent);
                }
                finish();

            }
        });
    }
}
