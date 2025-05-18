# ToDoManagerMacOS

Java-приложение для управления задачами (To-Do Manager), созданное с возможностью запуска в виде полноценного `.app` на macOS.  
Этот проект — часть моего портфолио и демонстрирует умение собирать кроссплатформенные Java-приложения с использованием Maven, SQLite и macOS-specific структур.

---

## 🔧 Функциональность

- Управление задачами: добавление, отображение, удаление.
- Хранение данных в локальной SQLite базе `tasks.db`.
- Автоматическое создание базы данных при первом запуске.
- GUI-запуск на macOS через `.app` (NoxManager.app).
- Maven-сборка с подключением всех зависимостей (fat JAR).

---

## 📁 Структура проекта

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
## ▶️ Как запустить

### 📦 Вариант 1: Через `.app` (на macOS)

1. Открой `NoxManager.app` двойным кликом.
2. Если macOS не разрешает запуск — зайди в:
Системные настройки → Конфиденциальность и безопасность → Разрешить запуск


### 🧱 Вариант 2: Через терминал вручную

```Терминал
cd ToDoManagerMacOS
mvn clean package
java -jar target/to-do-manager-app-1.0-SNAPSHOT-jar-with-dependencies.jar
```

## 🧰 Сборка проекта
```Терминал
mvn clean package
```
Будет создан jar:
`target/to-do-manager-app-1.0-SNAPSHOT-jar-with-dependencies.jar`

Убедитесь, что установлен Maven и JDK 22+
SQLite база создаётся автоматически при первом запуске в корне проекта (во избежание ошибок пустая БД предустановлена в проекте)


## 🐞 Возможные ошибки и их решение
No suitable driver found for jdbc:sqlite:
➤ Убедитесь, что в pom.xml подключена зависимость sqlite-jdbc от org.xerial.

ClassNotFoundException при загрузке драйвера:
➤ Используйте Class.forName("org.sqlite.JDBC"); и обязательно оберните в try-catch.

No line found (Scanner):
➤ Возникает при неожиданном завершении ввода — убедитесь, что Scanner.nextLine() вызывается после проверки наличия строки.

no main manifest attribute:
➤ Приложение запускается не через jar-with-dependencies, либо манифест не был указан. Убедитесь в правильной конфигурации maven-assembly-plugin.

src refspec master does not match any:
➤ Это означает, что локальная ветка master не существует. Используйте текущую ветку (main) или создайте master.

## 🛠 Используемые технологии
Java 22

SQLite (через JDBC)

Maven

macOS .app structure

Shell скрипт для запуска


## 📦 Релиз
Собранный .app можно упаковать в .zip или .dmg и распространять для запуска на других Mac-устройствах.
Также рекомендуется оформить раздел GitHub Releases.

## 👨‍💻 Автор
WardenFA
GitHub: github.com/WardenFA

## 📄 Лицензия
MIT License — свободно используйте и модифицируйте проект.



