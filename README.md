# MeetingRoom

To do:

Create Room entity [Solved in R-0.0.2]

Create in-memory repository [Solved in R-0.0.2]

Add read and add functionality for the room [Solved in R-0.0.2]

Create Seat entity [Solved in R-0.0.3]

Add update functionality for the room [Solved in R-0.0.3]

Create tests for the controller (Mockito and JUnit) [Solved in R-0.0.4]

Add delete functionality for a room. [Solved in R-0.0.5]

Create a function that returns all the available seats for a room. [Solved in R-0.0.6]

Create a service layer between controller and repository layers. [Solved in R-0.0.7]

Create a separate repository for Seat object. [Solved in R-0.0.8]

Create integration tests for controller.

Updates:

[U-0.0.6.1] 
- In MainController, changed components injection to constructor injection
- In MainControllerTest, changed the name of the components to be more suggestive
- In SeatDto, changed the modifiers of fields

[R-0.0.9]
- Switched to a new API for the repositories. Used springframework CrudRepository interface.