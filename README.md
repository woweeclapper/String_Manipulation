# String Manipulation API

A modern Spring Boot REST API for string and array manipulation operations, refactored from a legacy Java 14 console application.

## Project Overview

This project was originally a Java 14 console application built in NetBeans IDE that provided basic string and array manipulation through user input. It asked the user to input a string or elements based on the service they want. Reverse and Shift ask for string as input as well as additional parameter for shifted. Sort and Sum ask for an array of elements after verifying the length the user want and each element must be inputted. Small amount of input validations was implemented, but it was merely a final project for a class.

The project has been completely refactored into a modern REST API using Spring Boot 4.0.2 and Java 21.

### Architecture

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Frontend      │    │   Backend API   │    │   Services      │
│   (React +      │◄──►│   (Spring Boot) │◄──►│   (Business     │
│   Vite)         │    │   REST API)     │    │    Logic)       │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                              │
                              ▼
                       ┌─────────────────┐
                       │   Validation    │
                       │   & Error       │
                       │   Handling      │
                       └─────────────────┘
```

## API Endpoints

### Array Operations

#### POST `/api/array/sum`
Calculates the sum of numbers in an array.

**Request DTO:** `SumRequest`
```json
{
  "numbersList": [1, 2, 3, 4.5]
}
```

**Response DTO:** `SumResponse`
```json
{
  "sum": 10.5
}
```

#### POST `/api/array/sort`
Sorts an array of numbers in ascending or descending order.

**Request DTO:** `SortRequest`
```json
{
  "numbersList": [3, 1, 4, 1, 5],
  "orderType": "ASCENDING" // or "DESCENDING"
}
```

**Response DTO:** `IntSortResponse` or `DoubleSortResponse`
```json
{
  "sortedList": [1, 1, 3, 4, 5],
  "orderType": "ASCENDING"
}
```

#### POST `/api/array/separate`
Separates numbers based on parity (even/odd) or sign (positive/negative).

**Request DTO:** `SeparationRequest`
```json
{
  "numberList": [1, 2, -3, 4, -5],
  "separationType": "PARITY" // or "SIGN"
}
```

**Response DTO:** `IntSepResponses` or `DoubleSepResponse`
```json
{
  "firstList": [2, 4],
  "secondList": [1, -3, -5],
  "separationType": "PARITY"
}
```

### String Operations

#### POST `/api/string/reverse`
Reverses the input string.

**Request DTO:** `ReverseRequest`
```json
{
  "text": "Hello World"
}
```

**Response DTO:** `ReverseResponse`
```json
{
  "reversedText": "dlroW olleH"
}
```

#### POST `/api/string/shift`
Shifts characters in a string left or right by specified positions.

**Request DTO:** `ShiftRequest`
```json
{
  "text": "Hello",
  "numOfShifts": 2,
  "direction": "LEFT" // or "RIGHT", "L", "R"
}
```

**Response DTO:** `ShiftResponse`
```json
{
  "shiftedText": "lloHe",
  "numOfShifts": 2,
  "direction": "LEFT"
}
```

## Request DTOs

### Array DTOs
- **`SumRequest`**: List of numbers (integers/doubles) to sum
- **`SortRequest`**: List of numbers + sort order (ASCENDING/DESCENDING)
- **`SeparationRequest`**: List of numbers + separation type (PARITY/SIGN)

### String DTOs
- **`ReverseRequest`**: Text string to reverse
- **`ShiftRequest`**: Text string + number of shifts + direction (LEFT/RIGHT)

## Response DTOs

### Array Responses
- **`SumResponse`**: Contains the calculated sum (double)
- **`IntSortResponse`**: Sorted integer list + order type
- **`DoubleSortResponse`**: Sorted double list + order type
- **`IntSepResponses`**: Two integer lists + separation type
- **`DoubleSepResponse`**: Two double lists + separation type

### String Responses
- **`ReverseResponse`**: Reversed text string
- **`ShiftResponse`**: Shifted text + shift count + direction

## Error Response Format

All errors return a standardized `ErrorResponse` format:

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "details": [
    "Text cannot be blank",
    "Number of shifts cannot be negative"
  ],
  "timestamp": "2024-01-15T10:30:00"
}
```

