package com.dataoracle.agrizen.location

import android.content.Context
import android.location.Address
import android.location.Geocoder
import java.util.*

class LocationUtils  {
    companion object {
        fun getCompleteAddressString(context: Context, LATITUDE: Double, LONGITUDE: Double): String? {
            var strAdd = ""
            val geocoder = Geocoder(context, Locale.getDefault())
            try {
                val addresses: List<Address>? = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1)
                if (addresses != null) {
                    val returnedAddress: Address = addresses[0]
                    val strReturnedAddress = StringBuilder("")
                    for (i in 0..returnedAddress.getMaxAddressLineIndex()) {
                        strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n")
                    }
                    strAdd = strReturnedAddress.toString()
                } else {
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return strAdd
        }
    }
}