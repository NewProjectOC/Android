package com.example.lenovo.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    public EditText et_output;
    public Button bt_0;
    public Button bt_1;
    public Button bt_2;
    public Button bt_3;
    public Button bt_4;
    public Button bt_5;
    public Button bt_6;
    public Button bt_7;
    public Button bt_8;
    public Button bt_9;
    public Button bt_p;
    public Button bt_ac;
    public Button bt_back;
    public Button bt_plus;
    public Button bt_minus;
    public Button bt_multiply;
    public Button bt_divide;
    public Button bt_equal;

    String line="";
    String num1,num2;
    double sum = 0;
    int flag = 0;   //符号标记，0为无符号，1为加，2为减，3为乘，4为除

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_0 = (Button)findViewById(R.id.bt_0);
        bt_1= (Button) findViewById(R.id.bt_1);
        bt_2= (Button) findViewById(R.id.bt_2);
        bt_3= (Button) findViewById(R.id.bt_3);
        bt_4= (Button) findViewById(R.id.bt_4);
        bt_5= (Button) findViewById(R.id.bt_5);
        bt_6= (Button) findViewById(R.id.bt_6);
        bt_7= (Button) findViewById(R.id.bt_7);
        bt_8= (Button) findViewById(R.id.bt_8);
        bt_9= (Button) findViewById(R.id.bt_9);
        bt_p= (Button) findViewById(R.id.bt_p);
        bt_plus= (Button) findViewById(R.id.bt_plus);
        bt_minus= (Button) findViewById(R.id.bt_minus);
        bt_multiply= (Button) findViewById(R.id.bt_multiply);
        bt_divide= (Button) findViewById(R.id.bt_divide);
        bt_ac= (Button) findViewById(R.id.bt_ac);
        bt_back= (Button) findViewById(R.id.bt_back);
        bt_equal= (Button) findViewById(R.id.bt_equal);
        et_output= (EditText) findViewById(R.id.et_output);
        line = "";
        et_output.setText(line);
    }

    public void pushNum0(View view) {
        if(!line.equals("Infinity")&&!line.equals("error")) {
            line += "0";
            et_output.setText(line);
        }
    }

    public void pushNum1(View view) {
        if(!line.equals("Infinity")&&!line.equals("error")) {
            line += "1";
            et_output.setText(line);
        }
    }

    public void pushNum2(View view) {
        if(!line.equals("Infinity")&&!line.equals("error")) {
            line += "2";
            et_output.setText(line);
        }
    }

    public void pushNum3(View view) {
        if(!line.equals("Infinity")&&!line.equals("error")) {
            line += "3";
            et_output.setText(line);
        }
    }

    public void pushNum4(View view) {
        if(!line.equals("Infinity")&&!line.equals("error")) {
            line += "4";
            et_output.setText(line);
        }
    }

    public void pushNum5(View view) {
        if(!line.equals("Infinity")&&!line.equals("error")) {
            line += "5";
            et_output.setText(line);
        }
    }

    public void pushNum6(View view) {
        if(!line.equals("Infinity")&&!line.equals("error")) {
            line += "6";
            et_output.setText(line);
        }
    }

    public void pushNum7(View view) {
        if(!line.equals("Infinity")&&!line.equals("error")) {
            line += "7";
            et_output.setText(line);
        }
    }

    public void pushNum8(View view) {
        if(!line.equals("Infinity")&&!line.equals("error")) {
            line += "8";
            et_output.setText(line);
        }
    }

    public void pushNum9(View view) {
        if(!line.equals("Infinity")&&!line.equals("error")) {
            line += "9";
            et_output.setText(line);
        }
    }

    public void pushPoint(View view) {
        if(!line.equals("Infinity")&&!line.equals("error")) {
            int index = 0;
            if (flag == 1)
                index = line.lastIndexOf("+");
            if (flag == 2)
                index = line.lastIndexOf("-");
            if (flag == 3)
                index = line.lastIndexOf("*");
            if (flag == 4)
                index = line.lastIndexOf("/");
            String x = et_output.getText().toString();
            int ispoint = x.indexOf(".");
            if (flag == 0) {
                if (ispoint == -1) {
                    line += ".";
                    et_output.setText(line);
                }
            } else {
                if (!x.substring(index + 1, x.length()).contains(".")) {
                    line += ".";
                    et_output.setText(line);
                }
            }
        }
    }

    public void pushPlus(View view) {
        if(!line.equals("Infinity")&&!line.equals("error")) {
            int index = 0;
            Double sum;
            if (flag == 1)
                index = line.lastIndexOf("+");
            if (flag == 2)
                index = line.lastIndexOf("-");
            if (flag == 3)
                index = line.lastIndexOf("*");
            if (flag == 4)
                index = line.lastIndexOf("/");
            if (flag == 0) {
                if (line.equals(".") || line.equals("") || line.equals("-"))
                    sum = 0.0;
                else
                    sum = Double.parseDouble(line.substring(0, line.length()));
                line = String.valueOf(sum);
                et_output.setText(line);
                line += "+";
                et_output.setText(line);
                flag = 1;
            } else if (index == line.length() - 1) {
                line = line.substring(0, line.length() - 1);
                if (line.equals("."))
                    sum = 0.0;
                else
                    sum = Double.parseDouble(line.substring(0, line.length()));
                line = String.valueOf(sum);
                et_output.setText(line);
                line += "+";
                et_output.setText(line);
                flag = 1;
            } else {
                pushEqual(view);
                pushPlus(view);
            }
        }
    }

    public void pushMinus(View view) {
        if(!line.equals("Infinity")&&!line.equals("error")) {
            int index = 0;
            Double sum;
            if (flag == 1)
                index = line.lastIndexOf("+");
            if (flag == 2)
                index = line.lastIndexOf("-");
            if (flag == 3)
                index = line.lastIndexOf("*");
            if (flag == 4)
                index = line.lastIndexOf("/");
            if (flag == 0) {
                if (line.equals(".") || line.equals("") || line.equals("-"))
                    sum = 0.0;
                else
                    sum = Double.parseDouble(line.substring(0, line.length()));
                line = String.valueOf(sum);
                et_output.setText(line);
                line += "-";
                et_output.setText(line);
                flag = 2;
            } else if (index == line.length() - 1) {
                line = line.substring(0, line.length() - 1);
                if (line.equals("."))
                    sum = 0.0;
                else
                    sum = Double.parseDouble(line.substring(0, line.length()));
                line = String.valueOf(sum);
                et_output.setText(line);
                line += "-";
                et_output.setText(line);
                flag = 2;
            } else {
                pushEqual(view);
                pushMinus(view);
            }
        }
    }

    public void pushMultiply(View view) {
        if(!line.equals("Infinity")&&!line.equals("error")) {
            int index = 0;
            Double sum;
            if (flag == 1)
                index = line.lastIndexOf("+");
            if (flag == 2)
                index = line.lastIndexOf("-");
            if (flag == 3)
                index = line.lastIndexOf("*");
            if (flag == 4)
                index = line.lastIndexOf("/");
            if (flag == 0) {
                if (line.equals(".") || line.equals("") || line.equals("-"))
                    sum = 0.0;
                else
                    sum = Double.parseDouble(line.substring(0, line.length()));
                line = String.valueOf(sum);
                et_output.setText(line);
                line += "*";
                et_output.setText(line);
                flag = 3;
            } else if (index == line.length() - 1) {
                line = line.substring(0, line.length() - 1);
                if (line.equals("."))
                    sum = 0.0;
                else
                    sum = Double.parseDouble(line.substring(0, line.length()));
                line = String.valueOf(sum);
                et_output.setText(line);
                line += "*";
                et_output.setText(line);
                flag = 3;
            } else {
                pushEqual(view);
                pushMultiply(view);
            }
        }
    }

    public void pushDivide(View view) {
        if(!line.equals("Infinity")&&!line.equals("error")) {
            int index = 0;
            Double sum;
            if (flag == 1)
                index = line.lastIndexOf("+");
            if (flag == 2)
                index = line.lastIndexOf("-");
            if (flag == 3)
                index = line.lastIndexOf("*");
            if (flag == 4)
                index = line.lastIndexOf("/");
            if (flag == 0) {
                if (line.equals(".") || line.equals("") || line.equals("-"))
                    sum = 0.0;
                else
                    sum = Double.parseDouble(line.substring(0, line.length()));
                line = String.valueOf(sum);
                et_output.setText(line);
                line += "/";
                et_output.setText(line);
                flag = 4;
            } else if (index == line.length() - 1) {
                line = line.substring(0, line.length() - 1);
                if (line.equals("."))
                    sum = 0.0;
                else
                    sum = Double.parseDouble(line.substring(0, line.length()));
                line = String.valueOf(sum);
                et_output.setText(line);
                line += "/";
                et_output.setText(line);
                flag = 4;
            } else {
                pushEqual(view);
                pushDivide(view);
            }
        }
    }

    public void pushC(View view) {
        line = et_output.getText().toString();
        if(!line.equals("Infinity")&&!line.equals("error")) {
            if (!line.equals("0") && !line.equals("") && line.length() >= 1) {
                if(line.substring(line.length()-1,line.length()).equals("+") || line.substring(line.length()-1,line.length()).equals("-") || line.substring(line.length()-1,line.length()).equals("*") || line.substring(line.length()-1,line.length()).equals("/")) {
                    flag = 0;
                    line = line.substring(0,line.length()-1);
                }
                else if(line.length()==1) {
                    flag = 0;
                    line = "";
                }
                else
                    line = line.substring(0, line.length() - 1);
            }
            et_output.setText(line);
        }
    }

    public void pushAc(View view) {
        line = "";
        et_output.setText(line);
        sum = 0;
        flag = 0;
    }

    public void pushEqual(View view) {
        line = et_output.getText().toString();
        if (!line.equals("Infinity") && !line.equals("error")) {
            if (flag == 0) {
                if (line.equals("") || line.equals(".") || line.equals("+") || line.equals("-") || line.equals("*") || line.equals("/"))
                    line = "0";
                sum = Double.parseDouble(line);
                et_output.setText(String.valueOf(sum));
                line = et_output.getText().toString();
            } else if (flag == 1) {
                int index = line.indexOf("+");
                if (index == 0 || line.equals("+"))
                    sum = 0;
                else if (index == 0) {
                    if (line.substring(index + 1, line.length()).equals("."))
                        sum = 0 + 0;
                    else
                        sum = 0 + Double.parseDouble(line.substring(index + 1, line.length()));
                } else if (index == line.length() - 1) {
                    if (line.substring(0, index).equals("."))
                        sum = 0;
                    else
                        sum = Double.parseDouble(line.substring(0, index));
                } else {
                    if (line.substring(index + 1, line.length()).equals(".") && line.substring(0, index).equals("."))
                        sum = 0;
                    else if (line.substring(0, index).equals("."))
                        sum = 0 + Double.parseDouble(line.substring(index + 1, line.length()));
                    else if (line.substring(index + 1, line.length()).equals("."))
                        sum = Double.parseDouble(line.substring(0, index)) + 0;
                    else
                        sum = Double.parseDouble(line.substring(0, index)) + Double.parseDouble(line.substring(index + 1, line.length()));
                }
                et_output.setText(String.valueOf(sum));
                line = et_output.getText().toString();
                flag = 0;
            } else if (flag == 2) {
                int index = line.lastIndexOf("-");
                if (index == 0 || line.equals("-"))
                    sum = 0;
                else if (index == 0) {
                    if (line.substring(index + 1, line.length()).equals("."))
                        sum = 0 - 0;
                    else
                        sum = 0 - Double.parseDouble(line.substring(index + 1, line.length()));
                } else if (index == line.length() - 1) {
                    if (line.substring(0, index).equals("."))
                        sum = 0;
                    else
                        sum = Double.parseDouble(line.substring(0, index));
                } else {
                    if (line.substring(index + 1, line.length()).equals(".") && line.substring(0, index).equals("."))
                        sum = 0;
                    else if (line.substring(0, index).equals("."))
                        sum = 0 - Double.parseDouble(line.substring(index + 1, line.length()));
                    else if (line.substring(index + 1, line.length()).equals("."))
                        sum = Double.parseDouble(line.substring(0, index)) - 0;
                    else
                        sum = Double.parseDouble(line.substring(0, index)) - Double.parseDouble(line.substring(index + 1, line.length()));
                }
                et_output.setText(String.valueOf(sum));
                line = et_output.getText().toString();
                flag = 0;
            } else if (flag == 3) {
                int index = line.indexOf("*");
                if (index == 0 || line.equals("*"))
                    sum = 0;
                else if (index == 0) {
                    if (line.substring(index + 1, line.length()).equals("."))
                        sum = 0 * 0;
                    else
                        sum = 0 * Double.parseDouble(line.substring(index + 1, line.length()));
                } else if (index == line.length() - 1) {
                    if (line.substring(0, index).equals("."))
                        sum = 0;
                    else
                        sum = Double.parseDouble(line.substring(0, index));
                } else {
                    if (line.substring(index + 1, line.length()).equals(".") && line.substring(0, index).equals("."))
                        sum = 0;
                    else if (line.substring(0, index).equals("."))
                        sum = 0 * Double.parseDouble(line.substring(index + 1, line.length()));
                    else if (line.substring(index + 1, line.length()).equals("."))
                        sum = Double.parseDouble(line.substring(0, index)) * 0;
                    else
                        sum = Double.parseDouble(line.substring(0, index)) * Double.parseDouble(line.substring(index + 1, line.length()));
                }
                et_output.setText(String.valueOf(sum));
                line = et_output.getText().toString();
                flag = 0;
            } else if (flag == 4) {
                int index = line.indexOf("/");
                if (line.substring(index + 1).equals("0")) {
                    line = "Infinity";
                    et_output.setText(line);
                } else {
                    if (index == 0 || line.equals("/"))
                        sum = 0;
                    else if (index == 0) {
                        if (line.substring(index + 1, line.length()).equals("."))
                            sum = 0;
                        else
                            sum = 0 / Double.parseDouble(line.substring(index + 1, line.length()));
                    } else if (index == line.length() - 1) {
                        if (line.substring(0, index).equals("."))
                            sum = 0;
                        else
                            sum = Double.parseDouble(line.substring(0, index));
                    } else {
                        if (line.substring(index + 1, line.length()).equals(".") && line.substring(0, index).equals("."))
                            sum = 0;
                        else if (line.substring(0, index).equals("."))
                            sum = 0 / Double.parseDouble(line.substring(index + 1, line.length()));
                        else if (line.substring(index + 1, line.length()).equals("."))
                            sum = 0;
                        else
                            sum = Double.parseDouble(line.substring(0, index)) / Double.parseDouble(line.substring(index + 1, line.length()));
                    }
                    et_output.setText(String.valueOf(sum));
                    line = et_output.getText().toString();
                    flag = 0;
                }
            } else {
                line = "error";
                et_output.setText(line);
                flag = 0;
            }
        }
    }

}