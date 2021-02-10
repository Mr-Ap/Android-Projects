package com.example.foodies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.foodies.Common.Common;
import com.example.foodies.Interface.ItemClickListener;
import com.example.foodies.ViewHolder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import Model.Food;

public class FoodList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference foodList;

    String categoryId="";

    FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        //Firebase

        database = FirebaseDatabase.getInstance();
        foodList = database.getReference("Food");

        recyclerView = (RecyclerView)findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //get intent here

        if(getIntent() != null)
            categoryId = getIntent().getStringExtra("CategoryId");
        if(!categoryId.isEmpty() && categoryId!=null)
        {
            if(Common.isConnectedToInterner(getBaseContext()))
                loadListFood(categoryId);
            else {
                Toast.makeText(FoodList.this,"Please Check your Internet Connection..!",Toast.LENGTH_SHORT).show();
                return;
            }
        }



    }

    private void loadListFood(String categoryId) {
        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(Food.class,R.layout.food_item,FoodViewHolder.class,foodList.orderByChild("MenuId").equalTo(categoryId)) {
            //like : select * from food rare MenuId =
            @Override

            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {
                viewHolder.food_name.setText(model.getName());

                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.food_image);

                final Food local = model;

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Toast.makeText(FoodList.this,""+local.getName(),Toast.LENGTH_SHORT).show();
                        //Start new Activity
                        Intent foodDetail=new Intent(FoodList.this,FoodDetail.class);
                        foodDetail.putExtra("FoodId",adapter.getRef(position).getKey());
                        startActivity(foodDetail);

                    }
                });
            }
        };
        //setadapter

        //Log.d("TAG",""+adapter.getItemCount());

        recyclerView.setAdapter(adapter);
    }
}
