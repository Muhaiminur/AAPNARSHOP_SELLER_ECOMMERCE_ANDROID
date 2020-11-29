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
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
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
import com.aapnarshop.seller.Model.GET.Image;
import com.aapnarshop.seller.Model.GET.ProductDetails;
import com.aapnarshop.seller.Model.GET.ProductType;
import com.aapnarshop.seller.Model.GET.Supplier;
import com.aapnarshop.seller.Model.GET.UnitType;
import com.aapnarshop.seller.Model.SEND.Product;
import com.aapnarshop.seller.Model.SEND.ProductStatus;
import com.aapnarshop.seller.R;
import com.aapnarshop.seller.VIEW.ACTIVITY.GalleryActivity;
import com.aapnarshop.seller.VIEW.Adapter.AddProductImageAdapter;
import com.aapnarshop.seller.VIEW.Adapter.GenericAdapter;
import com.aapnarshop.seller.VIEWMODEL.ProductViewModel;
import com.aapnarshop.seller.databinding.FragmentEditProductBinding;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class EditProduct extends Fragment implements AddProductImageAdapter.ListInterface {

    FragmentEditProductBinding binding;
    ProductViewModel productViewModel;
    Toolbar toolbar;
    TextView textToolHeader, switchTextView;
    SwitchCompat editProductSwitch;
    LinearLayout linearLayout;
    Product product;
    List<String> imageList = new ArrayList<>();
    String url;

    Utility utility;
    List<Category> categories = new ArrayList<>();
    List<Supplier> supplierList = new ArrayList<>();
    List<ProductType> productTypeList = new ArrayList<>();
    private final List<Brand> brandList = new ArrayList<>();
    private final List<UnitType> unitTypeList = new ArrayList<>();
    private final List<UnitType> unitTypeList2 = new ArrayList<>();
    Category category;
    private ProductType productType;
    Uri photoURI;
    private List<String> imageListForGalleryImage = new ArrayList<>();
    private List<String> imageFromAPI = new ArrayList<>();
    File photoFile = null;
    private String currentPhotoPath;
    private String unitType, unitWeightType = KeyWord.BLANK, unitWeightQuantity = KeyWord.BLANK, productCategory =KeyWord.BLANK, type = KeyWord.BLANK, description = KeyWord.BLANK, brand = KeyWord.BLANK, supplier = KeyWord.BLANK, size = KeyWord.BLANK;
    private File file;
    List<String> newImageList = new ArrayList<>();
    boolean isUnitTypeSelected = false;
    ProductDetails productDetails;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.fragment_edit_product, container, false);
        try {
            getActivity().getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
            );
            utility = new Utility(getActivity());
            productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
            toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
            textToolHeader = (TextView) toolbar.findViewById(R.id.title);
            switchTextView = toolbar.findViewById(R.id.switch_textView);
            textToolHeader.setText(getActivity().getResources().getString(R.string.edit_product));
            linearLayout = toolbar.findViewById(R.id.switch_layout_for_edit_product);
            editProductSwitch = toolbar.findViewById(R.id.edit_product_switch);
            category = new Category();
            productType = new ProductType();
            product = new Product(EditProductArgs.fromBundle(getArguments()).getProductId());
            utility.showProgress(false, "");

            observeData();

            editProductSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    ProductStatus productStatus = new ProductStatus(product.getProductId(), "ACTIVE");
                    switchTextView.setText(getString(R.string.active_product));
                    productViewModel.updateProductStatus(productStatus);
                } else {
                    ProductStatus productStatus = new ProductStatus(product.getProductId(), "INACTIVE");
                    switchTextView.setText(getString(R.string.inactive_product));
                    productViewModel.updateProductStatus(productStatus);
                }
            });
            observeFilteredDataForCategory();

            binding.editProductCategory.setOnItemClickListener((parent, view, position, id) -> {

                category.setCategoryId(categories.get(position).getCategoryId());
                binding.editProductCategory.setText(categories.get(position).getCategoryTitle());
                binding.editProductCategory.setSelection(categories.get(position).getCategoryTitle().length());

            });
            binding.editProductSupplier.setOnItemClickListener((parent, view, position, id) -> {
                binding.editProductSupplier.setText(supplierList.get(position).getSupplierTitle());
                binding.editProductSupplier.setSelection(supplierList.get(position).getSupplierTitle().length());

            });
            binding.editProductProductType.setOnItemClickListener((parent, view, position, id) -> {
                productType.setTypeId(productTypeList.get(position).getTypeId());

                binding.editProductProductType.setText(productTypeList.get(position).getTypeTitle());
                binding.editProductProductType.setSelection(productTypeList.get(position).getTypeTitle().length());

            });
            binding.editProductBrand.setOnItemClickListener((parent, view, position, id) -> {

                binding.editProductBrand.setText(brandList.get(position).getBrandTitle());
                binding.editProductBrand.setSelection(brandList.get(position).getBrandTitle().length());

            });

            binding.editProductUnitDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    unitType = unitTypeList.get(position).getUnitTypeId();
                    if (unitTypeList.get(position).getUnitType().equalsIgnoreCase("piece")) {
                        binding.editProductWeightOfUnitLayout.setVisibility(View.VISIBLE);
                        isUnitTypeSelected = true;
                        for (int i = 0; i < unitTypeList2.size(); i++) {
                            if (productDetails.getUnitWeight().getUnitId().equals(unitTypeList2.get(i).getUnitTypeId())) {
                                binding.editProductWeightUnitDropdown.setSelection(i);
                            }
                        }

                    } else {
                        binding.editProductWeightOfUnitLayout.setVisibility(View.GONE);
                        isUnitTypeSelected = false;
                        unitWeightQuantity = KeyWord.BLANK;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            binding.editProductWeightUnitDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (isUnitTypeSelected) {
                        if (unitTypeList2.get(position).getUnitTypeId().equals("0")) {
                            unitWeightType = KeyWord.BLANK;
                        } else {
                            unitWeightType = unitTypeList2.get(position).getUnitTypeId();


                        }
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            binding.editProductCategory.addTextChangedListener(new TextWatcher() {
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

            binding.editProductSupplier.addTextChangedListener(new TextWatcher() {
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
            binding.editProductProductType.addTextChangedListener(new TextWatcher() {
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
            binding.editProductBrand.addTextChangedListener(new TextWatcher() {
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

            onClick();
            productViewModel.getProductUpdateResponse().observe(getViewLifecycleOwner(), api_response -> {

                if (api_response.getCode() == 200) {

                    utility.hideProgress();
                    utility.showDialog("Product edit successful");
                } else {
                    utility.showDialog(api_response.getData().getAsString());
                    utility.hideProgress();
                }
            });

        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }


        return binding.getRoot();
    }

    private void onClick() {
        binding.editProductBrowse.setOnClickListener(v -> {
            if (ReadExternalStoragePermission.isReadStoragePermissionGranted(getActivity())) {
                startActivityForResult(new Intent(getActivity(), GalleryActivity.class), KeyWord.GALLERY_IMAGE);
            }

        });

        binding.editProductCamera.setOnClickListener(v -> {
            if (ReadExternalStoragePermission.isReadStoragePermissionGranted(getActivity())) {
                dispatchTakePictureIntent();
            }
        });

        binding.update.setOnClickListener(v -> {
            if (TextUtils.isEmpty(binding.editProductCategory.getText())) {
                productCategory = KeyWord.BLANK;
            } else {
                productCategory = binding.editProductCategory.getText().toString();
            }
            if (TextUtils.isEmpty(binding.editProductProductType.getText())) {
                type = KeyWord.BLANK;
            } else {
                type = binding.editProductProductType.getText().toString();
            }
            if (TextUtils.isEmpty(binding.editProductBrand.getText())) {
                brand = KeyWord.BLANK;
            } else {
                brand = binding.editProductBrand.getText().toString();
            }
            if (TextUtils.isEmpty(binding.editProductSupplier.getText())) {
                supplier = KeyWord.BLANK;
            } else {
                supplier = binding.editProductSupplier.getText().toString();
            }
            if (TextUtils.isEmpty(binding.editProductProductSize.getText())) {
                size = KeyWord.BLANK;
            } else {
                size = binding.editProductProductSize.getText().toString();
            }
            if (TextUtils.isEmpty(binding.editProductDetails.getText())) {
                description = KeyWord.BLANK;
            } else {
                description = binding.editProductDetails.getText().toString();
            }
            if (TextUtils.isEmpty(binding.editProductWeightUnit.getText())) {
                unitWeightQuantity = KeyWord.BLANK;
            } else {
                unitWeightQuantity = binding.editProductWeightUnit.getText().toString();
            }

            if (TextUtils.isEmpty(binding.editProductProductName.getText())) {
                binding.editProductProductName.setError("Enter product name");
                binding.editProductProductName.requestFocus();
            } else if (TextUtils.isEmpty(binding.editProductRetailSellPrice.getText())) {
                binding.editProductRetailSellPrice.setError("Enter retail sell price");
                binding.editProductRetailSellPrice.requestFocus();
            } else if (TextUtils.isEmpty(binding.editTotalQuantity.getText())) {
                binding.editTotalQuantity.setError("Enter product quantity");
                binding.editTotalQuantity.requestFocus();
            } else if (TextUtils.isEmpty(binding.editProductTotalPriceWholesell.getText())) {
                binding.editProductTotalPriceWholesell.setError("Enter total price");
                binding.editProductTotalPriceWholesell.requestFocus();
            } else if (TextUtils.isEmpty(binding.editProductUnit.getText())) {
                binding.editProductUnitError.setVisibility(View.VISIBLE);
                binding.editProductUnit.requestFocus();
            } else {
                utility.showProgress(false, "");

                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                builder.addFormDataPart("productId", product.getProductId())
                        .addFormDataPart("category", productCategory)
                        .addFormDataPart("type", type)
                        .addFormDataPart("brand", brand)
                        .addFormDataPart("supplier", supplier)
                        .addFormDataPart("title", binding.editProductProductName.getText().toString())
                        .addFormDataPart("size", size)
                        .addFormDataPart("unitType", unitType)
                        .addFormDataPart("unitQuantity", binding.editProductUnit.getText().toString())
                        .addFormDataPart("unitWeightType", unitWeightType)
                        .addFormDataPart("unitWeightQuantity", unitWeightQuantity)
                        .addFormDataPart("unitPrice", binding.editProductRetailSellPrice.getText().toString())
                        .addFormDataPart("totalQuantity", binding.editTotalQuantity.getText().toString())
                        .addFormDataPart("totalPrice", binding.editProductTotalPriceWholesell.getText().toString())
                        .addFormDataPart("description", description);


                if (imageFromAPI.size() > 0) {
                    for (String image : imageFromAPI) {
                        builder.addFormDataPart("oldImages", image);
                    }
                }


                if (newImageList.size() > 0) {
                    for (String image : newImageList) {
                        file = new File(image);
                        builder.addFormDataPart("newImage", "image", RequestBody.create(MultipartBody.FORM, file));

                    }
                }

                RequestBody requestBody = builder.build();

                productViewModel.updateProduct(requestBody);


            }


        });

    }


    private void observeFilteredDataForCategory() {
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
                binding.editProductCategory.setAdapter(autocompleteAdapter);
                autocompleteAdapter.notifyDataSetChanged();

            }

        });

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
                binding.editProductSupplier.setAdapter(autocompleteAdapter);
                autocompleteAdapter.notifyDataSetChanged();

            }

        });
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
                binding.editProductProductType.setAdapter(autocompleteAdapter);
                autocompleteAdapter.notifyDataSetChanged();

            }

        });
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
                binding.editProductBrand.setAdapter(autocompleteAdapter);
                autocompleteAdapter.notifyDataSetChanged();

            }
        });


    }


    private void observeData() {

        productViewModel.getUnitType().observe(getActivity(), unitTypes -> {

            unitTypeList.clear();
            unitTypeList.addAll(unitTypes);

            unitTypeList2.add(new UnitType("Select", "0"));
            for (UnitType unitType : unitTypes) {
                if (!unitType.getUnitType().equalsIgnoreCase("piece")) {
                    unitTypeList2.add(unitType);
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
            binding.editProductUnitDropdown.setAdapter(spinnerAdapter);
            if (unitTypeList2.size() > 0) {
                GenericAdapter<UnitType> spinnerAdapter1 = new GenericAdapter<UnitType>(getActivity(), unitTypeList2) {
                    @Override
                    public View getCustomView(int position, View view, ViewGroup parent) {
                        UnitType model = getItem(position);
                        View spinnerRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.unit_spinner, parent, false);

                        TextView label = spinnerRow.findViewById(com.aapnarshop.seller.R.id.custom_spinner);
                        label.setText(model.getUnitType());


                        return spinnerRow;
                    }
                };
                binding.editProductWeightUnitDropdown.setAdapter(spinnerAdapter1);
            }
        });

        productViewModel.sendProductDetailsRequest(product);

        productViewModel.getProductDetails().observe(getActivity(), productDetails -> {


            this.productDetails = productDetails;
            if (!productDetails.getCategory().getCategoryTitle().equalsIgnoreCase("BLANK")) {
                binding.editProductCategory.setText(productDetails.getCategory().getCategoryTitle());

            }
            if (!productDetails.getTitle().equalsIgnoreCase("BLANK")) {
                binding.editProductProductName.setText(productDetails.getTitle());
            }
            if (!productDetails.getType().getTypeTitle().equalsIgnoreCase("BLANK")) {
                binding.editProductProductType.setText(productDetails.getType().getTypeTitle());
            }
            if (!productDetails.getBrand().getBrandTitle().equalsIgnoreCase("BLANK")) {
                binding.editProductBrand.setText(productDetails.getBrand().getBrandTitle());
            }
            if (!productDetails.getSupplier().getSupplierTitle().equalsIgnoreCase("BLANK")) {
                binding.editProductSupplier.setText(productDetails.getSupplier().getSupplierTitle());
            }
            if (!productDetails.getSize().equalsIgnoreCase("BLANK")) {
                binding.editProductProductSize.setText(productDetails.getSize());
            }

            if (!productDetails.getUnit().getUnitQuantity().equalsIgnoreCase("BLANK")) {
                binding.editProductUnit.setText(productDetails.getUnit().getUnitQuantity());

            }
            if (!productDetails.getUnitPrice().equalsIgnoreCase("BLANK")) {
                binding.editProductRetailSellPrice.setText(productDetails.getUnitPrice());

            }
            if (!productDetails.getTotalQuantity().equalsIgnoreCase("BLANK")) {
                binding.editTotalQuantity.setText(productDetails.getTotalQuantity());

            }
            if (!productDetails.getTotalPrice().equalsIgnoreCase("BLANK")) {
                binding.editProductTotalPriceWholesell.setText(productDetails.getTotalPrice());

            }
            if (!productDetails.getDescription().equalsIgnoreCase("BLANK")) {
                binding.editProductDetails.setText(productDetails.getDescription());

            }
            if (!productDetails.getUnitWeight().getUnitQuantity().equalsIgnoreCase("BLANK")) {
                binding.editProductWeightUnit.setText(productDetails.getUnitWeight().getUnitQuantity());

            }

            for (int i = 0; i < unitTypeList.size(); i++) {
                if (productDetails.getUnit().getUnitId().equals(unitTypeList.get(i).getUnitTypeId())) {
                    binding.editProductUnitDropdown.setSelection(i);
                }
            }


            for (Image image : productDetails.getImages()) {
                url = image.getUrl();
                imageList.add(url);
                imageFromAPI.clear();
                imageFromAPI.addAll(imageList);
                initAdapterForProductImage(imageFromAPI);
            }

            if (productDetails.getStatus().equalsIgnoreCase("ACTIVE")) {

                editProductSwitch.setChecked(true);
                switchTextView.setText(getString(R.string.active_product));
            } else {
                editProductSwitch.setChecked(false);
                switchTextView.setText(getString(R.string.inactive_product));

            }

            utility.hideProgress();

        });


    }

    private void initAdapterForProductImage(List<String> images) {

        AddProductImageAdapter addProductImageAdapter = new AddProductImageAdapter(getActivity(), images, EditProduct.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        binding.addProductProductImageRecyclerview.setLayoutManager(layoutManager);
        binding.addProductProductImageRecyclerview.setAdapter(addProductImageAdapter);
        addProductImageAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == KeyWord.GALLERY_IMAGE) {

            imageListForGalleryImage.clear();

            if (data != null) {
                imageListForGalleryImage = data.getStringArrayListExtra("image");
                if (imageListForGalleryImage != null) {

                    newImageList.addAll(imageListForGalleryImage);
                }
            }

        } else if (requestCode == KeyWord.CAMERA_IMAGE) {


            newImageList.add(currentPhotoPath);


        }
        imageList.addAll(newImageList);

        initAdapterForProductImage(imageList);


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
    public void onResume() {
        super.onResume();
        linearLayout.setVisibility(View.VISIBLE);

    }

    @Override
    public void onPause() {
        super.onPause();
        linearLayout.setVisibility(View.GONE);

    }

    @Override
    public void listIndex(int index) {
        imageFromAPI.remove(index);

    }
}