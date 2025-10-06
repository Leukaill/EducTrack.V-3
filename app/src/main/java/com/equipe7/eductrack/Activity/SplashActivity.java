package com.equipe7.eductrack.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.equipe7.eductrack.Auth.LoginActivity;
import com.equipe7.eductrack.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SplashActivity extends AppCompatActivity {

    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        videoView = findViewById(R.id.videoView);

        // Charger la vidéo depuis res/raw
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.eductrack);
        videoView.setVideoURI(videoUri);

        // Ne pas boucler la vidéo
        videoView.setOnPreparedListener(mp -> mp.setLooping(false));

        // Quand la vidéo se termine, vérifier l'utilisateur
        videoView.setOnCompletionListener(mp -> checkUserLoggedIn());

        // Démarrer la vidéo
        videoView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (videoView != null && videoView.isPlaying()) {
            videoView.stopPlayback(); // ✅ libère le buffer quand on quitte l'écran
        }
    }

    /**
     * Vérifie si un utilisateur est déjà connecté
     * et le redirige vers l'écran correspondant.
     */
    private void checkUserLoggedIn() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            goToLogin();
            return;
        }

        FirebaseFirestore.getInstance().collection("users")
                .document(currentUser.getUid())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String role = documentSnapshot.getString("role");
                        if ("admin".equals(role)) {
                            goToActivity(AdminHomeActivity.class);
                        } else if ("parent".equals(role)) {
                            goToActivity(ParentHomeActivity.class);
                        } else {
                            goToActivity(TeacherHomeActivity.class);
                        }
                    } else {
                        goToLogin();
                    }
                })
                .addOnFailureListener(e -> goToLogin());
    }

    private void goToLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void goToActivity(Class<?> cls) {
        startActivity(new Intent(this, cls));
        finish();
    }
}
