package com.aapnarshop.seller.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.aapnarshop.seller.Http.API_RESPONSE;
import com.aapnarshop.seller.Http.ApiClient;
import com.aapnarshop.seller.Http.ApiInterface;
import com.aapnarshop.seller.Library.KeyWord;
import com.aapnarshop.seller.Library.Utility;
import com.aapnarshop.seller.Model.GET.AllProduct;
import com.aapnarshop.seller.Model.GET.Brand;
import com.aapnarshop.seller.Model.GET.Category;
import com.aapnarshop.seller.Model.GET.ProductDetails;
import com.aapnarshop.seller.Model.GET.ProductType;
import com.aapnarshop.seller.Model.GET.Supplier;
import com.aapnarshop.seller.Model.GET.UnitType;
import com.aapnarshop.seller.Model.SEND.FilterProduct;
import com.aapnarshop.seller.Model.SEND.Product;
import com.aapnarshop.seller.Model.SEND.ProductQuantity;
import com.aapnarshop.seller.Model.SEND.ProductStatus;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepo {
    private final Application application;
    private final MutableLiveData<ProductDetails> productDetailsMutableLiveData;
    private final MutableLiveData<API_RESPONSE> apiResponseMutableLiveData;
    private final MutableLiveData<List<Category>> categoryMutableLiveData;
    private final MutableLiveData<List<Supplier>> supplierMutableLiveData;
    private final MutableLiveData<List<ProductType>> productTypeMutableLiveData;
    private final MutableLiveData<List<UnitType>> unitTypeMutableLiveData;
    private final MutableLiveData<List<Brand>> brandMutableLiveData;
    private final MutableLiveData<List<AllProduct>> allProductMutableLiveData;
    private final Utility utility;
    private final ApiInterface apiInterface;
    private final Gson gson;

    public ProductRepo(Application application) {
        this.application = application;
        productDetailsMutableLiveData = new MutableLiveData<>();
        unitTypeMutableLiveData = new MutableLiveData<>();
        apiResponseMutableLiveData = new MutableLiveData<>();
        categoryMutableLiveData = new MutableLiveData<>();
        supplierMutableLiveData = new MutableLiveData<>();
        productTypeMutableLiveData = new MutableLiveData<>();
        brandMutableLiveData = new MutableLiveData<>();
        allProductMutableLiveData = new MutableLiveData<>();
        utility = new Utility(application);
        apiInterface = ApiClient.getBaseClient().create(ApiInterface.class);
        gson = new Gson();
    }

    public void sendProductDetailsRequest(Product product) {
        Call<API_RESPONSE> call = apiInterface.getProductDetails(utility.getAuthorizationKey(), "1", KeyWord.TOKEN, utility.getLangauge(), product);
        call.enqueue(new Callback<API_RESPONSE>() {
            @Override
            public void onResponse(@NotNull Call<API_RESPONSE> call, @NotNull Response<API_RESPONSE> response) {
                if (response.isSuccessful()) {
                    API_RESPONSE api_response = response.body();
                    if (api_response != null && api_response.getCode() == 200) {
                        productDetailsMutableLiveData.postValue(gson.fromJson(api_response.getData(), ProductDetails.class));
                    }
                }
            }

            @Override
            public void onFailure(Call<API_RESPONSE> call, Throwable t) {

            }
        });
    }

    public LiveData<ProductDetails> getProductDetails() {

        return productDetailsMutableLiveData;
    }

    public void updateProductStatus(ProductStatus productStatus) {

        Call<API_RESPONSE> call = apiInterface.updateProductStatus(utility.getAuthorizationKey(), "1", KeyWord.TOKEN, utility.getLangauge(), productStatus);
        call.enqueue(new Callback<API_RESPONSE>() {
            @Override
            public void onResponse(Call<API_RESPONSE> call, Response<API_RESPONSE> response) {
                if (response.isSuccessful() && response != null) {
                    apiResponseMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<API_RESPONSE> call, Throwable t) {

            }
        });
    }

    public LiveData<API_RESPONSE> getProductStatusResponse() {

        return apiResponseMutableLiveData;
    }

    public void searchProductCategory(String title) {

        Call<API_RESPONSE> call = apiInterface.filterCategory(utility.getAuthorizationKey(), "1", KeyWord.TOKEN, utility.getLangauge(), title);

        call.enqueue(new Callback<API_RESPONSE>() {
            @Override
            public void onResponse(Call<API_RESPONSE> call, Response<API_RESPONSE> response) {
                utility.logger("Category" + response.body());
                if (response.isSuccessful()) {
                    API_RESPONSE apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getCode() == 200) {
                        Type listType = new TypeToken<List<Category>>() {
                        }.getType();
                        List<Category> category = gson.fromJson(apiResponse.getData(), listType);
                        categoryMutableLiveData.postValue(category);
                        utility.logger("List" + category.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<API_RESPONSE> call, Throwable t) {

            }
        });
    }

    public LiveData<List<Category>> getFilteredCategory() {

        return categoryMutableLiveData;
    }

    public void searchSupplier(String title) {

        Call<API_RESPONSE> call = apiInterface.filterSupplier(utility.getAuthorizationKey(), "1", KeyWord.TOKEN, utility.getLangauge(), title);

        call.enqueue(new Callback<API_RESPONSE>() {
            @Override
            public void onResponse(Call<API_RESPONSE> call, Response<API_RESPONSE> response) {
                utility.logger("Category" + response.body());
                if (response.isSuccessful()) {
                    API_RESPONSE apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getCode() == 200) {
                        Type listType = new TypeToken<List<Supplier>>() {
                        }.getType();
                        List<Supplier> supplierList = gson.fromJson(apiResponse.getData(), listType);
                        supplierMutableLiveData.postValue(supplierList);
                    }
                }
            }

            @Override
            public void onFailure(Call<API_RESPONSE> call, Throwable t) {

            }
        });
    }

    public LiveData<List<Supplier>> getFilteredSupplierList() {

        return supplierMutableLiveData;
    }

    public void searchProductType(String title, Category category) {

        Call<API_RESPONSE> call = apiInterface.filterProductType(utility.getAuthorizationKey(), "1", KeyWord.TOKEN, utility.getLangauge(), title, category);

        call.enqueue(new Callback<API_RESPONSE>() {
            @Override
            public void onResponse(Call<API_RESPONSE> call, Response<API_RESPONSE> response) {
                utility.logger("Category" + response.body());
                if (response.isSuccessful()) {
                    API_RESPONSE apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getCode() == 200) {
                        Type listType = new TypeToken<List<ProductType>>() {
                        }.getType();
                        List<ProductType> productTypeList = gson.fromJson(apiResponse.getData(), listType);
                        productTypeMutableLiveData.postValue(productTypeList);
                    }
                }
            }

            @Override
            public void onFailure(Call<API_RESPONSE> call, Throwable t) {

            }
        });
    }

    public LiveData<List<ProductType>> getFilteredProductTypeList() {

        return productTypeMutableLiveData;
    }

    public void searchBrand(String title, ProductType productType) {

        Call<API_RESPONSE> call = apiInterface.filterBrand(utility.getAuthorizationKey(), "1", KeyWord.TOKEN, utility.getLangauge(), title, productType);

        call.enqueue(new Callback<API_RESPONSE>() {
            @Override
            public void onResponse(Call<API_RESPONSE> call, Response<API_RESPONSE> response) {
                utility.logger("Category" + response.body());
                if (response.isSuccessful()) {
                    API_RESPONSE apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getCode() == 200) {
                        Type listType = new TypeToken<List<Brand>>() {
                        }.getType();
                        List<Brand> brandList = gson.fromJson(apiResponse.getData(), listType);
                        brandMutableLiveData.postValue(brandList);
                    }
                }
            }

            @Override
            public void onFailure(Call<API_RESPONSE> call, Throwable t) {

            }
        });
    }

    public LiveData<List<Brand>> getFilteredBrandList() {

        return brandMutableLiveData;
    }


    public MutableLiveData<List<UnitType>> getUnitList() {

        Call<API_RESPONSE> call = apiInterface.getUnitList(utility.getAuthorizationKey(), "16", KeyWord.TOKEN, utility.getLangauge());
        call.enqueue(new Callback<API_RESPONSE>() {
            @Override
            public void onResponse(Call<API_RESPONSE> call, Response<API_RESPONSE> response) {
                if (response.isSuccessful()) {
                    API_RESPONSE apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getCode() == 200) {
                        Type listType = new TypeToken<List<UnitType>>() {
                        }.getType();
                        List<UnitType> unitTypeList = gson.fromJson(apiResponse.getData(), listType);
                        unitTypeMutableLiveData.postValue(unitTypeList);

                    }
                }
            }

            @Override
            public void onFailure(Call<API_RESPONSE> call, Throwable t) {

            }
        });

        return unitTypeMutableLiveData;
    }

    public MutableLiveData<API_RESPONSE> createProduct(RequestBody requestBody) {

        Call<API_RESPONSE> call = apiInterface.createProduct(utility.getAuthorizationKey(), "16", KeyWord.TOKEN, utility.getLangauge(), requestBody);
        call.enqueue(new Callback<API_RESPONSE>() {
            @Override
            public void onResponse(Call<API_RESPONSE> call, Response<API_RESPONSE> response) {
                utility.logger("response" + response.toString());
                if (response.isSuccessful()) {
                    API_RESPONSE apiResponse = response.body();
                    utility.logger("response" + apiResponse.toString());
                    apiResponseMutableLiveData.postValue(apiResponse);
                }
            }

            @Override
            public void onFailure(Call<API_RESPONSE> call, Throwable t) {

            }
        });

        return apiResponseMutableLiveData;
    }

    public void updateProduct(RequestBody requestBody) {

        Call<API_RESPONSE> call = apiInterface.updateProduct(utility.getAuthorizationKey(), "16", KeyWord.TOKEN, utility.getLangauge(), requestBody);
        call.enqueue(new Callback<API_RESPONSE>() {
            @Override
            public void onResponse(Call<API_RESPONSE> call, Response<API_RESPONSE> response) {
                utility.logger("response" + response.toString());
                utility.logger("response" + response.body());
                if (response.isSuccessful()) {
                    API_RESPONSE apiResponse = response.body();
                    utility.logger("update api response" + apiResponse.toString());
                    apiResponseMutableLiveData.postValue(apiResponse);
                }
            }

            @Override
            public void onFailure(Call<API_RESPONSE> call, Throwable t) {

                utility.logger("error response " + t.getMessage());

            }
        });

    }

    public MutableLiveData<API_RESPONSE> getProductUpdateResponse() {

        return apiResponseMutableLiveData;
    }

    public MutableLiveData<List<AllProduct>> getProductList(FilterProduct filterProduct) {

        Call<API_RESPONSE> call = apiInterface.getAllProduct(utility.getAuthorizationKey(), "16", KeyWord.TOKEN, utility.getLangauge(), filterProduct);

        call.enqueue(new Callback<API_RESPONSE>() {
            @Override
            public void onResponse(Call<API_RESPONSE> call, Response<API_RESPONSE> response) {
                if (response.isSuccessful()) {
                    API_RESPONSE apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getCode() == 200) {
                        Type listType = new TypeToken<List<AllProduct>>() {
                        }.getType();
                        List<AllProduct> productList = gson.fromJson(apiResponse.getData(), listType);

                        allProductMutableLiveData.postValue(productList);
                    }
                }
            }

            @Override
            public void onFailure(Call<API_RESPONSE> call, Throwable t) {

            }
        });

        return allProductMutableLiveData;
    }

    public MutableLiveData<API_RESPONSE> updateProductQuantity(ProductQuantity productQuantity) {

        Call<API_RESPONSE> call = apiInterface.updateProductQuantity(utility.getAuthorizationKey(), "16", KeyWord.TOKEN, utility.getLangauge(), productQuantity);
        call.enqueue(new Callback<API_RESPONSE>() {
            @Override
            public void onResponse(Call<API_RESPONSE> call, Response<API_RESPONSE> response) {
                if (response.isSuccessful()) {
                    apiResponseMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<API_RESPONSE> call, Throwable t) {

            }
        });

        return apiResponseMutableLiveData;
    }


    public MutableLiveData<List<Category>> getProductCategory() {

        Call<API_RESPONSE> call = apiInterface.getProductCategory(utility.getAuthorizationKey(), "16", KeyWord.TOKEN, utility.getLangauge());
        call.enqueue(new Callback<API_RESPONSE>() {
            @Override
            public void onResponse(Call<API_RESPONSE> call, Response<API_RESPONSE> response) {
                if (response.isSuccessful()) {
                    API_RESPONSE apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getCode() == 200) {
                        Type listType = new TypeToken<List<Category>>() {
                        }.getType();
                        List<Category> categoryList = gson.fromJson(apiResponse.getData(), listType);
                        categoryMutableLiveData.postValue(categoryList);
                    }
                }
            }

            @Override
            public void onFailure(Call<API_RESPONSE> call, Throwable t) {

            }
        });

        return categoryMutableLiveData;
    }


}
