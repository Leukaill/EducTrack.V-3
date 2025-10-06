package com.equipe7.eductrack.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.equipe7.eductrack.Auth.AdminLoginActivity;
import com.equipe7.eductrack.R;
import com.equipe7.eductrack.models.UserModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdminProfileActivity extends AppCompatActivity {

    private ImageView profileImage;
    private TextView tvName, tvRole;
    private TextInputEditText etUsername, etEmail;
    private Button btnUpdateInfo, btnLogout;

    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private FirebaseUser currentUser;

    private boolean isEditing = false; // mode édition

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminprofil);

        // Initialisation des vues
        profileImage = findViewById(R.id.profileImage);
        tvName = findViewById(R.id.tvName);
        tvRole = findViewById(R.id.tvRole);
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        btnUpdateInfo = findViewById(R.id.btnUpdateInfo);
        btnLogout = findViewById(R.id.btnLogout);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        currentUser = auth.getCurrentUser();

        if (currentUser != null) {
            loadAdminProfile(currentUser.getUid());
        }

        // Bouton Update Info
        btnUpdateInfo.setOnClickListener(v -> {
            if (!isEditing) {
                // Activer le champ username
                etUsername.setEnabled(true);
                btnUpdateInfo.setText("Save");
                isEditing = true;
            } else {
                // Sauvegarder dans Firestore
                saveAdminProfile();
            }
        });

        // Bouton Logout
        btnLogout.setOnClickListener(v -> {
            auth.signOut();
            Toast.makeText(this, "Déconnexion réussie", Toast.LENGTH_SHORT).show();

            // Redirection vers AdminLoginActivity et suppression de la pile
            Intent intent = new Intent(AdminProfileActivity.this, AdminLoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void loadAdminProfile(String uid) {
        db.collection("users").document(uid).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        UserModel user = documentSnapshot.toObject(UserModel.class);
                        if (user != null) {
                            tvName.setText(user.getName());
                            tvRole.setText(user.getRole());
                            etUsername.setText(user.getName());
                            etEmail.setText(user.getEmail());
                        }
                    }
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Erreur : " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }

    private void saveAdminProfile() {
        String newName = etUsername.getText().toString().trim();

        if (currentUser != null) {
            db.collection("users").document(currentUser.getUid())
                    .update("name", newName)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Profil mis à jour", Toast.LENGTH_SHORT).show();
                        etUsername.setEnabled(false);
                        btnUpdateInfo.setText("Update Info");
                        isEditing = false;
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "Erreur : " + e.getMessage(), Toast.LENGTH_SHORT).show()
                    );
        }
    }
}
