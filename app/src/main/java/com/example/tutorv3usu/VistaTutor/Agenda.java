package com.example.tutorv3usu.VistaTutor;

import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.tutorv3usu.FragmentTutor.AlumnosFragment;
import com.example.tutorv3usu.FragmentTutor.Cursos_Tutores_Fragment;
import com.example.tutorv3usu.FragmentTutor.Eventos_Fragment;
import com.example.tutorv3usu.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class Agenda extends AppCompatActivity implements Eventos_Fragment.OnFragmentInteractionListener,Cursos_Tutores_Fragment.OnFragmentInteractionListener, AlumnosFragment.OnFragmentInteractionListener {
    private FragmentManager fragmentManager;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.eventos:
                    Eventos_Fragment eventos = new Eventos_Fragment();
                  //  Bundle bundle = getIntent().getExtras();
                    //String texto = bundle.getString("code4");
                    //Bundle args = new Bundle();
                    //args.putString("code41", texto);
                    //eventos.setArguments(args);

                    Cargar(eventos,fragmentManager);
                    return true;
                case R.id.otro1:
                    Cursos_Tutores_Fragment cursos  = new Cursos_Tutores_Fragment();
                    Cargar(cursos,fragmentManager);
                    return true;
                case R.id.otro2:
                    AlumnosFragment alumnos  = new AlumnosFragment();
                    Cargar(alumnos,fragmentManager);
                    return true;
            }
            return false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        BottomNavigationView navView = findViewById(R.id.nav_view1);
        fragmentManager = getSupportFragmentManager();
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        String fragmen,fragment3;
        fragmen=getIntent().getStringExtra("btn1" );
        fragment3=getIntent().getStringExtra("btn2");
        if (fragment3!=null){
            AlumnosFragment fragment2 = new AlumnosFragment();
            //   fragment.setArguments(args);
         //   mOnNavigationItemSelectedListener.onNavigationItemSelected(onMenuItemSelected());
            getSupportFragmentManager().beginTransaction().replace(R.id.idcontainerfragment2,fragment2).commit();
        }
        if (fragmen!=null){
            Cursos_Tutores_Fragment fragment4 = new Cursos_Tutores_Fragment();
            //   fragment.setArguments(args);
            getSupportFragmentManager().beginTransaction().replace(R.id.idcontainerfragment2,fragment4).commit();
            Toast.makeText(this, "f"+ fragmen, Toast.LENGTH_SHORT).show();
        }

      Eventos_Fragment fragment = new Eventos_Fragment();
      //  fragment.setArguments(args);
       getSupportFragmentManager().beginTransaction().replace(R.id.idcontainerfragment2,fragment).commit();




    }




    public void Cargar(Fragment f1, FragmentManager fm){
        FragmentTransaction ft= fm.beginTransaction();
        ft.replace(R.id.idcontainerfragment2,f1).commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
