# Geekstagram
Own implementation of the instagram

# TODO
1. Do not pass android-classes to presenter
2. Совет: репозиторий работает с контекстом, поэтому имеет смысл инкапсулировать работу с ним в 
специальных классах (PrefUtil например). Создаем его, передаем в него контекст
(можно даже ApplicationContext). Далее инжектим PrefUtil в репозиторий через 
конструктор например. Получается чистый тестируемый код, без зависимостей от Android. 
Полезная ссылка: http://telegra.ph/Android-Architecture-05-04
