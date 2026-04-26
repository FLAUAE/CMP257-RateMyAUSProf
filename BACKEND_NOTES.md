# RateMyAUSProf — Backend Notes

## How to Run

1. Run `mvn package` at the repo root to produce `target/rateauprof-1.0.war`.
2. Copy (or deploy) the `.war` to your Tomcat 9 `webapps/` directory.
3. Start Tomcat. The app deploys under the context path `/rateauprof-1.0` (or rename the WAR to `ROOT.war` for `/`).
4. Open `http://localhost:8080/rateauprof-1.0/homepage.html` in a browser.

## API Endpoints

### GET /api/departments
Returns all departments with their nested professors.

Sample response:
```json
[
  {
    "short_id": "CEN",
    "name": "College of Engineering",
    "image_path": "img/aus-cen.jpeg",
    "professors": [
      {
        "id": "p_cen_1",
        "name": "Dr. Fadi Aloul",
        "gender": "M",
        "dept_short_id": "CEN",
        "rating": 4.5,
        "reviews": 62
      }
    ]
  }
]
```

### POST /api/departments
Body: `{ "short_id": "...", "name": "...", "image_path": "..." }`
Returns 201 + created department JSON.

### PUT /api/departments
Body: `{ "short_id": "...", "name": "...", "image_path": "..." }`
Updates name and image_path for the given short_id. Returns 200 + updated department or 404.

### DELETE /api/departments?id=CEN
Returns 204 on success, 404 if not found.

### POST /api/professors
Body: `{ "dept_short_id": "CEN", "professor": { "id": "...", "name": "...", "gender": "M", "dept_short_id": "CEN", "rating": 4.5, "reviews": 10 } }`
Returns 201 + created professor JSON.

### PUT /api/professors
Same body shape as POST. Returns 200 + updated professor or 404.

### DELETE /api/professors?dept=CEN&id=p_cen_1
Returns 204 on success, 404 if not found.

## Data Persistence

All data is stored in memory (ConcurrentHashMap). It resets to the seeded defaults every time Tomcat restarts. There is no database or file persistence.

## Admin Pages

- `admin.html` — Dashboard landing page with links to the two management areas.
- `admin-departments.html` — View, add, edit, and delete departments.
- `admin-professors.html` — Select a department, then view, add, edit, and delete its professors.

## Features Not Implemented

- **Login / auth / session management** — not in scope for this contributor.
- **View Reviews** — owned by Mohammed.
- **Logout flow** — owned by a separate teammate.
- **Teacher rating page** — already built by another teammate; professor "View" buttons link to `teacher.html?id=<prof_id>`.
