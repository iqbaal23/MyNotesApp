package com.example.mynotesapp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mynotesapp.CustomOnItemClickListener;
import com.example.mynotesapp.R;
import com.example.mynotesapp.activity.NoteAddUpdateActivity;
import com.example.mynotesapp.entity.Note;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private ArrayList<Note> listNotes = new ArrayList<>();
    private Activity activity;

    public NoteAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setListNotes(ArrayList<Note> listNotes) {
        if (listNotes.size() > 0){
            this.listNotes.clear();
        }
        this.listNotes.addAll(listNotes);

        notifyDataSetChanged();
    }

    public ArrayList<Note> getListNotes() {
        return listNotes;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_note, viewGroup, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder noteViewHolder, int i) {
        noteViewHolder.tvTitle.setText(listNotes.get(i).getTitle());
        noteViewHolder.tvDate.setText(listNotes.get(i).getDate());
        noteViewHolder.tvDescription.setText(listNotes.get(i).getDescription());
        noteViewHolder.cvNote.setOnClickListener(new CustomOnItemClickListener(i, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int i) {
                Intent intent = new Intent(activity, NoteAddUpdateActivity.class);
                intent.putExtra(NoteAddUpdateActivity.EXTRA_POSITION, i);
                intent.putExtra(NoteAddUpdateActivity.EXTRA_NOTE, listNotes.get(i));
                activity.startActivityForResult(intent, NoteAddUpdateActivity.REQUEST_UPDATE);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return listNotes.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        final TextView tvTitle, tvDescription, tvDate;
        final CardView cvNote;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_item_title);
            tvDescription = itemView.findViewById(R.id.tv_item_description);
            tvDate = itemView.findViewById(R.id.tv_item_date);
            cvNote = itemView.findViewById(R.id.cv_item_note);
        }
    }

    public void addItem(Note note){
        this.listNotes.add(note);
        notifyItemInserted(listNotes.size()-1);
    }

    public void updateItem(int position, Note note){
        this.listNotes.set(position, note);
        notifyItemChanged(position, note);
    }

    public void removeItem(int position){
        this.listNotes.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listNotes.size());
    }
}
