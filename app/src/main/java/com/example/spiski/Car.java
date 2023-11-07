package com.example.spiski;

import android.graphics.Bitmap;

class Car {
   private String name;
   private String descriptionResource;
   private Bitmap pictureResource;
   private int amount;

   public Car(String name, String description, Bitmap pictureResource, int amount) {
      this.name = name;
      this.descriptionResource = description;
      this.pictureResource = pictureResource;
      this.amount = amount;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getDescriptionResource() {
      return descriptionResource;
   }

   public void setDescriptionResource(String descriptionResource) {
      this.descriptionResource = descriptionResource;
   }

   public Bitmap getPictureResource() {
      return pictureResource;
   }

   public void setPictureResource(Bitmap pictureResource) {
      this.pictureResource = pictureResource;
   }

   public int getAmount() {
      return amount;
   }

   public void setAmount(int amount) {
      this.amount = amount;
   }
}
