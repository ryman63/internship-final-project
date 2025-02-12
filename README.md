# Система управления стажировками компании Digital Spirit.

Для управления стажировками компании Digital Spirit будет использоваться 
описанная ниже система. Ваша задача реализовать backend приложение для 
системы управления стажировками. Вы реализуете REST API, предполагая, что в дальнейшем с 
ним будет взаимодействовать реализованное frontend-приложение или различные клиенты. 
Вся бизнес-логика должна находиться на стороне backend.

## Требования к системе:
- Spring Boot;
- Spring Security;
- REST API;
- PostgreSQL;
- Flyway;
- Gitlab CE;
- Docker.

## Задачи системы:
- Учёт стажировок;
- Учёт участников;
- Архив информации о предыдущих стажировках и участниках;
- Хранение занятий и задач;
- Управление репозиториями участников;
- Учёт успеваемости участников.

## Описание системы и функциональные требования:

Система и все компоненты, должны разворачиваться с помощью docker-compose, 
а необходимые для работы данные хранить в ./data. 
REST API должно быть разделено на три части: публичная, пользовательская и административная. 
Административная часть должна быть доступна пользователям с ролью admin. 
Пользовательская информация должна быть доступна только владельцу данных. 
Соответственно, публичная доступна всем.
Приватные части API должно быть защищены с помощью Spring Security. 
У сущностей могут быть статусы. Проект должен быть покрыт unit тестами. 
Все api должны документированы в swagger. Классы должны быть документированы.

Минимальный набор атрибутов для стажировки: Название, описание, дата начала, дата окончания, 
дата окончания записи, статус стажировки. Записаться на курс можно только пока доступна запись.

Для записи на стажировку пользователь должен предоставить следующие данные:
ФИО, e-mail, мобильный номер, username, telegram id, информация о себе, дата рождения, 
город проживания, статус обучения (студент, закончил обучение, не имею профильного образования), 
ВУЗ/СУЗ, факультет, специальность, курс. 
Допускается множественная запись на стажировки, т.е. один человек может несколько раз записываться 
на стажировки компании, но в конкретную стажировку только один раз. При записи необходимо проверять 
не проходил ли данный человек стажировку ранее и если проходил, то обновлять информацию.

Если участник отчисляется со стажировки его успеваемость и пользовательская информация должны храниться в архиве, 
так же как и сама стажировка.

У каждой стажировки есть уникальная структура занятий и задач. 
У каждого занятия есть множество задач. У каждой задачи есть название и эталонный репозиторий в gitlab. 
В административной части должна быть возможность публикации занятия. 
После публикации занятия в gitlab должны быть созданы репозитории всех задач занятия для каждого активного участника.
Т.е. создан forkEntity эталонного репозитория.
В случае ошибки необходимо создать сообщение для всех пользователей с ролью admin.

В административной части должна быть возможность проверить задачи занятия. 
После запуска процесса в gitlab проверяется наличие свежих коммитов, и возвращается список содержащий: 
id задачи, название задачи, username_id, username, дата и время последнего коммита, 
автор последнего коммита, ссылка на коммит. Проверку необходимо осуществлять только у незасчитанных задач 
или у задач отправленных на доработку.

Администратор должен иметь возможность оценить задачу с помощью следующих атрибутов: 
зачет/незачёт, комментарий. После проверки создать сообщение пользователю.р
Также администратор может сформировать ведомость по всей стажировке. 
В ведомость включаются все студенты и задачи, которые были опубликованы на момент формирования 
ведомости. Участник должен иметь возможность посмотреть собственную успеваемость в 
пользовательской части.

В пользовательской и административной части должна быть возможность 
получать все сообщения пользователя. 