# Мобильное приложение "Персональный помощник автомобильного клининга"

## Описание

Мобильное приложение "Персональный помощник автомобильного клининга" разработано для помощи автовладельцам в определении оптимального времени для мытья своих автомобилей. Приложение предоставляет пользователю информацию о погодных условиях, список ближайших автомоек с рейтингом и контактной информацией, а также позволяет отслеживать расходы на мойку автомобиля.

## Функциональность

- **Извлечение и анализ данных о погоде:** Приложение использует [WeatherAPI](https://www.weatherapi.com) для получения актуальной информации о погоде. Анализируя эти данные, оно предоставляет рекомендацию оптимального дня для мытья автомобиля.
- **Рекомендация оптимального дня мойки автомобиля:** Используя данные о погоде, приложение предоставляет рекомендации о наилучшем времени для мойки автомобиля, чтобы избежать неблагоприятных погодных условий, таких как дождь или сильный ветер.
- **Отображение автомоек поблизости на карте:** Приложение использует [Google Maps API](https://developers.google.com/maps) и [Places API](https://developers.google.com/maps/documentation/places/web-service/overview) для отображения автомоек, находящихся поблизости от пользователя.
- **Отправка уведомлений:** Приложение отправляет уведомления пользователю, когда наступает оптимальное время для мойки автомобиля согласно рекомендации на основе погодных данных.
- **Отслеживание расходов на автомойку:** Пользователь может отслеживать свои расходы на мойку автомобиля, записывая информацию о стоимости и дате мойки.

## Используемые API и инструменты

- **API:**
    - [WeatherAPI](https://www.weatherapi.com)
    - [Google Maps API](https://developers.google.com/maps)
    - [Places API](https://developers.google.com/maps/documentation/places/web-service/overview)

- **Инструменты:**
    - RoomDatabase
    - OkHttp
    - MVVM 

## Скриншоты

![Скриншот 1](https://github.com/ismailkhanova/MyFirstApp/blob/master/readmefiles/photo_2023-05-21_19-57-30.jpg width="100")
_Основной экран приложения, отображающий рекомендацию по мойке автомобиля и прогноз погоды_

![Скриншот 2](https://github.com/ismailkhanova/MyFirstApp/blob/master/readmefiles/photo_2023-05-21_19-57-32.jpg | width=100)
_Отображение экрана записи расходов_

![Скриншот 3](https://github.com/ismailkhanova/MyFirstApp/blob/master/readmefiles/photo_2023-05-21_19-57-36.jpg | width=100)
_Экран отображения карты_
