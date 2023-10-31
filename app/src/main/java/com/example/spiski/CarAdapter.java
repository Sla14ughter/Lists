package com.example.spiski;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<Car> cars;

    public CarAdapter(Context context, List<Car> cars) {
        this.inflater = LayoutInflater.from(context);
        this.cars = cars;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Car car = cars.get(position);
        holder.pictureView.setImageResource(car.getPictureResource());
        holder.nameView.setText(car.getName());
        holder.descriptionView.setText(car.getDescriptionResource());
        holder.plusbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                car.setAmount(car.getAmount() + 1);
            }
        });
        holder.minusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                car.setAmount(car.getAmount() - 1);
            }
        });
        holder.delbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cars.remove(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView pictureView;
        final TextView nameView, descriptionView;
        final Button plusbtn, minusbtn, delbtn;

        ViewHolder(View view) {
            super(view);
            pictureView = view.findViewById(R.id.picture);
            nameView = view.findViewById(R.id.name);
            descriptionView = view.findViewById(R.id.description);
            plusbtn = view.findViewById(R.id.plusbtn);
            minusbtn = view.findViewById(R.id.minusbtn);
            delbtn = view.findViewById(R.id.delbtn);
        }
    }
}
