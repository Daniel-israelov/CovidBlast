package com.example.covidblast;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class MainCoverFragment extends Fragment implements View.OnClickListener {
    final String SETTINGS_FRAGMENT_TAG = "settings_fragment";
    final String UPGRADES_FRAGMENT_TAG = "upgrades_fragment";
    final String BACKGROUND_FRAGMENT_TAG = "background_fragment";
    final String SCORES_FRAGMENTS_TAG = "scores_fragment";

    SettingsFragment sf;
    BackgroundFragment bgf;
    UpgradesFragment uf;
    ScoreboardFragment sbf;
    BtnListener btnListener;

    Button playBtn, scoresBtn, instructionsBtn;
    ImageButton settingsBtn, backgroundBtn, upgradeBtn;
    FragmentTransaction transaction;

    MainCoverFragment(){
        sf = new SettingsFragment();
        uf = new UpgradesFragment();
        bgf = new BackgroundFragment();
        sbf = new ScoreboardFragment();
        btnListener = new BtnListener();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_cover, container, false);

        backgroundBtn = view.findViewById(R.id.btn_backgrounds);
        settingsBtn = view.findViewById(R.id.btn_settings);
        upgradeBtn = view.findViewById(R.id.btn_upgrades);
        playBtn = view.findViewById(R.id.btn_play);
        scoresBtn = view.findViewById(R.id.btn_scores);
        instructionsBtn = view.findViewById(R.id.btn_instructions);

        backgroundBtn.setOnClickListener(this);
        settingsBtn.setOnClickListener(this);
        upgradeBtn.setOnClickListener(this);

        playBtn.setOnClickListener(btnListener);
        scoresBtn.setOnClickListener(this);
        instructionsBtn.setOnClickListener(btnListener);

        return view;
    }

    private static class BtnListener implements View.OnClickListener{
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_play:
                    //Do something
                    break;
                case R.id.btn_instructions:
                    //Do something
                    break;
            }
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        transaction = getParentFragmentManager().beginTransaction();

        switch (view.getId()){
            case R.id.btn_settings:
                MngFrags(sf, SETTINGS_FRAGMENT_TAG);
                break;
            case R.id.btn_upgrades:
                MngFrags(uf, UPGRADES_FRAGMENT_TAG);
                break;
            case R.id.btn_backgrounds:
                MngFrags(bgf, BACKGROUND_FRAGMENT_TAG);
                break;
            case R.id.btn_scores:
                MngFrags(sbf, SCORES_FRAGMENTS_TAG);
                break;
        }
        transaction.commit();
    }

    /**
     * Handles the fragments in 'main_cover_frag'.
     * @param frag The fragment that's currently being used in the app.
     */
    private void MngFrags(Fragment frag, String TAG) {
        for(Fragment f : getParentFragmentManager().getFragments())
            if(!(f instanceof MainCoverFragment))
                transaction.hide(f);


        if (!frag.isAdded())
            transaction.add(R.id.main_cover_frag, frag, TAG);

        if (!frag.isVisible())
            transaction.show(frag);
    }
}