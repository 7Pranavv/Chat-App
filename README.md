# 💬 Real-Time Chat App

A full-stack real-time chat application built with **Spring Boot**, **MongoDB**, **WebSockets (STOMP)**, and **React + Vite**. Users can create or join chat rooms and exchange messages instantly.

---

## 🏗️ Tech Stack

### Backend
- **Java 21** with **Spring Boot 3.4.0**
- **Spring WebSocket** (STOMP protocol over SockJS)
- **Spring Data MongoDB**
- **Lombok**
- **Spring DevTools**

### Frontend
- **React 18** with **Vite 6**
- **@stomp/stompjs** + **sockjs-client** — WebSocket client
- **Axios** — REST API calls
- **Tailwind CSS** — styling
- **React Router v7** — routing
- **React Hot Toast** — notifications

---

## 📁 Project Structure

```
chat-app-main/
├── chat-app-backend/          # Spring Boot backend
│   └── src/main/java/com/substring/chat/
│       ├── controllers/
│       │   ├── RoomController.java    # REST: create room, join room, get messages
│       │   └── ChatController.java    # WebSocket: send & receive messages
│       ├── entities/
│       │   ├── Room.java              # MongoDB Room document
│       │   └── Message.java           # Embedded message entity
│       ├── playload/
│       │   └── MessageRequest.java    # WebSocket message payload
│       └── repositories/
│           └── RoomRepository.java    # MongoDB repository
│
└── front-chat/                # React frontend
    └── src/
        ├── components/
        │   ├── JoinCreateChat.jsx     # Landing page — create/join room
        │   └── ChatPage.jsx           # Active chat room UI
        ├── context/
        │   └── ChatContext.jsx        # Global state (roomId, user, connection)
        ├── services/
        │   └── RoomService.js         # Axios API calls
        └── config/
            ├── AxiosHelper.js         # Axios base URL config
            ├── Routes.jsx             # React Router routes
            └── helper.js             # Utility functions (timeAgo etc.)
```

---

## ⚙️ Prerequisites

Make sure the following are installed before running the app:

| Tool | Version | Download |
|------|---------|----------|
| Java | 21+ | https://adoptium.net |
| Maven | 3.9+ (or use `mvnw`) | Bundled |
| Node.js | 18+ | https://nodejs.org |
| MongoDB | 7+ (running locally) | https://www.mongodb.com |

---

## 🚀 Getting Started

### 1. Start MongoDB

Make sure MongoDB is running locally on the default port:

```bash
# On Windows (if installed as a service)
net start MongoDB

# On macOS/Linux
mongod --dbpath /data/db
```

MongoDB connection: `mongodb://localhost:27017/chatapp` (configured in `application.properties`)

---

### 2. Start the Backend

```bash
cd chat-app-backend

# Windows
.\mvnw.cmd spring-boot:run

# macOS / Linux
./mvnw spring-boot:run
```

The backend starts at **http://localhost:8080**

---

### 3. Start the Frontend

```bash
cd front-chat
npm install
npm run dev
```

The frontend starts at **http://localhost:5173**

---

## 🔌 API Reference

### REST Endpoints (via Axios)

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/v1/rooms` | Create a new chat room |
| `GET` | `/api/v1/rooms/{roomId}` | Join / fetch a room by ID |
| `GET` | `/api/v1/rooms/{roomId}/messages?page=0&size=20` | Get paginated message history |

**Create room request body:**
```json
{ "roomId": "my-room-name" }
```

---

### WebSocket (STOMP over SockJS)

| Direction | Destination | Description |
|-----------|-------------|-------------|
| Connect | `http://localhost:8080/chat` | SockJS endpoint |
| Publish | `/app/sendMessage/{roomId}` | Send a message to a room |
| Subscribe | `/topic/room/{roomId}` | Receive messages in real time |

**Message payload:**
```json
{
  "content": "Hello!",
  "sender": "YourName"
}
```

---

## 🖥️ How to Use

1. Open **http://localhost:5173** in your browser.
2. Enter your **name** and a **Room ID**.
3. Click **Create Room** to start a new room, or **Join Room** to enter an existing one.
4. Share the Room ID with others — they can join from their own browsers.
5. Messages are delivered in real time via WebSocket and persisted in MongoDB.

---

## 🐛 Known Issues & Fixes

### "Invalid Input" on Create / Join Room
The original `RoomService.js` sent the room ID as plain text (`Content-Type: text/plain`), but the backend expects a JSON object. The fix is to send `{ roomId: "..." }` with `Content-Type: application/json`.

Also, the original `JoinCreateChat.jsx` managed form state as a single object, which could cause stale closure bugs where typed values weren't captured before button click. The fix is to use separate `useState` hooks for each field.

**Apply both fixes** by replacing these two files with the corrected versions:
- `front-chat/src/services/RoomService.js`
- `front-chat/src/components/JoinCreateChat.jsx`

---

## 🔧 Configuration

Backend config lives in `chat-app-backend/src/main/resources/application.properties`:

```properties
spring.application.name=chat-app-backend
spring.data.mongodb.uri=mongodb://localhost:27017/chatapp
```

Frontend base URL is set in `front-chat/src/config/AxiosHelper.js`:

```js
export const baseURL = "http://localhost:8080";
```

---

## 📦 Building for Production

### Backend (JAR)
```bash
cd chat-app-backend
./mvnw clean package -DskipTests
java -jar target/chat-app-backend-0.0.1-SNAPSHOT.jar
```

### Frontend (Static files)
```bash
cd front-chat
npm run build
# Output: front-chat/dist/
```

Serve `dist/` with any static file server (Nginx, Apache, Vercel, etc.) and point the `baseURL` in `AxiosHelper.js` to your deployed backend URL.

---

## 📄 License

This project is open source. Feel free to use and modify it.
