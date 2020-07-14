package com.example.clothpile.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.clothpile.R;
import com.example.clothpile.entity.ClothesList;
import com.example.clothpile.fragments.AddListFragment;
import com.example.clothpile.ui.ClothesListRecyclerview;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private Realm myRealm;
    private RecyclerView recyclerView;
    private ClothesListRecyclerview clothesListRecyclerview;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //super.onCreate();
        Realm.init(this);
        setContentView(R.layout.activity_main);

        myRealm = Realm.getDefaultInstance();
        displayLists();
    }

    protected void onResume() {
        super.onResume();
        if (clothesListRecyclerview != null)
            clothesListRecyclerview.notifyDataSetChanged();
    }

    private void displayLists(){

        RealmResults<ClothesList> realmResults = myRealm.where(ClothesList.class).findAll();

        recyclerView = findViewById(R.id.my_lists);

        mLinearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setHasFixedSize(true);

        clothesListRecyclerview = new ClothesListRecyclerview(this, myRealm, realmResults);
        recyclerView.setAdapter(clothesListRecyclerview);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            /*case R.id.setting_menu:

                FBToast.infoToast(MainActivity.this,
                        "Setting Menu Is Clicked"
                        , FBToast.LENGTH_SHORT);

                break;*/

            case R.id.insert_menu:
                loadFragment(new AddListFragment());
                //startActivity(new Intent(MainActivity.this, AddActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        myRealm.close();
    }
}