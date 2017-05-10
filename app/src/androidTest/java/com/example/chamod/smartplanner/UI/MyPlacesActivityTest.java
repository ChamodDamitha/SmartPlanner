package com.example.chamod.smartplanner.UI;
//
///**
// * Created by chamod on 5/3/17.
// */
//
import android.support.test.espresso.Espresso;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.example.chamod.smartplanner.MyPlacesActivity;
import com.example.chamod.smartplanner.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MyPlacesActivityTest {

    private static final String MESSAGE = "test";
    private static final String PACKAGE_NAME = "com.example.myfirstapp";

    /* Instantiate an IntentsTestRule object. */
    @Rule
    public IntentsTestRule<MyPlacesActivity> mIntentsRule =
            new IntentsTestRule<>(MyPlacesActivity.class);

    @Test
    public void verifyMyPlaceActivityViewDisplay() {

        // Types a message into a EditText element.
        Espresso.onView(withId(R.id.textViewLocAddress))
                .check(ViewAssertions.matches(isDisplayed()));

//        // Clicks a button to send the message to another
//        // activity through an explicit intent.
//        onView(withId(R.id.send_message)).perform(click());
//
//        // Verifies that the DisplayMessageActivity received an intent
//        // with the correct package name and message.
//        intended(allOf(
//                hasComponent(hasShortClassName(".DisplayMessageActivity")),
//                toPackage(PACKAGE_NAME),
//                hasExtra(MainActivity.EXTRA_MESSAGE, MESSAGE)));

    }


}
