package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.MessageFormat;
import java.util.ArrayList;

public class ClassesAdapter extends RecyclerView.Adapter<ClassesAdapter.ViewHolder> {

    private ArrayList<Class> classes;
    private final OnClassClickListener onClickListener;
    private final LayoutInflater inflater;
    public interface OnClassClickListener{
        void onClassClick(Class currentClass, int position);
    }

    public ClassesAdapter(Context context, ArrayList<Class> classes,OnClassClickListener onClickListener){
        this.inflater = LayoutInflater.from(context);
        this.classes = classes;
        this.onClickListener = onClickListener;
    }
    public ClassesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.class_in_list, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ClassesAdapter.ViewHolder holder, int position) {
        Class currentClass = classes.get(position);
        holder.classImage.setImageResource(currentClass.getImageRecourseID());
        holder.classNumber.setText("Class Number: "+ MessageFormat.format("{0}", currentClass.number));
        holder.classTeacher.setText("Teacher: "+ currentClass.classTeacher.getFullName());
        holder.classNumOfLearners.setText("Learners: "+ currentClass.learnersList.size());
        holder.itemView.setOnClickListener(v ->
                onClickListener.onClassClick(currentClass, position));
    }

    @Override
    public int getItemCount() {
        return classes.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView classImage;
        final TextView classNumber, classTeacher, classNumOfLearners;
        ViewHolder(View v){
            super(v);
            classImage = v.findViewById(R.id.classInRecyclerViewImage);
            classNumber = v.findViewById(R.id.classInRecyclerViewNumber);
            classNumOfLearners = v.findViewById(R.id.classInRecyclerViewNumOfLearners);
            classTeacher = v.findViewById(R.id.classInRecyclerViewTeacher);
        }

    }
}
