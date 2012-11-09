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
        jint pid, jlong budget_sec, jlong budget_nsec,
        jlong period_sec, jlong period_nsec, jint rtprio) {

    struct timespec budget;
    struct timespec period;

    budget.tv_sec = budget_sec;
    budget.tv_nsec = budget_nsec;
    period.tv_sec = period_sec;
    period.tv_nsec = period_nsec;

    return budget_sec + budget_nsec;
    //return syscall(378, pid, budget, period, rtprio);
}

JNIEXPORT jint JNICALL
Java_com_budget_budgetizer_MySyscall_CancelBudget(JNIEnv *env, jclass class, jint pid) {
	return pid + 1000;
	//return syscall(379, pid);
}

JNIEXPORT jint JNICALL
Java_com_budget_budgetizer_MySyscall_WaitUntilNextPeriod(JNIEnv *env, jclass class, jint pid) {
	return pid + 2000;
    //return syscall(380, pid);
}
