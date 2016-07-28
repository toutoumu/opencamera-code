package sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.view.TextureView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;
import net.sourceforge.opencamera.R;

import org.wordpress.passcodelock.AppLockManager;

public class SampleActivity extends AppCompatActivity {
  TextView number1;
  TextView number2;
  EditText value;
  View submit;
  int n1, n2;

  @Override public void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.sample_activity);
    super.onCreate(savedInstanceState);
    number1 = (TextView) findViewById(R.id.number1);
    number2 = (TextView) findViewById(R.id.number2);
    value = (EditText) findViewById(R.id.value);
    n1 = new Random().nextInt(100);
    n2 = new Random().nextInt(100);
    number1.setText("" + n1);
    number2.setText("" + n2);
    submit = findViewById(R.id.submit);
    submit.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (TextUtils.isEmpty(value.getText().toString().trim())) {
          Toast.makeText(getApplication(), "请回答", Toast.LENGTH_SHORT).show();
          return;
        }
        int result = Integer.parseInt(value.getText().toString().trim());
        if (n1 + n2 == result) {
          n1 = new Random().nextInt(100);
          n2 = new Random().nextInt(100);
          number1.setText("" + n1);
          number2.setText("" + n2);
          value.setText("");
          Toast.makeText(getApplication(), "答对了", Toast.LENGTH_SHORT).show();
        } else {
          Toast.makeText(getApplication(), "答错了", Toast.LENGTH_SHORT).show();
        }
      }
    });
  }

  @Override protected void onStop() {
    super.onStop();
    AppLockManager.getInstance().setExtendedTimeout();
    AppLockManager.getInstance().getAppLock().forcePasswordLock(true);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.sample, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override public boolean onOptionsItemSelected(final MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_settings:
        startActivity(new Intent(SampleActivity.this, SamplePreferenceActivity.class));
        return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
