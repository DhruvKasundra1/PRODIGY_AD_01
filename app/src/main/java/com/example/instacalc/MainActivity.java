package com.example.instacalc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView ResultTv, SolutionTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ResultTv = findViewById(R.id.result_text);
        SolutionTv = findViewById(R.id.solution_text);


        findViewById(R.id.btn_C).setOnClickListener(this);
        findViewById(R.id.btn_openbracket).setOnClickListener(this);
        findViewById(R.id.btn_closedbracket).setOnClickListener(this);
        findViewById(R.id.btn_mul).setOnClickListener(this);
        findViewById(R.id.btn_sub).setOnClickListener(this);
        findViewById(R.id.btn_sum).setOnClickListener(this);
        findViewById(R.id.btn_divide).setOnClickListener(this);
        findViewById(R.id.btn_equal).setOnClickListener(this);
        findViewById(R.id.btn_0).setOnClickListener(this);
        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);
        findViewById(R.id.btn_4).setOnClickListener(this);
        findViewById(R.id.btn_5).setOnClickListener(this);
        findViewById(R.id.btn_6).setOnClickListener(this);
        findViewById(R.id.btn_7).setOnClickListener(this);
        findViewById(R.id.btn_8).setOnClickListener(this);
        findViewById(R.id.btn_9).setOnClickListener(this);
        findViewById(R.id.btn_AC).setOnClickListener(this);
        findViewById(R.id.btn_dot).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataCalculate = SolutionTv.getText().toString();

        switch (buttonText) {
            case "AC":
                SolutionTv.setText("");
                ResultTv.setText("0");
                break;
            case "=":
                String expression = SolutionTv.getText().toString();
                String result = evaluateExpression(expression);
                SolutionTv.setText(result);
                ResultTv.setText(result);
                break;
            case "C":
                if (!dataCalculate.isEmpty()) {
                    dataCalculate = dataCalculate.substring(0, dataCalculate.length() - 1);
                    SolutionTv.setText(dataCalculate);
                }
                break;
            default:
                dataCalculate = dataCalculate + buttonText;
                SolutionTv.setText(dataCalculate);
                break;
        }
    }

    private String evaluateExpression(String expression) {
        try {
            Context rhino = Context.enter();
            rhino.setOptimizationLevel(-1);  // Avoids maximum stack size errors
            Scriptable scope = rhino.initStandardObjects();

            Object result = rhino.evaluateString(scope, expression, "JavaScript", 1, null);

            String finalResult = Context.toString(result);

            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.substring(0, finalResult.length() - 2);
            }

            if (finalResult.equals("Infinity")) {
                return "Error: Division by zero";
            }

            return finalResult;
        } catch (Exception e) {
            return "Error";
        } finally {
            Context.exit();
        }
    }
}
