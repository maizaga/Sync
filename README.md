# Sync

# General description of the solution
In an architectural level, MVVM pattern was chosen. Main reason for this scope, was to be able to make a unit test using JUnit, without the need of an instrumentation test. It is also the pattern I'm most comfortable with, for bigger projects. It includes data binding and lifecycle aware components, if you rotate the screen the project will still work as expected. Also dagger2 was used to implement dependency injection, very useful for testing and code readability.<br>
Another important decition, was to use RxJava2 for threading. I find it very convenient in general, specially for this project where performance was a challenge. Schedulers make it very easy to create dispatchers where threading is handled correctly, IO threads for reading the files and computation threads to manipulate long lists of data in memory. This pool makes a better use of the available device cores. RxJava was very usefull for unit testing, replacing with dependency injection the Schedulers.<br>
The repository pattern was used to make an abstraction of the origin of the data also good for testing or easily replacing the files for API calls.<br>
Since this project is focused in offline information, the use of a DB was necessary. Google introduced Room as Android SQLite ORM which I find very useful and easy to use.<br>
UI/UX was not the focus of the exercise, I just placed required components with default theme.

# Algorithm
The main algorithm is found in the `MainViewModel` `importJson()` function. First the selected file is read, using io scheduler to read the file and observed in a computation scheduler to continue working with the data. `startSync()` is called and created a new stream to be able to observe values in the main thread, and `syncValues()` is the function that actually does the job. I keep counters to provide the answer at the end, and helper variables and dictionaries to be able to keep track of Sessions that were originally stored and the one that we got from the file that have to be synced. This also is an effort to try to minimize computation.

# Model
It was indicated that the model consisted in Sessions and Roles, but from the restriction of storing a person only once I thought it was better to have a Session Entity and a Person entity. Then a SessionWithPerson Entity was added to store the many to many relationship and add the role value.

# How to run the unit test
Importing the project to Android Studio, you just need to right-click MainViewModelTest and Run it

# How to run the app
Same as with unit tests, with Android Studio hit run for the default app configuration. Make sure you have a virtual device or a device connected with USB.

