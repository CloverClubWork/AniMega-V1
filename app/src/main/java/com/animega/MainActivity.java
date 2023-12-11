package com.animega;

import android.animation.*;
import android.app.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.Typeface;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.os.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.*;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnAdapterChangeListener;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import com.blogspot.atifsoftwares.animatoolib.*;
import com.github.ybq.android.spinkit.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import org.json.*;
import xyz.hasnat.sweettoast.*;

public class MainActivity extends AppCompatActivity {
	
	private LinearLayout linear1;
	private ViewPager viewpager1;
	private LinearLayout bottomNavigation;
	private LinearLayout homeNav;
	private LinearLayout searchNav;
	private ImageView imageview1;
	private TextView textview1;
	private ImageView imageview2;
	private TextView textview2;
	
	private NavigationFragmentsFragmentAdapter navigationFragments;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear1 = findViewById(R.id.linear1);
		viewpager1 = findViewById(R.id.viewpager1);
		bottomNavigation = findViewById(R.id.bottomNavigation);
		homeNav = findViewById(R.id.homeNav);
		searchNav = findViewById(R.id.searchNav);
		imageview1 = findViewById(R.id.imageview1);
		textview1 = findViewById(R.id.textview1);
		imageview2 = findViewById(R.id.imageview2);
		textview2 = findViewById(R.id.textview2);
		navigationFragments = new NavigationFragmentsFragmentAdapter(getApplicationContext(), getSupportFragmentManager());
		
		viewpager1.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int _position, float _positionOffset, int _positionOffsetPixels) {
				
			}
			
			@Override
			public void onPageSelected(int _position) {
				_NavigationBar(_position);
			}
			
			@Override
			public void onPageScrollStateChanged(int _scrollState) {
				
			}
		});
		
		homeNav.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_NavigationBar(0);
			}
		});
		
		searchNav.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_NavigationBar(1);
			}
		});
	}
	
	private void initializeLogic() {
		textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/comforta_bold.ttf"), 1);
		textview2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/comforta_bold.ttf"), 1);
		homeNav.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)50, 0xFF1A237E));
		imageview1.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_ATOP);
		searchNav.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)50, Color.TRANSPARENT));
		imageview2.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_ATOP);
		navigationFragments.setTabCount(2);
		viewpager1.setAdapter(navigationFragments);
		viewpager1.setCurrentItem((int)0);
	}
	
	public class NavigationFragmentsFragmentAdapter extends FragmentStatePagerAdapter {
		// This class is deprecated, you should migrate to ViewPager2:
		// https://developer.android.com/reference/androidx/viewpager2/widget/ViewPager2
		Context context;
		int tabCount;
		
		public NavigationFragmentsFragmentAdapter(Context context, FragmentManager manager) {
			super(manager);
			this.context = context;
		}
		
		public void setTabCount(int tabCount) {
			this.tabCount = tabCount;
		}
		
		@Override
		public int getCount() {
			return tabCount;
		}
		
		@Override
		public CharSequence getPageTitle(int _position) {
			
			return null;
		}
		
		@Override
		public Fragment getItem(int _position) {
			if (_position == 0) {
				return new HomepageFragmentActivity();
			}
			if (_position == 1) {
				return new SearchFragmentActivity();
			}
			return null;
		}
	}
	
	public void _NavigationBar(final double _position) {
		if (_position == 0) {
			textview1.setVisibility(View.VISIBLE);
			textview2.setVisibility(View.GONE);
			homeNav.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)50, 0xFF1A237E));
			imageview1.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_ATOP);
			searchNav.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)50, Color.TRANSPARENT));
			imageview2.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_ATOP);
			viewpager1.setCurrentItem((int)0);
		}
		if (_position == 1) {
			textview1.setVisibility(View.GONE);
			textview2.setVisibility(View.VISIBLE);
			homeNav.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)50, Color.TRANSPARENT));
			imageview1.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_ATOP);
			searchNav.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)50, 0xFF1A237E));
			imageview2.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_ATOP);
			viewpager1.setCurrentItem((int)1);
		}
	}
	
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels() {
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels() {
		return getResources().getDisplayMetrics().heightPixels;
	}
}