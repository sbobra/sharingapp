package splat.sharingcontroller;

import network.NetworkEvent;
import network.SharingType;
import network.UserData;
import serverconnection.HandshakeListener;
import serverconnection.HandshakeListener.OnConnectionConfirmedListener;
import serverconnection.ServerConnection;
import serverconnection.ServerConnection.OnDisconnectListener;
import splat.sharingmodel.Settings;
import splat.sharingmodel.State;
import splat.sharingview.LoginActivity;
import splat.sharingview.ShootActivity;
import android.content.Intent;
import android.widget.Toast;

public class LoginController {
	private LoginActivity view;
	private LoginDisconnectListener loginDisconnectListener = new LoginDisconnectListener();

	public LoginController(LoginActivity view) {
		this.view = view;
		ServerConnection sc = NetworkController.getServerConnection();
		if (sc != null) {
			// We backed into this activity from the lobby
			sc.setOnReceiveNetworkEventListener(null);
		}
	}

	public void onSubmitPressed() {
		NetworkController.startConnection(Settings.getIp(), Settings.getPort(),
				Settings.newId(), loginDisconnectListener);

		NetworkController.getServerConnection().connect(
				new HandshakeListener(new OnConnectionConfirmedListener() {
					@Override
					public void onConnectionConfirmed(
							ServerConnection connection) {
						connection
								.setOnReceiveNetworkEventListener(new LoginNetworkEventListener());
						String email = "";
						String password = "";
						Object[] data = new Object[2];
						data[0] = email;
						data[1] = password;// ENCRYPT ME
						connection.sendEvent(new NetworkEvent(
								SharingType.LOG_IN, data));
					}
				}));
	}

	public void onNewUserPressed() {
		// TODO: pop up dialog
	}

	public void onNewUserSubmitPressed(final String email, final String password) {
		NetworkController.startConnection(Settings.getIp(), Settings.getPort(),
				Settings.newId(), loginDisconnectListener);

		NetworkController.getServerConnection().connect(
				new HandshakeListener(new OnConnectionConfirmedListener() {
					@Override
					public void onConnectionConfirmed(
							ServerConnection connection) {
						connection
								.setOnReceiveNetworkEventListener(new LoginNetworkEventListener());
						Object[] data = new Object[2];
						data[0] = email;
						data[1] = password;
						connection.sendEvent(new NetworkEvent(
								SharingType.NEW_USER, data));
					}
				}));
		// TODO: close dialog
	}

	public void onNewUserCancelPressed() {
		// TODO: close dialog
	}

	/**
	 * Shows message on disconnect
	 * 
	 * @author Sam
	 */
	public class LoginDisconnectListener implements OnDisconnectListener {
		@Override
		public void onDisconnect(ServerConnection connection) {
			view.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast toast = Toast.makeText(view.getApplicationContext(),
							(CharSequence) "Could not connect to server!",
							Toast.LENGTH_SHORT);
					toast.show();
					// view.showDisconnectDialog();
				}
			});
		}
	}

	private class LoginNetworkEventListener extends HandshakeListener {

		@Override
		public void onReceiveNetworkEvent(final ServerConnection sc,
				final NetworkEvent evt) {
			super.onReceiveNetworkEvent(sc, evt);
			if (evt.getType().equals(SharingType.LOG_IN)) {
				// interpret data
				Object[] dataArray = (Object[]) evt.getData();
				Object[] userArray = new Object[State.getInstance().getMe()
						.serialize().length];
				for (int i = 0; i < State.getInstance().getMe().serialize().length; i++) {
					userArray[i] = dataArray[i];
				}
				UserData myData = new UserData();
				myData.deserialize(userArray);
				// set state based on data received
				State.getInstance().setMe(myData);
				State.getInstance().setClientId(
						(Short) dataArray[dataArray.length - 1]);
				// go to next view
				sc.setOnReceiveNetworkEventListener(null);
				sc.setOnDisconnectListener(null);
				view.startActivity(new Intent(view, ShootActivity.class));
				view.finish();
				/*
				 * lobbyActivity.runOnUiThread(new Runnable() {
				 * 
				 * @Override public void run() { toGame((Short) evt.getData());
				 * } });
				 */
			} else if (evt.getType().equals(SharingType.NEW_USER)) {
				// fetch from view
				String email = "";
				String password = "";
				// interpret data
				Object[] dataArray = (Object[]) evt.getData();
				int myAccountId = (Integer) dataArray[0];
				short myClientId = (Short) dataArray[1];
				// set state based on data received
				UserData myData = new UserData();
				myData.setEmail(email);
				myData.setPassword(password);
				myData.setId(myAccountId);
				State.getInstance().setMe(myData);
				State.getInstance().setClientId(myClientId);
				// go to next view
				sc.setOnReceiveNetworkEventListener(null);
				sc.setOnDisconnectListener(null);
				view.startActivity(new Intent(view, ShootActivity.class));
				view.finish();
				/*
				 * lobbyActivity.runOnUiThread(new Runnable() {
				 * 
				 * @Override public void run() { toGame((Short) evt.getData());
				 * } });
				 */
			}
		}
	}
}
