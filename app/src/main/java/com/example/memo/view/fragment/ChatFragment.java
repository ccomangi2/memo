package com.example.memo.view.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.memo.R;
import com.example.memo.view.activity.ChatingActivity;
import com.example.memo.view.activity.MainActivity;
import com.example.memo.view.activity.UserData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ChatFragment extends BaseFragment {
    private static final int SERVER_TEXT_UPDATE = 100;
    private static final int CLIENT_TEXT_UPDATE = 200;

    private ListView listView;
    private Button btn_create;

    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> arr_roomList = new ArrayList<>();
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Room");
    private String name;

    private String str_name;
    private String str_room;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseRef;

    Map<String, Object> map = new HashMap<String, Object>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_chat,container,false);

        listView = (ListView) v.findViewById(R.id.list);
        btn_create = (Button) v.findViewById(R.id.btn_create);

        //Firebase??? ??????
        mAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users");

        // ????????? ???????????? ???????????????
        arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, arr_roomList);
        listView.setAdapter(arrayAdapter);


        // ????????????????????? ????????? ????????? ????????? ???????????? ???????????????
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                final EditText et_inDialog = new EditText(getContext());

                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("????????? ?????? ??????");
                builder.setView(et_inDialog);
                builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        str_room = et_inDialog.getText().toString();
                        map.put(str_room, "");
                        reference.updateChildren(map);
                    }
                });
                builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }
        });

        // ?????? ????????? ?????? ????????? ?????? ?????? ????????? ?????? ?????? ?????????
        // onDataChange??? Database??? ?????????????????? ????????????
        // onCancelled??? ??????????????? ???????????????
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Set<String> set = new HashSet<String>();
                Iterator i = dataSnapshot.getChildren().iterator();

                while (i.hasNext()) {
                    set.add(((DataSnapshot) i.next()).getKey());
                }

                arr_roomList.clear();
                arr_roomList.addAll(set);

                arrayAdapter.notifyDataSetChanged();
            }

            @Override public void onCancelled(DatabaseError databaseError) {

            }
        });

        // ??????????????? ???????????? ???????????? ??? ??????
        // ???????????? ????????? ???????????? ????????? ????????? ??????
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                if (firebaseUser != null) {
                    mDatabaseRef.child("UserData").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                UserData userData = dataSnapshot.getValue(UserData.class);
                                str_name = userData.getName();
                                Intent intent = new Intent(getContext(), ChatingActivity.class);
                                intent.putExtra("room_name", ((TextView) view).getText().toString());
                                intent.putExtra("user_name", str_name);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) { }
                    });
                }
            }
        });

        return v;
    }
}
