package com.example.tutorv3usu.Chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tutorv3usu.Clases.Usuaros;
import com.example.tutorv3usu.Info.Perfil;
import com.example.tutorv3usu.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Buscar extends AppCompatActivity {


    private Toolbar toolbar;
    private EditText searchInput;
    private ImageView backButton;
    private TextView notFoundTV;

    private RecyclerView peoples_list;
    private DatabaseReference peoplesDatabaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);
        toolbar = findViewById(R.id.search_appbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater layoutInflater = (LayoutInflater)
                this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.appbar_search, null);
        actionBar.setCustomView(view);

        searchInput = findViewById(R.id.serachInput);
        notFoundTV = findViewById(R.id.notFoundTV);
        backButton = findViewById(R.id.backButton);

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchPeopleProfile(searchInput.getText().toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Setup recycler view
        peoples_list = findViewById(R.id.SearchList);
        peoples_list.setHasFixedSize(true);
        peoples_list.setLayoutManager(new LinearLayoutManager(this));

        peoplesDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Usuarios");
        peoplesDatabaseReference.keepSynced(true); // for offline     searchInput = findViewById(R.id.serachInput);


    }

    private void searchPeopleProfile(final String searchString) {
        final Query searchQuery = peoplesDatabaseReference.orderByChild("nombre")
                .startAt(searchString).endAt(searchString + "\uf8ff");
        //final Query searchQuery = peoplesDatabaseReference.orderByChild("search_name").equalTo(searchString);

        FirebaseRecyclerOptions<Usuaros> recyclerOptions = new FirebaseRecyclerOptions.Builder<Usuaros>()
                .setQuery(searchQuery, Usuaros.class)
                .build();

        FirebaseRecyclerAdapter<Usuaros, SearchPeopleVH> adapter = new FirebaseRecyclerAdapter<Usuaros, SearchPeopleVH>(recyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull SearchPeopleVH holder, final int position, @NonNull Usuaros model) {
                holder.name.setText(model.getNombre());
                holder.status.setText(model.getApellido());
                //  final  String celular =model.getTelefono();

                Picasso.get()
                        .load(model.getUser_image())
                        .networkPolicy(NetworkPolicy.OFFLINE) // for Offline
                        .placeholder(R.drawable.default_profile_image)
                        .into(holder.profile_pic);

                holder.verified_icon.setVisibility(View.GONE);
                if (model.getVerified().contains("true")){
                    holder.verified_icon.setVisibility(View.VISIBLE);
                } else {
                    holder.verified_icon.setVisibility(View.GONE);
                }

                /**on list >> clicking item, then, go to single user profile*/
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String visit_user_id = getRef(position).getKey();
                        Intent intent = new Intent(Buscar.this, Perfil.class);
                        intent.putExtra("visitUserId", visit_user_id);
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public SearchPeopleVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_single_profile_display, viewGroup, false);
                return new SearchPeopleVH(view);
            }
        };
        peoples_list.setAdapter(adapter);
        adapter.startListening();
    }
    public static class SearchPeopleVH extends RecyclerView.ViewHolder{
        TextView name, status;
        CircleImageView profile_pic;
        ImageView verified_icon;
        public SearchPeopleVH(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.all_user_name);
            status = itemView.findViewById(R.id.all_user_status);
            profile_pic = itemView.findViewById(R.id.all_user_profile_img);
            verified_icon = itemView.findViewById(R.id.verifiedIcon);
        }
    }
}
