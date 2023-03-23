Car Parking Micro Service
---
1. When the car comes in, check if there is a space and allocate space if available, otherwise give message saying full.   Let us assume the car park size is 100.
2. When the car leaves, calculate the time spent and charge £2 per hour.  We don’t need the payment module but just returning the amount is enough.
3. Handle multiple cars coming at the same time.

How to run the Application:
----
User will be able to start the application using one of the below steps :
1. mvn spring-boot:run
2. run the CarParkingApplication Class in IDE

Endpoints:
---
* localhost:8082/car (GET):  Load the Car Parking Details 
* localhost:8082/carAudit (GET) : Load all the Car Parking Activity including Current Parking Details
* localhost:8082/checkIn (POST) : Check the Spaces available, and Car not already Parked , then assign a Parking space
* localhost:8082/checkOut (POST) : Check Car in Parking, if yes, Calculate the Price and release the space
* localhost:8082/getSpaces(GET): To get the Number of Spaces Available ( Currently total spaced hardcoded to 100)
