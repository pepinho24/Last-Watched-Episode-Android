package com.example.peter.lastwatchedepisode;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peter.lastwatchedepisode.fragments.AddShowFragment;
import com.example.peter.lastwatchedepisode.fragments.ChangeBackgroundFragment;
import com.example.peter.lastwatchedepisode.fragments.HomeFragment;
import com.example.peter.lastwatchedepisode.fragments.LastWatchedEpisodesListFragment;
import com.example.peter.lastwatchedepisode.fragments.ShowDetailsPageFragment;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    public void ToastNotify(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        // Set background on start
        SetBackground();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments

        Fragment fragment;
        FragmentManager fm = getSupportFragmentManager();
        switch (position) {
            default:
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new LastWatchedEpisodesListFragment();
                break;
            case 2:
                fragment = new ShowDetailsPageFragment();
                break;
            case 3:
                fragment = new AddShowFragment();
                break;
            case 4:
                fragment = new ChangeBackgroundFragment();
                break;

        }

//        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .setCustomAnimations(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom)
                .replace(R.id.container, fragment)
//                .addToBackStack(null)
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_sectionHome);
                break;
            case 2:
                mTitle = getString(R.string.title_section1);
                break;
            case 3:
                mTitle = getString(R.string.title_section2);
                break;
            case 4:
                mTitle = getString(R.string.title_section3);
                break;
            case 5:
                mTitle = getString(R.string.title_section_background);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    private void GoToFragment(Fragment fragment) {

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .setCustomAnimations(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom)
                .replace(R.id.container, fragment)
                .commit();
    }

    public void GoToDetails(Show show) {
        Bundle obj = new Bundle();
        ArrayList<String> props = new ArrayList<String>();
        props.add(String.valueOf(show.getId()));
        props.add(show.getTitle());
        props.add(show.getDescription());
        props.add(show.getAirWeekDay());
        obj.putStringArrayList("ShowDetails", props);

        ShowDetailsPageFragment frag = new ShowDetailsPageFragment();
        frag.setArguments(obj);

        GoToFragment(frag);
    }

    public void GoToShowsList(View view) {
        GoToFragment(new LastWatchedEpisodesListFragment());
    }

    public void GoToAddNewShow(View view) {
        GoToFragment(new AddShowFragment());
    }


    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView iv_background_image;

    public void OnTakePictureClick(View view) {
        iv_background_image = (ImageView) findViewById(R.id.iv_background_image);
        Button btn_take_picture = (Button) findViewById(R.id.btn_take_picture);

        // disable the button if the user has no camera
        if (!hasCamera()) {
            btn_take_picture.setEnabled(false);
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    static final int SELECT_FILE = 1;

    public void OnAddFromGalleryClick(View view) {
        iv_background_image = (ImageView) findViewById(R.id.iv_background_image);

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select file"), SELECT_FILE);
    }

    public void OnDefaultBackgroundClick(View view) {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("background_uri", "default");
        editor.commit();

        SetBackground();
        ToastNotify("Background changed!");
    }

    private void SetBackground() {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String res_uri = sharedPref.getString("background_uri", "default");

        if (res_uri == "default") {
            FrameLayout frl = (FrameLayout) findViewById(R.id.container);
            frl.setBackground(getResources().getDrawable(R.drawable.background_blue_green));
        } else {
            FrameLayout frl = (FrameLayout) findViewById(R.id.container);
            Uri parsedUri = Uri.parse(res_uri);
            Drawable myDrawable;
            try {
                InputStream input = getContentResolver().openInputStream(parsedUri);
                myDrawable = Drawable.createFromStream(input, parsedUri.toString());
            } catch (FileNotFoundException e) {
                myDrawable = getResources().getDrawable(R.drawable.background_blue_green);
            }

            frl.setBackground(myDrawable);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
//            iv_background_image.setImageURI(imageUri);
//            FrameLayout frl = (FrameLayout) findViewById(R.id.container);
//            frl.setBackground(iv_background_image.getDrawable());

            SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();

            editor.putString("background_uri", imageUri.toString());
            editor.commit();

            SetBackground();
            ToastNotify("Background changed!");
        }
    }


    public boolean hasCamera() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }
}
