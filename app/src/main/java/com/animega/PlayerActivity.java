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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.*;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.blogspot.atifsoftwares.animatoolib.*;
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

public class PlayerActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	
	private String qualitySelected = "";
	VideoView videoView;
	private HashMap<String, Object> map = new HashMap<>();
	private String currentQuality = "";
	private boolean videoIsCompleted = false;
	private double savedDuration = 0;
	private String downloadUri = "";
	
	private ArrayList<HashMap<String, Object>> streamListMap = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> qualityListMap = new ArrayList<>();
	
	private RelativeLayout linear1;
	private RelativeLayout linear_player;
	private LinearLayout linear_controllers;
	private LinearLayout toolbar;
	private LinearLayout linear4;
	private LinearLayout linear_seekbar;
	private LinearLayout linear_backbtn;
	private TextView textview1;
	private LinearLayout linear8;
	private ImageView imageview1;
	private Spinner spinner1;
	private LinearLayout linear6;
	private LinearLayout linear5;
	private LinearLayout linear7;
	private SpinKitView progressbar1;
	private LinearLayout rewindBtn;
	private LinearLayout playBtn;
	private LinearLayout forwardBtn;
	private ImageView rewindIcon;
	private ImageView playIcon;
	private ImageView forwardIcon;
	private Button button1;
	private TextView currentVideoTime;
	private SeekBar seekbar1;
	private TextView videoTimeDuration;
	private ImageView imageview2;
	
	private RequestNetwork requestServerVideo;
	private RequestNetwork.RequestListener _requestServerVideo_request_listener;
	private TimerTask durationTimer;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.player);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear1 = findViewById(R.id.linear1);
		linear_player = findViewById(R.id.linear_player);
		linear_controllers = findViewById(R.id.linear_controllers);
		toolbar = findViewById(R.id.toolbar);
		linear4 = findViewById(R.id.linear4);
		linear_seekbar = findViewById(R.id.linear_seekbar);
		linear_backbtn = findViewById(R.id.linear_backbtn);
		textview1 = findViewById(R.id.textview1);
		linear8 = findViewById(R.id.linear8);
		imageview1 = findViewById(R.id.imageview1);
		spinner1 = findViewById(R.id.spinner1);
		linear6 = findViewById(R.id.linear6);
		linear5 = findViewById(R.id.linear5);
		linear7 = findViewById(R.id.linear7);
		progressbar1 = findViewById(R.id.progressbar1);
		rewindBtn = findViewById(R.id.rewindBtn);
		playBtn = findViewById(R.id.playBtn);
		forwardBtn = findViewById(R.id.forwardBtn);
		rewindIcon = findViewById(R.id.rewindIcon);
		playIcon = findViewById(R.id.playIcon);
		forwardIcon = findViewById(R.id.forwardIcon);
		button1 = findViewById(R.id.button1);
		currentVideoTime = findViewById(R.id.currentVideoTime);
		seekbar1 = findViewById(R.id.seekbar1);
		videoTimeDuration = findViewById(R.id.videoTimeDuration);
		imageview2 = findViewById(R.id.imageview2);
		requestServerVideo = new RequestNetwork(this);
		
		linear_player.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				linear_controllers.setVisibility(View.VISIBLE);
			}
		});
		
		linear_controllers.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				linear_controllers.setVisibility(View.GONE);
			}
		});
		
		linear_backbtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				finish();
				Animatoo.animateSwipeLeft(PlayerActivity.this);
			}
		});
		
		spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				if (_position == 0) {
					qualitySelected = "Auto";
					progressbar1.setVisibility(View.VISIBLE);
					linear7.setVisibility(View.GONE);
					if (SketchwareUtil.isConnected(getApplicationContext())) {
						requestServerVideo.startRequestNetwork(RequestNetworkController.GET, getIntent().getStringExtra("path"), "a", _requestServerVideo_request_listener);
					}
					else {
						SweetToast.error(getApplicationContext(), "Please check your internet connection and try again");
						finish();
					}
				}
				if (_position == 1) {
					qualitySelected = "Backup";
					progressbar1.setVisibility(View.VISIBLE);
					linear7.setVisibility(View.GONE);
					if (SketchwareUtil.isConnected(getApplicationContext())) {
						requestServerVideo.startRequestNetwork(RequestNetworkController.GET, getIntent().getStringExtra("path"), "a", _requestServerVideo_request_listener);
					}
					else {
						SweetToast.error(getApplicationContext(), "Please check your internet connection and try again");
						finish();
					}
				}
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> _param1) {
				
			}
		});
		
		rewindBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_click_effect(rewindBtn, "#212121");
				videoView.seekTo((int) videoView.getCurrentPosition() - 10000);
			}
		});
		
		playBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_click_effect(playBtn, "#212121");
				if (videoView.isPlaying()) {
					playIcon.setImageResource(R.drawable.control_4);
					videoView.pause();
				}
				else {
					playIcon.setImageResource(R.drawable.control_3);
					videoView.start();
				}
			}
		});
		
		forwardBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_click_effect(forwardBtn, "#212121");
				videoView.seekTo((int) videoView.getCurrentPosition() + 10000);
			}
		});
		
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				videoView.seekTo((int) videoView.getCurrentPosition() + 85000);
			}
		});
		
		seekbar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar _param1, int _param2, boolean _param3) {
				final int _progressValue = _param2;
				if(_param3){
					videoView.seekTo((int) _progressValue);
					_SavedCurrentDuration(getIntent().getStringExtra("animeID"), String.valueOf((long)(_progressValue)));
				}
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar _param1) {
				onProgressChanged(_param1,_param1.getProgress(),true);
			}
			
			@Override
			public void onStopTrackingTouch(SeekBar _param2) {
				
			}
		});
		
		imageview2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_openChrome(downloadUri);
			}
		});
		
		_requestServerVideo_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				map = new Gson().fromJson(_response, new TypeToken<HashMap<String, Object>>(){}.getType());
				try {
					textview1.setText(getIntent().getStringExtra("episodeTitle"));
					if (map.containsKey("sources")) {
						String sources = (new Gson()).toJson(map.get("sources"), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
						streamListMap = new Gson().fromJson(sources, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
						if (qualitySelected.equals("Auto")) {
							videoView.setVideoURI(Uri.parse(streamListMap.get((int)0).get("url").toString()));
						}
						else {
							if (qualitySelected.equals("Backup")) {
								videoView.setVideoURI(Uri.parse(streamListMap.get((int)1).get("url").toString()));
							}
						}
						linear7.setVisibility(View.GONE);
						progressbar1.setVisibility(View.GONE);
						SharedPreferences sh = getSharedPreferences("CurrentDuration", MODE_APPEND); 
						if (sh.getAll().containsKey(getIntent().getStringExtra("animeID"))) {
							int savedDuration = (int) sh.getInt(getIntent().getStringExtra("animeID"), 0);
							videoView.seekTo(savedDuration);
							SweetToast.defaultLong(getApplicationContext(), "Auto skip last sesion!");
						}
					}
				} catch (Exception e) {
					SweetToast.defaultLong(getApplicationContext(), "Something went wrong, please contact the developer");
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
		videoIsCompleted = false;
		textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/comforta_bold.ttf"), 1);
		button1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/comforta_bold.ttf"), 1);
		currentVideoTime.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/comfortaa_medium.ttf"), 0);
		videoTimeDuration.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/comfortaa_medium.ttf"), 0);
		button1.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)50, 0xFFFF5722));
		linear_backbtn.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)100, 0xCC212121));
		rewindBtn.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)100, Color.TRANSPARENT));
		playBtn.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)100, Color.TRANSPARENT));
		forwardBtn.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)100, Color.TRANSPARENT));
		linear7.setVisibility(View.GONE);
		progressbar1.setVisibility(View.VISIBLE);
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("quality", "Auto");
			qualityListMap.add(_item);
		}
		
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("quality", "Backup");
			qualityListMap.add(_item);
		}
		
		spinner1.setAdapter(new Spinner1Adapter(qualityListMap));
		spinner1.setSelection((int)(0));
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
		
		videoView = new VideoView(PlayerActivity.this);
		videoView.setLayoutParams(params);
		
		linear_player.addView(videoView);
		MediaController ctrl = new MediaController(PlayerActivity.this);
		ctrl.setVisibility(View.GONE);
		videoView.setMediaController(ctrl);
		videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
						@Override
						public void onPrepared(MediaPlayer _mediaPlayer) {
								linear7.setVisibility(View.VISIBLE);
								progressbar1.setVisibility(View.GONE);
								int duration = videoView.getDuration() / 1000;
								int hours = duration / 3600;
								int minutes = (duration / 60) - (hours * 60);
								int seconds = duration - (hours * 3600) - (minutes * 60);
								videoTimeDuration.setText(hours+":"+minutes+":"+seconds);
								seekbar1.setMax((int)videoView.getDuration());
								durationTimer = new TimerTask() {
										@Override
										public void run() {
												runOnUiThread(new Runnable() {
														@Override
														public void run() {
																int curr_duration = videoView.getCurrentPosition() / 1000;
																int curr_hour = curr_duration / 3600;
																int curr_minute = (curr_duration / 60) - (curr_hour * 60);
																int curr_second = curr_duration - (curr_hour * 3600) - (curr_minute * 60);
																currentVideoTime.setText(curr_hour+":"+curr_minute+":"+curr_second);
																seekbar1.setProgress((int)videoView.getCurrentPosition());
																savedDuration = seekbar1.getProgress();
														}
												});
										}
								};
								_timer.scheduleAtFixedRate(durationTimer, (int)(1000), (int)(1000));
								_mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
										     @Override
										      public boolean onInfo(MediaPlayer _mediaPlayer, int what, int extra) {
												           if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START){
														                   linear_controllers.setVisibility(View.VISIBLE);
														                   linear7.setVisibility(View.GONE);
														                   progressbar1.setVisibility(View.VISIBLE);
														           }else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END){
														                   linear_controllers.setVisibility(View.GONE);
														                   linear7.setVisibility(View.VISIBLE);
														                   progressbar1.setVisibility(View.GONE);
														           }
												           return false;
												      }
								});
						}
				});
				
				videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
						@Override
						public boolean onError(MediaPlayer _mediaPlayer, int _what, int _extra) {
								//playerContainer.setVisibility(View.GONE);
								
				            linear_controllers.setVisibility(View.VISIBLE);
							   
				           return true;
						}
				});
		videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
						@Override
						public void onCompletion(MediaPlayer _mediaPlayer) {
								videoIsCompleted = true;
								durationTimer.cancel();
								playIcon.setImageResource(R.drawable.control_4);
								progressbar1.setVisibility(View.GONE);
								linear7.setVisibility(View.VISIBLE);
						}
				});
	}
	
	@Override
	public void onBackPressed() {
		_SavedCurrentDuration(getIntent().getStringExtra("animeID"), String.valueOf((long)(savedDuration)));
		finish();
		Animatoo.animateSwipeLeft(PlayerActivity.this);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		videoView.resume();
	}
	
	@Override
	public void onPause() {
		super.onPause();
		videoView.pause();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		_SavedCurrentDuration(getIntent().getStringExtra("animeID"), String.valueOf((long)(savedDuration)));
		videoView.stopPlayback();
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
	
	
	public void _SavedCurrentDuration(final String _key, final String _value) {
		SharedPreferences sharedPreferences = getSharedPreferences("CurrentDuration",MODE_PRIVATE);
		SharedPreferences.Editor myEdit = sharedPreferences.edit();
		myEdit.putInt(_key, Integer.parseInt(_value));
		myEdit.commit();
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
	
	public class Spinner1Adapter extends BaseAdapter {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Spinner1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public int getCount() {
			return _data.size();
		}
		
		@Override
		public HashMap<String, Object> getItem(int _index) {
			return _data.get(_index);
		}
		
		@Override
		public long getItemId(int _index) {
			return _index;
		}
		
		@Override
		public View getView(final int _position, View _v, ViewGroup _container) {
			LayoutInflater _inflater = getLayoutInflater();
			View _view = _v;
			if (_view == null) {
				_view = _inflater.inflate(R.layout.quality, null);
			}
			
			final TextView textview1 = _view.findViewById(R.id.textview1);
			
			textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/comforta_bold.ttf"), 1);
			textview1.setText(_data.get((int)_position).get("quality").toString());
			
			return _view;
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