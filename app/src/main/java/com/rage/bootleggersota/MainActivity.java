package com.rage.bootleggersota;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.rage.bootleggersota.Fragments.BaseFragment;

public class MainActivity extends AppCompatActivity {

    private final int FRAGMENT_CONTAINER_ID = R.id.frameLayoutFragment;
    private FrameLayout fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BaseFragment fragment = new BaseFragment();
        Bundle data = new Bundle();
        data.putString("info", "Check for updates?");
        fragment.setArguments(data);
        replaceFragmentWithAnimation(fragment);
    }

    private void defineLayouts () {
        fragmentContainer = findViewById(FRAGMENT_CONTAINER_ID);
    }

    private void replaceFragmentWithAnimation (Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        transaction.replace(FRAGMENT_CONTAINER_ID, fragment);
        transaction.commit();
    }
}
