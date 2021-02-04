package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.MessageFormat;
import java.util.List;

public class ParticipantAdapter extends RecyclerView.Adapter<ParticipantAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    private final List<Participant> participantList;
    private final OnParticipantClickListener onClickListener;
    interface OnParticipantClickListener{
        void onParticipantClick(Participant participant, int position);
    }
    ParticipantAdapter(Context context, List<Participant> participantList,OnParticipantClickListener onClickListener) {
        this.participantList = participantList;
        this.inflater = LayoutInflater.from(context);
        this.onClickListener = onClickListener;
    }
    @Override
    public ParticipantAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.person_in_list, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ParticipantAdapter.ViewHolder holder, int position) {
        Participant participant = participantList.get(position);
        holder.iconView.setImageResource(participant.getImageRecourse());
        holder.IDView.setText("Card ID: "+MessageFormat.format("{0}", participant.getCardID()));
        holder.nameView.setText("Full name: "+ participant.getFullName());
        holder.statusView.setText(participant.status);
        holder.itemView.setOnClickListener(v ->
                onClickListener.onParticipantClick(participant, position));
    }


    @Override
    public int getItemCount() {
        return participantList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView iconView;
        final TextView nameView, IDView, statusView;
        ViewHolder(View view){
            super(view);
            iconView = (ImageView)view.findViewById(R.id.personInListAvatar);
            nameView = (TextView) view.findViewById(R.id.personInListName);
            IDView = (TextView) view.findViewById(R.id.personInListID);
            statusView = (TextView) view.findViewById(R.id.personInListStatus);
        }
    }
}
