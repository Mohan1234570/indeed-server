# 🧰 Job Posting Backend API

This is a Spring Boot-based RESTful API for managing job posts. It allows creating, retrieving, updating, deleting, and searching job postings.

---

## 🚀 Tech Stack

- Java 17+
- Spring Boot
- Spring Data JPA
- MongoDB
- Maven
- CORS enabled for frontend integration

---

## 📦 Features

- Create a new job post
- Fetch all job posts
- Search job posts by job title (autocomplete support)
- Update and delete job posts
- JSON-based REST API
- Deployed locally on `http://localhost:8080`

---

## 📁 Folder Structure

src/ ├── main/ │ ├── java/ │ │ └── com.example.jobpostings/ │ │ ├── controller/ │ │ ├── model/ │ │ ├── repository/ │ │ ├── service/ │ │ └── JobPostingsApplication.java │ └── resources/ │ ├── application.properties │ └── data.sql (optional seed data)


Method | Endpoint | Description
GET | /posts | Fetch all job posts
GET | /posts/search?query= | Search posts by job title
POST | /posts | Create a new job post
PUT | /posts/{id} | Update a job post by ID
DELETE | /posts/{id} | Delete a job post by ID
