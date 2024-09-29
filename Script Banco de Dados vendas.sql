create database vendas;

use vendas;

CREATE TABLE `vendas`.`clientes` (
  `codcli` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(40) NOT NULL,
  `ender` VARCHAR(45) NOT NULL,
  `bairro` VARCHAR(20) NOT NULL,
  `cidade` VARCHAR(20) NOT NULL,
  `cep` VARCHAR(9) NOT NULL,
  `uf` CHAR(2) NOT NULL,
  `email` VARCHAR(30) NOT NULL,
  `fone` VARCHAR(10) NOT NULL,
  `celular` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`codcli`));

CREATE TABLE `vendas`.`pedidos` (
  `codped` INT NOT NULL AUTO_INCREMENT,
  `codcliente` INT NOT NULL,
  `data` DATETIME NOT NULL,
  PRIMARY KEY (`codped`),
  INDEX `codcliente_idx` (`codcliente` ASC) VISIBLE,
  CONSTRAINT `codcliente`
    FOREIGN KEY (`codcliente`)
    REFERENCES `vendas`.`clientes` (`codcli`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `vendas`.`produtos` (
  `codprod` INT NOT NULL AUTO_INCREMENT,
  `descricao` VARCHAR(30) NOT NULL,
  `preco` DECIMAL(10,2) NOT NULL,
  `unidade` CHAR(2) NOT NULL,
  `qtde_inicial` DECIMAL(10,2) NOT NULL,
  `data_cad` DATETIME NOT NULL,
  `qtde_atual` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`codprod`));
  
CREATE TABLE `vendas`.`itens_ped` (
`id` INT NOT NULL AUTO_INCREMENT,
  `codped` INT NOT NULL,
  `codprod` INT NOT NULL,
  `qtde` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `codped_idx` (`codped` ASC) VISIBLE,
  INDEX `codprod_idx` (`codprod` ASC) VISIBLE,
  CONSTRAINT `codped`
    FOREIGN KEY (`codped`)
    REFERENCES `vendas`.`pedidos` (`codped`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `codprod`
    FOREIGN KEY (`codprod`)
    REFERENCES `vendas`.`produtos` (`codprod`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
