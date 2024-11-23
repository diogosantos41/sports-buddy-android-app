# Sports Buddy Android App

Sports Buddy is an android app that offers real-time event countdowns for sports events. It uses an **MVI Clean Architecture** structure to ensure a readable, scalable, and maintainable application. For such a simple app, I definitely went overkill with the project structure and utility functions, but I wanted to replicate what I would do if this was actually a project that would grow into something relatively big.

## Features

- **Real-time Event Countdown**: Track the remaining time of live sports events.
- **Favorite Events**: Mark your events as favorite, and they are saved persistently.
- **Error Handling**: If there are no events, or if there's no internet connection, all errors are accounted for.
- **Animations**: I implemented animations for every icon and content size change to create an awesome UX.
- **Custom Icons**: Each sport has its own custom icon.
- **Custom Serialization**: Due to the API response format, I was forced to create custom serialization.

## API

- I'm using a free mock API to fetch all sports: [https://618d3aa7fe09aa001744060a.mockapi.io/api/sports](https://618d3aa7fe09aa001744060a.mockapi.io/api/sports)

## Project Structure

The project is separated into two main packages:

- **core**: This is where all the common code, layouts, and utility functions are stored.
- **sport**: This is where all the code related to the sport events screen is implemented.

Each package is separated into three main layers:

- **presentation**: This is where all the components, ViewModel, State, and utility functions related to the UI are stored.
- **domain**: Domain acts as the middleman between the presentation and data layers. This is where the app defines what it should do, without any dependencies on the other layers.
- **data**: This is where all the data-related code is stored, such as Ktor, data store, HTTP client functions, and more.

The main idea of this three-layer structure is to create a scalable, maintainable, and testable application. This is because the presentation only "knows" about the domain, and the data layer also only "knows" about the domain. They have no dependencies on each other. The awesome part is that we can easily change the data implementation (for example, from Ktor to Retrofit or from Data Store to a Room solution) and we wouldn't have to change a single thing in the presentation layer. Also, we can easily change the data layer with a fake repository for testing since the domain doesn’t know how data is fetched, only what it should do and return.

## Tech Stack

- **UI**: Jetpack Compose
- **Flows and Coroutines**: To handle the API calls and observe the favorite events.
- **Data Store**: For storing the favorite event IDs.
- **Dagger2/Hilt**: For a simple dependency injection implementation
- **Ktor**: For remote API calls.

## Issues

- **SDK 24 Emulators**: I'm getting an `SSLHandshakeException` when calling the API in emulators with SDK 24 or below. I'm not sure if it only happens in emulators since I don't have an older physical device to test on.


 
