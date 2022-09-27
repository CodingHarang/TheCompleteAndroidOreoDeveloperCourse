package com.harang.bluetoothfinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

  ListView listView;
  TextView statusTextView;
  Button searchButton;
  ArrayList<String> bluetoothDevices = new ArrayList<>();
  ArrayList<String> addresses = new ArrayList<>();
  ArrayAdapter arrayAdapter;

  BluetoothAdapter bluetoothAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    listView = findViewById(R.id.listView);
    statusTextView = findViewById(R.id.statusTextView);
    searchButton = findViewById(R.id.searchButton);

    arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);

    listView.setAdapter(arrayAdapter);

    searchButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        searchClicked(view);
      }
    });

    bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
    intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
    intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
    intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
    registerReceiver(broadcastReceiver, intentFilter);
  }

  public void searchClicked(View view) {
    statusTextView.setText("Searching...");
    searchButton.setEnabled(false);
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_SCAN}, 1);
    }
    bluetoothDevices.clear();
    addresses.clear();
    bluetoothAdapter.startDiscovery();
  }

  private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
      String action = intent.getAction();
      Log.i("Action", action);

      if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
        statusTextView.setText("Finished");
        searchButton.setEnabled(true);
      } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
        }
        String name = device.getName();
        String address = device.getAddress();
        String rssi = Integer.toString(intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE));
        Log.i("Device Found", "Name: " + name + " Address: " + address + " RSSI: " + rssi);
        String deviceString = "";

        if(addresses.contains(address)) {
          addresses.add(address);
          if(name == null || name.equals("")) {
            deviceString =  address + " - RSSI " + rssi + "dBm";
          } else {
            deviceString =  name + " - RSSI " + rssi + "dBm";
          }
          bluetoothDevices.add(deviceString);
          arrayAdapter.notifyDataSetChanged();
        }
      }
    }
  };
}