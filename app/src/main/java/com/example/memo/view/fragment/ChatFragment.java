package com.example.memo.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.memo.R;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ChatFragment extends BaseFragment {
    private static final int SERVER_TEXT_UPDATE = 100;
    private static final int CLIENT_TEXT_UPDATE = 200;

    private Handler handler = new Handler() {
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(Message msgg) {
            super.handleMessage(msgg);
            switch (msgg.what) {
                case SERVER_TEXT_UPDATE: {
                    serverMsg.append(msg);
                    //serverText.setText(serverMsg.toString());
                }
                break;
                case CLIENT_TEXT_UPDATE: {
                    clientMsgBuilder.append(clientMsg);
                    //clientText.setText(clientMsgBuilder.toString());
                }
                break;

            }
        }
    };
    //서버세팅
    private ServerSocket serverSocket;
    private Socket socket;
    private String msg;
    private StringBuilder serverMsg = new StringBuilder();
    private StringBuilder clientMsgBuilder = new StringBuilder();
    private Map<String, DataOutputStream> clientsMap = new HashMap<String, DataOutputStream>();

    //클라세팅
    private Socket clientSocket;
    private DataInputStream clientIn;
    private DataOutputStream clientOut;
    private String clientMsg;
    private String nickName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_chat,container,false);
        return v;
    }
}
