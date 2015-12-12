package com.zczczy.leo.shengjingspecialcar.viewgroup;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.TintManager;
import android.support.v7.widget.TintTypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zczczy.leo.shengjingspecialcar.R;


/**
 * Created by leo on 2015/10/23.
 */
public class MyTitleBar extends RelativeLayout implements Parcelable {

    private static final String TAG = "MyTitleBar";

    private TextView mTitleTextView;

    private CharSequence mTitleText, mLeftText, mRightText, mSearchHintText;

    private TextView mLeftTextView, mRightTextView;

    private ImageButton mNavButtonView;

    private ImageButton mRightButtonView;

    private ImageView logoView;

    private EditText mSearchView;

    private int mTitleTextColor, mLeftTextColor, mRightTextColor, mSearchHintTextColor;

    private float mTitleSize, mLeftTextSize, mRightTextSize;

    private int textSize = 15;

    private TintManager mTintManager;

    private Drawable searchDrawable;

    private int mRightTextMarginRight;

    private boolean mLogoShape;

    private int mBorderColor = Color.BLACK;

    private int mBorderWidth = 0;

    private OnNavigationClickListener onNavigationClickListener;

    private OnRightClickListener onRightClickListener;

    private Context context;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }


    public static final Creator<MyTitleBar> CREATOR = new Creator<MyTitleBar>() {
        public MyTitleBar createFromParcel(Parcel in) {
            return null;
        }

        @Override
        public MyTitleBar[] newArray(int size) {
            return new MyTitleBar[0];
        }
    };


    public interface OnNavigationClickListener {

        void onNavClick(View v);

    }

    public interface OnRightClickListener {

        void onNavClick(View v);

    }


    public MyTitleBar(Context context) {
        this(context, null);
    }

    public MyTitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;

        final TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs, R.styleable.MyTitleBar, defStyleAttr, 0);

        mRightTextMarginRight=a.getDimensionPixelSize(R.styleable.MyTitleBar_mRightTextMarginRight, 20);


        final Drawable rightButtonView = a.getDrawable(R.styleable.MyTitleBar_mRightButtonIcon);
        if (rightButtonView != null) {
            setRightButtonIcon(rightButtonView);
        }


        final CharSequence mRightText = a.getText(R.styleable.MyTitleBar_mRightText);
        if (!TextUtils.isEmpty(mRightText)) {
            setRightText(mRightText);
        }

        final Drawable rightTextDrawable = a.getDrawable(R.styleable.MyTitleBar_mRightTextDrawable);

        if(rightTextDrawable!=null){

            rightTextDrawable.setBounds(0,0,rightTextDrawable.getMinimumWidth(),rightTextDrawable.getMinimumHeight());

            mRightTextView.setCompoundDrawables(null,null,rightTextDrawable,null);
        }


        if (a.hasValue(R.styleable.MyTitleBar_mRightTextColor)) {
            setRightTextColor(a.getColor(R.styleable.MyTitleBar_mRightTextColor, 0xffffffff));

        }

        if (a.hasValue(R.styleable.MyTitleBar_mRightTextSize)) {
            setRightTextSize(a.getDimensionPixelSize(R.styleable.MyTitleBar_mRightTextSize, textSize));
        }

        final CharSequence title = a.getText(R.styleable.MyTitleBar_mTitle);
        if (!TextUtils.isEmpty(title)) {
            setTitle(title);
        }
        if (a.hasValue(R.styleable.MyTitleBar_mTitleTextColor)) {
            setTitleTextColor(a.getColor(R.styleable.MyTitleBar_mTitleTextColor, 0xffffffff));
        }
        if (a.hasValue(R.styleable.MyTitleBar_mTitleSize)) {
            setTitleSize(a.getDimensionPixelSize(R.styleable.MyTitleBar_mTitleSize, 20));
        }

        final CharSequence searchHintText = a.getText(R.styleable.MyTitleBar_mSearchHintText);
        if (!TextUtils.isEmpty(searchHintText)) {
            setSearchText(searchHintText);
        }

        final Drawable searchDrawableLeft = a.getDrawable(R.styleable.MyTitleBar_mSearchViewDrwableLeft);
        if (searchDrawableLeft != null) {
            setSearchDrawableLeft(searchDrawableLeft);
        }

        final Drawable searchDrawableRight = a.getDrawable(R.styleable.MyTitleBar_mSearchViewDrwableRight);
        if (searchDrawableRight != null) {
            setSearchDrawableRight(searchDrawableRight);
        }

        if (a.hasValue(R.styleable.MyTitleBar_mSearchHintTextColor)) {
            setSearchHintTextColor(a.getColor(R.styleable.MyTitleBar_mSearchHintTextColor, 0xffffffff));
        }

        final Drawable navIcon = a.getDrawable(R.styleable.MyTitleBar_mNavButtonIcon);
        if (navIcon != null) {
            setNavButtonIcon(navIcon);
        }

        final CharSequence mLeftText = a.getText(R.styleable.MyTitleBar_mLeftText);
        if (!TextUtils.isEmpty(mLeftText)) {
            setLeftText(mLeftText);
        }
        if (a.hasValue(R.styleable.MyTitleBar_mLeftTextColor)) {
            setLeftTextColor(a.getColor(R.styleable.MyTitleBar_mLeftTextColor, 0xffffffff));
        }

        if (a.hasValue(R.styleable.MyTitleBar_mLeftTextSize)) {
            setLeftTextSize(a.getDimensionPixelSize(R.styleable.MyTitleBar_mLeftTextSize, textSize));
        }

        final Drawable logoDrawable = a.getDrawable(R.styleable.MyTitleBar_mLogoIcon);
        if (logoDrawable != null) {
            setLogoIcon(logoDrawable);
        }

