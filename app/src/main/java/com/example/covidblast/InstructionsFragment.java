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
    ImageView syringe, hand, bloodDrop;
    Animation animation_dragging, animation_shooting;

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
        bloodDrop = view.findViewById(R.id.blood_drop_iv);

        animation_dragging = AnimationUtils.loadAnimation(getContext(), R.anim.drag_anim);
        animation_shooting = AnimationUtils.loadAnimation(getContext(), R.anim.shoot_anim);

        syringe.startAnimation(animation_dragging);
        hand.startAnimation(animation_dragging);
        bloodDrop.startAnimation(animation_shooting);


        return view;
    }
}