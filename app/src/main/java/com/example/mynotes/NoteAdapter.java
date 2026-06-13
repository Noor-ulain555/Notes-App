package com.example.mynotes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<note> noteList = new ArrayList<>();
    private final OnNoteClickListener listener;

    public NoteAdapter(OnNoteClickListener listener) {
        this.listener = listener;
    }

    public void setNotes(List<note> notes) {
        this.noteList = notes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_rv, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bind(noteList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public note getNoteAt(int position) {
        return noteList.get(position);
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTextView;
        private final TextView descriptionTextView;
        private final ImageView editIcon;

        NoteViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titlerv);
            descriptionTextView = itemView.findViewById(R.id.discription);
            editIcon = itemView.findViewById(R.id.update);
        }

        void bind(note note, OnNoteClickListener listener) {
            titleTextView.setText(note.getTitle());
            descriptionTextView.setText(note.getDisp());
            itemView.setOnClickListener(v -> {
                if (listener != null) listener.onNoteClick(note);
            });
            editIcon.setOnClickListener(v -> {
                if (listener != null) listener.onNoteClick(note);
            });
        }
    }

    public interface OnNoteClickListener {
        void onNoteClick(note note);
    }
}