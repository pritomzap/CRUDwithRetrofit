package com.example.user.crudwithretrofit;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerAdapter adapter;
    private List<Trains> trains;
    private ApiInterface apiInterface;
    private DatabaseHelper databaseHelper;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ReadFragment readFragment = new ReadFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame,readFragment,"fragment1");
        fragmentTransaction.commit();

        serverFetch();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_create) {

            WriteFragment writeFragment = new WriteFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,writeFragment,"fragment2");
            fragmentTransaction.commit();
            // Handle the camera action
        } else if (id == R.id.nav_read) {
            //setTitle("Read from database");
            ReadFragment readFragment = new ReadFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,readFragment,"fragment1");
            fragmentTransaction.commit();


        } else if (id == R.id.nav_update) {

        } else if (id == R.id.nav_delete) {

        }
        else if (id == R.id.sql_show){
            SQLshow_frag sqLshowfrag = new SQLshow_frag();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,sqLshowfrag,"fragment1");
            fragmentTransaction.commit();

        }
        else if (id == R.id.sql_insert){
            SQLinsert_frag sqLinsertFrag = new SQLinsert_frag();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,sqLinsertFrag,"fragment1");
            fragmentTransaction.commit();
        }
        else if (id == R.id.sql_delete){
            SQLdelete_frag sqLdeleteFrag = new SQLdelete_frag();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,sqLdeleteFrag,"fragment1");
            fragmentTransaction.commit();

        }
        else if (id == R.id.sql_update){
            SQLupdate_frag sqLupdateFrag = new SQLupdate_frag();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,sqLupdateFrag,"fragment1");
            fragmentTransaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    public void serverFetch(){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Trains>> call = apiInterface.getTrains();
        databaseHelper = new DatabaseHelper(getBaseContext());

        if(databaseHelper.emptyCheck() == false){


            call.enqueue(new Callback<List<Trains>>() {
                @Override
                public void onResponse(Call<List<Trains>> call, Response<List<Trains>> response) {
                    trains = response.body();
                    //Log.d(TAG, "onResponse: .......................................... "+trains.get(0).getName());

                    boolean result;
                    for (int i = 0;i < trains.size();i++){
                        result = databaseHelper.insertData(trains.get(i).getName(),trains.get(i).getType());
                        if (result == false)
                            Toast.makeText(getBaseContext(),"NOT INSERTED",Toast.LENGTH_LONG).show();

                    }
                    adapter = new RecyclerAdapter(trains,1);
                }

                @Override
                public void onFailure(Call<List<Trains>> call, Throwable t) {

                }
            });
        }

        databaseHelper.close();
    }
}
