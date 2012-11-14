package com.budget.budgetizer;

public class MySyscall {
    native private static int SetProcessBudget(int pid, long budget_sec, long budget_nsec,
            long period_sec, long period_nsec, int rtprio);
    native public static int CancelBudget(int pid);
    native public static int WaitUntilNextPeriod(int pid);
    
    public static int SetProcessBudget(int pid, TimeSpec budget, TimeSpec period, int rtprio) {
        return SetProcessBudget(pid, budget.tv_sec, budget.tv_nsec, period.tv_sec, period.tv_nsec, rtprio);
    }
    
    static class TimeSpec {
        public TimeSpec(long sec, long nsec) {
            this.tv_sec = sec;
            this.tv_nsec = nsec;
        }
        public TimeSpec(double d) {
            tv_sec = (long) d;
            double nsec = (d - tv_sec) * 1000000000;
            tv_nsec = (long) nsec;
        }
        
        long tv_sec;
        long tv_nsec;
    }
    
    static {
        System.loadLibrary("MySyscall");
    }
}
