Stock Market Data Microservice Integration

Description:
Implemented a microservices architecture for integrating with a third-party API 
to retrieve stock market data. The project utilized the Stocks Feed API, 
an open API providing stock prices and company information without authentication 
requirements. The documentation for the API can be found at
[https://iexcloud.io/docs/api/].

Key Features and Requirements:

1. Developed a Spring Boot application without a web interface, designed to output messages to the console.
2. Upon application startup, initiated a request to the Stocks API (https://sandbox.iexapis.com/stable/ref-data/symbols?token=Tpk_ee567917a6b640bb8602834c9d30e571) 
to fetch symbols, names, and statuses of each trading company.
3. Iterated through each activated company and queued requests to retrieve stock data.
4. Implemented multiple threads for processing the queue, responsible for fetching current stock prices for each 
company using the API endpoint (https://sandbox.iexapis.com/stable/stock/{stock code}/quote?token={token}).
5. Persisted data from each stock request into a structured database, enabling queryability. 
The database could either be an in-memory solution integrated into the project or an external solution like a Docker container 
or a local instance of Postgres.
6. Regularly displayed statistical data in the console window, such as the top 5 stocks with the highest value 
(sorted by price and then by company name) and the last 5 companies with the highest percentage change in 
stock value (calculated using latestPrice).
7. Implemented continuous data retrieval process to update stock information for each company. 
If there were changes in stock information, they were recorded in the database, creating a log of changes 
for each company's stocks over time.

Technologies Used: Spring Boot, Java, API Integration, Multithreading, Database Integration, CI/CD on Amazon EKS using AWS CodeCommit, 
AWS CodePipeline, AWS CodeBuild.

This project demonstrated proficiency in designing and implementing microservices, API integration, asynchronous processing, 
and database management within a distributed architecture.