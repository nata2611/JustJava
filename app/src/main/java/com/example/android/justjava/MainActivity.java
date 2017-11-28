/**
 * IMPORTANT: Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava; 
 *
 */
package com.example.android.justjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /*Set initial value of number of coffee*/
    int quantityOfCup = 0;

    /*This method increments a quantity*/
    public void increment(View view) {
        if (quantityOfCup == 8) {
            String notificationText = "You can't have more than 8 coffee.";
            showToast(view, notificationText);
            return;
        }
        quantityOfCup++;
        displayQuantity(quantityOfCup);
    }
    /*This method decrements a quantity*/
    public void decrement(View view) {
        if (quantityOfCup == 0) {
            String notificationText = "You can't have less than 1 coffee.";
            showToast(view, notificationText );
            return;
        }
        quantityOfCup--;
        displayQuantity(quantityOfCup);
    }
    /*This method show small popup notification*/
    public void showToast(View view, String text) {
        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        toast.show();
    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        // Get user name
        EditText nameEditText = (EditText) findViewById(R.id.name_edit_text);
        Editable nameEditable = nameEditText.getText();
        String name = nameEditable.toString();

        // Figure out if the user wants whipped cream topping
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        String wantWhippedCream = "No";
        if (quantityOfCup > 0) {
            if (hasWhippedCream) {
                wantWhippedCream = "Yes";
            }
        }

        // Figure out if the user wants chocolate topping
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        String wantChocolate = "No";
        if (quantityOfCup > 0){
            if (hasChocolate) {
                wantChocolate = "Yes";
            }
        }

        //Calculate the price
        int price = calculatePrice(hasWhippedCream, hasChocolate, quantityOfCup);

        // Display the order summary on the screen
        String priceMessage = createOrderSummary (name, price, hasWhippedCream, hasChocolate, wantWhippedCream, wantChocolate);
        displayMessage (priceMessage);

        // Use an intent to launch an email app.
        // Send the order summary in the email body.
    }

/*Calculates the price of the order.
*
* @param addWhippedCream is whether or not we should include cream topping in the price
* @param addChocolate    is whether or not we should include chocolate topping in the price
* @return total price
*/

    private int calculatePrice (boolean addWhippedCream, boolean addChocolate, int quantityOfCup) {
        // Calculate the price of one cuo of coffee
        int basePrice = 5;
        //Price with whipped cream
        if (addWhippedCream ) {
            basePrice += 1;
        }
        //Price with chocolate
        if (addChocolate) {
            basePrice += 2;
        }
        // Calculate the total order price
        return  quantityOfCup *  basePrice;
    }
/*Create summary of the order.
*
* @param name
* @param price
* @param addWhippedCream
* @param addChocolate
* @return text summary
*/
    private String createOrderSummary (String name, int price, boolean addWhippedCream, boolean addChocolate, String wantWhippedCream, String wantChocolate ) {
        String priceMessage = name;
        priceMessage += "\nAdd Whipped Cream? " + wantWhippedCream;
        priceMessage += "\nAdd Whipped Chocolate? " + wantChocolate;
        priceMessage += "\nQuantity Of Cup: " + quantityOfCup;
        priceMessage += "\nPrice: " + NumberFormat.getCurrencyInstance().format(price);
        priceMessage += "\n" + "Thank you!";
        return priceMessage;
    }

//    private String createOrderSummary (String name, int price, boolean addWhippedCream, boolean addChocolate) {
//        String priceMessage = getString(R.string.order_summary_name, name);
//        priceMessage += "\n" + getString(R.string.order_summary_whipped_cream, addWhippedCream);
//        priceMessage += "\n" + getString(R.string.order_summary_chocolate, addChocolate);
//        priceMessage += "\n" + getString(R.string.order_summary_quantity, quantityOfCup);
//        priceMessage += "\n" + getString(R.string.order_summary_price, NumberFormat.getCurrencyInstance().format(price));
//        priceMessage += "\n" + getString(R.string.thank_you);
//        return priceMessage;
//    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }
    private void displayMessage (String message) {
        TextView summary = (TextView) findViewById(R.id.order_summary_text_view);
        summary.setText("" + message);
    }

}