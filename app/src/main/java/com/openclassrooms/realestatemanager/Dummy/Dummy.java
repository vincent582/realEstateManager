package com.openclassrooms.realestatemanager.Dummy;


import com.openclassrooms.realestatemanager.Model.Property;
import com.openclassrooms.realestatemanager.Model.User;
import com.openclassrooms.realestatemanager.Model.Address;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Dummy {

    public static User mUser = new User("Vincent");

    public static Address mAddress = new Address(515,"Broadway","NewYork","NY",10012,"United States");

    public static List<String> facilities = Arrays.asList(
            "School","Station","Supermarket");

    public static List<Property> sPropertyList = Arrays.asList(
            new Property(1, "House",12000000,200,7,"Stunning Loft at the Heart of Soho. VIRTUAL TOUR AVAILABLE! Fabulous 4800+ sf live work loft with every original detail you could want. MASSIVE oversized windows; soaring ceilings; wood beams; and hardwood floors. This space is currently configured as a massive open space, with master sleep area at one end and a guest area at the other. Chef's Kitchem plus two full bathrooms round out the space. The space can easily adapt to a number of configurations and uses.",
                    mAddress,facilities,"Vendu",new Date(),new Date(),mUser),
            new Property(2, "Flat",26070000,150,5,"Stunning Loft at the Heart of Soho. VIRTUAL TOUR AVAILABLE! Fabulous 4800+ sf live work loft with every original detail you could want. MASSIVE oversized windows; soaring ceilings; wood beams; and hardwood floors. This space is currently configured as a massive open space, with master sleep area at one end and a guest area at the other. Chef's Kitchem plus two full bathrooms round out the space. The space can easily adapt to a number of configurations and uses.",
                    mAddress,facilities,"A vendre",new Date(),new Date(),mUser),
            new Property(3, "House",21000000,320,8,"Stunning Loft at the Heart of Soho. VIRTUAL TOUR AVAILABLE! Fabulous 4800+ sf live work loft with every original detail you could want. MASSIVE oversized windows; soaring ceilings; wood beams; and hardwood floors. This space is currently configured as a massive open space, with master sleep area at one end and a guest area at the other. Chef's Kitchem plus two full bathrooms round out the space. The space can easily adapt to a number of configurations and uses.",
                    mAddress,facilities,"A vendre",new Date(),new Date(),mUser),
            new Property(4, "PentHouse",18006000,600,12,"Stunning Loft at the Heart of Soho. VIRTUAL TOUR AVAILABLE! Fabulous 4800+ sf live work loft with every original detail you could want. MASSIVE oversized windows; soaring ceilings; wood beams; and hardwood floors. This space is currently configured as a massive open space, with master sleep area at one end and a guest area at the other. Chef's Kitchem plus two full bathrooms round out the space. The space can easily adapt to a number of configurations and uses.",
                    mAddress,facilities,"Vendu",new Date(),new Date(),mUser)
    );
}
