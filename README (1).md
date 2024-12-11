
#Ticket Management System

##Project Overview

The Ticket Management System is a robust application designed to manage event ticketing operations. It provides functionality for vendors to release tickets, customers to purchase or cancel tickets, and administrators to monitor the overall system. This project combines a console-based interface for system setup and operations, as well as a user-friendly GUI for ticket management.

#Features

##Core Functionality

Vendor Operations:

    Release tickets into a synchronized ticket pool.
    Manage ticket availability and monitor system performance.

Customer Operations:

    Purchase tickets from the pool.
    Cancel purchased tickets using a ticket ID.

Admin Monitoring:

    View system status, including active tickets and vendor/customer details.



GUI Highlights
    Dropdown menu for selecting ticket categories.
    Low-availability tickets are highlighted in red for visibility.
    Details of selected tickets are displayed for users.
    Add tickets to the cart and dynamically calculate final prices             with applicable discounts.

# Prerequisites

    1.Java: Version 23
    2.Maven 
    3.IDE: IntelliJ IDEA

# Build and Run the Application

    1.Navigate to the project directory: https://github.com/Chanuthi02/Real-Time-Ticket-Eventing-System

    2.Necessary depedencies: JavaFX and Log4j as specified in the pom.xml file.


##Other Features

Discount Management:
    First-purchase discounts for new users.
    Discounts for purchasing multiple products in the same category.

Data Persistence:
    Save ticket data to a file and reload it during subsequent application runs.

Logging:
    All system actions are logged, ensuring traceability.


##System Architecture 

Classes

    Ticket: Represents a ticket with attributes such as ID, event name, and price.
    Vendor: Simulates vendors releasing tickets into the pool.
    Customer: Manages customer interactions, including purchasing and canceling tickets.
    TicketPool: A synchronized queue for managing tickets, ensuring thread safety.
    Logger: Handles logging of events, such as ticket purchases and cancellations.
    Configuration: Manages system configuration, allowing customization of ticket pool size and release rates.


User Interface
    Console Menu: Offers basic operations such as adding tickets, removing tickets, viewing tickets, and saving/loading data.

    Graphical User Interface (GUI): Provides a more intuitive way to interact with the system, ideal for end-users.


Setup Instructions

##Prerequisites

    Java Development Kit (JDK) 11 or later.
    Any IDE or text editor supporting Java (e.g., IntelliJ IDEA, Eclipse).
    Gson Library for JSON file operations (included in the dependencies).

##Steps to Run

    Clone the repository or download the source code.
    Open the project in your IDE.
    Ensure all dependencies (e.g., Gson) are resolved.
    Run the UserInterface class to start the GUI-based system.
    Optionally, run the console-based menu for testing core functionalities.

##Running Tests

    The project includes unit tests for core operations such as ticket addition, removal, and cancelation.

    Use the provided test plan to validate all functionalities manually or automatically.

##Testing

The system has undergone rigorous testing with the following focus:

Console Operations:
    Adding, removing, and viewing tickets.
    Input validation and error handling.

GUI Functionality:
    Displaying and selecting tickets.
    Adding tickets to the cart with proper discount calculations.

Concurrency:
    Synchronized ticket pool operations for vendors and customers.

Logging:
    Logging all major events for traceability.
    Future Improvements
    
Enhanced Reporting: 
    Add detailed reports for admin users, including graphical statistics on ticket sales.

Search Functionality: 
    Enable searching for tickets by ID or event name.

Advanced Discount Mechanisms: 
    Support complex discount scenarios based on ticket bundles.


##Contributors
R.M.C.Nuhansa Wickramasinghe
20231590
w2052172

##Contact Information
For any queries or issues, feel free to reach out:
Email: chanuthi.20231590@iit.ac.lk / w20521722@westminster.ac.uk
GitHub: https://github.com/Chanuthi02

