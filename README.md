RealTicker – Stock Insights Platform


Overview

RealTicker is a full-stack web application that helps users understand stock market trends using data analysis and visualization.

It displays the Top 10 stocks, shows historical price trends, and provides investment insights in a simple and user-friendly interface.

Features
 Top 10 Stocks Table
 6 Months Historical Price Chart
Investment Insights (Trend, Risk, Suggestion)
Fast and responsive UI
Data-driven analysis (no external dependency)

Tech Stack
Frontend
React.js (Vite)
Tailwind CSS
Recharts (for charts)
Backend
Spring Boot (Java)


Data
Mock stock data (generated dynamically)
Setup Steps
🔹 1. Clone Repository
git clone https://github.com/your-username/realticker.git
cd realticker
🔹 2. Backend Setup (Spring Boot)
cd realticker-backend
mvn spring-boot:run

Runs on:

http://localhost:8081
🔹 3. Frontend Setup (React)
cd realticker-frontend
npm install
npm run dev

Runs on:

http://localhost:5173
🔹 4. Open Application

Visit:
http://localhost:5173


Architecture Diagram
          ┌────────────────────┐
          │    React Frontend  │
          │ (Vite + Tailwind) │
          └─────────┬──────────┘
                    │ API Calls
                    ▼
          ┌────────────────────┐
          │  Spring Boot API   │
          │   (Backend)        │
          └─────────┬──────────┘
                    │
                    ▼
          ┌────────────────────┐
          │  Mock Stock Data   │
          │ (Generated Logic)  │
          └────────────────────┘

          
Analysis Logic (LLM Replacement)

Originally, the system was designed to use a HuggingFace LLM for stock analysis.

However, to ensure:

Reliability
Faster performance
No API dependency

we implemented a data-driven analysis model.

How Analysis Works

Trend
Upward → Price increasing
Downward → Price decreasing
Sideways → Stable
Risk

Based on price volatility
Suggestion
Buy / Hold / Avoid


 Example Output
Trend: Upward
Risk: Medium
Suggestion: Buy
Reason: Stock shows consistent growth over time


LLM Used (Original Design)
HuggingFace Models (planned):
google/flan-t5-base
mistralai/Mistral-7B-Instruct

Note:
Due to API limitations and stability concerns, the final implementation uses deterministic analysis logic instead of live LLM calls.

API Endpoints
Method	Endpoint	Description
GET	/api/stocks/top10	Get top 10 stocks
GET	/api/stocks/{ticker}/history	Get stock history
POST	/api/stocks/{ticker}/analyze	Get stock analysis
