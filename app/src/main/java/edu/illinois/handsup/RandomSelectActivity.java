package edu.illinois.handsup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.util.Log;
import android.net.Uri;

import java.util.*;

import android.content.Intent;

public class RandomSelectActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private Button course;
    private Button right;
    private Button wrong;
    private Button history;
    private Button nextStudent;
    private Button notify, group;

    private Random random;

    private int lastMember = R.drawable.chris;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_random_select);
        toolbar = (Toolbar) findViewById(R.id.toolbar_menu);
        setActionBar(toolbar);

        nextStudent = (Button) findViewById(R.id.next_student);
        nextStudent.setOnClickListener(this);

        course = (Button) findViewById(R.id.course);
        course.setOnClickListener(this);

        right = (Button) findViewById(R.id.right);
        right.setOnClickListener(this);

        wrong = (Button) findViewById(R.id.wrong);
        wrong.setOnClickListener(this);

        history = (Button) findViewById(R.id.history);
        history.setOnClickListener(this);

        group = (Button) findViewById(R.id.group_of_student);
        group.setOnClickListener(this);

        random = new Random();

        ImageView image = (ImageView) findViewById(R.id.pic);
        TextView text = (TextView) findViewById(R.id.name);
        Integer randomKey   = DataStore.getInstance().getRandomStudent();

        image.setImageResource(randomKey);
        lastMember = randomKey;

        int marks = DataStore.getInstance().getStudentScore(randomKey);
        text.setText(DataStore.getInstance().getStudentName(randomKey) + " \nMarks : " + marks);

        notify = (Button) findViewById(R.id.notify_student);
        notify.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendEmail();
            }
        });
    }

    public void onClick(View view) {
        int marks;
        TextView text;
        switch (view.getId()) {
            case R.id.next_student:
                ImageView image = (ImageView) findViewById(R.id.pic);
                text = (TextView) findViewById(R.id.name);
                Integer randomKey   = DataStore.getInstance().getRandomStudent();
                while (randomKey == lastMember){
                    randomKey   = DataStore.getInstance().getRandomStudent();
                }

                image.setImageResource(randomKey);
                lastMember = randomKey;

                marks = DataStore.getInstance().getStudentScore(randomKey);
                text.setText(DataStore.getInstance().getStudentName(randomKey) + " \nMarks : " + marks);
                break;

            case R.id.course:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.history:
                Log.d("HandsUp", "touched history button");
                Intent i2 = new Intent(this, HistoryActivity.class);
                startActivity(i2);
                break;

            case R.id.right:
                marks = DataStore.getInstance().getStudentScore(lastMember)+1;
                DataStore.getInstance().setStudentScore(lastMember, marks);
                text = (TextView) findViewById(R.id.name);
                text.setText(DataStore.getInstance().getStudentName(lastMember) + " \nMarks : " + marks);
                break;

            case R.id.wrong:
                marks = DataStore.getInstance().getStudentScore(lastMember)-1;
                DataStore.getInstance().setStudentScore(lastMember, marks);
                text = (TextView) findViewById(R.id.name);
                text.setText(DataStore.getInstance().getStudentName(lastMember) + " \nMarks : " + marks);
                break;

            case R.id.group_of_student:
                Intent group = new Intent(this, GroupActivity.class);
                startActivity(group);
                break;

            default:
                Log.d("HandsUp", "Unrecognized click event!");
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_overflow_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.option1) {
            /*Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;*/
        }
        return true;
    }

    public void sendEmail(){
        String[] TO = {DataStore.getInstance().getStudentName(lastMember)};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
//            finish();
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(RandomSelectActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

}
