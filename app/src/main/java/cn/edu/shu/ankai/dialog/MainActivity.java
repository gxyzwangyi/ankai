package cn.edu.shu.ankai.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import cn.edu.shu.ankai.R;

public class MainActivity extends Activity {

	private TextView textview;
	private CityDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		textview = (TextView) findViewById(R.id.textview);
		textview.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CityDialog.InputListener listener = new CityDialog.InputListener() {
					
					@Override
					public void getText(String str) {
						// TODO Auto-generated method stub
						textview.setText(str);
					}
				};
				dialog = new CityDialog(MainActivity.this, listener);
				dialog.setTitle("地点");
				dialog.show();
			}
		});
	}
}
