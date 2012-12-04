package com.appdisp.AppDisp;

public class MySyscall {
    native public static int setDisplayBrightness(int value);
    
    static {
        System.loadLibrary("MySyscall");
    }
}
