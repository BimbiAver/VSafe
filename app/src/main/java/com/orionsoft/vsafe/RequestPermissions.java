package com.orionsoft.vsafe;

import android.Manifest;

public class RequestPermissions {
    // Permissions request code
    final static int REQUEST_CODE_ASK_PERMISSIONS = 1;

    // Permissions that need to be explicitly requested from end user
    public static final String[] REQUIRED_SDK_PERMISSIONS = new String[] {
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA
    };
}