package com.aapnarshop.seller.VIEW.ACTIVITY;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.aapnarshop.seller.VIEW.Adapter.GalleryImageAdapter;
import com.aapnarshop.seller.Library.KeyWord;
import com.aapnarshop.seller.R;
import com.aapnarshop.seller.databinding.ActivityGalleryBinding;

import java.util.ArrayList;
import java.util.Collections;

public class GalleryActivity extends AppCompatActivity {

    private ArrayList<String> imagesFromGallery,selectedImageList;
    GalleryImageAdapter galleryImageAdapter;

    ActivityGalleryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding= DataBindingUtil.setContentView(this,R.layout.activity_gallery);

        try{

            selectedImageList = new ArrayList<>();
            imagesFromGallery = getAllShownImagesPath(this);

            galleryImageAdapter = new GalleryImageAdapter(this, imagesFromGallery, selectedImageList);
            binding.galleryGridView.setAdapter(galleryImageAdapter);

            onClickEvent();
        }
        catch(Exception e){
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }

    }

    //All kinds of click events

    private void onClickEvent() {

        binding.galleryGridView.setOnItemClickListener((arg0, view, position, arg3) -> {

            if (null != imagesFromGallery && !imagesFromGallery.isEmpty()) {

                // Logic to show button and show total selected image

                if (selectedImageList.size() < 6) {
                    for (String image : selectedImageList) {
                        if (image.equalsIgnoreCase(imagesFromGallery.get(position))) {
                            selectedImageList.remove(image);
                            view.findViewById(R.id.textViewID).setVisibility(View.GONE);
                            view.findViewById(R.id.imageCheckIVId).setVisibility(View.GONE);
                            if (selectedImageList.size() == 0) {
                                binding.gallerySendButton.setVisibility(View.GONE);
                            } else {
                                binding.gallerySendButton.setText("Select (" + selectedImageList.size() + ")");
                            }

                            return;
                        }

                    }
                    selectedImageList.add(imagesFromGallery.get(position));
                    view.findViewById(R.id.textViewID).setVisibility(View.VISIBLE);
                    view.findViewById(R.id.imageCheckIVId).setVisibility(View.VISIBLE);
                    binding.gallerySendButton.setVisibility(View.VISIBLE);
                    binding.gallerySendButton.setText("Select (" + selectedImageList.size() + ")");


                } else {
                    for (String image : selectedImageList) {
                        if (image.equalsIgnoreCase(imagesFromGallery.get(position))) {
                            selectedImageList.remove(image);
                            view.findViewById(R.id.textViewID).setVisibility(View.GONE);
                            view.findViewById(R.id.imageCheckIVId).setVisibility(View.GONE);
                            binding.gallerySendButton.setText("Select (" + selectedImageList.size() + ")");
                            return;
                        }

                    }
                    Toast.makeText(GalleryActivity.this, "Sorry ! You can not select more than 06 photos", Toast.LENGTH_SHORT).show();

                }

            }
        });

        binding.gallerySendButton.setOnClickListener(v -> {

            Intent intent = new Intent();
            intent.putStringArrayListExtra("image", selectedImageList);
            setResult(KeyWord.GALLERY_IMAGE, intent);
            finish();
        });
    }

    // Load all image from gallery
    private ArrayList<String> getAllShownImagesPath(Activity activity) {
        Uri uri;
        Cursor cursor;
        int column_index_data;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        String absolutePathOfImage = null;
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {"_data",
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        cursor = activity.getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow("_data");

        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);

            listOfAllImages.add(absolutePathOfImage);

        }
        Collections.reverse(listOfAllImages);
        return listOfAllImages;
    }
}