## Example Requests

### Successful Requests

**Sum Array:**
```bash
curl -X POST http://localhost:8080/api/array/sum \
  -H "Content-Type: application/json" \
  -d '{"numbersList": [1, 2, 3, 4]}'
```

**Reverse String:**
```bash
curl -X POST http://localhost:8080/api/string/reverse \
  -H "Content-Type: application/json" \
  -d '{"text": "Hello World"}'
```

**Sort Array:**
```bash
curl -X POST http://localhost:8080/api/array/sort \
  -H "Content-Type: application/json" \
  -d '{"numbersList": [3, 1, 4, 1, 5], "orderType": "ASCENDING"}'
```

### Example Errors

**Validation Error (400):**
```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "details": ["Numbers list cannot be empty"],
  "timestamp": "2024-01-15T10:30:00"
}
```

**Invalid Direction (400):**
```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "details": ["Direction must represent 'left', 'right', 'l', or 'r'."],
  "timestamp": "2024-01-15T10:30:00"
}
```

## Normalization Rules

### Input Validation
- **String text**: Cannot be blank, max 10,000 characters
- **Number arrays**: 1-1000 elements, no null values
- **Shift count**: Must be non-negative integer
- **Direction**: Case-insensitive "LEFT"/"RIGHT" or "L"/"R"
- **Order type**: "ASCENDING" or "DESCENDING"
- **Separation type**: "PARITY" or "SIGN"

### Type Handling
- Arrays automatically detect integer vs double types
- Integer arrays return integer-specific response DTOs
- Double arrays return double-specific response DTOs
- Mixed type arrays are treated as doubles

### String Normalization
- Direction strings are trimmed and case-insensitive
- Whitespace in direction patterns is ignored
- Text is processed exactly as provided (no automatic trimming)

## Technology Stack

### Backend
- **Framework**: Spring Boot 4.0.2
- **Java Version**: 21
- **Build Tool**: Maven
- **Validation**: Jakarta Bean Validation
- **JSON Processing**: Jackson
- **Testing**: JUnit 5, AssertJ, Mockito
- **Code Coverage**: JaCoCo

### Frontend
- **Framework**: React 19.2.0
- **Build Tool**: Vite 7.3.1
- **Language**: JavaScript
- **Linting**: ESLint
- **Type Checking**: TypeScript (for React types)

## Goals and Future Development

- [x] Update from Java 14 to Java 21
- [x] Refactoring current project into a functional Backend
- [x] Frontend designed in React + Vite which will also double as a portfolio website showcasing other works
- [x] Spring Boot will be implemented into Backend once refactoring is done
- [ ] Dockerized the production-ready backend for development

## Hosting

- **Frontend**: GitHub Pages
- **Backend**: Render (Docker is needed for Render hosting since it does not support Java)

## Getting Started

### Prerequisites
- Java 21
- Maven 3.6+
- Node.js 18+ (for frontend)
- npm or yarn

### Running the Backend
```bash
cd server
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`

### Running the Frontend
```bash
cd client
npm install
npm run dev
```

The React application will be available at `http://localhost:5173`

### Running Tests
**Backend Tests:**
```bash
cd server
mvn test
```

**Frontend Tests:**
```bash
cd client
npm run lint
```

### Code Coverage Report
```bash
cd server
mvn jacoco:report
```
View the report at `server/target/site/jacoco/index.html`

### Building for Production
**Backend:**
```bash
cd server
mvn clean package
```

**Frontend:**
```bash
cd client
npm run build
```
The built files will be in the `client/dist` directory.
  
