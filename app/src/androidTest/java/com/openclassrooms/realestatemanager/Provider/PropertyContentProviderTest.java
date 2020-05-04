package com.openclassrooms.realestatemanager.Provider;

import android.content.ContentResolver;
import android.database.Cursor;
import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.openclassrooms.realestatemanager.Database.RealEstateManagerDataBase;
import com.openclassrooms.realestatemanager.Utils.PropertyProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class PropertyContentProviderTest {

    private ContentResolver mContentResolver;

    @Before
    public void setUp(){
        Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
            RealEstateManagerDataBase.class)
            .allowMainThreadQueries()
            .build();
        mContentResolver = InstrumentationRegistry.getContext().getContentResolver();
    }

    @Test
    public void getProperties(){
        final Cursor cursor = mContentResolver.query(PropertyProvider.URI_PROPERTY,null,null,null,null);
        assertEquals(2,cursor.getCount());
        if (cursor.moveToFirst()){ // data?
            System.out.println("AddedDate :" + cursor.getString(cursor.getColumnIndex("addedDate")));
            System.out.println("Id :" + cursor.getString(cursor.getColumnIndex("id")));
            System.out.println("Price :" + cursor.getString(cursor.getColumnIndex("price")));
            System.out.println("Facilities : " + cursor.getString(cursor.getColumnIndex("facilities")));
        }
        cursor.close();
    }
}
