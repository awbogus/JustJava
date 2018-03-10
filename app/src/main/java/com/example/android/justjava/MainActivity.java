package com.example.android.justjava;



import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;
    int perCoffeePrice = 5;
    int totalPrice;
    String priceMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText customerName = (EditText) findViewById(R.id.enter_name);
        String name = customerName.getText().toString();

        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();

        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckbox.isChecked();

        int totalPrice = calculatePrice(hasWhippedCream, hasChocolate);

        String priceMessage = createOrderSummary(totalPrice, name, hasWhippedCream, hasChocolate);
        displayMessage(priceMessage);

        /**
         * This is how you send an email. Commenting it out because it doesn't work well on the emulator.


        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, "alexandra.bogus@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "New Order for" + customerName);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }  */

    }


    /**
     * This method is called when the + button is clicked.
     */

    public void increment(View view) {
        if (quantity < 100) {
        quantity = quantity + 1;} else {
            Context context = getApplicationContext();
            CharSequence text = "You may only order up to 100 coffees.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        displayQuantity(quantity);
    }

    /**
     * This method is called when the - button is clicked.
     */

    public void decrement(View view) {
        if (quantity > 1) {
        quantity = quantity - 1;} else {
            Context context = getApplicationContext();
            CharSequence text = "You must order at least 1 coffee.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        displayQuantity(quantity);

    }

    /**
     * Calculates the price of the order.
     *
     * quantity is the number of cups of coffee ordered
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int totalPricePerCoffee = perCoffeePrice;
        if (hasWhippedCream) {
           totalPricePerCoffee = totalPricePerCoffee + 1;
        }
        if (hasChocolate)  {
            totalPricePerCoffee = totalPricePerCoffee + 2;
        }
        int totalPrice = quantity * totalPricePerCoffee;
        return totalPrice;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int amount) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_Text_View);
        quantityTextView.setText("" + amount);
    }


    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_Text_View);
        orderSummaryTextView.setText(message);
    }

    private String createOrderSummary(int totalPrice, String name, boolean addWhippedCream, boolean addChocolate) {
        String priceMessage = getString(R.string.name) + ": " + name;
        priceMessage = priceMessage + "\n" + getString(R.string.addWhippedCream) + ": " + addWhippedCream;
        priceMessage = priceMessage + "\n" + getString(R.string.addChocolate) + ": " + addChocolate;
        priceMessage = priceMessage + "\n" + getString(R.string.quantity) + quantity;
        priceMessage = priceMessage + "\n" + getString(R.string.total) + "$" + totalPrice;
        priceMessage = priceMessage + "\n" + getString(R.string.thankYou);
        return priceMessage;
    }

}