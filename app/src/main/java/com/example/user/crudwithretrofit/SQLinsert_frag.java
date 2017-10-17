package com.example.user.crudwithretrofit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import es.dmoral.toasty.Toasty;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link SQLinsert_frag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SQLinsert_frag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @BindView(R.id.namefield)
    EditText editText;

    @BindView(R.id.spinner)
    Spinner spinner;

    @BindView(R.id.imageButton)
    ImageButton imageButton;

    private ArrayAdapter<CharSequence> adapter;
    private String name,type;
    private DatabaseHelper databaseHelper;

    //private OnFragmentInteractionListener mListener;

    public SQLinsert_frag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SQLinsert_frag.
     */
    // TODO: Rename and change types and number of parameters
    public static SQLinsert_frag newInstance(String param1, String param2) {
        SQLinsert_frag fragment = new SQLinsert_frag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sqlinsert_frag, container, false);
        databaseHelper = new DatabaseHelper(getContext());
        ButterKnife.bind(this,v);
        adapter = ArrayAdapter.createFromResource(getContext(),R.array.train_types,R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);



        // Inflate the layout for this fragment
        return v;
    }


    @OnClick(R.id.imageButton)
    public void setImageButton(){
        boolean res = false;
        name = editText.getText().toString();
        if(name.isEmpty()==false){
            res = databaseHelper.insertData(name,type);
            if (res == false){
                Toasty.error(getContext(),"Erroer in inserting database",Toast.LENGTH_LONG).show();
            }
            else
                Toasty.success(getContext(),"Inserted in database",Toast.LENGTH_LONG).show();
        }
        else
            Toasty.error(getContext(),"Enter the name",Toast.LENGTH_LONG).show();
    }

    @OnItemSelected(R.id.spinner)
    public void onItemSelected(int position){
        type = (String) spinner.getItemAtPosition(position);
        //Toast.makeText(getContext(),type,Toast.LENGTH_LONG).show();
    }


}
