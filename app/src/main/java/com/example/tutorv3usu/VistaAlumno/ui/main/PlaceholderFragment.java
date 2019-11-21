package com.example.tutorv3usu.VistaAlumno.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.tutorv3usu.Fragment.ChatsFragment;
import com.example.tutorv3usu.Fragment.ChatsFragment2;
import com.example.tutorv3usu.Fragment.RequestsFragment;
import com.example.tutorv3usu.FragmentAlumno.ChatFragment;
import com.example.tutorv3usu.FragmentAlumno.ReunionesFragment;
import com.example.tutorv3usu.FragmentAlumno.SocialFragment;
import com.example.tutorv3usu.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;

  /*  public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }*/
  public static Fragment newInstance(int index) {
      Fragment fragment=null;

      switch (index){
          case 1:
              fragment =new ChatsFragment2();
              break;
          case 2:
              fragment =new ReunionesFragment();
              break;
          case 3:
              fragment =new SocialFragment();
              break;
          case 4:
              fragment =new RequestsFragment();
              break;
      }
      return fragment;
  }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        final TextView textView = root.findViewById(R.id.section_label);
        pageViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}