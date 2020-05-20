package com.example.roomdemo;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    NoteDao noteDao;
    NoteRoomDatabase noteRoomDatabase;
    LiveData<List<Note>> allNote;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        noteRoomDatabase = NoteRoomDatabase.getDatabase(application);
        noteDao = noteRoomDatabase.NoteDao();
        allNote = noteDao.getAllNotes();
    }

    public void insert(Note note) {
        new InsertAsyncTask(noteDao).execute(note);

    }

    void updateNote(Note note) {
        new UpdateAsyncTask(noteDao).execute(note);
    }

    void deleteNote(Note note) {
        new DeleteAsyncTask(noteDao).execute(note);
    }

    LiveData<List<Note>> getAllNote() {
        return allNote;
    }

    private static class InsertAsyncTask extends AsyncTask<Note, Void, Void> {
        NoteDao noteDao;

        public InsertAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }


        @Override
        protected Void doInBackground(Note... note) {
            noteDao.insert(note[0]);
            return null;
        }
    }

    private class UpdateAsyncTask extends AsyncTask<Note, Void, Void> {
        NoteDao noteDao;

        public UpdateAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.updateNote(notes[0]);
            return null;
        }
    }

    private class DeleteAsyncTask extends AsyncTask<Note, Void, Void> {
        NoteDao noteDao;

        public DeleteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.deleteNote(notes[0]);
            return null;
        }
    }
}