# DB Dump for sessions_initial import:
```
D/com.maizaga.sync.viewmodels.MainViewModel: DB Dump
    Session: id: c07aed75de7cb71af7374d44825f1112, name: Registration, description: null, start: Sat Oct 19 12:00:00 GMT-03:00 2019, end: Sat Oct 19 13:00:00 GMT-03:00 2019
    artists: id: 6996364, name: Taylor McKnight
    Session: id: cd87ef377b2a8c95493b2e561381566a, name: Spacewalk Simulator, description: Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum., start: Sat Oct 19 13:00:00 GMT-03:00 2019, end: Sat Oct 19 15:00:00 GMT-03:00 2019
    speakers: id: 3677032, name: Neil Armstrong
    moderators: id: 4241674, name: George Jetson
    speakers: id: 6996370, name: Peggy Witson
    sponsors: id: 6996373, name: NASA
    moderators: id: 6996400, name: Yuri Gagarin
    Session: id: db96b37bb85de13bf415d35547f20f29, name: Robotics Banners, description: Create banners to represent the attributes of your <a href="https://en.wikipedia.org/wiki/Robotics" target="_self">robotics</a> group., start: Sat Oct 19 14:00:00 GMT-03:00 2019, end: Sat Oct 19 15:00:00 GMT-03:00 2019
    moderators: id: 3677032, name: Neil Armstrong
    speakers: id: 6996379, name: John Glenn
    speakers: id: 6996382, name: Mae Jemison
    sponsors: id: 6996385, name: Lego
    Session: id: 58beb7efa54037900c212a9aa43d76c5, name: Spacewalk Simulator 2, description: This motion-based simulation features a spacewalk outside of the International Space Station., start: Sat Oct 19 14:00:00 GMT-03:00 2019, end: Sat Oct 19 16:00:00 GMT-03:00 2019
    speakers: id: 3677032, name: Neil Armstrong
    sponsors: id: 6996373, name: NASA
    speakers: id: 6996376, name: Valentina Tereshkova
    Session: id: fb6d8dfdc42841d3425c4fdf92112cc3, name: Multi-Axis Trainer, description: This simulator, modeled after a trainer used in the Mercury program, allows the you to experience the disorientation astronauts would feel if a capsule went into a tumble spin., start: Sat Oct 19 15:00:00 GMT-03:00 2019, end: Sat Oct 19 16:00:00 GMT-03:00 2019
    moderators: id: 6996370, name: Peggy Witson
    sponsors: id: 6996373, name: NASA
    speakers: id: 6996394, name: Sally Ride
    Session: id: bf0b472aa687b0214b3372808fc7de3f, name: Spacewalk Simulator 3, description: This motion-based simulation features a spacewalk outside of the International Space Station., start: Sat Oct 19 15:00:00 GMT-03:00 2019, end: Sat Oct 19 16:00:00 GMT-03:00 2019
    speakers: id: 3677032, name: Neil Armstrong
    sponsors: id: 6996373, name: NASA
    artists: id: 6996388, name: LE ARTISTE
    exhibitors: id: 6996391, name: Tay Exh Mod
    moderators: id: 6996394, name: Sally Ride
    Session: id: cfa04d5e8c5a7653c84bb092f9cb52d0, name: Early Space History, description: In this session, trainees find out the inside story behind the beginnings of the space program. Highlights include stories of early rocket scientists and dreamers who perservered despite ridicule from their contemporaries, the reaction of Americans to the Soviet launch of Sputnik and how animals paved the way for manned launches.<br /><br /><a href="https://schedspacecamp2015.sched.org/editor/amazon.com/earlyspacehistory" target="_blank">Purchase the book</a>, start: Sat Oct 19 15:15:00 GMT-03:00 2019, end: Sat Oct 19 16:15:00 GMT-03:00 2019
    speakers: id: 2168712, name: Buzz Aldrin
    speakers: id: 6996379, name: John Glenn
    speakers: id: 6996400, name: Yuri Gagarin
    Session: id: b91846ab1443bb564ebcc7c4d63a1da2, name: Astronomy Stories, description: Learn about the reasons for and history behind constellations. Learn the Greek mythology versions of the constellations through story telling and roleplaying. Identifying various constellations in the sky and determining in what seasons certain constellations can be viewed is also part of the activity., start: Sun Oct 20 05:00:00 GMT-03:00
```
    
