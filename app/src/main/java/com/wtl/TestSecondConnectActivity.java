package com.wtl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wtl.service.BluetoothLeService;
import com.wtl.ui.Ble_Activity;

public class TestSecondConnectActivity extends Activity {

	private TextView sed_connect_state, sed_resp_bit_data;

	private boolean mConnected = false;
	private String status = "disconnected";

	private static BluetoothLeService mBluetoothLeService;
	private ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics = new ArrayList<ArrayList<BluetoothGattCharacteristic>>();

	public static String HEART_RATE_MEASUREMENT = "0000ffe1-0000-1000-8000-00805f9b34fb";
	public static String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
	public static String EXTRAS_DEVICE_RESP = "DEVICE_RESP";
	public static String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
	public static String EXTRAS_DEVICE_RSSI = "RSSI";
	//
	private static BluetoothGattCharacteristic target_chara = null;
	//
	private String mDeviceName;
	//
	private String mDeviceAddress;
	//
	private String mRssi;
	private Bundle b;
	private String rev_str = "";
	private String respData;
	
	private Handler mhandler = new Handler();
	private Handler myHandler = new Handler() {
		// 2.
		public void handleMessage(Message msg) {
			switch (msg.what) {
			//
			case 1: {
				//
				String state = msg.getData().getString("sed_connect_state");
				sed_connect_state.setText(state);
				break;
			}
			}
			super.handleMessage(msg);
		}
	};

	public void onCreate(Bundle arg0) {
		final View view = View.inflate(this, R.layout.activity_second, null);
		setContentView(view);
		super.onCreate(arg0);
		b = getIntent().getExtras();
		System.out.println("bbbb"+b.toString());
		//
		mDeviceName = b.getString(EXTRAS_DEVICE_NAME);
		mDeviceAddress = b.getString(EXTRAS_DEVICE_ADDRESS);
		mRssi = b.getString(EXTRAS_DEVICE_RSSI);
		
		/*  */
		Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
		bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
		
		mDeviceName = b.getString(EXTRAS_DEVICE_NAME);
		mDeviceAddress = b.getString(EXTRAS_DEVICE_ADDRESS);
		mRssi = b.getString(EXTRAS_DEVICE_RSSI);
//		respData=b.getString(EXTRAS_DEVICE_RESP);
		init();
		
	}

