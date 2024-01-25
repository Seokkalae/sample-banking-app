CREATE EXTENSION pgcrypto;
-- public.account определение

CREATE TABLE public.account (
                                id uuid NOT NULL DEFAULT gen_random_uuid(), -- id аккаунта
                                first_name varchar(255) NOT NULL, -- имя
                                last_name varchar(255) NOT NULL, -- фамилия
                                patronymic varchar(255) NULL, -- отчество
                                CONSTRAINT account_pk PRIMARY KEY (id)
);
COMMENT ON TABLE public.account IS 'аккаунт (человек)';

-- Column comments

COMMENT ON COLUMN public.account.id IS 'id аккаунта';
COMMENT ON COLUMN public.account.first_name IS 'имя';
COMMENT ON COLUMN public.account.last_name IS 'фамилия';
COMMENT ON COLUMN public.account.patronymic IS 'отчество';

-- public.bank_account определение

CREATE TABLE public.bank_account (
                                     id uuid NOT NULL DEFAULT gen_random_uuid(), -- id банковского счета
                                     money_funds money NOT NULL, -- денежные средства
                                     pin varchar(4) NOT NULL, -- пин код
                                     account_id uuid NOT NULL, -- id аккаунта
                                     CONSTRAINT bank_account_pk PRIMARY KEY (id)
);
COMMENT ON TABLE public.bank_account IS 'банковский счет';

-- Column comments

COMMENT ON COLUMN public.bank_account.id IS 'id банковского счета';
COMMENT ON COLUMN public.bank_account.money_funds IS 'денежные средства';
COMMENT ON COLUMN public.bank_account.pin IS 'пин код';
COMMENT ON COLUMN public.bank_account.account_id IS 'id аккаунта';


-- public.bank_account внешние ключи

ALTER TABLE public.bank_account ADD CONSTRAINT bank_account_account_fk FOREIGN KEY (account_id) REFERENCES public.account(id);

CREATE TYPE operation_type AS ENUM ('DEPOSIT', 'TRANSFER', 'WITHDRAW');

-- public.history определение

CREATE TABLE public.history (
                                id uuid NOT NULL DEFAULT gen_random_uuid(), -- id истории
                                account_id uuid NOT NULL, -- id аккаунта
                                operation_type public.operation_type NOT NULL, -- тип операции (enum: deposit, transfer, withdraw)
                                operation_sum money NOT NULL, -- сумма операции
                                operation_timestamp timestamptz NOT NULL DEFAULT now(), -- время операции (время создание записи в таблице)
                                to_account_id uuid NOT NULL, -- id аккаунта, кому переводят деньги
                                CONSTRAINT history_pk PRIMARY KEY (id)
);

-- Column comments

COMMENT ON COLUMN public.history.id IS 'id истории';
COMMENT ON COLUMN public.history.account_id IS 'id аккаунта';
COMMENT ON COLUMN public.history.operation_type IS 'тип операции (enum: deposit, transfer, withdraw)';
COMMENT ON COLUMN public.history.operation_sum IS 'сумма операции';
COMMENT ON COLUMN public.history.operation_timestamp IS 'время операции (время создание записи в таблице)';
COMMENT ON COLUMN public.history.to_account_id IS 'id аккаунта, кому переводят деньги';


-- public.history внешние включи

ALTER TABLE public.history ADD CONSTRAINT history_account_fk FOREIGN KEY (account_id) REFERENCES public.account(id);
ALTER TABLE public.history ADD CONSTRAINT history_to_account_fk FOREIGN KEY (to_account_id) REFERENCES public.account(id);