# DB Dump for sessions edited import:
```
D/com.maizaga.sync.viewmodels.MainViewModel: DB Dump
    Session: id: c07aed75de7cb71af7374d44825f1112, name: Registration, description: null, start: Sat Oct 19 12:00:00 GMT-03:00 2019, end: Sat Oct 19 13:00:00 GMT-03:00 2019
    artists: id: 6996364, name: Taylor McKnight
    Session: id: cd87ef377b2a8c95493b2e561381566a, name: Spacewalk Simulator, description: Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum., start: Sat Oct 19 13:00:00 GMT-03:00 2019, end: Sat Oct 19 15:00:00 GMT-03:00 2019
    speakers: id: 3677032, name: Neil Armstrong
    moderators: id: 4241674, name: George Jetson
    speakers: id: 6996370, name: Peggy Witson
    sponsors: id: 6996373, name: NASA
    moderators: id: 6996400, name: Yuri Gagarin
    Session: id: db96b37bb85de13bf415d35547f20f29, name: Robotics Banners, description: Create banners to represent the attributes of your <a href="https://en.wikipedia.org/wiki/Robotics" target="_self">robotics</a> group., start: Sat Oct 19 14:00:00 GMT-03:00 2019, end: Sat Oct 19 15:00:00 GMT-03:00 2019
    moderators: id: 3677032, name: Neil Armstrong
    speakers: id: 6996379, name: John Glenn
    speakers: id: 6996382, name: Mae Jemison
    sponsors: id: 6996385, name: Lego
    Session: id: 58beb7efa54037900c212a9aa43d76c5, name: Spacewalk Simulator 2, description: This motion-based simulation features a spacewalk outside of the International Space Station., start: Sat Oct 19 14:00:00 GMT-03:00 2019, end: Sat Oct 19 16:00:00 GMT-03:00 2019
    speakers: id: 3677032, name: Neil Armstrong
    sponsors: id: 6996373, name: NASA
    speakers: id: 6996376, name: Valentina Tereshkova
    Session: id: fb6d8dfdc42841d3425c4fdf92112cc3, name: Multi-Axis Trainer, description: This simulator, modeled after a trainer used in the Mercury program, allows the you to experience the disorientation astronauts would feel if a capsule went into a tumble spin., start: Sat Oct 19 15:00:00 GMT-03:00 2019, end: Sat Oct 19 16:00:00 GMT-03:00 2019
    moderators: id: 6996370, name: Peggy Witson
    sponsors: id: 6996373, name: NASA
    speakers: id: 6996394, name: Sally Ride
    Session: id: bf0b472aa687b0214b3372808fc7de3f, name: Spacewalk Simulator 3, description: This motion-based simulation features a spacewalk outside of the International Space Station., start: Sat Oct 19 15:00:00 GMT-03:00 2019, end: Sat Oct 19 16:00:00 GMT-03:00 2019
    speakers: id: 3677032, name: Neil Armstrong
    sponsors: id: 6996373, name: NASA
    artists: id: 6996388, name: LE ARTISTE
    exhibitors: id: 6996391, name: Tay Exh Mod
    moderators: id: 6996394, name: Sally Ride
    Session: id: cfa04d5e8c5a7653c84bb092f9cb52d0, name: Early Space History, description: In this session, trainees find out the inside story behind the beginnings of the space program. Highlights include stories of early rocket scientists and dreamers who perservered despite ridicule from their contemporaries, the reaction of Americans to the Soviet launch of Sputnik and how animals paved the way for manned launches.<br /><br /><a href="https://schedspacecamp2015.sched.org/editor/amazon.com/earlyspacehistory" target="_blank">Purchase the book</a>, start: Sat Oct 19 15:15:00 GMT-03:00 2019, end: Sat Oct 19 16:15:00 GMT-03:00 2019
    speakers: id: 2168712, name: Buzz Aldrin
    speakers: id: 6996379, name: John Glenn
    speakers: id: 6996400, name: Yuri Gagarin
    Session: id: b91846ab1443bb564ebcc7c4d63a1da2, name: Astronomy Stories, description: Learn about the reasons for and history behind constellations. Learn the Greek mythology versions of the constellations through story telling and roleplaying. Identifying various constellations in the sky and determining in what seasons certain constellations can be viewed is also part of the activity., start: Sun Oct 20 05:00:00 GMT-03:00
```

