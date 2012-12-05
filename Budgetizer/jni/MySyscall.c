/*
 * 18-648 Fall 2012 Group 14
 * Benet Clark
 * Nipunn Koorapati
 * Jacob Olson
 */

#include <jni.h>
#include <time.h>

JNIEXPORT jint JNICALL
Java_com_budget_budgetizer_MySyscall_SetProcessBudget(JNIEnv *env, jclass class,
        jint pid, jlong budget_cycles,
        jlong period_sec, jlong period_nsec) {

    struct timespec period;
	unsigned long budget = budget_cycles;

    period.tv_sec = period_sec;
    period.tv_nsec = period_nsec;

    return syscall(378, pid, budget, period);
}

JNIEXPORT jint JNICALL
Java_com_budget_budgetizer_MySyscall_CancelBudget(JNIEnv *env, jclass class, jint pid) {
	return syscall(379, pid);
}

JNIEXPORT jint JNICALL
Java_com_budget_budgetizer_MySyscall_WaitUntilNextPeriod(JNIEnv *env, jclass class, jint pid) {
	return syscall(380, pid);
}
