package com.example.clothpile.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.clothpile.R;
import com.example.clothpile.app.UserUtil;
import com.example.clothpile.entity.ClothesList;
import com.example.clothpile.ui.ItemAdapter;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmAsyncTask;

public class AddListFragment extends Fragment {


    Button addListButton, pickDateButton;
    ElegantNumberButton elegantNumberButton;
    TextView roomNumber, collectionDate;
    ListView listView;
    private Realm myRealm;
    private RealmAsyncTask realmAsyncTask;
    Calendar c;
    DatePickerDialog datePickerDialog;
    UserUtil userUtil;

    private ArrayList<String> itemsList;
    private ArrayList<Integer> priceList, numberOfItems;
    private ItemAdapter itemAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.add_list_fragment, container, false);
        roomNumber = view.findViewById(R.id.room_number);

        addListButton = view.findViewById(R.id.add_list);
        pickDateButton = view.findViewById(R.id.pick_date_btn);
        collectionDate = view.findViewById(R.id.date);
        elegantNumberButton = view.findViewById(R.id.elegant_number_button);

        userUtil = new UserUtil();
        roomNumber.setText(userUtil.roomNumber);

        addListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertRecords();

            }
        });
        pickDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickDate();
            }
        });


        listView = view.findViewById(R.id.items_list_view);

        itemsList = userUtil.clothItemsList;
        numberOfItems = userUtil.numberOfItems;

        itemAdapter = new ItemAdapter(getContext(), itemsList, numberOfItems);
        listView.setAdapter(itemAdapter);
        itemAdapter.notifyDataSetChanged();

        myRealm = Realm.getDefaultInstance();

        return view;
    }

    private void insertRecords() {

        final String id = UUID.randomUUID().toString();


        if(numberOfItems == null){
            Toast.makeText(getContext(), "Fields empty!", Toast.LENGTH_SHORT).show();
            return;
        }



        realmAsyncTask = myRealm.executeTransactionAsync(
                new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {

                        ClothesList clothesList = realm.createObject(ClothesList.class, id);
                        //clothesList.setTotalClothes(totalClothes);
                        clothesList.setRoomNumber(userUtil.roomNumber);
                        clothesList.setNumberOfItems(numberOfItems);
                        clothesList.setPriceList(priceList);
                        clothesList.setCollectionDate(pickDate());
                        clothesList.calculateBill();
                    }
                },
                new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getContext(), "List added", Toast.LENGTH_SHORT).show();
                        // TODO : refresh
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

    private String pickDate(){
        c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        final String[] date = new String[1];

        datePickerDialog = new DatePickerDialog(this.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                date[0] = day + "/" + month;
                collectionDate.setText(date[0]);
            }
        }, year, month, day);
        datePickerDialog.show();
        return date[0];
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
