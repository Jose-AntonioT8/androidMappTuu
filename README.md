# MappTuu (Android) — documentación (modo visor)

Proyecto Android en **Kotlin** con **Jetpack Compose**. La app funciona como un **visor** de contenido (actividades, planes y perfil), con navegación entre pantallas, render reactivo de estado y un mapa con Google Maps.


---

## Stack / tech

- **UI**: Jetpack Compose + Material3
- **Navigation**: Navigation Compose (con `Route` y `NavHost`)
- **State**: `StateFlow` + `mutableStateOf` (y `collectAsState()` en UI)
- **DI**: Hilt (`@HiltAndroidApp`, `@HiltViewModel`)
- **Local cache**: Room (`MappTuuDataBase`, `Dao`, `Entity`, `Flow`)
- **Remote**: Retrofit + OkHttp Interceptor (Authorization Bearer token)
- **Auth**: FirebaseAuth
- **Maps**: Maps SDK + `maps-compose`
- **Images**: Coil (`AsyncImage`)

---

## Estructura del proyecto (high level)

El código principal vive en `app/src/main/java/com/example/mapptuu/` y se organiza en:

- **`ui/`**: screens de Compose (listas, detalle, mapa, profile, login/landing, settings, cámara) y components comunes (`Header`, `Footer`, etc.).
- **`data/`**: data layer: models, local (Room), remote (Retrofit) y repositories.
- **`di/`**: módulos de Hilt (bindings, Retrofit, FirebaseAuth, helpers).
- **`utils/`**: helpers transversales (por ejemplo `NotificationHelper`).

---

## Arranque de la app

### `AndroidManifest.xml`

- Declara el `application` como `.di.MappTuuApplication`.
- `MainActivity` es el `LAUNCHER`.
- Permisos relevantes: `INTERNET`, `CAMERA`, `POST_NOTIFICATIONS`.
- Config de Maps:
  - `meta-data` `com.google.android.geo.API_KEY` con `android:value="${MAPS_API_KEY}"`.

### `MappTuuApplication`

En `di/MappTuuApplication.kt`:

- `@HiltAndroidApp` inicializa Hilt para todo el graph de dependencias.

---

## Navegación (cómo se mueve el usuario por el visor)

### `ui/navigation/Route.kt`

Define un `sealed class Route` con rutas typesafe, por ejemplo:

- `ActivityList`, `ActivityDetail(id)`
- `PlanList`, `PlanDetail(id)`
- `LandingPage`, `Login`, `Register`
- `Profile`, `Map`, `Setting`, `Camera`

Además hay helpers tipo:

- `NavController.navigateToActivityDetail(id)`
- `NavController.navigateToPlanList()`
- `NavController.navigateToLandingPage()`

La idea es que los screens no “construyen strings”, sino que navegan usando estas funciones.

### `ui/navigation/NavGraph.kt`

`NavGraph()` crea:

- Un `NavController` (`rememberNavController()`).
- Un `NavHost` con `startDestination = Route.LandingPage`.
- El wiring de destinos: lista → detalle, landing → login/register, tabs hacia map/profile, etc.

En el visor, esto es el “router” central.

---

## Data flow (read/observe) — cómo llega el contenido a UI

### Concepto clave

El visor pinta UI a partir de **streams** (`Flow`) que vienen de la capa local, y los `ViewModel` los convierten a `uiState` para Compose.

En la práctica:

- **Room** expone `Flow<List<...>>` (ej. `observeAll()` en un `Dao`).
- Un **Repository** ofrece `observe(): Flow<Result<List<Model>>>`.
- El **ViewModel** hace `collect` en `viewModelScope` y actualiza un `StateFlow` (`uiState`).
- El **Screen** hace `collectAsState()` y renderiza con `when(uiState)`.

### Ejemplo real: lista de actividades

En `ui/activityList/ActivityListViewModel.kt`:

- `uiState: StateFlow<ListUiState>` con estados `Initial`, `Loading`, `Error`, `Succes`.
- Se mantiene una lista completa en memoria (`fullList`) para poder filtrar.
- Hay un state de search (`busquedaParametros`) y un `search()` que filtra por palabras.

En `ui/activityList/ActivityListScreen.kt`:

- Se usa `LazyColumn` para renderizar cards.
- Cada item es clickable y navega a detail con el `id`.
- `AsyncImage` carga la imagen desde URL (Coil).
- `Header` y `Footer` actúan como top/bottom navigation (tipo tabs).

---

## Pantallas principales del visor (UI)

Según el wiring de `NavGraph`/`Route`, los destinos principales del visor son:

- **Landing**
  - `LandingPageScreen`: entry screen para ir a login/register o saltar a contenido si aplica.
- **Login / Register**
  - `LoginScreen`, `SignUpScreen`: autenticación (el visor usa el token para requests).
- **Activities**
  - `ActivityListScreen`: lista con `LazyColumn`, search y navegación a detalle.
  - `ActivityDetailScreen`: vista detalle por `id`.
- **Plans**
  - `PlanListScreen`: lista de planes con navegación a detalle.
  - `PlanDetailScreen`: vista detalle por `id`.
- **Map**
  - `MapScreen`: visor geográfico (Google Maps + maps-compose).
- **Profile / Camera**
  - `ProfileScreen`: vista de perfil.
  - `CameraScreen`: captura/uso de cámara desde el flujo de profile.
- **Setting**
  - `SettingScreen`: ajustes.

---

## DI (Hilt) y configuración de red

### `di/AppModule.kt`

- Provee `FirebaseAuth` (singleton).
- Provee `NotificationHelper` (singleton).
- Define bindings entre interfaces y implementations (por ejemplo `Repository` y `DataSource`) usando qualifiers:
  - `@LocalDataSource`
  - `@RemoteDataSource`

### `di/RemoteModule.kt`

- Crea `Retrofit` con `OkHttpClient` y un `Interceptor` que añade:
  - `Authorization: Bearer <token>` cuando hay un token disponible desde `AuthRepository`.
- Expone APIs (`ActivityApi`, `PlanApi`, etc.) ya listas para usar.

---

## Room (local cache del visor)

### `data/local/MappTuuDataBase.kt`

`RoomDatabase` con entities:

- `UsersEntity`
- `ActivityEntity`
- `ActivityTypesEntity`
- `PlansEntity`

Expone `Dao`:

- `UsersDao`, `ActivityDao`, `ActivityTypesDao`, `PlansDao`

