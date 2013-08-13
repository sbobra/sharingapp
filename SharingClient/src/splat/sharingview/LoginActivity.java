package splat.sharingview;

import splat.sharing.R;
import splat.sharingcontroller.LoginController;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class LoginActivity extends Activity {

    private LoginController controller;
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
    }

}
