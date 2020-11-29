package com.aapnarshop.seller.VIEW.FRAGMENT.authentication;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.aapnarshop.seller.Library.Utility;
import com.aapnarshop.seller.Model.GET.Area;
import com.aapnarshop.seller.Model.GET.City;
import com.aapnarshop.seller.Model.GET.District;
import com.aapnarshop.seller.Model.GET.Division;
import com.aapnarshop.seller.Model.GET.ShopCategory;
import com.aapnarshop.seller.Model.SEND.Registration;
import com.aapnarshop.seller.R;
import com.aapnarshop.seller.VIEW.Adapter.GenericAdapter;
import com.aapnarshop.seller.VIEWMODEL.SignUpViewModel;
import com.aapnarshop.seller.databinding.FragmentSignUpBinding;

import java.util.ArrayList;
import java.util.List;

public class SignUp extends Fragment {


    private FragmentSignUpBinding binding;
    private SignUpViewModel signUpViewModel;
    private List<ShopCategory> shopCategoryList = new ArrayList<>();
    private List<Division> divisionList = new ArrayList<>();
    private List<District> districtList = new ArrayList<>();
    private List<City> cityList = new ArrayList<>();
    private List<Area> areaList = new ArrayList<>();
    private Utility utility;
    private String shopType, areaId;


