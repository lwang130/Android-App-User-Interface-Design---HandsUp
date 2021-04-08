package edu.illinois.handsup;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GroupActivity extends AppCompatActivity implements View.OnClickListener{

    LinearLayout groupLL;
    private static Map<LinearLayout, Integer> groupLayout_to_id;
    Button course, history, select_student, notify_group, next_group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        course = (Button) findViewById(R.id.group_course);
        course.setOnClickListener(this);

        history = (Button) findViewById(R.id.group_to_history);
        history.setOnClickListener(this);

        select_student = (Button) findViewById(R.id.select_from_group);
        select_student.setOnClickListener(this);

        notify_group = (Button) findViewById(R.id.notify_group);
        notify_group.setOnClickListener(this);

        next_group = (Button) findViewById(R.id.next_group);
        next_group.setOnClickListener(this);

        groupLL = (LinearLayout) findViewById(R.id.group_list);
        groupLayout_to_id = new HashMap<>();
        int j = 0;
        for (Integer key : DataStore.getInstance().getStudentReferences()) {
            LinearLayout child = (LinearLayout) groupLL.getChildAt(j);
            groupLayout_to_id.put(child, key);
            TextView marks = (TextView) child.getChildAt(3);
            String score = String.valueOf(DataStore.getInstance().getStudentScore(key));
            marks.setText(score);
            if (DataStore.getInstance().getRandomGroup().contains(key)) {
                if (DataStore.getInstance().getVolunteers().contains(key)) {
                    Button volunteer = (Button) child.getChildAt(0);
                    volunteer.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cursor_pointer, 0, 0, 0);
                }
                child.setVisibility(View.VISIBLE);
            } else {
                child.setVisibility(View.GONE);
            }
            j++;
        }
    }


    public void decreaseScore(View view) {
        LinearLayout ll = (LinearLayout) view.getParent();
        Integer id = groupLayout_to_id.get(ll);
        TextView textView = (TextView) ll.getChildAt(3);
        Integer score = Integer.valueOf(textView.getText().toString());
        Integer newScore = score - 1;
        textView.setText(String.valueOf(newScore));
        DataStore.getInstance().setStudentScore(id, newScore);

    }

    public void increaseScore(View view) {
        LinearLayout ll = (LinearLayout) view.getParent();
        Integer id = groupLayout_to_id.get(ll);
        TextView textView = (TextView) ll.getChildAt(3);
        Integer score = Integer.valueOf(textView.getText().toString());
        Integer newScore = score + 1;
        textView.setText(String.valueOf(newScore));
        DataStore.getInstance().setStudentScore(id, newScore);
    }

    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.group_course:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.group_to_history:
                intent = new Intent(this, HistoryActivity.class);
                startActivity(intent);
                break;

            case R.id.select_from_group:
                intent = new Intent(this, RandomSelectActivity.class);
                startActivity(intent);
                break;

            case R.id.notify_group:
                notifyGroup();
                break;

            case R.id.next_group:
                DataStore.getInstance().buildRandomGroup();
                intent = new Intent(this, GroupActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void notifyGroup(){
        Set<Integer> group = DataStore.getInstance().getRandomGroup();
        ArrayList<String> ar = new ArrayList<String>();
        for(Integer i : group)
            ar.add(DataStore.getInstance().getStudentName(i));
        String[] TO = new String[ar.size()];
        TO = ar.toArray(TO);
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail"));
//            finish();
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(GroupActivity.this, "there is no email client installed", Toast.LENGTH_SHORT).show();
        }
    }
}
