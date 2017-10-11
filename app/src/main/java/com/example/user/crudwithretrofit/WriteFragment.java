package com.example.user.crudwithretrofit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 * Use the {@link WriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WriteFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Spinner spinner;
    private String type;
    private String name;
    private ApiInterface apiInterface;

    private ImageButton imageButton;
    private EditText editText;
    ArrayAdapter<CharSequence> adapter;
    //private OnFragmentInteractionListener mListener;

    public WriteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WriteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WriteFragment newInstance(String param1, String param2) {
        WriteFragment fragment = new WriteFragment();
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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_write, container, false);
        spinner = (Spinner)v.findViewById(R.id.spinner);

        adapter = ArrayAdapter.createFromResource(getContext(),R.array.train_types,R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        imageButton = (ImageButton)v.findViewById(R.id.imageButton);
        editText = (EditText)v.findViewById(R.id.namefield);

        //Toast.makeText(getContext(),name,Toast.LENGTH_LONG).show();




        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = (String)parent.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent){

            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = editText.getText().toString();
                if(name.isEmpty()==false){
                    apiCall();
                }
                else{
                    Toast.makeText(getActivity(),"Please enter name",Toast.LENGTH_LONG).show();
                }

            }
        });
        //Toast.makeText(getContext(),getType(),Toast.LENGTH_LONG).show();
        return v;
    }

    public void apiCall(){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Trains> call = apiInterface.setTrains(name,type);
        call.enqueue(new Callback<Trains>() {
            @Override
            public void onResponse(Call<Trains> call, Response<Trains> response) {
                String message = response.body().getMessage();
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Trains> call, Throwable t) {

            }
        });
    }

}
