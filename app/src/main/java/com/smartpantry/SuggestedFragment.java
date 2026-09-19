package com.smartpantry;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smartpantry.adapters.RecipeAdapter;
import com.smartpantry.data.AppDatabase;
import com.smartpantry.data.Ingredient;
import com.smartpantry.data.Recipe;

import java.util.ArrayList;
import java.util.List;

public class SuggestedFragment extends Fragment implements RecipeAdapter.Listener {

    private RecipeAdapter adapter;
    private TextView txtEmpty;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        requireActivity().setTitle("Ready to cook");
        View v = inflater.inflate(R.layout.fragment_suggested, container, false);
        RecyclerView rv = v.findViewById(R.id.recyclerRecipes);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        txtEmpty = v.findViewById(R.id.txtEmpty);
        adapter = new RecipeAdapter(new ArrayList<>(), this);
        rv.setAdapter(adapter);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshSuggestedRecipes();
    }

    private void refreshSuggestedRecipes() {
        List<Recipe> matches = computeStrictMatches();
        if (adapter != null) {
            adapter = new RecipeAdapter(matches, this);
            RecyclerView rv = requireView().findViewById(R.id.recyclerRecipes);
            rv.setAdapter(adapter);
        }
        if (txtEmpty != null) {
            txtEmpty.setVisibility(matches.isEmpty() ? View.VISIBLE : View.GONE);
        }
    }

    private List<Recipe> computeStrictMatches() {
        AppDatabase db = AppDatabase.getInstance(requireContext());
        List<Recipe> all = db.recipeDao().getAll();
        List<Ingredient> pantry = db.ingredientDao().getAll();

        List<Recipe> result = new ArrayList<>();
        for (Recipe recipe : all) {
            if (com.smartpantry.logic.RecipeMatcher.matches(recipe, pantry)) result.add(recipe);
        }
        return result;
    }

    @Override
    public void onOpen(Recipe recipe) {
        android.content.Intent it = new android.content.Intent(requireContext(), RecipeDetailActivity.class);
        it.putExtra("recipe_id", recipe.id);
        startActivity(it);
    }
}
