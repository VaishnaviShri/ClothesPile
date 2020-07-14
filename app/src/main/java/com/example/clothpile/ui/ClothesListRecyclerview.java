package com.example.clothpile.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.recyclerview.widget.RecyclerView;

import com.example.clothpile.R;
import com.example.clothpile.entity.ClothesList;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

public class ClothesListRecyclerview extends  RecyclerView.Adapter<ClothesListRecyclerview.Holders> {

    private Context context;
    private Realm realm;
    private RealmResults<ClothesList> realmResults;
    private LayoutInflater inflater;

    public ClothesListRecyclerview(Context context, Realm realm, RealmResults<ClothesList> realmResults) {
        this.context = context;
        this.realm = realm;
        this.realmResults = realmResults;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ClothesListRecyclerview.Holders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.clothes_list_layout, parent, false);
        return new Holders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClothesListRecyclerview.Holders holder, int position) {

        ClothesList clothesList = realmResults.get(position);
        assert clothesList != null;
        holder.setClothesList(clothesList, position);
        holder.setListner();
    }

    @Override
    public int getItemCount() {
        return realmResults.size();
    }

    public class Holders extends RecyclerView.ViewHolder{

        private int position;
        private TextView room_number, total_clothes, bill;
        Button delete_button, edit_button;

        public Holders(@NonNull View itemView) {
            super(itemView);

            room_number = itemView.findViewById(R.id.room_number);
            total_clothes = itemView.findViewById(R.id.total_clothes);
            bill = itemView.findViewById(R.id.bill);
            delete_button = itemView.findViewById(R.id.delete_button);
            edit_button = itemView.findViewById(R.id.edit_button);

        }

        public void setClothesList(ClothesList clothesList, int position){

            this.position = position;
            String roomNumber = clothesList.getRoomNumber();
            String totalClothes = "Total Clothes : " + clothesList.getTotalClothes();
            String billAmount = "Bill : Rs." + clothesList.getBill();

            room_number.setText(roomNumber);
            total_clothes.setText(totalClothes);
            bill.setText(billAmount);

        }

        public void setListner(){

            delete_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realmResults.deleteFromRealm(position);
                            Toast.makeText( context,"List deleted", Toast.LENGTH_SHORT).show();
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, realmResults.size());
                            notifyDataSetChanged();
                        }
                    });

                }
            });

            edit_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}
