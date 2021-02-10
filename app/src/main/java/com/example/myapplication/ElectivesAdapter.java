package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.text.MessageFormat;
import java.util.ArrayList;

public class ElectivesAdapter extends RecyclerView.Adapter<ElectivesAdapter.ViewHolder> {
    ArrayList<Elective> electives;
    private final ElectivesAdapter.OnElectivesClickListener onClickListener;
    private final LayoutInflater inflater;
    public ElectivesAdapter(Context context, ArrayList<Elective> electives, ElectivesAdapter.OnElectivesClickListener onClickListener){
        this.inflater = LayoutInflater.from(context);
        this.electives = electives;
        this.onClickListener = onClickListener;
    }
    public interface OnElectivesClickListener{
        void onElectiveClick(Elective currentElective, int position);
    }
    @NonNull
    @Override
    public ElectivesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.elective_in_list, parent, false);
        return new ElectivesAdapter.ViewHolder(view);
    }
    @SuppressLint("SetTextI18n")
    public void onBindViewHolder(@NonNull ElectivesAdapter.ViewHolder holder, int position) {
        Elective elective = electives.get(position);
        holder.iconView.setImageResource(elective.getImageRecourseID());
        holder.subjectView.setText("Subject:  "+ MessageFormat.format("{0}", elective.academicSubject));
        holder.teacherIDView.setText("Teacher: "+ elective.electiveTeacher.getFullName());
        holder.learnersView.setText("Learners: "+ elective.listLearners.size());
        holder.itemView.setOnClickListener(v ->
                onClickListener.onElectiveClick(elective, position));
    }
    @Override
    public int getItemCount() {
        return electives.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView iconView;
        final TextView subjectView, teacherIDView, learnersView;
        ViewHolder(View view){
            super(view);
            iconView = (ImageView)view.findViewById(R.id.electiveInListIcon);
            subjectView = (TextView) view.findViewById(R.id.electiveInListSibject);
            teacherIDView = (TextView) view.findViewById(R.id.electiveInListTeacherID);
            learnersView = (TextView) view.findViewById(R.id.electiveInListNumOfLearners);
        }
    }
}
