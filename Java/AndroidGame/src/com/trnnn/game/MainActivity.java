package com.trnnn.game;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.trnnn.game.snake.MrNomGame;

public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ListView listView = (ListView) this.findViewById(R.id.listView1);
		listView.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1, getData()));
		listView.setOnItemClickListener(new MainListViewListener());
		// R.id.listView1;
		// this.setContentView(new AndroidGame(savedInstanceState));
		// Intent intent = new Intent(this, MrNomGame.class);
		// startActivity(intent);
	}

	class MainListViewListener implements OnItemClickListener {

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Toast.makeText(MainActivity.this, "arg2 -> " + arg2,
					Toast.LENGTH_LONG).show();
			if (arg2 == 0) {
				Intent intent = new Intent(MainActivity.this, MrNomGame.class);
				startActivity(intent);
			} else if (arg2 == 1) {
				Intent intent = new Intent(MainActivity.this,
						OpenGLTestActivity.class);
				startActivity(intent);
			}
		}

	}

	private List<String> getData() {
		List<String> data = new ArrayList<String>();
		data.add("Snake Game");
		data.add("OpenGL Test ");
		return data;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
