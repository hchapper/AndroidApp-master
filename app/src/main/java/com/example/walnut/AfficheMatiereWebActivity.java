package com.example.walnut;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class AfficheMatiereWebActivity extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_affiche_matiere_web);

        Intent intent = getIntent ();
        String messageRecu = intent.getStringExtra(Intent.EXTRA_TEXT) ;
        WebView affichageWeb = findViewById (R.id.navigateur);

// if (messageRecu.equals IgnoreCase ("slam3")) // monDiffuseur.setText (this.getText (R.string.texte_sio));

        if (messageRecu.equalsIgnoreCase ("BLOC3")){
            affichageWeb.loadUrl ("file:///android_asset/BLOC3.html");

        }
        else if(messageRecu.equalsIgnoreCase("BLOC2")) {
            affichageWeb.loadUrl("file:///android_asset/BLOC2.html");
        }
        else if(messageRecu.equalsIgnoreCase ("BLOC1")){
            affichageWeb.loadUrl("file:///android_asset/BLOC1.html");
        }
        else if(messageRecu.equalsIgnoreCase ("Espagnol")){
            affichageWeb.loadUrl("file:///android_asset/Espagnol.html");
        }
        else if(messageRecu.equalsIgnoreCase ("Anglais")){
            affichageWeb.loadUrl("file:///android_asset/Anglais.html");
        }
        else if(messageRecu.equalsIgnoreCase ("Maths")){
            affichageWeb.loadUrl("file:///android_asset/Maths.html");
        }
            else affichageWeb.loadUrl ("file:///android_asset/erreur.html");
    }

}
