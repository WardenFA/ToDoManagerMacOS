# ToDo Manager for macOS

A lightweight Java-based To-Do manager application designed to run natively on macOS with a clean UI and automatic SQLite database creation. Built using Java 22 and packaged into a macOS `.app` bundle.

---

## 📦 Features

- Add, list, and delete tasks directly in the terminal  
- SQLite-based persistent storage  
- Automatically creates `tasks.db` on first launch  
- Fully packaged as a native `.app` bundle for macOS  
- Built using Maven with a fat JAR (`jar-with-dependencies`)  

---

## 🛠 Technologies Used

- Java 22  
- SQLite (via `sqlite-jdbc`)  
- Maven (build automation)  
- macOS `.app` bundling with custom script  
- Terminal-based UI  

---

## 🚀 Installation & Running
Varint-1 (hard)
1. **Clone the repository**

```bash
git clone https://github.com/WardenFA/ToDoManagerMacOS.git
cd ToDoManagerMacOS
```
2. **Build the project using Maven**
```mvn clean package```
3.Run the app (if using command line)
```java -jar target/to-do-manager-app-1.0-SNAPSHOT-jar-with-dependencies.jar```

Variant-2(easy)
- simply double-click the NoxManager.app (macOS native bundle)
  ```if there are any problems with confidencial launching --> find confidency parametrs on your mac --> press acception for launching app```

## Project structure
<pre>
ToDoManagerMacOS/
├── NoxManager.app/               
│   └── Contents/
│       ├── Info.plist            
│       ├── MacOS/
│       │   └── run.sh            
│       └── Resources/
│           └── target/
│               └── to-do-manager-app-1.0-SNAPSHOT-jar-with-dependencies.jar
├── pom.xml                       
├── src/
│   └── main/
│       └── java/org/example/
│           ├── Main.java
│           └── DatabaseManager.java
├── tasks.db                      
└── README.md                     
</pre>

---

## 🧰 For macOS Bundling
The .app was created manually and includes:

Contents/MacOS with shell script to launch the JAR

Contents/Resources with your compiled fat JAR

Info.plist for macOS app metadata

If you want help building the .app on your machine, feel free to reach out or check the script provided in the project.

## 📜 License
This project is licensed under the MIT License


## 👤 Author
WardenFA
