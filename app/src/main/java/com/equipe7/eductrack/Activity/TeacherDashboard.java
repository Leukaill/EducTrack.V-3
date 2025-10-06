package com.equipe7.eductrack.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
import com.equipe7.eductrack.R;

public class TeacherDashboard extends AppCompatActivity {

    private TextView tvTeacherName, tvTotalStudents, tvTotalClasses, tvPendingAssignments;
    private ImageView ivNotifications, ivProfile;
    private MaterialCardView cardAttendance, cardAssignment, cardGrading, cardSchedule;
    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard);

        initViews();
        setupClickListeners();
        loadTeacherData();
    }

    private void initViews() {
        tvTeacherName = findViewById(R.id.tvTeacherName);
        tvTotalStudents = findViewById(R.id.tvTotalStudents);
        tvTotalClasses = findViewById(R.id.tvTotalClasses);
        tvPendingAssignments = findViewById(R.id.tvPendingAssignments);
        ivNotifications = findViewById(R.id.ivNotifications);
        ivProfile = findViewById(R.id.ivProfile);
        cardAttendance = findViewById(R.id.cardAttendance);
        cardAssignment = findViewById(R.id.cardAssignment);
        cardGrading = findViewById(R.id.cardGrading);
        cardSchedule = findViewById(R.id.cardSchedule);
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

        // Attendance card
        cardAttendance.setOnClickListener(v -> {
            // Navigate to attendance activity
            // Intent intent = new Intent(this, AttendanceActivity.class);
            // startActivity(intent);
        });

        // Assignment card
        cardAssignment.setOnClickListener(v -> {
            Intent intent = new Intent(this, ActivityHomework.class);
            startActivity(intent);
        });

        // Grading card
        cardGrading.setOnClickListener(v -> {
            // Navigate to grading activity
            // Intent intent = new Intent(this, GradingActivity.class);
            // startActivity(intent);
        });

        // Schedule card
        cardSchedule.setOnClickListener(v -> {
            // Navigate to schedule activity
            // Intent intent = new Intent(this, ScheduleActivity.class);
            // startActivity(intent);
        });

        // Bottom Navigation
        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                // Already on home
                return true;
            } else if (itemId == R.id.nav_classes) {
                // Navigate to classes
                return true;
            } else if (itemId == R.id.nav_attendance) {
                // Navigate to attendance
                return true;
            } else if (itemId == R.id.nav_grading) {
                // Navigate to grading
                return true;
            } else if (itemId == R.id.nav_profile) {
                startActivity(new Intent(this, ActivityProfile.class));
                return true;
            }
            return false;
        });
    }

    private void loadTeacherData() {
        // Set teacher name (you can get this from SharedPreferences or database)
        tvTeacherName.setText("Teacher Dashboard");
        
        // Load teacher statistics (replace with actual data from your database)
        tvTotalStudents.setText("156");
        tvTotalClasses.setText("8");
        tvPendingAssignments.setText("12");
    }
}
