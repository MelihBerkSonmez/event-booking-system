package com.event_booking_system.app;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.event_booking_system.classes.*;
import com.event_booking_system.enums.*;
import com.event_booking_system.systems.*;
import com.event_booking_system.io.FileIO;

public class TicketBookingApp {

    public static void main(String[] args) {
        Path eventsFilePath = Paths.get("events.csv");
        Path customersFilePath = Paths.get("customers.csv");

        ArrayList<Event> events1 = FileIO.readEvents(eventsFilePath);
        ArrayList<Customer> customers1 = FileIO.readCustomers(customersFilePath);

        // Phase 1
        BookingSystem bookingSystem1 = new BookingSystem(events1, customers1); //group policies are disabled

        bookingSystem1.printSystemInfo(); // (1)
        bookingSystem1.executeCommonOperations(); // (2)

        // query will not modify the actual objects, deep copying them
        Query query = new Query(bookingSystem1.getEvents(), bookingSystem1.getCustomers(), bookingSystem1.getBookings()); // (3)
        query.executeQueriesInOrder();

        // Phase 2
        ArrayList<Event> events2 = FileIO.readEvents(eventsFilePath);
        ArrayList<Customer> customers2 = FileIO.readCustomers(customersFilePath);

        // Group Rule
        int groupNumber = 23;
        int ruleNumber = ((groupNumber - 1) % 5) + 1;
        int policyIndex = ruleNumber - 1; // 0 based indexing

        GroupPolicy[] policies = GroupPolicy.values();
        GroupPolicy selectedPolicy = policies[policyIndex];

        //Printing Group Infos
        System.out.println("========== GROUP-SPECIFIC RULE ==========");
        System.out.println("Group: " + groupNumber);
        System.out.println("Rule: " + selectedPolicy);

        BookingSystem bookingSystem2 = null;
        switch (selectedPolicy) {
            case GroupPolicy.CATEGORY_RESTRICTION:
                int restrictedCategoryIndex = groupNumber % 3;
                String[] restrictedCategories = {"CONCERT", "SPORTS", "CONFERENCE"};

                EventCategory restrictedCategory = EventCategory.valueOf(restrictedCategories[restrictedCategoryIndex]);
                System.out.println("Parameter: " + restrictedCategory);

                bookingSystem2 = new BookingSystem(events2, customers2, restrictedCategory);
                break;
            default:
                System.out.println("Only implemented our group policy.");
                System.exit(1);
        }

        bookingSystem2.executeGroupSpecificOperations();

    }
}
