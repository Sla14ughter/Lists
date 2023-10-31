package com.example.spiski;

class Car {
   private String name;
   private int descriptionResource;
   private int pictureResource;
   private int amount;

   public Car(String name, int description, int pictureResource, int amount) {
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

   public int getDescriptionResource() {
      return descriptionResource;
   }

   public void setDescriptionResource(int descriptionResource) {
      this.descriptionResource = descriptionResource;
   }

   public int getPictureResource() {
      return pictureResource;
   }

   public void setPictureResource(int pictureResource) {
      this.pictureResource = pictureResource;
   }

   public int getAmount() {
      return amount;
   }

   public void setAmount(int amount) {
      this.amount = amount;
   }
}
