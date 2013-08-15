package splat.sharingview;

import splat.sharing.R;
import splat.sharingcontroller.LoginController;
import splat.sharingcontroller.ShootController;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class ShootActivity extends Activity {

    private ShootController controller;
    private Button shootButton;
    private Button editProfileButton;
    private Button contactsButton;
    
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_shoot);
		controller = new ShootController(this);
		shootButton = (Button) findViewById(R.id.shoot_button);
		shootButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				controller.onShootPressed();
			}
		});
		editProfileButton = (Button) findViewById(R.id.shoot_editprofile);
		editProfileButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				controller.onEditProfilePressed();
			}
		});
    }

}
