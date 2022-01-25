package js.projects.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    Context context;
    private static final String DatabaseName = "NotesDatabase";
    private static final int DatabaseVersion = 1;

    private static final String TableName = "Notes";
    private static final String columnId = "id";
    private static final String columnTitle = "title";
    private static final String columnDescription = "description";
    private static final String columnTimeStamp = "timestamp";


    public Database(@Nullable Context context) {
        super(context, DatabaseName, null, DatabaseVersion);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TableName +
                " (" +columnId + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                columnTitle + " TEXT, " +
                columnDescription + " TEXT, " +
                columnTimeStamp + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TableName);
        onCreate(db);
    }

    void addNotes(String title, String description, String timestamp) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(columnTitle, title);
        cv.put(columnDescription, description);
        cv.put(columnTimeStamp, timestamp);

        long resultValue = db.insert(TableName, null, cv);

        if (resultValue == -1) {
            Toast.makeText(context, "Data Not Added", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Data Added Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readDatabase(){
        String query = "SELECT * FROM "+  TableName;
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = null;
        if(database!=null){
            cursor = database.rawQuery(query, null);
        }
        return cursor;
    }

    public void updateNotes(String title, String description,String timestamp, String id) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(columnTitle,title);
        cv.put(columnDescription,description);
        cv.put(columnTimeStamp,timestamp);

        long result = database.update(TableName, cv,"id=?", new String[]{id});
        if(result == -1){
            Toast.makeText(context, "Fail to update", Toast.LENGTH_SHORT).show();
        }
        else Toast.makeText(context, "Update successful", Toast.LENGTH_SHORT).show();
    }

    public void deleteSingleItem(String id) {
        SQLiteDatabase database = this.getWritableDatabase();

        long result = database.delete(TableName, "id=?", new String[]{id});
        if (result == -1) {
            Toast.makeText(context, "Item Not Deleted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Item Deleted Successfully", Toast.LENGTH_SHORT).show();
        }
    }

}
