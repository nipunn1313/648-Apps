package com.budget.budgetizer;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
    
    EditText pidText;
    EditText budgetText;
    EditText periodText;
    EditText rtPrioText;
    
    TextView helloText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        helloText = (TextView) findViewById(R.id.HelloView);
        
        pidText = (EditText) findViewById(R.id.pidText);
        budgetText = (EditText) findViewById(R.id.budgetText);
        periodText = (EditText) findViewById(R.id.periodText);
        rtPrioText = (EditText) findViewById(R.id.rtPrioText);
        
        Button setBudgetButton = (Button) findViewById(R.id.set_budget_button);
        Button cancelBudgetButton = (Button) findViewById(R.id.cancel_budget_button);
        
        setBudgetButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setBudget();
            }
        });
        
        cancelBudgetButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelBudget();
            }
        });
    }

    private void setBudget() {
        int pid;
        MySyscall.TimeSpec budget;
        MySyscall.TimeSpec period;
        int rtPrio;
        
        try {
            String pidStr = pidText.getText().toString();
            pid = Integer.parseInt(pidStr);
        } catch (NumberFormatException e) {
            helloText.setText("pid must be an integer");
            return;
        }
        
        try {
            String budgetString = budgetText.getText().toString();
            double budg = Double.parseDouble(budgetString);
            budget = new MySyscall.TimeSpec(budg);
        } catch (NumberFormatException e) {
            helloText.setText("budget must be a double");
            return;
        }
        
        try {
            String periodString = periodText.getText().toString();
            double p = Double.parseDouble(periodString);
            period = new MySyscall.TimeSpec(p);
        } catch (NumberFormatException e) {
            helloText.setText("period must be a double");
            return;
        }
        
        try {
            String prioString = rtPrioText.getText().toString();
            rtPrio = Integer.parseInt(prioString);
        } catch (NumberFormatException e) {
            helloText.setText("RT Priority must be an integer");
            return;
        }
        
        int result = MySyscall.SetProcessBudget(pid, budget, period, rtPrio);
        helloText.setText("setProcessBudget returned: " + result);
    }
    
    private void cancelBudget() {
        try {
            String pidStr = pidText.getText().toString();
            int pid = Integer.parseInt(pidStr);
            int result = MySyscall.CancelBudget(pid);
            helloText.setText("cancelBudget returned: " + result);
        } catch (NumberFormatException e) {
            helloText.setText("pid must be an integer");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
