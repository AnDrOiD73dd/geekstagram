# Geekstagram
Own implementation of the instagram

# TODO
1. Delete android-framework code from presenter
2. Do not pass android-classes to presenter
3. Совет: репозиторий работает с контекстом, поэтому имеет смысл инкапсулировать работу с ним в 
специальных классах (PrefUtil например). Создаем его, передаем в него контекст
(можно даже ApplicationContext). Далее инжектим PrefUtil в репозиторий через 
конструктор например. Получается чистый тестируемый код, без зависимостей от Android.
