# Product Requirements Document (PRD): Nice Movie Mobile Application

## 1. Project Overview
* **Product Name:** Nice Movie
* **Project Type:** Mobile Application (Android / iOS)
* **Time Constraint / Time-box:** 4 hours development test
* **Objective:** Build a lightweight mobile application for browsing, searching, viewing details, and managing favorite movies using a public REST API.

---

## 2. Target Audience & Stakeholders
* **Developer Candidate:** Software Engineer taking a practical technical competency test.
* **Evaluator / Reviewer:** Technical Evaluator (`iam@andazlan.com`).

---

## 3. Core Features & Functional Requirements

| Feature ID | Feature Name | Description & Acceptance Criteria | Weight / Score |
| :--- | :--- | :--- | :--- |
| **F-01** | **Movie List View** | • Fetch and display a list of movies.<br>• Each item row must show the movie poster thumbnail, title, and release year.<br>• Clean UI layout mirroring the reference screenshot. | 20 points |
| **F-02** | **Movie Detail View** | • Display comprehensive information for a selected movie.<br>• Include: Full poster art, release date, runtime duration, IMDb rating, plot summary, genre, director, and actors list.<br>• Quick-action button to toggle/add the movie to favorites. | 20 points |
| **F-03** | **Live Search** | • Real-time search functionality as the user types queries (e.g., searching "batman").<br>• Instant filtering/fetching of matching movie results from the API with a clear/dismiss action. | 10 points |
| **F-04** | **Local Favorites Management** | • Ability to save/bookmark movies into local device storage.<br>• Dedicated "Favorite" screen listing all bookmarked movies for offline/quick access. | 30 points |
| **F-05** | **Bonus / Creative Features** | • Optional extra features implemented based on developer creativity (e.g., empty states, animations, caching). | *Bonus* |

---

## 4. Technical Architecture & Guidelines

* **External API:** [OMDb API](http://www.omdbapi.com/) (Open Movie Database REST API).
* **Design Pattern:** Recommended architecture includes **MVP (Model-View-Presenter)**, MVVM, or any structured pattern to ensure separation of concerns. (Using MVP yields a value-add score).
* **Suggested Technology Stack (Android Reference):**
  * **Networking:** Android-Networking / Retrofit / OkHttp for HTTP requests.
  * **Serialization/Deserialization:** Gson for JSON-to-POJO mapping.
  * **View Binding / Injection:** Butterknife or modern ViewBinding alternatives.
  * **Image Loading:** Picasso or Glide for caching and rendering poster images from URLs.
  * **Local Storage:** SQLite, Room, or SharedPreferences for storing favorite movies locally.

---

## 5. Deliverables & Submission Criteria

1. **Source Code Repository:** 
   * Host source code on GitHub.
   * Include a comprehensive `README.md` explaining the application architecture, libraries used, setup instructions, and app screenshots matching the reference UI.
2. **Compiled Binary:** 
   * Deliver the compiled application build artifact (`.apk` for Android).
3. **Submission Channel:** 
   * Email both the compiled `.apk` and the GitHub repository link to `iam@andazlan.com`.

---

## 6. Evaluation Criteria Weighting Summary

* **Movie List Feature:** 20%
* **Movie Detail Feature:** 20%
* **Live Search Feature:** 10%
* **Local Favorites Feature:** 30%
* **Design Pattern Implementation (e.g., MVP/MVVM):** 20%
* **Documentation & GitHub Presentation (README + Screenshots):** 10%
* **Submission Compliance (APK + Repo link sent correctly):** 10%
