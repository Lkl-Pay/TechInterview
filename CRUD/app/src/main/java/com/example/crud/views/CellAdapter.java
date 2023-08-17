package com.example.crud.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crud.MainActivity;
import com.example.crud.R;
import com.example.crud.api.Customer;

import java.util.List;

public class CellAdapter extends RecyclerView.Adapter<CellAdapter.ViewHolder> {

    private List<Customer> customerList;
    private int selected = RecyclerView.NO_POSITION;
    public CellAdapter(List<Customer> customerList) {
        this.customerList = customerList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvAge;
        TextView tvCard;
        TextView tvAmount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvAge = itemView.findViewById(R.id.tvAge);
            tvCard = itemView.findViewById(R.id.tvCard);
            tvAmount = itemView.findViewById(R.id.tvAmount);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Customer customer = customerList.get(position);
        holder.tvName.setText(customer.getName());
        holder.tvAge.setText(customer.getAge());
        holder.tvCard.setText(customer.getCard());
        holder.tvAmount.setText(customer.getAmount());

        int firstColor = holder.itemView.getResources().getColor(R.color.firstColor);
        int secondColor = holder.itemView.getResources().getColor(R.color.secondColor);
        int color = position % 2 == 0 ? firstColor : secondColor;
        holder.itemView.setBackgroundColor(color);

        int selectedColor = holder.itemView.getResources().getColor(R.color.selected);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int previous = selected;
                selected = holder.getAdapterPosition();
                notifyItemChanged(previous);
                notifyItemChanged(selected);
            }
        });
        holder.itemView.setBackgroundColor(selected == position ? selectedColor : color);
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    public int getSelected() {
        return selected;
    }
}
