# Changes to Implementation

- DCD Available to edit: https://www.lucidchart.com/invitations/accept/55f1d5e4-90d9-4b43-94f2-e7201fd422d1
- Implemented the Properties file as suggested in the spec
- The suggested implementation in the code reference increased cohesion to the Simulation Class, so I implemented a Singleton Pattern based on the Information Expert principle. I don't think this counts strictly as a "Design Flaw" though.
- Removed the seedMap as it was an inappropriate use of a HashMap. (I mean it worked but an int is more than sufficient. Breaks the coding principal You Ain't Gonna Need It).
- Removed all Seed allocation from Simulation class as it increased cohesion (by passing variables which can otherwise be acquired elsewhere)
- Changes Property Code to make it a Public Static variable within the Simulation class.
- Fixed Magic Numbers (They're now in the properties file)
- Edited the StorageTube Constructor to allow multiple Tube sizes (But still need to be defined in-code for now)
- Will probably fix the coding style to be more... Consistent
- Moved and Refactored ReportDelivery() as it makes no sense to use the interface then lock the instance inside the simulation code.