# DB Dump for sessions deleted import:
```
D/com.maizaga.sync.viewmodels.MainViewModel: DB Dump
    Session: id: c07aed75de7cb71af7374d44825f1112, name: Registration, description: null, start: Sat Oct 19 12:00:00 GMT-03:00 2019, end: Sat Oct 19 13:00:00 GMT-03:00 2019
    artists: id: 6996364, name: Taylor McKnight
    Session: id: cd87ef377b2a8c95493b2e561381566a, name: Spacewalk Simulator, description: Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum., start: Sat Oct 19 13:00:00 GMT-03:00 2019, end: Sat Oct 19 15:00:00 GMT-03:00 2019
    speakers: id: 3677032, name: Neil Armstrong
    moderators: id: 4241674, name: George Jetson
    speakers: id: 6996370, name: Peggy Witson
    sponsors: id: 6996373, name: NASA
    moderators: id: 6996400, name: Yuri Gagarin
    Session: id: db96b37bb85de13bf415d35547f20f29, name: Robotics Banners, description: Create banners to represent the attributes of your <a href="https://en.wikipedia.org/wiki/Robotics" target="_self">robotics</a> group., start: Sat Oct 19 14:00:00 GMT-03:00 2019, end: Sat Oct 19 15:00:00 GMT-03:00 2019
    moderators: id: 3677032, name: Neil Armstrong
    speakers: id: 6996379, name: John Glenn
    speakers: id: 6996382, name: Mae Jemison
    sponsors: id: 6996385, name: Lego
    Session: id: 58beb7efa54037900c212a9aa43d76c5, name: Spacewalk Simulator 2, description: This motion-based simulation features a spacewalk outside of the International Space Station., start: Sat Oct 19 14:00:00 GMT-03:00 2019, end: Sat Oct 19 16:00:00 GMT-03:00 2019
    speakers: id: 3677032, name: Neil Armstrong
    sponsors: id: 6996373, name: NASA
    speakers: id: 6996376, name: Valentina Tereshkova
    Session: id: fb6d8dfdc42841d3425c4fdf92112cc3, name: Multi-Axis Trainer, description: This simulator, modeled after a trainer used in the Mercury program, allows the you to experience the disorientation astronauts would feel if a capsule went into a tumble spin., start: Sat Oct 19 15:00:00 GMT-03:00 2019, end: Sat Oct 19 16:00:00 GMT-03:00 2019
    moderators: id: 6996370, name: Peggy Witson
    sponsors: id: 6996373, name: NASA
    speakers: id: 6996394, name: Sally Ride
    Session: id: bf0b472aa687b0214b3372808fc7de3f, name: Spacewalk Simulator 3, description: This motion-based simulation features a spacewalk outside of the International Space Station., start: Sat Oct 19 15:00:00 GMT-03:00 2019, end: Sat Oct 19 16:00:00 GMT-03:00 2019
    speakers: id: 3677032, name: Neil Armstrong
    sponsors: id: 6996373, name: NASA
    artists: id: 6996388, name: LE ARTISTE
    exhibitors: id: 6996391, name: Tay Exh Mod
    moderators: id: 6996394, name: Sally Ride
    Session: id: cfa04d5e8c5a7653c84bb092f9cb52d0, name: Early Space History, description: In this session, trainees find out the inside story behind the beginnings of the space program. Highlights include stories of early rocket scientists and dreamers who perservered despite ridicule from their contemporaries, the reaction of Americans to the Soviet launch of Sputnik and how animals paved the way for manned launches.<br /><br /><a href="https://schedspacecamp2015.sched.org/editor/amazon.com/earlyspacehistory" target="_blank">Purchase the book</a>, start: Sat Oct 19 15:15:00 GMT-03:00 2019, end: Sat Oct 19 16:15:00 GMT-03:00 2019
    speakers: id: 2168712, name: Buzz Aldrin
    speakers: id: 6996379, name: John Glenn
    speakers: id: 6996400, name: Yuri Gagarin
    Session: id: b91846ab1443bb564ebcc7c4d63a1da2, name: Astronomy Stories, description: Learn about the reasons for and history behind constellations. Learn the Greek mythology versions of the constellations through story telling and roleplaying. Identifying various constellations in the sky and determining in what seasons certain constellations can be viewed is also part of the activity., start: Sun Oct 20 05:00:00 GMT-03:00
```

# App Scereenshots
![Alt text](/device-2019-08-25-193717.png?raw=true "App after session_initial import")
![Alt text](/device-2019-08-25-193818.png?raw=true "App after session_deleted import")

# App Profiling
I profiled the app taking a look at the CPU usage. The first image shows the first import action executed. In the second one you can see the peak was at 18% CPU and in the first one, how threads are distributed reading from disk and making calculations in computaion thread. Third and fourth image show the other imports done in the profile session: session_edited and session-deleted json files import.
![Alt text](/Screenshot2019-08-25at18.58.27.png?raw=true "Profile monitor after session_initial import")
![Alt text](/Screenshot2019-08-25at18.58.34.png?raw=true "Profile monitor after session_initial import")
![Alt text](/Screenshot2019-08-25at18.58.51.png?raw=true "Profile monitor after session_edited import")
![Alt text](/Screenshot2019-08-25at18.59.01.png?raw=true "Profile monitor after session_deleted import")