	/** 
	* @Title: init 
	* @Description: TODO()
	* @param
	* @return void    
	* @throws 
	*/ 
	private void init()
	{	
		//
		sed_connect_state = (TextView) this.findViewById(R.id.sed_connect_state);
		sed_connect_state.setText(status);
		//
		sed_resp_bit_data = (TextView) this.findViewById(R.id.sed_resp_bit_data);
		sed_resp_bit_data.setText(respData);

	}
	/*  */
	private void updateConnectionState(String status)
	{
//		sed_connect_state = (TextView) this.findViewById(R.id.sed_connect_state);
//		sed_connect_state.setText(status);
//		Message msg = new Message();
//		msg.what = 1;
//		Bundle b = new Bundle();
//		b.putString("sed_connect_state", status);
//		msg.setData(b);
//		//
//		myHandler.sendMessage(msg);
		
		
		runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				sed_connect_state.setText("ok");
			}
		});


	}
	/**
	 *
	 */
	private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver()
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			final String action = intent.getAction();
			if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action))//
			{
				mConnected = true;
				status = "connected";
				//
				updateConnectionState(status);
				System.out.println("BroadcastReceiver :" + "device connected");

			} else if (BluetoothLeService.ACTION_GATT_DISCONNECTED//
					.equals(action))
			{
				mConnected = false;
				status = "disconnected";
				//
				updateConnectionState(status);
				System.out.println("BroadcastReceiver :"
						+ "device disconnected");

			} else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED//
					.equals(action))
			{
				// Show all the supported services and characteristics on the
				// user interface.
				//
				displayGattServices(mBluetoothLeService
						.getSupportedGattServices());
				System.out.println("BroadcastReceiver :"
						+ "device SERVICES_DISCOVERED");
			} else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action))//
			{    
				 //
				sed_resp_bit_data.setText(intent.getExtras().getString(
						BluetoothLeService.EXTRA_DATA));
			
				
				
			}
		}
	};
	/** 
	* @Title: displayGattServices 
	* @Description: TODO()
	* @param
	* @return void  
	* @throws 
	*/ 
	private void displayGattServices(List<BluetoothGattService> gattServices)
	{

		if (gattServices == null)
			return;
		String uuid = null;
		String unknownServiceString = "unknown_service";
		String unknownCharaString = "unknown_characteristic";

		//
		ArrayList<HashMap<String, String>> gattServiceData = new ArrayList<HashMap<String, String>>();

		//
		ArrayList<ArrayList<HashMap<String, String>>> gattCharacteristicData = new ArrayList<ArrayList<HashMap<String, String>>>();

		//
		mGattCharacteristics = new ArrayList<ArrayList<BluetoothGattCharacteristic>>();

		// Loops through available GATT Services.
		for (BluetoothGattService gattService : gattServices)
		{

			//
			HashMap<String, String> currentServiceData = new HashMap<String, String>();
			uuid = gattService.getUuid().toString();

			//

			gattServiceData.add(currentServiceData);

			System.out.println("Service uuid:" + uuid);

			ArrayList<HashMap<String, String>> gattCharacteristicGroupData = new ArrayList<HashMap<String, String>>();

			//
			List<BluetoothGattCharacteristic> gattCharacteristics = gattService
					.getCharacteristics();

			ArrayList<BluetoothGattCharacteristic> charas = new ArrayList<BluetoothGattCharacteristic>();

			// Loops through available Characteristics.
			//
			for (final BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics)
			{
				charas.add(gattCharacteristic);
				HashMap<String, String> currentCharaData = new HashMap<String, String>();
				uuid = gattCharacteristic.getUuid().toString();

				if (gattCharacteristic.getUuid().toString()
						.equals(HEART_RATE_MEASUREMENT))
				{
					//
					mhandler.postDelayed(new Runnable()
					{

						@Override
						public void run()
						{
							// TODO Auto-generated method stub
							mBluetoothLeService
									.readCharacteristic(gattCharacteristic);
						}
					}, 200);

					//
					mBluetoothLeService.setCharacteristicNotification(
							gattCharacteristic, true);
					target_chara = gattCharacteristic;
					//
					//
					// mBluetoothLeService.writeCharacteristic(gattCharacteristic);
				}
				List<BluetoothGattDescriptor> descriptors = gattCharacteristic
						.getDescriptors();
				for (BluetoothGattDescriptor descriptor : descriptors)
				{
					System.out.println("---descriptor UUID:"
							+ descriptor.getUuid());
					//
					mBluetoothLeService.getCharacteristicDescriptor(descriptor);
					// mBluetoothLeService.setCharacteristicNotification(gattCharacteristic,
					// true);
				}

				gattCharacteristicGroupData.add(currentCharaData);
			}
			//
			mGattCharacteristics.add(charas);
			//
			gattCharacteristicData.add(gattCharacteristicGroupData);

		}

	}
	/*  */
	private final ServiceConnection mServiceConnection = new ServiceConnection()
	{

		@Override
		public void onServiceConnected(ComponentName componentName,
				IBinder service)
		{
			mBluetoothLeService = ((BluetoothLeService.LocalBinder) service)
					.getService();
			if (!mBluetoothLeService.initialize())
			{
				finish();
			}
			// Automatically connects to the device upon successful start-up
			// initialization.
			//
			mBluetoothLeService.connect(mDeviceAddress);

		}

		@Override
		public void onServiceDisconnected(ComponentName componentName)
		{
			mBluetoothLeService = null;
		}

	};
	//
		@Override
		protected void onResume()
		{
			super.onResume();
			//
			registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
			//
			//TODO
			registerBoradcastReceiver();
			if (mBluetoothLeService != null)
			{    
				//
				final boolean result = mBluetoothLeService.connect(mDeviceAddress);
				if(result){
					status="connected";
				}else{
					status="disConnected";
					
				}
			}else{
				status="connected";
			}
			sed_connect_state.setText(status);
		}
		/*  */
		private static IntentFilter makeGattUpdateIntentFilter()
		{
			final IntentFilter intentFilter = new IntentFilter();
			intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
			intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
			intentFilter
					.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
			intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
			return intentFilter;
		}
		@Override
		protected void onDestroy()
		{
			super.onDestroy();
	        //
			unregisterReceiver(mGattUpdateReceiver);
			mBluetoothLeService = null;
			
		}
	    private void registerBoradcastReceiver() {
	        IntentFilter stateChangeFilter = new IntentFilter(
	                BluetoothAdapter.ACTION_STATE_CHANGED);
	        IntentFilter connectedFilter = new IntentFilter(
	                BluetoothDevice.ACTION_ACL_CONNECTED);
	        IntentFilter disConnectedFilter = new IntentFilter(
	                BluetoothDevice.ACTION_ACL_DISCONNECTED);
	        registerReceiver(stateChangeReceiver, stateChangeFilter);
	        registerReceiver(stateChangeReceiver, connectedFilter);
	        registerReceiver(stateChangeReceiver, disConnectedFilter);
	    }

	        private BroadcastReceiver stateChangeReceiver = new BroadcastReceiver() {
	        @Override
	        public void onReceive(Context context, Intent intent) {
	            String action = intent.getAction();
	            if (BluetoothDevice.ACTION_ACL_CONNECTED .equals(action)) {
	            	Toast.makeText(TestSecondConnectActivity.this, "ACTION_ACL_CONNECTED", Toast.LENGTH_LONG)
	    			.show();
	            }
	            if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
	            	Toast.makeText(TestSecondConnectActivity.this, "ACTION_ACL_DISCONNECTED", Toast.LENGTH_LONG)
	    			.show();
	            }
	            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
	            	Toast.makeText(TestSecondConnectActivity.this, "ACTION_STATE_CHANGED", Toast.LENGTH_LONG)
	    			.show();
	            }
	        }
	    };
}