package com.example.user.crudwithretrofit;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 * Use the {@link SQLshow_frag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SQLshow_frag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapter adapter;
    private DatabaseHelper databaseHelper;
    private List<Trains> trainses;
    private RecyclerAdapter recyclerAdapter;



    public SQLshow_frag() {
        // Required empty public constructor
    }



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SQLshow_frag.
     */
    // TODO: Rename and change types and number of parameters
    public static SQLshow_frag newInstance(String param1, String param2) {
        SQLshow_frag fragment = new SQLshow_frag();
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
        View v = inflater.inflate(R.layout.fragment_sqlshow_frag, container, false);

        recyclerView = (RecyclerView)v.findViewById(R.id.recyle);
        // Inflate the layout for this fragment
        layoutManager = new LinearLayoutManager(getContext());
        databaseHelper  = new DatabaseHelper(getActivity());
        trainses = new ArrayList<>();
        Cursor cursor = databaseHelper.getData();

        if(cursor.getCount() == 0){
            Toast.makeText(getActivity(),"EMPTY",Toast.LENGTH_LONG).show();
        }



        List<String> name = new ArrayList<>();
        List<String> type = new ArrayList<>();
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            name.add(cursor.getString(1));
            type.add(cursor.getString(2));
            cursor.moveToNext();
        }
        for (int i = 0;i < name.size();i++){
            trainses.add(new Trains(name.get(i),type.get(i)));
        }
        recyclerAdapter = new RecyclerAdapter(trainses,1);

        //Toast.makeText(getActivity(),name.size(),Toast.LENGTH_LONG).show();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerAdapter);
        databaseHelper.close();
        return v;
    }


}
