package tc.airl2.watch;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageItemInfo;
import android.content.pm.ResolveInfo;
import android.media.AudioManager;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import tc.airl2.settings;
import tc.airl2.u;
import tc.airl2.watch.DateUtils;
import tc.airl2.watch.R;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextClock;
import android.graphics.Color;

public class home extends Activity implements OnClickListener,OnLongClickListener,Comparator<a> {

    @Override
    public boolean onLongClick(final View view) {
        final String pkgName=a.get(view.getId()).p;
        final String name=a.get(view.getId()).t.toString();
        PopupMenu popup = new PopupMenu(self, view);
        popup.getMenuInflater().inflate(R.menu.app_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.info:
                            if (VERSION.SDK_INT >= 9) {
                                startActivity(new Intent("android.settings.APPLICATION_DETAILS_SETTINGS").setData(Uri.fromParts("package", a.get(view.getId()).p, (String) null)).addFlags(268435456));
                            }
                            break;
                        case R.id.launch:
                            try {
                                startActivity(new Intent().setFlags(268435456).setComponent(new ComponentName(pkgName, a.get(view.getId()).c)));
                            } catch (Exception e) {
                                recreate();
                            }
                            break;
                        case R.id.hidden:
                            AlertDialog dialog = new AlertDialog.Builder(self)
                                .setTitle("是否隐藏" + name)
                                .setMessage("这将隐藏" + pkgName + "以及其所有的快捷方式")
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dia, int which) {
                                        u.setHidden(pkgName, true);
                                        if (sdk(11))recreate();
                                    }
                                })
                                .setNegativeButton(android.R.string.cancel, null)
                                .create();
                            dialog.show();
                            break;
                        case R.id.unins:
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_DELETE);
                            intent.setData(Uri.fromParts("package", a.get(view.getId()).p, null));
                            startActivity(intent);
                            break;
                        case R.id.settings:
                            Intent intent1=new Intent();
                            intent1.setClass(self, settings.class);
                            self.startActivity(intent1);
                            break;
                    }
                    return true;
                }
            });
        popup.show();
        return true;
    }

    @Override
    public int compare(a p1, a p2) {
        return 0;
    }


    @Override
    public void onClick(View view) {
        try {
            startActivity(new Intent().setFlags(268435456).setComponent(new ComponentName(((a) this.a.get(view.getId())).p, ((a) this.a.get(view.getId())).c)));
        } catch (Exception e) {
            recreate();
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        this.ir = new r(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addDataScheme("package");
        registerReceiver(this.ir, intentFilter);
    }


    public static final String TAG = "home";

    private ViewPager viewPager;
    private ArrayList<View> pageview;

    List<a> a = new ArrayList<>();
    int h;
    r ir;
    b ll;
    int p;
    List<ResolveInfo> r;
    ScrollView s;
    int w;
    int col = 2;
    private Intent intent;
    private static Activity self;
    private static OnClickListener selfoc;
    private static OnLongClickListener selfolc;
    private static Comparator<a> selfc;
    private static ImageView bg;
    LinearLayout page2;

    boolean canSlidePanel=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActionBar() != null) getActionBar().hide();
        u.setContext(this);
        intent = getIntent();
        if (sdk(21)) {
            getWindow().addFlags(Integer.MIN_VALUE);
            getWindow().setNavigationBarColor(0);
            getWindow().setStatusBarColor(0);
        } else if (sdk(19)) {
            getWindow().addFlags(201326592);
        }
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        w = displayMetrics.widthPixels;
        h = displayMetrics.heightPixels;
        self = this;
        selfoc = this;
        selfolc = this;
        selfc = this;
        loadHomePage();
    }
    public void loadHomePage() {
        Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    //查找布局文件用LayoutInflater.inflate
                    LayoutInflater inflater =getLayoutInflater();
                    final LinearLayout pagePanel=(LinearLayout)inflater.inflate(R.layout.page_panel, null);

                    bg = new ImageView(self);
                    bg.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                    bg.setImageResource(R.drawable.wallpaper_3);
                    bg.setScaleType(ScaleType.CENTER_CROP);
                    runUi(new Runnable(){@Override public void run() { addContentView(bg, new WindowManager.LayoutParams()); }});
                    viewPager = new ViewPager(self);
                    viewPager.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                    viewPager.setOffscreenPageLimit(0);
                    runUi(new Runnable(){@Override public void run() { addContentView(viewPager, new WindowManager.LayoutParams()); }});
                    View view1 = inflater.inflate(R.layout.page_home, null);

                    loadAppList();

                    pageview = new ArrayList<View>();
                    page2 = new LinearLayout(self);
                    page2.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                    pageview.add(view1);
                    pageview.add(page2);
                    //数据适配器
                    PagerAdapter mPagerAdapter = new PagerAdapter(){

                        @Override
                        //获取当前窗体界面数
                        public int getCount() {
                            // TODO Auto-generated method stub
                            return pageview.size();
                        }

                        @Override
                        public boolean isViewFromObject(View arg0, Object arg1) {
                            // TODO Auto-generated method stub
                            return arg0 == arg1;
                        }
                        public void destroyItem(View arg0, int arg1, Object arg2) {
                            ((ViewPager) arg0).removeView(pageview.get(arg1));
                        }
                        public Object instantiateItem(final View arg0, final int arg1) {
                            runUi(new Runnable(){@Override public void run() {((ViewPager)arg0).addView(pageview.get(arg1));}});
                            return pageview.get(arg1);
                        }
                    };
                    viewPager.setAdapter(mPagerAdapter);
                    viewPager.setCurrentItem(0);

                    final TextView homeTime=view1.findViewById(R.id.homeTime);
                    final TextView homeDate=view1.findViewById(R.id.homeDate);
                    final LinearLayout homePowerLayout=view1.findViewById(R.id.homePowerLayout);

                    homeTime.setTextSize(u.px2dp(self, w / (float)3.5));
                    homeDate.setTextSize(u.px2dp(self, w / 4 / 4));
                    ViewGroup.LayoutParams homePowerLayoutParams=homePowerLayout.getLayoutParams();
                    homePowerLayoutParams.width = (int)(w * 0.6);
                    homePowerLayout.setLayoutParams(homePowerLayoutParams);

                    final TextView homePowerText=view1.findViewById(R.id.homePowerText);
                    final ProgressBar homePowerProgress=view1.findViewById(R.id.homePowerProgress);

                    if (!sdk(21)) {
                        homePowerText.setVisibility(View.GONE);
                        homePowerProgress.setVisibility(View.GONE);
                    }

                    final Runnable reloadDate = new Runnable(){

                        @Override
                        public void run() {
                            runOnUiThread(new Runnable(){
                                    @Override
                                    public void run() {
                                        homeTime.setText(DateUtils.getCurrentTime());
                                        homeDate.setText(DateUtils.getCurrentDateString() + " ");
                                        if (sdk(21)) {
                                            BatteryManager manager = (BatteryManager) getSystemService(BATTERY_SERVICE);
                                            String charging="";
                                            int bs=manager.getIntProperty(BatteryManager.BATTERY_PROPERTY_STATUS);
                                            if (bs == BatteryManager.BATTERY_STATUS_CHARGING || bs == BatteryManager.BATTERY_STATUS_FULL) charging = " +";
                                            homePowerText.setText(manager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY) + "%" + charging);
                                            homePowerProgress.setProgress(manager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY));
                                        }
                                    }
                                });

                            new Handler(Looper.getMainLooper()).postDelayed(this, 750);
                        }
                    };
                    new Handler(Looper.getMainLooper()).postDelayed(reloadDate, 0);

                    final LinearLayout homeTopLayout=view1.findViewById(R.id.homeTopLayout);
                    viewPager.setOnTouchListener(new OnTouchListener(){
                            private float lastX, lastY;
                            @Override
                            public boolean onTouch(View p1, MotionEvent event) {
                                if (canSlidePanel) {
                                    switch (event.getAction()) {
                                        case MotionEvent.ACTION_DOWN: // 手指按下
                                            lastX = event.getX();
                                            lastY = event.getY();
                                            return false; // 返回true表示此事件被消费
                                        case MotionEvent.ACTION_MOVE: // 手指滑动
                                            float deltaX = event.getX() - lastX;
                                            float deltaY = event.getY() - lastY;
                                            if (Math.abs(deltaX) < Math.abs(deltaY) && deltaY > h / 10) {
                                                pagePanel.setLayoutParams(new FrameLayout.LayoutParams(-1, (int)event.getY()));
                                                pagePanel.setVisibility(View.VISIBLE);
                                                if (event.getY() >= h / 2) {
                                                    canSlidePanel = false;
                                                    ValueAnimator valueAnimator = ValueAnimator.ofInt(pagePanel.getHeight(), h);
                                                    valueAnimator.setDuration(300); // 设置动画持续时间，单位是毫秒
                                                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                                            @Override
                                                            public void onAnimationUpdate(ValueAnimator animation) {
                                                                // 获取动画的当前值
                                                                int animatedValue = (int) animation.getAnimatedValue();
                                                                // 设置View的新高度
                                                                ViewGroup.LayoutParams layoutParams = pagePanel.getLayoutParams();
                                                                layoutParams.height = animatedValue;
                                                                pagePanel.setLayoutParams(layoutParams);
                                                            }
                                                        });
                                                    valueAnimator.start(); // 开始动画
                                                    initVolume(pagePanel);
                                                }
                                                return true;
                                            }
                                            break;
                                        case event.ACTION_UP:
                                            if (canSlidePanel) {
                                                ValueAnimator valueAnimator = ValueAnimator.ofInt(pagePanel.getHeight(), 0);
                                                valueAnimator.setDuration(200);
                                                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                                        @Override
                                                        public void onAnimationUpdate(ValueAnimator animation) {
                                                            // 获取动画的当前值
                                                            int animatedValue = animation.getAnimatedValue();
                                                            // 设置View的新高度
                                                            ViewGroup.LayoutParams layoutParams = pagePanel.getLayoutParams();
                                                            layoutParams.height = animatedValue;
                                                            pagePanel.setLayoutParams(layoutParams);
                                                        }
                                                    });
                                                valueAnimator.start();
                                            }
                                    }
                                }
                                return false;
                            }


                        });

                    OnTouchListener cantSlidePanel=new OnTouchListener(){
                        @Override
                        public boolean onTouch(View p1, MotionEvent p2) {
                            canSlidePanel = false;
                            return false;
                        }
                    };

                    view1.findViewById(R.id.homeCenterLayout).setOnTouchListener(cantSlidePanel);
                    homePowerLayout.setOnTouchListener(cantSlidePanel);

                    homeTopLayout.setOnTouchListener(new OnTouchListener(){
                            @Override
                            public boolean onTouch(View p1, MotionEvent p2) {
                                canSlidePanel = true;
                                return false;
                            }
                        });
                    pagePanel.setVisibility(View.GONE);
                    runUi(new Runnable(){@Override public void run() { addContentView(pagePanel, new WindowManager.LayoutParams()); }});

                    pagePanel.setOnTouchListener(new OnTouchListener(){
                            private float lastX, lastY;
                            @Override
                            public boolean onTouch(View p1, MotionEvent event) {

                                switch (event.getAction()) {
                                    case MotionEvent.ACTION_DOWN: // 手指按下
                                        lastX = event.getX();
                                        lastY = event.getY();
                                        return false;
                                    case MotionEvent.ACTION_MOVE: // 手指滑动
                                        float deltaX = event.getX() - lastX;
                                        float deltaY = event.getY() - lastY;
                                        if (Math.abs(deltaX) < Math.abs(deltaY) && deltaY < 0 - h / 12) {
                                            pagePanel.setLayoutParams(new FrameLayout.LayoutParams(-1, (int)event.getY()));
                                            if (event.getY() <= h / 5 * 2) {
                                                canSlidePanel = true;
                                                ValueAnimator valueAnimator = ValueAnimator.ofInt(pagePanel.getHeight(), 0);
                                                valueAnimator.setDuration(275); // 设置动画持续时间，单位是毫秒
                                                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                                        @Override
                                                        public void onAnimationUpdate(ValueAnimator animation) {
                                                            // 获取动画的当前值
                                                            int animatedValue = animation.getAnimatedValue();
                                                            // 设置View的新高度
                                                            ViewGroup.LayoutParams layoutParams = pagePanel.getLayoutParams();
                                                            layoutParams.height = animatedValue;
                                                            pagePanel.setLayoutParams(layoutParams);
                                                        }
                                                    });
                                                valueAnimator.start();
                                            }
                                        }
                                        break;
                                    case event.ACTION_UP:
                                        if (!canSlidePanel) {
                                            ValueAnimator valueAnimator = ValueAnimator.ofInt(pagePanel.getHeight(), h);
                                            valueAnimator.setDuration(200);
                                            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                                    @Override
                                                    public void onAnimationUpdate(ValueAnimator animation) {
                                                        // 获取动画的当前值
                                                        int animatedValue = animation.getAnimatedValue();
                                                        // 设置View的新高度
                                                        ViewGroup.LayoutParams layoutParams = pagePanel.getLayoutParams();
                                                        layoutParams.height = animatedValue;
                                                        pagePanel.setLayoutParams(layoutParams);
                                                    }
                                                });
                                            valueAnimator.start();
                                        }
                                }
                                return false;
                            }
                        });
                    initPanel(pagePanel);
                }
            });
        thread.start();
    }
    public void initPanel(LinearLayout panel) {
        final LinearLayout statusBar = panel.findViewById(R.id.panelStatusBar);

        runUi(new Runnable(){@Override public void run() {
                    final TextClock clock=new TextClock(self);
                    clock.setTextColor(Color.WHITE);
                    clock.setLayoutParams(new LayoutParams(-2, -1));
                    statusBar.addView(clock);
                }});

        initVolume(panel);
    }
    public void initVolume(LinearLayout panel) {
        SeekBar volumeBar = panel.findViewById(R.id.panelVolumeBar);
        final TextView volumeText2 = panel.findViewById(R.id.panelVolumeText2);

        final AudioManager mAudioManager = (AudioManager) self.getSystemService(Service.AUDIO_SERVICE);
        final int maxMusicVolume=mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        final int nowMusicVolume=mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        volumeBar.setMax(maxMusicVolume);
        volumeBar.setProgress(nowMusicVolume);
        volumeText2.setText((Math.round((float)nowMusicVolume / (float)maxMusicVolume * 100)) + "%");

        volumeBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

                @Override
                public void onProgressChanged(SeekBar p1, int p2, boolean p3) {
                    mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                                                  p2,
                                                  AudioManager.FLAG_PLAY_SOUND
                                                  | AudioManager.FLAG_SHOW_UI);
                    volumeText2.setText((Math.round((float)p2 / (float)maxMusicVolume * 100)) + "%");
                }

                @Override
                public void onStartTrackingTouch(SeekBar p1) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar p1) {
                }
            });
    }
    public void loadAppList() {
        Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    int i;
                    p = 0;
                    r = getPackageManager().queryIntentActivities(new Intent("android.intent.action.MAIN").addCategory("android.intent.category.LAUNCHER"), 0);
                    for (i = 0; i < r.size(); i++) {
                        PackageItemInfo packageItemInfo =  r.get(i).activityInfo;
                        if (!packageItemInfo.packageName.equals(getPackageName()) && !u.isHidden(packageItemInfo.packageName)) {
                            a.add(new a(packageItemInfo.loadLabel(self.getPackageManager()), packageItemInfo.loadIcon(self.getPackageManager()), packageItemInfo.packageName, packageItemInfo.name));
                        }
                    }
                    Collections.sort(a, selfc);
                    ll = new b(self.getApplicationContext());
                    ll.set(col, p, p);
                    ll.setLayoutParams(new LayoutParams(-1, -1));

                    int iconSize = 72;
                    if (u.getInt("iconSize", 0) == 0) {
                        if (w <= 320) {
                            iconSize = 72;
                        } else if (w <= 480) {
                            iconSize = 96;
                        } else if (w <= 560) {
                            iconSize = 114;
                        } else {
                            iconSize = 144;
                        }
                    } else {
                        iconSize = u.getInt("iconSize", 1);
                    }

                    for (i = 0; i < a.size(); i++) {
                        LinearLayout linearLayout = new LinearLayout(getApplication());
                        linearLayout.setPadding(0, 18, 0, 18);
                        linearLayout.setOrientation(1);
                        linearLayout.setGravity(17);
                        linearLayout.setId(i);
                        linearLayout.setOnClickListener(selfoc);
                        linearLayout.setOnLongClickListener(selfolc);
                        View imageView = new ImageView(getApplication());
                        imageView.setPadding(p, p, p, p);
                        ((ImageView) imageView).setImageDrawable(a.get(i).i);
                        ((ImageView) imageView).setLayoutParams(new LayoutParams(
                                                                    iconSize,
                                                                    iconSize
                                                                ));
                        linearLayout.addView(imageView);
                        imageView = new LinearLayout(self);
                        ((LinearLayout)imageView).setGravity(17);
                        HorizontalScrollView horizontalScrollView = new HorizontalScrollView(self);
                        horizontalScrollView.setHorizontalScrollBarEnabled(false);
                        TextView textView = new TextView(getApplication());
                        textView.setShadowLayer((float) 2, 0.5f, 0.5f, -16777216);
                        textView.setTextColor(-1);
                        textView.setText(a.get(i).t);
                        textView.setTextSize(13);
                        horizontalScrollView.addView(textView);
                        ((LinearLayout)imageView).addView(horizontalScrollView);
                        linearLayout.addView(imageView);
                        ll.addView(linearLayout);
                    }
                    s = new ScrollView(self);
                    s.addView(ll);
                    s.setFitsSystemWindows(true);

                    self.runOnUiThread(new Runnable(){

                            @Override
                            public void run() {
                                page2.addView(s);
                            }


                        });
                }
            });
        thread.start();
    }
    public boolean sdk(int sdk) {
        if (sdk + 1 <= VERSION.SDK_INT + 1) return true;
        return false;
    }
    public void runUi(Runnable c) {
        self.runOnUiThread(c);
    }
    class r extends BroadcastReceiver {
        private final home this$0;

        public r(home lVar) {
            this.this$0 = lVar;
        }

        home access$0(r rVar) {
            return rVar.this$0;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("android.intent.action.PACKAGE_ADDED")) {
                self.recreate();
            } else if (action.equals("android.intent.action.PACKAGE_REMOVED")) {
                self.recreate();
            }
        }
    }
    public void nothing(View v) {}
}
