package com.equipe7.eductrack.Auth;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.equipe7.eductrack.Firebase.FirebaseManager;
import com.equipe7.eductrack.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import okhttp3.*;

import java.io.IOException;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText etEmail;
    private Button btnSendOtp;
    private ProgressBar progressBar;

    private FirebaseFirestore db;

    // EmailJS constants
    private final String EMAILJS_SERVICE_ID = "service_yvl11d5";
    private final String EMAILJS_TEMPLATE_ID = "template_zlp263e";
    private final String EMAILJS_PUBLIC_KEY = "Un7snKzeE4AGeorc-";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        etEmail = findViewById(R.id.tilEmail);
        btnSendOtp = findViewById(R.id.btnSendResetEmail);
        progressBar = findViewById(R.id.progressBar);

        db = FirebaseManager.getInstance().getFirestore();

        btnSendOtp.setOnClickListener(v -> sendOtp());
    }

    private void sendOtp() {
        String email = etEmail.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        // 🔍 Rechercher le parent par champ "email"
        db.collection("parents")
                .whereEqualTo("email", email)
                .limit(1)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    if (!querySnapshot.isEmpty()) {
                        QueryDocumentSnapshot parentDoc = (QueryDocumentSnapshot) querySnapshot.getDocuments().get(0);
                        String otp = generateOtp();

                        // 📝 Sauvegarder OTP dans Firestore
                        parentDoc.getReference().update("otp", otp, "used", false, "timestamp", System.currentTimeMillis());

                        // 📧 Envoyer OTP par EmailJS
                        sendEmailJS(email, otp);
                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(this, "No parent account found with this email", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private String generateOtp() {
        int number = 100000 + new Random().nextInt(900000);
        return String.valueOf(number);
    }

    private void sendEmailJS(String email, String otp) {
        OkHttpClient client = new OkHttpClient();

        JSONObject json = new JSONObject();
        try {
            JSONObject templateParams = new JSONObject();
            templateParams.put("user_email", email);
            templateParams.put("user_otp", otp);

            json.put("service_id", EMAILJS_SERVICE_ID);
            json.put("template_id", EMAILJS_TEMPLATE_ID);
            json.put("user_id", EMAILJS_PUBLIC_KEY);
            json.put("template_params", templateParams);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(json.toString(), MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url("https://api.emailjs.com/api/v1.0/email/send")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ForgotPasswordActivity.this, "Failed to send OTP: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    if (response.isSuccessful()) {
                        Toast.makeText(ForgotPasswordActivity.this, "OTP sent to your email", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ForgotPasswordActivity.this, "Failed to send OTP", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
