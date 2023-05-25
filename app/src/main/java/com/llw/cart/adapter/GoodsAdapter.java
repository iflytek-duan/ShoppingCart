package com.llw.cart.adapter;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.llw.cart.bean.CarResponse;
import com.llw.cart.R;
import com.llw.cart.util.CashierInputFilter;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品适配器
 *
 * @author llw
 */
public class GoodsAdapter extends BaseQuickAdapter<CarResponse.OrderDataBean.CartlistBean, BaseViewHolder> {

    public GoodsAdapter(int layoutResId, @Nullable List<CarResponse.OrderDataBean.CartlistBean> data) {
        super(layoutResId, data);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void convert(BaseViewHolder helper, CarResponse.OrderDataBean.CartlistBean item) {
        helper.setText(R.id.tv_good_name, item.getProductName())
                .setText(R.id.tv_good_color, item.getColor())
                .setText(R.id.tv_good_size, item.getSize())
                .setText(R.id.tv_goods_num, item.getCount() + "");
        EditText editText = (EditText) helper.getView(R.id.tv_goods_price);
        editText.setFilters(new InputFilter[]{new CashierInputFilter()});
        editText.setCursorVisible(false);
        editText.setText(String.valueOf(item.getPrice()));
        editText.setOnTouchListener((View v, MotionEvent event) -> {
            editText.setCursorVisible(true);
            return false;
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                item.setPrice(TextUtils.isEmpty(s.toString()) ? new BigDecimal(0) : new BigDecimal(s.toString()));
            }
        });
        ImageView goodImg = helper.getView(R.id.iv_goods);
        Glide.with(mContext).load(item.getDefaultPic()).into(goodImg);

        ImageView checkedGoods = helper.getView(R.id.iv_checked_goods);
        //判断商品是否选中
        if (item.isChecked()) {
            checkedGoods.setImageDrawable(mContext.getDrawable(R.drawable.ic_checked));
        } else {
            checkedGoods.setImageDrawable(mContext.getDrawable(R.drawable.ic_check));
        }
        //添加点击事件
        helper.addOnClickListener(R.id.iv_checked_goods)//选中商品
                .addOnClickListener(R.id.tv_increase_goods_num)//增加商品
                .addOnClickListener(R.id.tv_reduce_goods_num);//减少商品
    }
}
