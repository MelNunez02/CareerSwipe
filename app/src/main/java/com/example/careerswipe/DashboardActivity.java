package com.example.careerswipe;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        } else {
            throw new IllegalStateException("Toolbar not found in layout.");
        }

        // Set up the navigation drawer
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        if (drawerLayout == null || navigationView == null) {
            throw new IllegalStateException("DrawerLayout or NavigationView not found in layout.");
        }

        // Handle navigation item clicks
        navigationView.setNavigationItemSelectedListener(item -> {
            // You can handle other navigation items here if needed
            drawerLayout.closeDrawers(); // Close the drawer after selection
            return true;
        });
    }
}
