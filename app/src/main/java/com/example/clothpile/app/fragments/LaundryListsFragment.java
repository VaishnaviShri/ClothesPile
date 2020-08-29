package com.example.clothpile.app.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clothpile.R;
import com.example.clothpile.app.entity.ClothesList;
import com.example.clothpile.app.helper.ClothesListRecyclerview;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class LaundryListsFragment extends Fragment {

    private Realm myRealm;
    private RecyclerView recyclerView;
    private ClothesListRecyclerview clothesListRecyclerview;
    private static final int SIGN_IN_REQUEST_CODE = 0;
    FloatingActionButton addListFab;

    Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.laundry_lists_fragment, container, false);
        recyclerView = view.findViewById(R.id.my_lists);

        Realm.init(context);

        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();

        addListFab = view.findViewById(R.id.add_fab_button);

        addListFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new AddEditListFragment());
            }
        });

        //Log.v("hello", "HELLLLLLLLLLLLLO VAISH");
        myRealm = Realm.getDefaultInstance();
        displayLists();
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        displayLists();
    }

    public void onResume() {
        super.onResume();
        if (clothesListRecyclerview != null)
            clothesListRecyclerview.notifyDataSetChanged();
        displayLists();
    }

    private void displayLists(){

        RealmResults<ClothesList> realmResults = myRealm.where(ClothesList.class).findAll();
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(context);

        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setHasFixedSize(true);

        clothesListRecyclerview = new ClothesListRecyclerview(context, myRealm, realmResults);
        recyclerView.setAdapter(clothesListRecyclerview);
    }

    private void loadFragment(Fragment fragment){
        AppCompatActivity activity = (AppCompatActivity) context;
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).addToBackStack(null).commit();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        myRealm.close();
    }
}
