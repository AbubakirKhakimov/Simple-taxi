# <img src="https://github.com/AbubakirKhakimov/Simple-taxi/blob/master/app/src/main/res/drawable/app_icon.png" alt="App Icon" width="50" height="50"> Simple Taxi

Simple Taxi App is a mobile application designed to provide a seamless taxi booking experience. Built using modern Android development tools and frameworks, this app leverages **Mapbox** for map and navigation features, **Jetpack Compose** for a responsive and dynamic UI, and follows the **Clean Architecture** pattern along with **Model-View-Intent (MVI)** for robust and maintainable code.

## Features

- **Map Integration:** Utilizes Mapbox for displaying and interacting with maps.
- **Dynamic UI:** Built with Jetpack Compose, offering a fluid and responsive user interface.
- **Dark/Light Mode Support:** Adapts the map appearance based on the system's theme.
- **Clean Architecture:** Ensures separation of concerns, making the app easy to maintain and extend.
- **MVI Architecture:** Employs the MVI pattern for managing UI state predictably and efficiently.
- **Local Database:** Uses Room for local data storage and management.

## Screenshots

<div style="display: flex; justify-content: space-between;">
  <img src="https://github.com/AbubakirKhakimov/Simple-taxi/blob/master/app/src/main/res/drawable/light_1.jpg" alt="Screenshot 1" width="200" height="auto" style="margin-right: 10px;">
  <img src="https://github.com/AbubakirKhakimov/Simple-taxi/blob/master/app/src/main/res/drawable/light_2.jpg" alt="Screenshot 2" width="200" height="auto" style="margin-right: 10px;">
  <img src="https://github.com/AbubakirKhakimov/Simple-taxi/blob/master/app/src/main/res/drawable/night_1.jpg" alt="Screenshot 3" width="200" height="auto" style="margin-right: 10px;">
  <img src="https://github.com/AbubakirKhakimov/Simple-taxi/blob/master/app/src/main/res/drawable/night_2.jpg" alt="Screenshot 4" width="200" height="auto">
</div>

## Installation

1. Clone the repository:

   ```sh
   git clone https://github.com/your_username/simple-taxi-app.git
   ```

2. Open the project in Android Studio.

3. Add your Mapbox access token to the `local.properties` file:

   ```
   MAPBOX_ACCESS_TOKEN=your_mapbox_access_token
   ```

4. Build and run the project on an Android device or emulator.

## Architecture

### Clean Architecture
This project follows the principles of Clean Architecture, which ensures a clear separation of concerns. The project is divided into several layers:

- **Domain Layer:** Contains the business logic and use cases.
- **Data Layer:** Handles data operations, including network and database interactions.
- **Presentation Layer:** Manages the UI and user interaction using Jetpack Compose and MVI.

### MVI (Model-View-Intent)
The app follows the MVI pattern, which is ideal for managing complex UI states and interactions in a predictable manner. The core components include:

- **Model:** Represents the state of the UI.
- **View:** Displays the state and reacts to user actions.
- **Intent:** Represents user actions or events that modify the state.

## Dependencies

- **Mapbox SDK**: For map and navigation features.
- **Jetpack Compose**: For building the user interface.
- **Kotlin Coroutines**: For asynchronous programming.
- **Hilt**: For dependency injection.
- **Room**: For local data storage and management.


