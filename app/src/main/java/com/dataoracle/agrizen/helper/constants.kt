package com.dataoracle.agrizen.helper

class constants {
    companion object {
        // codes to launch and result of activities
        const val LAUNCH_LOGIN_CODE = 1
        const val LAUNCH_VERIFY_CODE = LAUNCH_LOGIN_CODE+1
        const val VERIFICATION_FAILURE = LAUNCH_LOGIN_CODE+2
        const val LAUNCH_LOCATION_CODE = LAUNCH_LOGIN_CODE+3
        const val REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE = LAUNCH_LOGIN_CODE+4

        const val PACKAGE_NAME = "com.dataoracle.agrizen"
        const val RECEIVER = "$PACKAGE_NAME.RECEIVER"
        const val RESULT_DATA_KEY = "$PACKAGE_NAME.RESULT_DATA_KEY"
        const val LOCATION_DATA_EXTRA = "$PACKAGE_NAME.LOCATION_DATA_EXTRA"

        const val SUCCESS_RESULT = 0
        const val FAILURE_RESULT = 1

        const val CLOUDINARY_URL = "cloudinary://734658891718625:n9O_LEc4JBy8_NhSi7vH--XPO6I@dataoracle"
        const val CLOUDINARY_KEY = "734658891718625"
        const val CLOUDINARY_SECRET = "n9O_LEc4JBy8_NhSi7vH--XPO6I"
        const val CLOUDINARY_SAMPLE = "https://res.cloudinary.com/aky0788/image/upload/c_thumb,w_200,g_face/v1599364496/sample.jpg"
    }
}