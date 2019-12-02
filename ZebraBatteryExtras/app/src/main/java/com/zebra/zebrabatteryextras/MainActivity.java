package com.zebra.zebrabatteryextras;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = "ZebraBattery";
    final ArrayList<String> list = new ArrayList<String>();
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        final ListView listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(adapter);

        //  Battery info
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = registerReceiver(null, ifilter);
        handleBatteryIntent(batteryStatus);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryReceiver, ifilter);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        unregisterReceiver(batteryReceiver);
    }

    private BroadcastReceiver batteryReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == Intent.ACTION_BATTERY_CHANGED)
                handleBatteryIntent(intent);
        }
    };

    private void handleBatteryIntent(Intent batteryStatus) {
        float percentage = 0.0f;
        if (batteryStatus != null) {
            boolean present = batteryStatus.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false);
            String presentTxt = "No battery";
            if (present)
                presentTxt = "Yes";
            int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            if (level != -1 && scale != -1)
                percentage = (level / (float) scale) * 100f;
            else
                percentage = -1;
            String percentageVal = "unknown";
            if (percentage > -1)
                percentageVal = String.format("%.1f", percentage) + "%";
            int pluggedIn = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            String pluggedInStatus = "unknown";
            if (pluggedIn == 0)
                pluggedInStatus = "Not Plugged In";
            else if (pluggedIn == BatteryManager.BATTERY_PLUGGED_AC)
                pluggedInStatus = "Plugged In (AC)";
            else if (pluggedIn == BatteryManager.BATTERY_PLUGGED_USB)
                pluggedInStatus = "Plugged In (USB)";
            else if (pluggedIn == BatteryManager.BATTERY_PLUGGED_WIRELESS)
                pluggedInStatus = "Charging Wirelessly";
            int health = batteryStatus.getIntExtra(BatteryManager.EXTRA_HEALTH, -1);
            String healthTxt = "unknown";
            if (health == BatteryManager.BATTERY_HEALTH_COLD)
                healthTxt = "Cold";
            else if (health == BatteryManager.BATTERY_HEALTH_DEAD)
                healthTxt = "Dead";
            else if (health == BatteryManager.BATTERY_HEALTH_GOOD)
                healthTxt = "Good";
            else if (health == BatteryManager.BATTERY_HEALTH_OVERHEAT)
                healthTxt = "Overheat";
            else if (health == BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE)
                healthTxt = "Over voltage";
            else if (health == BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE)
                healthTxt = "Unspecified failure";

            int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            String statusTxt = "unknown";
            if (status == BatteryManager.BATTERY_STATUS_CHARGING)
                statusTxt = "Charging";
            else if (status == BatteryManager.BATTERY_STATUS_DISCHARGING)
                statusTxt = "Discharging";
            else if (status == BatteryManager.BATTERY_STATUS_FULL)
                statusTxt = "Full";
            else if (status == BatteryManager.BATTERY_STATUS_NOT_CHARGING)
                statusTxt = "Not Charging";

            String technologyTxt = batteryStatus.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);
            int temperature = batteryStatus.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1);
            String temperatureTxt = "unknown";
            if (temperature > -1)
                temperatureTxt = "" + (((float)temperature)/10) + "°c";
            int voltage = batteryStatus.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1);
            String voltageTxt = "unknown";
            if (voltage > -1)
                voltageTxt = "" + voltage + "mv";


            for (String key : batteryStatus.getExtras().keySet()) {
                Log.v(LOG_TAG, key);
            }

            //  Zebra specific battery extras
            String manufactureDate = batteryStatus.getStringExtra("mfd");
            if (manufactureDate == null)
                manufactureDate = "unknown";
            String partNumber = batteryStatus.getStringExtra("partnumber");
            if (partNumber == null)
                partNumber = "unknown";
            String serialNumber = batteryStatus.getStringExtra("serialnumber");
            if (serialNumber == null)
                serialNumber = "unknown";
            int backupBatteryVoltage = batteryStatus.getIntExtra("bkvoltage", -1);
            String backupBatteryVoltageTxt = "unknown";
            if (backupBatteryVoltage > -1)
                backupBatteryVoltageTxt = "" + backupBatteryVoltage + "mV";
            int ratedCapacity = batteryStatus.getIntExtra("ratedcapacity", -1);
            String ratedCapacityTxt = "unknown";
            if (ratedCapacity > -1)
                ratedCapacityTxt = "" + ratedCapacity + "mAh";
            int decommissionStatus = batteryStatus.getIntExtra("battery_decommission", -1); //  0, 1, 2
            String decommissionStatusTxt = "unknown";
            if (decommissionStatus == 0)
                decommissionStatusTxt = "Good";
            else if (decommissionStatus == 1)
                decommissionStatusTxt = "Decommissioned";
            int cumulativeCharge = batteryStatus.getIntExtra("base_cumulative_charge", -1);
            String cumulativeChargeTxt = "unknown";
            if (cumulativeCharge > -1)
                cumulativeChargeTxt = "" + cumulativeCharge + "mAh";
            int numberChargeCycles = batteryStatus.getIntExtra("battery_usage_numb", -1);
            String numberChargeCyclesTxt = "unknown";
            if (numberChargeCycles > -1)
                numberChargeCyclesTxt = "" + numberChargeCycles;
            int cumulativeChargeAll = batteryStatus.getIntExtra("total_cumulative_charge", -1);
            String cumulativeChargeAllTxt = "unknown";
            if (cumulativeChargeAll > -1)
                cumulativeChargeAllTxt = "" + cumulativeChargeAll + "mAh";
            int secondsSinceFirstCharge = batteryStatus.getIntExtra("seconds_since_first_use", -1);
            String secondsSinceFirstChargeTxt = "unknown";
            if (secondsSinceFirstCharge > -1)
                secondsSinceFirstChargeTxt = "" + secondsSinceFirstCharge + "s";
            int presentCapacity = batteryStatus.getIntExtra("present_capacity", -1);
            String presentCapacityTxt = "unknown";
            if (presentCapacity > -1)
                presentCapacityTxt = "" + presentCapacity + "mAh";
            int zebraHealth = batteryStatus.getIntExtra("health_percentage", -1);
            String zebraHealthTxt = "unknown";
            if (zebraHealth > -1)
                zebraHealthTxt = "" + zebraHealth + "%";
            int timeUntilEmpty = batteryStatus.getIntExtra("time_to_empty", -1);  //  65535 is 'unknown'
            String timeUntilEmptyTxt = "unknown";
            if (timeUntilEmpty > -1 && timeUntilEmpty < 65535)
                timeUntilEmptyTxt = "" + timeUntilEmpty + "mins";
            int timeUntilFull = batteryStatus.getIntExtra("time_to_full", -1);  //  65535 is 'unknown'
            String timeUntilFullTxt = "unknown";
            if (timeUntilFull > -1 && timeUntilFull < 65535)
                timeUntilFullTxt = "" + timeUntilFull + "mins";
            int presentCharge = batteryStatus.getIntExtra("present_charge", -1);
            String presentChargeTxt = "unknown";
            if (presentCharge > -1)
                presentChargeTxt = "" + presentCharge + "mAh";

            list.clear();

            list.add("=== STANDARD ANDROID ===");
            list.add("Battery Present?: " + presentTxt);
            list.add("Battery Percentage: " + percentageVal);
            list.add("Plugged In?: " + pluggedInStatus);
            list.add("Health: " + healthTxt);
            list.add("Status: " + statusTxt);
            list.add("Technology: " + technologyTxt);
            list.add("Temperature: " + temperatureTxt);
            list.add("Voltage: " + voltageTxt);
            list.add("=== ZEBRA ONLY ===");
            list.add("Manufacture Date: " + manufactureDate);
            list.add("Part Number: " + partNumber);
            list.add("Serial Number: " + serialNumber);
            list.add("Backup Battery Voltage: " + backupBatteryVoltageTxt);
            list.add("Rated Capacity: " + ratedCapacityTxt);
            list.add("Decommission Status: " + decommissionStatusTxt);
            list.add("Cumulative Charge (Zebra): " + cumulativeChargeTxt);
            list.add("Number of Charge Cycles: " + numberChargeCyclesTxt);
            list.add("Cumulative Charge (All): " + cumulativeChargeAllTxt);
            list.add("Time since first charge: " + secondsSinceFirstChargeTxt);
            list.add("Present capacity: " + presentCapacityTxt);
            list.add("Percent health: " + zebraHealthTxt);
            list.add("Time to empty: " + timeUntilEmptyTxt);
            list.add("Time to full: " + timeUntilFullTxt);
            list.add("Present charge: " + presentChargeTxt);

            adapter.notifyDataSetChanged();

            Log.i(LOG_TAG, "Battery Health percentage: " + health);
        }
    }
}
