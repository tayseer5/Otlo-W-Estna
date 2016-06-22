package com.example.iti.sidemenumodule.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.iti.sidemenumodule.R;
import com.example.iti.sidemenumodule.daos.SkillsManager;
import com.example.iti.sidemenumodule.daos.UserManager;
import com.example.iti.sidemenumodule.helperclasses.DialogResponce;
import com.example.iti.sidemenumodule.model.Skills;
import com.example.iti.sidemenumodule.model.Users;
import com.example.iti.sidemenumodule.network_manager.AfterPraseResult;
import com.google.gson.Gson;
import com.norbsoft.typefacehelper.TypefaceHelper;

import java.util.ArrayList;


public class SignUpFragment extends Fragment implements DialogResponce, View.OnClickListener,AfterPraseResult {
    View rootView;
    ArrayList<Skills> selectedList;
    EditText email;
    EditText password;
    EditText rePassword;
    EditText userName;
    CheckBox ruleAgree;
    Users users;
    public SignUpFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.signup_fragment, container, false);
        TypefaceHelper.typeface(rootView);
        email= (EditText) rootView.findViewById(R.id.signUpEmail);
        password = (EditText) rootView.findViewById(R.id.signUpPassword);
        rePassword = (EditText) rootView.findViewById(R.id.signUpRePassword);
        userName = (EditText) rootView.findViewById(R.id.signUpUserName);
        ruleAgree = (CheckBox) rootView.findViewById(R.id.ruleAgreecheckBox);

        Button skillsButton = (Button) rootView.findViewById(R.id.choiseSkills);
        skillsButton.setOnClickListener(this);
        Button sighupButton = (Button) rootView.findViewById(R.id.sigin_up_button);
        sighupButton.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void result(ArrayList<Skills> selectedList) {
        this.selectedList=selectedList;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.choiseSkills:
                getSkills();
                break;
            case R.id.sigin_up_button:
                signUpInformationCollection();
                break;
        }


    }

    private void signUpInformationCollection() {
        Log.e("signUpInformationCollection","1");
        if(ruleAgree.isChecked())
        { Log.e("signUpInformationCollection","2");
            if (password.getText().toString().trim().equals(rePassword.getText().toString())&&!password.getText().toString().trim().equals(null)&&!password.getText().toString().trim().equals(""))
            { Log.e("signUpInformationCollection","3");
                users = new Users();
                if(android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches()&&!email.getText().toString().trim().equals(null)&&!email.getText().toString().trim().equals("")) {
                    if(!userName.getText().toString().trim().equals(null)&&!userName.getText().toString().trim().equals("")) {
                        users.setUserEmail(email.getText().toString().trim());
                        users.setPassword(password.getText().toString().trim());
                        users.setUserName(userName.getText().toString().trim());
                        if (!selectedList.isEmpty()) {
                            users.setUserSkills(selectedList);
                            signUpProcess(users);
                        } else {

                            //alert select skills
                            Log.e("signUpInformationCollection","4");
                        }
                    }
                    else
                    {

                        //alert to user name
                        Log.e("signUpInformationCollection","5");
                    }
                }
                else
                {

                    //alert mail
                    Log.e("signUpInformationCollection","6");
                }

            }
            else
            {

                //alert passwords identical
                Log.e("signUpInformationCollection","7");
            }

        }
        else
        {
            Log.e("alert agree terms","alert agree terms");
            //alert agree terms
        }
    }

    private void signUpProcess(Users users) {
        Log.e("signUpProcess","signUpProcess");
        UserManager userManager = new UserManager(this.getActivity(),this);
        userManager.registration(users,1);
    }
    private void loginProcess(Users users) {
        UserManager userManager = new UserManager(this.getActivity(),this);
        userManager.Login(users.getUserEmail(),users.getPassword(),0);
    }

    private void getSkills() {
        SkillsManager skillsManager = new SkillsManager(this.getActivity(),this);
        skillsManager.getSkills(2);
    }

    @Override
    public void afterParesResult(Object message, int code) {
        switch (code) {
            case 0:
                //login logic
                Intent returnIntent = new Intent();
                getActivity().setResult(Activity.RESULT_OK,returnIntent);
                getActivity().finish();
                break;
            case 1:
                //registration logic
                Log.e("here","ge");
                if (message.toString().contains("ture Insert"))
                {
                    loginProcess(users);
                }
                else{
                    Log.e("not reg","ge");
                }

                break;
            case 2:
                //skills dialog logic
                Log.e("here","hh");
                showSkillsDialog((ArrayList<Skills>) message);
                break;

        }
    }

    @Override
    public void errorParesResult(String errorMessage, int code) {
        //alert with msg
        Log.e("reach fragmnet fail",errorMessage);
    }
    private void showSkillsDialog(ArrayList<Skills> allSkills)
    {
        Gson gson = new Gson();
        String allSkillsJsonArray =gson.toJson(allSkills);
        Skills_Alert_Dialog_fragment dialogFragment = new Skills_Alert_Dialog_fragment();
        Bundle args = new Bundle();
        args.putString("skills", allSkillsJsonArray);
        dialogFragment.setArguments(args);
        dialogFragment.setTargetFragment(this,0);
        dialogFragment.show(getFragmentManager(), "skills");

    }


}
