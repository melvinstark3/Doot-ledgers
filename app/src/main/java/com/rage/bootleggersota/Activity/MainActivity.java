package com.rage.bootleggersota.Activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rage.bootleggersota.Fragments.BaseFragment;
import com.rage.bootleggersota.Fragments.UpdateFragment;
import com.rage.bootleggersota.R;
import com.rage.bootleggersota.Utils.CheckUpdate;
import com.rage.bootleggersota.Utils.ExecShell;

import java.io.File;

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
        managePermission();

        CheckUpdate checkUpdate = new CheckUpdate();
        if (checkUpdate.isAbleToCheckUpdate()) {
            if (checkUpdate.isUpdateAvailable()) {
                UpdateFragment updateFragment = new UpdateFragment();
                updateFragment.setArguments(checkUpdate.getData());
                replaceFragmentWithAnimation(updateFragment);
            }
            else {
                setBaseFragment();
            }
        }
        else {
            Log.e("TAG", "ERROR");
        }
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

    private void setUpdateFragment () {
        UpdateFragment updateFragment = new UpdateFragment();
        replaceFragmentWithAnimation(updateFragment);
    }

    private void replaceFragmentWithAnimation (Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        transaction.replace(FRAGMENT_CONTAINER_ID, fragment);
        transaction.commit();
    }

    private final int REQUEST_CODE = 123;

    private void managePermission () {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // not granted
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
        }
        else {
            makefolders();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //permission granted
                makefolders();
            }
            else {
                Toast.makeText(getApplicationContext(), "Storage permission is required to manage files!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void makefolders () {
        try {
            File folder = new File(Environment.getExternalStorageDirectory() + "/BootleggersOTA");
            if (!folder.exists())
                if (folder.mkdir()) {
                    Log.e("ta", "");
                }
                else {
                    Log.e("ta", "Startvhvhvhv");
                }

        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Unable to Access Storage!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            finish();
        }
    }

}
