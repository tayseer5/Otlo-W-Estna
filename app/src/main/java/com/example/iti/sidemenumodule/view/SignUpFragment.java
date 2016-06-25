package com.example.iti.sidemenumodule.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

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


public class SignUpFragment extends Fragment implements DialogResponce, View.OnClickListener, AfterPraseResult{
    View rootView;
    ArrayList<Skills> selectedList= new ArrayList<>();
    EditText email;
    EditText password;
    EditText rePassword;
    EditText userName;
    CheckBox workerCheckBox;
    CheckBox hiredCheckBox;
    CheckBox ruleAgree;
    Button skillsButton;
    Users users;
    String bussinessType = "";
    int alertErrorMsg;
    int alertEmailErrorMsg;
    int alertPasswordErrorMsg;


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
        email = (EditText) rootView.findViewById(R.id.signUpEmail);
        password = (EditText) rootView.findViewById(R.id.signUpPassword);
        rePassword = (EditText) rootView.findViewById(R.id.signUpRePassword);
        userName = (EditText) rootView.findViewById(R.id.signUpUserName);
        ruleAgree = (CheckBox) rootView.findViewById(R.id.ruleAgreecheckBox);
        hiredCheckBox = (CheckBox) rootView.findViewById(R.id.hiredCheckBox);
        workerCheckBox = (CheckBox) rootView.findViewById(R.id.workerCheckBox);
        skillsButton = (Button) rootView.findViewById(R.id.choiseSkills);
        skillsButton.setOnClickListener(this);
        Button sighupButton = (Button) rootView.findViewById(R.id.sigin_up_button);
        sighupButton.setOnClickListener(this);
        workerCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked)
                {
                    skillsButton.setVisibility(View.VISIBLE);
                }
                else
                {
                    skillsButton.setVisibility(View.GONE);
                }
            }
        });


        return rootView;
    }

    @Override
    public void result(ArrayList<Skills> selectedList) {
        this.selectedList = selectedList;

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
        alertErrorMsg = 0;
        alertPasswordErrorMsg=0;
        alertEmailErrorMsg=0;
        users = new Users();
        if (!ruleAgree.isChecked()) {
            alertErrorMsg = 1;
        }
        if (password.getText().toString().trim().equals(rePassword.getText().toString()) && !password.getText().toString().trim().equals(null) && !password.getText().toString().trim().equals("")) {
            users.setPassword(password.getText().toString().trim());
        } else {
            alertPasswordErrorMsg = 1;
        }

        if (android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches() && !email.getText().toString().trim().equals(null) && !email.getText().toString().trim().equals("")) {
            users.setUserEmail(email.getText().toString().trim());
        } else {
            alertEmailErrorMsg = 1;
        }
        if (!userName.getText().toString().trim().equals(null) && !userName.getText().toString().trim().equals("")) {
            users.setUserName(userName.getText().toString().trim());
        } else {
            alertErrorMsg = 1;
        }
        if (hiredCheckBox.isChecked() && workerCheckBox.isChecked()) {
            bussinessType = "both";
            if (!selectedList.isEmpty()) {
                users.setUserSkills(selectedList);
            } else {
                alertErrorMsg = 1;
            }
        } else {
            if (workerCheckBox.isChecked()) {
                bussinessType = "work";
                if (!selectedList.isEmpty()) {
                    users.setUserSkills(selectedList);
                } else {
                    alertErrorMsg = 1;
                }
            }
            if (hiredCheckBox.isChecked()) {
                bussinessType = "hire";
            }
        }

        if (!bussinessType.isEmpty()) {
            users.setTypeOfBusiness(bussinessType);

        } else {
            alertErrorMsg = 1;
            //alert select account type
        }


        signUpProcess(users);
    }

    private void signUpProcess(Users users) {
        Log.e("signUpProcess", "signUpProcess");
        AlertDialog alertDialog = new AlertDialog.Builder(this.getActivity()).create();
        // Setting Dialog Title
        alertDialog.setTitle(getString(R.string.registrationErrorMsg));
        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.ic_info);

Log.e("error",alertErrorMsg+"");
       if (alertPasswordErrorMsg==1) {
           // Setting Dialog Message
           alertDialog.setMessage(getString(R.string.password_error_msg));
           // Setting OK Button
           alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                   new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int which) {
                           dialog.dismiss();
                       }
                   });
           alertDialog.show();
       }
                else if (alertEmailErrorMsg==1) {
           // Setting Dialog Message
           alertDialog.setMessage(getString(R.string.emailErrorMsg));
           alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                   new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int which) {
                           dialog.dismiss();
                       }
                   });

           // Showing Alert Message
           alertDialog.show();
       }
        else if (alertErrorMsg==1) {

           // Setting Dialog Message
           alertDialog.setMessage(getString(R.string.dataErrorMsg));
           alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                   new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int which) {
                           dialog.dismiss();
                       }
                   });
           alertDialog.show();
       }
        else if(alertErrorMsg==0&&alertPasswordErrorMsg==0&&alertEmailErrorMsg==0)
       {
                UserManager userManager = new UserManager(this.getActivity(), this);
                userManager.registration(users, 1);
        }

    }

    private void loginProcess(Users users) {
        UserManager userManager = new UserManager(this.getActivity(), this);
        userManager.Login(users.getUserEmail(), users.getPassword(), 0);
    }

    private void getSkills() {
        SkillsManager skillsManager = new SkillsManager(this.getActivity(), this);
        skillsManager.getSkills(2);
    }

    @Override
    public void afterParesResult(Object message, int code) {
        switch (code) {
            case 0:
                //login logic
                Intent returnIntent = new Intent();
                getActivity().setResult(Activity.RESULT_OK, returnIntent);
                getActivity().finish();
                break;
            case 1:
                //registration logic
                Log.e("here", "ge");
                if (message.toString().contains("ture Insert")) {
                    loginProcess(users);
                } else {
                    Toast.makeText(getActivity(), message.toString(), Toast.LENGTH_LONG).show();
                }

                break;
            case 2:
                //skills dialog logic
                Log.e("here", "hh");
                showSkillsDialog((ArrayList<Skills>) message);
                break;

        }
    }

    @Override
    public void errorParesResult(String errorMessage, int code) {
        //alert with msg
        Log.e("reach fragmnet fail", errorMessage);
    }

    private void showSkillsDialog(ArrayList<Skills> allSkills) {
        Gson gson = new Gson();
        String allSkillsJsonArray = gson.toJson(allSkills);
        Skills_Alert_Dialog_fragment dialogFragment = new Skills_Alert_Dialog_fragment();
        Bundle args = new Bundle();
        args.putString("skills", allSkillsJsonArray);
        dialogFragment.setArguments(args);
        dialogFragment.setTargetFragment(this, 0);
        dialogFragment.show(getFragmentManager(), "skills");

    }



}
