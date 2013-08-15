package splat.sharingcontroller;

import android.content.Intent;
import android.widget.Toast;
import network.NetworkEvent;
import network.SharingType;
import network.UserData;
import serverconnection.HandshakeListener;
import serverconnection.ServerConnection;
import serverconnection.ServerConnection.OnDisconnectListener;
import splat.sharingmodel.State;
import splat.sharingview.EditActivity;
import splat.sharingview.ShootActivity;

public class EditController {
	private EditActivity view;
	private ServerConnection sc;

	public EditController(EditActivity view) {
		this.view = view;
		sc = NetworkController.getServerConnection();
		if (sc != null) {
			// We backed into this activity from the lobby
			sc.setOnReceiveNetworkEventListener(new EditNetworkEventListener());
			sc.setOnDisconnectListener(new EditDisconnectListener());
		}
	}

	public void onSubmitPressed(UserData newData) {
		sc.sendEvent(new NetworkEvent(SharingType.UPDATE_USER, newData
				.serialize()));
	}

	private class EditNetworkEventListener extends HandshakeListener {

		@Override
		public void onReceiveNetworkEvent(final ServerConnection sc,
				final NetworkEvent evt) {
			super.onReceiveNetworkEvent(sc, evt);
			if (evt.getType().equals(SharingType.UPDATE_USER)) {
				int success = (Integer) evt.getData();
				if (success == 1) {
					// success toast
					Toast toast = Toast.makeText(view.getApplicationContext(),
							(CharSequence) "Success!", Toast.LENGTH_SHORT);
					toast.show();
					// update state from form
					State.getInstance().setMe(view.getUserDataFromForm());
					// go back to previous activity
					sc.setOnReceiveNetworkEventListener(null);
					sc.setOnDisconnectListener(null);
					view.startActivity(new Intent(view, ShootActivity.class));
					view.finish();
				} else {
					// error message
					Toast toast = Toast.makeText(view.getApplicationContext(),
							(CharSequence) "Error!", Toast.LENGTH_SHORT);
					toast.show();
					// go back to previous activity
					sc.setOnReceiveNetworkEventListener(null);
					sc.setOnDisconnectListener(null);
					view.startActivity(new Intent(view, ShootActivity.class));
					view.finish();
				}
			}
		}
	}

	/**
	 * Shows message on disconnect
	 * 
	 * @author Sam
	 */
	public class EditDisconnectListener implements OnDisconnectListener {
		@Override
		public void onDisconnect(ServerConnection connection) {
			view.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast toast = Toast.makeText(view.getApplicationContext(),
							(CharSequence) "Could not connect to server!", Toast.LENGTH_SHORT);
					toast.show();
					// view.showDisconnectDialog();
				}
			});
		}
	}
	
	public void onBackPressed() {
		sc.setOnReceiveNetworkEventListener(null);
		sc.setOnDisconnectListener(null);
		view.startActivity(new Intent(view, ShootActivity.class));
		view.finish();
	}
}
