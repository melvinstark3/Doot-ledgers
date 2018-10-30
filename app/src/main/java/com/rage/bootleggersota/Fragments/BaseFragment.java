package com.rage.bootleggersota.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rage.bootleggersota.R;

public class BaseFragment extends Fragment {

    private TextView info;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_base, container, false);
        defineItems(layout);
        setData();
        return  layout;
    }

    private void defineItems (View layout) {
        info = layout.findViewById(R.id.textViewBaseInfo);
    }

    private void setData () {
        if (getArguments() != null) {
            String textInfo = getArguments().getString("info");
            info.setText(textInfo);
        }
    }

}
