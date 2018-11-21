
更新：2018-11-21

CommonListAdapter 用于RecyclerView List的适配，可以更好更方便，快速的适配显示不同的Item的处理
Adapter的Binder 数据的业务逻辑可以单独抽离出来，对于同一个item xml 布局样式的界面，可以分开处理，逻辑清晰，有利于后期的
维护处理

用法：
//实例化 第一个参数是当前Activity 或 Fragment 的Context, 第二个参数为List
CommonListAdapter baseAdapter = new CommonListAdapter(this, textItems);

//注册item 第一个 item类型的实体 第二个参数 ItemViewBinder 要实现自己的逻辑就要继承 ItemViewBinder
baseAdapter.register(String.class, null);

demo：
baseAdapter.register(TextItem.class, new ItemViewBinder<TextItem, RecyclerView.ViewHolder>() {
            @Override
            protected int getItemViewId() {
                return R.layout.item_text; //item的UI
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