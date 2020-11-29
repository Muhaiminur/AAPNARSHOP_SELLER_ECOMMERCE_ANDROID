package com.aapnarshop.seller.VIEW.FRAGMENT;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.ListPopupWindow;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.aapnarshop.seller.Library.KeyWord;
import com.aapnarshop.seller.Library.Utility;
import com.aapnarshop.seller.Model.GET.Area;
import com.aapnarshop.seller.Model.GET.City;
import com.aapnarshop.seller.Model.GET.District;
import com.aapnarshop.seller.Model.GET.Division;
import com.aapnarshop.seller.Model.SEND.SendDeliveryArea;
import com.aapnarshop.seller.R;
import com.aapnarshop.seller.VIEW.Adapter.DeliveryAreaAdapter;
import com.aapnarshop.seller.VIEW.Adapter.PopupWindowAdapter;
import com.aapnarshop.seller.VIEWMODEL.DeliveryAreaViewModel;
import com.aapnarshop.seller.VIEWMODEL.SignUpViewModel;
import com.aapnarshop.seller.databinding.FragmentDeliveryAreaBinding;
import com.aapnarshop.seller.databinding.RecyclerviewDeliveryAreaBinding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class DeliveryAreaFragment extends Fragment {


    private FragmentDeliveryAreaBinding binding;
    private Utility utility;
    private DeliveryAreaViewModel deliveryAreaViewModel;
    private SignUpViewModel signUpViewModel;
    private List<Division> divisionList;
    private List<District> districtList;
    private List<City> cityList;
    private List<Area> areaList;
    private final List<Division> currentDivisionList = new ArrayList<>();
    private final List<District> currentDistrictList = new ArrayList<>();
    private final List<City> currentCityList = new ArrayList<>();
    private final List<Area> currentAreaList = new ArrayList<>();
    private final HashSet<Division> divisionHashSet = new HashSet<>();
    private final HashSet<District> districtHashSet = new HashSet<>();
    private final HashSet<City> cityHashSet = new HashSet<>();
    private final HashSet<Area> areaHashSet = new HashSet<>();
    private final HashSet<District> receiveDistrictHashSet = new HashSet<>();
    private final HashSet<City> receiveCityHashSet = new HashSet<>();
    private final HashSet<Area> receiveAreaHashSet = new HashSet<>();


    public DeliveryAreaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_delivery_area, container, false);
        try {
            deliveryAreaViewModel = new ViewModelProvider(this).get(DeliveryAreaViewModel.class);
            signUpViewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
            utility = new Utility(getActivity());
            Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
            TextView textToolHeader = toolbar.findViewById(R.id.title);
            textToolHeader.setText(utility.getLangauge().equals(KeyWord.BANGLA) ? KeyWord.DELIVERY_AREA_BN : KeyWord.DELIVERY_AREA);

            String language = utility.getLangauge();
            divisionList = new ArrayList<>();
            districtList = new ArrayList<>();
            cityList = new ArrayList<>();
            areaList = new ArrayList<>();

            //======Change Language==========
            binding.deliveryAreaDivisionTv.setText(language.equals(KeyWord.BANGLA) ? getString(R.string.division_bn) : getString(R.string.division));
            binding.deliveryAreaDistrictTv.setText(language.equals(KeyWord.BANGLA) ? getString(R.string.district_bn) : getString(R.string.district));
            binding.deliveryAreaCityTv.setText(language.equals(KeyWord.BANGLA) ? getString(R.string.city_bn) : getString(R.string.city));
            binding.deliveryAreaAreaTv.setText(language.equals(KeyWord.BANGLA) ? getString(R.string.area_bn) : getString(R.string.area));
            binding.deliveryAreaSelectDivision.setText(language.equals(KeyWord.BANGLA) ? getString(R.string.select_division_bn) : getString(R.string.select_division));
            binding.deliveryAreaSelectDistrict.setText(language.equals(KeyWord.BANGLA) ? getString(R.string.select_district_bn) : getString(R.string.select_district));
            binding.deliveryAreaSelectCity.setText(language.equals(KeyWord.BANGLA) ? getString(R.string.select_city_bn) : getString(R.string.select_city));
            binding.deliveryAreaSelectArea.setText(language.equals(KeyWord.BANGLA) ? getString(R.string.select_area_bn) : getString(R.string.select_area));
            //=======End=============

            observeDeliveryAreaData();

            // Click event for popup menu
            binding.deliveryAreaDivision.setOnClickListener(this::showDivisionListPopupWindow);
            binding.deliveryAreaDistrict.setOnClickListener(this::showDistrictListPopupWindow);
            binding.deliveryAreaCity.setOnClickListener(this::showCityListPopupWindow);
            binding.deliveryAreaArea.setOnClickListener(this::showAreaListPopupWindow);

            binding.deliveryAreaSave.setOnClickListener(v -> {
                StringBuilder sb = new StringBuilder();
                SendDeliveryArea sendDeliveryArea = new SendDeliveryArea();

                if (currentDivisionList.size() > 0 && currentDistrictList.size() <= 0 && currentCityList.size() <= 0 && currentAreaList.size() <= 0) {
                    String result = "";
                    for (Division division : currentDivisionList) {
                        sb.append(division.getDivisionId()).append(",");
                    }
                    result = sb.deleteCharAt(sb.length() - 1).toString();
                    sendDeliveryArea.setDivisionId(result);
                    sendDeliveryArea.setDistrictId("");
                    sendDeliveryArea.setCityId("");
                    sendDeliveryArea.setAreaId("");
                    deliveryAreaViewModel.updateDeliveryArea(sendDeliveryArea);
                    utility.logger("Division " + sendDeliveryArea.toString());

                } else if (currentDivisionList.size() > 0 && currentDistrictList.size() > 0 && currentCityList.size() <= 0 && currentAreaList.size() <= 0) {
                    String result = "";
                    for (District district : currentDistrictList) {
                        sb.append(district.getDistrictId()).append(",");
                    }
                    result = sb.deleteCharAt(sb.length() - 1).toString();
                    sendDeliveryArea.setDivisionId("");
                    sendDeliveryArea.setDistrictId(result);
                    sendDeliveryArea.setCityId("");
                    sendDeliveryArea.setAreaId("");
                    deliveryAreaViewModel.updateDeliveryArea(sendDeliveryArea);
                    utility.logger("District " + sendDeliveryArea.toString());
                } else if (currentDivisionList.size() > 0 && currentDistrictList.size() > 0 && currentCityList.size() > 0 && currentAreaList.size() <= 0) {
                    String result = "";
                    for (City city : currentCityList) {
                        sb.append(city.getCityId()).append(",");
                    }
                    result = sb.deleteCharAt(sb.length() - 1).toString();
                    sendDeliveryArea.setDivisionId("");
                    sendDeliveryArea.setDistrictId("");
                    sendDeliveryArea.setCityId(result);
                    sendDeliveryArea.setAreaId("");
                    deliveryAreaViewModel.updateDeliveryArea(sendDeliveryArea);
                    utility.logger("City " + sendDeliveryArea.toString());
                } else if (currentDivisionList.size() > 0 && currentDistrictList.size() > 0 && currentCityList.size() > 0 && currentAreaList.size() > 0) {
                    String result = "";
                    for (Area area : currentAreaList) {
                        sb.append(area.getAreaId()).append(",");
                    }
                    result = sb.deleteCharAt(sb.length() - 1).toString();
                    sendDeliveryArea.setDivisionId("");
                    sendDeliveryArea.setDistrictId("");
                    sendDeliveryArea.setCityId("");
                    sendDeliveryArea.setAreaId(result);
                    deliveryAreaViewModel.updateDeliveryArea(sendDeliveryArea);
                    utility.logger("Area " + sendDeliveryArea.toString());
                } else {
                    utility.showDialog("Select Delivery area");
                }
            });

        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }

        return binding.getRoot();
    }

    // Get delivery area & division list for popup window
    private void observeDeliveryAreaData() {
        deliveryAreaViewModel.getDeliveryArea().observe(getActivity(), deliveryArea -> {
            if (deliveryArea.getDivisions().size() > 0) {
                initDivisionAdapter(deliveryArea.getDivisions());
                currentDivisionList.clear();
                currentDivisionList.addAll(deliveryArea.getDivisions());

            }
            if (deliveryArea.getDistricts().size() > 0) {
                initDistrictAdapter(deliveryArea.getDistricts());
                currentDistrictList.clear();
                currentDistrictList.addAll(deliveryArea.getDistricts());

            }
            if (deliveryArea.getCities().size() > 0) {
                initCityAdapter(deliveryArea.getCities());
                currentCityList.clear();

                currentCityList.addAll(deliveryArea.getCities());

            }
            if (deliveryArea.getAreas().size() > 0) {
                initAreaAdapter(deliveryArea.getAreas());
                currentAreaList.clear();
                currentAreaList.addAll(deliveryArea.getAreas());
            }
            utility.logger("Get division" + deliveryArea.getDivisions().toString());
        });

        signUpViewModel.getDivisionList().observe(getViewLifecycleOwner(), divisions -> {

            if (divisions.size() > 0) {
                divisionList.clear();
                divisionList.addAll(divisions);
            }

        });

        deliveryAreaViewModel.getUpdateDeliveryAreaResponse().observe(getActivity(), apiResponse -> {
            if (apiResponse.getCode() == 200) {
                utility.showDialog(apiResponse.getData().getAsString());
            } else if ((apiResponse.getCode() == 333)) {
                utility.showDialog(apiResponse.getData().getAsString());

            }
        });
    }

    // Getting District List for popup window
    private void observeDistrictData(Division division) {

        signUpViewModel.getDistrictList(division).observe(getViewLifecycleOwner(), districts -> {

            receiveDistrictHashSet.addAll(districts);
            districtList.clear();
            districtList.addAll(receiveDistrictHashSet);

        });
    }

    //Getting City List for popup
    private void observeCityData(District district) {

        signUpViewModel.getCityList(district).observe(getViewLifecycleOwner(), cities -> {
            receiveCityHashSet.addAll(cities);
            cityList.clear();
            cityList.addAll(receiveCityHashSet);

        });
    }

    //Getting Area List for popup window
    private void observeAreaData(City city) {
        signUpViewModel.getAreaList(city).observe(getViewLifecycleOwner(), areas -> {
            receiveAreaHashSet.addAll(areas);
            areaList.clear();
            areaList.addAll(receiveAreaHashSet);

        });
    }

    // Initialize adapter to show division,district,city,area
    private void initDivisionAdapter(List<Division> divisions) {
        DeliveryAreaAdapter divisionAdapter = new DeliveryAreaAdapter<Division>(getActivity(), divisions) {
            @Override
            public RecyclerView.ViewHolder setViewHolder(ViewGroup parent) {
                RecyclerviewDeliveryAreaBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.recyclerview_delivery_area, parent, false);
                return new ViewHolder(binding);
            }

            @Override
            public void onBindData(RecyclerView.ViewHolder holder, Division division, int position) {
                ViewHolder divisionViewHolder = (ViewHolder) holder;
                divisionViewHolder.binding.recyclerviewDeliveryAreaTitle.setText(division.getDivisionName());
                divisionViewHolder.binding.recyclerviewDeliveryAreaClear.setOnClickListener(v -> {
                    districtList.clear();
                    cityList.clear();
                    areaList.clear();
                    currentDivisionList.clear();
                    currentDistrictList.clear();
                    currentCityList.clear();
                    currentAreaList.clear();
                    initDistrictAdapter(currentDistrictList);
                    initCityAdapter(currentCityList);
                    initAreaAdapter(currentAreaList);
                    currentDivisionList.remove(division);
                    divisions.remove(division);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, divisions.size());
                    if (divisions.isEmpty()) {
                        currentDivisionList.clear();
                        districtList.clear();
                        binding.deliveryAreaDivisionRecyclerView.setVisibility(View.GONE);
                        binding.deliveryAreaSelectDivision.setVisibility(View.VISIBLE);
                        notifyDataSetChanged();
                    }
                });
            }
        };
        binding.deliveryAreaSelectDivision.setVisibility(View.GONE);
        binding.deliveryAreaDivisionRecyclerView.setVisibility(View.VISIBLE);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL);
        binding.deliveryAreaDivisionRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        binding.deliveryAreaDivisionRecyclerView.setAdapter(divisionAdapter);
        divisionAdapter.notifyDataSetChanged();

    }

    private void initDistrictAdapter(List<District> districts) {
        DeliveryAreaAdapter districtAdater = new DeliveryAreaAdapter<District>(getActivity(), districts) {
            @Override
            public RecyclerView.ViewHolder setViewHolder(ViewGroup parent) {
                RecyclerviewDeliveryAreaBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.recyclerview_delivery_area, parent, false);
                return new ViewHolder(binding);
            }

            @Override
            public void onBindData(RecyclerView.ViewHolder holder, District district, int position) {
                ViewHolder viewHolder = (ViewHolder) holder;
                viewHolder.binding.recyclerviewDeliveryAreaTitle.setText(district.getDistrictName());
                viewHolder.binding.recyclerviewDeliveryAreaClear.setOnClickListener(v -> {
                    cityList.clear();
                    areaList.clear();
                    currentCityList.clear();
                    currentAreaList.clear();
                    initCityAdapter(currentCityList);
                    initAreaAdapter(currentAreaList);
                    currentDistrictList.remove(district);
                    districts.remove(district);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, districts.size());
                    if (districts.isEmpty()) {
                        binding.deliveryAreaDistrictRecyclerView.setVisibility(View.GONE);
                        binding.deliveryAreaSelectDistrict.setVisibility(View.VISIBLE);
                        notifyDataSetChanged();
                    }
                });
            }
        };

        if (districts.size() == 0) {
            binding.deliveryAreaSelectDistrict.setVisibility(View.VISIBLE);
            binding.deliveryAreaDistrictRecyclerView.setVisibility(View.GONE);
            districtAdater.notifyDataSetChanged();
        } else {

            binding.deliveryAreaSelectDistrict.setVisibility(View.GONE);
            binding.deliveryAreaDistrictRecyclerView.setVisibility(View.VISIBLE);
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL);
            binding.deliveryAreaDistrictRecyclerView.setLayoutManager(staggeredGridLayoutManager);
            binding.deliveryAreaDistrictRecyclerView.setAdapter(districtAdater);
            districtAdater.notifyDataSetChanged();
        }


    }

    private void initCityAdapter(List<City> cities) {
        DeliveryAreaAdapter cityAdapter = new DeliveryAreaAdapter<City>(getActivity(), cities) {
            @Override
            public RecyclerView.ViewHolder setViewHolder(ViewGroup parent) {
                RecyclerviewDeliveryAreaBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.recyclerview_delivery_area, parent, false);
                return new ViewHolder(binding);
            }

            @Override
            public void onBindData(RecyclerView.ViewHolder holder, City city, int position) {
                ViewHolder viewHolder = (ViewHolder) holder;
                viewHolder.binding.recyclerviewDeliveryAreaTitle.setText(city.getCityName());
                viewHolder.binding.recyclerviewDeliveryAreaClear.setOnClickListener(v -> {
                    cities.remove(city);
                    areaList.clear();
                    currentAreaList.clear();
                    initAreaAdapter(currentAreaList);
                    currentCityList.remove(city);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, cities.size());
                    if (cities.isEmpty()) {
                        binding.deliveryAreaCityRecyclerview.setVisibility(View.GONE);
                        binding.deliveryAreaSelectCity.setVisibility(View.VISIBLE);
                        notifyDataSetChanged();
                    }
                });

            }
        };
        if (cities.size() == 0) {
            binding.deliveryAreaSelectCity.setVisibility(View.VISIBLE);
            binding.deliveryAreaCityRecyclerview.setVisibility(View.GONE);
            cityAdapter.notifyDataSetChanged();

        } else {
            binding.deliveryAreaSelectCity.setVisibility(View.GONE);
            binding.deliveryAreaCityRecyclerview.setVisibility(View.VISIBLE);
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
            binding.deliveryAreaCityRecyclerview.setLayoutManager(staggeredGridLayoutManager);
            binding.deliveryAreaCityRecyclerview.setAdapter(cityAdapter);
            cityAdapter.notifyDataSetChanged();
        }


    }

    private void initAreaAdapter(List<Area> areas) {
        DeliveryAreaAdapter areaAdapter = new DeliveryAreaAdapter<Area>(getActivity(), areas) {
            @Override
            public RecyclerView.ViewHolder setViewHolder(ViewGroup parent) {
                RecyclerviewDeliveryAreaBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.recyclerview_delivery_area, parent, false);
                return new ViewHolder(binding);
            }

            @Override
            public void onBindData(RecyclerView.ViewHolder holder, Area area, int position) {
                ViewHolder viewHolder = (ViewHolder) holder;
                viewHolder.binding.recyclerviewDeliveryAreaTitle.setText(area.getAreaName());
                viewHolder.binding.recyclerviewDeliveryAreaClear.setOnClickListener(v -> {
                    areas.remove(area);
                    areas.remove(area);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, areas.size());
                    if (areas.isEmpty()) {
                        binding.deliveryAreaAreaRecyclerview.setVisibility(View.GONE);
                        binding.deliveryAreaSelectArea.setVisibility(View.VISIBLE);
                        notifyDataSetChanged();
                    }
                });

            }
        };
        if (areas.size() == 0) {
            binding.deliveryAreaSelectArea.setVisibility(View.VISIBLE);
            binding.deliveryAreaAreaRecyclerview.setVisibility(View.GONE);
        } else {
            binding.deliveryAreaSelectArea.setVisibility(View.GONE);
            binding.deliveryAreaAreaRecyclerview.setVisibility(View.VISIBLE);
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
            binding.deliveryAreaAreaRecyclerview.setLayoutManager(staggeredGridLayoutManager);
            binding.deliveryAreaAreaRecyclerview.setAdapter(areaAdapter);
            areaAdapter.notifyDataSetChanged();
        }


    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerviewDeliveryAreaBinding binding;

        public ViewHolder(RecyclerviewDeliveryAreaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    //Show popup menu for division
    private void showDivisionListPopupWindow(View anchor) {

        ListPopupWindow listPopupWindow = createPopupWindowForDivision(anchor, divisionList);
        listPopupWindow.setOnItemClickListener((parent, view, position, id) -> {
            listPopupWindow.dismiss();
            binding.deliveryAreaSelectDivision.setVisibility(View.GONE);
            binding.deliveryAreaDivisionRecyclerView.setVisibility(View.VISIBLE);
            Division division = new Division();
            division.setDivisionId(divisionList.get(position).getDivisionId());
            division.setDivisionName(divisionList.get(position).getDivisionName());
            divisionHashSet.clear();
            divisionHashSet.add(division);
            divisionHashSet.addAll(currentDivisionList);
            currentDivisionList.clear();
            currentDivisionList.addAll(divisionHashSet);
            initDivisionAdapter(currentDivisionList);

            observeDistrictData(division);

        });
        listPopupWindow.show();
    }

    //Show popup menu for district
    private void showDistrictListPopupWindow(View anchor) {


        ListPopupWindow listPopupWindow = createPopupWindowForDistrict(anchor, districtList);

        listPopupWindow.setOnItemClickListener((parent, view, position, id) -> {
            listPopupWindow.dismiss();
            binding.deliveryAreaSelectDistrict.setVisibility(View.GONE);
            binding.deliveryAreaDistrictRecyclerView.setVisibility(View.VISIBLE);
            String districtId = districtList.get(position).getDistrictId();
            District district = new District();
            district.setDistrictId(districtId);
            district.setDistrictName(districtList.get(position).getDistrictName());
            districtHashSet.clear();
            districtHashSet.add(district);
            districtHashSet.addAll(currentDistrictList);
            currentDistrictList.clear();
            currentDistrictList.addAll(districtHashSet);
            initDistrictAdapter(currentDistrictList);
            observeCityData(district);

        });
        listPopupWindow.show();
    }

    //Show popup menu for city
    private void showCityListPopupWindow(View anchor) {

        ListPopupWindow listPopupWindow = createPopupWindowForCity(anchor, cityList);

        listPopupWindow.setOnItemClickListener((parent, view, position, id) -> {
            listPopupWindow.dismiss();
            binding.deliveryAreaSelectCity.setVisibility(View.GONE);
            binding.deliveryAreaCityRecyclerview.setVisibility(View.VISIBLE);
            City city = new City();
            String cityId = cityList.get(position).getCityId();
            city.setCityId(cityId);
            city.setCityName(cityList.get(position).getCityName());
            cityHashSet.clear();
            cityHashSet.add(city);
            cityHashSet.addAll(currentCityList);
            currentCityList.clear();
            currentCityList.addAll(cityHashSet);
            initCityAdapter(currentCityList);
            observeAreaData(city);

        });
        listPopupWindow.show();
    }

    //Show popup menu for area
    private void showAreaListPopupWindow(View anchor) {

        ListPopupWindow listPopupWindow = createPopupWindowForArea(anchor, areaList);

        listPopupWindow.setOnItemClickListener((parent, view, position, id) -> {
            listPopupWindow.dismiss();
            binding.deliveryAreaSelectArea.setVisibility(View.GONE);
            binding.deliveryAreaAreaRecyclerview.setVisibility(View.VISIBLE);
            Area area = new Area();
            area.setAreaId(areaList.get(position).getAreaId());
            area.setAreaName(areaList.get(position).getAreaName());
            areaHashSet.clear();
            areaHashSet.add(area);
            areaHashSet.addAll(currentAreaList);
            currentAreaList.clear();
            currentAreaList.addAll(areaHashSet);
            initAreaAdapter(currentAreaList);

        });
        listPopupWindow.show();
    }


    //Create popup menu for division
    private ListPopupWindow createPopupWindowForDivision(View anchor, List<Division> items) {
        final ListPopupWindow popup = new ListPopupWindow(getActivity());
        PopupWindowAdapter adapter = new PopupWindowAdapter<Division>(items) {

            @Override
            public View getCustomView(int position, View view, ViewGroup parent) {
                Division model = getItem(position);

                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_popup, parent, false);
                TextView lable = view1.findViewById(R.id.popup_menu_title);

                lable.setText(model.getDivisionName());

                return view1;
            }
        };
        popup.setAnchorView(anchor);
        popup.setDropDownGravity(Gravity.END);
        popup.setWidth(600);
        popup.setAdapter(adapter);
        return popup;
    }

    //Create popup menu for district
    private ListPopupWindow createPopupWindowForDistrict(View anchor, List<District> items) {
        final ListPopupWindow popup = new ListPopupWindow(getActivity());
        PopupWindowAdapter adapter = new PopupWindowAdapter<District>(items) {

            @Override
            public View getCustomView(int position, View view, ViewGroup parent) {
                District model = getItem(position);

                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_popup, parent, false);
                TextView lable = view1.findViewById(R.id.popup_menu_title);

                lable.setText(model.getDistrictName());

                return view1;
            }
        };
        popup.setAnchorView(anchor);
        popup.setWidth(600);
        popup.setDropDownGravity(Gravity.END);
        popup.setAdapter(adapter);
        return popup;
    }

    //Create popup menu for city
    private ListPopupWindow createPopupWindowForCity(View anchor, List<City> items) {
        final ListPopupWindow popup = new ListPopupWindow(getActivity());
        PopupWindowAdapter adapter = new PopupWindowAdapter<City>(items) {

            @Override
            public View getCustomView(int position, View view, ViewGroup parent) {
                City model = getItem(position);

                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_popup, parent, false);
                TextView lable = view1.findViewById(R.id.popup_menu_title);

                lable.setText(model.getCityName());

                return view1;
            }
        };
        popup.setAnchorView(anchor);
        popup.setWidth(600);
        popup.setDropDownGravity(Gravity.END);
        popup.setAdapter(adapter);
        return popup;
    }

    //Create popup menu for city
    private ListPopupWindow createPopupWindowForArea(View anchor, List<Area> items) {
        final ListPopupWindow popup = new ListPopupWindow(getActivity());
        PopupWindowAdapter<Area> adapter = new PopupWindowAdapter<Area>(items) {

            @Override
            public View getCustomView(int position, View view, ViewGroup parent) {
                Area model = getItem(position);

                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_popup, parent, false);
                TextView lable = view1.findViewById(R.id.popup_menu_title);

                lable.setText(model.getAreaName());

                return view1;
            }
        };
        popup.setAnchorView(anchor);
        popup.setWidth(600);
        popup.setDropDownGravity(Gravity.END);
        popup.setAdapter(adapter);
        return popup;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}