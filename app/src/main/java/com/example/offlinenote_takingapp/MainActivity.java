package com.example.offlinenote_takingapp;

import android.database.Cursor;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
//    private NotesDatabaseHelper dbHelper;
//    private RecyclerView recyclerViewNotes;
//    private FloatingActionButton fabAddNote;
private NotesDatabaseHelper dbHelper;
    private RecyclerView recyclerViewNotes;
    private FloatingActionButton fabAddNote;
    private NoteAdapter noteAdapter;
    private List<String> notesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fabAddNote = findViewById(R.id.fabAddNote);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        loadNotes();
        dbHelper = new NotesDatabaseHelper(this);
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
        fabAddNote = findViewById(R.id.fabAddNote);

        recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this));
        // Adapter setup here (omitted for brevity)

        fabAddNote.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EditNoteActivity.class);
            startActivity(intent);
        });
    }
    // Method to load notes from the database
    private void loadNotes() {
        notesList = new ArrayList<>();
        Cursor cursor = dbHelper.getAllNotes();
        if (cursor.moveToFirst()) {
            do {
                String note = cursor.getString(cursor.getColumnIndexOrThrow("note"));
                notesList.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();

        // Set up the adapter with the notes list
        noteAdapter = new NoteAdapter(notesList);
        recyclerViewNotes.setAdapter(noteAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes(); // Refresh notes when returning to MainActivity
    }
}