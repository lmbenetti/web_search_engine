# Web Search Engine

This is the final project for the course Introductory Programming at IT University Copenhagen. We developed a web-based search engine with Java. This README file provides an overview of the project, including its features, the techniques used and how to get started.

## 🔎 Project Overview
This project was a collaboration between 4 students, involved creating a functional web-based search engine in Java base on an inverted index.

Our search engine takes a filename as an argument, which represents a database of web pages. This "flat" file contains entries for each web page, including its URL, title, and the words on the page. We implemented a series of tasks to build upon a basic prototype, culminating in a robust search engine with advanced query capabilities and ranking algorithms, using an inverted index.

## 🛠️ Tools and Techniques
The project emphasized the practical application of several programming tools and techniques covered in class, with the goal of developing good software design principles.

- **Version Control:** We used Git and ITU's GitHub for collaborative development, with all group members contributing code.
- **Unit Testing:** JUnit was used for unit testing to ensure the code's correctness. We used  gradlew test jacocoTestReport to generate coverage reports and refactor the code safely.
- **Debugging:** Debugging was done with IDE support.
- **Web Services:** The search engine is implemented as a simple web service that runs on port 8080 and can be accessed at http://localhost:8080. The user interface is a provided HTML/CSS/JavaScript client that communicates with our Java server.
- **Code Documentation:** Javadoc was used to document all classes and public methods. We verified our documentation using gradlew javadoc.
- **Build Tools:** We used Gradle to build, run, and test the project.

## 🕹️ How to Run

**Prerequisites**
- Java Development Kit (JDK) 17 or newer
- Gradle 8.10.2.

1. **Clone the repo** 
2. **Run:**
    ```bash
    ../gradlew run
    #This command compiles the project and starts the web server.
3. **Access the search engine:** Open a web browser and go to http://localhost:8080 to interact with the search engine.






