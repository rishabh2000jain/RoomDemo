package com.example.roomdemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements NoteActivityAdapter.OnDeleteClickListener {
    private static final String NOTE_ADDED = "new_note";
    FloatingActionButton floatingActionButton;
    TextView textView;
    NoteViewModel noteViewModel;
    RecyclerView recyclerView;
    NoteActivityAdapter noteActivityAdapter;

    static final int NEW_NOTE_ACTIVITY_RESULT_CODE = 1;
    static final int UPDATE_NOTE_ACTIVITY_RESULT_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatingActionButton = findViewById(R.id.button);
        textView = findViewById(R.id.text);
        recyclerView = findViewById(R.id.recyclerview);
        noteActivityAdapter = new NoteActivityAdapter(this, this);

        recyclerView.setAdapter(noteActivityAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final Intent intent = new Intent(MainActivity.this, Main2Activity.class);
        noteViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(NoteViewModel.class);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(intent, NEW_NOTE_ACTIVITY_RESULT_CODE);
                Log.i(this.getClass().getSimpleName(), "Button Clicked");
            }
        });

        noteViewModel.getAllNote().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                noteActivityAdapter.setNote(notes);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (NEW_NOTE_ACTIVITY_RESULT_CODE == requestCode && resultCode == RESULT_OK) {
            String id = UUID.randomUUID().toString();
            String noteData = data.getStringExtra(NOTE_ADDED);
            Note note = new Note(id, noteData);
            noteViewModel.insert(note);
            Toast.makeText(this, "inserted", Toast.LENGTH_SHORT).show();
        } else if (UPDATE_NOTE_ACTIVITY_RESULT_CODE == requestCode && resultCode == RESULT_OK) {
            //update note
            if (data != null) {
                Note note = new Note(
                        data.getStringExtra(EditTextActivity.NOTE_ID),
                        data.getStringExtra(EditTextActivity.NOTE_UPDATE)
                );
                noteViewModel.updateNote(note);
            }

        } else {

            Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show();


        }

    }

    @Override
    public void onDeleteClickListener(Note note) {
        //delete operation
        noteViewModel.deleteNote(note);
    }
}
