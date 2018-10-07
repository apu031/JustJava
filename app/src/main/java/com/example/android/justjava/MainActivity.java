/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    final int PRICE_PER_CUP = 5;
    int Quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void increment(View view) {
        if (Quantity < 50) {
            Quantity = Quantity + 1;
            display(Quantity);
        } else {
            display(Quantity);
        }
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void decrement(View view) {
        if (Quantity != 1) {
            Quantity -= 1;
            display(Quantity);
        } else {
            display(Quantity);
        }
    }


    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        String customerName = findingCustomerName();

        String orderSummary = null;

        CheckBox checkBox1 = findViewById(R.id.whipped_cream);
        boolean hasWhippedCream = checkBox1.isChecked();

        CheckBox checkBox2 = findViewById(R.id.chocolate);
        boolean hasChocolat = checkBox2.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolat);

        if (hasWhippedCream && hasChocolat) {
            orderSummary = createOrderSummary(customerName, price, checkBox1, checkBox2);
        } else if (hasWhippedCream) {
            orderSummary = createOrderSummary(customerName, price, checkBox1);
        } else if (hasChocolat) {
            orderSummary = createOrderSummary(customerName, price, checkBox2);
        } else {
            orderSummary = createOrderSummary(customerName, price);
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        //intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.JustJavaOrderFor) + customerName);
        intent.putExtra(Intent.EXTRA_TEXT, orderSummary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        displayMessage(orderSummary);
    }

    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolat) {
        if (hasWhippedCream && hasChocolat) {
            return Quantity * (PRICE_PER_CUP + 1 + 2);
        } else if (hasWhippedCream) {
            return Quantity * (PRICE_PER_CUP + 1);
        } else if (hasChocolat) {
            return Quantity * (PRICE_PER_CUP + 2);
        } else {
            return Quantity * PRICE_PER_CUP;
        }

    }

    private String createOrderSummary(String customerName, int price) {
        String fullMessage = "\n" + getString(R.string.Quantity) + ": " + Quantity + "\n" + getString(R.string.Total) + ": " + price + "\n" + getString(R.string.ThankYou) + "!";
        return getString(R.string.Name) + ": " + customerName + fullMessage;
    }

    private String createOrderSummary(String customerName, int price, CheckBox checkBox) {

        String topping = checkBox.getText().toString();
        topping = "\n" + getString(R.string.Toppings) + getString(R.string.Added) + ": " + topping;
        String fullMessage = topping + "\n" + getString(R.string.Quantity) + ": " + Quantity + "\n" + getString(R.string.Total) + ": " + price + "\n" + getString(R.string.ThankYou) + "!";
        return getString(R.string.Name) + ": " + customerName + fullMessage;
    }

    private String createOrderSummary(String customerName, int price, CheckBox checkBox1, CheckBox checkBox2) {

        String topping1 = checkBox1.getText().toString();
        String topping2 = checkBox2.getText().toString();
        String topping = "\n" + getString(R.string.Toppings) + getString(R.string.Added) + ": " + topping1 + " " + getString(R.string.And) + " " + topping2;
        String fullMessage = topping + "\n" + getString(R.string.Quantity) + ": " + Quantity + "\n" + getString(R.string.Total) + ": " + price + "\n" + getString(R.string.ThankYou) + "!";
        return getString(R.string.Name) + ": " + customerName + fullMessage;
    }

    private String findingCustomerName() {
        EditText editText = (EditText) findViewById(R.id.customer_name);
        return editText.getText().toString();
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}