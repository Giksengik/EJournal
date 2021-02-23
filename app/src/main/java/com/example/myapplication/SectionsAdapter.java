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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class SectionsAdapter extends RecyclerView.Adapter<SectionsAdapter.ViewHolder> {
        private final ArrayList<Section> sections;
        private final LayoutInflater inflater;
        private final SectionsAdapter.OnSectionClickListener onClickListener;

        public SectionsAdapter(Context context, ArrayList<Section> sections, SectionsAdapter.OnSectionClickListener onClickListener) {
            this.sections = sections;
            this.inflater = LayoutInflater.from(context);
            this.onClickListener = onClickListener;
        }
        public interface OnSectionClickListener{
            void onSectionClick(Section section, int position);
        }
        @NonNull
        @Override
        public SectionsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.section_in_list, parent, false);
            return new SectionsAdapter.ViewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull SectionsAdapter.ViewHolder holder, int position) {
            Section section = sections.get(position);
            holder.numOfLearnersView.setText(section.learners.size() + "");
            holder.disciplineView.setText(section.name);
            holder.teahcerIDView.setText(section.classTeacher.getFullName());
            holder.itemView.setOnClickListener(v -> onClickListener.onSectionClick(section, position));
        }
        @Override
        public int getItemCount() {
            return sections.size();
        }

    public static class ViewHolder extends RecyclerView.ViewHolder {
            final TextView disciplineView, teahcerIDView, numOfLearnersView;
            ViewHolder(View view){
                super(view);
                disciplineView = (TextView) view.findViewById(R.id.sectionInListDiscipline);
                teahcerIDView = (TextView) view.findViewById(R.id.sectionInListTeacherID);
                numOfLearnersView = (TextView) view.findViewById(R.id.sectionInListNumOfLearners);
            }
        }
    }
