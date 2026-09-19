package com.smartpantry;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Seed recipes on first run
        com.smartpantry.data.SeedData.ensureSeeded(this);
        // Load PantryFragment by default
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new PantryFragment())
                    .commit();
        }

        BottomNavigationView nav = findViewById(R.id.bottomNav);
        nav.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                getSupportFragmentManager().popBackStackImmediate(null, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE);
                switch (item.getItemId()) {
                    case R.id.nav_pantry:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PantryFragment()).commit();
                        return true;
                    case R.id.nav_suggested:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SuggestedFragment()).commit();
                        return true;
                    case R.id.nav_settings:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingsFragment()).commit();
                        return true;
                }
                return false;
            }
        });
    }
}
