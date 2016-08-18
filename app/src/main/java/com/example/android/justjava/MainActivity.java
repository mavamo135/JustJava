package com.example.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    int quantity=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void submitOrder(View view) {
        EditText nameText = (EditText) findViewById(R.id.name_view);
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        CheckBox chocolateToppingCheckBox = (CheckBox) findViewById(R.id.chocolate_topping_checkbox);
        int price = calculatePrice(quantity, whippedCreamCheckBox.isChecked(), chocolateToppingCheckBox.isChecked());
        String orderSummary = createOrderSummary(nameText.getText().toString(),quantity, price, whippedCreamCheckBox.isChecked(),
                                                    chocolateToppingCheckBox.isChecked());
        Toast.makeText(this, R.string.infoJava, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        //intent.putExtra(Intent.EXTRA_EMAIL, emailText.getText().toString());
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.justJavaOrderFor) + nameText.getText().toString());
        intent.putExtra(Intent.EXTRA_TEXT, orderSummary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        /*
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo:47.6, -122.3"));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }*/
    }
    public void decrement(View view) {
        if (quantity==1) {
            Toast.makeText(this, R.string.lessCoffeeJava, Toast.LENGTH_SHORT).show();
            return;}
        quantity--;
        display(quantity);
    }
    public void increment(View view) {
        if (quantity==99) {
            Toast.makeText(this, R.string.moreCoffeesJava, Toast.LENGTH_SHORT).show();
            return;}
        quantity++;
        display(quantity);
    }
    private int calculatePrice(int number, boolean whippedCream, boolean chocolateTopping) {
        int price = 5;
        if (whippedCream){price += 1;}
        if(chocolateTopping){price += 2;}
        return price*number;
    }
    private String createOrderSummary(String name, int number, int price, boolean whippedCream, boolean chocolateTopping ){
        String priceMessage = getString(R.string.nameJava) + name;
        priceMessage += getString(R.string.quantityJava) + number;
        priceMessage += getString(R.string.whippedCreamJava) + whippedCream;
        priceMessage += getString(R.string.chocolateToppingJava) + chocolateTopping;
        priceMessage += getString(R.string.totalJava) + NumberFormat.getCurrencyInstance().format(price);
        priceMessage += getString(R.string.thankYouJava);
        return priceMessage;
    }
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
}