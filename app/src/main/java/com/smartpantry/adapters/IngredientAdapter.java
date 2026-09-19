package com.smartpantry.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartpantry.R;
import com.smartpantry.data.Ingredient;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.VH> {

    public interface Listener {
        void onEdit(Ingredient ingredient);
        void onDelete(Ingredient ingredient);
    }

    private List<Ingredient> items;
    private Listener listener;

    public IngredientAdapter(List<Ingredient> items, Listener listener) {
        this.items = items;
        this.listener = listener;
    }

    public void updateList(List<Ingredient> newList) {
        this.items = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Ingredient ing = items.get(position);
        holder.name.setText(ing.name);
        holder.qty.setText(ing.quantity + " " + (ing.unit == null ? "" : ing.unit));
        String expiry = ing.expiry == null ? "" : ing.expiry;
        if (!expiry.isEmpty()) {
            String label = " | Expires " + expiry;
            if (holder.itemView.getContext().getSharedPreferences("pantry_settings", 0).getBoolean("expiry_alerts", true)) {
                try {
                    java.time.LocalDate date = java.time.LocalDate.parse(expiry);
                    if (date.isBefore(java.time.LocalDate.now())) label += " (expired)";
                    else if (!date.isAfter(java.time.LocalDate.now().plusDays(3))) label += " (use soon)";
                } catch (RuntimeException ignored) { }
            }
            holder.qty.append(label);
        }
        holder.btnEdit.setContentDescription("Edit " + ing.name);
        holder.btnDelete.setContentDescription("Delete " + ing.name);
        holder.btnEdit.setOnClickListener(v -> listener.onEdit(ing));
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(ing));
    }

    @Override
    public int getItemCount() { return items == null ? 0 : items.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView name, qty;
        ImageButton btnEdit, btnDelete;
        VH(@NonNull View v) {
            super(v);
            name = v.findViewById(R.id.txtName);
            qty = v.findViewById(R.id.txtQty);
            btnEdit = v.findViewById(R.id.btnEdit);
            btnDelete = v.findViewById(R.id.btnDelete);
        }
    }
}
