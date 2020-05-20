package com.example.roomdemo;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class EditTextViewModel extends AndroidViewModel {
    NoteRoomDatabase noteRoomDatabase;
    NoteDao noteDao;

    public EditTextViewModel(@NonNull Application application) {
        super(application);
        noteRoomDatabase = NoteRoomDatabase.getDatabase(application);
        noteDao = noteRoomDatabase.NoteDao();
    }
    LiveData<Note> getNoteId(String id)
    {
       return noteDao.getNoteId(id);
    }


}
