package com.animega;

import android.animation.*;
import android.app.*;
import android.content.*;
import android.content.Intent;
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
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.*;
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

public class HomepageFragmentActivity extends Fragment {
	
	private Timer _timer = new Timer();
	
	private HashMap<String, Object> animeMap = new HashMap<>();
	private double episodeNumber = 0;
	
	private ArrayList<HashMap<String, Object>> animeListMap = new ArrayList<>();
	
	private SwipeRefreshLayout swiperefreshlayout1;
	private ScrollView vscroll1;
	private LinearLayout linear1;
	private LinearLayout linear2;
	private LinearLayout linear3;
	private LinearLayout linear4;
	private TextView textview1;
	private SpinKitView loader1;
	private RecyclerView recyclerview1;
	private TextView textview2;
	private SpinKitView loader2;
	private RecyclerView recyclerview2;
	private TextView textview3;
	private SpinKitView loader3;
	private RecyclerView recyclerview3;
	
	private RequestNetwork requestData;
	private RequestNetwork.RequestListener _requestData_request_listener;
	private RequestNetwork requestTopAiring;
	private RequestNetwork.RequestListener _requestTopAiring_request_listener;
	private TimerTask clickDelay;
	private Intent intent = new Intent();
	private RequestNetwork requestPopular;
	private RequestNetwork.RequestListener _requestPopular_request_listener;
	
