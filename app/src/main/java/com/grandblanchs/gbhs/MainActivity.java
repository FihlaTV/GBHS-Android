package com.grandblanchs.gbhs;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.crashlytics.android.Crashlytics;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import java.util.ArrayList;
import java.util.Random;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    //Your consumer key and secret should be obfuscated in your source code before shipping.
    //TODO: (Wesner) Ensure these keys are replaced with the active release keys before publishing to Google Play.
    private static final String TWITTER_KEY = "ZJnydLJQ8PPjT8hxt5znyscnj";
    private static final String TWITTER_SECRET = "gzSfM0fG4fFaHuQUb46SvWEM30v9XkJih0RVJiB3nEisDZfICV";

    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;

    private int prevCount;
    private int selectedMenuItem;

    private ArrayList<String> prevTitles = new ArrayList<>();
    private ArrayList<Integer> prevMenuItems = new ArrayList<>();

    private FragmentManager fm;
    private static final TwitterFragment twitterFrag = new TwitterFragment();
    private static final MapFragment mapFrag = new MapFragment();
    private static final CalendarFragment calFrag = new CalendarFragment();
    private static final AnnounceFragment announceFrag = new AnnounceFragment();
    private static final CollegeFragment collegeFrag = new CollegeFragment();
    private static final AdminFragment adminFrag = new AdminFragment();
    private static final StaffFragment staffFrag = new StaffFragment();
    private static final ExternalFragment externalFrag = new ExternalFragment();
    private static final GuidanceFragment guidanceFrag = new GuidanceFragment();
    private static final SportsFragment sportsFrag = new SportsFragment();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);

        //Disable crashlytics for debug builds.
        if (BuildConfig.USE_CRASHLYTICS) {
            Fabric.with(this, new Crashlytics(), new Twitter(authConfig));
        } else {
            Fabric.with(this, new Twitter(authConfig));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, 0, 0) {

            @Override
            public void onDrawerSlide(View drawerView, float drawerOffset) {
                super.onDrawerSlide(drawerView, 0);
            }
        };

        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        fm = getSupportFragmentManager();

        if (savedInstanceState == null) {
            //Set the announcements as home fragment
            setTitle(R.string.Announce);
            fm.beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.FragmentContainer, announceFrag)
                    .commit();

            prevTitles.add(prevCount, getTitle().toString());
            prevMenuItems.add(prevCount, selectedMenuItem);
            prevCount++;

        } else {
            setTitle(savedInstanceState.getCharSequence("Title"));
            prevTitles = savedInstanceState.getStringArrayList("prevTitles");

            selectedMenuItem = savedInstanceState.getInt("MenuItem");
            prevMenuItems = savedInstanceState.getIntegerArrayList("prevMenuItems");
            prevCount = savedInstanceState.getInt("prevCount");
        }

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);

            navigationView.inflateHeaderView(getRandomHeaderLayout());

            if (savedInstanceState != null) {
                navigationView.getMenu().getItem(selectedMenuItem).setChecked(true);
            }

            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem) {

                    mDrawerLayout.closeDrawers();

                    prevTitles.add(prevCount, getTitle().toString());
                    prevMenuItems.add(prevCount, selectedMenuItem);
                    prevCount++;

                    switch (menuItem.getItemId()) {
                        case R.id.Announce:
                            setTitle(R.string.Announce);
                            selectedMenuItem = 0;
                            fm.beginTransaction()
                                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                                    .replace(R.id.FragmentContainer, announceFrag, "Announce")
                                    .addToBackStack(null)
                                    .commit();
                            return true;
                        case R.id.Twitter:
                            setTitle(R.string.Twitter);
                            selectedMenuItem = 1;
                            fm.beginTransaction()
                                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
                                            android.R.anim.fade_in, android.R.anim.fade_out)
                                    .replace(R.id.FragmentContainer, twitterFrag, "Twitter")
                                    .addToBackStack(null)
                                    .commit();
                            return true;
                        case R.id.Calendar:
                            setTitle(R.string.Calendar);
                            selectedMenuItem = 2;
                            fm.beginTransaction()
                                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
                                            android.R.anim.fade_in, android.R.anim.fade_out)
                                    .replace(R.id.FragmentContainer, calFrag, "Calendar")
                                    .addToBackStack(null)
                                    .commit();
                            return true;
                        case R.id.Sports:
                            setTitle(R.string.Sports);
                            selectedMenuItem = 3;
                            fm.beginTransaction()
                                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
                                            android.R.anim.fade_in, android.R.anim.fade_out)
                                    .replace(R.id.FragmentContainer, sportsFrag, "Sports")
                                    .addToBackStack(null)
                                    .commit();
                            return true;
                        case R.id.Staff:
                            setTitle(R.string.Staff);
                            selectedMenuItem = 4;
                            fm.beginTransaction()
                                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
                                            android.R.anim.fade_in, android.R.anim.fade_out)
                                    .replace(R.id.FragmentContainer, staffFrag, "Staff")
                                    .addToBackStack(null)
                                    .commit();
                            return true;
                        case R.id.Guidance:
                            setTitle(R.string.Guidance);
                            selectedMenuItem = 5;
                            fm.beginTransaction()
                                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
                                            android.R.anim.fade_in, android.R.anim.fade_out)
                                    .replace(R.id.FragmentContainer, guidanceFrag, "Guidance")
                                    .addToBackStack(null)
                                    .commit();
                            return true;
                        case R.id.Admin:
                            setTitle(R.string.Admin);
                            selectedMenuItem = 6;
                            fm.beginTransaction()
                                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
                                            android.R.anim.fade_in, android.R.anim.fade_out)
                                    .replace(R.id.FragmentContainer, adminFrag, "Admin")
                                    .addToBackStack(null)
                                    .commit();
                            return true;
                        case R.id.Map:
                            setTitle(R.string.Map);
                            selectedMenuItem = 7;
                            fm.beginTransaction()
                                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
                                            android.R.anim.fade_in, android.R.anim.fade_out)
                                    .replace(R.id.FragmentContainer, mapFrag, "Map")
                                    .addToBackStack(null)
                                    .commit();
                            return true;
                        case R.id.College:
                            setTitle(R.string.College);
                            selectedMenuItem = 8;
                            fm.beginTransaction()
                                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
                                            android.R.anim.fade_in, android.R.anim.fade_out)
                                    .replace(R.id.FragmentContainer, collegeFrag, "College")
                                    .addToBackStack(null)
                                    .commit();
                            return true;
                        case R.id.External:
                            setTitle(R.string.External);
                            selectedMenuItem = 9;
                            fm.beginTransaction()
                                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
                                            android.R.anim.fade_in, android.R.anim.fade_out)
                                    .replace(R.id.FragmentContainer, externalFrag, "External")
                                    .addToBackStack(null)
                                    .commit();
                            return true;
                    }
                    return true;
                }
            });
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence("Title", getTitle());
        outState.putStringArrayList("prevTitles", prevTitles);

        outState.putInt("MenuItem", selectedMenuItem);
        outState.putIntegerArrayList("prevMenuItems", prevMenuItems);

        outState.putInt("prevCount", prevCount);
    }

    private static int getRandomHeaderLayout() {
        //Randomize the header image.
        Random rand = new Random();

        switch (rand.nextInt(7)) {
            default: //Required
            case 0:
                return R.layout.nav_header_flag;
            case 1:
                return R.layout.nav_header_frank;
            case 2:
                return R.layout.nav_header_gym;
            case 3:
                return R.layout.nav_header_west;
            case 4:
                return R.layout.nav_header_aerial_1;
            case 5:
                return R.layout.nav_header_aerial_2;
            case 6:
                return R.layout.nav_header_aerial_3;
        }
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.gbhs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        fm = getSupportFragmentManager();

        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.Times:
                Intent i = new Intent(this, TimesActivity.class);
                startActivity(i);
                return true;
            case R.id.Grades:
                new GradesDialog(this).show();
                return true;
            case R.id.Facebook:
                new FacebookDialog(this).show();
                return true;
            case R.id.GBHSWeb:
                Intent w = new Intent(Intent.ACTION_VIEW, Uri.parse("http://grandblanc.high.schoolfusion.us"));
                startActivity(w);
                return true;
            case R.id.About:
                Intent a = new Intent(this, AboutActivity.class);
                startActivity(a);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else if (fm.getBackStackEntryCount() > 0) {
            // Catch back action and pops from backstack
            // (if you called previously to addToBackStack() in your transaction)
            setTitle(prevTitles.get(prevCount - 1));

            selectedMenuItem = prevMenuItems.get(prevCount - 1);
            navigationView.getMenu().getItem(selectedMenuItem).setChecked(true);

            prevCount--;

            fm.popBackStack();
        } else {
            // Default action on back pressed
            super.onBackPressed();
        }
    }
}