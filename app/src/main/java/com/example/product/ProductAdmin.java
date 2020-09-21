package com.example.product;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProductAdmin extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageButton btnDelete;
    TextView txtID;
    Query query1;
    private DatabaseReference mdatabasereference;
    private ProgressDialog progressDialog;
    FirebaseRecyclerAdapter<Product, BlogViewHolder> firebaseRecyclerAdapter;
    LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_admin);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressDialog = new ProgressDialog(ProductAdmin.this);
        progressDialog.setMessage("Loading Products Please Wait...");
        progressDialog.show();

        mdatabasereference = FirebaseDatabase.getInstance().getReference("products").child("accessories");

        recyclerView = (RecyclerView) findViewById(R.id.adminRecyclerViewGridView);

        btnDelete = (ImageButton) findViewById(R.id.adminpDelete);
        txtID = (TextView) findViewById(R.id.adminpID);

        //String pID = txtID.getText().toString();

//        btnDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DatabaseReference delRef = FirebaseDatabase.getInstance().getReference().child("products").child("accessories");
//                delRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        if(dataSnapshot.hasChild(txtID.getText().toString())){
//                            mdatabasereference = FirebaseDatabase.getInstance().getReference().child("products").child("accessories").child(txtID.getText().toString());
//                            mdatabasereference.removeValue();
//
//                            Toast.makeText(getApplicationContext(), "Deleted Successfully!!", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//            }
//        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductAdmin.this, AddNewProduct.class);
                startActivity(intent);

//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        query1 = FirebaseDatabase.getInstance().getReference().child("products").child("accessories");
        FirebaseRecyclerOptions<Product> options =
                new FirebaseRecyclerOptions.Builder<Product>()
                        .setQuery(query1, Product.class)
                        .build();

        //Log.d("Options"," data : "+options);

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Product, BlogViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull BlogViewHolder blogViewHolder, final int i, @NonNull Product product_get_set_v) {

                //blogViewHolder.setname(product_get_set_v.getName());
                blogViewHolder.setid(product_get_set_v.getId());
                blogViewHolder.settitle(product_get_set_v.getTitle());
                blogViewHolder.setprice(product_get_set_v.getPrice());
                blogViewHolder.setsize(product_get_set_v.getSize());
                blogViewHolder.setcolor(product_get_set_v.getColor());
                blogViewHolder.setdescription(product_get_set_v.getDescription());
                String image_url =blogViewHolder.setimage(product_get_set_v.getImage());

                //String link= product_get_set_v.getLink();
                //Log.d("LINKDATA"," data : "+link);

                blogViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String productid=getRef(i).getKey();
                        //Log.d("productid"," data : "+productid);


                        mdatabasereference.child(productid).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String finallink = dataSnapshot.child("link").getValue(String.class);
                                //Log.d("productLink"," data : "+finallink);

//                                if(finallink!=null)
//                                {
//                                    Uri uriUrl = Uri.parse(finallink);
//                                    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
//                                    startActivity(launchBrowser);
//                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }
                });
            }

            @NonNull
            @Override
            public BlogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.activity_product_admin_card, parent, false);
                progressDialog.dismiss();
                return new BlogViewHolder(view);
            }
        };
        firebaseRecyclerAdapter.startListening();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),1);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class BlogViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public BlogViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
        }

//        public void setname(String name)
//        {
//            TextView ename=(TextView)mView.findViewById(R.id.admintext1);
//            ename.setText(name);
//
//        }

        public void setid(String id)
        {
            TextView pid=(TextView)mView.findViewById(R.id.adminpID);
            pid.setText(id);

        }

        public void settitle(String title)
        {
            TextView ptitle=(TextView)mView.findViewById(R.id.adminpTitle);
            ptitle.setText(title);

        }

        public void setprice(Float price)
        {
            TextView pprice=(TextView)mView.findViewById(R.id.adminpPrice);
            pprice.setText(String.valueOf(price));

        }

        public void setsize(String size)
        {
            TextView psize=(TextView)mView.findViewById(R.id.adminpSize);
            psize.setText(size);

        }

        public void setcolor(String color)
        {
            TextView pcolor=(TextView)mView.findViewById(R.id.adminpColors);
            pcolor.setText(color);

        }

        public void setdescription(String description)
        {
            TextView pdescription=(TextView)mView.findViewById(R.id.adminpDescription);
            pdescription.setText(description);

        }

        public String setimage(String url)
        {
            ImageView image = (ImageView)mView.findViewById(R.id.adminpimage);
            Picasso.get().load(url).into(image);
            return url;
        }
    }

}
