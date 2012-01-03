package at.fhhgb.auth.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import at.fhhgb.auth.intent.IntentIntegrator;

public class DemoAppNeedsAuthActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    public void onClick(View v) {
    	switch (v.getId()) {
    	case R.id.btn_auth:
    		startAuth();
    		break;
    		default:break;
    	}
    }

	/**
	 * 
	 */
	private void startAuth() {
		Intent intent = new Intent(IntentIntegrator.Actions.ACTION_START_AUTH);
		startActivityForResult(intent, IntentIntegrator.REQUEST_CODE);
	}
}