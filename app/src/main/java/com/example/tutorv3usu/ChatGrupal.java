package com.example.tutorv3usu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tutorv3usu.Adaptadores.TabsPagerAdapter;
import com.example.tutorv3usu.Chat.Buscar;
import com.example.tutorv3usu.Chat.Contenedor;
import com.example.tutorv3usu.Clases.ArchivosGrupo;
import com.example.tutorv3usu.Clases.msn;
import com.example.tutorv3usu.VistaAlumno.InfoGrupo;
import com.example.tutorv3usu.VistaTutor.Archivos;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatGrupal extends AppCompatActivity {
    EditText e1;
    TextView t1,t2;
    private String user_name,room_name;
    DatabaseReference reference;
    DatabaseReference reference2;
    DatabaseReference reference3;
    String temp_key;
    RecyclerView recycler;
Button btnsubir;

    private DatabaseReference getUserDatabaseReference;
    private FirebaseAuth mAuth;
ImageView imgbuton;

    private Toolbar mToolbarr;

    private StorageReference mStorageRef;
    private static final int READ_REQUEST_CODE = 1;

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private TabsPagerAdapter mTabsPagerAdapter;
    String iddegrupo,nombregrupo;
ImageView imgbutton;
EditText txtnamefile;
TextView idfile;
String nomnreusuario,tipousuario;
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_grupal);
        recycler = (RecyclerView) findViewById(R.id.recylermensaje);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        e1= (EditText)findViewById(R.id.editText2);
       // user_name=getIntent().getStringExtra("n" );

        mAuth = FirebaseAuth.getInstance();
        String user_id = mAuth.getCurrentUser().getUid();
        iddegrupo=getIntent().getStringExtra("idgrupo" );
        nombregrupo=getIntent().getStringExtra("namegrupo" );

     //   Uri uri;
        imgbuton=(ImageView)findViewById(R.id.idarchivos);
        imgbuton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);//ESTAS 3 COSAS
                intent.setType("*/*");
                startActivityForResult(intent, READ_REQUEST_CODE);
            }
        });

        mStorageRef = FirebaseStorage.getInstance().getReference();
        getUserDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(user_id);
        getUserDatabaseReference.keepSynced(true);
        getUserDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nomnreusuario = dataSnapshot.child("nombre").getValue().toString();
                tipousuario= dataSnapshot.child("tipo").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        reference2=  FirebaseDatabase.getInstance().getReference().child("ChatGrupal2").child(iddegrupo);
        reference3=  FirebaseDatabase.getInstance().getReference().child("ArchivoGrupo").child(iddegrupo);
        setTitle("Chat de " +nombregrupo);
        //mTabLayout = findViewById(R.id.main_tabs);
        //mTabLayout.setupWithViewPager(mViewPager);


        // room_name = getIntent().getExtras().get("room_name").toString();

        // ESTO LO COMENTE RECIEN PARA HCER EL CHAT 2
   /*     if (tipousuario=="Alumno") {
            reference3=FirebaseDatabase.getInstance().getReference().child("AlumnoTutor").child(user_id);
            reference3.keepSynced(true);
            reference3.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
*/
       // reference = FirebaseDatabase.getInstance().getReference().child("chat1");

    /*    reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
               append_chat(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                append_chat(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                append_chat(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        */


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menuchatgrupal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.inforgrupo){
            Toast.makeText(this, "hola 2", Toast.LENGTH_SHORT).show();
            Intent intent =  new Intent(ChatGrupal.this, InfoGrupo.class);
            Bundle bundle=new Bundle();
            bundle.putString("name2",nombregrupo);
            bundle.putString("g",iddegrupo);  // llevo el id del grupo
            intent.putExtras(bundle);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.idarchivos){
            Toast.makeText(this, "subit archivos", Toast.LENGTH_SHORT).show();
            Intent intent =  new Intent(ChatGrupal.this, Archivos.class);
            Bundle bundle=new Bundle();
            bundle.putString("name2",nombregrupo);
            bundle.putString("g2",iddegrupo);  // llevo el id del grupo
            intent.putExtras(bundle);
            startActivity(intent);
        }

        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 final Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Toast.makeText(this, "Apreto ok", Toast.LENGTH_SHORT).show();

            Toast.makeText(this, "apreto  ok", Toast.LENGTH_SHORT).show();

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = ChatGrupal.this.getLayoutInflater();
            builder.setTitle("subiendo archivo");
            View view2 = inflater.inflate(R.layout.dialogo_archivo, null);
            builder.setView(view2);
            builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                  //  filePath=resultData.getData();
                   // uploadFile();
                Uri uri=resultData.getData();


                    final StorageReference filepath=mStorageRef.child("documentos").child(uri.getLastPathSegment());

                    filepath.putFile(uri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if  (!task.isSuccessful()){
                        throw new Exception();

                     }
                    return filepath.getDownloadUrl();
                }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            Uri download=task.getResult();
                            String ruta;
                         //   TextView tx= findViewById(R.id.link);
                           // tx.setText(download.toString());
                            ruta=download.toString();
                          //  Log.e("archivo aver ",download.toString());
                            String id  = reference3.push().getKey();
                            ArchivosGrupo track = new ArchivosGrupo(id,txtnamefile.getText().toString(),download.toString(),"12/12/2019");
                            reference3.child(id).setValue(track);
                            Toast.makeText(getApplicationContext(), "GUARDO", Toast.LENGTH_SHORT).show();
                        }
                    });

                }




            });

            builder.show();

            txtnamefile=(EditText)view2.findViewById(R.id.idnombrearchivo);
            idfile=(TextView) view2.findViewById(R.id.idarchivo);


           // final StorageReference filepath=mStorageRef.child("documentos").child(uri.getLastPathSegment());
            /*
            filepath.putFile(uri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if  (!task.isSuccessful()){
                        throw new Exception();
                    }
                    return filepath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    Uri download=task.getResult();
                    TextView tx= findViewById(R.id.link);
                    tx.setText(download.toString());
                    Toast.makeText(getApplicationContext(), "GUARDO", Toast.LENGTH_SHORT).show();
                }
            });

             */
        }

    }

    private void uploadFile() {
        //if there is a file to upload
        if (filePath != null) {
          //  filePath=resultData.getData();
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Cargando");
            progressDialog.show();
         //   StorageReference filepath=mStorageRef.child("documentos").child(uri.getLastPathSegment());
            StorageReference riversRef = mStorageRef.child("documentos").child(filePath.getLastPathSegment());
            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //if the upload is successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                           //
                            //and displaying a success toast
                            Toast.makeText(getApplicationContext(), "Archivo subido ", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying error message
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {


                            UploadTask.TaskSnapshot download=task.getResult();
                         //   TextView tx= findViewById(R.id.link);
                           // tx.setText(download.toString());
                            Log.e("archivod",download.toString());

                            Toast.makeText(getApplicationContext(), download.toString(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            //displaying percentage in progress dialog
                            progressDialog.setMessage("Subido " + ((int) progress) + "%...");
                        }
                    });
        }
        //if there is not any file
        else {
            //you can display an error toast
        }
    }


    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<msn> recyclerOptions = new FirebaseRecyclerOptions.Builder<msn>()
                .setQuery(reference2, msn.class)
                .build();

        FirebaseRecyclerAdapter<msn,ChatsVH> adapter2 = new FirebaseRecyclerAdapter<msn, ChatsVH>(recyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull final ChatsVH holder, int position, @NonNull msn model) {

                final String userID = getRef(position).getKey();

                reference2.child(userID).addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){

                            final String userName = dataSnapshot.child("mensaje").getValue().toString();
                            final String name = dataSnapshot.child("nombre").getValue().toString();
                            holder.user_presence.setText(name);
                            holder.user_name.setText(userName);

                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });

                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @NonNull
            @Override
            public ChatsVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat, viewGroup, false);
                return new ChatsVH(view);
            }
        };

        recycler.setAdapter(adapter2);
        adapter2.startListening();

    }
    public static class ChatsVH extends RecyclerView.ViewHolder{
        TextView user_name, user_presence,tipo;

        public ChatsVH(View itemView) {
            super(itemView);
            user_presence= itemView.findViewById(R.id.nname);
            user_name = itemView.findViewById(R.id.txtmensaje);

        }
    }

    public void send(View v)
    {
        Map<String,Object> map = new HashMap<String,Object>();
        temp_key = reference2.push().getKey();
        reference2.updateChildren(map);

        DatabaseReference child_ref = reference2.child(temp_key);
        Map<String,Object> map2 = new HashMap<>();
        map2.put("nombre",nomnreusuario);
        map2.put("mensaje", e1.getText().toString());
        child_ref.updateChildren(map2).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        e1.setText("");

    }
    public void append_chat(DataSnapshot ss)
    {
        String chat_msg,chat_username;
        Iterator i = ss.getChildren().iterator();
        while(i.hasNext())
        {
            chat_msg = ((DataSnapshot)i.next()).getValue().toString();
            chat_username = ((DataSnapshot)i.next()).getValue().toString();

         //   t1.append(chat_username + ": " +chat_msg + " \n");
            t1.setText(chat_username);
        }
    }
}
