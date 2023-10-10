package com.example.spiski;

class Car {
   private String name;
   private int descriptionResource;
   private int pictureResource;

   public Car(String name, int description, int pictureResource) {
      this.name = name;
      this.descriptionResource = description;
      this.pictureResource = pictureResource;
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
}
