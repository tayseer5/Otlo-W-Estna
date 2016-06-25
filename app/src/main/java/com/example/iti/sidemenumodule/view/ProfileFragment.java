package com.example.iti.sidemenumodule.view;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.example.iti.sidemenumodule.R;
import com.example.iti.sidemenumodule.controller.MyApplication;
import com.example.iti.sidemenumodule.controller.ProfileActivity;
import com.example.iti.sidemenumodule.controller.ProtoflioActivity;
import com.example.iti.sidemenumodule.daos.SkillsManager;
import com.example.iti.sidemenumodule.daos.UserManager;
import com.example.iti.sidemenumodule.helperclasses.DialogResponce;
import com.example.iti.sidemenumodule.helperclasses.IncodeImageInterface;
import com.example.iti.sidemenumodule.helperclasses.Incode_image;
import com.example.iti.sidemenumodule.model.Portfolio;
import com.example.iti.sidemenumodule.model.Skills;
import com.example.iti.sidemenumodule.model.Users;
import com.example.iti.sidemenumodule.network_manager.AfterPraseResult;
import com.example.iti.sidemenumodule.network_manager.URLManager;
import com.google.gson.Gson;
import com.norbsoft.typefacehelper.TypefaceHelper;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements AfterPraseResult,DialogResponce,IncodeImageInterface {


    View rootView;
    FragmentActivity myContext;
    Users user;
    Button portfolioButton;
    Button chooseSkills;
    TextView profile_email;
    ArrayList<Skills> selectedList;
    TextView profile_name;
    EditText professinal_title;
    EditText summary;
    EditText city;
    EditText street;
    Button change_PP;
    Uri outputFileUri;
    ImageView ppImgVi;
    EditText governorate;
    Button saveBt;
    EditText phone;
    public ProfileFragment() {
        Log.e("heloo","hello");

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        MyApplication myApplication= (MyApplication) getActivity().getApplicationContext();
        Log.e("test",myApplication+"");
        user= myApplication.getUser();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.fragment_profile, container, false);
        TypefaceHelper.typeface(rootView);
        if(user!=null)
        {
            //lsa  portofilo w phone
            profile_email = (TextView) rootView.findViewById(R.id.profile_email_textview);
            profile_email.setText(user.getUserEmail());
            profile_name = (TextView) rootView.findViewById(R.id.profile_name_textview);
            profile_name.setText(user.getUserName());
            professinal_title = (EditText) rootView.findViewById(R.id.professinal_title_edittext);
            change_PP = (Button)rootView.findViewById(R.id.change_pic_button);
            ppImgVi = (ImageView)rootView.findViewById(R.id.employee_image);
            phone= (EditText) rootView.findViewById(R.id.phone_edittext);
            change_PP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("update prof","change pp");
                    imageIntent();
                }
            });
            if (user.getUserImageUrl()!=null&&!user.getUserImageUrl().isEmpty())
            {
                Picasso.with(myContext)
                        .load(URLManager.ip+"/itiProject"+user.getUserImageUrl())
                        .placeholder(R.drawable.ic_rudsonlive)
                        .into(ppImgVi);
            }
            if (user.getProfessinalTiltle()!=null&&!user.getProfessinalTiltle().isEmpty())
            {
                professinal_title.setText(user.getProfessinalTiltle());
            }
            summary = (EditText) rootView.findViewById(R.id.summary_edittext);
            if (user.getSummery()!=null&&!user.getSummery().isEmpty())
            {
                //cannot be null
            }
            governorate = (EditText) rootView.findViewById(R.id.governorate_edittext);
            if (user.getGovernorate()!=null&&!user.getGovernorate().isEmpty())
            {
                governorate.setText(user.getGovernorate());
            }
            city = (EditText) rootView.findViewById(R.id.city_edittext);
            if (user.getCity()!=null&&!user.getCity().isEmpty())
            {
                city.setText(user.getCity());
            }
            street = (EditText) rootView.findViewById(R.id.street_edittext);
            if (user.getStreet()!=null&&!user.getStreet().isEmpty())
            {
                street.setText(user.getStreet());
            }

            RadioGroup radioSexGroup = (RadioGroup) rootView.findViewById(R.id.radioSex);
            int selectedId = radioSexGroup.getCheckedRadioButtonId();
            if (selectedId==0)
            {
                user.setGender(false);
            }
            if (selectedId==1)
            {
                user.setGender(true);
            }

        }
        portfolioButton=(Button) rootView.findViewById(R.id.protolio_button);
        chooseSkills= (Button) rootView.findViewById(R.id.choiseSkills);
        chooseSkills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSkills();
            }
        });
        saveBt =(Button) rootView.findViewById(R.id.save_button);
        saveBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("bt","click");
                collectDate2();
            }
        });
        return rootView;
    }
    private void getSkills() {
        SkillsManager skillsManager = new SkillsManager(this.getActivity(),this);
        skillsManager.getSkills(3);
    }
    private void collectDate2() {
        Log.e("here","1");
        Users userUpdateData = new Users();
        String dataNotWritten=" ";
        userUpdateData.setUserId(user.getUserId());
        if (!phone.getText().toString().trim().equals(null)&&!phone.getText().toString().trim().equals("")) {
            userUpdateData.setMobile(phone.getText().toString().trim());
        }else{
            Log.e("here","6");
            dataNotWritten.concat("phone ");
        }
        userUpdateData.setUserSkills(user.getUserSkills());
        userUpdateData.setGender(user.isGender());


        if(user.getUserImageUrl()==null)
        {
            Log.e("here","2");
            imageIntent();
        }
        else {
            Log.e("here","3");
            userUpdateData.setUserImageUrl(user.getUserImageUrl());
        }
        if (!profile_name.getText().toString().trim().equals(null)&&!profile_name.getText().toString().trim().equals("")) {
            Log.e("here","4");
            userUpdateData.setUserEmail(profile_email.getText().toString().trim());
            userUpdateData.setUserName(profile_name.getText().toString().trim());
            if (!professinal_title.getText().toString().trim().equals(null)&&!professinal_title.getText().toString().trim().equals("")) {
                Log.e("here","5");
                userUpdateData.setProfessinalTiltle(professinal_title.getText().toString().trim());
            }else{
                Log.e("here","6");
                dataNotWritten.concat("professinal_title ");
            }
            if (!summary.getText().toString().trim().equals(null)&&!summary.getText().toString().trim().equals("")) {
                userUpdateData.setSummery(summary.getText().toString().trim());
                Log.e("here","12");
            }else{
                dataNotWritten.concat("summary ");
            }
            if (!city.getText().toString().trim().equals(null)&&!city.getText().toString().trim().equals("")) {
                userUpdateData.setCity(city.getText().toString().trim());
                Log.e("here","11");
            }else{
                dataNotWritten.concat("city ");
            }
            if (!street.getText().toString().trim().equals(null)&&!street.getText().toString().trim().equals("")) {
                userUpdateData.setStreet(street.getText().toString().trim());
                Log.e("here","10");
            }else{
                dataNotWritten.concat("street ");
            }
            if (!governorate.getText().toString().trim().equals(null)&&!governorate.getText().toString().trim().equals("")) {
                Log.e("here","9");
                userUpdateData.setGovernorate(governorate.getText().toString().trim());
            }else{
                dataNotWritten.concat("governorate ");
            }
//            if (dataNotWritten.)
//            {
            Log.e("here","7");
            updateProcess(userUpdateData);
//            }
//            else
//            {
//
//                Log.e("collect data21221",dataNotWritten);
//            }
        }
        else
        {
            Log.e("collect data","profile name cannot be null");
        }
    }

    private void updateProcess(Users userUpdateData) {
        Log.e("here","8");
        UserManager userManager = new UserManager(this.getActivity(),this);
        userManager.updateUser(userUpdateData, 2);
    }

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public void afterParesResult(Object data, int code) {
        switch (code)
        {
            case 2:
                Log.e("updat profile S",data.toString());
                break;
            case 3:
                //skills dialog logic
                Log.e("update skills s",data+"");
                showSkillsDialog((ArrayList<Skills>) data);
                break;
        }
    }
    private void showSkillsDialog(ArrayList<Skills> allSkills)
    {
        //handel if he want to remove skill
        ArrayList<Skills> skills=user.getUserSkills();
        if (skills!=null&&allSkills.size()==0)
            for (int i=0;i<skills.size();i++) {
                Skills userSkil=skills.get(i);
                allSkills.remove(userSkil);
            }
        else
        {
            Log.e("skills",skills+"");
            Log.e("allSkills",allSkills.size()+"");
        }
        if(allSkills.size()==0)
        {
            Log.e("allSkills","you choose all skils no more skills");
        }
        else {
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
    @Override
    public void errorParesResult(String errorMessage, int code) {
        switch (code)
        {
            case 2:
                Log.e("updat profile e",errorMessage.toString());
                break;
            case 3:
                //skills dialog logic
                Log.e("update skills e",errorMessage+"");

                break;
        }

    }

    @Override
    public void result(ArrayList<Skills> selectedList) {
        Log.e("selected list",selectedList.size()+"");
        for (int i=0;i<selectedList.size();i++)
        {

            user.getUserSkills().add(selectedList.get(i));

        }
    }
    private void imageIntent()
    {
        // Determine Uri of camera image to save.
        final File root = new File(Environment.getExternalStorageDirectory() + File.separator + "MyDir" + File.separator);
        root.mkdirs();
        final String fname = "xyz.jpg";
        final File sdImageMainDirectory = new File(root, fname);
        outputFileUri = Uri.fromFile(sdImageMainDirectory);

        // Camera.
        final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = this.getActivity().getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for(ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(packageName);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            cameraIntents.add(intent);
        }
        // Filesystem.
        final Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

        // Chooser of filesystem options.
        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");

        // Add the camera options.
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[cameraIntents.size()]));

        startActivityForResult(chooserIntent,152);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.getActivity().RESULT_OK) {
            if (requestCode == 152) {
                final boolean isCamera;
                if (data == null) {
                    isCamera = true;
                } else {
                    final String action = data.getAction();
                    if (action == null) {
                        isCamera = false;
                    } else {
                        isCamera = action.equals(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    }
                }

                Uri selectedImageUri;
                if (isCamera) {
                    selectedImageUri = outputFileUri;
                    onSelectFromGalleryResult(selectedImageUri);
                } else {

                    selectedImageUri = data == null ? null : data.getData();
                    Log.e("selectedImageUri",selectedImageUri+"");
                    onSelectFromGalleryResult(data.getData());
                }
            }
        }

    }
    private void onSelectFromGalleryResult(Uri data) {

        Log.e("update prof","after get image uri");
        if (ppImgVi!=null) {
            if(data!=null) {
                ppImgVi.setImageURI(data);
                Incode_image incode_image = new Incode_image(this.getActivity(),this);
                incode_image.execute(data);
            }
        }
    }

    @Override
    public void afterDecode(String decodedImage) {
        user.setUserImageUrl(decodedImage);
        Log.e("update prof afterDecode",user.getUserImageUrl());
    }
}
