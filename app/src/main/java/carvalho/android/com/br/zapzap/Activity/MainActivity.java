package carvalho.android.com.br.zapzap.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import carvalho.android.com.br.zapzap.R;

public class MainActivity extends AppCompatActivity {

    //private DatabaseReference referenciaFirebase = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //referenciaFirebase.child("pontos").setValue(100);
    }
}
