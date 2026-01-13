# Appointment System

## Project Overview

The Appointment System is a robust web application designed to streamline the process of scheduling and managing appointments. Built with Spring Boot, it provides a comprehensive backend solution for various user roles, including administrators, staff, and customers. The system aims to prevent scheduling conflicts, manage services, and facilitate efficient communication through notifications. It incorporates modern security practices, such as JWT-based authentication, and leverages WebSocket for real-time updates. A notable feature is the integration with Google Gemini AI for intelligent appointment suggestions.

## Team Contributions

This project was developed collaboratively by the following team members, with each member taking primary responsibility for specific modules:

*   **حيدرة أكثم علي (Haidara Aktham Ali)**: Led the development of the **User** module, including its models, services, controllers, DTOs, repositories, and associated HTML files.
*   **بتول لؤي منصور (Batoul Loay Mansour)**: Responsible for the **Service** module, encompassing its models, services, controllers, DTOs, repositories, and corresponding HTML files.
*   **أحمد عماد يونس (Ahmed Emad Younis)**: Developed the **Schedule** module, including its models, services, controllers, DTOs, repositories, and related HTML files.
*   **نور ابراهيم الكفري (Noor Ibrahim Al-Kafri)**: Focused on the **Appointment** module, handling its models, services, controllers, DTOs, repositories, and dedicated HTML files.

All team members contributed to the overall project configuration, security setup, and other cross-cutting concerns to ensure a cohesive and functional application.

## Features

*   **User Management**: Supports multiple roles (Admin, Staff, Customer) with secure registration and login.
*   **Service Management**: Allows creation, updating, and deactivation of services offered.
*   **Appointment Scheduling**: Enables customers to book appointments, with validation for working hours and conflict prevention.
*   **Appointment Status Management**: Admins and staff can approve, cancel, or complete appointments.
*   **Real-time Notifications**: Utilizes WebSockets to send instant updates on appointment status changes.
*   **Security**: Implements Spring Security with JWT for authentication and authorization, and HTTPS for secure communication.
*   **Logging**: Centralized logging with Aspect-Oriented Programming (AOP) for better monitoring and error handling.
*   **AI-Powered Suggestions**: Integrates with Google Gemini AI to suggest optimal appointment slots.

## Technologies Used

*   **Backend**: Java 17, Spring Boot, Spring Web, Spring Data JPA
*   **Database**: H2 Database (in-memory for development/testing)
*   **Security**: Spring Security, JWT (JSON Web Tokens)
*   **Messaging**: Spring WebSocket
*   **Build Tool**: Maven
*   **Templating**: Thymeleaf
*   **AI Integration**: Google Gemini API
*   **Logging**: SLF4J with Logback (configured via AOP)

## API Endpoints

The API is structured around RESTful principles, providing clear and consistent access to resources. All API endpoints are secured and require appropriate authentication and authorization.

### 1. Authentication (`/api/auth`)

| Method | Endpoint | Description | Roles | Request Body | Response Body |
| :----- | :------- | :---------- | :---- | :----------- | :------------ |
| `POST` | `/login` | Authenticates a user and returns a JWT token. | All | `AuthRequest` (email, password) | `AuthResponse` (token, email, role, userId) |
| `POST` | `/register/staff` | Registers a new staff member. | Public | `StaffRegistrationRequest` (name, email, password, specialty, licenseNumber) | `StaffResponse` |
| `POST` | `/register/customer` | Registers a new customer. | Public | `CustomerRegistrationRequest` (name, email, password, phoneNumber, address) | `CustomerResponse` |

### 2. Appointments (`/api/appointments`)

| Method | Endpoint | Description | Roles | Request Body | Response Body |
| :----- | :------- | :---------- | :---- | :----------- | :------------ |
| `GET` | `/` | Retrieves all appointments (admin only). Supports pagination and filtering by status and date range. | ADMIN | (Query Params: `page`, `size`, `sort`, `status`, `from`, `to`) | `Page<AppointmentResponse>` |
| `GET` | `/{id}` | Retrieves a specific appointment by ID. | ADMIN | (Path Variable: `id`) | `AppointmentResponse` |
| `DELETE` | `/{id}` | Deletes an appointment by ID. | ADMIN | (Path Variable: `id`) | `ApiResponse<Void>` |
| `PUT` | `/{id}/status` | Updates the status of an appointment. | ADMIN | `UpdateAppointmentStatusRequestDTO` (status, reason) | `AppointmentResponse` |
| `POST` | `/` | Books a new appointment. | CUSTOMER | `AppointmentRequestDTO` (staffId, serviceId, appointmentDateTime, notes) | `AppointmentResponse` |
| `GET` | `/customer` | Retrieves all appointments for the current customer. | CUSTOMER | None | `List<AppointmentResponse>` |
| `PUT` | `/{id}/cancel` | Cancels an appointment. | CUSTOMER | (Path Variable: `id`, Query Param: `reason`) | `AppointmentResponse` |
| `GET` | `/staff` | Retrieves all appointments for the current staff member. | STAFF | None | `List<AppointmentResponse>` |
| `GET` | `/staff/pending` | Retrieves pending appointments for the current staff member. | STAFF | None | `List<AppointmentResponse>` |
| `GET` | `/available-slots` | Retrieves available time slots for a given staff, service, and date. | All | (Query Params: `staffId`, `serviceId`, `date`) | `List<AvailableSlotResponse>` |

