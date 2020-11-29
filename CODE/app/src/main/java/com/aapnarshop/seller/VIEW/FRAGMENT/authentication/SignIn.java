package com.aapnarshop.seller.VIEW.FRAGMENT.authentication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.aapnarshop.seller.Library.KeyWord;
import com.aapnarshop.seller.Library.Utility;
import com.aapnarshop.seller.Model.GET.Profile;
import com.aapnarshop.seller.Model.SEND.LogIn;
import com.aapnarshop.seller.R;
import com.aapnarshop.seller.VIEW.ACTIVITY.HomePage;
import com.aapnarshop.seller.VIEWMODEL.SignInViewModel;
import com.aapnarshop.seller.databinding.FragmentSignInBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

public class SignIn extends Fragment {

    FragmentSignInBinding binding;
    SignInViewModel signInViewModel;
    Utility utility;
    Gson gson;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false);
        signInViewModel = new ViewModelProvider(this).get(SignInViewModel.class);
        try {
            getActivity().getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
            );
            gson = new Gson();
            utility = new Utility(getActivity());
            String language = utility.getLangauge();

            signInViewModel.getLoginResponse().observe(getActivity(), apiResponse -> {
                if (apiResponse.getCode() == 200) {
                    Profile profile = gson.fromJson(apiResponse.getData(), Profile.class);
                    saveInSharedPreference(profile);
                    startActivity(new Intent(getActivity(), HomePage.class));
                    getActivity().finish();
                } else if (apiResponse.getCode() == 201) {
                    utility.showDialog(apiResponse.getData().getAsString());
                } else if (apiResponse.getCode() == 202) {
                    utility.showDialog(apiResponse.getData().getAsString());
                } else if (apiResponse.getCode() == 203) {
                    utility.showDialog(apiResponse.getData().getAsString());
                }
            });

            signInViewModel.getForgetPasswordResponse().observe(getActivity(), api_response -> {
                if (api_response.getCode() == 200) {
                    utility.showDialog(api_response.getData().getAsString());
                } else if (api_response.getCode() == 202) {
                    utility.showDialog(api_response.getData().getAsString());
                } else if (api_response.getCode() == 203) {
                    utility.showDialog(api_response.getData().getAsString());
                } else if (api_response.getCode() == 333) {
                    utility.showDialog(api_response.getData().getAsString());

                }

            });

            binding.signInBtn.setOnClickListener(v -> {
                validation();
            });
            binding.signInRegisterForShop.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_signInFragment_to_signUpFragment));

            //Change Language -----------Start--------

            binding.signInWelcomeTv.setText(language.equals(KeyWord.BANGLA) ? getString(R.string.welcome_bn) : getString(R.string.welcome));
            binding.signInAapnarshopTv.setText(language.equals(KeyWord.BANGLA) ? getString(R.string.to_aapnershop_bn) : getString(R.string.to_aapnershop));
            binding.signInAapnarshopTv.setTextColor(getActivity().getResources().getColor(R.color.app_yellow));
            //binding.signInPhoneEmilEt.setHint(language.equals(KeyWord.BANGLA)?getString(R.string.phone_number_email_bn):getString(R.string.phone_number_email));
            //  binding.signInPasswordEt.setHint(language.equals(KeyWord.BANGLA)?getString(R.string.password_bn):getString(R.string.password));
            binding.signInForgetPasTv.setText(language.equals(KeyWord.BANGLA) ? getString(R.string.forget_your_password_bn) : getString(R.string.forget_your_password));
            binding.signInBtn.setText(language.equals(KeyWord.BANGLA) ? getString(R.string.sign_in_bn) : getString(R.string.sign_in));
            binding.signInMessageTv.setText(language.equals(KeyWord.BANGLA) ? getString(R.string.do_you_have_any_shop_bn) : getString(R.string.do_you_have_any_shop));
            binding.signInRegisterForShop.setText(language.equals(KeyWord.BANGLA) ? getString(R.string.register_for_online_shop_bn) : getString(R.string.register_for_online_shop));
            // binding.termsAndPolicy.setText(language.equals(KeyWord.BANGLA)?getString(R.string.by_signing_up_you_agree_with_the_terms_of_service_and_privacy_policy_bn):getString(R.string.by_signing_up_you_agree_with_the_terms_of_service_and_privacy_policy));

            //------------End------------------

            binding.signInForgetPasTv.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View view = getLayoutInflater().inflate(R.layout.dialog_forget_password, null);

                TextInputEditText phone_email = view.findViewById(R.id.forgot_password_phone_emil_et);
                TextView forget_password = view.findViewById(R.id.forgot_password);


                builder.setView(view);
                final AlertDialog dialog = builder.create();
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

                dialog.show();
                forget_password.setOnClickListener(v1 -> {
                    if (!TextUtils.isEmpty(phone_email.getText())) {
                        LogIn logIn = new LogIn(phone_email.getText().toString().trim());
                        observeForgetPassword(logIn);
                        dialog.dismiss();

                    } else {
                        phone_email.setError("Enter valid phone number/email address");
                    }
                });

            });
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }

        return binding.getRoot();
    }

    private void observeForgetPassword(LogIn logIn) {

        signInViewModel.forgetPassword(logIn);
    }

    private void saveInSharedPreference(Profile profile) {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("Profile", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(profile);
        editor.putString("MyObject", json);
        editor.apply();
    }


    private void validation() {
        try {if (TextUtils.isEmpty(binding.signInPasswordEt.getText())) {
                binding.signInPasswordLayout.setError("Enter password");
            } else {
                LogIn logIn = new LogIn(binding.signInPhoneEmilEt.getText().toString(), binding.signInPasswordEt.getText().toString());
                signInViewModel.sendLogin(logIn);

            }
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}