package com.example.mynotes;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynotes.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private NoteViewModel noteViewModel;
    private NoteAdapter noteAdapter;

    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
//        cardView=findViewById(R.id.cv);

        RecyclerView recyclerView = binding.rv;
        noteAdapter = new NoteAdapter(new NoteAdapter.OnNoteClickListener() {
//            @Override
//            public void onNoteDeleteClick(int position) {
//                // Handle the click event to delete the item
//                noteViewModel.delete(noteAdapter.getNoteAt(position));
//
//                Toast.makeText(MainActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();
//            }

        },noteViewModel);

            recyclerView.setAdapter(noteAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteViewModel.getNoteList().observe(this, notes -> {
            noteAdapter.setNotes(notes);
        });

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                final int position = viewHolder.getAdapterPosition();
//                View itemView = viewHolder.itemView;
//                CardView cardView = itemView.findViewById(R.id.cv);

                if (direction == ItemTouchHelper.RIGHT) {

                    noteViewModel.delete(noteAdapter.getNoteAt(position));
//                    cardView.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.red));

                    Toast.makeText(MainActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(MainActivity.this, DataInsertActivity2.class);
                    intent.putExtra("type","update");
                    intent.putExtra("title", noteAdapter.getNoteAt(position).getTitle());
                    intent.putExtra("disp", noteAdapter.getNoteAt(position).getDisp());
                    intent.putExtra("id", noteAdapter.getNoteAt(position).getId());
                    startActivityForResult(intent, 2);
//                    Toast.makeText(MainActivity.this, "updating", Toast.LENGTH_SHORT).show();
                }
         }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DataInsertActivity2.class);
                intent.putExtra("type","addMode");
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String title = data.getStringExtra("title");
            String disp = data.getStringExtra("disp");
            note notes = new note(title, disp);
            noteViewModel.insert(notes);
            Toast.makeText(this, "Note added", Toast.LENGTH_SHORT).show();
        } else if (requestCode == 2) {
            String title = data.getStringExtra("title");
            String disp = data.getStringExtra("disp");
            note notes = new note(title, disp);
            notes.setId(data.getIntExtra("id", 0));
            noteViewModel.update(notes);
            Toast.makeText(this, "Note Updated", Toast.LENGTH_LONG).show();
        }
    }
}