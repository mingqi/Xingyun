package com.xingyun.activity;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
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

public class SignupActivity extends Activity {
	private String nextStep;
	private SignupActivity thisActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);

		thisActivity = this;
		try {
			nextStep = this.getIntent().getStringExtra("nextstep");
		} catch (Exception ex) {
			nextStep = "";
		}

		final EditText txtUsername = (EditText) this
				.findViewById(R.id.txt_username);
		final EditText txtPassword = (EditText) this
				.findViewById(R.id.txt_password);
		final EditText txtRePassword = (EditText) this
				.findViewById(R.id.txt_repassword);
		final EditText txtRealName = (EditText) this
				.findViewById(R.id.txt_realname);

		Button btnSignup = (Button) this.findViewById(R.id.btn_signup);
		btnSignup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				try {
					((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
							.hideSoftInputFromWindow(SignupActivity.this
									.getCurrentFocus().getWindowToken(),
									InputMethodManager.HIDE_NOT_ALWAYS);
				} catch (Exception ex) {
				}

				String username = txtUsername.getText().toString();
				String password = txtPassword.getText().toString();
				String rePassword = txtRePassword.getText().toString();
				String realName = txtRealName.getText().toString();

				if (username.trim().equals("")) {
					Toast toast = Toast.makeText(getApplicationContext(),
							getResources().getString(R.string.empty_username),
							Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				} else if (realName.trim().equals("")) {
					Toast toast = Toast.makeText(getApplicationContext(),
							getResources().getString(R.string.realname),
							Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				} else if (!password.equals(rePassword)) {
					Toast toast = Toast.makeText(
							getApplicationContext(),
							getResources().getString(
									R.string.different_password),
							Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				} else {
					String data = "{\"name\":\"" + username
							+ "\",\"password\" : \"" + password
							+ "\",\"contact_name\": \"" + realName + "\"}";

					// post data
					HttpPut httpPut = new HttpPut(Configuration.WS_SIGNUP);

					HttpParams params = new BasicHttpParams();

					params.setParameter(CoreConnectionPNames.SO_TIMEOUT,
							Configuration.WS_SOCKETTIMEOUT);
					params.setParameter(
							CoreConnectionPNames.CONNECTION_TIMEOUT,
							Configuration.WS_CONNTIMEOUT);

					try {
						StringEntity se = new StringEntity(data, "UTF-8");
						httpPut.setEntity(se);
						HttpResponse httpResponse = new DefaultHttpClient()
								.execute(httpPut);

						// 200 ok
						if (httpResponse.getStatusLine().getStatusCode() == 200) {
							// {"customer_id": 3, "name": "Uu"}
							JSONObject user = new JSONObject(StringUtility
									.inputstreamToString(httpResponse
											.getEntity().getContent()));

							UserManager.setName(username);
							UserManager.setContactName(realName);
							UserManager.setId(Integer.parseInt(user
									.getString("customer_id")));
							UserManager.setLogin(true);

							goNextStep();

						} else {
							Toast toast = Toast.makeText(
									getApplicationContext(),
									getResources().getString(
											R.string.plz_check_input),
									Toast.LENGTH_LONG);
							toast.setGravity(Gravity.BOTTOM, 0, 0);
							toast.show();
						}
					} catch (Exception e) {
						Toast toast = Toast.makeText(
								getApplicationContext(),
								getResources().getString(
										R.string.failed_to_get_data),
								Toast.LENGTH_LONG);
						toast.setGravity(Gravity.BOTTOM, 0, 0);
						toast.show();
					}
				}
			}

		});

		Button btnLogin = (Button) this.findViewById(R.id.btn_login);
		btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
							.hideSoftInputFromWindow(SignupActivity.this
									.getCurrentFocus().getWindowToken(),
									InputMethodManager.HIDE_NOT_ALWAYS);
				} catch (Exception ex) {
				}

				View view = UserProfileActivityGroup.group
						.getLocalActivityManager()
						.startActivity(
								"activity_login",
								new Intent(SignupActivity.this,
										LoginActivity.class)
										.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
						.getDecorView();
				UserProfileActivityGroup.group.setContentView(view);

			}

		});
	}

	private void goNextStep() {
		if (this.nextStep.equals("order")) {
			View view = UserProfileActivityGroup.group
					.getLocalActivityManager()
					.startActivity(
							"activity_orderinfo",
							new Intent(SignupActivity.this,
									OrderInfoActivity.class)).getDecorView();
			UserProfileActivityGroup.group.setContentView(view);
		} else {
			View view = UserProfileActivityGroup.group
					.getLocalActivityManager()
					.startActivity(
							"activity_userprofile",
							new Intent(SignupActivity.this,
									UserProfileActivity.class)
									.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
					.getDecorView();
			UserProfileActivityGroup.group.setContentView(view);
		}
	}
}