### 3. Services (`/api/services`)

| Method | Endpoint | Description | Roles | Request Body | Response Body |
| :----- | :------- | :---------- | :---- | :----------- | :------------ |
| `POST` | `/` | Creates a new service. | ADMIN, STAFF | `ServiceRequestDTO` (name, description, duration, price, isActive) | `ServiceResponse` |
| `PUT` | `/{id}` | Updates an existing service. | ADMIN, STAFF | `ServiceRequestDTO` (name, description, duration, price, isActive) | `ServiceResponse` |
| `GET` | `/{id}` | Retrieves a service by ID. | All | (Path Variable: `id`) | `ServiceResponse` |
| `GET` | `/` | Retrieves all active services. Supports pagination. | All | (Query Params: `page`, `size`, `sort`) | `Page<ServiceResponse>` |
| `GET` | `/available` | Retrieves all available services. Supports pagination. | All | (Query Params: `page`, `size`, `sort`) | `Page<ServiceResponse>` |
| `GET` | `/provider/{providerId}` | Retrieves services offered by a specific provider. | All | (Path Variable: `providerId`) | `List<ServiceResponse>` |
| `DELETE` | `/{id}` | Deactivates a service by ID. | ADMIN | (Path Variable: `id`) | `ApiResponse<Void>` |

### 4. Schedules (`/api/schedule`)

| Method | Endpoint | Description | Roles | Request Body | Response Body |
| :----- | :------- | :---------- | :---- | :----------- | :------------ |
| `POST` | `/` | Creates a new working schedule. | ADMIN, STAFF | `WorkingScheduleRequestDTO` (staffId, dayOfWeek, startTime, endTime, isHoliday) | `WorkingScheduleResponse` |
| `PUT` | `/{id}` | Updates an existing working schedule. | ADMIN, STAFF | `WorkingScheduleRequestDTO` (staffId, dayOfWeek, startTime, endTime, isHoliday) | `WorkingScheduleResponse` |
| `GET` | `/staff/{staffId}` | Retrieves working schedules for a specific staff member. | All | (Path Variable: `staffId`) | `List<WorkingScheduleResponse>` |
| `GET` | `/available-slots` | Retrieves available time slots for a given staff, service, and date. | All | (Query Params: `staffId`, `serviceId`, `date`) | `List<AvailableSlotResponse>` |
| `DELETE` | `/{id}` | Deletes a working schedule by ID. | ADMIN | (Path Variable: `id`) | `ApiResponse<Void>` |

### 5. Users (`/api/users`)

| Method | Endpoint | Description | Roles | Request Body | Response Body |
| :----- | :------- | :---------- | :---- | :----------- | :------------ |
| `POST` | `/` | Creates a new user (admin can create any role). | ADMIN | `UserRequest` (name, email, password, role) | `UserResponse` |
| `GET` | `/{id}` | Retrieves a user by ID. | All | (Path Variable: `id`) | `UserResponse` |
| `GET` | `/profile` | Retrieves the profile of the currently authenticated user. | Authenticated | None | `UserResponse` |

### 6. AI Integration (`/api/ai`)

| Method | Endpoint | Description | Roles | Request Body | Response Body |
| :----- | :------- | :---------- | :---- | :----------- | :------------ |
| `GET` | `/suggest` | Suggests the best available appointment time using Google Gemini AI. | All | (Query Params: `staffId`, `serviceId`, `date`) | `String` (AI-generated suggestion) |

## Setup and Run

1.  **Clone the repository:**
    ```bash
    git clone <repository_url>
    cd AppointmentSystem
    ```
2.  **Build the project:**
    ```bash
    ./mvnw clean install
    ```
3.  **Run the application:**
    ```bash
    ./mvnw spring-boot:run
    ```
    The application will start on `https://localhost:8443` (due to HTTPS configuration) and `http://localhost:8080` (redirects to HTTPS).

## Usage

*   Access the application through your web browser at `https://localhost:8443`.
*   Register as a customer or staff member via the `/register` page or use the API endpoints.
*   Log in using your credentials.
*   Explore the dashboard based on your assigned role.
*   Admins can manage users, services, and schedules.
*   Staff can manage their schedules and view appointments.
*   Customers can book and manage their appointments.
*   Utilize the `/api/ai/suggest` endpoint to get AI-powered appointment suggestions.

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.
