package com.kzj.mall.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kzj.mall.R;
import com.yatoooon.screenadaptation.ScreenAdapterTools;


/**
 * Created by Chenwy on 2018/2/2.
 */

public class RootLayout extends LinearLayout {
    private View statusView;

    private TextView tvTitle;
    private ImageView ivLeft;
    private ImageView ivRight;
    private TextView tvRight;

    private Context mContext;
    private final View mTitleBarView;
    /**
     * 标题栏颜色
     */
    private int mTitleBarColor;

    /**
     * 标题文字
     */
    private String mTitleBarTitle;

    /**
     * 标题文字颜色
     */
    private int mTitleBarTitleColor;

    /**
     * 标题栏高度
     */
    private float mTitleBarHeight;

    /**
     * 标题栏左按钮
     */
    private int mTitleBarLeftIcon;
    private int mTitleBarLeftBackground;

    /**
     * 标题栏右按钮
     */
    private int mTitleBarRightIcon;

    /**
     * 标题栏右文本
     */
    private String mTitleBarRightText;

    /**
     * 标题栏右文本颜色
     */
    private int mTitleBarRightTextColor;

    /**
     * 是否显示右文本,默认不显示
     */
    private boolean isShowRightText;

    /**
     * 是否显示右图标,默认不显示
     */
    private boolean isShowRightIcon;

    /**
     * 是否显示左边图标，默认显示
     */
    private boolean isShowLeftIcon;


    public RootLayout(Context context) {
        this(context, null);
    }

    public RootLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RootLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.RootLayout);
        mTitleBarColor = t.getColor(R.styleable.RootLayout_titleBarColor, ContextCompat.getColor(context, R.color.colorPrimary));
        mTitleBarTitle = t.getString(R.styleable.RootLayout_titleBarTitle);
        mTitleBarTitleColor = t.getColor(R.styleable.RootLayout_titleBarTitleColor, ContextCompat.getColor(context, R.color.white));
        mTitleBarLeftBackground = t.getColor(R.styleable.RootLayout_titleBarLeftBackgroundColor, -1);
        mTitleBarHeight = t.getDimension(R.styleable.RootLayout_titleBarHeight, getResources().getDimension(R.dimen.titleBarHeight));
        mTitleBarLeftIcon = t.getResourceId(R.styleable.RootLayout_titleBarLeftIcon, R.mipmap.back2);
        mTitleBarRightIcon = t.getResourceId(R.styleable.RootLayout_titleBarRightIcon, 0);
        mTitleBarRightText = t.getString(R.styleable.RootLayout_titleBarRightText);
        mTitleBarRightTextColor = t.getColor(R.styleable.RootLayout_titleBarRightTextColor, ContextCompat.getColor(context, R.color.white));
        isShowRightText = t.getBoolean(R.styleable.RootLayout_isShowRightText, false);
        isShowRightIcon = t.getBoolean(R.styleable.RootLayout_isShowRightIcon, false);
        isShowLeftIcon = t.getBoolean(R.styleable.RootLayout_isShowLeftIcon, true);
        t.recycle();

        setClipToPadding(true);
        setOrientation(VERTICAL);
        setBackgroundColor(Color.parseColor("#FFF0F0F0"));

        ViewGroup.LayoutParams lpTitle = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                (int) mTitleBarHeight);
        mTitleBarView = LayoutInflater.from(context).inflate(R.layout.base_titlebar, null, false);
        if (!isInEditMode()) {
            ScreenAdapterTools.getInstance().loadView((ViewGroup) mTitleBarView);
        }
        mTitleBarView.setBackgroundColor(mTitleBarColor);
        addView(mTitleBarView, lpTitle);

        initViews();

        init();
    }

    private void initViews() {
        statusView = findViewById(R.id.status_view);
        tvTitle = findViewById(R.id.tv_title);
        ivLeft = findViewById(R.id.iv_left);
        ivRight = findViewById(R.id.iv_right);
        tvRight = findViewById(R.id.tv_right);
    }

    private void init() {
        tvTitle.setTextColor(mTitleBarTitleColor);

        if (!TextUtils.isEmpty(mTitleBarTitle)) {
            tvTitle.setText(mTitleBarTitle);
        }
        ivLeft.setVisibility(isShowLeftIcon ? VISIBLE : GONE);
        ivLeft.setImageResource(mTitleBarLeftIcon);
        setOnLeftOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) mContext).finish();
            }
        });

        if (mTitleBarRightIcon != 0) {
            ivRight.setVisibility(VISIBLE);
            ivRight.setImageResource(mTitleBarRightIcon);
        }

        if (!TextUtils.isEmpty(mTitleBarRightText)) {
            tvRight.setVisibility(VISIBLE);
            tvRight.setText(mTitleBarRightText);
        }

        if (mTitleBarLeftBackground != -1){
            ivLeft.setBackgroundColor(mTitleBarLeftBackground);
        }
    }

    public RootLayout setTitle(String title) {
        tvTitle.setText(title);
        return this;
    }

    public RootLayout setOnLeftOnClickListener(OnClickListener l) {
        if (ivLeft != null && ivLeft.getVisibility() == VISIBLE) {
            ivLeft.setOnClickListener(l);
        }
        return this;
    }

    public RootLayout setRightTextEnable(boolean enalbe) {
        tvRight.setEnabled(enalbe);
        if (!enalbe) {
            tvRight.setTextColor(Color.parseColor("#C2C6CC"));
        } else {
            tvRight.setTextColor(Color.parseColor("#2E3033"));
        }
        return this;
    }

    public RootLayout setRightIcon(int icon) {
        if (ivRight != null) {
            ivRight.setVisibility(VISIBLE);
            ivRight.setImageResource(icon);
        }
        return this;
    }

    public RootLayout setOnRightOnClickListener(OnClickListener l) {
        if (ivRight != null && ivRight.getVisibility() == VISIBLE) {
            ivRight.setOnClickListener(l);
            return this;
        }


        if (tvRight != null && tvRight.getVisibility() == VISIBLE) {
            tvRight.setOnClickListener(l);
        }
        return this;
    }

    public View getTitleBarView() {
        return mTitleBarView;
    }


    public RootLayout setStatusBarViewHeight(int height) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, height);
        statusView.setLayoutParams(params);
        int barHeight = (int) (mTitleBarHeight + height);
        ViewGroup.LayoutParams layoutParams = mTitleBarView.getLayoutParams();
        layoutParams.height = barHeight;
        mTitleBarView.requestLayout();
        return this;
    }

    public static RootLayout getInstance(Activity context) {
        return (RootLayout) ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);
    }
}
