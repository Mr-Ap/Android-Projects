package com.example.foodies;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodies.Common.Common;
import com.example.foodies.ViewHolder.CartAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import Model.Category;
import Model.Order;
import Model.Request;
import java.lang.String;

import com.example.foodies.Database.Database;

public class Cart extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;

    TextView txtTotalPrice;
    Button btnPlace;
    List<Order> cart = new ArrayList<>();

    static int total;
    CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //Firebase

        database = FirebaseDatabase.getInstance();
        requests= database.getReference("Requests");

        //Init
        recyclerView = (RecyclerView)findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice = (TextView)findViewById(R.id.total);

        btnPlace = (Button)findViewById(R.id.btnPlaceOrder);

        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cart.size() == 0) {
                    Toast.makeText(Cart.this, "Select at least one food item...", Toast.LENGTH_SHORT).show();
                }
                else {
                    //Create new Request
                    showAlertDialog();
                }
            }
        });

        loadListFood();


    }

    private void showAlertDialog() {

        AlertDialog.Builder alertDialog=new AlertDialog.Builder(Cart.this);
        alertDialog.setTitle("One More Step..!");
        alertDialog.setMessage("Add Special Cooking Instruction");

        final EditText edtAddress=new EditText(Cart.this);
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        edtAddress.setLayoutParams(lp);
        alertDialog.setView(edtAddress);
        alertDialog.setIcon(R.drawable.ic_restaurant_black_24dp);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              ProgressDialog progressDialog=new ProgressDialog(Cart.this);
                progressDialog.setMessage("Loading..!");
                progressDialog.show();
               Request request=new Request(
                        Common.currentUser.getPhone(),
                        Common.currentUser.getName(),
                        edtAddress.getText().toString(),
                        txtTotalPrice.getText().toString(),
                        cart
                );

                //Submit to Firebase
                //We will using Sysytem.currentMilli to key

                requests.child(String.valueOf(System.currentTimeMillis())).setValue(request);


                // startActivity(new Intent(Cart.this,upi_payment.class));
                //Delete Cart
               new Database (getBaseContext()).cleanCart();
                progressDialog.dismiss();
             //  startActivity(new Intent(Cart.this,upi_payment.class));
                Toast.makeText(Cart.this,"Thank you , Order Placed..!",Toast.LENGTH_SHORT).show();
               startActivity(new Intent(Cart.this,Home.class));

            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        alertDialog.show();
    }

    private void loadListFood() {

        cart = new Database(this).getCarts();
        adapter = new CartAdapter(cart,this);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        //calculate total price
        total=0;
        for(Order order:cart)
             total+=(Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));
        Locale locale = new Locale("en","IN");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        txtTotalPrice.setText(fmt.format(total));
        //tot=txtTotalPrice.getText().toString();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if(item.getTitle().equals(Common.DELETE))
            deleteCart(item.getOrder());
        return true;
    }

    private void deleteCart(int position) {

        //We will remove item at List<Order> by Position
        cart.remove(position);
        //After that,We will delete all old data from SQlite
        new Database(this).cleanCart();
        //We will Update new Data from List<Order> from SQlite
        for(Order item:cart)
            new Database(this).addToCart(item);
        //Refresh
        loadListFood();
    }
}
