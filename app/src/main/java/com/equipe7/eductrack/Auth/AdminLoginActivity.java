package com.equipe7.eductrack.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.equipe7.eductrack.Activity.AdminHomeActivity;
import com.equipe7.eductrack.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdminLoginActivity extends AppCompatActivity {

    private TextInputEditText etEmail, etPassword;
    private MaterialButton btnLogin;
    private ProgressBar progressBar;
    private TextView tvForgot, tvRegister;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        // --- Initialisation UI ---
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        progressBar = findViewById(R.id.progressBar);
        tvForgot = findViewById(R.id.tvForgot);
        tvRegister = findViewById(R.id.tvRegister);

        // --- Firebase ---
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // --- Bouton login admin ---
        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                etEmail.setError("Email required");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                etPassword.setError("Password required");
                return;
            }

            loginAdmin(email, password);
        });


        // --- Forgot password ---
        if (tvForgot != null) {
            tvForgot.setOnClickListener(v -> {
                Intent intent = new Intent(AdminLoginActivity.this, AdminForgotPassword.class);
                startActivity(intent);
            });
        }


        // --- Register link ---
        if (tvRegister != null) {
            tvRegister.setOnClickListener(v -> {
                Toast.makeText(this, "Redirect to registration page", Toast.LENGTH_SHORT).show();
                 startActivity(new Intent(this, CreateAdminAccountActivity.class));
            });
        }
    }

    private void loginAdmin(String email, String password) {
        progressBar.setVisibility(View.VISIBLE);
        btnLogin.setEnabled(false);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    progressBar.setVisibility(View.GONE);
                    btnLogin.setEnabled(true);

                    if (task.isSuccessful()) {
                        String uid = mAuth.getCurrentUser().getUid();

                        // Vérifie si l'utilisateur est un admin
                        db.collection("admins").document(uid).get()
                                .addOnSuccessListener(documentSnapshot -> {
                                    if (documentSnapshot.exists()) {
                                        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(AdminLoginActivity.this, AdminHomeActivity.class));
                                        finish(); // ferme l’écran de login
                                    } else {
                                        Toast.makeText(this, "Access denied: Not an admin", Toast.LENGTH_LONG).show();
                                        mAuth.signOut();
                                    }
                                })
                                .addOnFailureListener(e ->
                                        Toast.makeText(this, "Firestore error: " + e.getMessage(), Toast.LENGTH_LONG).show()
                                );
                    } else {
                        Toast.makeText(this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
