package com.example.favouritelistapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.text.InputType;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.favouritelistapp.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private RecyclerView categoryRecyclerView;
    private CategoryManager mCategoryManager = new CategoryManager(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        ArrayList<Category> categories = mCategoryManager.retrieveCategory();

        categoryRecyclerView = findViewById(R.id.category_recyclerview);
        categoryRecyclerView.setAdapter(new CategoryRecyclerAdapter(categories));
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> displayCreateCategoryDialog());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void displayCreateCategoryDialog(){

//        String alertTitle = "Enter the name of the category";
//        String positiveButtonTitle = "Create";
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        EditText categoryEditText = new EditText(this);
        categoryEditText.setInputType(InputType.TYPE_CLASS_TEXT);
        alertBuilder.setTitle(R.string.alertTitle);
        alertBuilder.setView(categoryEditText);


        alertBuilder.setPositiveButton(R.string.positiveButtonTite, (dialogInterface, i) -> {

            Category category = new Category(categoryEditText.getText().toString(), new ArrayList<String>());
            mCategoryManager.saveCategory(category);
            CategoryRecyclerAdapter categoryRecyclerAdapter = (CategoryRecyclerAdapter) categoryRecyclerView.getAdapter();
            CategoryRecyclerAdapter.addCategory(category);
            dialogInterface.dismiss();
        });
        alertBuilder.create().show();

    }

}