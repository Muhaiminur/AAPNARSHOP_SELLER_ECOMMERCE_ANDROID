package com.aapnarshop.seller.VIEWMODEL;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.aapnarshop.seller.Http.API_RESPONSE;
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
import com.aapnarshop.seller.Repository.ProductRepo;

import java.util.List;

import okhttp3.RequestBody;

public class ProductViewModel extends AndroidViewModel {

    private ProductRepo productRepo;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        productRepo = new ProductRepo(application);
    }

    public void sendProductDetailsRequest(Product product) {

        productRepo.sendProductDetailsRequest(product);
    }

    public LiveData<ProductDetails> getProductDetails() {
        return productRepo.getProductDetails();
    }

    public void updateProductStatus(ProductStatus productStatus) {

        productRepo.updateProductStatus(productStatus);
    }

    public LiveData<API_RESPONSE> getProductStatusResponse() {

        return productRepo.getProductStatusResponse();
    }

    public void searchCategory(String title) {
        productRepo.searchProductCategory(title);
    }

    public LiveData<List<Category>> getFilteredCategory() {

        return productRepo.getFilteredCategory();
    }

    public void searchSupplier(String title) {
        productRepo.searchSupplier(title);
    }

    public LiveData<List<Supplier>> getFilteredSupplierList() {

        return productRepo.getFilteredSupplierList();
    }

    public void searchProductType(String title, Category category) {
        productRepo.searchProductType(title, category);
    }

    public LiveData<List<ProductType>> getFilteredProductTypeList() {

        return productRepo.getFilteredProductTypeList();
    }

    public MutableLiveData<List<UnitType>> getUnitType() {

        return productRepo.getUnitList();
    }

    public void searchBrand(String title, ProductType productType) {

        productRepo.searchBrand(title, productType);
    }

    public LiveData<List<Brand>> getFilteredBandList() {

        return productRepo.getFilteredBrandList();
    }

    public MutableLiveData<API_RESPONSE> createProduct(RequestBody requestBody) {

        return productRepo.createProduct(requestBody);
    }

    public void updateProduct(RequestBody requestBody) {

        productRepo.updateProduct(requestBody);
    }

    public MutableLiveData<API_RESPONSE> getProductUpdateResponse() {

        return productRepo.getProductUpdateResponse();
    }

    public MutableLiveData<List<AllProduct>> getProductList(FilterProduct filterProduct) {

        return productRepo.getProductList(filterProduct);
    }

    public MutableLiveData<API_RESPONSE> updateProductQuantity(ProductQuantity productQuantity) {
        return productRepo.updateProductQuantity(productQuantity);
    }

    public MutableLiveData<List<Category>> getProductCategory() {
        return productRepo.getProductCategory();
    }
}
