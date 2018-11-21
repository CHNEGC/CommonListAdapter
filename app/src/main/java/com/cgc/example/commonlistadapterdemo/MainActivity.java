package com.cgc.example.commonlistadapterdemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cgc.example.commonlistadapterdemo.adapter.CommonListAdapter;
import com.cgc.example.commonlistadapterdemo.adapter.ItemViewBinder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rlv_list;
    private List<TextItem> textItems = new ArrayList<>();
    private CommonListAdapter baseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        rlv_list = (RecyclerView) findViewById(R.id.rlv_list);
        for (int i = 0; i < 10; i++) {
            textItems.add(new TextItem());
        }
        baseAdapter = new CommonListAdapter(this, textItems);
        baseAdapter.register(String.class, null);
        baseAdapter.register(TextItem.class, new ItemViewBinder<TextItem, RecyclerView.ViewHolder>() {
            @Override
            protected int getItemViewId() {
                return R.layout.item_text;
            }

            @NonNull
            @Override
            protected RecyclerView.ViewHolder onCreateViewHolder(@NonNull View itemView) {
                return new TextViewHolder(itemView);
            }

            @Override
            protected void onBinderViewHolder(@NonNull CommonListAdapter adapter, @NonNull RecyclerView.ViewHolder holder, @NonNull TextItem item, @NonNull int position) {
                TextViewHolder viewHolder = (TextViewHolder) holder;
                viewHolder.tvTxt.setText(item.getClass().getName() + "---" + position);
            }

        });
        baseAdapter.register(ImageItem.class, new ItemViewBinder<ImageItem, RecyclerView.ViewHolder>() {

            @Override
            protected int getItemViewId() {
                return R.layout.item_img;
            }

            @NonNull
            @Override
            protected RecyclerView.ViewHolder onCreateViewHolder(@NonNull View itemView) {
                return new ImgViewHolder(itemView);
            }

            @Override
            protected void onBinderViewHolder(@NonNull CommonListAdapter adapter, @NonNull RecyclerView.ViewHolder holder, @NonNull ImageItem item, @NonNull int position) {

            }
        });
        rlv_list.setLayoutManager(new LinearLayoutManager(this));
        rlv_list.setHasFixedSize(true);
        rlv_list.setAdapter(baseAdapter);
    }

    public class TextViewHolder extends RecyclerView.ViewHolder {

        TextView tvTxt;

        public TextViewHolder(View itemView) {
            super(itemView);
            tvTxt = itemView.findViewById(R.id.tv_txt);
        }
    }

    public class ImgViewHolder extends RecyclerView.ViewHolder {
        public ImgViewHolder(View itemView) {
            super(itemView);
        }
    }


    public void onAddText(View view) {
        baseAdapter.addItem(new TextItem());
    }

    public void onAddImg(View view) {
        baseAdapter.addItem(new ImageItem());
    }
}
