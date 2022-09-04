package com.example.dhrubajyoti.mynotes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.dhrubajyoti.mynotes.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private NoteViewModel noteViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        noteViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getApplication())).get(NoteViewModel.class);

        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InsertDataActivity.class).putExtra("type", "add");
                startActivityForResult(intent, 1);

            }
        });

        binding.RV.setLayoutManager(new LinearLayoutManager(this));
        binding.RV.setHasFixedSize(true);
        RVAdapter adapter = new RVAdapter(MainActivity.this);
        binding.RV.setAdapter(adapter);

        noteViewModel.getAllData().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.submitList(notes);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Note note = adapter.getNote(viewHolder.getAdapterPosition());
                if(direction == ItemTouchHelper.RIGHT){
                    Intent intent = new Intent(MainActivity.this, InsertDataActivity.class)
                            .putExtra("type", "update")
                            .putExtra("title", note.getTitle())
                            .putExtra("desc", note.getDesc())
                            .putExtra("id", note.getId());
                    startActivityForResult(intent, 2);
                }else {
                    noteViewModel.delete(note);
                    Toast.makeText(MainActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();
                }
            }
        }).attachToRecyclerView(binding.RV);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            String title = data.getStringExtra("title");
            String desc = data.getStringExtra("desc");
            Note note = new Note(title, desc);
            noteViewModel.insert(note);
            Toast.makeText(this, "Note added", Toast.LENGTH_SHORT).show();
        }else if(requestCode == 2){
            String title = data.getStringExtra("title");
            String desc = data.getStringExtra("desc");
            Note note = new Note(title, desc);
            note.setId(data.getIntExtra("id", 0));
            noteViewModel.update(note);
            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();
        }
    }
}