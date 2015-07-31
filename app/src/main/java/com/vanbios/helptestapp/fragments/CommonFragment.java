package com.vanbios.helptestapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.vanbios.helptestapp.MainActivity;

import java.util.LinkedList;



public abstract class CommonFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume(){
        super.onResume();
        while(!pendingTransactions.isEmpty()){
            Log.d("COLLIDER", "Executing pending transaction");
            pendingTransactions.pollFirst().run();
        }
    }

    public String getRealTag(){
        return this.getClass().getName();
    }

    protected void addFragment(CommonFragment f){
        ((MainActivity) getActivity()).addFragment(f);
    }

    /*protected void replaceFragment(CommonFragment f){
        ((MainActivity) getActivity()).replaceFragment(f);
    }*/

    protected void finish(){
        tryExecuteTransaction(new Runnable() {
            @Override
            public void run() {
                getFragmentManager().popBackStack();
            }
        });
    }

    protected void popAll(){
        getFragmentManager().popBackStack(1, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public abstract String getTitle();

    private LinkedList<Runnable> pendingTransactions = new LinkedList<>();

    protected void tryExecuteTransaction(Runnable runnable){
        if(isResumed()){
            runnable.run();
        }else{
            Log.d("COLLIDER", "Scheduling pending transaction");
            pendingTransactions.addLast(runnable);
        }
    }

}
