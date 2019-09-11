package com.example.daotest.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daotest.R;
import com.example.daotest.bean.OrderData;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author tian on 2019/8/27
 */
public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    private List<OrderData.DataBean> mList;

    private int TITLE = 6;
    private int EDIT = 1;
    private int SELECT = 3;
    private int BUTTON = 2;

    boolean hidden = false;
    int choice = 0;
    AlertDialog dialog = null;

    public MyAdapter(Context mContext, List<OrderData.DataBean> mList) {
        super();
        this.mContext = mContext;
        this.mList = mList;
    }

    public void setmList(List<OrderData.DataBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public List<OrderData.DataBean> getList() {
        return this.mList;
    }


    /**
     * 根据position,判断该item需要哪种视图,返回int类型的视图标志
     */
    @Override
    public int getItemViewType(int position) {
        if (mList.get(position).getValueType() == TITLE) {
            //如果是标题
            return TITLE;
        } else if (mList.get(position).getValueType() == EDIT) {
            //是输入
            return EDIT;
        } else if (mList.get(position).getValueType() == SELECT) {
            //选择
            return SELECT;
        } else if (mList.get(position).getValueType() == BUTTON) {
            //带切换的按钮
            return BUTTON;
        } else {
            return super.getItemViewType(position);
        }

    }

    /**
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == TITLE) {
            //标题
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_title, parent, false);
            TitleViewHolder titleViewHolder = new TitleViewHolder(view);
            //可在此处设置监听
            titleViewHolder.tvTitleTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = titleViewHolder.getAdapterPosition();
                    //设置监听方法


                    Log.i("TAG", "title" + position);
                }
            });
            return titleViewHolder;
        } else if (viewType == EDIT) {
            //输入栏
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_edit, parent, false);
            EditViewHolder editViewHolder = new EditViewHolder(view);
            editListenerUtil(editViewHolder);

            editViewHolder.tvEditTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = editViewHolder.getAdapterPosition();
                    Log.i("TAG", "edit" + position);
                }
            });

            return editViewHolder;
        } else if (viewType == SELECT) {
            //带选择栏
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_select, parent, false);
            SelectViewHolder selectViewHolder = new SelectViewHolder(view);
            selectViewHolder.selectMode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = selectViewHolder.getAdapterPosition();
                    OrderData.DataBean dataBean = mList.get(position);
                    List<String> item = dataBean.getAttrValueList();
                    if (item != null) {
                        String[] items = item.toArray(new String[item.size() - 1]);
                        Log.i("TAG", "数组长度" + items.length);
                        int select = selectUtil(mContext, items, "请选择" + dataBean.getAttrName(), selectViewHolder.tvSelectMode);
                        Log.i("TAG", "显示选择了哪个" + select);

                    } else {
                        Toast.makeText(mContext, "暂无数据", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            return selectViewHolder;
        } else if (viewType == BUTTON) {

            //带切换按钮
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_button, parent, false);
            ButtonViewHolder buttonViewHolder = new ButtonViewHolder(view);

            buttonViewHolder.btnButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = buttonViewHolder.getAdapterPosition();
                    Log.i("TAG", "button" + position);
                    hidden = !hidden;
                    //得到bean类中的list类
                    List<String> titles = mList.get(position).getAttrValueList();
                    for (String title: titles){
                        for (int i=0;i<mList.size();i++){
                            OrderData.DataBean dataBean = mList.get(i);
                            if (title.equals(dataBean.getAttrKeyName())){
                                dataBean.setHidden(hidden);
                                mList.set(i,dataBean);
                                notifyItemChanged(i);
                            }
                        }
                    }
                }
            });

            return buttonViewHolder;

        }

        return null;

    }

    /**
     * 绑定数据方法
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        OrderData.DataBean dataBean = mList.get(position);
        //绑定数据
        if (holder instanceof TitleViewHolder) {
            //标题
            TitleViewHolder titleViewHolder = (TitleViewHolder) holder;
            titleViewHolder.setIsRecyclable(false);
            titleViewHolder.tvTitleTitle.setText(dataBean.getAttrName());
        } else if (holder instanceof EditViewHolder) {
            //输入
            EditViewHolder editViewHolder = (EditViewHolder) holder;
            if (dataBean.isHidden()){
                editViewHolder.rlEdit.setVisibility(View.GONE);
            }else {
                editViewHolder.rlEdit.setVisibility(View.VISIBLE);
            }
            editViewHolder.tvEditTitle.setText(dataBean.getAttrName());

        } else if (holder instanceof SelectViewHolder) {
            //选择
            SelectViewHolder selectViewHolder = (SelectViewHolder) holder;
            if (dataBean.isHidden()){
                selectViewHolder.rlSelct.setVisibility(View.GONE);
            }else {
                selectViewHolder.rlSelct.setVisibility(View.VISIBLE);
            }
            selectViewHolder.modeTitle.setText(dataBean.getAttrName());


        } else if (holder instanceof ButtonViewHolder) {
            //切换
            ButtonViewHolder buttonViewHolder = (ButtonViewHolder) holder;

            buttonViewHolder.modeTitle.setText(dataBean.getAttrName());
        }


    }


    @Override
    public int getItemCount() {
        return mList.size() > 0 ? mList.size() : 0;
    }


    static class TitleViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.view_line)
        View viewLine;
        @InjectView(R.id.tv_title_title)
        TextView tvTitleTitle;
        @InjectView(R.id.tv_title_info)
        TextView tvTitleInfo;

        public TitleViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    static class EditViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.rl_edit)
        RelativeLayout rlEdit;
        @InjectView(R.id.tv_edit_title)
        TextView tvEditTitle;
        @InjectView(R.id.ed_edit_content)
        EditText edEditContent;

        public EditViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    static class SelectViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.rl_select)
        RelativeLayout rlSelct;

        @InjectView(R.id.mode_title)
        TextView modeTitle;
        @InjectView(R.id.tv_select_mode)
        TextView tvSelectMode;
        @InjectView(R.id.select_mode)
        LinearLayout selectMode;

        public SelectViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    static class ButtonViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.mode_title)
        TextView modeTitle;
        @InjectView(R.id.btn_button)
        Button btnButton;

        public ButtonViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }


    private int selectUtil(Context context, String[] items, String title,TextView textView) {
        choice = 0;
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setSingleChoiceItems(items, choice, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        choice = which;
                        textView.setText(items[choice]);
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    }
                });
        dialog = builder.create();
        dialog.show();
        return choice;
    }


    private void editListenerUtil(EditViewHolder editViewHolder){
        editViewHolder.edEditContent.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int position = editViewHolder.getAdapterPosition();
                mList.get(position).setAttrName(s.toString());
            }
        });

    }

}
