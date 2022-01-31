package js.projects.notes;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;


public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder>{

    Context context;
    Activity activity;
    RelativeLayout view;
    ArrayList<NotesModel> notes;

    public NotesAdapter(Context context, Activity activity, ArrayList<NotesModel> notesModel,
                        RelativeLayout view) {
        this.context = context;
        this.activity = activity;
        this.notes = notesModel;
        this.view = view;
    }


    public static class NotesViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView description;
        TextView timestamp;
        ImageView delete;
        CardView note;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            timestamp = itemView.findViewById(R.id.timestamp);
            delete = itemView.findViewById(R.id.delete);
            note = itemView.findViewById(R.id.note);
        }
    }
    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_note, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.NotesViewHolder holder, @SuppressLint("RecyclerView") int position) {
        NotesModel item = notes.get(position);
        holder.title.setText(item.getTitle());
        holder.description.setText(item.getDescription());
        holder.timestamp.setText(item.getTimestamp());

        holder.delete.setOnClickListener(v->{
            Database db = new Database(context);
            removeItem(position);
            Snackbar snackbar =
                    Snackbar.make(view,"Item Deleted", Snackbar.LENGTH_LONG).setAction("UNDO",
                            v1 -> restoreItem(item, position)).addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                        @Override
                        public void onDismissed(Snackbar transientBottomBar, int event) {
                            if(!(event == DISMISS_EVENT_ACTION)){
                                db.deleteSingleItem(item.getId());
                            }
                        }
                    });
            snackbar.setActionTextColor(Color.BLUE);
            snackbar.show();
        });

        holder.note.setOnClickListener(v->{
            Intent intent = new Intent(context, UpdateNote.class);
            intent.putExtra("title", item.getTitle());
            intent.putExtra("description", item.getDescription());
            intent.putExtra("id", item.getId());
            activity.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void removeItem(int position) {
        notes.remove(position);
        notifyItemRemoved(position);
    }
    public void restoreItem(NotesModel item, int position) {
        notes.add(position, item);
        notifyItemInserted(position);
    }

}