package com.xingyun.activity;

import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import com.xingyun.persistence.UserManager;
import com.xingyun.setting.Configuration;
import com.xingyun.utility.StringUtility;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	private LoginActivity thisActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		thisActivity = this;

		if (UserManager.isLogin()) {
			View view = UserProfileActivityGroup.group
					.getLocalActivityManager()
					.startActivity(
							"activity_userprofile",
							new Intent(LoginActivity.this,
									UserProfileActivity.class)
									.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
					.getDecorView();
			UserProfileActivityGroup.group.setContentView(view);
		}

		final EditText txtUsername = (EditText) this
				.findViewById(R.id.txt_username);
		final EditText txtPassword = (EditText) this
				.findViewById(R.id.txt_password);

		Button btnSignup = (Button) this.findViewById(R.id.btn_signup);
		btnSignup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
							.hideSoftInputFromWindow(LoginActivity.this
									.getCurrentFocus().getWindowToken(),
									InputMethodManager.HIDE_NOT_ALWAYS);
				} catch (Exception ex) {
				}
				View view = UserProfileActivityGroup.group
						.getLocalActivityManager()
						.startActivity(
								"activity_signup",
								new Intent(LoginActivity.this,
										SignupActivity.class)
										.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
						.getDecorView();
				UserProfileActivityGroup.group.setContentView(view);
			}

		});

		Button btnLogin = (Button) this.findViewById(R.id.btn_login);
		btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try {
					((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
							.hideSoftInputFromWindow(LoginActivity.this
									.getCurrentFocus().getWindowToken(),
									InputMethodManager.HIDE_NOT_ALWAYS);
				} catch (Exception ex) {
				}

				String username = txtUsername.getText().toString();
				String password = txtPassword.getText().toString();

				String path = Configuration.WS_LOGIN.replace("__1__", username)
						.replace("__2__", password);

				HttpGet httpGet = new HttpGet(path);

				HttpParams httpParameters = new BasicHttpParams();
				HttpConnectionParams.setConnectionTimeout(httpParameters,
						Configuration.WS_CONNTIMEOUT);
				HttpConnectionParams.setSoTimeout(httpParameters,
						Configuration.WS_SOCKETTIMEOUT);
				DefaultHttpClient httpClient = new DefaultHttpClient(
						httpParameters);
				HttpResponse response;
				try {
					response = httpClient.execute(httpGet);
					InputStream is = response.getEntity().getContent();
					String responseMsg = "";
					try {
						responseMsg = StringUtility.inputstreamToString(is);
						Log.e("====dd===", responseMsg);
					} catch (Exception e) {
						Toast toast = Toast.makeText(
								getApplicationContext(),
								getResources().getString(
										R.string.failed_to_get_data),
								Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}
					Log.e("===", response.getStatusLine().getStatusCode() + "");
					if (response.getStatusLine().getStatusCode() == 200) {
						// {"customer_id": 1, "contact_phone": "26", "name":
						// "mingqi1", "contact_name": "\u516b\u70b9"}

						JSONObject user = new JSONObject(responseMsg);
						int id = user.getInt("customer_id");
						String name = user.getString("name");
						String contactName = user.getString("contact_name");

						UserManager.setId(id);
						UserManager.setContactName(contactName);
						UserManager.setName(name);
						UserManager.setLogin(true);

						View view = UserProfileActivityGroup.group
								.getLocalActivityManager()
								.startActivity(
										"activity_userprofile",
										new Intent(LoginActivity.this,
												UserProfileActivity.class)
												.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
								.getDecorView();
						UserProfileActivityGroup.group.setContentView(view);

					} else {
						Toast toast = Toast.makeText(
								getApplicationContext(),
								getResources().getString(
										R.string.wrong_username_or_password),
								Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}

				} catch (Exception e) {
					Toast toast = Toast.makeText(
							getApplicationContext(),
							getResources().getString(
									R.string.failed_to_get_data),
							Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				}
			}

		});

	}
}
