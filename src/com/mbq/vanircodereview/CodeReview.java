package com.mbq.vanircodereview;

import org.w3c.dom.NodeList;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mbq.vanircodereview.fragments.CodeReviewFrag;
import com.mbq.vanircodereview.fragments.VanirGitHub;

public class CodeReview extends FragmentActivity {
	ActionBar actionBar;

	// Used later
	Intent intent;

	Context context;

	NodeList nodelist;

	ProgressDialog pDialog;

	private long lastPressedTime;

	private static final int PERIOD = 2000;
	
	//Menu mActionBarMenu;

	Toast toast;

	TextView textview;

	private DrawerLayout mDrawerLayout;

	private ListView mDrawerList;

	SharedPreferences prefs;

	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;

	private CharSequence mTitle;

	private String[] mCategories;
	
	//Fragment preferences = new Preferences();
	
	Fragment review = new CodeReviewFrag();
	
	Fragment gh = new VanirGitHub();

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.menudrawer);	

		mTitle = mDrawerTitle = getTitle();

		mCategories = getResources().getStringArray(R.array.Items);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK);

		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, mCategories));

		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		getActionBar().setDisplayHomeAsUpEnabled(true);

		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(

		this, mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close)

		{
			public void onDrawerClosed(View view) {

				getActionBar().setTitle(mTitle);
				
			//	mActionBarMenu.removeItem(R.id.info);

				invalidateOptionsMenu();

			}

			public void onDrawerOpened(View drawerView) {

				getActionBar().setTitle(mDrawerTitle);

				invalidateOptionsMenu();

			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {

			selectItem(0);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			switch (event.getAction()) {
			case KeyEvent.ACTION_DOWN:
				if (event.getDownTime() - lastPressedTime < PERIOD) {
					finish();
				} else {
					Toast.makeText(getApplicationContext(),
							"Press again to exit.", Toast.LENGTH_SHORT).show();
					lastPressedTime = event.getEventTime();
				}
				return true;
			}
		}
		return false;
	} 

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();

		inflater.inflate(R.menu.code_review, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {

		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (mDrawerToggle.onOptionsItemSelected(item)) {

			return true;
		}
		switch (item.getItemId()) {
		
		case R.id.bye:
			Toast.makeText(getApplicationContext(), "Bye </3", Toast.LENGTH_SHORT).show();
			super.finish();
			break;

		default:

		}
		;

		return super.onOptionsItemSelected(item);
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}

	private void selectItem(int position) {

		FragmentTransaction ft = getFragmentManager().beginTransaction();
		switch (position) {
		
		case 0:
			ft.replace(R.id.content_frame, review);
			break;
			
		case 1: 
			ft.replace(R.id.content_frame, gh);
            break;

		}

		ft.commit();

		mDrawerList.setItemChecked(position, true);

		mDrawerLayout.closeDrawer(mDrawerList);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {

		super.onPostCreate(savedInstanceState);

		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {

		super.onConfigurationChanged(newConfig);

		// Pass any configuration change to the drawer toggle
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	public static class CategoriesFragment extends Fragment {

		public static final String ARG_CATEGORY = "category";

		public CategoriesFragment() {

		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			View rootView = inflater.inflate(R.layout.fragment_categories,
					container, false);

			int i = getArguments().getInt(ARG_CATEGORY);

			String List = getResources().getStringArray(R.array.Items)[i];

			getActivity().setTitle(List);

			return rootView;

		}
	}
}