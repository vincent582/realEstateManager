package com.openclassrooms.realestatemanager.Dummy;


import com.openclassrooms.realestatemanager.Model.Property;
import com.openclassrooms.realestatemanager.Model.User;
import com.openclassrooms.realestatemanager.Model.Address;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Dummy {

    public static User mUser = new User("Vincent");

    public static Address mAddress = new Address(515,"Broadway","NewYork","NY",10012,"United States");
    public static Address mAddress1 = new Address(487-409,"Gates Ave","Brooklyn","NY",11216,"United States");
    public static Address mAddress2 = new Address(98-84,"Liberty St","New York","NY",10006,"United States");
    public static Address mAddress3 = new Address(241,"Central Park West","Manhattan","NY",10024,"United States");

    public static List<String> facilities = Arrays.asList(
            "School","Station","Supermarket");

    public static List<String> propertyType = Arrays.asList(
            "House","Flat","PentHouse","Manor");


    public static List<Property> sPropertyList = Arrays.asList(
            new Property(1, "House",12000000,200,7,"Stunning Loft at the Heart of Soho. VIRTUAL TOUR AVAILABLE! Fabulous 4800+ sf live work loft with every original detail you could want. MASSIVE oversized windows; soaring ceilings; wood beams; and hardwood floors. This space is currently configured as a massive open space, with master sleep area at one end and a guest area at the other. Chef's Kitchem plus two full bathrooms round out the space. The space can easily adapt to a number of configurations and uses.",
                    mAddress,facilities,true,new Date(),new Date(),mUser),
            new Property(2, "Flat",26070000,150,5,"Wonderful Modern 1 bedroom 1 bathroomKitchen consists of imported Italian cabinetry, ceasar stone counter, Blomberg stainless steel appliancesBathrooms equipped with Duravit deep soaking tubs and Toto toiletMaple engineered wood flooring throughout the unitWest FacingKing size bedroom w/ large sliding door closetIn Washer / DryerLarge private terrace (approximately 20&amp;rsquo;x20&amp;rsquo;)&amp; Building amenities include:Landscaped Roof Deck,Fitness centerVirtual doormanBicycle storageParking and Storage available for rent (Listing 6950785 Confirmed 3/29/2020.)",
                    mAddress1,facilities,false,new Date(),new Date(),mUser),
            new Property(3, "House",21000000,320,8,"Beautiful attached 1 family home in St. Albans. Fully brick construction with fireplace. 4 bedrooms, 1 full bath, kitchen, living room/ dining room on first floor along with a rear deck. Full finished basement, private driveway and garage.",
                    mAddress2,facilities,false,new Date(),new Date(),mUser),
            new Property(4, "PentHouse",18006000,600,12,"Stunning Loft at the Heart of Soho. VIRTUAL TOUR AVAILABLE! Fabulous 4800+ sf live work loft with every original detail you could want. MASSIVE oversized windows; soaring ceilings; wood beams; and hardwood floors. This space is currently configured as a massive open space, with master sleep area at one end and a guest area at the other. Chef's Kitchem plus two full bathrooms round out the space. The space can easily adapt to a number of configurations and uses.",
                    mAddress3,facilities,false,new Date(),new Date(),mUser)
    );

    public static ArrayList<Property> generateListProperties(){
        return new ArrayList<>(sPropertyList);
    }
}
