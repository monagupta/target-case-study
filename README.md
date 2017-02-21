# Target Case Study - REST API

## Set Up Steps
1. Install Spring CLI. On Fedora, followed guide here to install manually:
`http://howtoprogram.xyz/2016/08/28/install-spring-boot-command-line-interface-on-linux/`

2. Initialize Spring project: `spring init --dependencies=web target-case-study`



## Running the project locally
With Maven installed, run `mvn spring-boot:run`. The application will be running at `localhost:8080`.

## Running the project on Heroku
1. In project directory, run `heroku create`
2. Deploy code with `git push heroku master`