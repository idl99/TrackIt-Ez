package teamideals.com.trackitez;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import teamideals.com.trackitez.databinding.ContentMainBinding;

public class MainActivity extends AppCompatActivity {

    private TextView mTextViewUsername;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        intent = getIntent();
        User sessionUser = (User) intent.getExtras().getSerializable("user");

        /* Data Binding

         1. Declare layout binding variable in XML file: check content_main.xml

         2. Gradles generates the binding class for relevant layout file - ContentMainBinding

         3. Attach data binding to layout where binding variable declared, using
         inflate and set??? methods where ??? is the name of the binding variable declared
         in the layout file: setUser --> binding variable user in content_main.xml*/

        ContentMainBinding contentMainBinding = ContentMainBinding.inflate(
                getLayoutInflater(),(ViewGroup) findViewById(R.id.content_main_parent),true
        ); // Attach data binding to content_main_parent view
           // of layout where binding variable declared

        contentMainBinding.setUser(sessionUser); // copy reference of sessionUser to user in
                                                 // content_main.xml

    }

}
