package com.equipe7.eductrack.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
import com.equipe7.eductrack.R;

public class ActivityAdminDashboard extends AppCompatActivity {

    private TextView tvAdminName, tvTotalStudents, tvTotalTeachers;
    private ImageView ivNotifications, ivProfile;
    private MaterialCardView cardManageTeachers, cardManageStudents, cardAnalytics;
    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_home_activity);

        initViews();
        setupClickListeners();
        loadDashboardData();
    }

    private void initViews() {
        tvAdminName = findViewById(R.id.tvAdminName);
        tvTotalStudents = findViewById(R.id.tvTotalStudents);
        tvTotalTeachers = findViewById(R.id.tvTotalTeachers);
        ivNotifications = findViewById(R.id.ivNotifications);
        ivProfile = findViewById(R.id.ivProfile);
        cardManageTeachers = findViewById(R.id.cardManageTeachers);
        cardManageStudents = findViewById(R.id.cardManageStudents);
        cardAnalytics = findViewById(R.id.cardAnalytics);
        bottomNavigation = findViewById(R.id.bottomNavigation);
    }

    private void setupClickListeners() {
        // Profile click
        ivProfile.setOnClickListener(v -> {
            Intent intent = new Intent(this, ActivityProfile.class);
            startActivity(intent);
        });

        // Notifications click
        ivNotifications.setOnClickListener(v -> {
            Intent intent = new Intent(this, ActivityNotification.class);
            startActivity(intent);
        });

        // Manage Teachers card
        cardManageTeachers.setOnClickListener(v -> {
            Intent intent = new Intent(this, TeacherDashboard.class);
            startActivity(intent);
        });

        // Manage Students card
        cardManageStudents.setOnClickListener(v -> {
            Intent intent = new Intent(this, StudentDashboardActivity.class);
            startActivity(intent);
        });

        // Analytics card
        cardAnalytics.setOnClickListener(v -> {
            Intent intent = new Intent(this, AnalyticsDashboard.class);
            startActivity(intent);
        });

        // Bottom Navigation
        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_dashboard) {
                // Already on dashboard
                return true;
            } else if (itemId == R.id.nav_teachers) {
                startActivity(new Intent(this, TeacherDashboard.class));
                return true;
            } else if (itemId == R.id.nav_students) {
                startActivity(new Intent(this, StudentDashboardActivity.class));
                return true;
            } else if (itemId == R.id.nav_analytics) {
                startActivity(new Intent(this, AnalyticsDashboard.class));
                return true;
            } else if (itemId == R.id.nav_settings) {
                // Open settings
                return true;
            }
            return false;
        });
    }

    private void loadDashboardData() {
        // Set admin name (you can get this from SharedPreferences or database)
        tvAdminName.setText("Admin Dashboard");
        
        // Load statistics (replace with actual data from your database)
        tvTotalStudents.setText("1,247");
        tvTotalTeachers.setText("89");
    }
}
