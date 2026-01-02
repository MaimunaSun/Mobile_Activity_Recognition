# FitBox – Mobile Fitness Tracking Application

## Overview
This repository contains the **mobile fitness tracking application** that leverages **on-device activity recognition** to log and visualize users' daily activities.  
The app is designed for **Android smartphones** and integrates the trained machine learning model (`genesis.ort`) for **real-time activity classification** using the device's accelerometer.

---

## Problem Statement
Smartphone fitness tracking must balance **accuracy, responsiveness, and privacy** while running on devices with **limited computational resources**.  
The goal of this project is to deliver a **secure, intuitive, and real-time mobile application** capable of logging daily physical activities (walking, jogging, stairs, standing, sitting) and providing meaningful fitness insights.

---

## Application Features
- **Activity Recognition:** On-device classification of walking, jogging, stairs, standing, and sitting.  
- **Activity Logging:** Stores activity labels with timestamps in a secure, encrypted database.  
- **Insights Visualization:** Displays active/inactive time, calories burned, and goal progress.  
- **User Profile Management:** Create, update, and manage personal profiles with fitness goals.  
- **Privacy and Security:** All activity data remains on-device; database encrypted using **SQL Cipher** and keys stored securely in **Android Keystore**.  
- **Intuitive UI:** Designed with Android architecture components (ViewModel, Repository, LiveData) for responsive and maintainable interface.

---

## Tools & Technologies
- **Language:** Java, XML  
- **Platform:** Android Studio (API 30+)  
- **Database:** Room ORM with **SQL Cipher encryption**  
- **Machine Learning:** ONNX Runtime (ORT) for mobile inference  
- **Architecture:** MVVM (Model-View-ViewModel) pattern for robust and testable design  

---

## System Design
- **Data Flow:** Accelerometer Sensor → Feature Extraction → ML Model Inference → Encrypted Database → UI Visualization  
- **Activity Recognition:** 100 samples per 5-second window, 50% overlap for continuous monitoring  
- **Post-Processing:** Duration calculation, MET-based calorie estimation, and live insight updates  
- **UI Components:**
  - Home & Activity Log: Visualizes recent activities  
  - Insights: Active time, inactive time, calories burned  
  - User Profile: Personal goals and account management  
  - Privacy & Data Transparency: User consent and 24-hour data retention policy  

---

## Database Design
- **Tables:**
  - `UserAccount`: Stores account information (name, email, password)  
  - `UserProfile`: Stores fitness goals (active minutes, calories target)  
  - `ActivityData`: Stores logged activities and timestamps  
  - `ActivityInsights`: Stores processed metrics (active/inactive time, calories burned)  
- **Relationships:**
  - One-to-one: `UserAccount` ↔ `UserProfile`  
  - One-to-many: `UserAccount` ↔ `ActivityData`  
  - One-to-many: `UserProfile` ↔ `ActivityInsights`  

---



