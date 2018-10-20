package com.kumarsunil17.tinstudent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.kumarsunil17.tinstudent.fragments.About_Frgament;
import com.kumarsunil17.tinstudent.fragments.Attendance_Fragment;
import com.kumarsunil17.tinstudent.fragments.Help_Fragment;
import com.kumarsunil17.tinstudent.fragments.Home_Fragment;
import com.kumarsunil17.tinstudent.fragments.Profile_Fragment;
import com.kumarsunil17.tinstudent.fragments.Syllabus_Fragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Fragment f = null;
    String tag = "others";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        f = new Home_Fragment();
        tag = "home";
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container,f,tag);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportFragmentManager().findFragmentById(R.id.container).getTag().equals("home")){
                super.onBackPressed();
            }else{
                f = new Home_Fragment();
                tag = "home";
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container,f,tag);
                ft.commit();
            }
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

        if (id == R.id.nav_home) {
            f = new Home_Fragment();
            tag = "home";
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container,f,tag);
            ft.commit();

        } else if (id == R.id.nav_profile) {
            f = new Profile_Fragment();
            tag = "others";
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container,f,tag);
            ft.commit();

        } else if (id == R.id.nav_about) {
            f = new About_Frgament();
            tag = "others";
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container,f,tag);
            ft.commit();

        } else if (id == R.id.nav_attendance) {
            f = new Attendance_Fragment();
            tag = "others";
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container,f,tag);
            ft.commit();

        } else if (id == R.id.nav_help) {
            f = new Help_Fragment();
            tag = "others";
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container,f,tag);
            ft.commit();

        } else if (id == R.id.nav_syllabus) {
            f = new Syllabus_Fragment();
            tag = "others";
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container,f,tag);
            ft.commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
