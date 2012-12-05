package com.budget.budgetizer;

public class MySyscall {
    native private static int SetProcessBudget(int pid, long budget_cycles,
            long period_sec, long period_nsec);
    native public static int CancelBudget(int pid);
    native public static int WaitUntilNextPeriod(int pid);
    
    public static int SetProcessBudget(int pid, long budget, TimeSpec period) {
        return SetProcessBudget(pid, budget, period.tv_sec, period.tv_nsec);
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
