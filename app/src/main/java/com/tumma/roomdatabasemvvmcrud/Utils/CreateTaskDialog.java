//package com.tumma.roomdatabasemvvmcrud.Utils;
//
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.content.Context;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatDialogFragment;
//
//import com.tumma.roomdatabasemvvmcrud.Model.Task;
//import com.tumma.roomdatabasemvvmcrud.R;
//
//public class CreateTaskDialog extends AppCompatDialogFragment {
//
//    private EditText mName;
//    private EditText mAge;
//    private EditText mPosition;
//    private Button mSaveBtn;
//    private CreateTaskListener mListener;
//
//    @NonNull
//    @Override
//    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        LayoutInflater inflater = getActivity().getLayoutInflater();
//        View view = inflater.inflate(R.layout.layout_dialog,null);
//
//        builder.setView(view);
//        builder.setCancelable(true);
//        builder.setTitle("Add New Task");
//
//        mName = view.findViewById(R.id.et_name);
//        mAge = view.findViewById(R.id.et_age);
//        mPosition = view.findViewById(R.id.et_position);
//        mSaveBtn = view.findViewById(R.id.btn_save);
//
//        mSaveBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String name = mName.getText().toString();
//                String age = mAge.getText().toString();
//                String position = mPosition.getText().toString();
//
//                if(name.isEmpty()||age.isEmpty()||position.isEmpty()) {
//                    return;
//                }
//                else {
//                //    Task Task = new Task(name,position,age);
//                  //  mListener.saveNewTask(Task);
//                    dismiss();
//                }
//            }
//        });
//
//        return builder.create();
//    }
//
//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        mListener = (CreateTaskListener)context;
//    }
//
//    public interface CreateTaskListener{
//        void saveNewTask(Task Task);
//    }
//}
