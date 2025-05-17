
# User Management API

This REST API provides a set of endpoints to manage users, including searching for users and managing their associated phone numbers and emails.

---

## Base URL

```
/api/v1/users
```

---

## Endpoints

### Search Users

**`GET /search`**

Searches users using the given criteria. At least one parameter is required.

#### Query Parameters

| Name         | Type   | Description                                      |
|--------------|--------|--------------------------------------------------|
| `dateOfBirth`| string | Filters users born **after** the provided date (format: `yyyy-MM-dd`) |
| `phone`      | string | 100% match on phone number                       |
| `name`       | string | Partial match on name                            |
| `email`      | string | 100% match on email address                      |
| `page`       | int    | Page number (starts from 1)                      |
| `size`       | int    | Number of results per page                       |

#### Response

```json
{
  "content": [
    {
      "id": 1,
      "name": "Andrei Pronsky",
      "dateOfBirth": "1990-01-01"
    }
  ],
  "pageNumber": 1,
  "pageSize": 10,
  "totalElements": 1,
  "totalPages": 1
}
```

---

### Get User Phone Numbers

**`GET /phones/{userId}`**

Returns all phone numbers associated with the given user ID.

#### Response

```json
[
  {
    "id": 1,
    "phoneNumber": "79211234567"
  }
]
```

---

### Get User Emails

**`GET /emails/{userId}`**

Returns all emails associated with the given user ID.

#### Response

```json
[
  {
    "id": 1,
    "email": "andrei@example.com"
  }
]
```

---

### Add Phone to User

**`POST /add-phone`**

Adds a phone number to a user.

#### Request Body

```json
{
  "userId": 1,
  "phoneNumber": "79211234567"
}
```

#### Response

- `201 Created`

---

### Add Email to User

**`POST /add-email`**

Adds an email to a user.

#### Request Body

```json
{
  "userId": 1,
  "email": "andrei@example.com"
}
```

#### Response

- `201 Created`

---

### Update Email

**`PATCH /edit-email`**

Updates a specific email for a user.

#### Request Body

```json
{
  "userId": 1,
  "emailId": 2,
  "newEmail": "new@example.com"
}
```

#### Response

- `202 Accepted`

---

### Update Phone

**`PATCH /edit-phone`**

Updates a specific phone number for a user.

#### Request Body

```json
{
  "userId": 1,
  "phoneId": 3,
  "newPhoneNumber": "79211234567"
}
```

#### Response

- `202 Accepted`

---

### Delete Phone

**`DELETE /{userId}/delete-phone/{phoneId}`**

Deletes a phone number for the given user.

#### Response

- `204 No Content`

---

### Delete Email

**`DELETE /{userId}/delete-email/{emailId}`**

Deletes an email for the given user.

#### Response

- `204 No Content`

---

## Tech Stack

- Java
- Spring Boot
- Spring Security
- Hibernate
- Lombok
- Mapstruct
- PostgreSQL
- OpenAPI (Swagger)
- Validation (JSR-380)

---

## Response and Error Models

### `ExceptionResponseDto`

```json
{
  "message": "Description of the error",
  "errorCode": "ERROR_CODE",
  "timestamp": "2024-01-01T12:00:00Z"
}
```

---

## Notes

- All endpoints are secured and may return `403 Forbidden` if the caller lacks permissions.
- All data validations are enforced using JSR-380 (`@Valid`).
- Ensure correct userId/emailId/phoneId is provided to avoid `404 Not Found`.

---

## Getting Started

### Prerequisites

- Java 17+
- Maven 3.8+
- Docker & Docker Compose

### Run Locally

```bash
git clone https://github.com/your-org/user-subscription-service.git
cd user-subscription-service
mvn spring-boot:run
```

### Run with docker
docker-compose up --build

### Swagger UI would be available at
http://localhost:8080/swagger-ui.html
