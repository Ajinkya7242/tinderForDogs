# Tinder for Dogs

Tinder for Dogs is an Android application where users can swipe right or left on images of dogs, similar to the Tinder app. Users can like dogs, view their favorite dogs, and store data locally using a Room database.

# App Demo

https://github.com/user-attachments/assets/bbaa5a56-fb78-45d2-adc0-34cbd2e89157


## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Setup Instructions](#setup-instructions)
  - [Prerequisites](#prerequisites)
  - [Clone the Repository](#clone-the-repository)
  - [Set Up Firebase](#set-up-firebase)
  - [Build and Run the Project](#build-and-run-the-project)
- [License](#license)
- [Contributing](#contributing)
- [Acknowledgments](#acknowledgments)

## Features

- Swipe right or left on dog images to like or dislike.
- View a list of liked dogs.
- Save liked dogs to a local Room database.
- Smooth and responsive UI using CardStackView.
- Edge-to-edge UI with custom status bar management.
- swipe right in favorites to delete dog pic

## Technologies Used

- **Kotlin**: Primary programming language.
- **Hilt-Dagger**: Dependency Injection.
- **Room**: Local database for storing liked dogs.
- **Retrofit**: API client for fetching dog images.
- **Coroutines**: For managing background tasks.
- **Glide**: Image loading and caching.
- **Firebase Authentication**: User authentication.
- **Jetpack Components**: ViewModel, LiveData, etc.
- **CardStackView**: For swipeable cards.
- **Timber**: Logging utility.
- **Lottie**: For animations.
- **Material Components**: UI components.

## Setup Instructions

### Prerequisites

- Android Studio (Android Studio Koala | 2024.1.1)
- A Firebase account with a project set up
- Java version java 11.0.23

### Clone the Repository

git clone https://github.com/Ajinkya7242/tinderForDogs.git
