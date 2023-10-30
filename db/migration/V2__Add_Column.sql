            -- db/migration/V2__Add_Column.sql

ALTER TABLE employees
    ADD COLUMN hire_date DATE;
