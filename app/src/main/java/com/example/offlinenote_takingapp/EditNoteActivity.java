package com.example.offlinenote_takingapp;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class EditNoteActivity extends AppCompatActivity {
    private NotesDatabaseHelper dbHelper;
    private EditText editTextNote;
    private Button buttonSave;
    private int noteId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        dbHelper = new NotesDatabaseHelper(this);
        editTextNote = findViewById(R.id.editTextNote);
        buttonSave = findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(v -> saveNote());
    }

    private void saveNote() {
        String noteText = editTextNote.getText().toString();
        if (noteId == -1) {
            dbHelper.addNote(noteText);
        } else {
            dbHelper.updateNote(noteId, noteText);
        }
        finish();
    }
}

