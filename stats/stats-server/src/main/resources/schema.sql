-- Определение таблицы requests

-- Удаление таблицы, если она существует
DROP TABLE IF EXISTS requests;

-- Создание таблицы requests
CREATE TABLE IF NOT EXISTS requests (
    id BIGSERIAL PRIMARY KEY, -- Идентификатор записи
    app VARCHAR(150) NOT NULL, -- Идентификатор сервиса
    uri VARCHAR(250) NOT NULL, -- URI запроса
    ip VARCHAR(45) NOT NULL, -- IP-адрес пользователя
    time_stamp TIMESTAMP WITHOUT TIME ZONE NOT NULL -- Дата и время запроса
    );

-- Комментарии к столбцам
COMMENT ON COLUMN requests.id IS 'Идентификатор записи';
COMMENT ON COLUMN requests.app IS 'Идентификатор сервиса';
COMMENT ON COLUMN requests.uri IS 'URI запроса';
COMMENT ON COLUMN requests.ip IS 'IP-адрес пользователя';
COMMENT ON COLUMN requests.time_stamp IS 'Дата и время запроса';