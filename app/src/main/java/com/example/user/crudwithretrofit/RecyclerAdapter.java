package com.example.user.crudwithretrofit;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * Created by user on 10/1/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyviewHolder> {

    private List<Trains> trains;
    private int value;
    private DatabaseHelper databaseHelper;
    private String updateType;
    private String trainName;
    private ArrayAdapter<CharSequence> charadapter;

    public RecyclerAdapter(List<Trains> trains, int value) {
        this.trains = trains;
        this.value = value;
    }



    @Override
    public MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (value == 1)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item,parent,false);
        else if(value == 2)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_delete,parent,false);
        else
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_update,parent,false);



        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyviewHolder holder, final int position) {
        final Context context = holder.itemView.getContext();
        holder.name.setText(trains.get(position).getName());
        holder.type.setText(trains.get(position).getType());
        databaseHelper = new DatabaseHelper(context);
        if (value == 2)
            holder.cross.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    databaseHelper.deleteEntry(trains.get(position).getName().toString());
                    Toasty.success(context,"Success", Toast.LENGTH_LONG).show();
                    notifyItemRemoved(position);
                }
            });
        else if(value == 3){
            holder.xxx.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.update_alartbox);

                    TextView dName = (TextView)dialog.findViewById(R.id.dname);
                    TextView dtype = (TextView)dialog.findViewById(R.id.dtype);
                    Spinner spinner = (Spinner)dialog.findViewById(R.id.spinner);
                    ImageButton imageButton = (ImageButton)dialog.findViewById(R.id.imageButton);

                    charadapter = ArrayAdapter.createFromResource(context,R.array.train_types,R.layout.support_simple_spinner_dropdown_item);
                    spinner.setAdapter(charadapter);
                    trainName = trains.get(position).getName();
                    dName.setText("Train Name:"+trainName);
                    dtype.setText("Train Type:"+trains.get(position).getType());
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int mposition, long id) {
                            updateType = (String)parent.getItemAtPosition(mposition);
                            //Toasty.success(context,updateType, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    imageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            databaseHelper.updateEntry(trainName,updateType);
                            //notifyItemRemoved(position);
                            //notifyDataSetChanged();
                            notifyItemChanged(position);
                            dialog.dismiss();

                        }
                    });


                    dialog.show();

                    //https://stackoverflow.com/questions/1967085/android-how-to-resize-dialog
                    Display display =((WindowManager) context.getSystemService(context.WINDOW_SERVICE)).getDefaultDisplay();
                    int width = display.getWidth();
                    int height=display.getHeight();

                    //Log.v("width", width+"");
                    dialog.getWindow().setLayout((6*width)/7,(3*height)/5);


                }
            });

        }



    }

    @Override
    public int getItemCount() {
        return trains.size();
    }




    public class MyviewHolder extends RecyclerView.ViewHolder{

        TextView name,type;
        ImageButton cross,xxx;
        public MyviewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.name);
            type = (TextView)itemView.findViewById(R.id.type);
            cross = (ImageButton)itemView.findViewById(R.id.cross);
            xxx = (ImageButton)itemView.findViewById(R.id.xxx);
        }
    }
}
