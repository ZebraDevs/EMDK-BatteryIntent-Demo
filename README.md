# Zebra Battery Extras

*This application is provided without guarantee or warranty*
=========================================================

This application demonstrates the extra battery information which can be obtained on Zebra Android Mobile Devices

The standard Android battery Intent is [Intent.ACTION_BATTERY_CHANGED](https://developer.android.com/reference/android/content/Intent.html#ACTION_BATTERY_CHANGED) and on all Android devices this Intent will contain the following information:

- Whether the battery is present
- Current battery level
- Whether or not the battery is plugged in or charging and if so, how is it receiving charge.
- A general description of the health of the battery
- The temperature and voltage of the battery

Zebra mobile devices append additional battery information to this Intent as described in the [reference guide on techdocs](https://techdocs.zebra.com/emdk-for-android/latest/guide/reference/refbatteryintent/)

For example, the following additional information is available:

- Manufacture date
- Part number
- Serial number
- Backup battery voltage
- Battery capacity
- Decommission status
- Cumulative charge
- Number of charge cycles
- Present capacity
- Health expressed as a percentage
- Time until empty
- Time until full
- Amount of usable charge remaining in the battery

The following screenshots show the application running on a Zebra Mobile device:

![TC57 001](https://raw.githubusercontent.com/darryncampbell/Zebra_Battery_Extras/master/screenshots/tc57_001.jpg)

![TC57 002](https://raw.githubusercontent.com/darryncampbell/Zebra_Battery_Extras/master/screenshots/tc57_002.jpg)

![TC57 003](https://raw.githubusercontent.com/darryncampbell/Zebra_Battery_Extras/master/screenshots/tc57_003.jpg)

The following screenshots show the application running on a consumer device (Google Pixel 2):

![Pixel 2 XL 001](https://raw.githubusercontent.com/darryncampbell/Zebra_Battery_Extras/master/screenshots/pixel2xl_001.jpg)

