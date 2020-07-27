![Android DText Library](docs/assets/tasker_cover.png)

[![Download](https://api.bintray.com/packages/alshakib/Tasker/dev.alshakib.tasker/images/download.svg)](https://bintray.com/alshakib/Tasker/dev.alshakib.tasker/_latestVersion)

# Tasker - An [AsyncTask](https://developer.android.com/reference/android/os/AsyncTask) Alternative

`Tasker` is intended to enable proper and easy use of the UI and the background thread. `AsyncTask` is going to be deprecated in API level 30. Android reference suggests to use the standard `java.util.concurrent` class as alternative ([more here](https://developer.android.com/reference/android/os/AsyncTask)). `Tasker` is a wrapper class of `java.util.concurrent` to run computation in the background thread and publish the result in the UI thread. The source code is published under GPLv3 and the license is available [here](LICENSE). An article about `Tasker` is available [here](https://alshakib.dev/blog/let_me_introduce_tasker-18-07-2020).

## Table of Contents

- [Supported SDK](#supported-sdk)
- [Getting Started](#getting-started)
  - [Installation](#installation)
    - [For Gradle:](#for-gradle)
    - [For Maven:](#for-maven)
  - [Usages](#usages)
- [Contributing](#contributing)
- [Thanks to](#thanks-to)
- [License](#license)

## Supported SDK

The latest version is available for,

* Android SKD 14 and higher

## Getting Started

An `Tasker` object lets you perform operations in the background. When they've finished running, then it allows you to update UI in the main thread.

### Installation

#### For Gradle:

```java
repositories {
  ...
  jcenter()
}

dependencies {
  ...
  implementation 'dev.alshakib.tasker:tasker:1.0.1'
}
```

#### For Maven:

```xml
<dependency>
  <groupId>dev.alshakib.tasker</groupId>
  <artifactId>tasker</artifactId>
  <version>1.0.1</version>
  <type>pom</type>
</dependency>
```



### Usages

For basic usages, you'll need to create a `Tasker`  object, and pass a `Tasker.Task<Result>` object to the `executeAsync()` method.

You can create a `Tasker.Task<Result>` class by extending the `Tasker.Task<Result>` class, and implementing its `doInBackground()` method. The code in this method runs in a background thread, so it's the perfect place for you to put your code for a heavy job. The `Tasker.Task<Result>` class also has an `onPreExecute()` method that runs before `doInBackground()` and an `onPostExecute()` method that runs afterward.

`Tasker.Task` is defined by a generic parameter: Result which is the type of the task result. You can set this to Void if you're not going to use it.

```java
class MyTask extends Tasker.Task<Result> {

    @Override
    protected void onPreExecute() {
		// Code to run before executing the task.
    }

    @Override
    protected Result doInBackground() {
        // Code that you want to run in a background thread.
        return null;
    }

    @Override
    protected void onPostExecute(Result result) {
		// Code that you want to run when the task is complete.
    }
}
```

You can run a `Task` by calling the `Tasker executeAsync()` method and passing it a `Tasker.Task` object.

```java
Tasker tasker = new Tasker();
tasker.executeAsync(new MyTask());
```

For a practical example, see [here](https://gitlab.com/AlShakib/tasker/blob/master/app/src/main/java/dev/alshakib/tasker/example/MainActivity.java#L55)

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## Thanks to

- Project Icon is made by [Pixel Perfect](https://www.flaticon.com/authors/pixel-perfect) from [www.flaticon.com](https://www.flaticon.com)

## License

[GNU General Public License v3.0](LICENSE)

Copyright © 2020 [Al Shakib](https://alshakib.dev/)
