package com.smartpantry;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smartpantry.data.AppDatabase;
import com.smartpantry.data.Ingredient;
import com.smartpantry.adapters.IngredientAdapter;

import java.util.List;

public class PantryFragment extends Fragment implements IngredientAdapter.Listener {

    private IngredientAdapter adapter;
    private android.widget.TextView empty;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        requireActivity().setTitle("My pantry");
        View v = inflater.inflate(R.layout.fragment_pantry, container, false);
        empty = v.findViewById(R.id.txtPantryEmpty);
        RecyclerView rv = v.findViewById(R.id.recyclerIngredients);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new IngredientAdapter(java.util.Collections.emptyList(), this);
        rv.setAdapter(adapter);
        refreshIngredients();

        v.findViewById(R.id.fabAdd).setOnClickListener(view -> {
            // open Add/Edit fragment for new ingredient
            AddEditIngredientFragment f = AddEditIngredientFragment.newInstance(-1);
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, f)
                    .addToBackStack(null)
                    .commit();
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshIngredients();
    }

    private void refreshIngredients() {
        AppDatabase db = AppDatabase.getInstance(requireContext());
        List<Ingredient> items = db.ingredientDao().getAll();
        if (empty != null) empty.setVisibility(items.isEmpty() ? View.VISIBLE : View.GONE);
        if (adapter != null) {
            adapter.updateList(items);
        }
    }

    @Override
    public void onEdit(Ingredient ingredient) {
        AddEditIngredientFragment f = AddEditIngredientFragment.newInstance(ingredient.id);
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, f)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onDelete(Ingredient ingredient) {
        new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                .setTitle("Delete " + ingredient.name + "?")
                .setMessage("This removes this pantry entry.")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Delete", (dialog, which) -> {
                    AppDatabase.getInstance(requireContext()).ingredientDao().delete(ingredient);
                    refreshIngredients();
                }).show();
    }
}
