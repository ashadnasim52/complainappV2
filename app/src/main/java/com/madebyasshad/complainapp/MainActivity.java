package com.madebyasshad.complainapp;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.Manifest.permission.READ_PHONE_STATE;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    EditText names,mobile,email,extra,complain;
    CardView submity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        names=findViewById(R.id.name);
        email=findViewById(R.id.emailadrrress);
        mobile=findViewById(R.id.phonenumber);
        complain=findViewById(R.id.complain);
        extra=findViewById(R.id.extracontent);
        submity=findViewById(R.id.submit);

        submity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                complianclass();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public boolean checkvalid()
    {
        boolean status=true;
        if (names.getText().toString().equals(""))
        {
            Toast.makeText(getApplicationContext(),"Please Enter Your Name",Toast.LENGTH_SHORT).show();
            status=false;


        }
        else if (email.getText().toString().equals(""))
        {

            Toast.makeText(getApplicationContext(),"Please Enter Your email",Toast.LENGTH_SHORT).show();
            status=false;

        }
        else if (mobile.getText().toString().equals(""))
        {

            Toast.makeText(getApplicationContext(),"Please Enter Your Mobile Number",Toast.LENGTH_SHORT).show();
            status=false;
        }
        else if (complain.getText().toString().equals(""))
        {

            Toast.makeText(getApplicationContext(),"Please Enter Your COMPLAIN",Toast.LENGTH_SHORT).show();
            status=false;
        }
        else if (extra.getText().toString().equals(""))
        {
            extra.setText("null");
        }
        return status;

    }


    public void complianclass()
    {
        if (checkvalid())
        {
            //start working
            DatabaseReference myRef = database.getReference(names.getText().toString());

            myRef.setValue("1");
            myRef.child("name").setValue(names.getText().toString());
            myRef.child("phone no").setValue(mobile.getText().toString());
            myRef.child("email id").setValue(email.getText().toString());
            myRef.child("Complain").setValue(complain.getText().toString());
            myRef.child("extra").setValue(extra.getText().toString());
            Log.i("vaildity","yes");

        }
        else
        {
            //stop
            Log.i("vaildity","no");
        }

    }

}
