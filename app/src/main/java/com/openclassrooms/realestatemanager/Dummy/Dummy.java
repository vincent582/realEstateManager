package com.openclassrooms.realestatemanager.Dummy;

import com.openclassrooms.realestatemanager.Model.Property;
import com.openclassrooms.realestatemanager.Model.User;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Dummy {

    public static User mUser = new User("Vincent");

    public static List<String> facilities = Arrays.asList(
            "School","Station","Supermarket");

    public static List<Property> sPropertyList = Arrays.asList(
            new Property(1, "House",12000000,200,7,"nzejbuzi fz guzg z gzguizghui zuig iuzg uizuig uizgui zuieuig iuez guz euig uiz",
                    facilities,"Vendu",new Date(),new Date(),mUser),
            new Property(2, "Flat",26070000,150,5,"nzejbuzi fz guzg z gzguizghui zuig iuzg uizuig uizgui zuieuig iuez guz euig uiz",
                    facilities,"A vendre",new Date(),new Date(),mUser),
            new Property(3, "House",21000000,320,8,"nzejbuzi fz guzg z gzguizghui zuig iuzg uizuig uizgui zuieuig iuez guz euig uiz",
                    facilities,"A vendre",new Date(),new Date(),mUser),
            new Property(4, "PentHouse",18006000,600,12,"nzejbuzi fz guzg z gzguizghui zuig iuzg uizuig uizgui zuieuig iuez guz euig uiz",
                    facilities,"Vendu",new Date(),new Date(),mUser)
    );
}