    public SignUp() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false);

        try {
            signUpViewModel = new ViewModelProvider(getActivity()).get(SignUpViewModel.class);
            utility = new Utility(getActivity());
            observeData();

            districtList.add(new District("District"));
            initDistrictAdapter(districtList);
            cityList.add(new City("City"));
            initCityAdapter(cityList);
            areaList.add(new Area("Area"));
            initAreaAdapter(areaList);

            //Select Spinner Item
            binding.signUpShopType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if (position == 0) {

                        shopType = "";

                    } else {
                        binding.signUpShopCategoryError.setVisibility(View.GONE);
                        shopType = String.valueOf(shopCategoryList.get(position).getShopCategoryId());
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            binding.signUpDivision.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if (position == 0) {
                        districtList.clear();
                        districtList.add(new District("District"));
                        initDistrictAdapter(districtList);
                    } else {
                        String divisionId = divisionList.get(position).getDivisionId();
                        Division division = new Division(divisionId);
                        observeDistrictData(division);

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            binding.signUpDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 0) {
                        cityList.clear();
                        cityList.add(new City("City"));
                        initCityAdapter(cityList);
                    } else {
                        String districtId = districtList.get(position).getDistrictId();
                        District district = new District();
                        district.setDistrictId(districtId);
                        observeCityData(district);

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            binding.signUpCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 0) {
                        areaList.clear();
                        areaList.add(new Area("Area"));
                        initAreaAdapter(areaList);
                    } else {
                        String cityId = cityList.get(position).getCityId();
                        City city = new City();
                        city.setCityId(cityId);
                        observeAreaData(city);

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            binding.signUpArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 0) {
                        areaId = "";
                    } else {
                        binding.signUpAreaError.setVisibility(View.GONE);
                        areaId = areaList.get(position).getAreaId();

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            binding.signUpBackBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_signUpFragment_to_signInFragment));

            binding.signUp.setOnClickListener(this::validation);

        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }


        return binding.getRoot();
    }

    private void validation(View v) {
        if (TextUtils.isEmpty(binding.signUpFullName.getText().toString().trim())) {
            binding.signUpFullNameLayout.setError("Enter your full name");
            binding.signUpFullName.requestFocus();

        } else if (TextUtils.isEmpty(binding.signUpPhone.getText().toString()) || !utility.validateMsisdn(binding.signUpPhone.getText().toString())) {

            binding.signUpPhoneLayout.setError("Enter valid phone number");
            binding.signUpPhone.requestFocus();
        } else if (TextUtils.isEmpty(binding.signUpShopName.getText().toString())) {
            binding.signUpShopNameLayout.setError("Enter your shop name");
            binding.signUpShopName.requestFocus();
        } else if (TextUtils.isEmpty(binding.signUpShopAddress.getText().toString())) {
            binding.signUpShopAddressLayout.setError("Enter your shop address");
            binding.signUpShopAddress.requestFocus();

        } else if (TextUtils.isEmpty(shopType)) {
            binding.signUpShopCategoryError.setVisibility(View.VISIBLE);
            binding.signUpShopCategoryError.setText("Select shop type");
        } else if (TextUtils.isEmpty(areaId)) {
            binding.signUpAreaError.setVisibility(View.VISIBLE);
            binding.signUpAreaError.setText("Select Area");
        } else {
            Registration registration = new Registration(binding.signUpFullName.getText().toString(), binding.signUpPhone.getText().toString(),
                    binding.signUpEmail.getText().toString(), shopType, binding.signUpShopName.getText().toString(), areaId,
                    binding.signUpShopAddress.getText().toString());

            sendRegistration(registration, v);
        }


    }

    private void sendRegistration(Registration registration, View v) {

        signUpViewModel.sendRegistration(registration).observe(getViewLifecycleOwner(), api_response -> {

            if (api_response.getCode() == 200) {
                SignUpDirections.ActionSignUpFragmentToSignOutFragment action =  SignUpDirections.actionSignUpFragmentToSignOutFragment();
                action.setMessage(api_response.getData().getAsString());
                Navigation.findNavController(v).navigate(action);

            } else if (api_response.getCode() == 201) {
                SignUpDirections.ActionSignUpFragmentToSignOutFragment action =  SignUpDirections.actionSignUpFragmentToSignOutFragment();
                action.setMessage(api_response.getData().getAsString());
                Navigation.findNavController(v).navigate(action);

            }
            else if (api_response.getCode() == 202) {
                SignUpDirections.ActionSignUpFragmentToSignOutFragment action =  SignUpDirections.actionSignUpFragmentToSignOutFragment();
                action.setMessage(api_response.getData().getAsString());
                Navigation.findNavController(v).navigate(action);

            }


        });
    }

    // Getting shop category list & Division list
    private void observeData() {
        // Shop Category
        signUpViewModel.getShopCategoryList().observe(getViewLifecycleOwner(), shopCategories -> {

            if (shopCategories.size() > 0) {
                shopCategoryList.clear();
                shopCategoryList.add(new ShopCategory("Shop Type"));
                shopCategoryList.addAll(shopCategories);
                binding.signUpShopType.setAdapter(new GenericAdapter<ShopCategory>(getActivity(),shopCategoryList) {
                    @Override
                    public View getCustomView(int position, View view, ViewGroup parent) {

                        ShopCategory model = getItem(position);
                        View spinnerRow = LayoutInflater.from(parent.getContext()).inflate(com.aapnarshop.seller.R.layout.custom_spinner_item, parent, false);

                        TextView label = spinnerRow.findViewById(com.aapnarshop.seller.R.id.custom_spinner);
                        label.setText(model.getShopCategoryName());
                        if (position == 0) {
                            label.setTextColor(getActivity().getResources().getColor(R.color.app_black2));
                            label.setTypeface(label.getTypeface(), Typeface.BOLD);

                        }

                        return spinnerRow;
                    }
                });


            }


        });

        // Division List
        signUpViewModel.getDivisionList().observe(getViewLifecycleOwner(), divisions -> {

            if (divisions.size() > 0) {
                divisionList.clear();
                Division division = new Division();
                division.setDivisionName("Division");
                divisionList.add(division);
                divisionList.addAll(divisions);
                binding.signUpDivision.setAdapter(new GenericAdapter<Division>(getActivity(),divisionList) {
                    @Override
                    public View getCustomView(int position, View view, ViewGroup parent) {

                        Division model = getItem(position);
                        View spinnerRow = LayoutInflater.from(parent.getContext()).inflate(com.aapnarshop.seller.R.layout.custom_spinner_item, parent, false);

                        TextView label = spinnerRow.findViewById(com.aapnarshop.seller.R.id.custom_spinner);
                        label.setText(model.getDivisionName());
                        if (position == 0) {
                            label.setTextColor(getActivity().getResources().getColor(R.color.app_black2));
                            label.setTypeface(label.getTypeface(), Typeface.BOLD);

                        }

                        return spinnerRow;
                    }
                });

            }

        });

    }

    // Getting District List
    private void observeDistrictData(Division division) {

        signUpViewModel.getDistrictList(division).observe(getViewLifecycleOwner(), districts -> {
            districtList.clear();
            districtList.add(new District("District"));
            districtList.addAll(districts);

            initDistrictAdapter(districtList);

        });
    }

    //Getting City List
    private void observeCityData(District district) {

        signUpViewModel.getCityList(district).observe(getViewLifecycleOwner(), cities -> {
            cityList.clear();
            cityList.add(new City("City"));
            cityList.addAll(cities);

            initCityAdapter(cityList);

        });
    }

    //Getting Area List
    private void observeAreaData(City city) {

        signUpViewModel.getAreaList(city).observe(getViewLifecycleOwner(), areas -> {
            areaList.clear();
            areaList.add(new Area("Area"));
            areaList.addAll(areas);

            initAreaAdapter(areaList);

        });
    }

    //Initialize Adapter
    private void initAreaAdapter(List<Area> areaList) {
        try {
            binding.signUpArea.setAdapter(new GenericAdapter<Area>(getActivity(),areaList) {
                @Override
                public View getCustomView(int position, View view, ViewGroup parent) {

                    Area model = getItem(position);
                    View spinnerRow = LayoutInflater.from(parent.getContext()).inflate(com.aapnarshop.seller.R.layout.custom_spinner_item, parent, false);

                    TextView label = spinnerRow.findViewById(com.aapnarshop.seller.R.id.custom_spinner);

                    label.setText(model.getAreaName());
                    if (position == 0) {
                        label.setTextColor(getActivity().getResources().getColor(R.color.app_black2));
                        label.setTypeface(label.getTypeface(), Typeface.BOLD);
                    }

                    return spinnerRow;
                }
            });
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    private void initCityAdapter(List<City> cityList) {
        try {
            binding.signUpCity.setAdapter(new GenericAdapter<City>(getActivity(),cityList) {
                @Override
                public View getCustomView(int position, View view, ViewGroup parent) {

                    City model = getItem(position);
                    View spinnerRow = LayoutInflater.from(parent.getContext()).inflate(com.aapnarshop.seller.R.layout.custom_spinner_item, parent, false);

                    TextView label = spinnerRow.findViewById(com.aapnarshop.seller.R.id.custom_spinner);

                    label.setText(model.getCityName());
                    if (position == 0) {
                        label.setTextColor(getActivity().getResources().getColor(R.color.app_black2));
                        label.setTypeface(label.getTypeface(), Typeface.BOLD);
                    }

                    return spinnerRow;
                }
            });
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    private void initDistrictAdapter(List<District> districts) {

        try {
            binding.signUpDistrict.setAdapter(new GenericAdapter<District>(getActivity(),districts) {
                @Override
                public View getCustomView(int position, View view, ViewGroup parent) {

                    District model = getItem(position);
                    View spinnerRow = LayoutInflater.from(parent.getContext()).inflate(com.aapnarshop.seller.R.layout.custom_spinner_item, parent, false);

                    TextView label = spinnerRow.findViewById(com.aapnarshop.seller.R.id.custom_spinner);

                    label.setText(model.getDistrictName());
                    if (position == 0) {
                        label.setTextColor(getActivity().getResources().getColor(R.color.app_black2));
                        label.setTypeface(label.getTypeface(), Typeface.BOLD);
                    }

                    return spinnerRow;
                }
            });
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }


    }


}