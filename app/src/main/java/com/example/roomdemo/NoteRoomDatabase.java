package com.example.roomdemo;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = Note.class,version = 1)
public abstract class NoteRoomDatabase extends RoomDatabase {
   public abstract NoteDao NoteDao();
   private static volatile NoteRoomDatabase noteRoomDatabase;

   static  NoteRoomDatabase getDatabase(final Context context)
   {
       if (noteRoomDatabase == null)
       {
//           synchronized (NoteRoomDatabase.class)
           {
            if (noteRoomDatabase == null)
            {
                noteRoomDatabase = Room.databaseBuilder(context.getApplicationContext(),
                        NoteRoomDatabase.class,
                        "note_database")
                        .build();
            }
           }
       }
       return noteRoomDatabase;
   }
}
