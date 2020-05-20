package com.example.roomdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditTextActivity extends AppCompatActivity {
    EditTextViewModel editTextActivity;
    Bundle bundle;
    LiveData<Note> noteLiveData;
    String noteId;
    EditText editText;
    public static final String NOTE_ID="note_id";
    public static final String NOTE_UPDATE="update_note";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);
        editText = findViewById(R.id.edit_text_activity);
        editTextActivity = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(EditTextViewModel.class);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            noteId = bundle.getString("note_id");
        }
        noteLiveData = editTextActivity.getNoteId(noteId);
        noteLiveData.observe(this, new Observer<Note>() {
            @Override
            public void onChanged(Note note) {
                editText.setText(note.getNote());
            }
        });
    }

    public void setUpdate(View view) {
        Intent intent = new Intent();
        intent.putExtra(NOTE_ID,noteId);
        intent.putExtra(NOTE_UPDATE,editText.getText().toString());
        setResult(RESULT_OK,intent);
        finish();

    }

    public void cancelUpdate(View view) {
        finish();
    }
}
