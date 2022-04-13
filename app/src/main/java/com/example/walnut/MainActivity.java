package com.example.walnut;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "slam4";
    private CompetenceViewModel uneCompetenceViewModel;
    public static final int NEW_COMPETENCE_ACTIVITY_REQUEST_CODE = 1;
    public static final int AFFICHE_MATIERE_ACTIVITY_REQUEST_CODE = 1;

    private RecyclerView recyclerView ;
    private ItemTouchHelper monHelper ;
    public CompetenceListAdapter adapter;
    //WebView affichageWeb = findViewById (R.id.navigateur);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        monHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback ( ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove (@NonNull RecyclerView recyclerView,
                                   @NonNull RecyclerView.ViewHolder origine,
                                   @NonNull RecyclerView. ViewHolder destination) {
                int position_origin = origine.getAdapterPosition ();
                int position_destination = destination.getAdapterPosition ();
                adapter.moveItem (position_origin, position_destination);
                //adapter.notify ItemMoved (position_origin, position_destination);
                return true;
            }

            @Override
            public boolean isLongPressDragEnabled() {
                return true;
            }

            @Override
            public void onSwiped (@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position=viewHolder.getAdapterPosition();
                Competence maCompetence = adapter.getCompetenceALaPosition(position);

                if (direction == ItemTouchHelper.LEFT ) {

                    Toast.makeText(MainActivity.this, "Suppression de " + maCompetence.getNomCompetence(), Toast.LENGTH_LONG).show();
                    uneCompetenceViewModel.deleteCompetence(maCompetence);
                }
                else if (direction == ItemTouchHelper.RIGHT){
                    Toast.makeText(MainActivity.this, "Affichage éventuel du contenu de " + maCompetence.getNomCompetence(), Toast.LENGTH_LONG).show();


                    Intent intent = new Intent(MainActivity.this, AfficheMatiereWebActivity.class);
                    intent.putExtra(Intent.EXTRA_TEXT   , maCompetence.getNomCompetence());
                    startActivityForResult(intent, AFFICHE_MATIERE_ACTIVITY_REQUEST_CODE);

                }
            }
        });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NouvelleCompetence.class);
                startActivityForResult(intent, NEW_COMPETENCE_ACTIVITY_REQUEST_CODE);


            }
        });

        uneCompetenceViewModel = new ViewModelProvider(this).get(CompetenceViewModel.class);
        uneCompetenceViewModel.getMesCompetences().observe(this, competences -> {
            adapter.setMesCompetences(competences);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.supprime_toutes_competences) {
            Toast.makeText ( this, "Attention on supprime tout nous ...", Toast.LENGTH_SHORT).show ();
            uneCompetenceViewModel.supprime_Tout ();

            return true;

        } else if(id == R.id.page2){
            Toast.makeText(this,"On va ouvrir une autre page",Toast.LENGTH_SHORT).show();
            //affichageWeb.loadUrl("file:///android_asset/BLOC2.html");
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStart(){
        super.onStart();
        recyclerView = findViewById(R.id.recyclerview);
        adapter = new CompetenceListAdapter(this);
        monHelper.attachToRecyclerView (recyclerView);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStop () {
        super.onStop();
        for (int i = 0; i < adapter.getItemCount(); i++) {

            Competence compASauvegarder = adapter.getCompetenceALaPosition(i);
            //compASauvegarder.setOrdreAffiche(i);
            uneCompetenceViewModel.update(compASauvegarder);
        }
    }

    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_COMPETENCE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            Competence competence = new Competence(data.getStringExtra(NouvelleCompetence.EXTRA_REPLY));
            uneCompetenceViewModel.insert(competence);

        } else {
            Toast.makeText(getApplicationContext(),"Competence vide: non sauvegardé",Toast.LENGTH_LONG).show();
        }
    }
}


/*try {

                    // Recevoir le fichier
                    File f = new File("..contenuCompetence" + Competence.getNomCompetence());

                    // Créer un nouveau fichier
                    // Vérifier s'il n'existe pas
                    if (f.createNewFile())
                        System.out.println("File created");
                    else
                        System.out.println("File already exists");
                }
                catch (Exception e) {
                    System.err.println(e);
                }*/