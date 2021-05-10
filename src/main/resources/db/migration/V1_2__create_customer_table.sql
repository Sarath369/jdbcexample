alter  TABLE `customer` ADD `password` VARCHAR(200) ;
alter  TABLE `customer` ADD `haspassword` BOOLEAN default false;
alter  TABLE `customer` ADD `isdeleted` BOOLEAN default false;