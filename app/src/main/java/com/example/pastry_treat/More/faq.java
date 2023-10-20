package com.example.pastry_treat.More;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;

import com.example.pastry_treat.R;

import org.xml.sax.XMLReader;

import java.io.InputStream;
import java.util.Scanner;

public class faq extends AppCompatActivity {

    private TextView qna;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.faq);

        ActionBar actionBar = getSupportActionBar(); //actionbar = toolbar
        if (actionBar != null) {
            actionBar.hide();
        }

        qna = findViewById(R.id.qna);
        String faqText = readFAQsFromRawResource(R.raw.faq);

        faqText = faqText.replace("<b>", "<strong>").replace("</b>", "</strong>");

        Spanned formattedFaqText = Html.fromHtml(faqText);
        qna.setText(formattedFaqText);

    }

    private String readFAQsFromRawResource(int resourceId) {
        try {
            // Open the raw resource
            InputStream inputStream = getResources().openRawResource(resourceId);

            // Read the contents of the resource
            Scanner scanner = new Scanner(inputStream);
            StringBuilder faqTexts = new StringBuilder();
            while (scanner.hasNextLine()) {
                faqTexts.append(scanner.nextLine()).append("<br>"); // Add <br> for line breaks
            }

            // Close the input stream
            inputStream.close();

            return faqTexts.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "FAQs not available";
        }
    }


    public void onBackPressed() {
        super.onBackPressed();
    }
}