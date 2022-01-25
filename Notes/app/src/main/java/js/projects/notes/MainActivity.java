package js.projects.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView list;
    NotesAdapter notesAdapter;
    ArrayList<NotesModel> item = new ArrayList<>();
    FloatingActionButton add;
    Database database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = findViewById(R.id.list);
        add = findViewById(R.id.add);

        add.setOnClickListener(v-> startActivity(new Intent(this, AddNote.class)));

        notesAdapter = new NotesAdapter(this,MainActivity.this, item);
        database = new Database(this);
        fetchAllNotesFromDatabase();

        list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        list.setAdapter(notesAdapter);
    }

    private void fetchAllNotesFromDatabase() {
        Cursor cursor = database.readDatabase();
        if(cursor.getCount()==0){
            Toast.makeText(this, "No Notes to show", Toast.LENGTH_SHORT).show();
        }
        else{
            while(cursor.moveToNext()){
                item.add(new NotesModel(cursor.getString(0),cursor.getString(1),
                        cursor.getString(2),cursor.getString(3)));
            }
        }
    }
}