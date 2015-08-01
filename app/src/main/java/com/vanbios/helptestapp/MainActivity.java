package com.vanbios.helptestapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.vanbios.helptestapp.fragments.FrgDetail;
import com.vanbios.helptestapp.fragments.FrgTopicsList;
import com.vanbios.helptestapp.utils.InfoFromDB;
import com.vanbios.helptestapp.utils.InternetTester;
import com.vanbios.helptestapp.utils.ResultParser;
import com.vanbios.helptestapp.utils.SharedPref;
import com.vanbios.helptestapp.utils.ToastUtils;


public class MainActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {

    private Toolbar toolbar;
    private final int LIST_MODE = 0, DETAIL_MODE = 1;
    private static long backPressExitTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(this);

        setViews();

        SharedPref sharedPref = new SharedPref(this);
        if (!sharedPref.getDataPresentStatus()) {

            if (InternetTester.isConnectionEnabled(this) &&
                    InfoFromDB.getInstance().getDataSource().getData().isEmpty()) {

                ResultParser.sendGetHelpReq(this);
            }
        }
    }

    private void setViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        setToolbar(getString(R.string.app_name), LIST_MODE);
        addFragment(new FrgTopicsList());
    }

    private void setToolbar(String title, int mode) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {

            actionBar.setTitle(title);
            actionBar.setDisplayHomeAsUpEnabled(true);

            switch (mode) {

                case LIST_MODE: {
                    toolbar.setNavigationIcon(R.drawable.ic_menu);
                    break;
                }

                case DETAIL_MODE: {
                    toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
                    break;
                }
            }
        }
    }

    public void addFragment(Fragment f) {
        treatFragment(f, true, false);
    }

    public Fragment getTopFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.fragment_container);
    }

    private void treatFragment(Fragment f, boolean addToBackStack, boolean replace) {
        String tag = f.getClass().getName();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if (replace) {
            ft.replace(R.id.fragment_container, f, tag);
        } else {
            Fragment currentTop = getTopFragment();
            if (currentTop != null) ft.hide(currentTop);
            ft.add(R.id.fragment_container, f, tag);
        }
        if (addToBackStack) ft.addToBackStack(tag);
        ft.commitAllowingStateLoss();
    }

    private void popFragment() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onBackStackChanged() {
        Fragment topFragment = getTopFragment();
        if (topFragment instanceof FrgTopicsList) {
            setToolbar(getString(R.string.app_name), LIST_MODE);
        } else if (topFragment instanceof FrgDetail) {
            setToolbar(((FrgDetail) topFragment).getTitle(), DETAIL_MODE);
        }
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getTopFragment();
        if (fragment instanceof FrgTopicsList) {

            if (backPressExitTime + 2000 > System.currentTimeMillis()) {
                this.finish();

            } else {
                ToastUtils.showClosableToast(this, getString(R.string.press_again_to_exit), 1);
                backPressExitTime = System.currentTimeMillis();
            }
        } else if (fragment instanceof FrgDetail) {
            popFragment();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Fragment fragment = getTopFragment();
                if (fragment instanceof FrgDetail) {
                    popFragment();
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
