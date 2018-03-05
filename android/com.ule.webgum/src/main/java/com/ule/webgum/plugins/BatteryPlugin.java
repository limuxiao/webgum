package com.ule.webgum.plugins;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.view.View;

import com.ule.webgum.annotation.JSMethod;
import com.ule.webgum.core.IWebgumPlugin;
import com.ule.webgum.core.IWebgumView;
import com.ule.webgum.core.JSRequest;
import com.ule.webgum.core.JsResponse;

/**
 * @Title:
 * @Desc:
 * @CreateTime: 2018-01-29 10:39
 * @Creator: Liy
 * @Version: 1.0
 */

public class BatteryPlugin extends IWebgumPlugin {

	private BatteryBroadcastReceiver receiver;

	public BatteryPlugin(String pluginName, String pluginVersion) {
		super(pluginName, pluginVersion);
	}


	@JSMethod()
	public void getPower(JSRequest request, JsResponse response){
		if (receiver == null){
			receiver = new BatteryBroadcastReceiver(request, response);
		}
		registBattery(request.webgumView);
	}


	//注册电量监听的Broadcastreceiver
	public void registBattery(IWebgumView view){
		IntentFilter intentFilter=getFilter();
		view.getContext().registerReceiver(receiver,intentFilter);

	}
	//取消注册电量监听的Broadcastreceiver
	public void unRegistBattery(View view){
		view.getContext().unregisterReceiver(receiver);
	}

	///获取IntentFilter对象
	private IntentFilter getFilter() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_BATTERY_CHANGED);
		filter.addAction(Intent.ACTION_BATTERY_LOW);
		filter.addAction(Intent.ACTION_BATTERY_OKAY);
		return filter;
	}


	class BatteryBroadcastReceiver extends BroadcastReceiver{

		private JSRequest request;
		private JsResponse response;

		public BatteryBroadcastReceiver(JSRequest request, JsResponse response) {
			this.request = request;
			this.response = response;
		}

		@Override
		public void onReceive(Context context, Intent intent) {
			final String action = intent.getAction();
			if (action.equalsIgnoreCase(Intent.ACTION_BATTERY_CHANGED)) {
				// 当前电池的电压
				int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1);
				// 电池的健康状态
				int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, -1);
				String s = "";
				switch (health) {
					case BatteryManager.BATTERY_HEALTH_GOOD:
						s = "很好";
					case BatteryManager.BATTERY_HEALTH_COLD:
						s = "BATTERY_HEALTH_COLD";
						break;
					case BatteryManager.BATTERY_HEALTH_DEAD:
						s = "BATTERY_HEALTH_DEAD";
						break;
					case BatteryManager.BATTERY_HEALTH_OVERHEAT:
						s = "BATTERY_HEALTH_OVERHEAT";
						break;
					case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
						s = "BATTERY_HEALTH_OVER_VOLTAGE";
						break;
					case BatteryManager.BATTERY_HEALTH_UNKNOWN:
						s = "BATTERY_HEALTH_UNKNOWN";
						break;
					case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
						s = "BATTERY_HEALTH_UNSPECIFIED_FAILURE";
						break;
					default:
						break;
				}
				// 电池当前的电量, 它介于0和 EXTRA_SCALE之间
				int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
				// 电池电量的最大值
				int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
				// 当前手机使用的是哪里的电源
				int pluged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
				switch (pluged) {
					case BatteryManager.BATTERY_PLUGGED_AC:
						// 电源是AC charger.[应该是指充电器]
						s = "电源是AC charger.";
						break;
					case BatteryManager.BATTERY_PLUGGED_USB:
						// 电源是USB port
						s = "电源是USB port";
						break;
					default:
						break;
				}
				int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
				switch (status) {
					case BatteryManager.BATTERY_STATUS_CHARGING:
						// 正在充电
						s = "正在充电";
						break;
					case BatteryManager.BATTERY_STATUS_DISCHARGING:
						s = "BATTERY_STATUS_DISCHARGING";
						break;
					case BatteryManager.BATTERY_STATUS_FULL:
						// 充满
						s = "充满";
						break;
					case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
						// 没有充电
						s = "没有充电";
						break;
					case BatteryManager.BATTERY_STATUS_UNKNOWN:
						// 未知状态
						s = "未知状态";
						break;
					default:
						break;
				}
				// 电池使用的技术。比如，对于锂电池是Li-ion
				String technology = intent.
						getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);
				// 当前电池的温度
				int temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1);
				String str= "voltage = " + voltage + " technology = "
						+ technology + " temperature = " + temperature;
			} else if (action.equalsIgnoreCase(Intent.ACTION_BATTERY_LOW)) {
				// 表示当前电池电量低
			} else if (action.equalsIgnoreCase(Intent.ACTION_BATTERY_OKAY)) {
				// 表示当前电池已经从电量低恢复为正常
			}

		}
	}
}
