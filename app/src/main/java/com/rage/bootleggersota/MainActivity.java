package com.rage.bootleggersota;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.rage.bootleggersota.Fragments.BaseFragment;
import com.rage.bootleggersota.Utils.ExecShell;

public class MainActivity extends AppCompatActivity {

    private final int FRAGMENT_CONTAINER_ID = R.id.frameLayoutFragment;
    private FrameLayout fragmentContainer;
    private TextView txBuildCode, txBuildCodename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        defineLayouts();
        setCodenameAndBuild();
        setBaseFragment();
    }

    private void defineLayouts () {
        fragmentContainer = findViewById(FRAGMENT_CONTAINER_ID);
        txBuildCode = findViewById(R.id.textViewBuildCode);
        txBuildCodename = findViewById(R.id.textViewCodename);
    }


    private void setBaseFragment () {
        BaseFragment fragment = new BaseFragment();
        Bundle data = new Bundle();
        data.putString("info", "Check for maybe?");
        fragment.setArguments(data);
        replaceFragmentWithAnimation(fragment);
    }

    private void setCodenameAndBuild () {
        ExecShell execShell = new ExecShell();
        String codename = execShell.exec("getprop ro.bootleggers.songcodename");
        codename = codename.trim();
        String build = execShell.exec("getprop ro.bootleggers.version_number");
        build = "v"+build.trim();
        txBuildCodename.setText(codename);
        txBuildCode.setText(build);
    }

    private void replaceFragmentWithAnimation (Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        transaction.replace(FRAGMENT_CONTAINER_ID, fragment);
        transaction.commit();
    }
}
