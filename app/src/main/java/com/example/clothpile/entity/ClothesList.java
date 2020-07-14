package com.example.clothpile.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class ClothesList extends RealmObject {

    @PrimaryKey
    private String listId;

    private int totalClothes;

    private double bill;

    private String roomNumber;

    public ClothesList(){

    }

    public ClothesList(String roomNumber, int totalClothes){
        this.roomNumber = roomNumber;
        this.totalClothes = totalClothes;
    }

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    public String getRoomNumber(){
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public double getBill() {
        return bill;
    }

    public void setBill(double bill) {
        this.bill = bill;
    }

    public int getTotalClothes() {
        return totalClothes;
    }

    public void setTotalClothes(int totalClothes) {
        this.totalClothes = totalClothes;
    }

    public void  calculateBill(){
        setBill(totalClothes*10);
    }
}