//        if(a.hasValue(R.styleable.MyTitleBar_mCircleLogo)){
//            mLogoShape = a.getBoolean(R.styleable.MyTitleBar_mCircleLogo,false);
//        }
//
//        if(a.hasValue(R.styleable.MyTitleBar_border_width)){
//            mBorderWidth = a.getDimensionPixelSize(R.styleable.MyTitleBar_border_width, 0);
//        }
//
//        if(a.hasValue(R.styleable.MyTitleBar_border_color)){
//            mBorderColor = a.getColor(R.styleable.MyTitleBar_border_color, Color.BLACK);
//        }

        a.recycle();

        mTintManager = a.getTintManager();
    }

    /**
     * set Navigation  ClickListener
     *
     * @param listener
     */
    public void setNavigationOnClickListener(OnClickListener listener) {
//        ensureNavButtonView();
        if (mNavButtonView!=null)
            mNavButtonView.setOnClickListener(listener);
    }

    /**
     * set RightButton  ClickListener
     *
     * @param listener
     */
    public void setRightButtonOnClickListener(OnClickListener listener) {
        //ensureRightButtonView();
        if(mRightButtonView!=null)
            mRightButtonView.setOnClickListener(listener);

    }

    /**
     * set Search ClickListener
     * @param listener
     */
    public void setSearchViewOnClickListener(OnClickListener listener) {
        if (mSearchView != null)
            mSearchView.setOnClickListener(listener);

    }

    /**
     * set Right ClickListener
     * @param listener
     */
    public void setRightTextOnClickListener(OnClickListener listener){
        if(mRightTextView!=null)
            mRightTextView.setOnClickListener(listener);

    }

    public void setLeftTextOnClickListener(OnClickListener listener){
        if(mLeftTextView!=null)
            mLeftTextView.setOnClickListener(listener);
    }

    public void setLogoOnClickListener(OnClickListener listener){
        if(logoView!=null)
            logoView.setOnClickListener(listener);

    }

    public void setSearchDrawableRight(Drawable icon) {
        searchDrawable = icon;
        if (mSearchView != null) {
            mSearchView.setCompoundDrawablesWithIntrinsicBounds(null, null, searchDrawable, null);
        }
    }

    public void setSearchDrawableLeft(Drawable icon) {
        searchDrawable = icon;
        if (mSearchView != null) {
            mSearchView.setCompoundDrawablesWithIntrinsicBounds(searchDrawable, null, null, null);
        }
    }

    public void setSearchText(CharSequence title) {
        if (!TextUtils.isEmpty(title)) {
            if (mSearchView == null) {
                mSearchView = new EditText(getContext());
                mSearchView.setSingleLine();
                mSearchView.setEllipsize(TextUtils.TruncateAt.END);
                mSearchView.setClickable(true);
                if (mSearchHintTextColor != 0) {
                    mSearchView.setHintTextColor(mSearchHintTextColor);
                }
                LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                mSearchView.setLayoutParams(layoutParams);
                addView(mSearchView, layoutParams);
            }
        }
        if (mSearchView != null) {
            mSearchView.setHint(title);
        }
        mSearchHintText = title;
    }


    public void setSearchHintTextColor(@ColorInt int color) {
        mSearchHintTextColor = color;
        if (mSearchView != null) {
            mSearchView.setHintTextColor(mSearchHintTextColor);
        }
    }


    public void setSearchHintText(@StringRes int resId) {
        setSearchText(getContext().getText(resId));
    }

    public CharSequence getSearchHintText() {
        return mSearchHintText;
    }


    private void ensureRightButtonView() {
        if (mRightButtonView == null) {
            mRightButtonView = new ImageButton(getContext(), null, R.attr.toolbarNavigationButtonStyle);
            mRightButtonView.setId(R.id.m_right_button);
            LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            mRightButtonView.setLayoutParams(layoutParams);
            addView(mRightButtonView, layoutParams);
        }
    }


    public void setRightButtonIcon(@Nullable Drawable icon) {
        if (icon != null) {
            ensureRightButtonView();
        }
        if (mRightButtonView != null) {
            mRightButtonView.setImageDrawable(icon);
        }
    }


    public void setRightButtonIcon(@DrawableRes int resId) {
        setRightButtonIcon(mTintManager.getDrawable(resId));
    }

    public void setNavigationIcon(@DrawableRes int resId) {
        setNavButtonIcon(mTintManager.getDrawable(resId));
    }

    public void setNavButtonIcon(@Nullable Drawable icon) {
        if (icon != null) {
            ensureNavButtonView();
        }
        if (mNavButtonView != null) {
            mNavButtonView.setImageDrawable(icon);
        }
    }

    private void ensureNavButtonView() {
        if (mNavButtonView == null) {
            mNavButtonView = new ImageButton(getContext(), null, R.attr.toolbarNavigationButtonStyle);
            mNavButtonView.setId(R.id.m_nav_button);
            LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            mNavButtonView.setLayoutParams(layoutParams);
            addView(mNavButtonView, layoutParams);
        }
    }

    public void setLogoIcon(@DrawableRes int resId){
        setLogoIcon(mTintManager.getDrawable(resId));
    }

    public void setLogoIcon(@Nullable Drawable icon){
        if (icon != null) {
            ensureLogoView();
        }
        if (logoView != null) {
            logoView.setImageDrawable(icon);
        }
    }

    private void ensureLogoView(){
        if (logoView == null) {
            LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
            if (!mLogoShape){
                logoView = new  CircleImageView(getContext());
                layoutParams = new LayoutParams(100, 100);
            }else{
                logoView = new ImageView(getContext(), null, R.attr.toolbarNavigationButtonStyle);
            }
            logoView.setId(R.id.m_logo);
            if (mLeftTextView != null) {
                layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.m_left_text);
            }else if (mNavButtonView != null) {
                layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.m_nav_button);
            } else {
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            }
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            logoView.setLayoutParams(layoutParams);
            addView(logoView, layoutParams);
        }
    }

    public void setLeftText(@StringRes int resId) {
        setLeftText(getContext().getText(resId));
    }

    public void setLeftTextSize(float size) {
        mLeftTextSize = size;
        if (mLeftTextView != null) {
            mLeftTextView.setTextSize(size);
        }
    }


    public void setLeftText(CharSequence title) {
        if (!TextUtils.isEmpty(title)) {
            if (mLeftTextView == null) {
                mLeftTextView = new TextView(getContext());
                mLeftTextView.setSingleLine();
                mLeftTextView.setGravity(Gravity.CENTER);
                mLeftTextView.setId(R.id.m_left_text);
                mLeftTextView.setEllipsize(TextUtils.TruncateAt.END);
                if (mLeftTextColor != 0) {
                    mLeftTextView.setTextColor(mLeftTextColor);
                } else {
                    mLeftTextView.setTextColor(getResources().getColor(R.color.black));
                }
                if (mLeftTextSize != 0) {
                    mLeftTextView.setTextSize(mLeftTextSize);
                }
                LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
                layoutParams.alignWithParent = true;
                if (mNavButtonView == null) {
                    layoutParams.setMargins(20, 0, 5, 0);
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                } else {
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.m_nav_button);
                }
                mLeftTextView.setLayoutParams(layoutParams);
                addView(mLeftTextView, layoutParams);
            }
        }
        if (mLeftTextView != null) {
            mLeftTextView.setText(title);
        }
        mLeftText = title;
    }

    public CharSequence getLeftText() {
        return mLeftText;
    }


    public void setLeftTextColor(@ColorInt int color) {
        mLeftTextColor = color;
        if (mLeftTextView != null) {
            mLeftTextView.setTextColor(color);
        }
    }

    public void setRightTextSize(float size) {
        mRightTextSize = size;
        if (mRightTextView != null) {
            mRightTextView.setTextSize(size);
        }
    }


    public void setRightText(@StringRes int resId) {
        setRightText(getContext().getText(resId));
    }

    public CharSequence getRightText() {
        return mRightText;
    }

    public void setRightText(CharSequence title) {
        if (!TextUtils.isEmpty(title)) {
            if (mRightTextView == null) {
                mRightTextView = new TextView(getContext());
                mRightTextView.setSingleLine();
                mRightTextView.setGravity(Gravity.CENTER);
                mRightTextView.setEllipsize(TextUtils.TruncateAt.END);
                mRightTextView.setId(R.id.m_right_text);
                if (mRightTextColor != 0) {
                    mRightTextView.setTextColor(mRightTextColor);
                } else {
                    mRightTextView.setTextColor(getResources().getColor(R.color.black));
                }
                if (mRightTextSize != 0) {
                    mRightTextView.setTextSize(mRightTextSize);
                }
                LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                layoutParams.alignWithParent = true;
                if (mRightButtonView == null) {
                    layoutParams.setMargins(5, 0, mRightTextMarginRight, 0);
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                } else {
                    layoutParams.addRule(RelativeLayout.LEFT_OF, R.id.m_right_button);
                }
                layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
                mRightTextView.setLayoutParams(layoutParams);
                addView(mRightTextView, layoutParams);
            }
        }
        if (mRightTextView != null) {
            mRightTextView.setText(title);
        }
        mRightText = title;
    }


    public void setRightTextColor(@ColorInt int color) {
        mRightTextColor = color;
        if (mRightTextView != null) {
            mRightTextView.setTextColor(color);
        }
    }


    public void setTitleTextColor(@ColorInt int color) {
        mTitleTextColor = color;
        if (mTitleTextView != null) {
            mTitleTextView.setTextColor(color);
        }
    }

    public void setTitleSize(float size) {
        mTitleSize = size;
        if (mTitleTextView != null) {
            mTitleTextView.setTextSize(size);
        }
    }


    public void setTitle(CharSequence title) {

        if (!TextUtils.isEmpty(title)) {
            if (mTitleTextView == null) {
                mTitleTextView = new TextView(getContext());
                mTitleTextView.setSingleLine();
                mTitleTextView.setGravity(Gravity.CENTER);
                mTitleTextView.setTextSize(20);
                mTitleTextView.setEllipsize(TextUtils.TruncateAt.END);
                if (mTitleTextColor != 0) {
                    mTitleTextView.setTextColor(mTitleTextColor);
                } else {
                    mTitleTextView.setTextColor(getResources().getColor(R.color.black));
                }
                if (mTitleSize != 0) {
                    mTitleTextView.setTextSize(mTitleSize);
                }
                LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                mTitleTextView.setLayoutParams(layoutParams);
                addView(mTitleTextView, layoutParams);
            }
        }
        if (mTitleTextView != null) {
            mTitleTextView.setText(title);
        }
        mTitleText = title;
    }

    public void setCustomView(View view){
        removeAllViews();
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        if(mTitleTextView!=null){
            mTitleTextView.setVisibility(View.GONE);
        }
        view.setLayoutParams(layoutParams);
        addView(view);
    }

    public void setCustomView(@LayoutRes int resId){
        View view = LayoutInflater.from(context).inflate(resId, null);
        removeAllViews();
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        if(mTitleTextView!=null){
            mTitleTextView.setVisibility(View.GONE);
        }
        view.setLayoutParams(layoutParams);
        addView(view);
    }






    public void setTitle(@StringRes int resId) {
        setTitle(getContext().getText(resId));
    }

    public CharSequence getTitle() {
        return mTitleText;
    }


}
