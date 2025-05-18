#!/bin/bash

# Путь к приложению (предположим, что .app лежит рядом)
APP_PATH="./ToDoManager.app/Contents/MacOS/ToDoManager"

# Переходим в директорию приложения (важно для SQLite!)
cd "$(dirname "$APP_PATH")"

# Запуск
exec "$APP_PATH"
