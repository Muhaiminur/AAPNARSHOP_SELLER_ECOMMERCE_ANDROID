package com.aapnarshop.seller.Repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.aapnarshop.seller.Http.API_RESPONSE;
import com.aapnarshop.seller.Http.ApiClient;
import com.aapnarshop.seller.Http.ApiInterface;
import com.aapnarshop.seller.Library.KeyWord;
import com.aapnarshop.seller.Library.Utility;
import com.aapnarshop.seller.Model.GET.Area;
import com.aapnarshop.seller.Model.GET.City;
import com.aapnarshop.seller.Model.GET.District;
import com.aapnarshop.seller.Model.GET.Division;
import com.aapnarshop.seller.Model.GET.ShopCategory;
import com.aapnarshop.seller.Model.SEND.Registration;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpRepo {

    private MutableLiveData<List<ShopCategory>> shopCategoryMutableLiveData;
    private MutableLiveData<List<Division>> divisionMutableLiveData;
    private MutableLiveData<List<District>> districtMutableLiveData;
    private MutableLiveData<List<City>> cityMutableLiveData;
    private MutableLiveData<List<Area>> areaMutableLiveData;
    private ApiInterface apiInterface;
    private Utility utility;
    private Gson gson;
    private List<ShopCategory> shopCategoryList;
    private List<Division> divisionList;
    private List<District> districtList;
    private List<City> cityList;
    private List<Area> areaList;
    private API_RESPONSE api_response;
    private MutableLiveData<API_RESPONSE> registrationMutableLiveData;


    public SignUpRepo(Application application) {
        shopCategoryMutableLiveData = new MutableLiveData<>();
        divisionMutableLiveData = new MutableLiveData<>();
        districtMutableLiveData = new MutableLiveData<>();
        cityMutableLiveData = new MutableLiveData<>();
        areaMutableLiveData = new MutableLiveData<>();
        registrationMutableLiveData = new MutableLiveData<>();
        apiInterface = ApiClient.getBaseClient().create(ApiInterface.class);
        utility = new Utility(application);
        gson = new Gson();
    }

    public LiveData<List<ShopCategory>> getShopCategoryList() {
        Call<API_RESPONSE> call = apiInterface.getShopCategory(utility.getAuthorizationKey(), KeyWord.USERID, KeyWord.TOKEN,utility.getLangauge());
        call.enqueue(new Callback<API_RESPONSE>() {
            @Override
            public void onResponse(Call<API_RESPONSE> call, Response<API_RESPONSE> response) {
                try {
                    if (response.isSuccessful() && response != null) {
                        API_RESPONSE apiResponse = response.body();
                        if (apiResponse != null && apiResponse.getCode() == 200) {

                            Type listType = new TypeToken<List<ShopCategory>>() {
                            }.getType();
                            shopCategoryList = gson.fromJson(apiResponse.getData(), listType);
                            shopCategoryMutableLiveData.postValue(shopCategoryList);
                        } else {

                            shopCategoryMutableLiveData.postValue(shopCategoryList);
                            utility.logger("Get Shop Category" + shopCategoryList.size());
                        }

                    }
                } catch (Exception e) {
                    Log.d("Error Line Number", Log.getStackTraceString(e));
                }
            }

            @Override
            public void onFailure(Call<API_RESPONSE> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());
            }
        });


        return shopCategoryMutableLiveData;
    }

    public LiveData<List<Division>> getDivisionList() {
        Call<API_RESPONSE> call = apiInterface.getDivision(utility.getAuthorizationKey(), KeyWord.USERID, KeyWord.TOKEN,utility.getLangauge());
        call.enqueue(new Callback<API_RESPONSE>() {
            @Override
            public void onResponse(Call<API_RESPONSE> call, Response<API_RESPONSE> response) {
                try {
                    if (response.isSuccessful() && response != null) {
                        API_RESPONSE apiResponse = response.body();
                        if (apiResponse != null && apiResponse.getCode() == 200) {

                            Type listType = new TypeToken<List<Division>>() {
                            }.getType();
                            divisionList = gson.fromJson(apiResponse.getData(), listType);
                            divisionMutableLiveData.postValue(divisionList);
                        } else {

                            divisionMutableLiveData.postValue(divisionList);
                            utility.logger("Get Division" + divisionList.size());
                        }

                    }
                } catch (Exception e) {
                    Log.d("Error Line Number", Log.getStackTraceString(e));
                }
            }

            @Override
            public void onFailure(Call<API_RESPONSE> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());
            }
        });


        return divisionMutableLiveData;
    }

    public LiveData<List<District>> getDistrictList(Division division) {
        utility.logger("district");
        Call<API_RESPONSE> call = apiInterface.getDistrict(utility.getAuthorizationKey(), KeyWord.USERID, KeyWord.TOKEN,utility.getLangauge(),division);
        call.enqueue(new Callback<API_RESPONSE>() {
            @Override
            public void onResponse(Call<API_RESPONSE> call, Response<API_RESPONSE> response) {
                try {
                    if (response.isSuccessful() && response != null) {
                        API_RESPONSE apiResponse = response.body();
                        utility.logger("district" + apiResponse.toString());
                        if (apiResponse != null && apiResponse.getCode() == 200) {

                            Type listType = new TypeToken<List<District>>() {
                            }.getType();
                            districtList = gson.fromJson(apiResponse.getData(), listType);
                            districtMutableLiveData.postValue(districtList);
                        } else {

                            districtMutableLiveData.postValue(districtList);
                            utility.logger("Get Division" + districtList.size());
                        }

                    }
                } catch (Exception e) {
                    Log.d("Error Line Number", Log.getStackTraceString(e));
                }
            }

            @Override
            public void onFailure(Call<API_RESPONSE> call, Throwable t) {
                utility.logger("Error" + t.toString());
            }
        });


        return districtMutableLiveData;
    }

    public LiveData<List<City>> getCityList(District district) {

        Call<API_RESPONSE> call = apiInterface.getCity(utility.getAuthorizationKey(), KeyWord.USERID, KeyWord.TOKEN,utility.getLangauge(),district);
        call.enqueue(new Callback<API_RESPONSE>() {
            @Override
            public void onResponse(Call<API_RESPONSE> call, Response<API_RESPONSE> response) {
                try {
                    if (response.isSuccessful() && response != null) {
                        API_RESPONSE apiResponse = response.body();
                        if (apiResponse != null && apiResponse.getCode() == 200) {

                            Type listType = new TypeToken<List<City>>() {
                            }.getType();
                            cityList = gson.fromJson(apiResponse.getData(), listType);
                            cityMutableLiveData.postValue(cityList);
                        } else {

                            cityMutableLiveData.postValue(cityList);
                        }

                    }
                } catch (Exception e) {
                    Log.d("Error Line Number", Log.getStackTraceString(e));
                }
            }

            @Override
            public void onFailure(Call<API_RESPONSE> call, Throwable t) {
                utility.logger("Error" + t.toString());
            }
        });


        return cityMutableLiveData;
    }

    public LiveData<List<Area>> getAreaList(City city) {

        Call<API_RESPONSE> call = apiInterface.getArea(utility.getAuthorizationKey(), KeyWord.USERID, KeyWord.TOKEN,utility.getLangauge(),city);
        call.enqueue(new Callback<API_RESPONSE>() {
            @Override
            public void onResponse(Call<API_RESPONSE> call, Response<API_RESPONSE> response) {
                try {
                    if (response.isSuccessful() && response != null) {
                        API_RESPONSE apiResponse = response.body();
                        if (apiResponse != null && apiResponse.getCode() == 200) {

                            Type listType = new TypeToken<List<Area>>() {
                            }.getType();
                            areaList = gson.fromJson(apiResponse.getData(), listType);
                            areaMutableLiveData.postValue(areaList);
                        } else {

                            areaMutableLiveData.postValue(areaList);
                        }

                    }
                } catch (Exception e) {
                    Log.d("Error Line Number", Log.getStackTraceString(e));
                }
            }

            @Override
            public void onFailure(Call<API_RESPONSE> call, Throwable t) {
                utility.logger("Error" + t.toString());
            }
        });


        return areaMutableLiveData;
    }

    public LiveData<API_RESPONSE> sendRegistration(Registration registration) {

        utility.logger("Registration" + registration.toString());
        Call<API_RESPONSE> call = apiInterface.registration(utility.getAuthorizationKey(), KeyWord.USERID, KeyWord.TOKEN,utility.getLangauge(),registration);
        call.enqueue(new Callback<API_RESPONSE>() {
            @Override
            public void onResponse(Call<API_RESPONSE> call, Response<API_RESPONSE> response) {
                try {
                    if (response.isSuccessful() && response != null) {
                        API_RESPONSE apiResponse = response.body();
                        utility.logger("Registration" + apiResponse.toString());

                        registrationMutableLiveData.postValue(apiResponse);
                    }
                } catch (Exception e) {
                    Log.d("Error Line Number", Log.getStackTraceString(e));
                }
            }

            @Override
            public void onFailure(Call<API_RESPONSE> call, Throwable t) {

            }
        });


        return registrationMutableLiveData;
    }


}
