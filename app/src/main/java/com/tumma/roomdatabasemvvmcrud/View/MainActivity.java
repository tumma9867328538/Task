package com.tumma.roomdatabasemvvmcrud.View;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.github.dewinjm.monthyearpicker.MonthFormat;
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialog;
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialogFragment;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.imageview.ShapeableImageView;
import com.kal.rackmonthpicker.RackMonthPicker;
import com.kal.rackmonthpicker.listener.DateMonthDialogListener;
import com.kal.rackmonthpicker.listener.OnCancelMonthDialogListener;
import com.tumma.roomdatabasemvvmcrud.Adapter.CalendarAdapter;
import com.tumma.roomdatabasemvvmcrud.Interface.CalenderPickDate;
import com.tumma.roomdatabasemvvmcrud.Interface.OnClickListener_;
import com.tumma.roomdatabasemvvmcrud.Model.MyCalendar;
import com.tumma.roomdatabasemvvmcrud.Model.Task;
import com.tumma.roomdatabasemvvmcrud.Adapter.TasksAdapter;
import com.tumma.roomdatabasemvvmcrud.Utils.common.CommonMethods;
import com.tumma.roomdatabasemvvmcrud.ViewModel.TaskViewModel;
import com.tumma.roomdatabasemvvmcrud.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements CalenderPickDate, OnClickListener_ {

    OnClickListener_ onClickListener;
    private TaskViewModel TaskViewModel;
    private RecyclerView recyclerview_tasks;
    private View parent;
    TasksAdapter tasksAdapter;
    //
    SimpleDateFormat dateFormatprev = new SimpleDateFormat("dd MMM yyyy");
    // horizontal
    private List<MyCalendar> calendarList = new ArrayList<MyCalendar>();
    private List<String> calendarArrayList = new ArrayList<String>();
    private RecyclerView recyclerViewCal;
    private CalendarAdapter mAdapter;
    ///
    int yearSelected = 0;
    int monthSelected = 0;
    int currentYear;
    TextView txtMonYr, txtNotask;
    String strFullDate = "";

    CalenderPickDate calenderPickDate;
    private FloatingActionButton buttonAddTask;
    ShapeableImageView imgCalender;
    String strDateMOnthYear = "";
    String strcalenderPickDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intToolbar();
        intView();
        onClickListener = (OnClickListener_) this;

        //Observe Task add

        try {
            TaskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
            TaskViewModel.getAlltasks().observe(this, new Observer<List<Task>>() {
                @Override
                public void onChanged(List<Task> tasks) {
                    if (tasks.size() == 0) {
                        txtNotask.setVisibility(View.VISIBLE);
                        recyclerview_tasks.setVisibility(View.GONE);
                    } else {
                        txtNotask.setVisibility(View.GONE);
                        tasksAdapter = new TasksAdapter(MainActivity.this, tasks, onClickListener);
                        recyclerview_tasks.setAdapter(tasksAdapter);
                        tasksAdapter.notifyDataSetChanged();
                    }
                    Log.e("onChanged: ", String.valueOf(tasks.size()));
                }
            });
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


       try {
           new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                   ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
               @Override
               public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                   return false;
               }

               @Override
               public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                   TaskViewModel.delete(tasksAdapter.getTaskAt(viewHolder.getAdapterPosition()));
                   CommonMethods.snackBar("Task Deleted",parent);
               }
           }).attachToRecyclerView(recyclerview_tasks);

       }catch (Exception e){

           Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
       }
    }

    private void intView() {

        calenderPickDate = (CalenderPickDate) this; // interface..

        parent = findViewById(android.R.id.content);
        FloatingActionButton fab = findViewById(R.id.floating_button_add);

        recyclerview_tasks = findViewById(R.id.recyclerview_tasks);
        recyclerview_tasks.setLayoutManager(new LinearLayoutManager(this));
        recyclerview_tasks.setHasFixedSize(true);
        recyclerview_tasks = findViewById(R.id.recyclerview_tasks);
        buttonAddTask = findViewById(R.id.floating_button_add);
        imgCalender = findViewById(R.id.imgCalender);
        txtMonYr = findViewById(R.id.txtMonYr);
        txtNotask = findViewById(R.id.txtNotask);
        recyclerViewCal = (RecyclerView) findViewById(R.id.recycler_viewHorizontal);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                openCreateTaskDialog();
               try {
                   Nextactivity();
               }catch (Exception e){
                   Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
               }
            }
        });



        try {
            prepareCalendarData();
            updateViews(getMonthYear());

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        //

        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Nextactivity();
            }
        });

        imgCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    RackMonthPicker rackMonthPicker=new RackMonthPicker(MainActivity.this)
