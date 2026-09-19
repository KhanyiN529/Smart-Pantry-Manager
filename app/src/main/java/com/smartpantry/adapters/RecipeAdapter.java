package com.smartpantry.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartpantry.R;
import com.smartpantry.data.Recipe;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.VH> {
    public interface Listener { void onOpen(Recipe recipe); }

    private List<Recipe> items;
    private Listener listener;

    public RecipeAdapter(List<Recipe> items, Listener listener) {
        this.items = items; this.listener = listener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Recipe r = items.get(position);
        holder.name.setText(r.name);
        holder.itemView.setOnClickListener(v -> listener.onOpen(r));
    }

    @Override
    public int getItemCount() { return items == null ? 0 : items.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView name;
        VH(@NonNull View v) { super(v); name = v.findViewById(R.id.txtRecipeName); }
    }
}
