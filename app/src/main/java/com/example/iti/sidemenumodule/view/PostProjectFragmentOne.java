package com.example.iti.sidemenumodule.view;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.iti.sidemenumodule.R;
import com.example.iti.sidemenumodule.daos.CategoryManger;
import com.example.iti.sidemenumodule.model.Attribute;
import com.example.iti.sidemenumodule.model.Project;
import com.example.iti.sidemenumodule.model.Type;
import com.example.iti.sidemenumodule.network_manager.AfterPraseResult;
import com.norbsoft.typefacehelper.TypefaceHelper;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostProjectFragmentOne extends Fragment implements AfterPraseResult{
    int categoryId;
    ArrayList<Type> typesList;
    ArrayList<Attribute> attrList;
    String[] typesNames;
    String[] matrials;
    String[] colors;
    View rootView;
    FragmentActivity myContext;
    Spinner typesSpinner;
    Spinner colorsSpinner;
    Spinner matrialSpinner;
    Project project;

    public PostProjectFragmentOne(int categoryId) {
        // Required empty public constructor
        this.categoryId=categoryId;
        project=new Project();
        project.setCategory(categoryId);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        TypefaceHelper.typeface(myContext);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        typesList=new ArrayList<>();
        attrList=new ArrayList<>();
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_post_project_fragment_one, container, false);
        TypefaceHelper.typeface(rootView);
        CategoryManger categoryManger = CategoryManger.getInstance(myContext);
        categoryManger.getTypesList(this, categoryId);
       // typesNames=getTypeNames();
        colorsSpinner=(Spinner) rootView.findViewById(R.id.project_color_spinner);
        matrialSpinner=(Spinner) rootView.findViewById(R.id.project_matrial_spinner);
        typesSpinner = (Spinner) rootView.findViewById(R.id.project_type_spinner);

        typesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long arg3) {
                CategoryManger categoryManger = CategoryManger.getInstance(myContext);
                categoryManger.getAttList(PostProjectFragmentOne.this, typesList.get(pos).getTypeId());

                project.setTypeId(typesList.get(pos).getTypeId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        colorsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long arg3) {
                project.setColor(colors[pos]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        matrialSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long arg3) {
                project.setMaterial(matrials[pos]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final EditText longEditText=(EditText) rootView.findViewById(R.id.project_long_edittext);
        final EditText widthEditText=(EditText) rootView.findViewById(R.id.project_width_edittext);
        final EditText sizeEditText=(EditText) rootView.findViewById(R.id.project_size_edittext);
        final EditText titleEditText=(EditText) rootView.findViewById(R.id.project_title_edittext);

        Button nextButton=(Button) rootView.findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    project.setWidth(Integer.valueOf(widthEditText.getText().toString()));
                    project.setHight(Integer.valueOf(sizeEditText.getText().toString()));
                    project.setProjectLong(Integer.valueOf(longEditText.getText().toString()));
                    project.setProjectName(titleEditText.getText().toString());
                    project.setProjectSkills("1,2,3");
                    SecondPostProjectFragment secondPostProjectFragment=new SecondPostProjectFragment(project);
                    FragmentManager manager=myContext.getSupportFragmentManager();
                    FragmentTransaction transaction=manager.beginTransaction();
                    transaction.replace(R.id.fragment, secondPostProjectFragment, "secondPostProjectFragment");
                    transaction.commit();
                }catch (Exception e)
                {
                    Toast.makeText(myContext, getString(R.string.you_must_fill_alldata), Toast.LENGTH_LONG).show();
                }


            }
        });

        return rootView;
    }


    private String[] getTypeNames()
    {
        String[] names=new String[typesList.size()];
        for(int i=0;i<typesList.size();i++)
        {
            names[i]=typesList.get(i).getType();
        }
        return names;
    }

    private String[] getMatrials()
    {
        String[] names=new String[attrList.size()];
        for(int i=0;i<attrList.size();i++)
        {
            names[i]=attrList.get(i).getMaterial();
        }
        return names;
    }
    private String[] getColors()
    {
        String[] names=new String[attrList.size()];
        for(int i=0;i<attrList.size();i++)
        {
            names[i]=attrList.get(i).getColor();
        }
        return names;
    }

    private int getTypeIdByName(String name)
    {
        for(int i=0;i<typesList.size();i++)
        {
            if(typesList.get(i).getType().equals(name))
            {
                return typesList.get(i).getTypeId();
            }
        }
        return -1;
    }


    @Override
    public void afterParesResult(Object data, int code) {
        Log.e("get data",code+"");
        switch (code)
        {
            case 2:
                typesList=(ArrayList<Type>)data;
                Log.e("get data",typesList.size()+"");
                typesNames=getTypeNames();
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, typesNames);
                typesSpinner.setAdapter(adapter1);

                break;
            case 3:
                attrList=(ArrayList<Attribute>)data;
                if(attrList.size()>0) {
                    matrials = getMatrials();
                    colors = getColors();
                    ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, matrials);
                    matrialSpinner.setAdapter(adapter3);
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, colors);
                    colorsSpinner.setAdapter(adapter2);
                }
                break;
        }
    }

    @Override
    public void errorParesResult(String errorMessage, int code) {

    }

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

}
