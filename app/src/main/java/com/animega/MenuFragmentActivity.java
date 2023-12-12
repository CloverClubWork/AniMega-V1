package com.animega;

import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.content.*;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.*;
import android.graphics.*;
import android.graphics.Typeface;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.net.Uri;
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
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.*;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.blogspot.atifsoftwares.animatoolib.*;
import com.github.ybq.android.spinkit.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import org.json.*;
import xyz.hasnat.sweettoast.*;

public class MenuFragmentActivity extends Fragment {
	
	private ScrollView vscroll1;
	private LinearLayout linear1;
	private LinearLayout linear2;
	private LinearLayout linear4;
	private LinearLayout linear5;
	private LinearLayout linear6;
	private TextView textview1;
	private Switch switch1;
	private ImageView imageview1;
	private TextView textview2;
	private ImageView imageview2;
	private TextView textview3;
	private TextView textview4;
	private TextView textview5;
	
	private SharedPreferences skipTime;
	private Intent intent = new Intent();
	
	@NonNull
	@Override
	public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
		View _view = _inflater.inflate(R.layout.menu_fragment, _container, false);
		initialize(_savedInstanceState, _view);
		initializeLogic();
		return _view;
	}
	
	private void initialize(Bundle _savedInstanceState, View _view) {
		vscroll1 = _view.findViewById(R.id.vscroll1);
		linear1 = _view.findViewById(R.id.linear1);
		linear2 = _view.findViewById(R.id.linear2);
		linear4 = _view.findViewById(R.id.linear4);
		linear5 = _view.findViewById(R.id.linear5);
		linear6 = _view.findViewById(R.id.linear6);
		textview1 = _view.findViewById(R.id.textview1);
		switch1 = _view.findViewById(R.id.switch1);
		imageview1 = _view.findViewById(R.id.imageview1);
		textview2 = _view.findViewById(R.id.textview2);
		imageview2 = _view.findViewById(R.id.imageview2);
		textview3 = _view.findViewById(R.id.textview3);
		textview4 = _view.findViewById(R.id.textview4);
		textview5 = _view.findViewById(R.id.textview5);
		skipTime = getContext().getSharedPreferences("skipTime", Activity.MODE_PRIVATE);
		
		linear4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_openChrome("https://sociabuzz.com/cloverclub_03/tribe");
			}
		});
		
		linear5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_openChrome("https://github.com/CloverClubWork/AniMega-V1");
			}
		});
		
		switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton _param1, boolean _param2) {
				final boolean _isChecked = _param2;
				if (_isChecked) {
					skipTime.edit().putString("skipTime", "show").commit();
				}
				else {
					skipTime.edit().putString("skipTime", "hide").commit();
				}
			}
		});
	}
	
	private void initializeLogic() {
		textview1.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/comfortaa_medium.ttf"), 1);
		switch1.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/comfortaa_medium.ttf"), 1);
		textview3.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/comfortaa_medium.ttf"), 1);
		textview2.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/comfortaa_medium.ttf"), 1);
		textview4.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/comfortaa_medium.ttf"), 1);
		textview5.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/comfortaa_medium.ttf"), 1);
		imageview2.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_ATOP);
		if (skipTime.contains("skipTime")) {
			if (skipTime.getString("skipTime", "").equals("show")) {
				switch1.setChecked(true);
			}
			else {
				switch1.setChecked(false);
			}
		}
		else {
			skipTime.edit().putString("skipTime", "show").commit();
			switch1.setChecked(true);
		}
	}
	
	public void _openChrome(final String _url) {
		androidx.browser.customtabs.CustomTabsIntent.Builder builder = new androidx.browser.customtabs.CustomTabsIntent.Builder();
		androidx.browser.customtabs.CustomTabsIntent customTabsIntent = builder.build();
		customTabsIntent.launchUrl(getCurrentContext(this), Uri.parse(_url));
		
		
		
	}
	
	public Context getCurrentContext(Context c) {
		    return c;
	}
	public Context getCurrentContext(Fragment c) {
		    return c.getActivity();
	}
	public Context getCurrentContext(DialogFragment c) {
		    return c.getActivity();
	}
	
	
	{
	}
	
}