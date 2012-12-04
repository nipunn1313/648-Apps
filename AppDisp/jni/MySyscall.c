/*
 * 18-648 Fall 2012 Group 14
 * Benet Clark
 * Nipunn Koorapati
 * Jacob Olson
 */

#include <jni.h>

JNIEXPORT jint JNICALL Java_com_appdisp_AppDisp_MySyscall_setDisplayBrightness
  (JNIEnv *env, jclass class, jint brightness)
{
	return syscall(382, brightness);
}
