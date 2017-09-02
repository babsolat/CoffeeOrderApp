/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 */
package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /**
     * This method is called when the minus(-) button is clicked.
     */

    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, "You cannot have less than 1 coffees", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);

    }

    /**
     * This method is called when the plus(+) button is clicked.
     */
    public void increment(View view) {
        if (quantity == 20) {
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity + 1 ;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkBox) ;
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();



        EditText viewName = (EditText) findViewById(R.id.editText);
        String name  = viewName.getText().toString();


        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkBox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int price =  calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = CreateOrderSummary(price, hasWhippedCream, hasChocolate,name ) ;


        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for coffee: " + name );
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage );
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

//            displayMessage(priceMessage);
    }


    /**
     * Calculates the price of the order.
     * needWhippedCream is to ask if the buyer wants whippedcream
     * addChocolate is to ask if the buyer wants addChocolate
     *
     */


    public int calculatePrice(boolean needWhippedCream,boolean needChocolate ) {

        int basePrice = 5;

        /** when whippedCream is added to the coffee   */


        if (needWhippedCream) {
            basePrice = basePrice + 1 ;
        }

        /** when chocolate is added to the coffee   */
        if (needChocolate){
            basePrice = basePrice +2 ;
        }
        /** total price of coffee is calculated with whipped cream and chocolate when requested   */
        return quantity * basePrice ;
    }

    /**
     *  create summary of the order

     * @param price of the order
     * addWhippedCream is whether or not the user want whipped cream topping
     * @return text summary
     */

    public String CreateOrderSummary (int price, boolean addWhippedCream, boolean addChocolate,String name ){
        String priceMessage = "Name: Legendary Babsolat";
        priceMessage = priceMessage + "\nAdd Whipped Cream ? " + addWhippedCream ;
        priceMessage = priceMessage + "\nAdd Chocolate ? " + addChocolate;
        priceMessage = priceMessage + "\nQuantity: " + quantity ;
        priceMessage = priceMessage + "\nTotal: $ " + price;
        priceMessage = priceMessage + "\nThank You! ";
        priceMessage = priceMessage + "\nName: " + name ;

        return (priceMessage);
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }

//    /**
//     * This method displays the given text on the screen.
//     */
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView  = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }

}



