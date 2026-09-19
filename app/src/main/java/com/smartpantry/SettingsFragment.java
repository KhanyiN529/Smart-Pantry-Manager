package com.smartpantry;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.SwitchCompat;

public class SettingsFragment extends Fragment {
    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle state) {
        requireActivity().setTitle("Settings");
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        android.content.SharedPreferences preferences = requireContext().getSharedPreferences("pantry_settings", 0);
        SwitchCompat alerts = view.findViewById(R.id.switchAlerts);
        alerts.setChecked(preferences.getBoolean("expiry_alerts", true));
        alerts.setOnCheckedChangeListener((button, checked) -> preferences.edit().putBoolean("expiry_alerts", checked).apply());
        return view;
    }
}
