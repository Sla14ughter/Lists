package com.example.spiski;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Car> cars = new ArrayList<Car>();
    private ActivityResultLauncher<Intent> pickImageLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setInitialData();
        setContentView(R.layout.activity_main);
        RecyclerView carsView = findViewById(R.id.cars);
        CarAdapter adapter = new CarAdapter(this, cars);
        carsView.setAdapter(adapter);

        pickImageLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        Uri selectedImageUri = data.getData();
                        ImageView imageView = findViewById(R.id.imageView);
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            String[] filePathColumn = {MediaStore.Images.Media.DATA};
                            Cursor cursor = getContentResolver().query(selectedImageUri, filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();
                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String filePath = cursor.getString(columnIndex);
                                cursor.close();
                                ExifInterface exif = new ExifInterface(filePath);
                                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                                int rotationAngle = 0;
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
                                if (rotationAngle != 0) {
                                    Matrix matrix = new Matrix();
                                    matrix.postRotate(rotationAngle);
                                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                                }
                                imageView.setImageBitmap(bitmap);
                            }
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }
    private void setInitialData() {
        cars.add(new Car("Матиз", R.string.matiz_descr, R.drawable.matiz, 1));
    }

    public void addCar(View view) {
        setContentView(R.layout.activity_add);
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