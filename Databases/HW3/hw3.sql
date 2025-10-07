-- 1.1

drop table if exists sales_performance

create table sales_performance(
    sales_person_id int,
    name varchar(60),
    territory varchar(20),
    total_sales_value int,
    joining_date date,
    Primary Key (sales_person_id)

);

-- 1.2
insert into sales_performance values
    (2333, "Gregorio", "Bavaria", 19220, 21-Sep-2021),
    (4323, "Aaron", "London", 320000, 10-Jan-2018),
    (1113, "Sebastian", "London", 32433, 31-Oct-2020),
    (4134, "Pierre", "Paris", 21214, 01-Aug-2020);

-- 1.3
select sales_person_id
from sales_performance
order by sales_person_id;

-- 1.4

select *
from sales_performance
order by name;

--1.5
select *
from sales_performance
order by concat(territory, name);

--2.1
drop table if exists Customers

create table Customers(
    ID int,
    NAME varchar(60),
    AGE int,
    ADDRESS varchar(30),
    Salary real,
    Primary Key (sales_person_id)

);


--2.2
insert into Customers values
    (1, 'Ramesh', 32, 'Ahmedabad', 2000.00),
    (2, 'Khilan', 25, 'Delhi', 1500.00),
    (3, 'Kaushik', 23, 'Kota', 2000.00),
    (4, 'Chaitali', 25, 'Mumbai', 6500.00),
    (5, 'Hardik', 27, 'Bhopal', 8500.00),
    (6, 'Komal', 22, 'Hyderabad', 4500.00),
    (7, 'Muffy', 24, 'Indore', 10000.00)

-- 2.3
select *, min(salary) as min_salary
from Customers
group by (ADDRESS, AGE)
having AGE > 25

-- 2.4
select *
from Customers
group by (ADDRESS, AGE, salary)
having salary < 5000
order by desc sum(salary)



-- 2.5
select ADDRESS
from Customers
where avg(salary) > 5240
group by ADDRESS;