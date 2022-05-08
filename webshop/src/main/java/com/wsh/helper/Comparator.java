package com.wsh.helper;

import com.wsh.model.Category;
import com.wsh.model.Item;

public class Comparator  {
  public static  java.util.Comparator<Category> categoryComparator= new java.util.Comparator<Category>() {
          @Override
          public int compare(Category lhs, Category rhs) {

              return lhs.getPosition() < rhs.getPosition() ? -1 :   1  ;
          }
        @Override
        public boolean equals(Object o) {
            return false;
        }
    };
    public static  java.util.Comparator<Item> itemComparator= new java.util.Comparator<Item>() {
        @Override
        public int compare(Item lhs, Item rhs) {

            return lhs.getPrice() < rhs.getPrice() ? -1 :   1  ;
        }
        @Override
        public boolean equals(Object o) {
            return false;
        }
    };

}
