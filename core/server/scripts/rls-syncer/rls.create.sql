
CREATE DATABASE `rls` CHARACTER SET utf8 COLLATE utf8_general_ci;

GRANT ALL PRIVILEGES  ON rls.* TO 'root'@'%' IDENTIFIED BY 'root';
GRANT ALL PRIVILEGES  ON rls.* TO 'root'@'%';
GRANT ALL PRIVILEGES  ON rls.* TO 'tmis'@'%';
