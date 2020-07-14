package com.example.clothpile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import com.example.clothpile.R;
import com.example.clothpile.entity.ClothesList;

import androidx.fragment.app.Fragment;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmAsyncTask;

public class AddListFragment extends Fragment {

    private EditText roomNumberET, totalClothesET;
    Button addListButton;
    private Realm myRealm;
    private RealmAsyncTask realmAsyncTask;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.add_list_fragment, container, false);
        roomNumberET = view.findViewById(R.id.room_number_edit_text);
        totalClothesET = view.findViewById(R.id.total_clothes_edit_text);
        addListButton = view.findViewById(R.id.add_list);

        addListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertRecords();
            }
        });

        myRealm = Realm.getDefaultInstance();

        return view;
    }

    private void insertRecords() {

        final String id = UUID.randomUUID().toString();

        final String roomNumber = roomNumberET.getText().toString().trim();
        final int totalClothes = Integer.parseInt(totalClothesET.getText().toString().trim());

        if(roomNumber.isEmpty()||totalClothes<=0){
            Toast.makeText(getContext(), "Fields empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        realmAsyncTask = myRealm.executeTransactionAsync(
                new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {

                        ClothesList clothesList = realm.createObject(ClothesList.class, id);
                        clothesList.setRoomNumber(roomNumber);
                        clothesList.setTotalClothes(totalClothes);
                        clothesList.calculateBill();

                    }
                },
                new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getContext(), "List added", Toast.LENGTH_SHORT).show();
                        roomNumberET.setText("");
                        totalClothesET.setText("");
                    }
                },
                new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        Toast.makeText(getContext(), "Error in adding list", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }


    @Override
    public void onStop() {
        super.onStop();
        if (realmAsyncTask != null && realmAsyncTask.isCancelled()) {
            realmAsyncTask.cancel();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        myRealm.close();
    }
}
