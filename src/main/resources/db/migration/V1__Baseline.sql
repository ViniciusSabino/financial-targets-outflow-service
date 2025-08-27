-- Baseline Flyway - V1__baseline.sql
-- Estrutura inicial do banco de dados Financial Targets

-- Função para atualizar coluna updated_at
CREATE FUNCTION public.update_updated_at_column() RETURNS trigger
    LANGUAGE plpgsql
AS $$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$;

-- Tabelas

CREATE TABLE public.account_type (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE public.users (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE public.account (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    account_type_id BIGINT NOT NULL REFERENCES public.account_type(id) ON DELETE CASCADE,
    is_main BOOLEAN DEFAULT false NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT now() NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT now() NOT NULL,
    is_active BOOLEAN DEFAULT true NOT NULL,
    balance REAL DEFAULT 0.00 NOT NULL,
    user_id BIGINT NOT NULL REFERENCES public.users(id) ON DELETE CASCADE
);

CREATE TABLE public.income_statuses (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    status VARCHAR(255) NOT NULL
);

CREATE TABLE public.income_types (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    type VARCHAR(255) NOT NULL
);

CREATE TABLE public.incomes (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES public.users(id) ON DELETE CASCADE,
    account_id BIGINT NOT NULL REFERENCES public.account(id) ON DELETE CASCADE,
    income_type_id BIGINT NOT NULL REFERENCES public.income_types(id) ON DELETE SET NULL,
    income_status_id BIGINT NOT NULL REFERENCES public.income_statuses(id) ON DELETE SET NULL,
    amount REAL NOT NULL CHECK (amount > 0),
    date TIMESTAMP(6) WITH TIME ZONE NOT NULL,
    description VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Trigger para atualizar updated_at em incomes
CREATE TRIGGER trigger_update_income_updated_at
BEFORE UPDATE ON public.incomes
FOR EACH ROW EXECUTE FUNCTION public.update_updated_at_column();

CREATE TABLE public.essential_outflows (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    value NUMERIC(10,2) NOT NULL,
    due_date TIMESTAMP(6) WITH TIME ZONE NOT NULL,
    paid_value NUMERIC(10,2) DEFAULT 0,
    is_fully_paid BOOLEAN GENERATED ALWAYS AS (paid_value >= value) STORED,
    notes VARCHAR(255),
    account_id BIGINT NOT NULL REFERENCES public.account(id),
    user_id BIGINT NOT NULL REFERENCES public.users(id),
    recurrence TEXT CHECK (recurrence IN ('MONTHLY', 'ANNUAL')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE public.outflow_allocations (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    defined_percentage NUMERIC(5,2) NOT NULL CHECK (defined_percentage >= 0 AND defined_percentage <= 100),
    total_outflow_value NUMERIC(10,2) NOT NULL CHECK (total_outflow_value >= 0),
    applied_value NUMERIC(10,2) DEFAULT 0 NOT NULL CHECK (applied_value >= 0),
    remaining_value NUMERIC(10,2) GENERATED ALWAYS AS (total_outflow_value - applied_value) STORED,
    account_id BIGINT NOT NULL REFERENCES public.account(id),
    recurrence_type VARCHAR(20) CHECK (recurrence_type IN ('MONTHLY', 'ANNUAL')),
    allocation_date DATE DEFAULT CURRENT_DATE NOT NULL,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    type TEXT DEFAULT 'CUSTOMIZED' NOT NULL CHECK (type IN ('FREE', 'CUSTOMIZED')),
    user_id BIGINT NOT NULL REFERENCES public.users(id)
);