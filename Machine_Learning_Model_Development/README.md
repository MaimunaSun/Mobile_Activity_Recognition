# Mobile Activity Recognition – Model Development

## Overview
This repository contains the **machine learning model development pipeline** for a mobile activity recognition system using smartphone accelerometer data.  
The work focuses on **data preprocessing, feature engineering, model training, evaluation, and deployment optimisation**, and represents the model component of a larger mobile fitness application.

---

## Problem Statement
Smartphone-based activity recognition must balance **accuracy, computational efficiency, and robustness** to sensor noise and device orientation.  
The objective of this project was to design a **lightweight, high-performing model** suitable for **real-time, on-device inference**, without reliance on cloud processing or wearable devices.

---

## Dataset
- **Dataset:** WISDM v1.1  
- **Participants:** 36 users  
- **Sensor:** Tri-axial accelerometer  
- **Sampling Rate:** 20 Hz  
- **Phone Position:** Front pants pocket  
- **Activities (after relabelling):**
  - Walking  
  - Jogging  
  - Sitting  
  - Standing  
  - Stairs (upstairs + downstairs)

---

## Tools & Technologies
- **Language:** Python  
- **Environment:** Google Colab (Jupyter Notebook)  
- **Libraries:** Scikit-learn, NumPy, Pandas, Imbalanced-learn, ONNX, ONNX Runtime  

---

## Data Preprocessing
- Removed malformed records and samples with missing or zero-valued sensor readings  
- Sorted time-series data by user and timestamp  
- Merged upstairs and downstairs activities into a single *stairs* class to reduce label ambiguity  
- Addressed class imbalance using **class-weighted learning**

---

## Feature Engineering
- Segmented continuous sensor data using **5-second sliding windows** with **50% overlap**  
- Generated orientation-invariant features using acceleration magnitude  
- Extracted discriminative **time- and frequency-domain features**, including:
  - Statistical moments (mean, variance, skewness, kurtosis)
  - RMS, signal energy, interquartile range
  - Autocorrelation coefficients
  - FFT-based features, entropy, and cepstral coefficients

---

## Feature Selection
- Applied **Recursive Feature Elimination (RFE)** to reduce dimensionality  
- Selected an optimal subset of **40 features**, achieving strong performance with reduced computational cost  

---

## Model Design
- Preprocessing pipeline: mean imputation and feature standardisation  
- Classifier: **Support Vector Machine (Linear Kernel)**  
- Class weights incorporated to handle dataset imbalance  
- Model choice prioritised inference speed, memory efficiency, and generalisation  

---

## Model Evaluation
- **5-fold cross-validation**  
- Performance on unseen test data:
  - Accuracy: **93.07%**  
  - Sensitivity: **93.07%**  
  - Specificity: **98.27%**  
- Confusion analysis highlighted expected overlap between walking, stairs, and jogging activities  

---

## Deployment Preparation
- Converted the trained Scikit-learn pipeline to **ONNX**  
- Further optimised to **ONNX Runtime (ORT)** format for mobile inference  
- Verified identical prediction performance between Scikit-learn and ONNX Runtime models  

---

## Key Outcomes
- High-accuracy, lightweight activity recognition model  
- Optimised for real-time, on-device execution  
- Privacy-preserving by design with no cloud dependency  

---

## Relation to Full Project
This repository represents the **machine learning model development component** of a broader system that includes:
- Real-time sensor data acquisition  
- On-device feature extraction and classification  
- Activity visualisation and fitness insights  
- Compliance with data protection and responsible AI practices  

