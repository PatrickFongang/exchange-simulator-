## 👤 Zarządzanie Użytkownikami (`UserController`)

### 1. Pobierz listę wszystkich użytkowników

* **Metoda:** `GET`
* **URL:** `http://localhost:8080/api/user`

### 2. Pobierz dane konkretnego użytkownika (ID: 1)

* **Metoda:** `GET`
* **URL:** `http://localhost:8080/api/user/1`
* *Użyj tego, aby sprawdzić nagłówek 404, jeśli wpiszesz ID, którego nie ma w bazie.*

### 3. Stwórz nowego użytkownika

* **Metoda:** `POST`
* **URL:** `http://localhost:8080/api/user`
* **Nagłówki (Headers):** `Content-Type: application/json`
* **Body (raw JSON):**

```json
{
    "username": "tester_crypto",
    "email": "test@example.com"
}

```

---

## 📈 Zarządzanie Pozycjami (`PositionController`)

### 1. Pobierz portfel (pozycje) użytkownika (ID: 1)

* **Metoda:** `GET`
* **URL:** `http://localhost:8080/api/users-positions/1`

### 2. Otwórz nową pozycję (Kupno)

* **Metoda:** `POST`
* **URL:** `http://localhost:8080/api/users-positions/1/buy`
* **Nagłówki:** `Content-Type: application/json`
* **Body (raw JSON):**

```json
{
    "token": "BTC",
    "quantity": 0.05
}

```

### 3. Zamknij/Pomniejsz pozycję (Sprzedaż)

* **Metoda:** `POST`
* **URL:** `http://localhost:8080/api/users-positions/1/sell`
* **Nagłówki:** `Content-Type: application/json`
* **Body (raw JSON):**

```json
{
    "token": "BTC",
    "quantity": 0.02
}

```