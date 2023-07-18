package com.tumma.roomdatabasemvvmcrud.View;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.mcsoft.timerangepickerdialog.RangeTimePickerDialog;
import com.sdsmdg.tastytoast.TastyToast;
import com.tumma.roomdatabasemvvmcrud.Adapter.RecyclerViewCategoryTaskAdapter;
import com.tumma.roomdatabasemvvmcrud.Interface.CategoryInterface;
import com.tumma.roomdatabasemvvmcrud.Model.Task;
import com.tumma.roomdatabasemvvmcrud.R;
import com.tumma.roomdatabasemvvmcrud.ViewModel.TaskViewModel;

import java.net.IDN;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import mehdi.sakout.fancybuttons.FancyButton;

// shivakumar tumma - 17 - 07 - 2023

public class AddTaskActivity extends AppCompatActivity implements CategoryInterface{
    String sCategory = "";
    private EditText edt_taskname, edt_desciption;
    TextView txt_taskdate, txt_starttime, txt_endtime;
    private RecyclerView recyclerView;
    ArrayList<String> recyclerDataArrayList = new ArrayList<>();

    RecyclerViewCategoryTaskAdapter adapter;
    CategoryInterface categoryInterface;
    private Calendar calendar;
    private int year, month, day;
    StringBuilder date = null;

    LinearLayout linearstart, linearEnd;

    private TaskViewModel TaskViewModel;

    Task task;
    FancyButton savebtn;
    TextView txtHeaderTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        init();

        TaskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);

        // datepicker

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);

        if (getIntent().getStringExtra("flag").equalsIgnoreCase("1")) { //update task .
            task = (Task) getIntent().getSerializableExtra("task");
            savebtn.setText("Update");
            txtHeaderTxt.setText("Update Task");

            loadTask(task);
        }
    }

    private void loadTask(Task task) {

        Log.e("aaaaaaaa", task.getStarttime());

        StringTokenizer stringTokenizer = new StringTokenizer(task.getStarttime()," ");
        StringTokenizer stringTokenizer1 = new StringTokenizer(task.getEndtime()," ");

        edt_taskname.setText(task.getTask());
        edt_desciption.setText(task.getDesc());
        txt_starttime.setText(stringTokenizer.nextToken()+" "+stringTokenizer.nextToken());
        txt_endtime.setText(stringTokenizer1.nextToken()+" "+stringTokenizer1.nextToken());
        //.setText(task.getDesc());
    }


    //validations.
    private void saveTask(int ii) {
        if (edt_taskname.getText().toString().equalsIgnoreCase("")) {
            edt_taskname.setError("TaskName required");
            edt_taskname.requestFocus();
        } else if (txt_taskdate.getText().toString().equalsIgnoreCase("")) {
            txt_taskdate.setError("Date required");
            txt_taskdate.requestFocus();
        } else if (txt_starttime.getText().toString().equalsIgnoreCase("") || (txt_endtime.getText().toString().equalsIgnoreCase(""))) {
            Toast.makeText(this, "Please enter valid starttime and endtime", Toast.LENGTH_SHORT).show();
        } else if (edt_desciption.getText().toString().equalsIgnoreCase("")) {
            edt_desciption.setError("description required");
            edt_desciption.requestFocus();
        } else if (sCategory.equalsIgnoreCase("")) {
            edt_desciption.requestFocus();
            TastyToast.makeText(AddTaskActivity.this, "Please select Category", TastyToast.LENGTH_LONG, TastyToast.ERROR);

        } else {
            try {
                if (ii == 0) {
                    // inserting task data .
                    Task Task = new Task(0, edt_taskname.getText().toString(), date.toString(), txt_starttime.getText().toString(), txt_endtime.getText().toString(), edt_desciption.getText().toString(), sCategory);
                    TaskViewModel.insert(Task);
                    //finish();
                    Intent intent = new Intent(AddTaskActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    TastyToast.makeText(AddTaskActivity.this, "Task data saved successfully.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);


                } else {
                    // updating task data .
                    Log.e("update", "update");
                    Task Task = new Task(task.getId(), edt_taskname.getText().toString(), date.toString(), txt_starttime.getText().toString(), txt_endtime.getText().toString(), edt_desciption.getText().toString(), sCategory);
                    TaskViewModel.update(Task);
                    TastyToast.makeText(AddTaskActivity.this, "Task data updated successfully.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    finish();
                }

            } catch (Exception e) {
                Log.e("saveTaskException: ", e.getMessage());
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        if (searchMenuItem == null) {
            return true;
        }

        MenuItemCompat.setOnActionExpandListener(searchMenuItem, new MenuItemCompat.OnActionExpandListener() {

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Set styles for expanded state here
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                }
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Set styles for collapsed state here
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                }
                return true;
            }
        });

        return true;
    }

    private void init() {
        categoryInterface = (CategoryInterface) this;
        linearstart = findViewById(R.id.linearstart);
        linearEnd = findViewById(R.id.linearend);
        recyclerView = findViewById(R.id.recycle_category);
        edt_taskname = findViewById(R.id.edt_taskname);
        txt_taskdate = findViewById(R.id.txt_taskdate);
        txt_starttime = findViewById(R.id.txt_starttime);
        txt_endtime = findViewById(R.id.txt_endtime);
        edt_desciption = findViewById(R.id.edt_desciption);
        Toolbar toolbar = findViewById(R.id.toolbar);

        savebtn = findViewById(R.id.button_save);
        txtHeaderTxt = findViewById(R.id.txtHeaderTxt);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);

        //
        // added  category data from arraylist to adapter class.
        adapter = new RecyclerViewCategoryTaskAdapter(CategoryArrayListData(), this, categoryInterface);
        // setting grid layout manager to implement grid view.
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (AddTaskActivity.this.getIntent().getStringExtra("flag").equalsIgnoreCase("1")) {
                        saveTask(1);
                    } else {
                        saveTask(0);
                    }
                } catch (Exception e) {
                    Log.e("saveTaskException: ", e.getMessage());
                    Toast.makeText(AddTaskActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


        txt_taskdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(999);
            }
        });

        txt_starttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTimePickerDialog(txt_starttime).show();

            }
        });

        txt_endtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTimePickerDialog(txt_endtime).show();

            }
        });
    }

    private ArrayList<String> CategoryArrayListData() {

        // added data to array list
        recyclerDataArrayList.add("Marketing");
        recyclerDataArrayList.add("Meeting");
        recyclerDataArrayList.add("Dev");
        recyclerDataArrayList.add("Mobile");
        recyclerDataArrayList.add("UI Design");
        recyclerDataArrayList.add("HTML");
        recyclerDataArrayList.add("Graphic Design");
        recyclerDataArrayList.add("Android App");
        recyclerDataArrayList.add("IOS App");
        recyclerDataArrayList.add("Magento");
        recyclerDataArrayList.add("SEO");
        return recyclerDataArrayList;
    }

    @Override
    public void CategoryData(String catData) {
        sCategory = catData;
    }




    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            showDate(arg1, arg2 + 1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {

        try {
            date = new StringBuilder().append(day).append(" ").append(month).append(" ").append(year);
            SimpleDateFormat dateFormatprev = new SimpleDateFormat("dd MM yyyy");
            Date d = null;
            d = dateFormatprev.parse(String.valueOf(date));
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMM yyyy");
            String changedDate = dateFormat.format(d);
            txt_taskdate.setText(changedDate);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("exception", e.getMessage());
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


    public TimePickerDialog getTimePickerDialog(final TextView view) {

        TimePickerDialog timePickerDialog = new TimePickerDialog(AddTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {

                String ss=hourOfDay+":"+minutes;

                try {
                    SimpleDateFormat format24 = new SimpleDateFormat("HH:mm");
                    Date time = format24.parse(ss);
                    SimpleDateFormat format12 = new SimpleDateFormat("hh:mm a");
                    view.setText(format12.format(time));

                } catch (ParseException e) {
                    // Handle invalid input

                }
            }
        }, 0, 0, false);

        return timePickerDialog;
    }
}
