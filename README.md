# Pokedex Android App

Welcome to the README file for the Pokedex Android app built with Kotlin. This app integrates an Image Classification Machine Learning model as a backend, hosted on AWS using a Flask API. The app serves as a comprehensive guide to Pokemon, featuring a delightful user interface with support for both dark and light modes.

![Pokedex App Screenshots](/path/to/app/screenshots.png)

## Table of Contents

- [About the Project](#about-the-project)
  - [Features](#features)
  - [Screenshots](#screenshots)
- [Tech Stack](#tech-stack)
  - [Backend](#backend)
  - [Frontend](#frontend)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

## About the Project

The Pokedex Android app is designed to provide users with an intuitive and captivating experience while exploring the world of Pokemon. It combines a state-of-the-art Machine Learning model with a user-friendly interface to offer features like Pokemon information browsing, image classification, and customization options.

### Features

- Browse and explore information about all Pokemon generations.
- Switch between dark and light themes for a comfortable experience.
- Use the camera or upload images to identify Generation 1 Pokemon.
- Collect all Generation 1 Pokemon by scanning them with the integrated feature.
- Enjoy smooth transitions and animations for an appealing interface.

### Screenshots

Here are some screenshots showcasing the app's user interface and features:

![Screenshot 1](/path/to/screenshots/screenshot1.png)
*Caption for Screenshot 1*

![Screenshot 2](/path/to/screenshots/screenshot2.png)
*Caption for Screenshot 2*

## Tech Stack

### Backend

- **Machine Learning Model:** Utilizing TensorFlow's Transfer Learning, incorporating EfficientNet and ResNet models for accurate image classification.
- **AWS Services:** Hosting the Machine Learning model on Amazon Web Services for scalability and reliability.
- **Flask API:** Serving as a bridge between the app and the Machine Learning model, processing image data and returning predicted Pokemon details.

### Frontend

- **Language:** Kotlin
- **Architecture:** MVVM (Model-View-ViewModel) pattern for clean separation of concerns.
- **Network Calls:** Retrofit library for efficient API calls and data loading.
- **UI Components:** 
  - RecyclerView: Smooth lists for displaying Pokemon information.
  - Glide: Seamless image loading and caching.
  - Material Components: Consistent UI elements and themes for an attractive look and feel.
- **Asynchronous Operations:** Coroutines for handling asynchronous tasks efficiently.

## Usage

- Explore Pokemon information across generations.
- Identify Generation 1 Pokemon using the integrated ML model.
- Collect all Generation 1 Pokemon by scanning them.
- Customize the app's theme through the profile page.

## License

This project is licensed under the [Apache-2.0 license](https://github.com/nirajk24/Pokedex/blob/main/LICENSE). See the [Apache-2.0 license](https://github.com/nirajk24/Pokedex/blob/main/LICENSE) file for details.

## Contact

Have questions or feedback? Feel free to reach out:

- LinkedIn: [Niraj](https://www.linkedin.com/in/nirajk24/)
