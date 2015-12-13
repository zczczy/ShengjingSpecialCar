package com.zczczy.leo.shengjingspecialcar.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.jude.swipbackhelper.SwipeBackHelper;
import com.zczczy.leo.shengjingspecialcar.R;
import com.zczczy.leo.shengjingspecialcar.tools.AndroidTool;
import com.zczczy.leo.shengjingspecialcar.viewgroup.MyTitleBar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Leo on 2015/12/10.
 */
@EActivity(R.layout.main_layout)
public class MainActivity extends BaseActivity implements
        LocationSource, AMapLocationListener, AMap.OnMarkerClickListener,
        AMap.OnInfoWindowClickListener, AMap.OnMarkerDragListener, AMap.OnMapLoadedListener,
        AMap.InfoWindowAdapter, AMap.OnCameraChangeListener, AMap.CancelableCallback {

    @ViewById
    MyTitleBar myTitleBar;

    @ViewById
    MapView mapView;

    AMapLocationClient mlocationClient;

    OnLocationChangedListener mListener;

    AMapLocationClientOption mLocationOption;

    Circle circle;

    AMap aMap;

    MarkerOptions markerOption;

    Marker marker2;// 有跳动效果的marker对象

    UiSettings uiSettings;

    Bundle savedInstanceState;

    Marker marker;

    // 自定义系统定位蓝点
    MyLocationStyle myLocationStyle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        /*
         * 设置离线地图存储目录，在下载离线地图或初始化地图设置; 使用过程中可自行设置, 若自行设置了离线地图存储的路径，
		 * 则需要在离线地图下载和使用地图页面都进行路径设置
		 */
        // Demo中为了其他界面可以使用下载的离线地图，使用默认位置存储，屏蔽了自定义设置
        // MapsInitializer.sdcardDir =OffLineMapUtils.getSdCacheDir(this);
    }


    @AfterViews
    void afterView() {
        SwipeBackHelper.getCurrentPage(this).setSwipeBackEnable(false);
        myLocationStyle = new MyLocationStyle();
        // 自定义定位蓝点图标
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.
                fromResource(R.mipmap.ic_map_location_compass));
        // 自定义精度范围的圆形边框颜色
        myLocationStyle.strokeColor(Color.BLACK);
        //自定义精度范围的圆形边框宽度
        myLocationStyle.strokeWidth(0);


        mapView.onCreate(savedInstanceState);// 必须要写
        init();
    }

    /**
     * 初始化AMap对象
     */
    void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
    }


    /**
     * 设置一些amap的属性
     */
    void setUpMap() {
        aMap.setLocationSource(this);// 设置定位监听
        // 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);  //只在第一次定位移动到地图中心点。
//        aMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_ROTATE); //定位、移动到地图中心点，跟踪并根据面向方向旋转地图。
//        aMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_FOLLOW); //定位、移动到地图中心点并跟随。

        float scale = aMap.getScalePerPixel();//一像素代表多少米

        uiSettings = aMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(false);//设置是否显示缩放按钮
        uiSettings.setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_CENTER);//setZoomControlsEnabled 为false 不起作用
        uiSettings.setMyLocationButtonEnabled(true);// 是否显示定位按钮  设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);//  设置为true表示显示定位层并可触发定位， false表示隐藏定位层并不可触发定位，默认是false
        uiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_CENTER);//设置logog的位置
        uiSettings.setCompassEnabled(true);//设置指南针是否显示
        uiSettings.setScrollGesturesEnabled(true);//设置地图是否可以手势滑动
        uiSettings.setZoomGesturesEnabled(true);//设置地图是否可以手势缩放大小
        uiSettings.setTiltGesturesEnabled(true);//设置地图是否可以倾斜
        uiSettings.setRotateGesturesEnabled(true);//设置地图是否可以旋转
        uiSettings.setScaleControlsEnabled(true);//设置地图默认的比例尺是否显示

        aMap.setOnMarkerDragListener(this);// 设置marker可拖拽事件监听器
        aMap.setOnMapLoadedListener(this);// 设置amap加载成功事件监听器
        aMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
        aMap.setOnInfoWindowClickListener(this);// 设置点击infoWindow事件监听器
        aMap.setInfoWindowAdapter(this);// 设置自定义InfoWindow样式
        aMap.setOnCameraChangeListener(this);
        aMap.setMyLocationStyle(myLocationStyle);

        aMap.setMyLocationRotateAngle(90);


    }


    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        Log.e("onLocationChanged", "进入onLocationChanged");
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {

//                //定位成功回调信息，设置相关消息
//                aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
//                aMapLocation.getLatitude();//获取经度
//                aMapLocation.getLongitude();//获取纬度
//                aMapLocation.getAccuracy();//获取精度信息
//                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                Date date = new Date(aMapLocation.getTime());
//                df.format(date);//定位时间
//                aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果
//                aMapLocation.getCountry();//国家信息
//                aMapLocation.getProvince();//省信息
//                aMapLocation.getCity();//城市信息
//                aMapLocation.getDistrict();//城区信息
//                aMapLocation.getRoad();//街道信息
//                aMapLocation.getCityCode();//城市编码
//                aMapLocation.getAdCode();//地区编码
//                Log.e("经度", aMapLocation.getLatitude()+"");
//                Log.e("纬度", aMapLocation.getLongitude()+"");

                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点


            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        Log.e("activate", "进入activate");
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

            //设置是否返回地址信息（默认返回地址信息）
            mLocationOption.setNeedAddress(true);
            //设置是否只定位一次,默认为false
            mLocationOption.setOnceLocation(false);
            //设置是否强制刷新WIFI，默认为强制刷新
            mLocationOption.setWifiActiveScan(true);
            //设置是否允许模拟位置,默认为false，不允许模拟位置
            mLocationOption.setMockEnable(false);

            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    void addMarkersToMap() {

        MarkerOptions markerOptions = new MarkerOptions()
                .title("最快2分钟接驾")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.nova_passenger_driver_targer))
                .draggable(true).period(50);
        marker = aMap.addMarker(markerOptions);
        marker.showInfoWindow();
    }


    /**
     * 监听自定义infowindow窗口的infowindow事件回调
     */
    @Override
    public View getInfoWindow(Marker marker) {
//        if (radioOption.getCheckedRadioButtonId() != R.id.custom_info_contents) {
//            return null;
//        }
//        View infoContent = getLayoutInflater().inflate(
//                R.layout.custom_info_contents, null);
//        render(marker, infoContent);
//        return infoContent;

        return null;
    }


    /**
     * 监听自定义infowindow窗口的infocontents事件回调
     */
    @Override
    public View getInfoContents(Marker marker) {
//        if (radioOption.getCheckedRadioButtonId() != R.id.custom_info_contents) {
//            return null;
//        }
//        View infoContent = getLayoutInflater().inflate(
//                R.layout.custom_info_contents, null);
//        render(marker, infoContent);
//        return infoContent;

        return null;
    }

    /**
     * 监听点击infowindow窗口事件回调
     */
    @Override
    public void onInfoWindowClick(Marker marker) {
        AndroidTool.showToast(this, "你点击了infoWindow窗口" + marker.getTitle());
        AndroidTool.showToast(this, "当前地图可视区域内Marker数量:"
                + aMap.getMapScreenMarkers().size());
    }

    /**
     * 监听amap地图加载成功事件回调
     */
    @Override
    public void onMapLoaded() {
        Log.e("onMapLoaded", "进入onMapLoaded");
        // 设置所有maker显示在当前可视区域地图中
//        LatLngBounds bounds = new LatLngBounds.Builder()
//                .include(latlng).build();
//        aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 150));
        aMap.animateCamera(CameraUpdateFactory.zoomTo(14), 1000, this);

//        aMap.getMapScreenMarkers().get(0).setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_map_location_compass));

    }

    @Override
    public boolean onMarkerClick(Marker marker) {

//        markerText.setText("你点击的是" + marker.getTitle());
        return false;
    }

    /**
     * @param cameraPosition
     * @see com.amap.api.maps.AMap.OnCameraChangeListener
     */
    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        Log.e("onCammeraChange", "进入cameraPosition");
        LatLng target = cameraPosition.target;
        if (marker == null) {
            addMarkersToMap();
        }
        marker.setPosition(target);
//        aMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 1000, this);
    }

    /**
     * @param cameraPosition
     * @see com.amap.api.maps.AMap.OnCameraChangeListener
     */
    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
//        if (aMap != null) {
//            jumpPoint(marker);
//        }
    }


    /**
     * 地图动画效果终止回调方法
     *
     * @see com.amap.api.maps.AMap.CancelableCallback
     */
    @Override
    public void onFinish() {

    }

    /**
     * 地图动画效果完成回调方法
     *
     * @see com.amap.api.maps.AMap.CancelableCallback
     */
    @Override
    public void onCancel() {

    }


    /**
     * 监听开始拖动marker事件回调
     */
    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    /**
     * 监听拖动marker时事件回调
     */
    @Override
    public void onMarkerDrag(Marker marker) {
        Log.e("marker", marker.getTitle());
    }

    /**
     * 监听拖动marker结束事件回调
     */
    @Override
    public void onMarkerDragEnd(Marker marker) {

    }


    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
//        deactivate();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);

    }


    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        deactivate();
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
    }

}
