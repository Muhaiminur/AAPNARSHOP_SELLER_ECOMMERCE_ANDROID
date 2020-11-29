package com.aapnarshop.seller.VIEW.FRAGMENT;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.aapnarshop.seller.Library.KeyWord;
import com.aapnarshop.seller.Library.Utility;
import com.aapnarshop.seller.Model.GET.Schedule;
import com.aapnarshop.seller.Model.GET.Settings;
import com.aapnarshop.seller.Model.SEND.ChangePassword;
import com.aapnarshop.seller.R;
import com.aapnarshop.seller.VIEW.Adapter.GenericAdapter;
import com.aapnarshop.seller.VIEWMODEL.SettingsViewModel;
import com.aapnarshop.seller.databinding.FragmentSettingsBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SettingsFragment extends Fragment {

    FragmentSettingsBinding binding;
    private boolean isChangePasswordExpand = false;
    private boolean isShopOpenHourExpand = false;
    private Utility utility;
    private SettingsViewModel settingsViewModel;
    private TimePicker timePicker;
    private String sat, sun, mon, tue, wed, thu, fri;
    private final ArrayList<String> maxDeliveryTimeList = new ArrayList<>();
    private String maxDeliveryTime = "";
    Dialog dialog;
    @SuppressLint("SimpleDateFormat")
    final SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");

    public SettingsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false);
        try {

            settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

            dialog = new Dialog(getActivity());
            utility = new Utility(getActivity());
            String language = utility.getLangauge();
            Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
            TextView textToolHeader = (TextView) toolbar.findViewById(R.id.title);
            textToolHeader.setText(language.equals(KeyWord.BANGLA) ? KeyWord.SETTINGS_BN : getActivity().getResources().getString(R.string.settings));

            //Change Language----------Start-----------

            binding.settingsMaxDeliveryTimeTv.setText(language.equals(KeyWord.BANGLA) ? getString(R.string.max_delivery_time_bn) : getString(R.string.max_delivery_time));
            binding.settingsDeliveryChargeTv.setText(language.equals(KeyWord.BANGLA) ? getString(R.string.delivery_charge_bn) : getString(R.string.delivery_charge));
            binding.settingsWeightLimitTv.setText(language.equals(KeyWord.BANGLA) ? getString(R.string.weight_limit_bn) : getString(R.string.weight_limit));
            binding.settingsMinimumBuyTv.setText(language.equals(KeyWord.BANGLA) ? getString(R.string.minimum_buy_bn) : getString(R.string.minimum_buy));
            binding.settingsShopOpenHourTv.setText(language.equals(KeyWord.BANGLA) ? getString(R.string.shop_open_hours_bn) : getString(R.string.shop_open_hours));
            binding.settingsChangePasswordTv.setText(language.equals(KeyWord.BANGLA) ? getString(R.string.change_password_bn) : getString(R.string.change_password));
            binding.settingsSave.setText(language.equals(KeyWord.BANGLA) ? getString(R.string.save_bn) : getString(R.string.save));

            //-----------End--------------------

            maxDeliveryTimeList.add("0.3");
            maxDeliveryTimeList.add("1.0");
            maxDeliveryTimeList.add("1.3");
            maxDeliveryTimeList.add("2.0");
            maxDeliveryTimeList.add("2.3");
            maxDeliveryTimeList.add("3.0");
            maxDeliveryTimeList.add("3.3");
            maxDeliveryTimeList.add("4.0");
            maxDeliveryTimeList.add("4.3");
            maxDeliveryTimeList.add("5.0");
            maxDeliveryTimeList.add("5.3");
            maxDeliveryTimeList.add("6.0");
            maxDeliveryTimeList.add("6.3");
            maxDeliveryTimeList.add("7.0");
            maxDeliveryTimeList.add("7.3");
            maxDeliveryTimeList.add("8.0");
            maxDeliveryTimeList.add("8.3");
            maxDeliveryTimeList.add("9.0");
            maxDeliveryTimeList.add("9.3");
            maxDeliveryTimeList.add("10.0");
            maxDeliveryTimeList.add("10.3");
            maxDeliveryTimeList.add("11.0");
            maxDeliveryTimeList.add("11.3");
            maxDeliveryTimeList.add("12.0");
            maxDeliveryTimeList.add("12.3");
            maxDeliveryTimeList.add("13.0");
            maxDeliveryTimeList.add("13.3");
            maxDeliveryTimeList.add("14.0");
            maxDeliveryTimeList.add("14.3");
            maxDeliveryTimeList.add("15.0");
            maxDeliveryTimeList.add("15.3");
            maxDeliveryTimeList.add("16.0");
            maxDeliveryTimeList.add("16.3");
            maxDeliveryTimeList.add("17.0");
            maxDeliveryTimeList.add("17.3");
            maxDeliveryTimeList.add("18.0");
            maxDeliveryTimeList.add("18.3");
            maxDeliveryTimeList.add("19.0");
            maxDeliveryTimeList.add("19.3");
            maxDeliveryTimeList.add("20.0");
            maxDeliveryTimeList.add("20.3");
            maxDeliveryTimeList.add("21.0");
            maxDeliveryTimeList.add("21.3");
            maxDeliveryTimeList.add("22.0");
            maxDeliveryTimeList.add("22.3");
            maxDeliveryTimeList.add("23.0");
            maxDeliveryTimeList.add("23.3");
            maxDeliveryTimeList.add("24.0");

            GenericAdapter<String> adapter = new GenericAdapter<String>(getActivity(), maxDeliveryTimeList) {
                @Override
                public View getCustomView(int position, View view, ViewGroup parent) {
                    String model = getItem(position);


                    View spinnerRow = LayoutInflater.from(parent.getContext()).inflate(com.aapnarshop.seller.R.layout.custom_spinner_item, parent, false);

                    TextView label = spinnerRow.findViewById(com.aapnarshop.seller.R.id.custom_spinner);
                    label.setText(model);
                    return spinnerRow;
                }

            };
            binding.settingsMaxDeliveryTime.setAdapter(adapter);

            binding.settingsMaxDeliveryTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    maxDeliveryTime = maxDeliveryTimeList.get(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            observeSettingsData();

            binding.settingsChangePasswordExpand.setOnClickListener(v -> {

                if (!isChangePasswordExpand) {
                    binding.settingsChangePasswordLayout.setVisibility(View.VISIBLE);
                    binding.settingsChangePasswordExpandIcon.setImageResource(R.drawable.ic_baseline_expand_less_24);
                    isChangePasswordExpand = true;
                } else {
                    binding.settingsChangePasswordLayout.setVisibility(View.GONE);
                    binding.settingsChangePasswordExpandIcon.setImageResource(R.drawable.ic_baseline_expand_more_24);
                    isChangePasswordExpand = false;
                }

            });

            binding.settingsShopOpenHourLayout.setOnClickListener(v -> {

                if (!isShopOpenHourExpand) {
                    binding.settingsShopOpenHour.setVisibility(View.VISIBLE);
                    binding.settingsShopOpenHourIcon.setImageResource(R.drawable.ic_baseline_expand_less_24);
                    isShopOpenHourExpand = true;
                } else {
                    binding.settingsShopOpenHour.setVisibility(View.GONE);
                    binding.settingsShopOpenHourIcon.setImageResource(R.drawable.ic_baseline_expand_more_24);
                    isShopOpenHourExpand = false;
                }

            });

            binding.settingsSavePassword.setOnClickListener(v -> {
                if (TextUtils.isEmpty(binding.settingsCurrentPassword.getText())) {
                    utility.showDialog("Enter current password");
                } else if (TextUtils.isEmpty(binding.settingsNewPassword.getText())) {
                    utility.showDialog("Enter new password");
                } else if (TextUtils.isEmpty(binding.settingsRetypePassword.getText())) {
                    utility.showDialog("Re-type new password");
                } else {
                    ChangePassword changePassword = new ChangePassword(binding.settingsCurrentPassword.getText().toString(), binding.settingsRetypePassword.getText().toString());
                    settingsViewModel.changePassword(changePassword);
                }

            });

            binding.settingsRetypePassword.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if (s.toString().equals(binding.settingsNewPassword.getText().toString())) {
                        binding.settingsPasswordMatch.setText("Password matched");
                        binding.settingsPasswordMatch.setTextColor(getActivity().getResources().getColor(R.color.green));
                    } else {
                        binding.settingsPasswordMatch.setText("Password mismatch");
                        binding.settingsPasswordMatch.setTextColor(getActivity().getResources().getColor(R.color.deep_red));

                    }

                    if (s.length() > 0) {
                        binding.settingsPasswordMatch.setVisibility(View.VISIBLE);

                    } else {
                        binding.settingsPasswordMatch.setVisibility(View.GONE);

                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            binding.satFrom.setOnClickListener(v -> openTimePicker(binding.satFrom, binding.satDot, binding.satTo, "from"));
            binding.satTo.setOnClickListener(v -> openTimePicker(binding.satFrom, binding.satDot, binding.satTo, "to"));
            binding.sunFrom.setOnClickListener(v -> openTimePicker(binding.sunFrom, binding.sunDot, binding.sunTo, "from"));
            binding.sunTo.setOnClickListener(v -> openTimePicker(binding.sunFrom, binding.sunDot, binding.sunTo, "to"));
            binding.monFrom.setOnClickListener(v -> openTimePicker(binding.monFrom, binding.monDot, binding.monTo, "from"));
            binding.monTo.setOnClickListener(v -> openTimePicker(binding.monFrom, binding.monDot, binding.monTo, "to"));
            binding.tueFrom.setOnClickListener(v -> openTimePicker(binding.tueFrom, binding.tueDot, binding.tueTo, "from"));
            binding.tueTo.setOnClickListener(v -> openTimePicker(binding.tueFrom, binding.tueDot, binding.tueTo, "to"));
            binding.wedFrom.setOnClickListener(v -> openTimePicker(binding.wedFrom, binding.wedDot, binding.wedTo, "from"));
            binding.wedTo.setOnClickListener(v -> openTimePicker(binding.wedFrom, binding.wedDot, binding.wedTo, "to"));
            binding.thuFrom.setOnClickListener(v -> openTimePicker(binding.thuFrom, binding.thuDot, binding.thuTo, "from"));
            binding.thuTo.setOnClickListener(v -> openTimePicker(binding.thuFrom, binding.thuDot, binding.thuTo, "to"));
            binding.friFrom.setOnClickListener(v -> openTimePicker(binding.friFrom, binding.friDot, binding.friTo, "from"));
            binding.friTo.setOnClickListener(v -> openTimePicker(binding.friFrom, binding.friDot, binding.friTo, "to"));

            binding.settingsSave.setOnClickListener(v -> {

                if (binding.satFrom.getText().toString().equalsIgnoreCase("off day")) {
                    sat = "0";
                } else {
                    sat = convert12HoursTo24Hours(binding.satFrom.getText().toString(), binding.satTo.getText().toString());
                }
                if (binding.sunFrom.getText().toString().equalsIgnoreCase("off day")) {
                    sun = "0";
                } else {

                    sun = convert12HoursTo24Hours(binding.sunFrom.getText().toString(), binding.sunTo.getText().toString());
                }
                if (binding.monFrom.getText().toString().equalsIgnoreCase("off day")) {
                    mon = "0";
                } else {
                    mon = convert12HoursTo24Hours(binding.monFrom.getText().toString(), binding.monTo.getText().toString());
                }
                if (binding.tueFrom.getText().toString().equalsIgnoreCase("off day")) {
                    tue = "0";
                } else {
                    tue = convert12HoursTo24Hours(binding.tueFrom.getText().toString(), binding.tueTo.getText().toString());
                }
                if (binding.wedFrom.getText().toString().equalsIgnoreCase("off day")) {
                    wed = "0";
                } else {
                    wed = convert12HoursTo24Hours(binding.wedFrom.getText().toString(), binding.wedTo.getText().toString());
                }
                if (binding.thuFrom.getText().toString().equalsIgnoreCase("off day")) {
                    thu = "0";
                } else {
                    thu = convert12HoursTo24Hours(binding.thuFrom.getText().toString(), binding.thuTo.getText().toString());
                }
                if (binding.friFrom.getText().toString().equalsIgnoreCase("off day")) {
                    fri = "0";
                } else {
                    fri = convert12HoursTo24Hours(binding.friFrom.getText().toString(), binding.friTo.getText().toString());
                }
                Schedule schedule = new Schedule(sat, sun, mon, tue, wed, thu, fri);
                Settings settings = new Settings(binding.settingsMinimumBuy.getText().toString(), schedule, maxDeliveryTime,
                        binding.settingsDeliveryCharge.getText().toString(), binding.settingsWeightLimit.getText().toString());
                settingsViewModel.updateSettings(settings);
                utility.logger("Get settings" + settings.toString());

            });


        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }

        return binding.getRoot();
    }

    private String convert12HoursTo24Hours(String from, String to) {

        String fromDay = null;
        String toDay = null;
        try {
            Date date = _12HourSDF.parse(from);
            fromDay = _24HourSDF.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            Date date = _12HourSDF.parse(to);
            toDay = _24HourSDF.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return fromDay + "-" + toDay;
    }


    @SuppressLint("SetTextI18n")
    private void observeSettingsData() {
        settingsViewModel.getSettings().observe(getActivity(), settings -> {

            for (int i = 0; i < maxDeliveryTimeList.size(); i++) {
                if (settings.getMaxDeliveryTime().equals(maxDeliveryTimeList.get(i))) {
                    binding.settingsMaxDeliveryTime.setSelection(i);

                }
            }

            binding.settingsDeliveryCharge.setText(settings.getDeliveryCharge());
            binding.settingsDeliveryCharge.setSelection(settings.getDeliveryCharge().length());
            binding.settingsWeightLimit.setText(settings.getWeightLimit());
            binding.settingsMinimumBuy.setText(settings.getMinimumBuy());


            manageVisibility(settings.getSchedule().getSat(), binding.satFrom, binding.satDot, binding.satTo);
            manageVisibility(settings.getSchedule().getSun(), binding.sunFrom, binding.sunDot, binding.sunTo);
            manageVisibility(settings.getSchedule().getMon(), binding.monFrom, binding.monDot, binding.monTo);
            manageVisibility(settings.getSchedule().getTue(), binding.tueFrom, binding.tueDot, binding.tueTo);
            manageVisibility(settings.getSchedule().getWed(), binding.wedFrom, binding.wedDot, binding.wedTo);
            manageVisibility(settings.getSchedule().getThu(), binding.thuFrom, binding.thuDot, binding.thuTo);
            manageVisibility(settings.getSchedule().getFriday(), binding.friFrom, binding.friDot, binding.friTo);


        });

        settingsViewModel.getChangePasswordResponse().observe(getActivity(), apiResponse -> {
            if (apiResponse.getCode() == 200) {
                utility.showDialog(apiResponse.getData().getAsString());
            } else if (apiResponse.getCode() == 202) {
                utility.showDialog(apiResponse.getData().getAsString());

            } else if (apiResponse.getCode() == 333) {
                utility.showDialog(apiResponse.getData().getAsString());

            }
        });
    }


    //Manage layout visibility for off day
    private void manageVisibility(String dayTime, TextView from, TextView dot, TextView to) {
        if (dayTime.equalsIgnoreCase("0")) {
            from.setText(getString(R.string.off_day));
            dot.setVisibility(View.GONE);
            to.setVisibility(View.GONE);
        } else {
            dot.setVisibility(View.VISIBLE);
            to.setVisibility(View.VISIBLE);
            splitString(dayTime, from, to);
        }
    }

    //Split Schedule
    @SuppressLint("SetTextI18n")
    public static void splitString(String day, TextView fromDay, TextView toDay) {
        String[] parts = day.split("-");
        String from = parts[0];
        String to = parts[1];
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");

            Date fromDate = sdf.parse(from);
            Date toDate = sdf.parse(to);
            fromDay.setText(_12HourSDF.format(fromDate));
            toDay.setText(_12HourSDF.format(toDate));
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }

    }


    // Time Picker Dialog
    private void openTimePicker(TextView from, TextView dot, TextView to, String message) {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_Light_Dialog);
        View mView = getActivity().getLayoutInflater().inflate(R.layout.dialog_timepicker, null);
        Button done = mView.findViewById(R.id.cancelButton);
        TextView title = mView.findViewById(R.id.timePickerTitleTVID);
        CheckBox offDay = mView.findViewById(R.id.dialog_checkbox);

        timePicker = mView.findViewById(R.id.simpleTimePickerID);
        timePicker.setIs24HourView(false);


        mBuilder.setView(mView);
        dialog = mBuilder.create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);


        if (message.equalsIgnoreCase("from")) {
            offDay.setVisibility(View.VISIBLE);
            title.setText(getString(R.string.select_opening_time));
            String previousTime = from.getText().toString();

            if (!previousTime.equalsIgnoreCase("Off day")) {
                String[] splitTime = previousTime.split(":|\\s+");

                if (splitTime.length > 2) {
                    if (splitTime[2].equals("PM") || splitTime[2].equals("pm")) {
                        timePicker.setCurrentHour(Integer.valueOf(splitTime[0]) + 12);
                    } else {
                        timePicker.setCurrentHour(Integer.valueOf(splitTime[0]));
                    }

                    timePicker.setCurrentMinute(Integer.valueOf(splitTime[1]));
                } else {
                    timePicker.setCurrentHour(Integer.valueOf(splitTime[0]));
                    timePicker.setCurrentMinute(Integer.valueOf(splitTime[1]));
                }
            }

        } else {
            offDay.setVisibility(View.GONE);
            title.setText(getString(R.string.select_closing_time));
            String previousTime = to.getText().toString();

            String[] splitTime = previousTime.split(":|\\s+");

            if (splitTime.length > 2) {
                if (splitTime[2].equals("PM") || splitTime[2].equals("pm")) {
                    timePicker.setCurrentHour(Integer.valueOf(splitTime[0]) + 12);
                } else {
                    timePicker.setCurrentHour(Integer.valueOf(splitTime[0]));
                }

                timePicker.setCurrentMinute(Integer.valueOf(splitTime[1]));
            } else {
                timePicker.setCurrentHour(Integer.valueOf(splitTime[0]));
                timePicker.setCurrentMinute(Integer.valueOf(splitTime[1]));
            }
        }

        done.setOnClickListener(v -> {

            if (offDay.isChecked()) {
                from.setText(getString(R.string.off_day));
                dot.setVisibility(View.GONE);
                to.setVisibility(View.GONE);
            } else {
                dot.setVisibility(View.VISIBLE);
                to.setVisibility(View.VISIBLE);
                int min = timePicker.getCurrentMinute();
                int hour = timePicker.getCurrentHour();

                try {
                    String time = hour + ":" + min;

                    Date date = _24HourSDF.parse(time);
                    if (message.equalsIgnoreCase("from")) {
                        if (date != null) {
                            from.setText(_12HourSDF.format(date));
                        }
                    } else {
                        if (date != null) {
                            to.setText(_12HourSDF.format(date));
                        }

                    }

                } catch (Exception e) {
                    Log.d("Error Line Number", Log.getStackTraceString(e));
                }
            }


            dialog.dismiss();
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}