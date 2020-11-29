package com.aapnarshop.seller.VIEW.FRAGMENT.product;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.aapnarshop.seller.Library.KeyWord;
import com.aapnarshop.seller.Library.ReadExternalStoragePermission;
import com.aapnarshop.seller.Library.Utility;
import com.aapnarshop.seller.Model.GET.Brand;
import com.aapnarshop.seller.Model.GET.Category;
import com.aapnarshop.seller.Model.GET.ProductType;
import com.aapnarshop.seller.Model.GET.Supplier;
import com.aapnarshop.seller.Model.GET.UnitType;
import com.aapnarshop.seller.R;
import com.aapnarshop.seller.VIEW.ACTIVITY.GalleryActivity;
import com.aapnarshop.seller.VIEW.Adapter.AddProductImageAdapter;
import com.aapnarshop.seller.VIEW.Adapter.GenericAdapter;
import com.aapnarshop.seller.VIEWMODEL.ProductViewModel;
import com.aapnarshop.seller.databinding.FragmentAddProductBinding;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.aapnarshop.seller.VIEW.ACTIVITY.HomePage.navController;

public class AddProduct extends Fragment {


    private FragmentAddProductBinding binding;
    private ProductViewModel productViewModel;
    private List<String> imageList;
    private List<String> imageListAll;
    File photoFile = null;
    private String currentPhotoPath;
    private AddProductImageAdapter addProductImageAdapter;
    private LinearLayoutManager layoutManager;
    private Category category;
    private ProductType productType;
    private final List<Category> categories = new ArrayList<>();
    private final List<Supplier> supplierList = new ArrayList<>();
    private final List<ProductType> productTypeList = new ArrayList<>();
    private final List<Brand> brandList = new ArrayList<>();
    private final List<UnitType> unitTypeList = new ArrayList<>();
    private final List<UnitType> weightUnitTypeList = new ArrayList<>();
    private String unitType, unitWeightType =KeyWord.BLANK, unitWeightQuantity =KeyWord.BLANK, productCategory = KeyWord.BLANK, type = KeyWord.BLANK, description =KeyWord.BLANK, brand = KeyWord.BLANK, supplier = KeyWord.BLANK, size = KeyWord.BLANK;
    private Utility utility;
    private boolean isUnitTypeSelected = false;
    Uri photoURI;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.fragment_add_product, container, false);

        try {
            getActivity().getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
            );
            productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
            utility = new Utility(getActivity());
            String language = utility.getLangauge();
            Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
            TextView textToolHeader = toolbar.findViewById(R.id.title);
            textToolHeader.setText(language.equals(KeyWord.BANGLA) ? KeyWord.ADD_PRODUCT_BN : KeyWord.ADD_PRODUCT);

            //Change Language---------Start----------
            binding.addProductCategoryTv.setText(language.equals(KeyWord.BANGLA) ? getString(R.string.category_bn) : getString(R.string.category));
            binding.addProductProductTypeTv.setText(language.equals(KeyWord.BANGLA) ? getString(R.string.product_type_bn) : getString(R.string.product_type));
            binding.addProductBrandTv.setText(language.equals(KeyWord.BANGLA) ? getString(R.string.brand_bn) : getString(R.string.brand));
            binding.addProductSupplierTv.setText(language.equals(KeyWord.BANGLA) ? getString(R.string.supplier_bn) : getString(R.string.supplier));
            binding.addProductProductNameTv.setText(language.equals(KeyWord.BANGLA) ? getString(R.string.product_name_bn) : getString(R.string.product_name));
            binding.addProductProductSizeTv.setText(language.equals(KeyWord.BANGLA) ? getString(R.string.product_size_bn) : getString(R.string.product_size));
            binding.addProductUnitTv.setText(language.equals(KeyWord.BANGLA) ? getString(R.string.unit_size_bn) : getString(R.string.unit_size));
            binding.addProductWeightOfUnitTv.setText(language.equals(KeyWord.BANGLA) ? getString(R.string.weight_of_the_unit_bn) : getString(R.string.weight_of_the_unit));
            binding.addProductRetailSellPriceTv.setText(language.equals(KeyWord.BANGLA) ? getString(R.string.retail_sell_price_per_unit_bn) : getString(R.string.retail_sell_price_per_unit));
            binding.addProductTkTv.setText(language.equals(KeyWord.BANGLA) ? getString(R.string.tk_bn) : getString(R.string.tk));
            binding.addProductTkTv2.setText(language.equals(KeyWord.BANGLA) ? getString(R.string.tk_bn) : getString(R.string.tk));
            binding.addProductTotalQuantityTv.setText(language.equals(KeyWord.BANGLA) ? getString(R.string.total_quantity_bn) : getString(R.string.total_quantity));
            binding.addProductTotalPriceWholesellTv.setText(language.equals(KeyWord.BANGLA) ? getString(R.string.total_price_wholesale_bn) : getString(R.string.total_price_wholesale));
            binding.addProductProductDetailsTv.setText(language.equals(KeyWord.BANGLA) ? getString(R.string.product_details_bn) : getString(R.string.product_details));
            binding.cancel.setText(language.equals(KeyWord.BANGLA) ? getString(R.string.cancel_bn) : getString(R.string.cancel));
            binding.update.setText(language.equals(KeyWord.BANGLA) ? getString(R.string.update_bn) : getString(R.string.update));
            binding.addProductBrowse.setText(language.equals(KeyWord.BANGLA) ? getString(R.string.browse_bn) : getString(R.string.browse));


            //---------End------------------------

            imageList = new ArrayList<>();
            imageListAll = new ArrayList<>();
            category = new Category();
            productType = new ProductType();

            ReadExternalStoragePermission.isReadStoragePermissionGranted(getActivity());

            onclickEvent();
            observeFilteredDataForCategory();
            textWatcherListener();
            itemClickEvent();

        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }


        return binding.getRoot();
    }

    private void textWatcherListener() {

        binding.addProductCategoryDropdown.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() > 0) {
                    productViewModel.searchCategory(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.addProductSupplierDropdown.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() > 0) {
                    productViewModel.searchSupplier(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.addProductProductTypeDropdown.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() > 0) {
                    productViewModel.searchProductType(s.toString(), category);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.addProductBrandDropdown.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    productViewModel.searchBrand(s.toString(), productType);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.addProductUnitEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    binding.addProductUnitError.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void itemClickEvent() {
        binding.addProductCategoryDropdown.setOnItemClickListener((parent, view, position, id) -> {

            category.setCategoryId(categories.get(position).getCategoryId());
            binding.addProductCategoryDropdown.setText(categories.get(position).getCategoryTitle());
            binding.addProductCategoryDropdown.setSelection(categories.get(position).getCategoryTitle().length());

        });
        binding.addProductSupplierDropdown.setOnItemClickListener((parent, view, position, id) -> {
            binding.addProductSupplierDropdown.setText(supplierList.get(position).getSupplierTitle());
            binding.addProductSupplierDropdown.setSelection(supplierList.get(position).getSupplierTitle().length());

        });
        binding.addProductProductTypeDropdown.setOnItemClickListener((parent, view, position, id) -> {

            productType.setTypeId(productTypeList.get(position).getTypeId());
            binding.addProductProductTypeDropdown.setText(productTypeList.get(position).getTypeTitle());
            binding.addProductProductTypeDropdown.setSelection(productTypeList.get(position).getTypeTitle().length());

        });
        binding.addProductBrandDropdown.setOnItemClickListener((parent, view, position, id) -> {

            binding.addProductBrandDropdown.setText(brandList.get(position).getBrandTitle());
            binding.addProductBrandDropdown.setSelection(brandList.get(position).getBrandTitle().length());

        });


    }

    private void observeFilteredDataForCategory() {

        //Get filtered category
        productViewModel.getFilteredCategory().observe(getActivity(), categoryList -> {

            if (categoryList.size() > 0) {
                categories.clear();
                categories.addAll(categoryList);
                GenericAdapter<Category> autocompleteAdapter = new GenericAdapter<Category>(getActivity(), categoryList) {
                    @Override
                    public View getCustomView(int position, View view, ViewGroup parent) {
                        ViewHolder holder;

                        if (view == null) {

                            holder = new ViewHolder();

                            view = LayoutInflater.from(getContext()).inflate(R.layout.custom_spinner_item,
                                    parent, false);
                            holder.title = (TextView) view.findViewById(R.id.custom_spinner);

                            holder.title.setText(categoryList.get(position).getCategoryTitle());


                            view.setTag(holder);
                        }
                        return view;
                    }
                };
                binding.addProductCategoryDropdown.setAdapter(autocompleteAdapter);
                autocompleteAdapter.notifyDataSetChanged();

            }

        });

        //Get filtered supplier
        productViewModel.getFilteredSupplierList().observe(getActivity(), suppliers -> {

            if (suppliers.size() > 0) {
                supplierList.clear();
                supplierList.addAll(suppliers);
                GenericAdapter<Supplier> autocompleteAdapter = new GenericAdapter<Supplier>(getActivity(), supplierList) {
                    @Override
                    public View getCustomView(int position, View view, ViewGroup parent) {
                        ViewHolder holder;

                        if (view == null) {

                            holder = new ViewHolder();

                            view = LayoutInflater.from(getContext()).inflate(R.layout.custom_spinner_item,
                                    parent, false);
                            holder.title = (TextView) view.findViewById(R.id.custom_spinner);

                            holder.title.setText(supplierList.get(position).getSupplierTitle());


                            view.setTag(holder);
                        }
                        return view;
                    }
                };
                binding.addProductSupplierDropdown.setAdapter(autocompleteAdapter);
                autocompleteAdapter.notifyDataSetChanged();

            }

        });

        //Get filtered product type
        productViewModel.getFilteredProductTypeList().observe(getActivity(), productTypes -> {

            if (productTypes.size() > 0) {
                productTypeList.clear();
                productTypeList.addAll(productTypes);
                GenericAdapter<ProductType> autocompleteAdapter = new GenericAdapter<ProductType>(getActivity(), productTypeList) {
                    @Override
                    public View getCustomView(int position, View view, ViewGroup parent) {
                        ViewHolder holder;

                        if (view == null) {

                            holder = new ViewHolder();

                            view = LayoutInflater.from(getContext()).inflate(R.layout.custom_spinner_item,
                                    parent, false);
                            holder.title = (TextView) view.findViewById(R.id.custom_spinner);

                            holder.title.setText(productTypeList.get(position).getTypeTitle());


                            view.setTag(holder);
                        }
                        return view;
                    }
                };
                binding.addProductProductTypeDropdown.setAdapter(autocompleteAdapter);
                autocompleteAdapter.notifyDataSetChanged();

            }

        });

        //Get filtered Band
        productViewModel.getFilteredBandList().observe(getActivity(), brands -> {
            if (brands.size() > 0) {
                brandList.clear();
                brandList.addAll(brands);
                GenericAdapter<Brand> autocompleteAdapter = new GenericAdapter<Brand>(getActivity(), brandList) {
                    @Override
                    public View getCustomView(int position, View view, ViewGroup parent) {
                        ViewHolder holder;

                        if (view == null) {

                            holder = new ViewHolder();

                            view = LayoutInflater.from(getContext()).inflate(R.layout.custom_spinner_item,
                                    parent, false);
                            holder.title = (TextView) view.findViewById(R.id.custom_spinner);

                            holder.title.setText(brandList.get(position).getBrandTitle());


                            view.setTag(holder);
                        }
                        return view;
                    }
                };
                binding.addProductBrandDropdown.setAdapter(autocompleteAdapter);
                autocompleteAdapter.notifyDataSetChanged();

            }
        });

        //Get Unit type
        productViewModel.getUnitType().observe(getActivity(), unitTypes -> {

            unitTypeList.clear();
            unitTypeList.addAll(unitTypes);

            weightUnitTypeList.add(new UnitType("Select", "0"));
            for (UnitType unitType : unitTypes) {
                if (!unitType.getUnitType().equalsIgnoreCase("piece")) {
                    weightUnitTypeList.add(unitType);
                }
            }

            GenericAdapter<UnitType> spinnerAdapter = new GenericAdapter<UnitType>(getActivity(), unitTypes) {
                @Override
                public View getCustomView(int position, View view, ViewGroup parent) {
                    UnitType model = getItem(position);
                    View spinnerRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.unit_spinner, parent, false);

                    TextView label = spinnerRow.findViewById(com.aapnarshop.seller.R.id.custom_spinner);
                    label.setText(model.getUnitType());


                    return spinnerRow;
                }
            };
            binding.addProductUnitDropdown.setAdapter(spinnerAdapter);
            if (weightUnitTypeList.size() > 0) {
                GenericAdapter<UnitType> spinnerAdapter1 = new GenericAdapter<UnitType>(getActivity(), weightUnitTypeList) {
                    @Override
                    public View getCustomView(int position, View view, ViewGroup parent) {
                        UnitType model = getItem(position);
                        View spinnerRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.unit_spinner, parent, false);

                        TextView label = spinnerRow.findViewById(com.aapnarshop.seller.R.id.custom_spinner);
                        label.setText(model.getUnitType());


                        return spinnerRow;
                    }
                };
                binding.addProductWeightUnitDropdown.setAdapter(spinnerAdapter1);
            }
        });


    }

    //Handle all kinds of click events
    private void onclickEvent() {


        binding.addProductBrowse.setOnClickListener(v -> {
            if (ReadExternalStoragePermission.isReadStoragePermissionGranted(getActivity())) {
                startActivityForResult(new Intent(getActivity(), GalleryActivity.class), KeyWord.GALLERY_IMAGE);
            }

        });

        binding.addProductCamera.setOnClickListener(v -> {
            if (ReadExternalStoragePermission.isReadStoragePermissionGranted(getActivity())) {
                dispatchTakePictureIntent();
            }
        });

        binding.addProductForwardArrow.setOnClickListener(v -> {
            if (layoutManager != null && addProductImageAdapter != null) {
                if (layoutManager.findLastCompletelyVisibleItemPosition() < (addProductImageAdapter.getItemCount() - 1)) {
                    layoutManager.scrollToPosition(layoutManager.findLastCompletelyVisibleItemPosition() + 1);
                }
            } else {
                Toast.makeText(getActivity(), "Choose product image", Toast.LENGTH_SHORT).show();
            }

        });

        binding.addProductBackArrow.setOnClickListener(v -> {
            if (layoutManager != null && addProductImageAdapter != null) {
                if (layoutManager.findLastCompletelyVisibleItemPosition() < (addProductImageAdapter.getItemCount() + 1)) {
                    layoutManager.scrollToPosition(layoutManager.findLastCompletelyVisibleItemPosition() - 1);
                } else {
                    Toast.makeText(getActivity(), "Choose product image", Toast.LENGTH_SHORT).show();

                }
            }

        });

        binding.addProductUnitDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                unitType = unitTypeList.get(position).getUnitTypeId();
                if (unitTypeList.get(position).getUnitType().equalsIgnoreCase("piece")) {
                    binding.addProductWeightOfUnitLayout.setVisibility(View.VISIBLE);
                    isUnitTypeSelected = true;

                } else {
                    binding.addProductWeightOfUnitLayout.setVisibility(View.GONE);
                    isUnitTypeSelected = false;
                    unitWeightQuantity = KeyWord.BLANK;


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.addProductWeightUnitDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isUnitTypeSelected) {
                    if (weightUnitTypeList.get(position).getUnitTypeId().equals("0")) {
                        unitWeightType = KeyWord.BLANK;
                    } else {
                        unitWeightType = weightUnitTypeList.get(position).getUnitTypeId();


                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        binding.update.setOnClickListener(v -> {


            if (TextUtils.isEmpty(binding.addProductCategoryDropdown.getText())) {
                productCategory = KeyWord.BLANK;
            } else {
                productCategory = binding.addProductCategoryDropdown.getText().toString();
            }
            if (TextUtils.isEmpty(binding.addProductProductTypeDropdown.getText())) {
                type = KeyWord.BLANK;
            } else {
                type = binding.addProductProductTypeDropdown.getText().toString();
            }
            if (TextUtils.isEmpty(binding.addProductBrandDropdown.getText())) {
                brand = KeyWord.BLANK;
            } else {
                brand = binding.addProductBrandDropdown.getText().toString();
            }
            if (TextUtils.isEmpty(binding.addProductSupplierDropdown.getText())) {
                supplier = KeyWord.BLANK;
            } else {
                supplier = binding.addProductSupplierDropdown.getText().toString();
            }
            if (TextUtils.isEmpty(binding.addProductProductSizeDropdown.getText())) {
                size = KeyWord.BLANK;
            } else {
                size = binding.addProductProductSizeDropdown.getText().toString();
            }
            if (TextUtils.isEmpty(binding.addProductProductDetails.getText())) {
                description = KeyWord.BLANK;
            } else {
                description = binding.addProductProductDetails.getText().toString();
            }
            if (TextUtils.isEmpty(binding.addProductWeightUnit.getText())) {
                unitWeightQuantity = KeyWord.BLANK;
            } else {
                unitWeightQuantity = binding.addProductWeightUnit.getText().toString();
            }

            if (TextUtils.isEmpty(binding.addProductProductName.getText())) {
                binding.addProductProductName.setError("Enter product name");
                binding.addProductProductName.requestFocus();
            } else if (TextUtils.isEmpty(binding.addProductRetailSellPrice.getText())) {
                binding.addProductRetailSellPrice.setError("Enter retail sell price");
                binding.addProductRetailSellPrice.requestFocus();
            } else if (TextUtils.isEmpty(binding.totalQuantity.getText())) {
                binding.totalQuantity.setError("Enter product quantity");
                binding.totalQuantity.requestFocus();
            } else if (TextUtils.isEmpty(binding.addProductTotalPriceWholesell.getText())) {
                binding.addProductTotalPriceWholesell.setError("Enter total price");
                binding.addProductTotalPriceWholesell.requestFocus();
            } else if (TextUtils.isEmpty(binding.addProductUnitEt.getText())) {
                binding.addProductUnitError.setVisibility(View.VISIBLE);
                binding.addProductUnitEt.requestFocus();
            } else {
                utility.showProgress(false, "");
                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                builder.addFormDataPart("category", productCategory)
                        .addFormDataPart("type", type)
                        .addFormDataPart("brand", brand)
                        .addFormDataPart("supplier", supplier)
                        .addFormDataPart("title", binding.addProductProductName.getText().toString())
                        .addFormDataPart("size", size)
                        .addFormDataPart("unitType", unitType)
                        .addFormDataPart("unitQuantity", binding.addProductUnitEt.getText().toString())
                        .addFormDataPart("unitWeightType", unitWeightType)
                        .addFormDataPart("unitWeightQuantity", unitWeightQuantity)
                        .addFormDataPart("unitPrice", binding.addProductRetailSellPrice.getText().toString())
                        .addFormDataPart("totalQuantity", binding.totalQuantity.getText().toString())
                        .addFormDataPart("totalPrice", binding.addProductTotalPriceWholesell.getText().toString())
                        .addFormDataPart("description", description);

                for (String image : imageListAll) {
                    File file = new File(image);
                    builder.addFormDataPart("image", "image", RequestBody.create(MultipartBody.FORM, file));

                }
                RequestBody requestBody = builder.build();

                productViewModel.createProduct(requestBody)
                        .observe(getViewLifecycleOwner(), api_response -> {

                            if (api_response.getCode() == 200) {

                                utility.hideProgress();
                                navController.navigate(R.id.action_addProductFragment_to_productAddedSuccessfullyFragment);


                            } else {
                                utility.showDialog(api_response.getData().getAsString());
                                utility.hideProgress();
                            }
                        });

            }

            utility.hideProgress();

        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == KeyWord.GALLERY_IMAGE) {

            imageList.clear();

            if (data != null) {
                imageList = data.getStringArrayListExtra("image");
                if (imageList != null) {

                    imageListAll.addAll(imageList);
                }
            }

        } else if (requestCode == KeyWord.CAMERA_IMAGE) {

            imageListAll.add(currentPhotoPath);


        }
        addProductImageAdapter = new AddProductImageAdapter(getActivity(), imageListAll);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        binding.addProductProductImageRecyclerview.setLayoutManager(layoutManager);
        binding.addProductProductImageRecyclerview.setAdapter(addProductImageAdapter);
        addProductImageAdapter.notifyDataSetChanged();
    }

    // Create camera image file name
    private File createImageFile() throws IOException {
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    //Capture Image using camera
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {


            try {
                photoFile = createImageFile();
            } catch (Exception e) {
                Log.d("Error Line Number", Log.getStackTraceString(e));
            }


            if (photoFile != null) {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                    photoURI = Uri.fromFile(photoFile);
                } else {
                    photoURI = FileProvider.getUriForFile(getActivity(),
                            "com.aapnarshop.seller",
                            photoFile);
                }


                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, KeyWord.CAMERA_IMAGE);
            }
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}