package com.equipe7.eductrack.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.equipe7.eductrack.Adapter.StudentAdapter;
import com.equipe7.eductrack.Auth.ActivityStudentRegister;
import com.equipe7.eductrack.R;
import com.equipe7.eductrack.Utils.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StudentDashboardActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private StudentAdapter adapter;
    private List<Student> studentList;
    private EditText etSearch;
    private Button btnCreateStudent;

    private DatabaseReference studentRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        recyclerView = findViewById(R.id.recyclerStudents);
        etSearch = findViewById(R.id.etSearchAdmin);
        btnCreateStudent = findViewById(R.id.btnCreateStudent);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        studentList = new ArrayList<>();
        adapter = new StudentAdapter(studentList);
        recyclerView.setAdapter(adapter);

        // Référence Firebase (Realtime Database)
        studentRef = FirebaseDatabase.getInstance().getReference("students");

        // Charger les étudiants
        loadStudents();

        // Barre de recherche
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        // Bouton pour créer un compte étudiant
        btnCreateStudent.setOnClickListener(v -> {
            Intent intent = new Intent(StudentDashboardActivity.this, ActivityStudentRegister.class);
            startActivity(intent);
        });
    }

    private void loadStudents() {
        studentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                studentList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Student student = ds.getValue(Student.class);
                    if (student != null) {
                        studentList.add(student);
                    }
                }
                adapter.notifyDataSetChanged(); // ⚡ rafraîchir la liste
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Gérer les erreurs (logs, toast, etc.)
            }
        });
    }
}
