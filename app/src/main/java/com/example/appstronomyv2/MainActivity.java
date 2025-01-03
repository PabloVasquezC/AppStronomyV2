package com.example.appstronomyv2;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.appstronomyv2.data.database.AppDatabase;
import com.example.appstronomyv2.data.database.DatabaseClient;
import com.example.appstronomyv2.data.model.UserPreference;

import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import com.example.appstronomyv2.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    // Instancia de la base de datos
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inicializa Room
        appDatabase = DatabaseClient.getInstance(this).getAppDatabase();


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Ejemplo de insertar un nuevo UserPreference en un hilo de fondo
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        UserPreference userPreference = new UserPreference();
                        userPreference.setItemId("item123");
                        userPreference.setLiked(true);

                        // Inserta el objeto en la base de datos
                        appDatabase.userPreferenceDao().insert(userPreference);

                        // Consulta todos los elementos de la base de datos (en otro hilo de fondo)
                        List<UserPreference> allPreferences = appDatabase.userPreferenceDao().getAllPreferences();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // Aquí puedes actualizar la UI con los resultados de la consulta
                                Toast.makeText(MainActivity.this, "Número de preferencias: " + allPreferences.size(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).start();
            }
        });

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
