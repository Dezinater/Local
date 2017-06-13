package gorillasquad.local;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String TAG = "MainActivity";

    PostAdapter pa;
    ArrayList<Post> posts;
    PostHandler ph;
    String myId;

    Dialog connectingDialog ;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        connectingDialog = getLoadingDialog();
        connectingDialog.show();

        Log.d(TAG,""+mAuth.toString());

        mAuth.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "Logging in");
                if (task.isSuccessful()) {
                    connectingDialog.dismiss();
                    Log.d(TAG, "signInAnonymously:success");
                    myId = mAuth.getCurrentUser().getUid();
                    ph = new PostHandler(myId);
                } else {
                    Log.w(TAG, "signInAnonymously:failure", task.getException());
                    Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    System.exit(0);
                }
            }
        });




        posts = new ArrayList<>();

        ListView yourListView = (ListView) findViewById(R.id.postList);
        pa = new PostAdapter(this, posts);
        yourListView.setAdapter(pa);



        pa.add(new Post("Test"));
        pa.add(new Post("Test2"));
        pa.add(new Post("Test2"));
        pa.add(new Post("Test2"));
        pa.add(new Post("Test2"));
        pa.add(new Post("Test2"));
        pa.notifyDataSetChanged();
    }


    public void buttonClicked(View v) {
        Log.d(TAG,"button clicked");
        ph.addPost("test");
    }

    private Dialog getLoadingDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Loading");
        LayoutInflater inflater = this.getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.connecting, null));
        builder.setNegativeButton("Close App", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        builder.setCancelable(false);
        return builder.create();
    }

}