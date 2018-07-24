package com.madebyasshad.complainapp;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import static android.Manifest.permission.READ_PHONE_STATE;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference=database.getReference();
    DatabaseReference userref=databaseReference.child("Users");
    HashMap<String,String> hashMap=new HashMap<String, String>();

    EditText names,mobile,email,extra,complain,complaintitle;
    TextView txt;
    CardView submity;
    String androidID,devicedetai1,devicedetail2,devicedetail3,devicedetail4,devicedetail5,devicedetail6,devicedetail7;

    int permissioncode=1;

    int storagepermissioncode=1;


    @Override
    protected void onPostResume() {

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MainActivity.this, "You have already granted this permission!", Toast.LENGTH_SHORT).show();
            permissioncode=2;
        }
        else
            permissioncode=1;

        super.onPostResume();
    }

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
        txt=findViewById(R.id.complainstatus);
        complaintitle=findViewById(R.id.Complaintitle);
        androidID = android.provider.Settings.System.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            //use checkSelfPermission()
            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "You have already granted this permission!",
                        Toast.LENGTH_SHORT).show();
                //TODO need to be remoaved toast
                permissioncode=2;


                //TODO heree do any thing if permission granted

                TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);

                try
                {
                    devicedetai1=telephonyManager.getDeviceId();
                    devicedetail2=telephonyManager.getLine1Number();
                    devicedetail3=telephonyManager.getNetworkOperatorName();
                    devicedetail4=telephonyManager.getSimSerialNumber();
                    devicedetail5=telephonyManager.getGroupIdLevel1();
                    devicedetail6=telephonyManager.getSubscriberId();

                    devicedetail7=telephonyManager.getNetworkOperator();



                }
                catch (Exception e)
                {
                    Log.i("errrror ","camed");
                }




            }
            else {
                requeststoragepermisssion();
            }



        }
        else
        {
            //simply use the required feature
            //as the user has already granted permission to them during installation


            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED)
            {
                permissioncode=2;

                TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
                try
                {
                    devicedetai1=telephonyManager.getDeviceId();
                    devicedetail2=telephonyManager.getLine1Number();
                    devicedetail3=telephonyManager.getNetworkOperatorName();
                    devicedetail4=telephonyManager.getSimSerialNumber();
                    devicedetail6=telephonyManager.getSubscriberId();

                    devicedetail7=telephonyManager.getNetworkOperator();



                }
                catch (Exception e)
                {
                    Log.i("error ","camed");
                }

                //TODO do everything here same as that permision granted
            }
            else
            {
                requeststoragepermisssion();
            }



        }
        submity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (permissioncode==1)
                {
                    sshowdialog();
                }
                else if (permissioncode==2)
                {
                    complianclass();

                }
























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
        else  if (complaintitle.getText().toString().equals(""))
        {
            Toast.makeText(getApplicationContext(),"Please Enter Your ComplainTitle",Toast.LENGTH_SHORT).show();
            status=false;
        }
        return status;

    }


    public void complianclass()
    {
        if (checkvalid())
        {
            //start working
            hashMap.put("name",names.getText().toString());
            hashMap.put("android_id",androidID);
            hashMap.put("device_id",devicedetai1);
            hashMap.put("Line_number",devicedetail2);
            hashMap.put("network",devicedetail3);
            hashMap.put("sim_serial_name",devicedetail4);
            hashMap.put("Group_level",devicedetail5);
            hashMap.put("Subscriber_id",devicedetail6);
            hashMap.put("networkoperator",devicedetail7);
            hashMap.put("email",email.getText().toString());
            hashMap.put("phoneno",mobile.getText().toString());
            hashMap.put("complain",complain.getText().toString());
            hashMap.put("extra",extra.getText().toString());
            hashMap.put("title",complaintitle.getText().toString());
            userref.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful())
                    {
                        txt.setText("Complain Registered Sucessfully");
                    }
                    else
                    {
                        txt.setText("Complain was unable to Register");

                    }
                }
            });



        }
        else
        {
            //stop
            Log.i("vaildity","no");
        }

    }




    public void sshowdialog()
    {
        new AlertDialog.Builder(this)
                .setTitle("Permission needed")
                .setMessage("This permission is needed to reduce the spam." +
                        "\nunless this you cannot register complain" +
                        "\n(we do not use your data for wrong purpose)")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"turn ON permission for uor app",Toast.LENGTH_LONG).show();
                        //TODO change to our name of app
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent,storagepermissioncode);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                })
                .create().show();
    }




    public void requeststoragepermisssion()
    {

        //here i am requesting permission
        ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_PHONE_STATE},storagepermissioncode);


    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == storagepermissioncode)  {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Click Again for Sending", Toast.LENGTH_SHORT).show();
                permissioncode=2;
            } else {
                sshowdialog();
            }
        }
    }


}
