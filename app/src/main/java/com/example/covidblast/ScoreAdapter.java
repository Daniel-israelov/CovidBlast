package com.example.covidblast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class ScoreAdapter extends ArrayAdapter<Score> {
    private final Context mContext;
    private final int mResource;

    public ScoreAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Score> objects, Context mContext) {
        super(context, resource, objects);
        this.mContext = mContext;
        this.mResource = resource;
    }

    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).getUserName();
        String difficulty = getItem(position).getDifficulty();
        int score = getItem(position).getScore();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView nameTv = (TextView) convertView.findViewById(R.id.item_name);
        TextView scoreTv = (TextView) convertView.findViewById(R.id.item_score);
        TextView difficultyTv = (TextView) convertView.findViewById(R.id.item_difficulty);

        nameTv.setText(name);
        scoreTv.setText(score + "");
        difficultyTv.setText(difficulty);

        return convertView;
    }
}
