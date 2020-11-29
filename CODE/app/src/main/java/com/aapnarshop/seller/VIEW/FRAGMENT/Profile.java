package com.aapnarshop.seller.VIEW.FRAGMENT;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.aapnarshop.seller.Library.KeyWord;
import com.aapnarshop.seller.Library.ReadExternalStoragePermission;
import com.aapnarshop.seller.Library.Utility;
import com.aapnarshop.seller.R;
import com.aapnarshop.seller.VIEWMODEL.ProfileViewModel;
import com.aapnarshop.seller.databinding.FragmentProfileBinding;
import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;


import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;

public class Profile extends Fragment  {

    FragmentProfileBinding binding;
    private Utility utility;
    ProfileViewModel profileViewModel;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_profile, container, false);
        try{
            profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
            utility = new Utility(getActivity());
            String language = utility.getLangauge();
            Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
            TextView textToolHeader = (TextView) toolbar.findViewById(R.id.title);
            // Change Language----------Start-------------------
            textToolHeader.setText(language.equals(KeyWord.BANGLA)?KeyWord.PROFILE_BN:getActivity().getResources().getString(R.string.profile));
            binding.profileShopNameTv.setText(language.equals(KeyWord.BANGLA)?getString(R.string.shop_name_bn):getString(R.string.shop_name));
            binding.profileWebAddressTv.setText(language.equals(KeyWord.BANGLA)?getString(R.string.web_bn):getString(R.string.web));
            binding.profileShopTypeTv.setText(language.equals(KeyWord.BANGLA)?getString(R.string.shop_type_bn):getString(R.string.shop_type));
            binding.profileAddressTv.setText(language.equals(KeyWord.BANGLA)?getString(R.string.address_bn):getString(R.string.address));
            binding.profileCityTv.setText(language.equals(KeyWord.BANGLA)?getString(R.string.city_bn):getString(R.string.city));
            binding.profileAreaTv.setText(language.equals(KeyWord.BANGLA)?getString(R.string.area_bn):getString(R.string.area));
            binding.profilePrivateInfoTv.setText(language.equals(KeyWord.BANGLA)?getString(R.string.private_information_bn):getString(R.string.private_information));
            //------End-----------------

            binding.profileImageLayout.setOnClickListener(v->{
                if (ReadExternalStoragePermission.isReadStoragePermissionGranted(getActivity())) {
                    Intent choose = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(choose, 1);            }
            });

            profileViewModel.getProfileImageUpdateResponse().observe(getActivity(),api_response -> {
                if (api_response.getCode()==200){
                    utility.hideProgress();
                    Glide.with(getActivity())
                            .load(api_response.getData().getAsString())
                            .placeholder(R.drawable.ic_profile)
                            .into(binding.profileImage);
                }else {
                    utility.showDialog(api_response.getData().getAsString());
                }
            });
        }
        catch(Exception e){
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==1 && data !=null && resultCode == RESULT_OK){
            utility.showProgress(false,"");
            final Uri imageUri = data.getData();
            try {
               Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),imageUri);
                float originalWidth= bitmap.getWidth();
                float originalHeight = bitmap.getHeight();
                if(originalWidth>1200 && originalHeight>=originalWidth){

                    float destWidth = 1200;
                    float destHeight = originalHeight/(originalWidth/destWidth);
                    Bitmap bitmap1= Bitmap.createScaledBitmap(bitmap,Math.round(destWidth),Math.round(destHeight),false);
                    ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
                    bitmap1.compress(Bitmap.CompressFormat.JPEG,50,baos1);
                    byte[] byteImage = baos1.toByteArray();
                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), byteImage);
                    MultipartBody.Part image = MultipartBody.Part.createFormData("userImage", "userImage", requestFile);
                    profileViewModel.updateSellerProfileImage(image);


                }else if (originalWidth>1200 && originalHeight<originalWidth){
                    float destWidth = 1400;
                    float destHeight = originalHeight/(originalWidth/destWidth);
                    Bitmap bitmap1= Bitmap.createScaledBitmap(bitmap,Math.round(destWidth),Math.round(destHeight),false);
                    ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
                    bitmap1.compress(Bitmap.CompressFormat.JPEG,50,baos1);
                    byte[] byteImage = baos1.toByteArray();
                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), byteImage);
                    MultipartBody.Part image = MultipartBody.Part.createFormData("userImage", "userImage", requestFile);
                    profileViewModel.updateSellerProfileImage(image);

                }else {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos); //decodedFoodBitmap is the bitmap object
                    byte[] byteImage = baos.toByteArray();
                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), byteImage);
                    MultipartBody.Part image = MultipartBody.Part.createFormData("userImage", "userImage", requestFile);
                    profileViewModel.updateSellerProfileImage(image);

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
/*
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getContentResolver().query(imageUri, filePathColumn, null, null, null);
            if(cursor.moveToFirst()){
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String selectedFilePath = cursor.getString(columnIndex);
                File file = new File(selectedFilePath);

                Bitmap bitmapImage = BitmapFactory.decodeFile(selectedFilePath);
                int scale = (int) (bitmapImage.getHeight() * (1392.0 / bitmapImage.getWidth()));
                Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 1392, scale, true);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                scaled.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] imageInByte = stream.toByteArray();
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), imageInByte);
                MultipartBody.Part noticeImage = MultipartBody.Part.createFormData("userImage", file.getName(), requestFile);
                profileViewModel.updateSellerProfileImage(noticeImage);
            }
            cursor.close();*/
        }
    }

}