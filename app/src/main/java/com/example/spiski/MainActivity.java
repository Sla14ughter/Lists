package com.example.spiski;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Car> cars = new ArrayList<>();
    private ActivityResultLauncher<Intent> pickImageLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setInitialData();
        setContentView(R.layout.activity_main);
        fillCars();

        pickImageLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        Uri selectedImageUri = data.getData();
                        ImageView imageView = findViewById(R.id.imageView);
                        int rotationAngle = getRotationAngle(selectedImageUri);
                        Picasso.get()
                                .load(selectedImageUri)
                                .rotate(rotationAngle)
                                .into(imageView);
                    }
                }
            }
        });
    }
    private int getRotationAngle(Uri imageUri) {
        int rotationAngle = 0;
        try {
            ExifInterface exif = new ExifInterface(getRealPathFromUri(imageUri));
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotationAngle = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotationAngle = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotationAngle = 270;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotationAngle;
    }
    private String getRealPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String realPath = cursor.getString(column_index);
        cursor.close();
        return realPath;
    }
    public void fillCars() {
        RecyclerView carsView = findViewById(R.id.cars);
        CarAdapter adapter = new CarAdapter(this, cars);
        carsView.setAdapter(adapter);
    }
    private void setInitialData() {
        Resources resources = getResources();
        cars.add(new Car("Матиз", resources.getString(R.string.matiz_descr), BitmapFactory.decodeResource(getResources(), R.drawable.matiz), 1));
    }

    public void addCar(View view) {
        setContentView(R.layout.activity_add);
    }

    public void saveCar(View view) {
        EditText name = findViewById(R.id.carName);
        EditText amount = findViewById(R.id.carAmount);
        EditText description = findViewById(R.id.carDescription);
        ImageView picture = findViewById(R.id.imageView);
        cars.add(new Car(
                name.getText().toString(),
                description.getText().toString(),
                ((BitmapDrawable) picture.getDrawable()).getBitmap(),
                Integer.parseInt(amount.getText().toString())));
        setContentView(R.layout.activity_main);
        fillCars();
    }

    public void onImageViewClick(View view) {
        openImagePicker();
    }

    private void openImagePicker() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickImageLauncher.launch(intent);
        }
    }
}