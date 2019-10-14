# Sync
# POC

# General description of the solution
In an architectural level, MVVM pattern was chosen. Main reason for this scope, was to be able to make a unit test using JUnit, without the need of an instrumentation test. It is also the pattern I'm most comfortable with, for bigger projects. It includes data binding and lifecycle aware components, if you rotate the screen the project will still work as expected. Also dagger2 was used to implement dependency injection, very useful for testing and code readability.<br>
Another important desition, was to use RxJava2 for threading. I find it very convenient in general, specially for this POC where performance was a challenge. Schedulers make it very easy to create dispatchers where threading is handled correctly, IO threads for reading the files and computation threads to manipulate long lists of data in memory. This pool makes a better use of the available device cores. RxJava was very usefull for unit testing, replacing with dependency injection the Schedulers.<br>
The repository pattern was used to make an abstraction of the origin of the data also good for testing or easily replacing the files for API calls.<br>
Since this project is focused in offline information, the use of a DB was necessary. Google introduced Room as Android SQLite ORM which I find very useful and easy to use.<br>
UI/UX was not the focus of the exercise, I just placed required components with default theme.

# Algorithm
The main algorithm is found in the `MainViewModel` `importJson()` function. First the selected file is read, using io scheduler to read the file and observed in a computation scheduler to continue working with the data. `startSync()` is called and created a new stream to be able to observe values in the main thread, and `syncValues()` is the function that actually does the job. I keep counters to provide the answer at the end, and helper variables and dictionaries to be able to keep track of Sessions that were originally stored and the one that we got from the file that have to be synced. This also is an effort to try to minimize computation.

# Model
It was indicated that the model consisted in Sessions and Roles, but from the restriction of storing a person only once I thought it was better to have a Session Entity and a Person entity. Then a SessionWithPerson Entity was added to store the many to many relationship and add the role value.
