package com.example.covidblast;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.transition.TransitionInflater;

public class InstructionsFragment extends Fragment {
    ImageView syringe, hand;
    ImageButton xBtn;
    Animation animation;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater = TransitionInflater.from(getActivity());
        setEnterTransition(inflater.inflateTransition(R.transition.slide_down));
        setExitTransition(inflater.inflateTransition(R.transition.slide_down));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_instructions, container, false);

        syringe = view.findViewById(R.id.syringe_iv);
        hand = view.findViewById(R.id.hand_iv);
        xBtn = view.findViewById(R.id.exit_btn);

        animation = AnimationUtils.loadAnimation(getContext(), R.anim.drag_anim);
        syringe.startAnimation(animation);
        hand.startAnimation(animation);

        return view;
    }
}