	@NonNull
	@Override
	public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
		View _view = _inflater.inflate(R.layout.homepage_fragment, _container, false);
		initialize(_savedInstanceState, _view);
		initializeLogic();
		return _view;
	}
	
	private void initialize(Bundle _savedInstanceState, View _view) {
		swiperefreshlayout1 = _view.findViewById(R.id.swiperefreshlayout1);
		vscroll1 = _view.findViewById(R.id.vscroll1);
		linear1 = _view.findViewById(R.id.linear1);
		linear2 = _view.findViewById(R.id.linear2);
		linear3 = _view.findViewById(R.id.linear3);
		linear4 = _view.findViewById(R.id.linear4);
		textview1 = _view.findViewById(R.id.textview1);
		loader1 = _view.findViewById(R.id.loader1);
		recyclerview1 = _view.findViewById(R.id.recyclerview1);
		textview2 = _view.findViewById(R.id.textview2);
		loader2 = _view.findViewById(R.id.loader2);
		recyclerview2 = _view.findViewById(R.id.recyclerview2);
		textview3 = _view.findViewById(R.id.textview3);
		loader3 = _view.findViewById(R.id.loader3);
		recyclerview3 = _view.findViewById(R.id.recyclerview3);
		requestData = new RequestNetwork((Activity) getContext());
		requestTopAiring = new RequestNetwork((Activity) getContext());
		requestPopular = new RequestNetwork((Activity) getContext());
		
		swiperefreshlayout1.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				loader1.setVisibility(View.VISIBLE);
				loader2.setVisibility(View.VISIBLE);
				swiperefreshlayout1.setRefreshing(false);
				if (SketchwareUtil.isConnected(getContext().getApplicationContext())) {
					requestData.startRequestNetwork(RequestNetworkController.GET, "https://www.novelpubfile.xyz/anime/gogoanime/recent-episodes?page=1&type=1", "a", _requestData_request_listener);
					requestTopAiring.startRequestNetwork(RequestNetworkController.GET, "https://www.novelpubfile.xyz/anime/gogoanime/top-airing?page=1", "a", _requestTopAiring_request_listener);
				}
				else {
					swiperefreshlayout1.setRefreshing(false);
					SweetToast.warning(getContext().getApplicationContext(), "Please check your internet connection and try again");
				}
			}
		});
		
		_requestData_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				loader1.setVisibility(View.GONE);
				swiperefreshlayout1.setRefreshing(false);
				try{
					animeMap = new Gson().fromJson(_response, new TypeToken<HashMap<String, Object>>(){}.getType());
					if (animeMap.containsKey("results")) {
						String results = (new Gson()).toJson(animeMap.get("results"), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
						animeListMap = new Gson().fromJson(results, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
						recyclerview1.setAdapter(new Recyclerview1Adapter(animeListMap));
					}
				}catch(NullPointerException e){
					e.printStackTrace();
					SweetToast.error(getContext().getApplicationContext(), "Something went wrong! Please try again");
				}
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				
			}
		};
		
		_requestTopAiring_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				loader2.setVisibility(View.GONE);
				try{
					animeMap = new Gson().fromJson(_response, new TypeToken<HashMap<String, Object>>(){}.getType());
					if (animeMap.containsKey("results")) {
						String results = (new Gson()).toJson(animeMap.get("results"), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
						animeListMap = new Gson().fromJson(results, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
						recyclerview2.setAdapter(new Recyclerview2Adapter(animeListMap));
					}
				}catch(NullPointerException e){
					e.printStackTrace();
					SweetToast.error(getContext().getApplicationContext(), "Something went wrong! Please try again");
				}
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				
			}
		};
		
		_requestPopular_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				loader3.setVisibility(View.GONE);
				try{
					animeMap = new Gson().fromJson(_response, new TypeToken<HashMap<String, Object>>(){}.getType());
					if (animeMap.containsKey("results")) {
						String results = (new Gson()).toJson(animeMap.get("results"), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
						animeListMap = new Gson().fromJson(results, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
						recyclerview3.setAdapter(new Recyclerview3Adapter(animeListMap));
					}
				}catch(NullPointerException e){
					e.printStackTrace();
					SweetToast.error(getContext().getApplicationContext(), "Something went wrong! Please try again");
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
		swiperefreshlayout1.setColorSchemeColors(Color.BLUE, Color.YELLOW, Color.RED);
		textview1.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/comforta_bold.ttf"), 1);
		textview2.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/comforta_bold.ttf"), 1);
		recyclerview1.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false));
		recyclerview2.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false));
		recyclerview3.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false));
		loader1.setVisibility(View.VISIBLE);
		loader2.setVisibility(View.VISIBLE);
		loader3.setVisibility(View.VISIBLE);
		swiperefreshlayout1.setRefreshing(false);
		if (SketchwareUtil.isConnected(getContext().getApplicationContext())) {
			requestData.startRequestNetwork(RequestNetworkController.GET, "https://api-amvstrm.nyt92.eu.org/api/v1/recentepisode/all", "a", _requestData_request_listener);
			requestTopAiring.startRequestNetwork(RequestNetworkController.GET, "https://api-amvstrm.nyt92.eu.org/api/v1/topair", "a", _requestTopAiring_request_listener);
			requestPopular.startRequestNetwork(RequestNetworkController.GET, "https://api-amvstrm.nyt92.eu.org/api/v1/popular/1", "a", _requestPopular_request_listener);
		}
		else {
			swiperefreshlayout1.setRefreshing(false);
			SweetToast.warning(getContext().getApplicationContext(), "Please check your internet connection and try again");
		}
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
	
	public class Recyclerview1Adapter extends RecyclerView.Adapter<Recyclerview1Adapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getActivity().getLayoutInflater();
			View _v = _inflater.inflate(R.layout.items, null);
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
			final RelativeLayout linear3 = _view.findViewById(R.id.linear3);
			final ImageView imageview1 = _view.findViewById(R.id.imageview1);
			final LinearLayout linear4 = _view.findViewById(R.id.linear4);
			final LinearLayout linear_click = _view.findViewById(R.id.linear_click);
			final LinearLayout linear6 = _view.findViewById(R.id.linear6);
			final TextView textview1 = _view.findViewById(R.id.textview1);
			final TextView textview2 = _view.findViewById(R.id.textview2);
			
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_view.setLayoutParams(_lp);
			cardview1.setCardBackgroundColor(0xFFFFFFFF);
			cardview1.setRadius((float)8);
			cardview1.setCardElevation((float)0);
			cardview1.setPreventCornerOverlap(false);
			textview1.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/comfortaa_medium.ttf"), 1);
			textview2.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/comfortaa_medium.ttf"), 1);
			textview2.setText("Ep ".concat(_data.get((int)_position).get("episode").toString()));
			textview1.setText(_data.get((int)_position).get("title").toString());
			
			androidx.swiperefreshlayout.widget.CircularProgressDrawable loader = new androidx.swiperefreshlayout.widget.CircularProgressDrawable(getContext().getApplicationContext());
			loader.setColorSchemeColors(Color.BLUE, Color.YELLOW, Color.RED);
			loader.setStrokeWidth(6f);
			loader.setCenterRadius(35f);
			loader.start();
			 Glide.with(getContext().getApplicationContext())
			.load(Uri.parse(_data.get((int)_position).get("image_url").toString()))
			.placeholder(loader)
			.error(R.drawable.nocover)
			.into(imageview1);
			linear_click.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					_click_effect(linear_click, "#212121");
					clickDelay = new TimerTask() {
						@Override
						public void run() {
							getActivity().runOnUiThread(new Runnable() {
								@Override
								public void run() {
									intent.setClass(getContext().getApplicationContext(), AnimePageActivity.class);
									intent.putExtra("path", "https://api-amvstrm.nyt92.eu.org/api/v1/info/".concat(_data.get((int)_position).get("id").toString()));
									intent.putExtra("episodePath", "https://api-amvstrm.nyt92.eu.org/api/v1/episode/".concat(_data.get((int)_position).get("id").toString()));
									intent.putExtra("type", _data.get((int)_position).get("subOrdub").toString());
									startActivity(intent);
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
	
	public class Recyclerview2Adapter extends RecyclerView.Adapter<Recyclerview2Adapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Recyclerview2Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getActivity().getLayoutInflater();
			View _v = _inflater.inflate(R.layout.items, null);
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
			final RelativeLayout linear3 = _view.findViewById(R.id.linear3);
			final ImageView imageview1 = _view.findViewById(R.id.imageview1);
			final LinearLayout linear4 = _view.findViewById(R.id.linear4);
			final LinearLayout linear_click = _view.findViewById(R.id.linear_click);
			final LinearLayout linear6 = _view.findViewById(R.id.linear6);
			final TextView textview1 = _view.findViewById(R.id.textview1);
			final TextView textview2 = _view.findViewById(R.id.textview2);
			
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_view.setLayoutParams(_lp);
			cardview1.setCardBackgroundColor(0xFFFFFFFF);
			cardview1.setRadius((float)8);
			cardview1.setCardElevation((float)0);
			cardview1.setPreventCornerOverlap(false);
			textview1.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/comfortaa_medium.ttf"), 1);
			textview2.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/comfortaa_medium.ttf"), 1);
			textview2.setVisibility(View.GONE);
			textview1.setText(_data.get((int)_position).get("title").toString());
			
			androidx.swiperefreshlayout.widget.CircularProgressDrawable loader = new androidx.swiperefreshlayout.widget.CircularProgressDrawable(getContext().getApplicationContext());
			loader.setColorSchemeColors(Color.BLUE, Color.YELLOW, Color.RED);
			loader.setStrokeWidth(6f);
			loader.setCenterRadius(35f);
			loader.start();
			 Glide.with(getContext().getApplicationContext())
			.load(Uri.parse(_data.get((int)_position).get("image_url").toString()))
			.placeholder(loader)
			.error(R.drawable.nocover)
			.into(imageview1);
			linear_click.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					_click_effect(linear_click, "#212121");
					clickDelay = new TimerTask() {
						@Override
						public void run() {
							getActivity().runOnUiThread(new Runnable() {
								@Override
								public void run() {
									intent.setClass(getContext().getApplicationContext(), AnimePageActivity.class);
									intent.putExtra("path", "https://api-amvstrm.nyt92.eu.org/api/v1/info/".concat(_data.get((int)_position).get("id").toString()));
									intent.putExtra("episodePath", "https://api-amvstrm.nyt92.eu.org/api/v1/episode/".concat(_data.get((int)_position).get("id").toString()));
									intent.putExtra("type", "SUB");
									startActivity(intent);
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
	
	public class Recyclerview3Adapter extends RecyclerView.Adapter<Recyclerview3Adapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Recyclerview3Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getActivity().getLayoutInflater();
			View _v = _inflater.inflate(R.layout.items, null);
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
			final RelativeLayout linear3 = _view.findViewById(R.id.linear3);
			final ImageView imageview1 = _view.findViewById(R.id.imageview1);
			final LinearLayout linear4 = _view.findViewById(R.id.linear4);
			final LinearLayout linear_click = _view.findViewById(R.id.linear_click);
			final LinearLayout linear6 = _view.findViewById(R.id.linear6);
			final TextView textview1 = _view.findViewById(R.id.textview1);
			final TextView textview2 = _view.findViewById(R.id.textview2);
			
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_view.setLayoutParams(_lp);
			cardview1.setCardBackgroundColor(0xFFFFFFFF);
			cardview1.setRadius((float)8);
			cardview1.setCardElevation((float)0);
			cardview1.setPreventCornerOverlap(false);
			textview1.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/comfortaa_medium.ttf"), 1);
			textview2.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/comfortaa_medium.ttf"), 1);
			textview2.setVisibility(View.GONE);
			textview1.setText(_data.get((int)_position).get("title").toString());
			
			androidx.swiperefreshlayout.widget.CircularProgressDrawable loader = new androidx.swiperefreshlayout.widget.CircularProgressDrawable(getContext().getApplicationContext());
			loader.setColorSchemeColors(Color.BLUE, Color.YELLOW, Color.RED);
			loader.setStrokeWidth(6f);
			loader.setCenterRadius(35f);
			loader.start();
			 Glide.with(getContext().getApplicationContext())
			.load(Uri.parse(_data.get((int)_position).get("image_url").toString()))
			.placeholder(loader)
			.error(R.drawable.nocover)
			.into(imageview1);
			linear_click.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					_click_effect(linear_click, "#212121");
					clickDelay = new TimerTask() {
						@Override
						public void run() {
							getActivity().runOnUiThread(new Runnable() {
								@Override
								public void run() {
									intent.setClass(getContext().getApplicationContext(), AnimePageActivity.class);
									intent.putExtra("path", "https://api-amvstrm.nyt92.eu.org/api/v1/info/".concat(_data.get((int)_position).get("id").toString()));
									intent.putExtra("episodePath", "https://api-amvstrm.nyt92.eu.org/api/v1/episode/".concat(_data.get((int)_position).get("id").toString()));
									intent.putExtra("type", "SUB");
									startActivity(intent);
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
}