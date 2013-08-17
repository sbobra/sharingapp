package splat.sharingview;

import splat.sharing.R;
import splat.sharingcontroller.LoginController;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {

	private LoginController controller;
	private EditText emailText;
	private EditText passwordText;
	private Button submitButton;
	private Button newUserButton;
	private Dialog newUserDialog;
	private AlertDialog confirmationDialog;

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_login);
		controller = new LoginController(this);
		emailText = (EditText) findViewById(R.id.login_email);
		passwordText = (EditText) findViewById(R.id.login_password);
		submitButton = (Button) findViewById(R.id.login_submit);
		submitButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				controller.onSubmitPressed();
			}
		});
		newUserButton = (Button) findViewById(R.id.login_newuser);
		newUserButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				controller.onNewUserPressed();
			}
		});

		newUserDialog = new Dialog(this);
		newUserDialog.setContentView(R.layout.dialog_newuser);
		newUserDialog.setTitle("Create a new account");
		Button newUserSubmitButton = (Button) newUserDialog
				.findViewById(R.id.newuser_submit);
		newUserSubmitButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				EditText newUserEmail = (EditText) newUserDialog
						.findViewById(R.id.newuser_email);
				EditText newUserPassword = (EditText) newUserDialog
						.findViewById(R.id.newuser_password);
				controller.onNewUserSubmitPressed(newUserEmail.getText()
						.toString(), newUserPassword.getText().toString());
				newUserDialog.dismiss();
			}
		});
		Button newUserCancelButton = (Button) newUserDialog
				.findViewById(R.id.newuser_cancel);
		newUserCancelButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				newUserDialog.dismiss();
			}
		});
	}

	public void showNewUserDialog() {
		newUserDialog.show();
	}
	
	public void showConfirmationDialog(String name) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Would you like to share your data with " + name + "?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface confirmationDialog, int which) {
            	//TODO: FINISH ME
                confirmationDialog.dismiss();
            }
        });
        confirmationDialog = builder.create();
        confirmationDialog.show();
	}

	public String getEmailText() {
		return emailText.getText().toString();
	}

	public String getPasswordText() {
		return passwordText.getText().toString();
	}

}