//                    new RackMonthPicker(MainActivity.this)
                            .setLocale(Locale.ENGLISH)
                            .setColorTheme(getResources().getColor(R.color.pinkdark))
                            .setPositiveButton(new DateMonthDialogListener() {
                                @Override
                                public void onDateMonth(int month, int startDate, int endDate, int year, String monthLabel) {
                                    try {
                                        monthSelected = month;
                                        yearSelected = year;

                                        prepareCalendarData();

                                        Calendar calendar = Calendar.getInstance();
                                        calendar.set(yearSelected, monthSelected-1, 1);

                                        SimpleDateFormat format = new SimpleDateFormat("MMM yyyy");
                                        strDateMOnthYear = format.format(calendar.getTime());
                                        updateViews(strDateMOnthYear);

                                        if (!strDateMOnthYear.equalsIgnoreCase("") && (!strcalenderPickDate.equalsIgnoreCase(""))) {
                                            Toast.makeText(MainActivity.this, monthSelected + "", Toast.LENGTH_SHORT).show();
                                            strFullDate = strcalenderPickDate + " " + monthSelected + " " + yearSelected;

                                            Log.e("strFullDate", strFullDate);
                                            TaskViewModel.getAlltasksbydate(strFullDate.trim()).observe(MainActivity.this, new Observer<List<Task>>() {
                                                @Override
                                                public void onChanged(List<Task> tasks) {
                                                    if (tasks.size() == 0) {
                                                        txtNotask.setVisibility(View.VISIBLE);
                                                        recyclerview_tasks.setVisibility(View.GONE);
                                                    } else {
                                                        txtNotask.setVisibility(View.GONE);
                                                        recyclerview_tasks.setVisibility(View.VISIBLE);

                                                        tasksAdapter = new TasksAdapter(MainActivity.this, tasks, onClickListener);
                                                        recyclerview_tasks.setAdapter(tasksAdapter);
                                                        tasksAdapter.notifyDataSetChanged();
                                                    }
                                                    Log.e("onChanged: ", String.valueOf(tasks.size()));
                                                }
                                            });


                                        }
                                    } catch (Exception e) {
                                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        Log.e("onYearMonthSet: ", e.getMessage());
                                    }


                                }
                            })
                            .setNegativeButton(new OnCancelMonthDialogListener() {
                                @Override
                                public void onCancel(AlertDialog dialog) {

                                    dialog.dismiss();
                                }
                            });

                    rackMonthPicker.show();


                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("onYearMonthSet: ", e.getMessage());
                }
            }
        });

    }

    private void Nextactivity() {
        Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
        intent.putExtra("task", "0");
        intent.putExtra("flag", "0");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    private void prepareCalendarData() {

        calendarList.clear();
        SimpleDateFormat fmt = new SimpleDateFormat("dd EEEE");
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(yearSelected, monthSelected+1, 1);

        calendarArrayList.clear();

        int kk = 0;
//        get days

        for (int i = 0; i < CommonMethods.getDayscount(yearSelected, monthSelected); i++) { // total month days count..
            cal.add(Calendar.DAY_OF_MONTH, -1);
            kk = kk + 1;
            MyCalendar calendar = new MyCalendar(CommonMethods.getweekDay(kk, monthSelected, yearSelected), String.valueOf(kk));
            calendarList.add(calendar);
        }

        mAdapter = new CalendarAdapter(calendarList, this, calenderPickDate);
        recyclerViewCal.setHasFixedSize(true);

        // horizontal RecyclerView calnder
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCal.setLayoutManager(mLayoutManager);
        recyclerViewCal.setItemAnimator(new DefaultItemAnimator());
        recyclerViewCal.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        // notify adapter about data set changes
        // so that it will render the list with new data

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


        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (tasksAdapter != null)
                    tasksAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_allTask:
                try {
                    TaskViewModel.deleteAlltasks();
                    CommonMethods.snackBar("All Task Deleted",parent);
                }catch (Exception e){
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                return true;

            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

//    private void openCreateTaskDialog() {
//        CreateTaskDialog createTaskDialog = new CreateTaskDialog();
//        createTaskDialog.show(getSupportFragmentManager(), "create Task");
//    }

    private void intToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
    }

//    @Override
//    public void saveNewTask(Task Task) {
//        TaskViewModel.insert(Task);
//        snackBar("Task Saved");
//    }


    private void updateViews(String strDate) {
        txtMonYr.setText(strDate);
    }

    String getMonthYear() {
        Calendar calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);
        yearSelected = currentYear;
        monthSelected = calendar.get(Calendar.MONTH);
        calendar.set(yearSelected, monthSelected, 1);
        SimpleDateFormat format = new SimpleDateFormat("MMM yyyy");
        strDateMOnthYear = format.format(calendar.getTime());
        return strDateMOnthYear;

    }

    private MonthYearPickerDialogFragment createDialogWithRanges() {
        final int minYear = 2010;
        final int maxYear = currentYear;
        final int maxMoth = 11;
        final int minMoth = 0;
        final int minDay = 1;
        final int maxDay = 31;
        long minDate;
        long maxDate;

        Calendar calendar = Calendar.getInstance();

        calendar.clear();
        calendar.set(minYear, minMoth, minDay);
        minDate = calendar.getTimeInMillis();

        calendar.clear();
        calendar.set(maxYear, maxMoth, maxDay);
        maxDate = calendar.getTimeInMillis();

        return MonthYearPickerDialogFragment.getInstance(monthSelected, yearSelected, minDate, maxDate, null, MonthFormat.SHORT);
    }


    @Override
    public void CalenderPickDate(String calenderPickDate) {
        try {
            strcalenderPickDate = calenderPickDate;
            strFullDate = calenderPickDate + " " + strDateMOnthYear;
            Date d = null;
            d = dateFormatprev.parse(strFullDate);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd M yyyy");
            strFullDate = dateFormat.format(d);

            Log.e("strFullDate datepick", strFullDate);
            if (!strDateMOnthYear.equalsIgnoreCase("") && (!strcalenderPickDate.equalsIgnoreCase(""))) {

                TaskViewModel.getAlltasksbydate(strFullDate).observe(this, new Observer<List<Task>>() {
                    @Override
                    public void onChanged(List<Task> tasks) {
                        if (tasks.size() == 0) {
                            txtNotask.setVisibility(View.VISIBLE);
                            recyclerview_tasks.setVisibility(View.GONE);
                        } else {
                            txtNotask.setVisibility(View.GONE);
                            recyclerview_tasks.setVisibility(View.VISIBLE);
                            tasksAdapter = new TasksAdapter(MainActivity.this, tasks, onClickListener);
                            recyclerview_tasks.setAdapter(tasksAdapter);
                            tasksAdapter.notifyDataSetChanged();
                        }
                        Log.e("onChanged: ", String.valueOf(tasks.size()));
                    }
                });

            }
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void delete(int pos) {
        TaskViewModel.delete(tasksAdapter.getTaskAt(pos));
        CommonMethods.snackBar("Task Deleted",parent);
    }
}
