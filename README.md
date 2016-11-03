# Краткое описание

Два проекта:

 * advt-serv - сервер эмлирующий внешний сервис для запроса информации по рекламе (случайный слоган). Эмулирует случайную задержку.
 * advt-hub - микросервис для запроса рекламы. Имеет 2 микросервиса. Основной сервис, для запроса рекламы, эмулирует задержку выполнения бизнес логики (25 мсек), и если общее время запроса (с учетом запроса у внешнему сервису рекламы) превысило допустимый порог, возвращает ответ, указывающий на необходимость клиенту, повторить запрос через определенное время.

запуск серверов 
java -jar builded-jar-file.jar  [--param.one=some_value --param.two=some_value ...]

Все параметры c описанием находятся в файле resources/application.properties каждого проекта

В файле Advt_test.jmx содержится проект JMeter, для эмулирования нагрузки