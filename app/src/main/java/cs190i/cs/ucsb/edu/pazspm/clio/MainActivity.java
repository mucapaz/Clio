package cs190i.cs.ucsb.edu.pazspm.clio;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import cs190i.cs.ucsb.edu.pedro_ulmi.clio.R;
import cs190i.cs.ucsb.edu.pazspm.clio.adapters.ViewPagerAdapter;
import cs190i.cs.ucsb.edu.pazspm.clio.connection.MyAsyncTask;
import cs190i.cs.ucsb.edu.pazspm.clio.fragments.HomeFrag;
import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

public class MainActivity extends ActionBarActivity implements MaterialTabListener {

    //Connection

    MyAsyncTask task;
    public HomeFrag homeFrag;
    public static ImageStorageWrapper imageStorageWrapper;

    private MaterialTabHost tabHost;
    private ViewPager pager;
    private ViewPagerAdapter pagerAdapter;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String isLoggedIn = "LOGINKEY";
    SharedPreferences sharedPreferences;

    public static String id, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestPermissions();

        while (verifyPermissions() == false) {

        }

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        if(!sharedPreferences.contains(isLoggedIn)) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(isLoggedIn, false);
            editor.apply();
        }

        if(!sharedPreferences.getBoolean(isLoggedIn, false)){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }else{


            id = getBundleString(savedInstanceState, "id");
                    //savedInstanceState.getString("id");
            password= getBundleString(savedInstanceState, "password");
                    //=savedInstanceState.getString("password");

            id = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("id", "");

            task = new MyAsyncTask(this, id, "");
            task.execute();
            imageStorageWrapper = new ImageStorageWrapper();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    loadMaterialTabHost();
                } catch (Exception ignored) {

                }
                }
            }).start();
            new ToolbarMain(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onTabSelected(MaterialTab tab) {

    }

    @Override
    public void onTabReselected(MaterialTab tab) {

    }

    @Override
    public void onTabUnselected(MaterialTab tab) {

    }

    //----------------------------------------------------------------------------------------------
    private void loadMaterialTabHost(){
        tabHost = (MaterialTabHost) this.findViewById(R.id.materialTabHost);
        pager = (ViewPager) this.findViewById(R.id.viewPager);

        // init view pager
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // when user do a swipe the selected tab change
                tabHost.setSelectedNavigationItem(position);

            }
        });

        // insert all tabs from pagerAdapter data
        for (int i = 0; i < pagerAdapter.getCount(); i++) {
            tabHost.addTab(
                    tabHost.newTab()
                            .setText(pagerAdapter.getPageTitle(i))
                            .setTabListener(this)
            );

        }
    }

    public void saveUserImage(byte[] data) {
        imageStorageWrapper.saveUserImage(data);
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

        // send to server
    }

    public void bitmapFromServer(Bitmap bitmap) {
        homeFrag.putBitmap(bitmap);
    }

    public void bitmapToServer(Bitmap bitmap){
        task.bitmapToServer(bitmap);
    }

    public void requestImageFromServer(){
        task.requestImageFromServer();
    }

    public void requestPermissions() {

        ActivityCompat.requestPermissions(this,
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA},
                10);

    }

    public boolean verifyPermissions(){
        boolean b1 = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED;

        boolean b2 = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED;

        boolean b3 = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED;

        return b1 && b2 && b3;
    }

    protected void onDestroy(){
        super.onDestroy();
    }

    public void setHomeFrag(HomeFrag homeFrag){
        this.homeFrag = homeFrag;
    }

    public String getBundleString(Bundle savedInstanceState, String stringNeeded){
        String newString;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
            } else {
                newString= extras.getString(stringNeeded);
            }
        } else {
            newString= (String) savedInstanceState.getSerializable(stringNeeded);
        }

        return newString;
    }

    private void saveid() {
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("id", id).commit();
    }

}
