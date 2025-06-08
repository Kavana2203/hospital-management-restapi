# 🏥 Hospital Management System

A role-based, session-authenticated hospital management system built with **Spring Boot 3.5.0**, **Java 21**, and **MySQL 8**. It facilitates management of appointments, doctors, patients, medicines, and treatment history.

---

## 🚀 Features

- 🧑‍⚕️ **Doctor Management** – View patients, complete appointments, prescribe medicines.
- 👨‍💼 **Admin Dashboard** – Manage users, roles, doctors, medicines.
- 🧑‍💻 **User Registration & Login** – Role-based session authentication using Spring Security.
- 📅 **Appointment Booking** – Patients can book appointments with available doctors.
- 💊 **Medicine Management** – CRUD operations for medicines, linked to prescriptions.
- 📜 **Treatment History Tracking** – Doctors can update treatments and prescribed medicines.

---

## 🧰 Tech Stack

| Technology             | Description                                  |
|------------------------|----------------------------------------------|
| **Java 21**            | Latest long-term support version             |
| **Spring Boot 3.5.0**  | Backend application framework                |
| **Spring Security 6**  | Modern authentication and authorization      |
| **Spring Data JPA**    | ORM with Hibernate                           |
| **MySQL 8**            | Relational database                          |
| **Maven**              | Build and dependency management              |

---

## 📦 Modules

### 👥 User & Role Management
- Session-based login/logout
- Support for multiple roles per user (Admin, Doctor, Patient)

### 📅 Appointment
- Book appointment with doctor
- View upcoming and past appointments
- Doctors can complete appointments only after scheduled time

### 💊 Medicine
- Admin can add/update/delete medicines
- Doctors can prescribe medicines from this list

### 📝 Treatment History
- Linked to completed appointments
- Records diagnosis and prescribed medicines

---

## 🗃️ Database Schema (Entities Overview)

- `User` – common table for login info
- `Role` – user roles (ADMIN, DOCTOR, PATIENT)
- `Doctor` – specialization, experience, etc.
- `Patient` – basic personal and contact info
- `Appointment` – date, doctor, patient, status
- `Medicine` – name, manufacturer, price
- `TreatmentHistory` – appointment, notes, prescriptions
- `PrescribedMedicine` – junction table for prescriptions

---

## 🔐 Authentication & Authorization

- **Spring Security 6**
- **Session-based authentication**
- **Role-based access control** using `@PreAuthorize`, URL access rules, etc.
- `UserDetailsService` & `PasswordEncoder` for secure credential handling

---
