package com.xingyun.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import android.support.v4.app.FragmentActivity;

public class MapActivity extends FragmentActivity {
	static double lat = 41.7702;
	static double lon = 86.1598;

	BMapManager mBMapMan = null;
	static MapView mMapView = null;

	// 存放overlayitem
	public List<OverlayItem> mGeoList = new ArrayList<OverlayItem>();
	// 存放overlay图片
	public List<Drawable> res = new ArrayList<Drawable>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBMapMan = new BMapManager(getApplication());
		mBMapMan.init("D63FFC1CDEF9F5177357B644428FB8B9558E8525", null);
		// 注意：请在试用setContentView前初始化BMapManager对象，否则会报错
		setContentView(R.layout.activity_map);
		mMapView = (MapView) findViewById(R.id.bmapsView);
		mMapView.setBuiltInZoomControls(true);
		// 设置启用内置的缩放控件
		MapController mMapController = mMapView.getController();
		// 得到mMapView的控制权,可以用它控制和驱动平移和缩放
		GeoPoint point = new GeoPoint((int) (lat * 1E6), (int) (lon * 1E6));
		// 用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
		mMapController.setCenter(point);// 设置地图中心点
		mMapController.setZoom(20);// 设置地图zoom级别

		Drawable marker = getResources().getDrawable(R.drawable.icon_marka); // 得到需要标在地图上的资源
		mMapView.getOverlays().add(new OverItem(marker, this)); // 添加ItemizedOverlay实例到mMapView
		mMapView.refresh();// 刷新地图

		Button btnBack = (Button) findViewById(R.id.btn_back);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}

		});
	}

	@Override
	protected void onDestroy() {
		mMapView.destroy();
		if (mBMapMan != null) {
			mBMapMan.destroy();
			mBMapMan = null;
		}
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		if (mBMapMan != null) {
			mBMapMan.stop();
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		if (mBMapMan != null) {
			mBMapMan.start();
		}
		super.onResume();
	}
}

class OverItem extends ItemizedOverlay<OverlayItem> {
	private List<OverlayItem> GeoList = new ArrayList<OverlayItem>();
	private Context mContext;

	public OverItem(Drawable marker, Context context) {
		super(marker);

		this.mContext = context;

		// 用给定的经纬度构造GeoPoint，单位是微度 (度 * 1E6)
		GeoPoint p = new GeoPoint((int) (MapActivity.lat * 1E6),
				(int) (MapActivity.lon * 1E6));

		GeoList.add(new OverlayItem(p, "P", "行运餐厅\n人民东路银星大酒店旁"));
		populate(); // createItem(int)方法构造item。一旦有了数据，在调用其它方法前，首先调用这个方法
	}

	@Override
	protected OverlayItem createItem(int i) {
		return GeoList.get(i);
	}

	@Override
	public int size() {
		return GeoList.size();
	}

	@Override
	// 处理当点击事件
	protected boolean onTap(int i) {
		// Drawable marker = this.mContext.getResources().getDrawable(
		// R.drawable.pop); // 得到需要标在地图上的资源
		// BitmapDrawable bd = (BitmapDrawable) marker;
		// Bitmap popbitmap = bd.getBitmap();
		//
		// PopupOverlay pop = new PopupOverlay(MapActivity.mMapView,
		// new PopupClickListener() {
		//
		// @Override
		// public void onClickedPopup() {
		// }
		// });
		// pop.showPopup(popbitmap, new GeoPoint((int) (MapActivity.lat * 1E6),
		// (int) (MapActivity.lon * 1E6)), 32);

		Toast.makeText(this.mContext, GeoList.get(i).getSnippet(),
				Toast.LENGTH_SHORT).show();
		return true;
	}
}
