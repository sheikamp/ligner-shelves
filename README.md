
<h3 align="center">Ligner: Shelve Management</h3>

<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary><h2 style="display: inline-block">Table of Contents</h2></summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

The shelves service takes care of shelve creation, book addition and removal.
It also offers methods for testing, such as generating fake books to fill shelves.


### Built With

* Kotlin 1.4
* Spring Boot (WebFlux)
* GraphQL (Netflix DGS)
* Gradle 
* MongoDB
* RabbitMQ



<!-- GETTING STARTED -->
## Getting Started

To get a local copy up and running follow these simple steps.

### Prerequisites

The following tools are required for building the application:
* JDK 11: Possibly the easiest way to install Java on your system is using [SDK-MAN](https://sdkman.io/usage).
* Kotlin 1.4: This can also be installed using SDK-MAN.
* docker: Please check the [official installation instructions](https://docs.docker.com/get-docker/).

### Installation

1. Clone the repo
   ```sh
   git clone https://github.com/sheikamp/ligner-shelves
   ```
2. Build the docker image
   ```sh
   ./gradlew bootBuildImage
   ```


<!-- USAGE EXAMPLES -->
## Usage

The shelves service exposes a GraphQL API, the schema can be viewed under /graphiql.
It supports the usual CRUD operations plus some test support mutations like fake book generation.
