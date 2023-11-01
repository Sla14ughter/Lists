package com.example.spiski;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Car> cars = new ArrayList<Car>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setInitialData();
        setContentView(R.layout.activity_main);
        RecyclerView carsView = findViewById(R.id.cars);
        CarAdapter adapter = new CarAdapter(this, cars);
        carsView.setAdapter(adapter);
    }
    private void setInitialData() {
        cars.add(new Car("Матиз", R.string.matiz_descr, R.drawable.matiz, 1));
    }

    public void addCar() {
        setContentView(R.layout.activity_add);
    }
}