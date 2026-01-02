# Mobile Activity Recognition – Full Project

## Overview
This repository contains the complete **Mobile Activity Recognition** system, combining a **machine learning model** for activity classification with a **mobile Android application** for real-time fitness tracking.  
The system enables **privacy-preserving, on-device activity recognition**, providing users with insights such as calories burned, activity duration, and daily goals achieved.

---

## Project Structure
The project is organized into two main components:

1. **ML Model Development**  
   - Preprocessing, feature extraction, model training, evaluation, and export to ONNX/ORT for mobile deployment.  

2. **Fitness Tracking Application (FitBox)**  
   - Android app that performs on-device inference, logs activities, visualizes fitness insights, and manages user profiles securely.

---

## Key Features
- **Real-time Activity Recognition** on the smartphone  
- **Activity Logging** with encrypted local database  
- **Fitness Insights**: active/inactive time, calories burned, goal progress  
- **User Profile Management** with secure storage  
- **Privacy-first Design**: no cloud dependency, data stored locally  

---

## Technologies
- **Mobile App:** Java, Android Studio, MVVM architecture, Room ORM, SQL Cipher  
- **ML Model:** Python, Scikit-learn, ONNX Runtime (ORT)  
- **Sensors:** Smartphone accelerometer  

---

## Goal
This project demonstrates the integration of **machine learning, mobile software engineering, and privacy-aware design** to deliver a practical, cost-effective, and intuitive fitness tracking solution.
