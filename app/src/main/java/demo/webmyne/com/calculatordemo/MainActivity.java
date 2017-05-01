package demo.webmyne.com.calculatordemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.math.BigDecimal;

import me.grantland.widget.AutofitHelper;
import me.grantland.widget.AutofitTextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {
    public String str = "";
    private int count = 0;
    private boolean lastNumeric;
    // Represent that current state is in error or not
    private boolean stateError;
    // If true, do not allow to add another DOT
    private boolean lastDot;
    private Button one, two, three, four, five, six, seven, eight, nine, zero;
    private Button plus, divide;
    private Button dot, equal;
    AutofitTextView display;
    private Button minus;
    private Button delete;
    private AutofitTextView editDisp;
    private float mValueOne, mValueTwo, result1;
    private boolean mAddition, mSubtract, mMultiplication, mDivision;
    private Button multi;
    private LinearLayout display_screen;
    private LinearLayout mRevealView;
    private String mValue1;
    private ImageView imgThemeChange;
    private LinearLayout lvMain;
    private RelativeLayout rvDisplayLayout;
    private boolean isBlackThemeEnable = false;
    private LinearLayout them1RippleView;
    private LinearLayout them2RippleView;
    public static final String SHARED_PREF_NAME = "MY pref";
    public static final String blackTheme = "Black_Theme";
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        font();

        setRippleBackground();

        listener();
    }

    private void setRippleBackground() {
        if (Build.VERSION.SDK_INT >= 21) {
            lvMain.setBackgroundResource(R.drawable.ripple);
            one.setBackgroundResource(R.drawable.ripple);
            two.setBackgroundResource(R.drawable.ripple);
            three.setBackgroundResource(R.drawable.ripple);
            four.setBackgroundResource(R.drawable.ripple);
            five.setBackgroundResource(R.drawable.ripple);
            six.setBackgroundResource(R.drawable.ripple);
            seven.setBackgroundResource(R.drawable.ripple);
            eight.setBackgroundResource(R.drawable.ripple);
            nine.setBackgroundResource(R.drawable.ripple);
            zero.setBackgroundResource(R.drawable.ripple);
            dot.setBackgroundResource(R.drawable.ripple);
            delete.setBackgroundResource(R.drawable.ripple);
            plus.setBackgroundResource(R.drawable.ripple);
            minus.setBackgroundResource(R.drawable.ripple);
            multi.setBackgroundResource(R.drawable.ripple);
            divide.setBackgroundResource(R.drawable.ripple);
            equal.setBackgroundResource(R.drawable.ripple);
        } else {
            lvMain.setBackground(getResources().getDrawable(R.drawable.theme2_gradient));
            one.setBackgroundColor(Color.TRANSPARENT);
            two.setBackgroundColor(Color.TRANSPARENT);
            three.setBackgroundColor(Color.TRANSPARENT);
            four.setBackgroundColor(Color.TRANSPARENT);
            five.setBackgroundColor(Color.TRANSPARENT);
            six.setBackgroundColor(Color.TRANSPARENT);
            seven.setBackgroundColor(Color.TRANSPARENT);
            eight.setBackgroundColor(Color.TRANSPARENT);
            nine.setBackgroundColor(Color.TRANSPARENT);
            zero.setBackgroundColor(Color.TRANSPARENT);
            dot.setBackgroundColor(Color.TRANSPARENT);
            delete.setBackgroundColor(Color.TRANSPARENT);
            plus.setBackgroundColor(Color.TRANSPARENT);
            minus.setBackgroundColor(Color.TRANSPARENT);
            multi.setBackgroundColor(Color.TRANSPARENT);
            divide.setBackgroundColor(Color.TRANSPARENT);;
            equal.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onResume() {
        super.onResume();
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        boolean isBlack = sharedPreferences.getBoolean(blackTheme, true);
        if (isBlack) {
            applyBlackTheme();
        } else {
            applyColorFullTheme();
        }
    }

    private void font() {
        String font = "fonts/HelveticaNeue-Thin.otf";
        String[] fontPath =
                {"fonts/HelveticaNeue-Thin.otf",
                        "HelveticaNeue-ThinCond.otf",
                        "HelveticaNeue-ThinCondObl.otf",
                        "HelveticaNeue-ThinExt.otf",
                        "HelveticaNeue-ThinExtObl.otf",
                        "HelveticaNeue-ThinItalic.otf"
                };

        Typeface tf = Typeface.createFromAsset(getResources().getAssets(), font);
        editDisp.setTypeface(tf);
        display.setTypeface(tf);
        one.setTypeface(tf);
        two.setTypeface(tf);
        three.setTypeface(tf);
        four.setTypeface(tf);
        five.setTypeface(tf);
        six.setTypeface(tf);
        seven.setTypeface(tf);
        eight.setTypeface(tf);
        nine.setTypeface(tf);
        zero.setTypeface(tf);
        plus.setTypeface(tf);
        minus.setTypeface(tf);
        divide.setTypeface(tf);
        multi.setTypeface(tf);
        equal.setTypeface(tf);
        delete.setTypeface(tf);

        lvMain.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorLightBlack));
        rvDisplayLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorLightGrey));
    }

    private void init() {
        mRevealView = (LinearLayout) findViewById(R.id.rippleView);
        them1RippleView = (LinearLayout) findViewById(R.id.them1RippleView);
        them2RippleView = (LinearLayout) findViewById(R.id.them2RippleView);
        lvMain = (LinearLayout) findViewById(R.id.lvMain);
        display_screen = (LinearLayout) findViewById(R.id.display_screen);
        rvDisplayLayout = (RelativeLayout) findViewById(R.id.rvDisplayLayout);
        editDisp = (AutofitTextView) findViewById(R.id.editDisplay);
        display = (AutofitTextView) findViewById(R.id.display);
        AutofitHelper.create(editDisp);
        AutofitHelper.create(display);
        imgThemeChange = (ImageView) findViewById(R.id.imgThemeChange);
        display.setText("0");
        one = (Button) findViewById(R.id.one);
        two = (Button) findViewById(R.id.two);
        three = (Button) findViewById(R.id.three);
        four = (Button) findViewById(R.id.four);
        five = (Button) findViewById(R.id.five);
        six = (Button) findViewById(R.id.six);
        seven = (Button) findViewById(R.id.seven);
        eight = (Button) findViewById(R.id.eight);
        nine = (Button) findViewById(R.id.nine);
        zero = (Button) findViewById(R.id.zero);
        delete = (Button) findViewById(R.id.delete);
        plus = (Button) findViewById(R.id.plus);
        minus = (Button) findViewById(R.id.minus);
        divide = (Button) findViewById(R.id.division);
        multi = (Button) findViewById(R.id.multi);
        plus = (Button) findViewById(R.id.plus);
        dot = (Button) findViewById(R.id.dot);
        equal = (Button) findViewById(R.id.equal);

    }

    private void listener() {
        imgThemeChange.setOnClickListener(this);
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);
        dot.setOnClickListener(this);
        zero.setOnClickListener(this);
        delete.setOnClickListener(this);
        plus.setOnClickListener(this);
        minus.setOnClickListener(this);
        multi.setOnClickListener(this);
        divide.setOnClickListener(this);
        delete.setOnLongClickListener(this);
        equal.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.one:
                editDisp.setText(editDisp.getText() + "1");
                equal.setEnabled(true);
                lastNumeric = true;
                break;
            case R.id.two:
                editDisp.setText(editDisp.getText() + "2");
                equal.setEnabled(true);
                lastNumeric = true;
                break;
            case R.id.three:
                editDisp.setText(editDisp.getText() + "3");
                equal.setEnabled(true);
                lastNumeric = true;
                break;
            case R.id.four:
                editDisp.setText(editDisp.getText() + "4");
                equal.setEnabled(true);
                lastNumeric = true;
                break;
            case R.id.five:
                editDisp.setText(editDisp.getText() + "5");
                equal.setEnabled(true);
                lastNumeric = true;
                break;
            case R.id.six:
                editDisp.setText(editDisp.getText() + "6");
                equal.setEnabled(true);
                lastNumeric = true;
                break;
            case R.id.seven:
                editDisp.setText(editDisp.getText() + "7");
                equal.setEnabled(true);
                lastNumeric = true;
                break;
            case R.id.eight:
                editDisp.setText(editDisp.getText() + "8");
                equal.setEnabled(true);
                lastNumeric = true;
                break;
            case R.id.nine:
                editDisp.setText(editDisp.getText() + "9");
                equal.setEnabled(true);
                lastNumeric = true;
                break;
            case R.id.dot:
                equal.setEnabled(true);
                if (!lastNumeric && !lastDot) {
                    editDisp.append(".");
                    lastNumeric = true;
                    lastDot = true;
                }
                else if(lastNumeric &&!lastDot)
                {
                    editDisp.append(".");
                    lastNumeric = false;
                    lastDot=true;
                }

               /* if (count == 0 && editDisp.length() != 0) {
                    editDisp.setText(editDisp.getText() + ".");
                    count++;
                }*/
                break;
            case R.id.zero:
                editDisp.setText(editDisp.getText() + "0");
                equal.setEnabled(true);
                lastNumeric = true;
                break;

            case R.id.delete:
                backSpace(view);
                equal.setEnabled(true);
                break;

            case R.id.plus:
                operationClicked("+");
                break;

            case R.id.minus:
                operationClicked("-");
                break;

            case R.id.multi:
                operationClicked("*");
                break;

            case R.id.division:
                operationClicked("/");
                break;

            case R.id.imgThemeChange:
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (isBlackThemeEnable) {
                    applyBlackTheme();
                    editor.putBoolean(blackTheme, true);
                    editor.commit();
                    //startBlackAnimation();
                    isBlackThemeEnable = false;
                } else {
                    applyColorFullTheme();
                    editor.putBoolean(blackTheme, false);
                    editor.commit();
                    // startColorFullAnimation();
                    isBlackThemeEnable = true;
                }
                break;

            case R.id.equal:
                if (lastNumeric) {
                    String t = editDisp.getText().toString();
                    if(!TextUtils.isEmpty(t))
                    {
                        String lastChar = String.valueOf(t.charAt(t.length() - 1));
                        if (lastChar.equals("+")) {
                            equal.setEnabled(false);
                            return;
                        }
                        if (lastChar.equals("-")) {
                            equal.setEnabled(false);
                            return;
                        }
                        if (lastChar.equals("*")) {
                            equal.setEnabled(false);
                            return;
                        }
                        if (lastChar.equals("/")) {
                            equal.setEnabled(false);
                            return;
                        } else {
                            // Read the expression
                            String txt = editDisp.getText().toString();
                            Expression expression = new ExpressionBuilder(txt).build();
                            try {
                                // Calculate the result and display
                                double result = expression.evaluate();
                                display.setText(removeTrailingZero(String.valueOf(BigDecimal.valueOf(result).toPlainString())));
                                //display.setText(removeTrailingZero(Double.toString(result)));
                                lastDot = true; // Result contains a dot
                            } catch (ArithmeticException ex) {
                                // Display an error message
                                display.setText("Error");
                                lastNumeric = false;
                            }
                        }
                        equal.setEnabled(true);
                    }
                }
                equal.setEnabled(true);
                break;
            //Second method for equal
           /* if (editDisp.length() != 0) {
                mValue1 = editDisp.getText().toString();

                if (mAddition) {
                    //str = display.getText().toString() + mValue1;
                    String[] addition = mValue1.split("[+]");
                    if (addition.length < 2) {

                    } else {
                        String part1 = addition[0];
                        String part2 = addition[1];
                        if (part1.length() != 0 && part2.equals(".")) {
                            equal.setEnabled(false);
                        } else {
                            Float add = Float.parseFloat(part1) + Float.parseFloat(part2);
                            Log.e("add", add + "");
                            // display.setText(removeTrailingZero(String.format("%.0f", add)));
                            display.setText(removeTrailingZero(String.valueOf(BigDecimal.valueOf(add).toPlainString())));
                        }

                    }
                    mAddition = true;
                    plus.setEnabled(true);
                    minus.setEnabled(true);
                    multi.setEnabled(true);
                    divide.setEnabled(true);
                }

                if (mSubtract) {
                    String[] sub = mValue1.split("[-]");

                    if (sub.length < 2) {
                    } else {
                        String part1 = sub[0];
                        String part2 = sub[1];
                        if (part1.length() != 0 && part2.equals(".")) {
                            equal.setEnabled(false);
                        } else {
                            Float mus = Float.parseFloat(part1) - Float.parseFloat(part2);
                            // display.setText(removeTrailingZero(Float.toString(mus)));
                            Log.e("mus", mus + "");
                            display.setText(removeTrailingZero(String.valueOf(BigDecimal.valueOf(mus).toPlainString())));
                        }
                    }

                    mSubtract = true;
                    plus.setEnabled(true);
                    minus.setEnabled(true);
                    multi.setEnabled(true);
                    divide.setEnabled(true);
                }

                if (mMultiplication) {
                    String[] multipli = mValue1.split("[*]");
                    if (multipli.length < 2) {

                    } else {
                        String part1 = multipli[0];
                        String part2 = multipli[1];
                        if (part1.length() != 0 && part2.equals(".")) {
                            equal.setEnabled(false);
                        } else {
                            Float mul = Float.parseFloat(part1) * Float.parseFloat(part2);
                            // display.setText(removeTrailingZero(Float.toString(mul)));
                            display.setText(removeTrailingZero(String.valueOf(BigDecimal.valueOf(mul).toPlainString())));
                        }

                    }

                    mMultiplication = true;
                    plus.setEnabled(true);
                    minus.setEnabled(true);
                    multi.setEnabled(true);
                    divide.setEnabled(true);
                }

                if (mDivision) {
                    String[] divi = mValue1.split("[/]");
                    if (divi.length < 2) {

                    } else {
                        String part1 = divi[0];
                        String part2 = divi[1];
                        if (part1.length() != 0 && part2.equals(".")) {
                            equal.setEnabled(false);
                        } else {
                            Float dv = Float.parseFloat(part1) / Float.parseFloat(part2);
                            display.setText(removeTrailingZero(String.valueOf(BigDecimal.valueOf(dv).toPlainString())));
                            // display.setText(removeTrailingZero(String.format("%.0f", dv)));
                        }
                    }
                    mDivision = true;
                    plus.setEnabled(true);
                    minus.setEnabled(true);
                    multi.setEnabled(true);
                    divide.setEnabled(true);
                }
            }*/
        }
    }

    //Apply Theme 1 Black
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void applyBlackTheme() {
        imgThemeChange.setImageResource(R.drawable.theme_2);
        lvMain.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorLightBlack));
        rvDisplayLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorLightGrey));
        mRevealView.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.theme1RippleColor));

        display.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorWhiteSmoke));
        one.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorSilver));
        two.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorSilver));
        three.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorSilver));
        four.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorSilver));
        five.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorSilver));
        six.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorSilver));
        seven.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorSilver));
        eight.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorSilver));
        nine.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorSilver));
        zero.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorSilver));
        plus.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorSilver));
        minus.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorSilver));
        divide.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorSilver));
        multi.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorSilver));
        equal.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorSilver));
        delete.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorSilver));
    }

    //Apply Theme 2 ColorFull
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void applyColorFullTheme() {
        imgThemeChange.setImageResource(R.drawable.theme_1);
        lvMain.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.theme2_gradient));
        rvDisplayLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorWhite));
        mRevealView.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.theme2RippleColor));

        display.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorGrey));
        one.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorWhite));
        two.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorWhite));
        three.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorWhite));
        four.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorWhite));
        five.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorWhite));
        six.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorWhite));
        seven.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorWhite));
        eight.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorWhite));
        nine.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorWhite));
        zero.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorWhite));
        plus.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorWhite));
        minus.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorWhite));
        divide.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorWhite));
        multi.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorWhite));
        equal.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorWhite));
        delete.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorWhite));

    }

    @Override
    public boolean onLongClick(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startAnimation();
        }
        else
        {
            editDisp.setText("");
            display.setText("");
            equal.setEnabled(true);
            lastNumeric = false;
            stateError = false;
            lastDot = false;
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void startAnimation() {
        // finding X and Y co-ordinates
        final int[] cx = {(display_screen.getLeft() + display_screen.getRight())};
        final int[] cy = {(display_screen.getBottom())};

        final int[] startradius = {0};
        final int[] endradius = {Math.max(display_screen.getWidth(), display_screen.getHeight()) + Math.max(display_screen.getWidth(), display_screen.getHeight())};


        ViewTreeObserver viewTreeObserver = display_screen.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    display_screen.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    cx[0] = (display_screen.getLeft() + display_screen.getRight());
                    cy[0] = (display_screen.getBottom());

                    startradius[0] = 0;
                    endradius[0] = Math.max(display_screen.getWidth(), display_screen.getHeight()) + Math.max(display_screen.getWidth(), display_screen.getHeight());
                    Log.e("end", endradius[0] + "");
                    Log.e("end", Math.max(mRevealView.getPaddingLeft(), mRevealView.getTop()) + "");
                }
            });
        }
        Log.e("" + cx[0], "" + cy[0]);
        // to find  radius when icon is tapped for showing layout
        // performing circular reveal when icon will be tapped
        Animator animator = ViewAnimationUtils.createCircularReveal(mRevealView, cx[0], cy[0], startradius[0], endradius[0]);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(500);

        // to show the layout when icon is tapped
        mRevealView.setVisibility(View.VISIBLE);
        animator.start();

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mRevealView.setVisibility(View.GONE);
                editDisp.setText("");
                display.setText("");
                count = 0;
                str = "";
                equal.setEnabled(true);
                lastNumeric = false;
                stateError = false;
                lastDot = false;
            }
        });
    }
       /* one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editDisp.setText(editDisp.getText() + "1");
            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editDisp.setText(editDisp.getText() + "2");
            }
        });

        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editDisp.setText(editDisp.getText() + "3");
            }
        });

        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editDisp.setText(editDisp.getText() + "4");
            }
        });

        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editDisp.setText(editDisp.getText() + "5");
            }
        });

        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editDisp.setText(editDisp.getText() + "6");
            }
        });

        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editDisp.setText(editDisp.getText() + "7");
            }
        });

        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editDisp.setText(editDisp.getText() + "8");
            }
        });

        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editDisp.setText(editDisp.getText() + "9");
            }
        });

        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editDisp.setText(editDisp.getText() + "0");
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backSpace(view);
            }
        });

        delete.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                editDisp.setText("");
                display.setText("");
                count=0;
                str="";
                return false;
            }
        });

        dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count==0 && editDisp.length()!=0)
                {
                    editDisp.setText(editDisp.getText()+".");
                    count++;
                }
              //  editDisp.setText(editDisp.getText() + ".");
            }
        });

        divide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String div = editDisp.getText().toString();
                if (!div.isEmpty()) {
                    mValueOne = Float.parseFloat(editDisp.getText() + "");
                    mDivision = true;
                    editDisp.setText(null);
                } else {
                    editDisp.setText("");
                }

            }
        });

        multi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mul = editDisp.getText().toString();
                if (!mul.isEmpty()) {
                    mValueOne = Float.parseFloat(editDisp.getText() + "");
                    mMultiplication = true;
                    editDisp.setText(null);
                } else {
                    editDisp.setText("");
                }

            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String min = editDisp.getText().toString();
                if (!min.isEmpty()) {
                    mValueOne = Float.parseFloat(editDisp.getText() + "");
                    mSubtract = true;
                    editDisp.setText(null);
                } else {
                    editDisp.setText("");
                }

            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String plus = editDisp.getText().toString();
                if (!plus.isEmpty()) {
                    mValueOne = Float.parseFloat(editDisp.getText() + "");
                    mAddition = true;
                    editDisp.setText(null);
                } else {
                    editDisp.setText("");
                }
            }
        });

        equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eq = editDisp.getText().toString();
                if (!eq.isEmpty()) {
                    mValueTwo = Float.parseFloat(editDisp.getText() + "");

                    if (mAddition) {
                        //editDisp.setText(mValueOne + mValueTwo +"");
                        String add = mValueOne + mValueTwo + "";
                        //removeTrailingZero(add);
                        display.setText(removeTrailingZero(add));
                        mAddition = false;
                    }

                    if (mSubtract) {
                        String sub = mValueOne - mValueTwo + "";
                        display.setText(removeTrailingZero(sub));
                        mSubtract = false;
                    }

                    if (mMultiplication) {
                        String mul = mValueOne * mValueTwo + "";
                        display.setText(removeTrailingZero(mul));
                        mMultiplication = false;
                    }

                    if (mDivision) {
                        String div = mValueOne / mValueTwo + "";
                        display.setText(removeTrailingZero(div));
                        mDivision = false;
                    }
                } else {
                    editDisp.setText("");
                }

            }
        });

    }*/

    private String removeTrailingZero(String formattingInput) {
        if (!formattingInput.contains(".")) {
            return formattingInput;
        }
        int dotPosition = formattingInput.indexOf(".");
        String newValue = formattingInput.substring(dotPosition, formattingInput.length());
        if (newValue.equals(".0")) {
            return formattingInput.substring(0, dotPosition);
        }
        return formattingInput;
    }

    public void backSpace(View v) {
        display.setText(str);
        String str = editDisp.getText().toString();
        if (str.length() > 0) {
            str = str.substring(0, str.length() - 1);
            editDisp.setText(str);
        } else if (str.length() <= 1) {
            editDisp.setText("");
        }
        if(TextUtils.isEmpty(str))
        {
            lastNumeric=false;
        }
    }

    private void operationClicked(String op) {
        equal.setEnabled(true);
        if (editDisp.length() != 0 && lastNumeric) {
            String mvalue1 = editDisp.getText().toString();
            editDisp.setText(mvalue1 + op);
            count = 0;
            lastNumeric = false;
            lastDot = false;
        } /*else if (editDisp.length() != 0 && display.length() != 0) {
            String mvalue3 = display.getText().toString();
            editDisp.setText(mvalue3 + op);
            count = 0;
        }*/ else {
            String text = editDisp.getText().toString();
            if (text.length() > 0) {
                String mvalue2 = text.substring(0, text.length() - 1) + op;
                editDisp.setText(mvalue2);
            }
        }
    }

}
