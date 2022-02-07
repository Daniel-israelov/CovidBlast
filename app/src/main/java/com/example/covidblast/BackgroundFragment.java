package com.example.covidblast;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class BackgroundFragment extends Fragment implements View.OnClickListener{
    ImageButton riverBoat, darkSky, nightSnow, redSunset, retro, river, landscape, space;
    ImageButton island, cartoonSky, deepSea, desert, pyramid, hospital;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_background, container, false);

        assignViewsID(view);
        setListeners();

        return view;
    }

    private void assignViewsID(View v){
        riverBoat = v.findViewById(R.id.river_boat_img_btn);
        darkSky = v.findViewById(R.id.dark_sky_img_btn);
        nightSnow = v.findViewById(R.id.night_snow_img_btn);
        redSunset = v.findViewById(R.id.red_sunset_img_btn);
        retro = v.findViewById(R.id.retro_img_btn);
        river = v.findViewById(R.id.river_img_btn);
        landscape = v.findViewById(R.id.landscape_img_btn);
        space = v.findViewById(R.id.space_img_btn);
        island = v.findViewById(R.id.island_img_btn);
        cartoonSky = v.findViewById(R.id.cartoon_sky_img_btn);
        deepSea = v.findViewById(R.id.deep_sea_img_btn);
        desert = v.findViewById(R.id.desert_img_btn);
        pyramid = v.findViewById(R.id.pyramid_img_btn);
        hospital = v.findViewById(R.id.hospital_img_btn);
    }

    private void setListeners(){
        riverBoat.setOnClickListener(this);
        darkSky.setOnClickListener(this);
        nightSnow.setOnClickListener(this);
        redSunset.setOnClickListener(this);
        retro.setOnClickListener(this);
        river.setOnClickListener(this);
        landscape.setOnClickListener(this);
        space.setOnClickListener(this);
        island.setOnClickListener(this);
        cartoonSky.setOnClickListener(this);
        deepSea.setOnClickListener(this);
        desert.setOnClickListener(this);
        pyramid.setOnClickListener(this);
        hospital.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        FragmentActivity fa = getActivity();
        assert fa!= null;

        switch (view.getId()){
            case R.id.river_boat_img_btn:
                fa.findViewById(R.id.root_container).setBackgroundResource(R.drawable.river_boat);
                break;
            case R.id.dark_sky_img_btn:
                fa.findViewById(R.id.root_container).setBackgroundResource(R.drawable.dark_sky);
                break;
            case R.id.night_snow_img_btn:
                fa.findViewById(R.id.root_container).setBackgroundResource(R.drawable.night_snow);
                break;
            case R.id.red_sunset_img_btn:
                fa.findViewById(R.id.root_container).setBackgroundResource(R.drawable.red_sunset);
                break;
            case R.id.retro_img_btn:
                fa.findViewById(R.id.root_container).setBackgroundResource(R.drawable.retro);
                break;
            case R.id.river_img_btn:
                fa.findViewById(R.id.root_container).setBackgroundResource(R.drawable.river);
                break;
            case R.id.landscape_img_btn:
                fa.findViewById(R.id.root_container).setBackgroundResource(R.drawable.landscape);
                break;
            case R.id.space_img_btn:
                fa.findViewById(R.id.root_container).setBackgroundResource(R.drawable.space);
                break;
            case R.id.island_img_btn:
                fa.findViewById(R.id.root_container).setBackgroundResource(R.drawable.island);
                break;
            case R.id.cartoon_sky_img_btn:
                fa.findViewById(R.id.root_container).setBackgroundResource(R.drawable.cartoon_sky);
                break;
            case R.id.deep_sea_img_btn:
                fa.findViewById(R.id.root_container).setBackgroundResource(R.drawable.deep_sea);
                break;
            case R.id.desert_img_btn:
                fa.findViewById(R.id.root_container).setBackgroundResource(R.drawable.desert);
                break;
            case R.id.pyramid_img_btn:
                fa.findViewById(R.id.root_container).setBackgroundResource(R.drawable.pyramid);
                break;
            case R.id.hospital_img_btn:
                fa.findViewById(R.id.root_container).setBackgroundResource(R.drawable.hospital);
                break;
        }
    }
}