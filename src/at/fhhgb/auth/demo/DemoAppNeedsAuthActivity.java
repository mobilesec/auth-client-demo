package at.fhhgb.auth.demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import at.fhhgb.auth.intent.IntentIntegrator;
import at.fhhgb.auth.provider.AuthDb.Subject;

public class DemoAppNeedsAuthActivity extends Activity {
	
	private static final String PREF_KEY_USER_ID = "userId";
	private static final int REQUEST_ASSIGN_USER = 1;
	private Button btnAssignUser;
	private Button btnAuth;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        btnAssignUser = (Button) findViewById(R.id.btn_assign_user);
        btnAuth = (Button) findViewById(R.id.btn_auth);
        
        // find out if we already have a user
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String existingUserId = preferences.getString(PREF_KEY_USER_ID, null);
        
        if (existingUserId == null) {
        	showHintDialog();
        	btnAuth.setEnabled(false);
        } 
    }
    
	private void showHintDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Assign user");
		builder.setMessage("You have to assign a user that you will authenticate as later.\n" +
				"Press the \"Assign user\" button to start.");
		builder.setPositiveButton("OK", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		builder.show();
	}

	public void onClick(View v) {
    	switch (v.getId()) {
    	case R.id.btn_auth:
    		startAuth();
    		break;
    	case R.id.btn_assign_user:
    		assignUser();
    		break;
    		default:break;
    	}
    }
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_ASSIGN_USER && resultCode == RESULT_OK) {
			handleUserAssignment(data);
		}
	}

	private void handleUserAssignment(Intent data) {
		Uri assignedUserUri = data.getData();
		String id = assignedUserUri.getLastPathSegment();
		
		Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
		editor.putString(PREF_KEY_USER_ID, id);
		editor.commit();
		
		btnAuth.setEnabled(true);
	}

	private void assignUser() {
		Intent intent = new Intent(Intent.ACTION_PICK, Subject.CONTENT_URI);
		startActivityForResult(intent, REQUEST_ASSIGN_USER);
	}

	private void startAuth() {
		// TODO continue here add the assigned userId, also a preferred auth method
		Intent intent = new Intent(IntentIntegrator.Actions.ACTION_START_AUTH);
		startActivityForResult(intent, IntentIntegrator.REQUEST_CODE);
	}
}