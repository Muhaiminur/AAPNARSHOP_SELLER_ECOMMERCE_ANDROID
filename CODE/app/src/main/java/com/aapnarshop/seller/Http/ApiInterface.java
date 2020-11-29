package com.aapnarshop.seller.Http;

import com.aapnarshop.seller.Model.GET.Category;
import com.aapnarshop.seller.Model.GET.City;
import com.aapnarshop.seller.Model.GET.District;
import com.aapnarshop.seller.Model.GET.Division;
import com.aapnarshop.seller.Model.SEND.AddOfferProduct;
import com.aapnarshop.seller.Model.SEND.FilterProduct;
import com.aapnarshop.seller.Model.GET.ProductType;
import com.aapnarshop.seller.Model.GET.Settings;
import com.aapnarshop.seller.Model.SEND.ChangePassword;
import com.aapnarshop.seller.Model.SEND.LogIn;
import com.aapnarshop.seller.Model.SEND.OfferCategoryRequest;
import com.aapnarshop.seller.Model.SEND.OfferCreate;
import com.aapnarshop.seller.Model.SEND.OfferDetailsRequest;
import com.aapnarshop.seller.Model.SEND.OfferListRequest;
import com.aapnarshop.seller.Model.SEND.OfferProductListRequest;
import com.aapnarshop.seller.Model.SEND.OfferStatus;
import com.aapnarshop.seller.Model.SEND.OfferUpdate;
import com.aapnarshop.seller.Model.SEND.OrderDetailsRequest;
import com.aapnarshop.seller.Model.SEND.OrderListRequest;
import com.aapnarshop.seller.Model.SEND.OrderStatusRequest;
import com.aapnarshop.seller.Model.SEND.Product;
import com.aapnarshop.seller.Model.SEND.ProductQuantity;
import com.aapnarshop.seller.Model.SEND.ProductStatus;
import com.aapnarshop.seller.Model.SEND.Registration;
import com.aapnarshop.seller.Model.SEND.SendDeliveryArea;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
/**
 * Created by Anik Roy on 15,September,2020
 */
public interface ApiInterface {

    //1 App Configuration
    @POST("seller/config")
    Call<API_RESPONSE> getConfig(@Header("Authorization") String apiKey, @Header("userId") String usersId, @Header("token") String token,@Header("Language") String language);

    //2 Shop Category
    @POST("seller/shop/category")
    Call<API_RESPONSE> getShopCategory(@Header("Authorization") String apiKey, @Header("userId") String usersId, @Header("token") String token,@Header("Language") String language);

    //3 Division List
    @POST("general/address/division/list")
    Call<API_RESPONSE> getDivision(@Header("Authorization") String apiKey, @Header("userId") String usersId, @Header("token") String token,@Header("Language") String language);

    //4 District List
    @POST("general/address/district/list")
    Call<API_RESPONSE> getDistrict(@Header("Authorization") String apiKey, @Header("userId") String usersId, @Header("token") String token,@Header("Language") String language, @Body Division division);

    //5 City List
    @POST("general/address/city/list")
    Call<API_RESPONSE> getCity(@Header("Authorization") String apiKey, @Header("userId") String usersId, @Header("token") String token,@Header("Language") String language, @Body District district);

    //6 Area List
    @POST("general/address/area/list")
    Call<API_RESPONSE> getArea(@Header("Authorization") String apiKey, @Header("userId") String usersId, @Header("token") String token,@Header("Language") String language, @Body City city);

    //7 Registration
    @POST("seller/registration")
    Call<API_RESPONSE> registration(@Header("Authorization") String apiKey, @Header("userId") String usersId, @Header("token") String token,@Header("Language") String language, @Body Registration registration);

    //8 Sign In
    @POST("seller/login")
    Call<API_RESPONSE> login(@Header("Authorization") String apiKey, @Header("userId") String usersId, @Header("token") String token,@Header("Language") String language, @Body LogIn logIn);

    //9 Dashboard
    @POST("seller/dashboard")
    Call<API_RESPONSE> getDashboardInfo(@Header("Authorization") String apiKey, @Header("userId") String usersId, @Header("token") String token,@Header("Language") String language);

