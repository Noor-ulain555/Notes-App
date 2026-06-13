package com.example.mynotes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynotes.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NoteViewModel noteViewModel;
    private NoteAdapter noteAdapter;

    private ActivityResultLauncher<Intent> addNoteLauncher;
    private ActivityResultLauncher<Intent> updateNoteLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        setupLaunchers();
        setupAdapter();
        setupRecyclerView();
        setupObserver();
        setupSwipeGestures();
        setupFab();
    }

    private void setupLaunchers() {
        addNoteLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    String title = result.getData().getStringExtra("title");
                    String disp = result.getData().getStringExtra("disp");
                    noteViewModel.insert(new note(title, disp));
                    Toast.makeText(this, "Note added", Toast.LENGTH_SHORT).show();
                }
            }
        );

        updateNoteLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    String title = result.getData().getStringExtra("title");
                    String disp = result.getData().getStringExtra("disp");
                    int id = result.getData().getIntExtra("id", -1);
                    if (id != -1) {
                        note updated = new note(title, disp);
                        updated.setId(id);
                        noteViewModel.update(updated);
                        Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        );
    }

    private void setupAdapter() {
        noteAdapter = new NoteAdapter(noteToEdit -> {
            Intent intent = new Intent(this, DataInsertActivity2.class);
            intent.putExtra("type", "update");
            intent.putExtra("title", noteToEdit.getTitle());
            intent.putExtra("disp", noteToEdit.getDisp());
            intent.putExtra("id", noteToEdit.getId());
            updateNoteLauncher.launch(intent);
        });
    }

    private void setupRecyclerView() {
        binding.rv.setAdapter(noteAdapter);
        binding.rv.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupObserver() {
        noteViewModel.getNoteList().observe(this, notes -> {
            noteAdapter.setNotes(notes);
            binding.emptyState.setVisibility(
                (notes == null || notes.isEmpty()) ? View.VISIBLE : View.GONE
            );
        });
    }

    private void setupSwipeGestures() {
        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView rv, RecyclerView.ViewHolder vh, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                if (direction == ItemTouchHelper.RIGHT) {
                    noteViewModel.delete(noteAdapter.getNoteAt(position));
                    Toast.makeText(MainActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();
                } else {
                    note noteToEdit = noteAdapter.getNoteAt(position);
                    Intent intent = new Intent(MainActivity.this, DataInsertActivity2.class);
                    intent.putExtra("type", "update");
                    intent.putExtra("title", noteToEdit.getTitle());
                    intent.putExtra("disp", noteToEdit.getDisp());
                    intent.putExtra("id", noteToEdit.getId());
                    updateNoteLauncher.launch(intent);
                }
            }
        };
        new ItemTouchHelper(callback).attachToRecyclerView(binding.rv);
    }

    private void setupFab() {
        binding.floatingActionButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, DataInsertActivity2.class);
            intent.putExtra("type", "addMode");
            addNoteLauncher.launch(intent);
        });
    }
}