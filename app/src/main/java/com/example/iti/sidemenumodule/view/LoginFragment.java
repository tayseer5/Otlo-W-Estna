package com.example.iti.sidemenumodule.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.InputType;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.iti.sidemenumodule.R;
import com.example.iti.sidemenumodule.daos.UserManager;
import com.example.iti.sidemenumodule.network_manager.AfterPraseResult;
import com.norbsoft.typefacehelper.TypefaceHelper;

public class LoginFragment extends Fragment implements AfterPraseResult {
    View rootView;
    EditText mail;
    EditText password;

    TextView href;
    public LoginFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.login_fragment, container, false);
        TypefaceHelper.typeface(rootView);
        mail= (EditText) rootView.findViewById(R.id.loginEmail);
        password= (EditText) rootView.findViewById(R.id.loginPassword);
        href= (TextView) rootView.findViewById(R.id.forgetPasswordURL);
        href.setText(Html.fromHtml("<a href=http://www.stackoverflow.com> STACK OVERFLOW "));
        href.setMovementMethod(LinkMovementMethod.getInstance());
        Button loginButton = (Button) rootView.findViewById(R.id.login_bt);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginProcess();
            }
        });
        CheckBox showPasswordCheckBox = (CheckBox) rootView.findViewById(R.id.showPasswordCheckBox);
        showPasswordCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(!isChecked)
                {
                    password.setInputType(129);
                }
                else
                {
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }

            }
        });
        return rootView;
    }
    private void LoginProcess() {
        UserManager userManager = new UserManager(this.getActivity(),this);
        userManager.Login(mail.getText().toString(),password.getText().toString(),0);

    }
    @Override
    public void afterParesResult(Object message, int code) {
        Intent returnIntent = new Intent();
        getActivity().setResult(Activity.RESULT_OK,returnIntent);
        getActivity().onBackPressed();
    }

    @Override
    public void errorParesResult(String errorMessage, int code) {
        Log.e("errorMessage",errorMessage);
        //show alert

    }

}
