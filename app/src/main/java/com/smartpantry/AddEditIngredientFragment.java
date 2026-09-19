package com.smartpantry;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.smartpantry.data.AppDatabase;
import com.smartpantry.data.Ingredient;

public class AddEditIngredientFragment extends Fragment {

    private static final String ARG_ID = "arg_id";
    private int ingredientId = -1;

    public static AddEditIngredientFragment newInstance(int id) {
        AddEditIngredientFragment f = new AddEditIngredientFragment();
        Bundle b = new Bundle(); b.putInt(ARG_ID, id); f.setArguments(b);
        return f;
    }

    private EditText edtName, edtQty, edtUnit, edtExpiry;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_edit_ingredient, container, false);
        edtName = v.findViewById(R.id.edtName);
        edtQty = v.findViewById(R.id.edtQty);
        edtUnit = v.findViewById(R.id.edtUnit);
        edtExpiry = v.findViewById(R.id.edtExpiry);
        Button btnSave = v.findViewById(R.id.btnSave);

        if (getArguments() != null) ingredientId = getArguments().getInt(ARG_ID, -1);

        requireActivity().setTitle(ingredientId == -1 ? "Add ingredient" : "Edit ingredient");

        if (ingredientId != -1 && savedInstanceState == null) {
            AppDatabase db = AppDatabase.getInstance(requireContext());
            Ingredient ing = db.ingredientDao().findById(ingredientId);
            if (ing != null) {
                edtName.setText(ing.name);
                edtQty.setText(String.valueOf(ing.quantity));
                edtUnit.setText(ing.unit);
                edtExpiry.setText(ing.expiry);
            }
        }

        btnSave.setOnClickListener(view -> {
            String name = edtName.getText().toString().trim();
            String qtys = edtQty.getText().toString().trim();
            String unit = edtUnit.getText().toString().trim();
            String expiry = edtExpiry.getText().toString().trim();

            if (TextUtils.isEmpty(name)) { edtName.setError("Required"); return; }
            double q = 0;
            try { q = Double.parseDouble(qtys); } catch (Exception e) { edtQty.setError("Invalid"); return; }

            if ((Double.isNaN(q) || Double.isInfinite(q)) || q <= 0) { edtQty.setError("Enter a positive quantity"); return; }
            if (!com.smartpantry.logic.RecipeMatcher.supportedUnit(unit)) { edtUnit.setError("Use g, kg, ml, l, pcs, slices, cloves, heads or stalks"); return; }
            if (!expiry.isEmpty()) {
                try { java.time.LocalDate.parse(expiry); }
                catch (java.time.format.DateTimeParseException e) { edtExpiry.setError("Use a real date: YYYY-MM-DD"); return; }
            }
            AppDatabase db = AppDatabase.getInstance(requireContext());
            if (ingredientId == -1) {
                Ingredient ing = new Ingredient(name, q, unit, expiry);
                db.ingredientDao().insert(ing);
            } else {
                Ingredient ing = db.ingredientDao().findById(ingredientId);
                if (ing != null) {
                    ing.name = name; ing.quantity = q; ing.unit = unit; ing.expiry = expiry;
                    db.ingredientDao().update(ing);
                }
            }

            // go back to pantry list
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        v.findViewById(R.id.btnCancel).setOnClickListener(view -> requireActivity().getSupportFragmentManager().popBackStack());
        return v;
    }
}
