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
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.*;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener;
import com.blogspot.atifsoftwares.animatoolib.*;
import com.bumptech.glide.Glide;
import com.github.ybq.android.spinkit.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import org.json.*;
import xyz.hasnat.sweettoast.*;
import androidx.core.widget.NestedScrollView;

public class AnimePageActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	
	private HashMap<String, Object> animeInfoMap = new HashMap<>();
	private double genreNumberCount = 0;
	private double episodeNumber = 0;
	private double limitPage = 0;
	private HashMap<String, Object> episodesMap = new HashMap<>();
	private double limitPageBreak = 0;
	private double otherNamesNumber = 0;
	private String EpisodeTitle = "";
	private double EpisodePosition = 0;
	
	private ArrayList<String> geneStringList = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> episodesListMap = new ArrayList<>();
	private ArrayList<String> stringStrMap = new ArrayList<>();
	
	private SwipeRefreshLayout swiperefreshlayout1;
	private NestedScrollView vscroll1;
	private LinearLayout linear1;
	private LinearLayout linear2;
	private LinearLayout toolbar;
	private LinearLayout linear4;
	private LinearLayout linear9;
	private LinearLayout linear5;
	private LinearLayout linear16;
	private LinearLayout linear17;
	private LinearLayout linear6;
	private LinearLayout backBtn;
	private TextView textview_title;
	private ImageView backIcon;
	private CardView cardview1;
	private TextView textview_title2;
	private ImageView imageview_cover;
	private TextView textview3;
	private HorizontalScrollView hscroll2;
	private LinearLayout linear18;
	private LinearLayout linear12;
	private LinearLayout linear13;
	private LinearLayout linear10;
	private LinearLayout linear11;
	private TextView textview4;
	private TextView textview_status;
	private TextView textview5;
	private TextView textview_subtype;
	private LinearLayout linear14;
	private LinearLayout linear15;
	private TextView textview6;
	private TextView textview_type;
	private TextView textview7;
	private TextView textview_release;
	private TextView textview8;
	private HorizontalScrollView hscroll1;
	private LinearLayout linear_genre;
	private TextView textview9;
	private TextView textview_synopsis;
	private LinearLayout linear7;
	private SpinKitView loader1;
	private RecyclerView recyclerview1;
	private TextView textview1;
	private LinearLayout linear8;
	private LinearLayout epNextBtn;
	private TextView textview2;
	private ImageView imageview1;
	
	private RequestNetwork requestAnimeInfo;
	private RequestNetwork.RequestListener _requestAnimeInfo_request_listener;
	private RequestNetwork requestEpisodes;
	private RequestNetwork.RequestListener _requestEpisodes_request_listener;
	private TimerTask clickDelay;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.anime_page);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		swiperefreshlayout1 = findViewById(R.id.swiperefreshlayout1);
		vscroll1 = findViewById(R.id.vscroll1);
		linear1 = findViewById(R.id.linear1);
		linear2 = findViewById(R.id.linear2);
		toolbar = findViewById(R.id.toolbar);
		linear4 = findViewById(R.id.linear4);
		linear9 = findViewById(R.id.linear9);
		linear5 = findViewById(R.id.linear5);
		linear16 = findViewById(R.id.linear16);
		linear17 = findViewById(R.id.linear17);
		linear6 = findViewById(R.id.linear6);
		backBtn = findViewById(R.id.backBtn);
		textview_title = findViewById(R.id.textview_title);
		backIcon = findViewById(R.id.backIcon);
		cardview1 = findViewById(R.id.cardview1);
		textview_title2 = findViewById(R.id.textview_title2);
		imageview_cover = findViewById(R.id.imageview_cover);
		textview3 = findViewById(R.id.textview3);
		hscroll2 = findViewById(R.id.hscroll2);
		linear18 = findViewById(R.id.linear18);
		linear12 = findViewById(R.id.linear12);
		linear13 = findViewById(R.id.linear13);
		linear10 = findViewById(R.id.linear10);
		linear11 = findViewById(R.id.linear11);
		textview4 = findViewById(R.id.textview4);
		textview_status = findViewById(R.id.textview_status);
		textview5 = findViewById(R.id.textview5);
		textview_subtype = findViewById(R.id.textview_subtype);
		linear14 = findViewById(R.id.linear14);
		linear15 = findViewById(R.id.linear15);
		textview6 = findViewById(R.id.textview6);
		textview_type = findViewById(R.id.textview_type);
		textview7 = findViewById(R.id.textview7);
		textview_release = findViewById(R.id.textview_release);
		textview8 = findViewById(R.id.textview8);
		hscroll1 = findViewById(R.id.hscroll1);
		linear_genre = findViewById(R.id.linear_genre);
		textview9 = findViewById(R.id.textview9);
		textview_synopsis = findViewById(R.id.textview_synopsis);
		linear7 = findViewById(R.id.linear7);
		loader1 = findViewById(R.id.loader1);
		recyclerview1 = findViewById(R.id.recyclerview1);
		textview1 = findViewById(R.id.textview1);
		linear8 = findViewById(R.id.linear8);
		epNextBtn = findViewById(R.id.epNextBtn);
		textview2 = findViewById(R.id.textview2);
		imageview1 = findViewById(R.id.imageview1);
		requestAnimeInfo = new RequestNetwork(this);
		requestEpisodes = new RequestNetwork(this);
		
		swiperefreshlayout1.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				loader1.setVisibility(View.VISIBLE);
				swiperefreshlayout1.setRefreshing(true);
				if (SketchwareUtil.isConnected(getApplicationContext())) {
					requestAnimeInfo.startRequestNetwork(RequestNetworkController.GET, getIntent().getStringExtra("path"), "a", _requestAnimeInfo_request_listener);
					requestEpisodes.startRequestNetwork(RequestNetworkController.GET, getIntent().getStringExtra("episodePath"), "a", _requestEpisodes_request_listener);
				}
				else {
					swiperefreshlayout1.setRefreshing(false);
					SweetToast.warning(getApplicationContext(), "Please check your internet connection and try again");
				}
			}
		});
		
		backBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				finish();
				Animatoo.animateSwipeLeft(AnimePageActivity.this);
			}
		});
		
		epNextBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_click_effect(epNextBtn, "#212121");
				limitPageBreak = limitPage;
				limitPage = limitPage + 30;
				loader1.setVisibility(View.VISIBLE);
				requestEpisodes.startRequestNetwork(RequestNetworkController.GET, getIntent().getStringExtra("episodePath"), "a", _requestEpisodes_request_listener);
			}
		});
		
		_requestAnimeInfo_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				swiperefreshlayout1.setRefreshing(false);
				animeInfoMap = new Gson().fromJson(_response, new TypeToken<HashMap<String, Object>>(){}.getType());
				linear2.setVisibility(View.VISIBLE);
				try {
					textview_subtype.setText(getIntent().getStringExtra("type"));
					if (animeInfoMap.containsKey("title")) {
						try{
							textview_title.setText(animeInfoMap.get("title").toString());
							textview_title2.setText(animeInfoMap.get("title").toString());
						}catch(NullPointerException e){
							textview_title.setText("Unknown Title");
							textview_title2.setText("Unknown Title");
							e.printStackTrace();
						}
					}
					if (animeInfoMap.containsKey("image_url")) {
						try{
							
							androidx.swiperefreshlayout.widget.CircularProgressDrawable loader = new androidx.swiperefreshlayout.widget.CircularProgressDrawable(getApplicationContext());
							loader.setColorSchemeColors(Color.BLUE, Color.YELLOW, Color.RED);
							loader.setStrokeWidth(6f);
							loader.setCenterRadius(35f);
							loader.start();
							 Glide.with(getApplicationContext())
							.load(Uri.parse(animeInfoMap.get("image_url").toString()))
							.placeholder(loader)
							.error(R.drawable.nocover)
							.into(imageview_cover);
						}catch(NullPointerException e){
							imageview_cover.setImageResource(R.drawable.nocover);
							e.printStackTrace();
						}
					}
					if (animeInfoMap.containsKey("status")) {
						try{
							textview_status.setText(animeInfoMap.get("status").toString());
						}catch(NullPointerException e){
							textview_status.setText("Unknown Status");
							e.printStackTrace();
						}
					}
					if (animeInfoMap.containsKey("season")) {
						try{
							textview_type.setText(animeInfoMap.get("season").toString());
						}catch(NullPointerException e){
							textview_type.setText("Unknown Season");
							e.printStackTrace();
						}
					}
					if (animeInfoMap.containsKey("released")) {
						try{
							textview_release.setText(animeInfoMap.get("released").toString());
						}catch(NullPointerException e){
							textview_release.setText("Unknown Release Date");
							e.printStackTrace();
						}
					}
					if (animeInfoMap.containsKey("synopsis")) {
						try{
							textview_synopsis.setText(animeInfoMap.get("synopsis").toString());
						}catch(NullPointerException e){
							textview_synopsis.setText("Unknown Description");
							e.printStackTrace();
						}
					}
					if (animeInfoMap.containsKey("otherNames")) {
						try{
							String strGen = (new Gson()).toJson(animeInfoMap.get("otherNames"), new TypeToken<ArrayList<String>>(){}.getType());
							stringStrMap = new Gson().fromJson(strGen, new TypeToken<ArrayList<String>>(){}.getType());
							otherNamesNumber = 0;
							for(int _repeat303 = 0; _repeat303 < (int)(stringStrMap.size()); _repeat303++) {
								LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
								params1.setMargins(0,0,13,0);
								final TextView titleText = new TextView(AnimePageActivity.this);
								titleText.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/comfortaa_medium.ttf"), 1);
								titleText.setText(stringStrMap.get((int)(otherNamesNumber)));
								titleText.setTextColor(0xFFFFFFFF);
								titleText.setPadding(0,0,0,0);
								titleText.setTextSize(12);
								titleText.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)10, Color.TRANSPARENT));
								titleText.setLayoutParams(params1);
								linear18.addView(titleText);
								otherNamesNumber++;
							}
						}catch(com.google.gson.JsonSyntaxException e){
							LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
							params1.setMargins(0,0,13,0);
							final TextView titleText = new TextView(AnimePageActivity.this);
							titleText.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/comfortaa_medium.ttf"), 1);
							titleText.setText("Failed to fetch titles");
							titleText.setTextColor(0xFFFFFFFF);
							titleText.setPadding(0,0,0,0);
							titleText.setTextSize(12);
							titleText.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)10, Color.TRANSPARENT));
							titleText.setLayoutParams(params1);
							linear18.addView(titleText);
							e.printStackTrace();
						}
					}
					if (animeInfoMap.containsKey("genres")) {
						try{
							String strGen = (new Gson()).toJson(animeInfoMap.get("genres"), new TypeToken<ArrayList<String>>(){}.getType());
							geneStringList = new Gson().fromJson(strGen, new TypeToken<ArrayList<String>>(){}.getType());
							genreNumberCount = 0;
							for(int _repeat228 = 0; _repeat228 < (int)(geneStringList.size()); _repeat228++) {
								LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
								params.setMargins(0,0,13,0);
								final TextView genreText = new TextView(AnimePageActivity.this);
								genreText.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/comfortaa_medium.ttf"), 1);
								genreText.setText(geneStringList.get((int)(genreNumberCount)));
								genreText.setTextColor(0xFFFFFFFF);
								genreText.setPadding(0,0,0,0);
								genreText.setTextSize(12);
								genreText.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)10, Color.TRANSPARENT));
								genreText.setLayoutParams(params);
								linear_genre.addView(genreText);
								genreNumberCount++;
							}
						}catch(com.google.gson.JsonSyntaxException e){
							LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
							params.setMargins(0,0,0,0);
							final TextView genreText = new TextView(AnimePageActivity.this);
							genreText.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/comfortaa_medium.ttf"), 1);
							genreText.setText("Unknown Genres");
							genreText.setTextColor(0xFFFFFFFF);
							genreText.setTextSize(12);
							genreText.setPadding(0,0,0,0);
							genreText.setElevation((float)10);
							genreText.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)10, Color.TRANSPARENT));
							genreText.setLayoutParams(params);
							linear_genre.addView(genreText);
							e.printStackTrace();
						}
					}
				} catch (Exception e) {
					linear2.setVisibility(View.GONE);
					SweetToast.error(getApplicationContext(), "Something went wrong, please contact the developer");
					e.printStackTrace();
				}
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				
			}
		};
		
		_requestEpisodes_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				loader1.setVisibility(View.GONE);
				episodesMap = new Gson().fromJson(_response, new TypeToken<HashMap<String, Object>>(){}.getType());
				try {
					if (episodesMap.containsKey("title")) {
						EpisodeTitle = episodesMap.get("title").toString();
					}
					if (episodesMap.containsKey("episodes")) {
						String strEp = (new Gson()).toJson(episodesMap.get("episodes"), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
						episodesListMap = new Gson().fromJson(strEp, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
						if (episodesListMap.size() > 30) {
							epNextBtn.setVisibility(View.VISIBLE);
						}
						else {
							epNextBtn.setVisibility(View.GONE);
						}
						if (!(limitPage > 30)) {
							Collections.reverse(episodesListMap);
							while(episodesListMap.size() > (int) limitPage) {
								episodesListMap.remove((int)(limitPage));
							}
							recyclerview1.setAdapter(new Recyclerview1Adapter(episodesListMap));
						}
						else {
							Collections.reverse(episodesListMap);
							while(episodesListMap.size() > (int) limitPage) {
								episodesListMap.remove((int)(limitPage));
							}
							for(int _repeat151 = 0; _repeat151 < (int)(limitPageBreak); _repeat151++) {
								episodesListMap.remove((int)(0));
							}
							recyclerview1.setAdapter(new Recyclerview1Adapter(episodesListMap));
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				
			}
		};
	}
	
	private void initializeLogic() {
		_removeScollBar(vscroll1);
		_removeScollBar(hscroll1);
		_click_effect(backBtn, "#212121");
		limitPage = 30;
		cardview1.setCardBackgroundColor(0xFFFFFFFF);
		cardview1.setRadius((float)8);
		cardview1.setCardElevation((float)8);
		cardview1.setPreventCornerOverlap(false);
		linear2.setVisibility(View.GONE);
		textview_title.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/comforta_bold.ttf"), 1);
		textview_title2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/comforta_bold.ttf"), 1);
		textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/comforta_bold.ttf"), 1);
		textview2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/comforta_bold.ttf"), 1);
		textview_status.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/comfortaa_medium.ttf"), 1);
		textview_subtype.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/comfortaa_medium.ttf"), 1);
		textview_type.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/comfortaa_medium.ttf"), 1);
		textview_release.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/comfortaa_medium.ttf"), 1);
		textview_synopsis.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/comfortaa_medium.ttf"), 1);
		textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/comforta_bold.ttf"), 1);
		textview2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/comforta_bold.ttf"), 1);
		textview3.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/comforta_bold.ttf"), 1);
		textview4.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/comforta_bold.ttf"), 1);
		textview5.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/comforta_bold.ttf"), 1);
		textview6.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/comforta_bold.ttf"), 1);
		textview7.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/comforta_bold.ttf"), 1);
		textview8.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/comforta_bold.ttf"), 1);
		textview9.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/comforta_bold.ttf"), 1);
		recyclerview1.setLayoutManager(new LinearLayoutManager(this));
		swiperefreshlayout1.setColorSchemeColors(Color.BLUE, Color.YELLOW, Color.RED);
		loader1.setVisibility(View.VISIBLE);
		swiperefreshlayout1.setRefreshing(true);
		if (SketchwareUtil.isConnected(getApplicationContext())) {
			requestAnimeInfo.startRequestNetwork(RequestNetworkController.GET, getIntent().getStringExtra("path"), "a", _requestAnimeInfo_request_listener);
			requestEpisodes.startRequestNetwork(RequestNetworkController.GET, getIntent().getStringExtra("episodePath"), "a", _requestEpisodes_request_listener);
		}
		else {
			swiperefreshlayout1.setRefreshing(false);
			SweetToast.warning(getApplicationContext(), "Please check your internet connection and try again");
		}
	}
	
	@Override
	public void onBackPressed() {
		finish();
		Animatoo.animateSwipeLeft(AnimePageActivity.this);
	}
	
	public void _click_effect(final View _view, final String _c) {
		_view.setBackground(Drawables.getSelectableDrawableFor(Color.parseColor(_c)));
		_view.setClickable(true);
		
	}
	
	public static class Drawables {
		    public static android.graphics.drawable.Drawable getSelectableDrawableFor(int color) {
			        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
				            android.graphics.drawable.StateListDrawable stateListDrawable = new android.graphics.drawable.StateListDrawable();
				            stateListDrawable.addState(
				                new int[]{android.R.attr.state_pressed},
				                new android.graphics.drawable.ColorDrawable(Color.parseColor("#ffffff"))
				            );
				            stateListDrawable.addState(
				                new int[]{android.R.attr.state_focused},
				                new android.graphics.drawable.ColorDrawable(Color.parseColor("#00ffffff"))
				            );
				            stateListDrawable.addState(
				                new int[]{},
				                new android.graphics.drawable.ColorDrawable(Color.parseColor("#00ffffff"))
				            );
				            return stateListDrawable;
				        } else {
				            android.content.res.ColorStateList pressedColor = android.content.res.ColorStateList.valueOf(color);
				            android.graphics.drawable.ColorDrawable defaultColor = new android.graphics.drawable.ColorDrawable(Color.parseColor("#00ffffff"));
				            
				android.graphics.drawable.Drawable rippleColor = getRippleColor(color);
				            return new android.graphics.drawable.RippleDrawable(
				                pressedColor,
				                defaultColor,
				                rippleColor
				            );
				        }
			    }
		
		    private static android.graphics.drawable.Drawable getRippleColor(int color) {
			        float[] outerRadii = new float[8];
			        Arrays.fill(outerRadii, 0);
			        android.graphics.drawable.shapes.RoundRectShape r = new android.graphics.drawable.shapes.RoundRectShape(outerRadii, null, null);
			        
			android.graphics.drawable.ShapeDrawable shapeDrawable = new 
			android.graphics.drawable.ShapeDrawable(r);
			        shapeDrawable.getPaint().setColor(color);
			        return shapeDrawable;
			    }
		 
		    private static int lightenOrDarken(int color, double fraction) {
			        if (canLighten(color, fraction)) {
				            return lighten(color, fraction);
				        } else {
				            return darken(color, fraction);
				        }
			    }
		 
		    private static int lighten(int color, double fraction) {
			        int red = Color.red(color);
			        int green = Color.green(color);
			        int blue = Color.blue(color);
			        red = lightenColor(red, fraction);
			        green = lightenColor(green, fraction);
			        blue = lightenColor(blue, fraction);
			        int alpha = Color.alpha(color);
			        return Color.argb(alpha, red, green, blue);
			    }
		 
		    private static int darken(int color, double fraction) {
			        int red = Color.red(color);
			        int green = Color.green(color);
			        int blue = Color.blue(color);
			        red = darkenColor(red, fraction);
			        green = darkenColor(green, fraction);
			        blue = darkenColor(blue, fraction);
			        int alpha = Color.alpha(color);
			 
			        return Color.argb(alpha, red, green, blue);
			    }
		 
		    private static boolean canLighten(int color, double fraction) {
			        int red = Color.red(color);
			        int green = Color.green(color);
			        int blue = Color.blue(color);
			        return canLightenComponent(red, fraction)
			            && canLightenComponent(green, fraction)
			            && canLightenComponent(blue, fraction);
			    }
		 
		    private static boolean canLightenComponent(int colorComponent, double fraction) {
			        int red = Color.red(colorComponent);
			        int green = Color.green(colorComponent);
			        int blue = Color.blue(colorComponent);
			        return red + (red * fraction) < 255
			            && green + (green * fraction) < 255
			            && blue + (blue * fraction) < 255;
			    }
		 
		    private static int darkenColor(int color, double fraction) {
			        return (int) Math.max(color - (color * fraction), 0);
			    }
		 
		    private static int lightenColor(int color, double fraction) {
			        return (int) Math.min(color + (color * fraction), 255);
			    }
	}
	public static class CircleDrawables {
		    public static android.graphics.drawable.Drawable getSelectableDrawableFor(int color) {
			        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
				            android.graphics.drawable.StateListDrawable stateListDrawable = new android.graphics.drawable.StateListDrawable();
				            stateListDrawable.addState(
				                new int[]{android.R.attr.state_pressed},
				                new android.graphics.drawable.ColorDrawable(Color.parseColor("#ffffff"))
				            );
				            stateListDrawable.addState(
				                new int[]{android.R.attr.state_focused},
				                new android.graphics.drawable.ColorDrawable(Color.parseColor("#00ffffff"))
				            );
				            stateListDrawable.addState(
				                new int[]{},
				                new android.graphics.drawable.ColorDrawable(Color.parseColor("#00ffffff"))
				            );
				            return stateListDrawable;
				        } else {
				            android.content.res.ColorStateList pressedColor = android.content.res.ColorStateList.valueOf(color);
				            android.graphics.drawable.ColorDrawable defaultColor = new android.graphics.drawable.ColorDrawable(Color.parseColor("#00ffffff"));
				            
				android.graphics.drawable.Drawable rippleColor = getRippleColor(color);
				            return new android.graphics.drawable.RippleDrawable(
				                pressedColor,
				                defaultColor,
				                rippleColor
				            );
				        }
			    }
		
		    private static android.graphics.drawable.Drawable getRippleColor(int color) {
			        float[] outerRadii = new float[180];
			        Arrays.fill(outerRadii, 80);
			        android.graphics.drawable.shapes.RoundRectShape r = new android.graphics.drawable.shapes.RoundRectShape(outerRadii, null, null);
			        
			android.graphics.drawable.ShapeDrawable shapeDrawable = new 
			android.graphics.drawable.ShapeDrawable(r);
			        shapeDrawable.getPaint().setColor(color);
			        return shapeDrawable;
			    }
		 
		    private static int lightenOrDarken(int color, double fraction) {
			        if (canLighten(color, fraction)) {
				            return lighten(color, fraction);
				        } else {
				            return darken(color, fraction);
				        }
			    }
		 
		    private static int lighten(int color, double fraction) {
			        int red = Color.red(color);
			        int green = Color.green(color);
			        int blue = Color.blue(color);
			        red = lightenColor(red, fraction);
			        green = lightenColor(green, fraction);
			        blue = lightenColor(blue, fraction);
			        int alpha = Color.alpha(color);
			        return Color.argb(alpha, red, green, blue);
			    }
		 
		    private static int darken(int color, double fraction) {
			        int red = Color.red(color);
			        int green = Color.green(color);
			        int blue = Color.blue(color);
			        red = darkenColor(red, fraction);
			        green = darkenColor(green, fraction);
			        blue = darkenColor(blue, fraction);
			        int alpha = Color.alpha(color);
			 
			        return Color.argb(alpha, red, green, blue);
			    }
		 
		    private static boolean canLighten(int color, double fraction) {
			        int red = Color.red(color);
			        int green = Color.green(color);
			        int blue = Color.blue(color);
			        return canLightenComponent(red, fraction)
			            && canLightenComponent(green, fraction)
			            && canLightenComponent(blue, fraction);
			    }
		 
		    private static boolean canLightenComponent(int colorComponent, double fraction) {
			        int red = Color.red(colorComponent);
			        int green = Color.green(colorComponent);
			        int blue = Color.blue(colorComponent);
			        return red + (red * fraction) < 255
			            && green + (green * fraction) < 255
			            && blue + (blue * fraction) < 255;
			    }
		 
		    private static int darkenColor(int color, double fraction) {
			        return (int) Math.max(color - (color * fraction), 0);
			    }
		 
		    private static int lightenColor(int color, double fraction) {
			        return (int) Math.min(color + (color * fraction), 255);
		}
	}
	
	public void drawableclass() {
	}
	
	
	public void _removeScollBar(final View _view) {
		_view.setVerticalScrollBarEnabled(false); _view.setHorizontalScrollBarEnabled(false);
	}
	
	public class Recyclerview1Adapter extends RecyclerView.Adapter<Recyclerview1Adapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getLayoutInflater();
			View _v = _inflater.inflate(R.layout.episodes, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final androidx.cardview.widget.CardView cardview1 = _view.findViewById(R.id.cardview1);
			final RelativeLayout linear1 = _view.findViewById(R.id.linear1);
			final LinearLayout linear2 = _view.findViewById(R.id.linear2);
			final LinearLayout linear3 = _view.findViewById(R.id.linear3);
			final TextView textview1 = _view.findViewById(R.id.textview1);
			
			cardview1.setCardBackgroundColor(0x80212121);
			cardview1.setRadius((float)10);
			cardview1.setCardElevation((float)0);
			cardview1.setPreventCornerOverlap(false);
			textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/comfortaa_medium.ttf"), 1);
			textview1.setText(_data.get((int)_position).get("title").toString());
			linear3.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					_click_effect(linear3, "#76FF03");
					clickDelay = new TimerTask() {
						@Override
						public void run() {
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									Intent intent = new Intent(AnimePageActivity.this, PlayerActivity.class);
									intent.putExtra("path", "https://api-amvstrm.nyt92.eu.org/api/v1/stream/".concat(_data.get((int)_position).get("id").toString()));
									EpisodePosition = _position + 1;
									intent.putExtra("episodeTitle", EpisodeTitle.concat(" - ".concat(String.valueOf((long)(EpisodePosition)))));
									intent.putExtra("animeID", _data.get((int)_position).get("id").toString());
									startActivity(intent);
									Animatoo.animateSwipeLeft(AnimePageActivity.this);
								}
							});
						}
					};
					_timer.schedule(clickDelay, (int)(300));
				}
			});
		}
		
		@Override
		public int getItemCount() {
			return _data.size();
		}
		
		public class ViewHolder extends RecyclerView.ViewHolder {
			public ViewHolder(View v) {
				super(v);
			}
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