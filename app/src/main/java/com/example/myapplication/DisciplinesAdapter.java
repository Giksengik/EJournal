package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.MessageFormat;
import java.util.ArrayList;

public class DisciplinesAdapter extends RecyclerView.Adapter<DisciplinesAdapter.ViewHolder>{
    private final ArrayList<String> disciplines;
    private final DisciplinesAdapter.OnDisciplineClickListener onClickListener;
    private final LayoutInflater inflater;
    public interface OnDisciplineClickListener{
        void onDisciplineClick(String discipline, int position);
    }

    public DisciplinesAdapter(Context context, ArrayList<String> disciplines, DisciplinesAdapter.OnDisciplineClickListener onClickListener){
        this.inflater = LayoutInflater.from(context);
        this.disciplines = disciplines;
        this.onClickListener = onClickListener;
    }
    @NonNull
    public DisciplinesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.discipline_in_list, parent, false);
        return new DisciplinesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String discipline = disciplines.get(position);
      holder.textView.setText(discipline);
      holder.itemView.setOnClickListener(v ->
                onClickListener.onDisciplineClick(discipline, position));
    }

    @Override
    public int getItemCount() {
        return disciplines.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView textView;
        ViewHolder(View v){
            super(v);
            textView = v.findViewById(R.id.textViewDiscipline);
        }
    }
}
