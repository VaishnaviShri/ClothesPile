package com.example.clothpile.entity;

import com.example.clothpile.app.UserUtil;

import java.util.ArrayList;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class ClothesList extends RealmObject {

    @PrimaryKey
    private String listId;

    private int totalClothes;

    private double bill;
    private ArrayList<Integer> numberOfItems, priceList, itemTotalPrice;
    private ArrayList<String> itemsList;


    private String roomNumber, collectionDate;

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

    public void setTotalClothes() {
        totalClothes = UserUtil.sumOfArray(numberOfItems);
    }

    public void  calculateBill(){
        for(int i = 0; i < numberOfItems.size(); i++){
            itemTotalPrice.add(i, numberOfItems.get(i) * priceList.get(i));
        }
        setBill(UserUtil.sumOfArray(itemTotalPrice));
    }

    public void setNumberOfItems(ArrayList<Integer> numberOfItems) {
        this.numberOfItems = numberOfItems;
        setTotalClothes();
    }

    public void setPriceList(ArrayList<Integer> priceList) {
        this.priceList = priceList;
    }

    public void setCollectionDate(String collectionDate) {
        this.collectionDate = collectionDate;
    }

    public String getCollectionDate() {
        return collectionDate;
    }
}
