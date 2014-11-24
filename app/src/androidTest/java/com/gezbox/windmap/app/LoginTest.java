package com.gezbox.windmap.app;

import android.test.ActivityInstrumentationTestCase2;
import com.gezbox.windmap.app.activity.LoginActivity;
import com.robotium.solo.Solo;

/**
 * Created by zombie on 14-11-10.
 */
public class LoginTest extends ActivityInstrumentationTestCase2<LoginActivity> {
    private Solo solo;

    public LoginTest() {
        super(LoginActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    protected void tearDown() throws Exception {
        try {
            solo.finalize();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        getActivity().finish();
        super.tearDown();
    }

    public void testStartLoginActivity() throws Exception {
        solo.unlockScreen();

        solo.clickOnButton("登录");
        solo.takeScreenshot("empty_submit");

        solo.enterText(0, "123456");
        solo.clickOnButton("登录");
        solo.takeScreenshot("empty_tel");
    }

    public void testSubmit() throws Exception {
        solo.unlockScreen();

        solo.enterText(0, "123456");
        solo.enterText(1, "1234");
        solo.clickOnButton("登录");
        solo.takeScreenshot("error_input");
    }
}
