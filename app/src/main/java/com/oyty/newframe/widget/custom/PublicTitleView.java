package com.oyty.newframe.widget.custom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.oyty.newframe.R;
import com.oyty.newframe.widget.util.UIUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PublicTitleView extends FrameLayout {

    private final Context mContext;
    @BindView(R.id.mLeftImg)
    ImageView mLeftImg;
    @BindView(R.id.mLeftLabel)
    TextView mLeftLabel;
    @BindView(R.id.mLeftAction)
    RelativeLayout mLeftAction;
    @BindView(R.id.mSecondLeftImg)
    ImageView mSecondLeftImg;
    @BindView(R.id.mSecondLeftLabel)
    TextView mSecondLeftLabel;
    @BindView(R.id.mSecondLeftAction)
    RelativeLayout mSecondLeftAction;
    @BindView(R.id.mMiddleLabel)
    TextView mMiddleLabel;
    @BindView(R.id.mRightImg)
    ImageView mRightImg;
    @BindView(R.id.mRightLabel)
    TextView mRightLabel;
    @BindView(R.id.mMsgImg)
    ImageView mMsgImg;
    @BindView(R.id.mMsgView)
    BadgeView mMsgView;
    @BindView(R.id.mMessageLayout)
    RelativeLayout mMessageLayout;
    @BindView(R.id.mRightAction)
    RelativeLayout mRightAction;
    @BindView(R.id.mSecondRightImg)
    ImageView mSecondRightImg;
    @BindView(R.id.mSecondRightAction)
    RelativeLayout mSecondRightAction;
    @BindView(R.id.mStatusBarView)
    View mStatusBarView;
    @BindView(R.id.mBaseView)
    View mBaseView;

    public PublicTitleView(@NonNull Context context) {
        this(context, null);
    }

    public PublicTitleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PublicTitleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    private void initView() {
        View view = View.inflate(mContext, R.layout.view_public_title, this);
        ButterKnife.bind(this, view);

//        CommonUtil.updateStatusBarHeight(mContext, mStatusBarView);
    }

    public void setTitle(int recourseId) {
        mMiddleLabel.setVisibility(VISIBLE);
        mMiddleLabel.setText(recourseId);
        mMiddleLabel.setTextColor(UIUtil.getColor(R.color.white));
    }

    public void setTitle(int recourseId, int color) {
        mMiddleLabel.setVisibility(VISIBLE);
        mMiddleLabel.setText(recourseId);
        mMiddleLabel.setTextColor(UIUtil.getColor(color));
    }

    public void setLeftIcon(int resource_id, View.OnClickListener onClickListener) {
        mLeftImg.setVisibility(View.VISIBLE);
        mLeftImg.setImageResource(resource_id);
        mLeftAction.setOnClickListener(onClickListener);
    }

    public void setRightIcon(int resource_id, View.OnClickListener onClickListener) {
        mRightImg.setVisibility(VISIBLE);
        mRightImg.setImageResource(resource_id);
        mRightAction.setOnClickListener(onClickListener);
    }

    public void setLeftBackAction(View.OnClickListener onClickListener) {
        setLeftIcon(R.drawable.back_white, onClickListener);
    }

    public void setTitle(String title) {
        mMiddleLabel.setVisibility(VISIBLE);
        mMiddleLabel.setText(title);
        mMiddleLabel.setSelected(true);
        mMiddleLabel.setTextColor(UIUtil.getColor(R.color.white));
    }

    public void setSecondLeftLabel(int resource_id, View.OnClickListener onClickListener) {
        mSecondLeftLabel.setVisibility(View.VISIBLE);
        mSecondLeftLabel.setText(resource_id);
        mSecondLeftLabel.setTextColor(UIUtil.getColor(R.color.white));
        mSecondLeftAction.setOnClickListener(onClickListener);
    }

    public void setBackgroundColor(int color) {
        mBaseView.setBackgroundColor(color);
    }
}
