package com.example.softwareengineering;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;


public class ToDoMain extends AppCompatActivity {

    private List<ToDo> toDoList = new ArrayList<>();
    private ToDoAdapter toDoAdapter;
    DatabaseHandler db;
    private RecyclerView recyclerView;
    private TextView noToDoText;
    private ImageView noToDoIcon;
    private boolean  _doubleBackToExitPressedOnce    = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_main);
        recyclerView = (RecyclerView) findViewById(R.id.LIST);
        noToDoText= (TextView) findViewById(R.id.noToDoText);
        noToDoIcon= (ImageView) findViewById(R.id.noToDoIcon);

        db = new DatabaseHandler(this);
        toDoList = db.getAllToDo();

        if (toDoList.size() == 0) {
            Toast.makeText(ToDoMain.this, "No To Do found", Toast.LENGTH_SHORT).show();
        }
        else
        {
            noToDoText.setVisibility(View.GONE);
            noToDoIcon.setVisibility(View.GONE);
        }

        toDoAdapter = new ToDoAdapter(toDoList, db, this, recyclerView, toDoAdapter);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(toDoAdapter);

    }

    public void addNewToDo(View view) {
        Intent intent = new Intent(ToDoMain.this, NewToDo.class);
        startActivity(intent);
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {


            Intent intent = new Intent(ToDoMain.this, DashboardActivity.class);
            startActivity(intent);

    }



}