    //10 Rating
    @POST("seller/rating")
    Call<API_RESPONSE> getRating(@Header("Authorization") String apiKey, @Header("userId") String usersId, @Header("token") String token,@Header("Language") String language);

    //11 Get Settings
    @POST("seller/shop/settings")
    Call<API_RESPONSE> getSettings(@Header("Authorization") String apiKey, @Header("userId") String usersId, @Header("token") String token,@Header("Language") String language);

    //12 Update Settings
    @POST("seller/shop/settings/update")
    Call<API_RESPONSE> updateSettings(@Header("Authorization") String apiKey, @Header("userId") String usersId, @Header("token") String token,@Header("Language") String language, @Body Settings settings);

    //13 Change password
    @POST("seller/password/change")
    Call<API_RESPONSE> changePassword(@Header("Authorization") String apiKey, @Header("userId") String usersId, @Header("token") String token,@Header("Language") String language, @Body ChangePassword changePassword);

    //14 Get Deliver Area
    @POST("seller/delivery/area")
    Call<API_RESPONSE> getDeliveryArea(@Header("Authorization") String apiKey, @Header("userId") String usersId, @Header("token") String token,@Header("Language") String language);

    //15 Update Deliver Area
    @POST("seller/delivery/area/update")
    Call<API_RESPONSE> updateDeliveryArea(@Header("Authorization") String apiKey, @Header("userId") String usersId, @Header("token") String token,@Header("Language") String language, @Body SendDeliveryArea sendDeliveryArea);

    //16 Product Details
    @POST("seller/product")
    Call<API_RESPONSE> getProductDetails(@Header("Authorization") String apiKey, @Header("userId") String usersId, @Header("token") String token,@Header("Language") String language, @Body Product product);

    //17 Product Status
    @POST("seller/product/status/update")
    Call<API_RESPONSE> updateProductStatus(@Header("Authorization") String apiKey, @Header("userId") String usersId, @Header("token") String token,@Header("Language") String language, @Body ProductStatus productStatus);

    //18 Update profile image
    @Multipart
    @POST("seller/profile/image/update")
    Call<API_RESPONSE> updateProfileImage(@Header("Authorization") String apiKey, @Header("userId") String usersId, @Header("token") String token,@Header("Language") String language, @Part MultipartBody.Part files);

    //19 Filter product category
    @POST("seller/product/category/all")
    Call<API_RESPONSE> filterCategory(@Header("Authorization") String apiKey, @Header("userId") String usersId, @Header("token") String token,@Header("Language") String language, @Query("title") String title);

    //20 Filter product category
    @POST("seller/product/supplier/all")
    Call<API_RESPONSE> filterSupplier(@Header("Authorization") String apiKey, @Header("userId") String usersId, @Header("token") String token,@Header("Language") String language, @Query("title") String title);

    //21 Filter product type
    @POST("seller/product/type/all")
    Call<API_RESPONSE> filterProductType(@Header("Authorization") String apiKey, @Header("userId") String usersId, @Header("token") String token,@Header("Language") String language, @Query("title") String title, @Body Category category);

    //22 Forget Password
    @POST("seller/password/forget")
    Call<API_RESPONSE> forgetPassword(@Header("Authorization") String apiKey, @Header("userId") String usersId, @Header("token") String token,@Header("Language") String language,@Body LogIn logIn);

    //23 Unit list
    @POST("seller/unit/all")
    Call<API_RESPONSE> getUnitList(@Header("Authorization") String apiKey, @Header("userId") String usersId, @Header("token") String token,@Header("Language") String language);

    //24 Filter product brand
    @POST("seller/product/brand/all")
    Call<API_RESPONSE> filterBrand(@Header("Authorization") String apiKey, @Header("userId") String usersId, @Header("token") String token,@Header("Language") String language, @Query("title") String title, @Body ProductType productType);

    //25 Create Product
    @POST("seller/product/create")
    Call<API_RESPONSE> createProduct(@Header("Authorization") String apiKey, @Header("userId") String usersId, @Header("token") String token,@Header("Language") String language,@Body RequestBody requestBody);

    //26 All product list
    @POST("seller/product/all")
    Call<API_RESPONSE> getAllProduct(@Header("Authorization") String apiKey, @Header("userId") String usersId, @Header("token") String token, @Header("Language") String language, @Body FilterProduct filterProduct);

