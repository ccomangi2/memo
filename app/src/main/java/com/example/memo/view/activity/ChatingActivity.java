package com.example.memo.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.memo.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class ChatingActivity extends BaseActivity {
    private Button btn_send;
    private EditText et_msg;
    private ListView lv_chating;

    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> arr_room = new ArrayList<>();

    private String str_room_name;
    private String str_user_name;

    private DatabaseReference reference;
    private String key;
    private String chat_user;
    private String chat_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chating);

        lv_chating = (ListView) findViewById(R.id.lv_chating);
        btn_send = (Button) findViewById(R.id.btn_send);
        et_msg = (EditText) findViewById(R.id.et_send);

        str_room_name = getIntent().getExtras().get("room_name").toString();
        str_user_name = getIntent().getExtras().get("user_name").toString();
        reference = FirebaseDatabase.getInstance().getReference("Room").child(str_room_name);


        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr_room);
        lv_chating.setAdapter(arrayAdapter);
        // 리스트뷰가 갱신될때 하단으로 자동 스크롤
        lv_chating.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                // map을 사용해 name과 메시지를 가져오고, key에 값 요청
                Map<String, Object> map = new HashMap<String, Object>();
                key = reference.push().getKey();
                reference.updateChildren(map);

                DatabaseReference root = reference.child(key);

                // updateChildren를 호출하여 database 최종 업데이트
                Map<String, Object> objectMap = new HashMap<String, Object>();
                objectMap.put("name", str_user_name);
                objectMap.put("message", et_msg.getText().toString());

                root.updateChildren(objectMap);

                et_msg.setText("");
            }
        });


        /*
        addChildEventListener는 Child에서 일어나는 변화를 감지
        - onChildAdded()   : 리스트의 아이템을 검색하거나 추가가 있을 때 수신
        - onChildChanged() : 리스트의 아이템의 변화가 있을때 수신
        - onChildRemoved() : 리스트의 아이템이 삭제되었을때 수신
        - onChildMoved()   : 리스트의 순서가 변경되었을때 수신
         */

        reference.addChildEventListener(new ChildEventListener() {
            @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                chatConversation(dataSnapshot);
            }

            @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                chatConversation(dataSnapshot);
            }

            @Override public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    // addChildEventListener를 통해 실제 데이터베이스에 변경된 값이 있으면,
    // 화면에 보여지고 있는 Listview의 값을 갱신함
    private void chatConversation(DataSnapshot dataSnapshot) {
        Iterator i = dataSnapshot.getChildren().iterator();

        while (i.hasNext()) {
            chat_message = (String) ((DataSnapshot) i.next()).getValue();
            chat_user = (String) ((DataSnapshot) i.next()).getValue();

            arrayAdapter.add(chat_user + " : " + chat_message);
        }

        arrayAdapter.notifyDataSetChanged();
    }
}