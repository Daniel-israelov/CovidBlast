package com.example.covidblast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

public class ScoreAdapter extends BaseAdapter {
    List<Score> scores;
    private Context context;

    public ScoreAdapter(List<Score> scores, Context context) {
        this.scores = scores;
        this.context = context;
    }

    @Override
    public int getCount() {
        return scores.size();
    }

    @Override
    public Object getItem(int i) {
        return scores.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null)
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_view_item, viewGroup, false);

        Score score = scores.get(i);

        TextView nameTV = view.findViewById(R.id.item_name);
        TextView scoreTV = view.findViewById(R.id.item_score);
        TextView diffTV = view.findViewById(R.id.item_difficulty);

        nameTV.setText(score.getUserName());
        scoreTV.setText(score.getScore());
        diffTV.setText(score.getDifficulty());

        return view;
    }
}