    //27 Update product quantity
    @POST("seller/product/quantity/update")
    Call<API_RESPONSE> updateProductQuantity(@Header("Authorization") String apiKey, @Header("userId") String usersId, @Header("token") String token, @Header("Language") String language, @Body ProductQuantity productQuantity);

    //28 Product Category for filtering
    @POST("seller/product/filter/category/all")
    Call<API_RESPONSE> getProductCategory(@Header("Authorization") String apiKey, @Header("userId") String usersId, @Header("token") String token, @Header("Language") String language);

    //29 Update product
    @POST("seller/product/update")
    Call<API_RESPONSE> updateProduct(@Header("Authorization") String apiKey, @Header("userId") String usersId, @Header("token") String token,
                                     @Header("Language") String language,@Body RequestBody requestBody);

    //30 Order list
    @POST("seller/order/all")
    Call<API_RESPONSE> getOrderList(@Header("Authorization") String apiKey, @Header("userId") String usersId, @Header("token") String token, @Header("Language") String language, @Body OrderListRequest orderListRequest);

    //31 Order Status Change
    @POST("seller/order/status/update")
    Call<API_RESPONSE> updateOrderStatus(@Header("Authorization") String apiKey, @Header("userId") String usersId, @Header("token") String token, @Header("Language") String language, @Body OrderStatusRequest orderStatusRequest);

    //32 Order Details
    @POST("seller/order")
    Call<API_RESPONSE> getOrderDetails(@Header("Authorization") String apiKey, @Header("userId") String usersId, @Header("token") String token, @Header("Language") String language, @Body OrderDetailsRequest orderDetailsRequest);

    //33 Offer type list
    @POST("seller/offer/type/all")
    Call<API_RESPONSE> getOfferType(@Header("Authorization") String apiKey, @Header("userId") String usersId, @Header("token") String token, @Header("Language") String language);

    //34 Offer list
    @POST("seller/offer/all")
    Call<API_RESPONSE> getOfferList(@Header("Authorization") String apiKey, @Header("userId") String usersId, @Header("token") String token, @Header("Language") String language, @Body OfferListRequest offerListRequest);

    //35 Offer category list
    @POST("seller/offer/category/all")
    Call<API_RESPONSE> getOfferCategory(@Header("Authorization") String apiKey, @Header("userId") String usersId, @Header("token") String token, @Header("Language") String language, @Body OfferCategoryRequest offerCategoryRequest);

    //36 Offer details
    @POST("seller/offer")
    Call<API_RESPONSE> getOfferDetails(@Header("Authorization") String apiKey, @Header("userId") String usersId, @Header("token") String token, @Header("Language") String language, @Body OfferDetailsRequest offerDetailsRequest);

    //37 Offer status
    @POST("seller/offer/status/update")
    Call<API_RESPONSE> updateOfferStatus(@Header("Authorization") String apiKey, @Header("userId") String usersId, @Header("token") String token, @Header("Language") String language, @Body OfferStatus offerStatus);

    //38 Offer Create
    @POST("seller/offer/create")
    Call<API_RESPONSE> createOffer(@Header("Authorization") String apiKey, @Header("userId") String usersId, @Header("token") String token, @Header("Language") String language, @Body OfferCreate offerCreate);

    //39 Offer Update
    @POST("seller/offer/update")
    Call<API_RESPONSE> updateOffer(@Header("Authorization") String apiKey, @Header("userId") String usersId, @Header("token") String token, @Header("Language") String language, @Body OfferUpdate offerUpdate);

    //40 Offer Product list
    @POST("seller/offer/product/all")
    Call<API_RESPONSE> getOfferProductList(@Header("Authorization") String apiKey, @Header("userId") String usersId, @Header("token") String token, @Header("Language") String language, @Body OfferProductListRequest offerProductListRequest);

    //41 Add Offer Product
    @POST("seller/offer/product/add")
    Call<API_RESPONSE> addOfferProduct(@Header("Authorization") String apiKey, @Header("userId") String usersId, @Header("token") String token, @Header("Language") String language, @Body AddOfferProduct addOfferProduct);

}
