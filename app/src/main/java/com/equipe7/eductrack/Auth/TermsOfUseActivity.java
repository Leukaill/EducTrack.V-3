package com.equipe7.eductrack.Auth;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.equipe7.eductrack.Firebase.FirebaseManager;
import com.equipe7.eductrack.R;

import java.util.HashMap;
import java.util.Map;

public class TermsOfUseActivity extends AppCompatActivity {

    private TextView tvTerms;
    private Button btnAccept, btnDecline;

    private FirebaseManager firebaseManager;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_of_use);

        // Initialisation des vues
        tvTerms = findViewById(R.id.tv_terms);
        btnAccept = findViewById(R.id.btn_accept);
        btnDecline = findViewById(R.id.btn_decline); // ✅ AJOUTÉ

        firebaseManager = FirebaseManager.getInstance();

        // Texte formaté des conditions
        tvTerms.setText(Html.fromHtml(getFormattedTerms()));

        // Accepter
        btnAccept.setOnClickListener(view -> saveAcceptanceToFirebase(true));

        // Refuser
        btnDecline.setOnClickListener(view -> {
            Intent result = new Intent();
            result.putExtra("accepted", false);
            setResult(RESULT_OK, result);
            finish();
        });
    }

    private String getFormattedTerms() {
        return "<font color='red'><b>📘 1. Terms of Use (Conditions of Use)</b></font><br><br>" +
                "EduTrack – User Terms & Conditions<br>" +
                "Last updated: July 2025<br>" +
                "Welcome to EduTrack...<br><br>" +

                "<font color='blue'><b>1. Acceptance of Terms</b></font><br>" +
                "By accessing or using EduTrack, you agree to comply...<br><br>" +

                "<font color='blue'><b>2. User Roles and Access</b></font><br>" +
                "EduTrack supports three types of users...<br><br>" +

                "<font color='blue'><b>3. Account Responsibility</b></font><br>" +
                "Users must provide accurate info...<br><br>" +

                "<font color='blue'><b>4. Acceptable Use</b></font><br>" +
                "You agree not to impersonate...<br><br>" +

                "<font color='blue'><b>5. Modifications</b></font><br>" +
                "EduTrack reserves the right to update terms...<br><br>" +

                "<font color='blue'><b>6. Limitation of Liability</b></font><br>" +
                "EduTrack is provided “as is”…";
    }

    private void saveAcceptanceToFirebase(boolean accepted) {
        if (firebaseManager.getCurrentUser() == null) {
            Toast.makeText(this, "Utilisateur non connecté", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = firebaseManager.getCurrentUser().getUid();
        Map<String, Object> data = new HashMap<>();
        data.put("acceptedTerms", accepted);

        firebaseManager.updateUserData(userId, data)
                .addOnSuccessListener(unused -> {
                    // Retourner résultat à ActivityStudentRegister
                    Intent result = new Intent();
                    result.putExtra("accepted", accepted);
                    setResult(RESULT_OK, result);
                    finish();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Erreur lors de l'enregistrement", Toast.LENGTH_SHORT).show()
                );
    }
}
