# Hospital Management System

A backend REST API developed using Spring Boot to manage hospital operations, including doctors, patients, appointments, medicines, and treatment history. It implements session-based authentication with role-based access control for Admins, Doctors, and Patients.

## Features

- **Doctor Management** – View patients, complete appointments, prescribe medicines.
- **Admin Dashboard** – Manage users, roles, doctors, and medicines.
- **User Registration & Login** – Role-based session authentication using Spring Security.
- **Appointment Booking** – Patients can book appointments with available doctors.
- **Medicine Management** – Admin can perform CRUD operations on medicines, which are linked to prescriptions.
- **Treatment History Tracking** – Doctors and patients can view treatment history. Updates are not allowed once submitted.

## Tech Stack

- **Backend:** Spring Boot 3.5.0, Spring Security, Spring Data JPA, Hibernate
- **Database:** MySQL
- **Build Tool:** Maven
- **Java Version:** JDK 21

## Database Schema

Key entities include:

- `User` – Handles authentication and roles (`ADMIN`, `DOCTOR`, `PATIENT`)
- `Doctor` – Includes details like name, specialization, experience
- `Patient` – Includes personal and contact details
- `Appointment` – Manages scheduling between doctors and patients
- `Medicine` – Contains medicine details managed by admin
- `TreatmentHistory` – Logs diagnosis, medicines, and treatment notes; view-only after submission

## Roles and Permissions

| Role    | Permissions                                                  |
|---------|--------------------------------------------------------------|
| ADMIN   | Manage users, assign roles, add/edit doctors and medicines   |
| DOCTOR  | View patients, complete appointments, add treatment history  |
| PATIENT | Register, book appointments, view prescriptions & history    |

