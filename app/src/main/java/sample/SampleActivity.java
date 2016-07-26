package sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import net.sourceforge.opencamera.R;

import org.wordpress.passcodelock.AppLockManager;

public class SampleActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.sample_activity);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        AppLockManager.getInstance().setExtendedTimeout();
        AppLockManager.getInstance().getAppLock().forcePasswordLock(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sample, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(SampleActivity.this, SamplePreferenceActivity.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
