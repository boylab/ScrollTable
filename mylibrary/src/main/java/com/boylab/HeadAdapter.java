package com.boylab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.boylab.scrolltable.R;

public class HeadAdapter extends RecyclerView.Adapter<HeadAdapter.MyViewHolder> {

    private Context context;

    public HeadAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        //View inflate = LayoutInflater.from(context).inflate(R.layout.item_table_row_text, null);
        View inflate = View.inflate(viewGroup.getContext(), R.layout.item_table_row_text, null);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        holder.text_item.setText("Item"+position);
        /*ViewGroup.LayoutParams layoutParams = holder.text_item.getLayoutParams();
        layoutParams.height = headParams.getHeight();
        layoutParams.width = headParams.getWidth(position + 1);
        holder.text_item.setLayoutParams(layoutParams);

        holder.text_item.setText(itemRow.get(position + 1));

        holder.text_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (canFocus){
                    holder.text_item.setBackgroundColor(headParams.getFoucsColor());
                }
                holder.text_item.setBackgroundColor(headParams.getFoucsColor());
                if (onClickListener != null){
                    onClickListener.onClick(holder.text_item);
                }
            }
        });

        holder.text_item.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                if (canFocus){
                    holder.text_item.setBackgroundColor(headParams.getFoucsColor());
                }
                if (onLongClickListener != null){
                    onLongClickListener.onLongClick(holder.text_item);
                }
                return true;
            }
        });*/

    }

    @Override
    public int getItemCount() {
        /*if (itemRow.size() > 1){
            return itemRow.size() - 1;
        }*/
        return 20;
    }

    protected class MyViewHolder extends RecyclerView.ViewHolder {

        TextView text_item;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text_item = itemView.findViewById(R.id.text_item);

            /*text_item.setTextSize(headParams.getTextSize());
            text_item.setTextColor(headParams.getTextColor());
            text_item.setBackgroundColor(isFocus ? headParams.getFoucsColor() : headParams.getBackgroundColor());
            text_item.setPadding(headParams.getPaddingLeft(), headParams.getPaddingLeft(), headParams.getPaddingLeft(), headParams.getPaddingLeft());

            text_item.setGravity(headParams.getItemGravity().getGravity());*/
        }
    }

}
