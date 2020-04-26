package com.openclassrooms.realestatemanager.Utils;

import android.content.Context;

import androidx.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UtilsTest {

    private Context appContext;

    @Before
    public void setUp() throws Exception {
        appContext = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void isInternetAvailable() {

    }
}
