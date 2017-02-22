# Target Case Study - REST API

## Quick-Start
View the application at https://frozen-citadel-78149.herokuapp.com/products/{id}. Seed pricing data has been placed in the database for ID's 1, 2, and 3. The proper name will be fetched using the sample IDs supplied in the prompt, such as 13860428.

## Overview of Project
This project uses the Spring framework and MongoDb hosted through Heroku.

### Use of MongoDB
MongoDB tends to excel in use cases with frequent writes and seldom reads. These characteristics make MongoDB a sub-optimal choice for this project, in which fetching an item's price will likely happen much more often than updating the price. With these factors in mind, I chose to use MongoDB regardless because:
1) The database can easily handle the miniscule amount of data in this toy app, and
2) MongoDB is the only NoSQL database on Heroku with a free pay tier

In a production environment, we would need to reconsider what the best database is.

### Products Application
After starting, the Application calls `DbHelper` to add some dummy seed data to the database. This is not strictly necessary and should not be in a production environment. However, the seed data makes it easy to sanity-check simple functionality.

### DbHelper
`DbHelper` functions as a Singleton. (Note that it still may be loaded/unloaded during the lifetime of the app, which is suboptimal). Upon creation, it sets up a connection with the NoSQL database and keeps this connection open. It provides an easy interface to look up prices, update prices, and add seed data.

### Products Controller
There is one Controller in this project that handles both the `GET` and `PUT` to `/products/{id}`. This Controller handles the `HTTP` request to `redsky.target.com` using `RestTemplate`. All access to the NoSQL database goes through the Singleton `DbHelper`.

### Price and Product
These classes are simply container classes. They enable easy serial and deserialization with JSON.

## Running the project on Heroku
1. In project directory, run `heroku create`
2. Set up MongoDb sandbox instance through mongolab: `heroku addons:create mongolab`
* The database URI is saved as a Heroku config variable: `heroku config:get MONGODB_URI`
3. Deploy code with `git push heroku master`
4. The application is currently running at https://frozen-citadel-78149.herokuapp.com/products/{id}

## Running the project locally
1. Set your system environment variable `MONGODB_URI` to the correct location, or change all instances in code of this URI to point to your local database.
2. With Maven installed, run `mvn spring-boot:run`. The application will be running at `localhost:8080`.
