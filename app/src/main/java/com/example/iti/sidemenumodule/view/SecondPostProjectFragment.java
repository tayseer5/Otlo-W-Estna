package com.example.iti.sidemenumodule.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iti.sidemenumodule.R;
import com.example.iti.sidemenumodule.controller.MyApplication;
import com.example.iti.sidemenumodule.daos.JobsManger;
import com.example.iti.sidemenumodule.datamanger.DataManger;
import com.example.iti.sidemenumodule.model.Project;
import com.example.iti.sidemenumodule.model.Users;
import com.example.iti.sidemenumodule.network_manager.AfterPraseResult;
import com.norbsoft.typefacehelper.TypefaceHelper;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class SecondPostProjectFragment extends Fragment implements AfterPraseResult {
    private static final int PICK_PHOTO_FOR_AVATAR =100 ;
    View rootView;
    FragmentActivity myContext;
    Project project;
    EditText bugetEditText;
    Spinner dateSpinner;
    EditText moreEditText;
    Button attatcmentButton;
    Button doneButton;
    ImageView imageView;
    //String[] interval={getString(R.string.days_string),getString(R.string.two_weeks_string),getString(R.string.three_weeks_string),getString(R.string.month_string),getString(R.string.anytime_string)};
    String[] interval={"7days","two weeks"};
    Bitmap bitmap;
    String encoded_image;
    public SecondPostProjectFragment() {
        // Required empty public constructor
    }

    public SecondPostProjectFragment(Project project) {
        this.project=project;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
       // this.project=(Project)savedInstanceState.get("project");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.second_post_project_fragment, container, false);
        TypefaceHelper.typeface(rootView);
        bugetEditText=(EditText)  rootView.findViewById(R.id.project_budget_edittext);
        moreEditText=(EditText)  rootView.findViewById(R.id.project_more_details_edittext);
        dateSpinner = (Spinner) rootView.findViewById(R.id.date_spinner);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, interval);
        dateSpinner.setAdapter(adapter1);
        dateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long arg3) {
                String date = dateSpinner.getSelectedItem().toString();
                int daysNum = 0;
                switch (pos) {
                    case 0:
                        daysNum = 7;
                        break;
                    case 1:
                        daysNum = 14;
                        break;
                    case 2:
                        daysNum = 21;
                        break;
                    case 3:
                        daysNum = 30;
                        break;
                    default:
                        daysNum = 90;
                        break;
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                c.add(Calendar.DATE, daysNum);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
                SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd-yyyy");
                String output = sdf1.format(c.getTime());
                try {
                    project.setProjectDeadLine(sdf1.parse(output));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });
        attatcmentButton=(Button) rootView.findViewById(R.id.attachment_button);
        attatcmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               pickImage();
            }
        });
        doneButton=(Button) rootView.findViewById(R.id.done_button);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd-yyyy");
                String output = sdf1.format(c.getTime());
                try {
                    project.setStartDate(sdf1.parse(output));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                project.setProjectDescription(moreEditText.getText().toString());
                project.setBudget(Integer.parseInt(bugetEditText.getText().toString()));
                MyApplication userObject = (MyApplication) myContext.getApplicationContext();
                if(userObject!=null) {
                    Users user = userObject.getUser();
                    project.setUsers(user.getUserId());
                }else {
                    project.setUsers(4);
                }
                JobsManger jobsManger=JobsManger.getInstance(myContext);
                jobsManger.postProject(project,SecondPostProjectFragment.this);

            }
        });
        imageView=(ImageView)rootView.findViewById(R.id.attachment_imageview);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }


    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }else{
                String filePath = "";
                Uri selectedImage = data.getData();
                Encode_image encode_image=new Encode_image();
                String wholeID = DocumentsContract.getDocumentId(selectedImage);

                // Split at colon, use second item in the array
                String id = wholeID.split(":")[1];

                String[] column = { MediaStore.Images.Media.DATA };

                // where id is equal to
                String sel = MediaStore.Images.Media._ID + "=?";

                Cursor cursor = myContext.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        column, sel, new String[]{id}, null);

                int columnIndex = cursor.getColumnIndex(column[0]);

                if (cursor.moveToFirst()) {
                    filePath = cursor.getString(columnIndex);
                }
                cursor.close();
                Log.e("mytag",filePath);
                project.setImageName(project.getProjectName());
                encode_image.execute(filePath);

            }
        }
    }

    @Override
    public void afterParesResult(Object data,int code) {
        String message=(String)data;
       if(message.equals("tureInsert")){
           getActivity().onBackPressed();
       }
        else {
           Toast.makeText(myContext, message, Toast.LENGTH_SHORT).show();
       }
    }

    @Override
    public void errorParesResult(String errorMessage,int code) {
        Toast.makeText(myContext, errorMessage, Toast.LENGTH_LONG).show();
    }

    private  class Encode_image extends AsyncTask<String,Void,Void>
    {

        @Override
        protected Void doInBackground(String... params) {

            bitmap= BitmapFactory.decodeFile(params[0]);
            Log.e("tag3",bitmap+"");
            ByteArrayOutputStream stream=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
            byte[] array=stream.toByteArray();
            encoded_image= Base64.encodeToString(array, 0);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            project.setImageContent(encoded_image);
            imageView.setImageBitmap(bitmap);
        }
    }
}
