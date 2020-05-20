package com.example.roomdemo;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteActivityAdapter extends RecyclerView.Adapter<NoteActivityAdapter.NoteViewHolder> {
    interface OnDeleteClickListener{
        void onDeleteClickListener(Note note);
    }
    private LayoutInflater layoutInflater;
     Context mcontext;
    List<Note> allNote;
    OnDeleteClickListener onDeleteClickListener;


    public NoteActivityAdapter(Context context,OnDeleteClickListener onDeleteClickListener) {
        layoutInflater = LayoutInflater.from(context);
        this.mcontext = context;
        this.onDeleteClickListener=onDeleteClickListener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        NoteViewHolder viewholder = new NoteViewHolder(view);

        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        if (allNote == null) {
            holder.textView.setText("No Notes");
        } else {
            Note note = allNote.get(position);
            holder.setData(note.getNote(), position);
            holder.setListeners();
        }
    }


    public void setNote(List<Note> allNote) {
        this.allNote = allNote;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (allNote!=null) {
            if (allNote.size() != 0) {
                return allNote.size();
            } else {

                return 0;
            }
        }else
        {
            return 0;
        }
    }

    public  class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView editButton,deleteButton;
        int mPosition;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.txv_row);
            editButton = itemView.findViewById(R.id.edit_button);
            deleteButton = itemView.findViewById(R.id.delete_button);

        }

        public void setData(String note, int position) {

            textView.setText(
                    note
            );
            this.mPosition = position;

        }

        public void setListeners() {
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mcontext,EditTextActivity.class);
                    intent.putExtra("note_id",allNote.get(mPosition).getId());
                    ((Activity)mcontext).startActivityForResult(intent,MainActivity.UPDATE_NOTE_ACTIVITY_RESULT_CODE);
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    onDeleteClickListener.onDeleteClickListener(allNote.get(mPosition));
                }
            });
        }
    }
}
