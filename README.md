# Trello API Automation Framework

A comprehensive Java-based automation framework for testing Trello REST API endpoints. This project provides a robust, maintainable solution for automated testing of Trello's board, list, and card management functionalities.

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Setup](#setup)
- [Configuration](#configuration)
- [Running Tests](#running-tests)
- [Test Scenarios](#test-scenarios)
- [API Coverage](#api-coverage)
- [Contributing](#contributing)
- [License](#license)

## 🎯 Overview

This automation framework is designed to test Trello's REST API functionality through end-to-end test scenarios. It covers the complete workflow of creating boards, lists, and cards, along with update and deletion operations.

### Key Benefits
- **Modular Design**: Clean separation of concerns with dedicated client classes
- **Maintainable**: Object-oriented approach with reusable components
- **Comprehensive Logging**: Detailed logging for debugging and monitoring
- **Dynamic Test Data**: Automated test data generation with timestamps
- **Error Handling**: Robust error handling and reporting

## ✨ Features

- ✅ **Board Management**: Create and delete Trello boards
- ✅ **List Management**: Create lists within boards
- ✅ **Card Management**: Create, update, and delete cards within lists
- ✅ **Bulk Operations**: Delete all cards in a list
- ✅ **Dynamic Test Data**: Generate unique test data with timestamps
- ✅ **Comprehensive Logging**: Detailed operation logging with Log4j2
- ✅ **Configuration Management**: Externalized API credentials
- ✅ **Ordered Test Execution**: Sequential test execution with JUnit 5

## 🛠️ Technology Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| **Java** | 17 | Programming language |
| **Maven** | 3.x | Build tool and dependency management |
| **REST Assured** | 5.5.0 | API testing framework |
| **JUnit 5** | 5.13.1 | Testing framework |
| **Lombok** | 1.18.36 | Reduce boilerplate code |
| **Log4j2** | 2.20.0 | Logging framework |
| **Jackson** | 2.17.0 | JSON processing |
| **JavaFaker** | 1.0.2 | Test data generation |

## 📁 Project Structure

```
Trello-API-Automation/
├── src/
│   ├── main/
│   │   └── java/
│   │       ├── client/                 # API Client Classes
│   │       │   ├── BoardClient.java    # Board operations
│   │       │   ├── CardClient.java     # Card operations
│   │       │   └── CardListClient.java # List operations
│   │       ├── constants/
│   │       │   └── Endpoints.java      # API endpoints
│   │       ├── model/                  # Data Models
│   │       │   ├── Board.java
│   │       │   ├── Card.java
│   │       │   └── CardList.java
│   │       └── utils/                  # Utility Classes
│   │           ├── APIBase.java        # Base API client
│   │           ├── ConfigUtil.java     # Configuration management
│   │           └── TestDataGenerator.java # Test data generation
│   └── test/
│       ├── java/
│       │   └── test/
│       │       └── BoardTest.java      # Main test class
│       └── resources/
│           └── log4j2.xml             # Logging configuration
├── pom.xml                            # Maven configuration
└── README.md                          # Project documentation
```

## 📋 Prerequisites

Before running this project, ensure you have:

1. **Java Development Kit (JDK) 17** or higher
2. **Apache Maven 3.6+**
3. **Trello Account** with API access
4. **Trello API Key and Token**

## 🚀 Setup

### 1. Clone the Repository
```bash
git clone https://github.com/ezginacar/trello-api-automation.git
cd Trello-API-Automation
```

### 2. Install Dependencies
```bash
mvn clean install
```

### 3. Get Trello API Credentials

1. **Get API Key**: Visit [Trello Developer Portal](https://trello.com/app-key)
2. **Generate Token**: Click on the "Token" link on the same page
3. **Save Credentials**: Keep your API key and token secure

## ⚙️ Configuration

### Create Configuration File

Create a `config.properties` file in `src/test/resources/`:

```properties
# Trello API Configuration
baseUrl=https://api.trello.com/1
apiKey=your_trello_api_key_here
apiToken=your_trello_api_token_here
dateTimeFormat=yyyy-MM-dd-HH-mm-ss
```

### Configuration Parameters

| Parameter | Description | Example |
|-----------|-------------|---------|
| `baseUrl` | Trello API base URL | `https://api.trello.com/1` |
| `apiKey` | Your Trello API key | `your_api_key_here` |
| `apiToken` | Your Trello API token | `your_api_token_here` |
| `dateTimeFormat` | Format for timestamp generation | `yyyyMMdd-HHmmss` |

## 🏃‍♂️ Running Tests

### Run All Tests
```bash
mvn test
```

### Run Specific Test Class
```bash
mvn test -Dtest=BoardTest
```

### Run with Maven Profiles
```bash
mvn test -Pintegration
```

### Run with Custom Configuration
```bash
mvn test -Dconfig.path=/path/to/your/config.properties
```

## 🧪 Test Scenarios

The framework includes a comprehensive end-to-end test flow:

### Test Execution Order

1. **📋 Create Board** (`createBoard`)
   - Creates a new Trello board with timestamp-based name
   - Validates board creation success

2. **📝 Create List** (`createANewListOnTheBoard`)
   - Creates a list within the previously created board
   - Validates list creation and board association

3. **🗂️ Create Cards** (`createTwoNewCardsUnderTheList`)
   - Creates two cards within the list
   - Adds cards to local collection for tracking

4. **✏️ Update Card** (`updateCardNameUnderTheList`)
   - Randomly selects a card from the list
   - Updates the card name with new timestamp-based name

5. **🗑️ Delete Cards** (`deleteAllCardsInTheBoard`)
   - Deletes all cards from the list
   - Clears local card collection

6. **🗑️ Delete Board** (`deleteTheBoard`)
   - Deletes the entire board
   - Cleans up all created resources

### Sample Test Output
```
[INFO] Running test.BoardTest
[INFO] Board created: Ezgi Test Board-2024-01-15-14-30-45
[INFO] List created: Ezgi Test List-2024-01-15-14-30-46
[INFO] Card created: Card{id='card123', name='Ezgi Test Card1', ...}
[INFO] Card created: Card{id='card124', name='Ezgi Test Card2', ...}
[INFO] 🆕 The card updated with new params: Card{id='card123', name='Ezgi Updated Card-2024-01-15-14-30-50', ...}
[INFO] Card with ID card123 deleted successfully.
[INFO] Card with ID card124 deleted successfully.
[INFO] 🗑️ All cards under list 'Ezgi Test List-2024-01-15-14-30-46' deleted successfully.
[INFO] 🗑️ Board 'Ezgi Test Board-2024-01-15-14-30-45' deleted successfully.
```

## 🌐 API Coverage

### Supported Endpoints

| HTTP Method | Endpoint | Purpose | Implementation |
|-------------|----------|---------|----------------|
| `POST` | `/boards` | Create board | `BoardClient.createBoard()` |
| `DELETE` | `/boards/{id}` | Delete board | `BoardClient.deleteBoard()` |
| `POST` | `/lists` | Create list | `CardListClient.createList()` |
| `POST` | `/cards` | Create card | `CardClient.createCard()` |
| `PUT` | `/cards/{id}` | Update card | `CardClient.updateCard()` |
| `DELETE` | `/cards/{id}` | Delete card | `CardClient.deleteCard()` |

### Request/Response Handling

- **Automatic JSON Serialization**: Jackson handles object mapping
- **Request Specification**: REST Assured manages HTTP requests
- **Response Parsing**: Automatic conversion to model objects
- **Error Handling**: HTTP status code validation and error logging

## 🔧 Customization

### Adding New Test Scenarios

1. **Create new test method** in `BoardTest.java`:
```java
@Test
@Order(7)
@DisplayName("Your test description")
void yourTestMethod() {
    // Your test implementation
}
```

2. **Add new API endpoints** in `Endpoints.java`:
```java
public static final String YOUR_ENDPOINT = "/your-endpoint";
```

3. **Extend client classes** with new methods:
```java
public YourModel yourOperation(String param) {
    // Implementation
}
```

### Custom Test Data

Modify `TestDataGenerator.java` to add custom data generation:

```java
public String generateCustomData() {
    return faker.lorem().word() + getTimestamp();
}
```

## 📊 Logging

The framework uses Log4j2 for comprehensive logging:

### Log Levels
- **INFO**: General operation information
- **DEBUG**: Detailed debugging information
- **WARN**: Warning messages
- **ERROR**: Error conditions

### Log Output Example
```
14:30:45 [main] INFO  client.BoardClient - Board created: Ezgi Test Board-2024-01-15-14-30-45
14:30:46 [main] INFO  client.CardListClient - List created: Ezgi Test List-2024-01-15-14-30-46
14:30:47 [main] INFO  client.CardClient - Card created: Card{id='card123', name='Ezgi Test Card1'}
```

## 🤝 Contributing

1. **Fork the repository**
2. **Create a feature branch**: `git checkout -b feature/your-feature`
3. **Make your changes** and add tests
4. **Run tests**: `mvn test`
5. **Commit changes**: `git commit -am 'Add your feature'`
6. **Push to branch**: `git push origin feature/your-feature`
7. **Create Pull Request**

### Coding Standards
- Follow Java naming conventions
- Add JavaDoc comments for public methods
- Include unit tests for new functionality
- Maintain consistent logging patterns

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🔗 Useful Links

- [Trello API Documentation](https://developer.atlassian.com/cloud/trello/rest/)
- [REST Assured Documentation](https://rest-assured.io/)
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Maven Documentation](https://maven.apache.org/guides/)

## 📞 Support

For questions or issues:
1. Check the [Issues](../../issues) section
2. Review [Trello API Documentation](https://developer.atlassian.com/cloud/trello/rest/)
3. Create a new issue with detailed description

---

**Happy Testing! 🚀** 