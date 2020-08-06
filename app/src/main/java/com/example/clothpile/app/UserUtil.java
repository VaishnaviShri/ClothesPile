package com.example.clothpile.app;

import java.util.ArrayList;
import java.util.Arrays;

import io.realm.RealmList;

public class UserUtil {

    public ArrayList<String> clothItemsList;
    public ArrayList<Integer> clothItemsPriceList, numberOfItems;
    public String roomNumber;

    public UserUtil(){
        clothItemsList = new ArrayList<>(Arrays.asList("T-shirt,pants,bedsheets,towel".split(",")));
        clothItemsPriceList = new ArrayList<>(Arrays.asList(5, 7, 10, 8));
        roomNumber = "VK 398 L";
    }

    public static int sumOfArray(RealmList<Integer> arrayList){
        int sum = 0;
        for(int i=0; i < arrayList.size(); i++){
            sum += arrayList.get(i);
        }
        return sum;
    }


}
