package invenz.example.bijohn.dhamma.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import invenz.example.bijohn.dhamma.R;

public class Demo extends AppCompatActivity {
    public class MainActivity extends AppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
           /* mCalendarView = (CalendarView) findViewById(R.id.calendarView);
            FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addNote();
                }
            });
            mCalendarView.setOnDayClickListener(new OnDayClickListener() {
                @Override
                public void onDayClick(EventDay eventDay) {
                    previewNote(eventDay);
                }
            });*/
        }
       /* @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == ADD_NOTE && resultCode == RESULT_OK) {
                MyEventDay myEventDay = data.getParcelableExtra(RESULT);
                mCalendarView.setDate(myEventDay.getCalendar());
                mEventDays.add(myEventDay);
                mCalendarView.setEvents(mEventDays);
            }
        }



        private void addNote() {
            Intent intent = new Intent(this, AddNoteActivity.class);
            startActivityForResult(intent, ADD_NOTE);
        }


        private void previewNote(EventDay eventDay) {
            Intent intent = new Intent(this, NotePreviewActivity.class);
            if(eventDay instanceof MyEventDay){
                intent.putExtra(EVENT, (MyEventDay) eventDay);
            }
            startActivity(intent);
        }*/
}}
