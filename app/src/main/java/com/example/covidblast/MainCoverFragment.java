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

    public static boolean GAME_STARTED = false;

    final String SETTINGS_FRAGMENT_TAG = "settings_fragment";
    final String UPGRADES_FRAGMENT_TAG = "upgrades_fragment";
    final String BACKGROUND_FRAGMENT_TAG = "background_fragment";
    final String SCORES_FRAGMENTS_TAG = "scores_fragment";
    final String INSTRUCTIONS_TAG = "instructions_fragment";

    SettingsFragment settingsFragment;
    BackgroundFragment backgroundFragment;
    UpgradesFragment upgradesFragment;
    ScoreboardFragment scoreboardFragment;
    InstructionsFragment instructionsFragment;

    Button playBtn, scoresBtn, instructionsBtn;
    ImageButton settingsBtn, backgroundBtn, upgradeBtn;
    FragmentTransaction transaction;
    Fragment mainCoverFragment;

    MainCoverFragment(){
        settingsFragment = new SettingsFragment();
        upgradesFragment = new UpgradesFragment();
        backgroundFragment = new BackgroundFragment();
        scoreboardFragment = new ScoreboardFragment();
        instructionsFragment  = new InstructionsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_cover, container, false);
        mainCoverFragment = getParentFragmentManager().findFragmentByTag("main_cover_fragment");

        backgroundBtn = view.findViewById(R.id.btn_backgrounds);
        settingsBtn = view.findViewById(R.id.btn_settings);
        upgradeBtn = view.findViewById(R.id.btn_upgrades);
        playBtn = view.findViewById(R.id.btn_play);
        scoresBtn = view.findViewById(R.id.btn_scores);
        instructionsBtn = view.findViewById(R.id.btn_instructions);

        view.setOnClickListener(this);
        backgroundBtn.setOnClickListener(this);
        settingsBtn.setOnClickListener(this);
        upgradeBtn.setOnClickListener(this);
        playBtn.setOnClickListener(this);
        scoresBtn.setOnClickListener(this);
        instructionsBtn.setOnClickListener(this);

        return view;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        transaction = getParentFragmentManager().beginTransaction();

        switch (view.getId()){
            case R.id.btn_play:
                if (!scoreboardFragment.isVisible()) {
                    hide_all_sub_fragments();
                    transaction.hide(mainCoverFragment);
                    GAME_STARTED = true;
                    MainActivity.syringe.setVisibility(View.VISIBLE);
                    // need to activate media-player only if settings-music-button is NOT-pressed.
//                    if (!SettingsFragment.soundBtn.isPressed())
                    MainActivity.player.setMusicOnOff(true);
                }
                break;
            case R.id.btn_settings:
                if (!scoreboardFragment.isVisible())
                    manageFrags(settingsFragment, SETTINGS_FRAGMENT_TAG);
                break;
            case R.id.btn_upgrades:
                if (!scoreboardFragment.isVisible())
                    manageFrags(upgradesFragment, UPGRADES_FRAGMENT_TAG);
                break;
            case R.id.btn_backgrounds:
                if (!scoreboardFragment.isVisible())
                    manageFrags(backgroundFragment, BACKGROUND_FRAGMENT_TAG);
                break;
            case R.id.btn_scores:
                if (!instructionsFragment.isVisible())
                    manageFrags(scoreboardFragment, SCORES_FRAGMENTS_TAG);
                else
                    transaction.hide(instructionsFragment);
                break;
            case R.id.btn_instructions:
                manageFrags(instructionsFragment, INSTRUCTIONS_TAG);
                break;
            default:
                transaction.hide(settingsFragment);
                transaction.hide(upgradesFragment);
                transaction.hide(backgroundFragment);
                transaction.hide(instructionsFragment);
                break;
        }
        transaction.commit();
    }

    /**
     * Handles the fragments in 'main_cover_frag'.
     * @param frag The fragment that's currently being used in the app.
     */
    private void manageFrags(Fragment frag, String TAG) {
        if (frag.isVisible()) { transaction.hide(frag); }
        else {
            hide_all_sub_fragments();
            if (!frag.isAdded()) { transaction.add(R.id.main_cover_frag, frag, TAG); }
            transaction.show(frag);
        }
    }

    private void hide_all_sub_fragments() {
        for (Fragment f : getParentFragmentManager().getFragments()) {
            if (!(f instanceof MainCoverFragment))
                transaction.hide(f);
        }
    }

}