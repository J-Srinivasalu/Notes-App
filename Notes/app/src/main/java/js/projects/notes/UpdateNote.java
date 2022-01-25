package js.projects.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UpdateNote extends AppCompatActivity {

    EditText title,description;
    FloatingActionButton update;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);
        title=findViewById(R.id.title);
        description=findViewById(R.id.description);
        update=findViewById(R.id.update);

        Intent intent =getIntent();
        title.setText(intent.getStringExtra("title"));
        description.setText(intent.getStringExtra("description"));
        id=intent.getStringExtra("id");

        update.setOnClickListener(v->{
            if(!TextUtils.isEmpty(title.getText().toString()) && !TextUtils.isEmpty(description.getText().toString()))
            {

                Database db = new Database(this);
                db.updateNotes(title.getText().toString(),description.getText().toString(),
                        getTimestamp(),id);

                Intent i=new Intent(this,MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();


            }
            else
            {
                Toast.makeText(this, "Both Fields Required", Toast.LENGTH_SHORT).show();
            }

        });

    }
    @SuppressLint("SimpleDateFormat")
    private String getTimestamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar
                .getInstance().getTime());
    }
}