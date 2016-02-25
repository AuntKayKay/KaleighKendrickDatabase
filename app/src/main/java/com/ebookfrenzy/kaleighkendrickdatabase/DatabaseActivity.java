package com.ebookfrenzy.kaleighkendrickdatabase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class DatabaseActivity extends AppCompatActivity {

    TextView idView;
    EditText productBox;
    EditText quantityBox;

    public void newProduct (View view) {
        int quantity = -1;
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        //Tries to parse the quantity to an integer.
        //If that fails, it throws a stack trace.  quantity = -1
        try{
        quantity =
                Integer.parseInt(quantityBox.getText().toString());
        }catch (Throwable e) {
            e.printStackTrace();
        }
        //If the quantity is greater than or equal to zero, it captures the input and tries to add the product.
        if(quantity>=0) {
            Product product =
                    new Product(productBox.getText().toString(), quantity);

            dbHandler.addProduct(product);
            idView.setText("Product Added");
            productBox.setText("");
            quantityBox.setText("");
        } else {
            //Displays "Failed to Add" if product add is unsuccessful.
            idView.setText("Failed to Add");
        }

    }

    public void lookupProduct (View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        Product product =
                dbHandler.findProduct(productBox.getText().toString());

        if (product != null) {
            idView.setText(String.valueOf(product.getID()));

            quantityBox.setText(String.valueOf(product.getQuantity()));
        } else {
            idView.setText("No Match Found");
        }
    }

    public void updateProduct (View view) {
        int id = 0;
        boolean result = false;

        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        //Tries to parse the ID of the productID text field to an integer.
        //If that fails, it throws a stack trace.  ID = 0
        try {
            id =
                    Integer.parseInt(idView.getText().toString());
        }catch (Throwable e) {
            e.printStackTrace();
        }

        //If the ID is greater than zero, it captures the input and tries to update the product.
        if(id>0) {
            int quantity =
                    Integer.parseInt(quantityBox.getText().toString());

            Product product =
                    new Product(id, productBox.getText().toString(), quantity);

            result = dbHandler.updateProduct(product);
        }
        //If everything is groovy, displays "Update Record"
        if (result)
        {
            idView.setText("Record Updated");
            productBox.setText("");
            quantityBox.setText("");
        }
        //If everything isn't groovy, displays "Update Failed"
        else
            idView.setText("Update Failed");
            productBox.setText("");
            quantityBox.setText("");

    }

    public void removeProduct (View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null,
                null, 1);

        boolean result = dbHandler.deleteProduct(
                productBox.getText().toString());

        if (result)
        {
            idView.setText("Record Deleted");
            productBox.setText("");
            quantityBox.setText("");
        }
        else
            idView.setText("No Match Found");
    }

    public void removeAllProducts (View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null,
                null, 1);

        boolean result = dbHandler.deleteAllProducts();

        if (result)
        {
            idView.setText("Records Deleted");
            productBox.setText("");
            quantityBox.setText("");
        }
        else
            idView.setText("No Match Found");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        idView = (TextView) findViewById(R.id.productID);
        productBox = (EditText) findViewById(R.id.productName);
        quantityBox =
                (EditText) findViewById(R.id.productQuantity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_database, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
