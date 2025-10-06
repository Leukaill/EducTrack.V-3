package com.equipe7.eductrack.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
import com.equipe7.eductrack.R;

public class StudentDashboardActivity extends AppCompatActivity {

    private TextView tvStudentName, tvAttendanceRate, tvGradeAverage;
    private ImageView ivNotifications, ivProfile;
    private MaterialCardView cardCourses, cardHomework, cardExams, cardResults;
    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        initViews();
        setupClickListeners();
        loadStudentData();
    }

    private void initViews() {
        tvStudentName = findViewById(R.id.tvStudentName);
        tvAttendanceRate = findViewById(R.id.tvAttendanceRate);
        tvGradeAverage = findViewById(R.id.tvGradeAverage);
        ivNotifications = findViewById(R.id.ivNotifications);
        ivProfile = findViewById(R.id.ivProfile);
        cardCourses = findViewById(R.id.cardCourses);
        cardHomework = findViewById(R.id.cardHomework);
        cardExams = findViewById(R.id.cardExams);
        cardResults = findViewById(R.id.cardResults);
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

        // Courses card
        cardCourses.setOnClickListener(v -> {
            // Navigate to courses activity
            // Intent intent = new Intent(this, CoursesActivity.class);
            // startActivity(intent);
        });

        // Homework card
        cardHomework.setOnClickListener(v -> {
            Intent intent = new Intent(this, ActivityHomework.class);
            startActivity(intent);
        });

        // Exams card
        cardExams.setOnClickListener(v -> {
            Intent intent = new Intent(this, ActivityExams.class);
            startActivity(intent);
        });

        // Results card
        cardResults.setOnClickListener(v -> {
            // Navigate to results activity
            // Intent intent = new Intent(this, ResultsActivity.class);
            // startActivity(intent);
        });

        // Bottom Navigation
        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                // Already on home
                return true;
            } else if (itemId == R.id.nav_courses) {
                // Navigate to courses
                return true;
            } else if (itemId == R.id.nav_homework) {
                startActivity(new Intent(this, ActivityHomework.class));
                return true;
            } else if (itemId == R.id.nav_results) {
                // Navigate to results
                return true;
            } else if (itemId == R.id.nav_profile) {
                startActivity(new Intent(this, ActivityProfile.class));
                return true;
            }
            return false;
        });
    }

    private void loadStudentData() {
        // Set student name (you can get this from SharedPreferences or database)
        tvStudentName.setText("Student Dashboard");
        
        // Load student statistics (replace with actual data from your database)
        tvAttendanceRate.setText("95%");
        tvGradeAverage.setText("A-");
    }
}
