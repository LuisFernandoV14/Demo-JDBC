create user if not exists 'Luis Fernando'@'localhost' identified by ; -- Got rid of the password for security reasons | Tirei a senha daqui por questão de segurança

create database if not exists projetoJDBC;

grant all privileges on projetoJDBC.* to 'Luis Fernando'@'localhost';
grant all privileges on projetoJDBC.department to 'Luis Fernando'@'localhost';
grant all privileges on projetoJDBC.seller to 'Luis Fernando'@'localhost';


