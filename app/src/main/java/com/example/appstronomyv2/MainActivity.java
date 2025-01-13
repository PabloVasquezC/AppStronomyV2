package com.example.appstronomyv2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.room.Room;

import com.example.appstronomyv2.data.database.AppDatabase;
import com.example.appstronomyv2.data.database.DatabaseClient;
import com.example.appstronomyv2.ui.apod.ApodFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import com.example.appstronomyv2.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    // Instancia de la base de datos
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String userEmail = getIntent().getStringExtra("USER_EMAIL");  // Obtener el email

        Toast.makeText(this, "Bienvenido " + userEmail, Toast.LENGTH_SHORT).show();

        // Inicializa Room
        appDatabase = DatabaseClient.getInstance(this).getAppDatabase();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)  // Asegúrate de incluir el ID de ApodFragment
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Configura el listener de navegación para capturar la selección del menú
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Verifica si el item seleccionado es el de Apod
        if (id == R.id.nav_gallery) {
            String userEmail = getIntent().getStringExtra("USER_EMAIL");

            // Crear y pasar datos al fragmento Apod
            ApodFragment apodFragment = new ApodFragment();
            Bundle bundle = new Bundle();
            bundle.putString("USER_EMAIL", userEmail);
            apodFragment.setArguments(bundle);

            // Navegar a ApodFragment utilizando el NavController
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
            navController.navigate(R.id.nav_gallery, bundle); // Aquí navegas con el Bundle

            // Cierra el DrawerLayout después de seleccionar un item
            DrawerLayout drawer = binding.drawerLayout;
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

        // Maneja navegación a otros fragmentos si es necesario
        // ...

        DrawerLayout drawer = binding.drawerLayout;
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.logout) { // Verifica si el botón "Logout" fue seleccionado
            logout(); // Llama a la función para cerrar sesión
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        // Limpia las preferencias compartidas o cualquier sesión activa
        // Por ejemplo:
        // SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        // preferences.edit().clear().apply();

        // Redirige al usuario a la pantalla de LoginActivity
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        // Finaliza la MainActivity para evitar volver atrás
        finish();
    }
}
