package splat.sharingview;

import splat.sharing.R;
import splat.sharingcontroller.EditController;
import splat.sharingmodel.State;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import network.UserData;

public class EditActivity extends Activity {

	private EditController controller;
	private EditText nameText;
	private EditText passwordText;
	private EditText emailText;
	private EditText facebookText;
	private EditText twitterText;
	private EditText linkedinText;
	private Button submitButton;

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_edit);
		controller = new EditController(this);
		nameText = (EditText) findViewById(R.id.edit_name);
		passwordText = (EditText) findViewById(R.id.edit_password);
		emailText = (EditText) findViewById(R.id.edit_email);
		facebookText = (EditText) findViewById(R.id.edit_facebook);
		twitterText = (EditText) findViewById(R.id.edit_twitter);
		linkedinText = (EditText) findViewById(R.id.edit_linkedin);
		submitButton = (Button) findViewById(R.id.edit_submit);
		submitButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				controller.onSubmitPressed(getUserDataFromForm());
			}
		});
	}
	
	public UserData getUserDataFromForm() {
		UserData newData = new UserData();
		newData.setId(State.getInstance().getMe().getId());
		newData.setName(nameText.getText().toString());
		newData.setPassword(passwordText.getText().toString());
		newData.setEmail(emailText.getText().toString());
		newData.setFacebook(facebookText.getText().toString());
		newData.setTwitter(twitterText.getText().toString());
		newData.setLinkedIn(linkedinText.getText().toString());
		return newData;
	}
	public void onBackPressed(){
		controller.onBackPressed();
	}

}
