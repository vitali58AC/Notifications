# Notifications

Приложение для изучения уведомлений в системе Android. Изменения в работе с уведомлениями начиная с > Android 8.

Используется: FireBase Message Cloud, Jetpack Compose, Retrofit, Room, Coil, Coroutine.

**Описание приложения**
С главного экрана можно перейти на экран:
  - Акций - Акции приходят по каналу низкого приоритета.
  - Сообщений - Сообщения приходят по каналу с высоким приоритетом и звуком. Сообщения поддерживают Reply-to-action для ответа прямо в шторке уведомлений. 
    Сообщения сохраняются в базу данных.
  - Экран загрузки файла. При загрузке будет отображаться прогресс в шторке уведомлений.

Запрос можно осуществить с помощью curl, скрипт находится в корне проекта.
