package com.xingyun.activity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import sun.misc.IOUtils;

import com.xingyun.setting.Configuration;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class SignupActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newsignup);

		findViewById(R.id.btn_signup).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String cellPhone = ((EditText)findViewById(R.id.txt_cellphone)).getText().toString();
				String contactName = ((EditText)findViewById(R.id.txt_contactname)).getText().toString();
				String password = ((EditText)findViewById(R.id.txt_password)).getText().toString();
				
				if(cellPhone.length() == 0 ){
					new AlertDialog.Builder(SignupActivity.this)
				    .setMessage("请填写手机号码")
				    .setPositiveButton("知道了", new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) { 
				        }
				     })
				     .show();
					return;
				}
				if(contactName.length() == 0 ){
					new AlertDialog.Builder(SignupActivity.this)
				    .setMessage("请填写联系人姓名")
				    .setPositiveButton("知道了", new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) { 
				        }
				     })
				     .show();
					return;
				}
				if(password.length() == 0 ){
					new AlertDialog.Builder(SignupActivity.this)
				    .setMessage("请设置密码")
				    .setPositiveButton("知道了", new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) { 
				        }
				     })
				     .show();
					return;
				}
				
				int result = callSignupService(cellPhone, password, contactName);
				if(result == 1){
					new AlertDialog.Builder(SignupActivity.this)
				    .setMessage("您的信息已经注册过了,无需再注册")
				    .setPositiveButton("知道了", new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) { 
				        	Intent intent = new Intent();
							intent.setClass(SignupActivity.this,
									NavigationHomeAcvitity.class);
							startActivity(intent);
				        }
				     })
				     .show();
				}else if(result == 0){
					new AlertDialog.Builder(SignupActivity.this)
				    .setMessage("注册成功")
				    .setPositiveButton("知道了", new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) { 
				        	Intent intent = new Intent();
							intent.setClass(SignupActivity.this,
									NavigationHomeAcvitity.class);
							startActivity(intent);
				        }
				     })
				     .show();
				}
			}
		});
	}

	private int callSignupService(String userName, String password,
			String contactName) {
		String data = "{\"name\" : \"" + userName + "\", \"password\" : \""
				+ password + "\", \"contact_name\" : \"" + contactName + "\",\"contact_phone\" : \""+userName+"\"}";
		HttpPut httpPut = new HttpPut(Configuration.WS_SIGNUP);
		HttpParams params = new BasicHttpParams();

		params.setParameter(CoreConnectionPNames.SO_TIMEOUT,
				Configuration.WS_SOCKETTIMEOUT);
		params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
				Configuration.WS_CONNTIMEOUT);
		try {
			StringEntity se = new StringEntity(data, "UTF-8");
			httpPut.setEntity(se);

			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpPut);
			Log.d("mingqi", "status code: "
					+ httpResponse.getStatusLine().getStatusCode());
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				return 0;
			} else if (httpResponse.getStatusLine().getStatusCode() == 400) {
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}

		return 1;
	}
}
