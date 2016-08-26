package com.example.webmobilegroupchat;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.codebutler.android_websockets.WebSocketClient;
import com.codebutler.android_websockets.WebSocketClient.Listener;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class ChatOneActivity extends Activity {

	private static MessagesListAdapter adapter;
	private List<Message> listMessages;
	private ListView listViewMessages;
	private Button btnSend;
	private EditText inputMsg;
	Utils utils;
	public WebSocketClient client;
	private String toId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(android.os.Message msg) {
				// TODO Auto-generated method stub
				update();
			}
		};

		Timer timer = new Timer();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				handler.sendEmptyMessage(0);
			}
		};

		btnSend = (Button) findViewById(R.id.btnSend);
		listViewMessages = (ListView) findViewById(R.id.list_view_messages);
		inputMsg = (EditText) findViewById(R.id.inputMsg);
		utils = new Utils(getApplicationContext());
		client = ChatActivity.client;
		toId = getIntent().getStringExtra("toId");

		listMessages = ChatActivity.mapMessage.get(toId);
		if (listMessages == null) {
			listMessages = new ArrayList<Message>();
		}
		adapter = new MessagesListAdapter(this, listMessages);
		listViewMessages.setAdapter(adapter);

		btnSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				client.send(utils.getSendOneJson(inputMsg.getText().toString(),
						toId));
				Message message = new Message("myself", inputMsg.getText()
						.toString(), true);
				message.setSessionId(utils.getSessionId());
				listMessages.add(message);

				adapter.notifyDataSetChanged();
				inputMsg.setText("");

			}
		});
		timer.schedule(task, 3000,1500);
	}

	private void update() {
		adapter.notifyDataSetChanged();
	}
}
