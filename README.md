# Credit Application System Project
This is a capstone project for TODEB - Patika - Java Spring Bootcamp!

The project was constructed to be able to make a credit application. The bankers can
make easily a credit application for customers.

The project has two different basic roles. 

These are:
- Admin (Banker)
- Customer

These roles were constructed and set in the project. While the project was running an
admin sample and a user sample was made. Of course, the abilities of these are different. 
These differences make an opportunity to manage the authorization of the project. 
For example the users can just search their own credits with using their nationalNumberId.
All other operations are managed with admin/banker.

### Credit Application Path
- **For Admin**
  1. Sign In
  2. Create Customer
  3. Calculate Customer's Credit Score
  4. Make A Credit Application For Customer
  5. Send Credit Result To Customer Cell Phone By Sms

- **For Customer**
  1. Sign Up / Sign In
  2. Get Credit Result With nationalNumberId


You can check all these steps with using Postman.

The project runs at `8090` port

[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/1863f494813dba8e1718?action=collection%2Fimport)

Also, you can use Swagger to practice this project.

[Swagger UI](http://localhost:8090/swagger-ui/index.html)

## Technical Details

The project was constructed with using 
<p align="left"> 
<a href="https://www.java.com" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="java" width="40" height="40"/> </a>
<a href="https://spring.io/" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/springio/springio-icon.svg" alt="spring" width="40" height="40"/> </a> 
<a href="https://www.postgresql.org" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/postgresql/postgresql-original-wordmark.svg" alt="postgresql" width="40" height="40"/> </a>
</p>

### Requirements

- _**Customer**_
    - nationalNumberId
    - firstName
    - lastName
    - age
    - salary
    - phoneNumber
    - creditScore
    - credits
- _**Credit**_
    - applicationDate
    - creditLimit
    - creditStatus
    - customer
- _**Sms**_
  - smsStatus

<p> CRUD operations for Customer</p>
<p> Calculate credit score </p>
<p> Determining a customer-specific credit limit according to the determined 
conditions </p>
<p> Application is viewed with Customer nationalNumberId</p>

### Analysis
- Customer data is saved in the database, can be updated, deleted.
- Customer credit score is created.
- A customer-specific credit limit is added.
- Credit approval data is created.
- Credit approval data is sent to the customer via SMS.
- The customer can view the credit application.

### Design

<img src="attachments/CreditApplicationSystemUMLCase.png" width="1000"/>

### Endpoints
![](attachments/EndPoints.png)