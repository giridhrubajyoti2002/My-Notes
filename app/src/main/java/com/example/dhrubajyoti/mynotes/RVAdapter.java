package com.example.dhrubajyoti.mynotes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dhrubajyoti.mynotes.databinding.ListItemBinding;

public class RVAdapter extends ListAdapter<Note, RVAdapter.ViewHolder> {

    MainActivity mainActivity;
    public RVAdapter(MainActivity mainActivity){
        super(CALLBACK);
        this.mainActivity = mainActivity;
    }
    private static final DiffUtil.ItemCallback<Note> CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) && oldItem.getDesc().equals(newItem.getDesc());
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @NonNull ListItemBinding view = ListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = getItem(position);
        holder.binding.title.setText(note.getTitle());
        holder.binding.desc.setText(note.getDesc());
        holder.binding.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainActivity.getApplicationContext(), InsertDataActivity.class)
                        .putExtra("type", "update")
                        .putExtra("title", note.getTitle())
                        .putExtra("desc", note.getDesc())
                        .putExtra("id", note.getId());
                mainActivity.startActivityForResult(intent, 2);
            }
        });
    }
    public Note getNote(int position){
        return getItem(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        ListItemBinding binding;
        public ViewHolder(@NonNull ListItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
